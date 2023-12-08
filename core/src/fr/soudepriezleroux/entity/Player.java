package fr.soudepriezleroux.entity;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Player extends Entity{

    private int speed;
    public Player(String prefix, boolean isAnimated, int nbrFrame, float width, float height,
                  float x, float y, float textureSizeX, float textureSizeY, Facing facing) {
        super(prefix, isAnimated, nbrFrame, width, height, x, y, textureSizeX, textureSizeY, facing);

        this.speed = 200;
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

    @Override
    public void render(SpriteBatch spriteBatch){
        float[] screenCoord = getScreenCoord();
        run(screenCoord);
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