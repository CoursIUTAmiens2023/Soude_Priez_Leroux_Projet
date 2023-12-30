package fr.soudepriezleroux.entity;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.TimeUtils;
import fr.soudepriezleroux.map.MapManager;

import java.util.ArrayList;
import java.util.UUID;

public class Entity {
    private final UUID uuid;
    private final ArrayList<Sprite> sprites;
    private final Rectangle hitbox;
    protected float[] screenCoord;
    private final float[] textureSize;
    private final boolean isAnimated;
    private long animationLoopTimer = 0;
    private final int animationTime = 500;
    private int animationFrame = 0;
    protected Facing facing;

    public Entity(String prefix, boolean isAnimated, int nbrFrame, float width, float height, float x, float y, float textureSizeX, float textureSizeY, Facing facing){

        this.uuid = UUID.randomUUID();

        this.facing = facing;

        this.isAnimated = isAnimated;
        hitbox = new Rectangle(x,y,width,height);
        screenCoord = new float[]{x,y};
        textureSize = new float[]{textureSizeX,textureSizeY};
        sprites = new ArrayList<>();

        for (int i = 0; i < nbrFrame; i++) {
            Sprite spriteTemp = new Sprite(new Texture(Gdx.files.internal(prefix + "_f_"+i+".png")),0,0,16,16);
            spriteTemp.setRotation(facing.get());
            sprites.add(spriteTemp);
        }
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

        Sprite spriteTemp = sprites.get(animationFrame);
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

    public float[] getScreenCoord() {
        return screenCoord;
    }

    public void setCoord(float x, float y){
        this.screenCoord[0] = x;
        this.screenCoord[1] = y;
    }

    public void move(float distance){
        if(distance <= 0){
            return;
        }

        int currentX = (int) Math.ceil(this.screenCoord[0] / 30) - 1;
        int currentY = (int) Math.ceil(this.screenCoord[1] / 30) - 1;

        switch (this.facing){
            case RIGHT:
                if(currentX == (int) Math.ceil((this.screenCoord[0] + distance + textureSize[0]) / 30) - 1){
                    this.screenCoord[0] += distance;
                    break;
                }
                if(currentX + 1 > 26){
                    break;
                }
                if(MapManager.getData()[30-currentY][(int) Math.ceil((this.screenCoord[0] + distance + textureSize[0]) / 30) - 1] != 4){
                    if(MapManager.getData()[32 - (int) Math.ceil((this.screenCoord[1] + distance + textureSize[0]) / 30) - 1][(int) Math.ceil((this.screenCoord[0] + distance + textureSize[0]) / 30) - 1] == 4){
                        break;
                    }
                    this.screenCoord[0] += distance;
                    break;
                }
            case LEFT:
                if(currentX == (int) Math.ceil((this.screenCoord[0] - distance) / 30) - 1){
                    this.screenCoord[0] -= distance;
                    break;
                }
                if(currentX - 1 < 0){
                    break;
                }
                if(MapManager.getData()[30-currentY][(int) Math.ceil((this.screenCoord[0] - distance) / 30) - 1] != 4){
                    this.screenCoord[0] -= distance;
                    break;
                }
            case UP:
                if(currentY == (int) Math.ceil((this.screenCoord[1] + distance + textureSize[0]) / 30) - 1){
                    this.screenCoord[1] += distance;
                    break;
                }
                if(currentY + 1 > 30){
                    break;
                }
                if(MapManager.getData()[32 - (int) Math.ceil((this.screenCoord[1] + distance + textureSize[0]) / 30) - 1][currentX] != 4){
                    if(MapManager.getData()[32 - (int) Math.ceil((this.screenCoord[1] + distance + textureSize[0]) / 30) - 1][(int) Math.ceil((this.screenCoord[0] + distance + textureSize[0]) / 30) - 1] == 4){
                        break;
                    }
                    this.screenCoord[1] += distance;
                    break;
                }
            case DOWN:
                if(currentY == (int) Math.ceil((this.screenCoord[1] - distance) / 30) - 1){
                    this.screenCoord[1] -= distance;
                    break;
                }
                if(currentY - 1 < 0){
                    break;
                }
                if(MapManager.getData()[32 - (int) Math.ceil((this.screenCoord[1] - distance) / 30) - 1][currentX] != 4){
                    this.screenCoord[1] -= distance;
                    break;
                }
        }
    }

}