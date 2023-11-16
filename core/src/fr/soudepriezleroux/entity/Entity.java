package fr.soudepriezleroux.entity;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.TimeUtils;

import java.util.ArrayList;
import java.util.UUID;

public class Entity {
    private final UUID uuid;
    private final ArrayList<Sprite> textures;
    private final Rectangle hitbox;
    private float[] screenCoord;
    private final float[] textureSize;
    private final boolean isAnimated;
    private long animationLoopTimer = 0;
    private final int animationTime = 500;
    private int animationFrame = 0;
    private Facing facing;

    public Entity(String prefix, boolean isAnimated, int nbrFrame, float width, float height, float x, float y, float textureSizeX, float textureSizeY, Facing facing){

        this.uuid = UUID.randomUUID();

        this.facing = facing;

        this.isAnimated = isAnimated;
        hitbox = new Rectangle(x-(textureSizeX/2),y,width,height);
        screenCoord = new float[]{x,y};
        textureSize = new float[]{textureSizeX,textureSizeY};
        textures = new ArrayList<>();

        for (int i = 0; i < nbrFrame; i++) {
            Sprite spriteTemp = new Sprite(new Texture(Gdx.files.internal(prefix + "_f_"+i+".png")),0,0,16,16);
            spriteTemp.setRotation(facing.get());
            textures.add(spriteTemp);
        }
    }

    public void render(SpriteBatch spriteBatch){

        hitbox.x = screenCoord[0];
        hitbox.y = screenCoord[1];

        if(isAnimated){
            if(TimeUtils.millis() > animationLoopTimer + animationTime){
                if(animationFrame == 0){
                    animationFrame = 1;
                }else{
                    animationFrame = 0;
                }
                animationLoopTimer = TimeUtils.millis();
            }
        }

        Sprite spriteTemp = textures.get(animationFrame);
        spriteTemp.setBounds(hitbox.x, hitbox.y,textureSize[0],textureSize[1]);
        spriteTemp.setOriginCenter();
        spriteTemp.setRotation(facing.get());
        spriteTemp.draw(spriteBatch);
    }

    public void dispose(){
        for (Sprite sprite:textures) {
            sprite.getTexture().dispose();
        }
    }


    public UUID getUuid() {
        return uuid;
    }

    public float[] getScreenCoord() {
        return screenCoord;
    }
    public void setCoord(float x, float y){
        this.screenCoord[0] = x;
        this.screenCoord[1] = y;
    }

    public boolean isColliding(Rectangle hitbox){
        return hitbox.overlaps(this.hitbox);
    }

}