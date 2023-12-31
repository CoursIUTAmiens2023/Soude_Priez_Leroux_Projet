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
    //Identificateur unique de chaque entity
    private final UUID uuid;
    //Liste des textures de l'entité
    private final ArrayList<Sprite> sprites;

    private final Rectangle hitbox;
    //Coordonées sur l'ecran de l'entité
    protected float[] screenCoord;
    //Taille de la texture a affiché a l'écran
    private final float[] textureSize;
    //Si l'entité doit etre annimé entre ces plusieurs textures
    private final boolean isAnimated;
    //Variables de travail qui sert a gardé information du temps qui passe
    private long animationLoopTimer = 0;
    //Temps en milliseconds entre chaque frame de la texture
    private final int animationTime = 500;
    //Frame de base de l'animation
    private int animationFrame = 0;
    private ArrayList<Sprite> spritesBoosted;   //Sprite pour quand PacMan a mangé un fruit
    //Direction dans laquelle l'entité regarde
    protected Facing facing;

    public Entity(String prefix, boolean isAnimated, int nbrFrame, float width, float height, float x, float y, float textureSizeX, float textureSizeY, Facing facing){

        //Genere l'indentificateur unique et aléatoire
        this.uuid = UUID.randomUUID();

        this.facing = facing;

        this.isAnimated = isAnimated;
        hitbox = new Rectangle(x-(textureSizeX/2),y,width,height);
        screenCoord = new float[]{x,y};
        textureSize = new float[]{textureSizeX,textureSizeY};
        //Recuperation des diffrentes textures pour l'animation et donne la bonne orientation
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

    public Facing getFacing() {
        return facing;
    }

    public void setFacing(Facing facing) {
        this.facing = facing;
    }

    public void move(float distance){
        if(distance <= 0){
            return;
        }

        int currentX = (int) Math.ceil(this.screenCoord[0] / 30) - 1;
        int currentY = (int) Math.ceil(this.screenCoord[1] / 30) - 1;

        switch (this.facing){
            case RIGHT:
                if(currentX == (int) Math.ceil((this.screenCoord[0] + distance + textureSize[0] - 4) / 30) - 1){
                    this.screenCoord[0] += distance;
                    break;
                }
                if(currentX + 1 > 26){
                    break;
                }
                if(MapManager.getData()[30-currentY][(int) Math.ceil((this.screenCoord[0] + distance + textureSize[0] - 4) / 30) - 1] != 4){
                    if(MapManager.getData()[32 - (int) Math.ceil((this.screenCoord[1] + distance + textureSize[0] - 4) / 30) - 1][(int) Math.ceil((this.screenCoord[0] + distance + textureSize[0] - 4) / 30) - 1] == 4){
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
                if(currentY == (int) Math.ceil((this.screenCoord[1] + distance + textureSize[0] - 4) / 30) - 1){
                    this.screenCoord[1] += distance;
                    break;
                }
                if(currentY + 1 > 30){
                    break;
                }
                if(MapManager.getData()[32 - (int) Math.ceil((this.screenCoord[1] + distance + textureSize[0] - 4) / 30) - 1][currentX] != 4){
                    if(MapManager.getData()[32 - (int) Math.ceil((this.screenCoord[1] + distance + textureSize[0] - 4) / 30) - 1][(int) Math.ceil((this.screenCoord[0] + distance + textureSize[0] - 4) / 30) - 1] == 4){
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