package fr.soudepriezleroux.entity;

import com.badlogic.gdx.Gdx;

import java.util.ArrayList;
import java.util.UUID;

public class CollisionManager {
    private static ArrayList<Entity> entities;
    private static Player player;

    public static void init(ArrayList<Entity> entities, UUID player){
        CollisionManager.entities = entities;
        for (Entity entity: entities){
            if (entity.getUuid().equals(player)){
                CollisionManager.player = (Player)entity;
            }
        }
    }

    public static void render(){
        for (Entity entity:entities){
            if (player != entity){
                if(player.getHitbox().overlaps(entity.getHitbox())){
                    float[] playerScreenCoord = player.getScreenCoord();
                    float[] entityScreenCoord = entity.getScreenCoord();
                    entity.dispose();
                    /*
                    if (playerScreenCoord[0] < entityScreenCoord[0]){
                        //player.setCoord(playerScreenCoord[0]-(player.getSpeed() * Gdx.graphics.getDeltaTime()), playerScreenCoord[1]);
                    }else {
                        //player.setCoord(playerScreenCoord[0]+(player.getSpeed() * Gdx.graphics.getDeltaTime()), playerScreenCoord[1]);
                    }

                     */
                }
            }
        }
    }
}
