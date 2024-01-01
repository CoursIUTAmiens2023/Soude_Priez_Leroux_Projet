package fr.soudepriezleroux.entity;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

import java.util.ArrayList;
import java.util.UUID;

public class Map {

    private static Sprite sprite;
    private static float[] screenCoord;
    private static float[] textureSize;
    private static Rectangle hitbox;
    private static SpriteBatch spriteBatch;

    public static void init(Camera camera){
        spriteBatch = new SpriteBatch();

        spriteBatch.setProjectionMatrix(camera.combined);
    }

    public Map(String prefix, float coordX, float coordY, float textureSizeX, float textureSizeY, int srcWidth, int srcHeight){
        hitbox = new Rectangle(coordX-(textureSizeX/2),coordY,textureSizeX,textureSizeY);
        screenCoord = new float[]{coordX,coordY};
        textureSize = new float[]{textureSizeX,textureSizeY};
        sprite = new Sprite(new Texture(Gdx.files.internal(prefix + "_f_0.png")),0,0, srcWidth, srcHeight);

    }

    public static void render(){

        spriteBatch.begin();
        hitbox.x = screenCoord[0];
        hitbox.y = screenCoord[1];
        sprite.setBounds(hitbox.x, hitbox.y,textureSize[0],textureSize[1]);
        sprite.setOriginCenter();
        sprite.draw(spriteBatch);

        spriteBatch.end();
    }
}
