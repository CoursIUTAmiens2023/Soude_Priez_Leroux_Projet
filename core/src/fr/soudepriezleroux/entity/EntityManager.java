package fr.soudepriezleroux.entity;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class EntityManager {
    private static ArrayList<Entity> entities;
    private static SpriteBatch spriteBatch;

    public static void init(Camera camera){
        spriteBatch = new SpriteBatch();
        entities = new ArrayList<>();
        spriteBatch = new SpriteBatch();

        spriteBatch.setProjectionMatrix(camera.combined);
    }

    public static UUID addEntity(Entity entity){
        entities.add(entity);
        return (entity).getUuid();
    }

    public static List<UUID> addEntities(List<Entity> entities){
        List<UUID> NewUuids = new ArrayList<>();
        for (Entity entity:entities) {
            NewUuids.add(addEntity(entity));
        }
        return NewUuids;
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
