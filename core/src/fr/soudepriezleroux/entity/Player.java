package fr.soudepriezleroux.entity;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

public class Player extends Entity{

    private int speed;
    private static boolean isInvincible;
    private static  long timeInvicible;
    public Player(String prefix, boolean isAnimated, int nbrFrame, float width, float height,
                  float x, float y, float textureSizeX, float textureSizeY, Facing facing) {
        super(prefix, isAnimated, nbrFrame, width, height, x, y, textureSizeX, textureSizeY, facing);

        this.speed = 200;
        isInvincible = false;
    }

    private void run(float[] screenCoord){
        if(this.facing == Facing.UP){
            if (screenCoord[1] < 890){
                this.setCoord(screenCoord[0], screenCoord[1]+(speed * Gdx.graphics.getDeltaTime()));
            }
        }else if(this.facing == Facing.RIGHT){
            if(screenCoord[0] > 0){
                this.setCoord(screenCoord[0]-(speed * Gdx.graphics.getDeltaTime()), screenCoord[1]);
            }
        } else if (this.facing == Facing.DOWN) {
            if(screenCoord[1] > 0){
                this.setCoord(screenCoord[0], screenCoord[1]-(speed * Gdx.graphics.getDeltaTime()));
            }
        } else if (this.facing == Facing.LEFT) {
            if(screenCoord[0] < 1920-10) {
                this.setCoord(screenCoord[0] + (speed * Gdx.graphics.getDeltaTime()), screenCoord[1]);
            }
        }
    }

    public int getSpeed() {
        return speed;
    }

    public static void EatCheese(Object miamMiam){
        if (miamMiam.getClass().getSimpleName().equals("PacGum")){
            isInvincible = true;
            timeInvicible = System.currentTimeMillis();
            // + Ajout des points
        } else {
            // Ajout des points
        }   // Prise en compte de l'ingestion des fantomes
    }

    @Override
    public Rectangle getHitbox() {
        return super.getHitbox();
    }

    @Override
    public void render(SpriteBatch spriteBatch){
        float[] screenCoord = getScreenCoord();
        run(screenCoord);

        if (System.currentTimeMillis() - timeInvicible > 10000) isInvincible = false;

        if(!(Gdx.input.isKeyPressed(Input.Keys.LEFT) && Gdx.input.isKeyPressed(Input.Keys.RIGHT)) &&
                !(Gdx.input.isKeyPressed(Input.Keys.UP) && Gdx.input.isKeyPressed(Input.Keys.DOWN))){
            if(Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
                this.facing = Facing.LEFT;

            } else if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
                this.facing = Facing.RIGHT;

            } else if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
                this.facing = Facing.UP;

            } else if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
                this.facing = Facing.DOWN;
            }
        }
        super.render(spriteBatch);
    }
}