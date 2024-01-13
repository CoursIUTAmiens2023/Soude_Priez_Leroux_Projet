package fr.soudepriezleroux.entity;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import fr.soudepriezleroux.entity.ghost.Ghost;

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
    private boolean isInvincible;
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

    /**
     * Points de vie du joueur
     */
    private int lives;

    /**
     * Position de début du pacMan
     */
    private float[]startCoord;

    private int centreX;

    private int centreY;

    private int pointsMiam;

    private int pointsFantomes;

    private float[] screenCoordTunelGauche = new float[]{(float) 1, (float) 485.31522};
    private float[] screenCoordTunelDroit = new float[]{(float) 790, (float) 485.31522};


    public Player(String prefix, boolean isAnimated, int nbrFrame, float width, float height,
                  float x, float y, float textureSizeX, float textureSizeY, Facing facing) {
        super(prefix, isAnimated, nbrFrame, width, height, x, y, textureSizeX, textureSizeY, facing);

        this.speed = 50;
        this.isInvincible = false;
        this.points = 0;
        comboGhost = 0;
        eatenObject = new ArrayList<>();
        lives = 3;
        startCoord = new float[]{x,y};
        centreX = (int)width/2;
        centreY = (int)height/2;
        pointsMiam = 0;
        this.pointsFantomes = 0;
    }

    /**
     * Fonction permetant le déplacement du joueur sur l'écran
     * @param screenCoord - Les coordonnées du joueur sur l'écran
     */
    public void run(float[] screenCoord){
        if(!goTunel(screenCoord)){
            if (screenCoord[1] < 931 && screenCoord[1] > 0 && screenCoord[0] > 0 && screenCoord[0] < 811){
                this.move(speed * Gdx.graphics.getDeltaTime());
            }
        }
    }

    public int getSpeed() {
        return speed;
    }

    /**
     * Ajout des points quand le joueur mange un fantome
     */
    public void eatGhost(){
        if (comboGhost < 4){
            int calcul = 200 * (int)Math.pow(2, comboGhost);
            points += calcul;
            pointsFantomes+= calcul;
            comboGhost++;
        }
    }

    public int getPointsFantomes() {
        return pointsFantomes;
    }

    /**
     * Verifie et place pac-mac lorsqu'il traverse le tunnel
     */
    public boolean goTunel(float[] screenCoord){
        if (screenCoord[0] < screenCoordTunelGauche[0] && this.facing == Facing.LEFT){
            this.setCoord(screenCoordTunelDroit[0], screenCoordTunelDroit[1]);
            return true;
        }
        if (screenCoord[0] > screenCoordTunelDroit[0] && this.facing == Facing.RIGHT) {
            this.setCoord(screenCoordTunelGauche[0], screenCoordTunelGauche[1]);
            return true;
        }

        return false;
    }

    /**
     * Fonction qui gère l'ajout de points suite aux interactions du joueur sur les différentes entités
     * @param miamMiam - L'entité que le joueur mange
     */
    public void eatCheese(Entity miamMiam){
        String name = miamMiam.getClass().getSimpleName();
        if (!eatenObject.contains(miamMiam.getUuid())){
            if (name.equals("PacGum")) {
                setIsInvincible(true);
                for (Ghost ghost : EntityManager.getGhosts()){
                    ghost.setFrightened();
                }
                setTimeInvicible(System.currentTimeMillis());
            }
            int ptsTemp = ((MiniCheese) miamMiam).getPoints();
            points += ptsTemp;
            eatenObject.add(miamMiam.getUuid());
            if (ptsTemp == 10){
                pointsMiam++;
            }
        }
    }

    private static long getTimeInvicible() {
        return timeInvicible;
    }

    public int getPoints() {
        return points;
    }

    private static void setTimeInvicible(long timeInvicible) {
        Player.timeInvicible = timeInvicible;
    }

    @Override
    public Rectangle getHitbox() {
        return super.getHitbox();
    }

    public boolean isIsInvincible() {
        return isInvincible;
    }

    private void setIsInvincible(Boolean isInvincible){
        this.isInvincible = isInvincible;
    }

    private void resetComboGhost() {
        this.comboGhost = 0;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public void hitGhost(){
        setLives(lives-1);
    }

    public int getCentreX() {
        return centreX;
    }

    public void setCentreX(int centreX) {
        this.centreX = centreX;
    }

    public int getCentreY() {
        return centreY;
    }

    public void setCentreY(int centreY) {
        this.centreY = centreY;
    }

    public int getPointsMiam() {
        return pointsMiam;
    }

    public void setPointsMiam(int pointsMiam) {
        this.pointsMiam = pointsMiam;
    }

    @Override
    public void render(SpriteBatch spriteBatch){
        float[] screenCoord = getScreenCoord();
        run(screenCoord);

        // Regarde le temps d'invincibilité
        // Si le temps est suppérieur
        if (System.currentTimeMillis() - getTimeInvicible() > 10000) {
            setIsInvincible(false);
            for (Ghost ghost : EntityManager.getGhosts()){
                ghost.setChase();
            }
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

    public int getLives() {
        return lives;
    }

    private void setLives(int lives) {
        this.lives = lives;
    }

    public float[] getStartCoord() {
        return startCoord;
    }
}
