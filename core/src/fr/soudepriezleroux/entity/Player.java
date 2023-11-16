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

    @Override
    public void render(SpriteBatch spriteBatch){
        super.render(spriteBatch);
        float[] screenCoord = getScreenCoord();

        if(!(Gdx.input.isKeyPressed(Input.Keys.LEFT) && Gdx.input.isKeyPressed(Input.Keys.RIGHT)) &&
                !(Gdx.input.isKeyPressed(Input.Keys.UP) && Gdx.input.isKeyPressed(Input.Keys.DOWN))){

            if(Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
                this.setCoord(screenCoord[0]+(speed * Gdx.graphics.getDeltaTime()), screenCoord[1]);
                //this.facing = Facing.RIGHT;        Attente des modif de la class Entity

            } else if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
                this.setCoord(screenCoord[0]-(speed * Gdx.graphics.getDeltaTime()), screenCoord[1]);
                //this.facing = Facing.LEFT;        Attente des modif de la class Entity
            } else if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
                this.setCoord(screenCoord[0], screenCoord[1]+(speed * Gdx.graphics.getDeltaTime()));
                //this.facing = Facing.UP;        Attente des modif de la class Entity

            } else if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
                this.setCoord(screenCoord[0], screenCoord[1]-(speed * Gdx.graphics.getDeltaTime()));
                //this.facing = Facing.DOWN;        Attente des modif de la class Entity
            }
        }
    }
}