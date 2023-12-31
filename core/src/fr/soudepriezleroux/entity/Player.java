package fr.soudepriezleroux.entity;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.Rectangle;
import com.sun.org.apache.xpath.internal.operations.Bool;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Player extends Entity{

    /**
     * Vitesse du joueur
     */
    private int speed;
    /**
     * Indique si le joueur est invincible suite à la prise d'un Pac-Gum
     */
    private static boolean isInvincible;
    /**
     * Début de l'invincibilité
     */
    private static  long timeInvicible;
    /**
     * Liste des items déjà mangé par le joueur
     */
    private List<UUID> eatenObject;
    /**
     * Nombre de fantomes mangé durant un effet Pac-Gum
     */
    private int comboGhost;
    /**
     * Score du joueur
     */
    private int points;

    public Player(String prefix, boolean isAnimated, int nbrFrame, float width, float height,
                  float x, float y, float textureSizeX, float textureSizeY, Facing facing) {
        super(prefix, isAnimated, nbrFrame, width, height, x, y, textureSizeX, textureSizeY, facing);

        this.speed = 200;
        isInvincible = false;
        points = 0;
        comboGhost = 0;
        eatenObject = new ArrayList<>();
    }

    /**
     * Fonction permetant le déplacement du joueur sur l'écran
     * @param screenCoord - Les coordonnées du joueur sur l'écran
     */
    private void run(float[] screenCoord){
        if (screenCoord[1] < 931 && screenCoord[1] > 0 && screenCoord[0] > 0 && screenCoord[0] < 811){
            this.move(speed * Gdx.graphics.getDeltaTime());
        }
    }

    public int getSpeed() {
        return speed;
    }

    /**
     * Ajout des points quand le joueur mange un fantome
     */
    public void eatGhost(){
        points += 200 * (int)Math.pow(2, comboGhost);
        comboGhost++;
    }

    /**
     * Fonction qui gère l'ajout de points suite aux interactions du joueur sur les différentes entités
     * @param miamMiam - L'entité que le joueur mange
     * @param uuidEntity - L'id de l'entity mangé
     */
    public void eatCheese(Entity miamMiam, UUID uuidEntity){
        String name = miamMiam.getClass().getSimpleName();
        if (!eatenObject.contains(uuidEntity)){
            if (name.equals("PacGum")) {
                eatenObject.add(uuidEntity);
                setIsInvincible(true);
                setTimeInvicible(System.currentTimeMillis());
                points += ((MiniCheese) miamMiam).getPoints();

            }else if(name.equals("Fruits")){
                points += ((MiniCheese) miamMiam).getPoints();
                eatenObject.add(uuidEntity);

            } else {
                points+= ((MiniCheese) miamMiam).getPoints();
                eatenObject.add(uuidEntity);

            }
        }
    }

    private static long getTimeInvicible() {
        return timeInvicible;
    }

    private static void setTimeInvicible(long timeInvicible) {
        Player.timeInvicible = timeInvicible;
    }

    @Override
    public Rectangle getHitbox() {
        return super.getHitbox();
    }

    public static boolean isIsInvincible() {
        return isInvincible;
    }

    private static void setIsInvincible(Boolean isInvincible){
        Player.isInvincible = isInvincible;
    }

    private void resetComboGhost() {
        this.comboGhost = 0;
    }

    @Override
    public void render(SpriteBatch spriteBatch){

        float[] screenCoord = getScreenCoord();
        run(screenCoord);
        System.out.println(points);

        // Regarde le temps d'invincibilité
        // Si le temps est suppérieur
        if (System.currentTimeMillis() - getTimeInvicible() > 10000) {
            setIsInvincible(false);
            resetComboGhost();
        }

        //  On vérifie quel input le joueur presse et on modifie la direction du joueur en conséquence
        if(!(Gdx.input.isKeyPressed(Input.Keys.LEFT) && Gdx.input.isKeyPressed(Input.Keys.RIGHT)) &&
                !(Gdx.input.isKeyPressed(Input.Keys.UP) && Gdx.input.isKeyPressed(Input.Keys.DOWN))){
            if(Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
                this.facing = Facing.RIGHT;

            } else if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
                this.facing = Facing.LEFT;

            } else if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
                this.facing = Facing.UP;

            } else if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
                this.facing = Facing.DOWN;
            }
        }
        run(getScreenCoord());
        super.render(spriteBatch);
    }
}
