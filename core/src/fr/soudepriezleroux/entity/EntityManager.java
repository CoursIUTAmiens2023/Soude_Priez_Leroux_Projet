package fr.soudepriezleroux.entity;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class EntityManager {
    private static ArrayList<Entity> entities = new ArrayList<>();
    private static SpriteBatch spriteBatch = new SpriteBatch();

    public static void init(Camera camera){
        entities = new ArrayList<>();
        spriteBatch.setProjectionMatrix(camera.combined);
    }

    public static UUID addEntity(Object entity){
        entities.add((Entity) entity);
        return ((Entity) entity).getUuid();
    }

    public static void addEntities(List<?> entities){
        for (Object entity:entities) {
            addEntity((Entity) entity);
        }
    }

    public static ArrayList<Entity> getEntities(){
        return entities;
    }

    public static void render(){
        spriteBatch.begin();
        for (Entity entity:entities) {
            entity.render(spriteBatch);
        }
        spriteBatch.end();
    }

    public static void removeEntity(UUID uuid){
        entities.forEach(entity -> entity.dispose());
        entities.removeIf(entity -> uuid == entity.getUuid());
    }

    public static void removeAll(){
        for (Entity entity:entities) {
            EntityManager.removeEntity(entity.getUuid());
        }
    }

    public static void dispose(){
        removeAll();
        spriteBatch.dispose();
    }

}
