package fr.soudepriezleroux.entity;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Player extends Entity{

    private int speed;
    public Player(String prefix, boolean isAnimated, int nbrFrame, float width, float height,
                  float x, float y, float textureSizeX, float textureSizeY, Facing facing) {
        super(prefix, isAnimated, nbrFrame, width, height, x, y, textureSizeX, textureSizeY, facing);

        this.speed = 100;
    }

    private void run(float[] screenCoord){
        if (screenCoord[1] < 931 && screenCoord[1] > 0 && screenCoord[0] > 0 && screenCoord[0] < 811){
            this.move(speed * Gdx.graphics.getDeltaTime());
        }
    }

    @Override
    public void render(SpriteBatch spriteBatch){
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
