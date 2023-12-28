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
    private final ArrayList<Sprite> sprites;
    private final Rectangle hitbox;
    private float[] screenCoord;
    private final float[] textureSize;
    private final boolean isAnimated;
    private long animationLoopTimer = 0;
    private final int animationTime = 500;
    private int animationFrame = 0;
    protected Facing facing;
    private ArrayList<Sprite> spritesBoosted;   //Sprite pour quand PacMan a mangé un fruit

    public Entity(String prefix, boolean isAnimated, int nbrFrame, float width, float height, float x, float y, float textureSizeX, float textureSizeY, Facing facing){

        this.uuid = UUID.randomUUID();

        this.facing = facing;

        this.isAnimated = isAnimated;
        hitbox = new Rectangle(x-(textureSizeX/2),y,width,height);
        screenCoord = new float[]{x,y};
        textureSize = new float[]{textureSizeX,textureSizeY};

        sprites =  setListSprite(0, nbrFrame, prefix);

        if (prefix.equals("ghost")){
            spritesBoosted = setListSprite(nbrFrame, (nbrFrame*2), prefix);
        }
    }

    private ArrayList<Sprite> setListSprite(int start, int nbrFrame, String prefix){
        ArrayList<Sprite> spriteArrayListTemp = new ArrayList<>();
        for (int i = start; i < nbrFrame; i++){
            Sprite spriteTemp;
            spriteTemp = new Sprite(new Texture(Gdx.files.internal(prefix + "_f_"+i+".png")),0,0,16,16);
            spriteTemp.setRotation(facing.get());
            spriteArrayListTemp.add(spriteTemp);
        }

        return spriteArrayListTemp;
    }

    public void render(SpriteBatch spriteBatch){

        hitbox.x = screenCoord[0];
        hitbox.y = screenCoord[1];

        if(isAnimated){
            if(TimeUtils.millis() > animationLoopTimer + animationTime){
                if(animationFrame == (sprites.size()-1)){
                    animationFrame = 0;
                } else {
                   animationFrame++;
                }
                animationLoopTimer = TimeUtils.millis();
            }
        }

        Sprite spriteTemp;
        if (spritesBoosted != null && Player.isIsInvincible()){
            spriteTemp = spritesBoosted.get(animationFrame);
        } else {
           spriteTemp = sprites.get(animationFrame);
        }
        spriteTemp.setBounds(hitbox.x, hitbox.y,textureSize[0],textureSize[1]);
        spriteTemp.setOriginCenter();
        spriteTemp.setRotation(facing.get());
        spriteTemp.draw(spriteBatch);


    }

    public void dispose(){
        for (Sprite sprite: sprites) {
            sprite.getTexture().dispose();
        }
    }

    public UUID getUuid() {
        return uuid;
    }

    public Rectangle getHitbox() {
        return hitbox;
    }

    public float[] getScreenCoord() {
        return screenCoord;
    }
    public void setCoord(float x, float y){
        this.screenCoord[0] = x;
        this.screenCoord[1] = y;
    }
}
