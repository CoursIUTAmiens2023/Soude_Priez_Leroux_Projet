package fr.soudepriezleroux.entity;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class EntityManager {
    //Liste de toutes les entités de notre jeu
    private static ArrayList<Entity> entities = new ArrayList<>();
    //Liste de touts leurs sprites
    @Getter
    private static SpriteBatch spriteBatch = new SpriteBatch();

    //Initialisation les sprites avec la camera
    public static void init(Camera camera){
        entities = new ArrayList<>();
        spriteBatch = new SpriteBatch();
        spriteBatch.setProjectionMatrix(camera.combined);
    }

    //Ajout d'une entité a la liste
    public static UUID addEntity(Entity entity){
        entities.add(entity);
        return (entity).getUuid();
    }

    //Ajout de plusieurs entité a la liste
    public static List<UUID> addEntities(List<Entity> entities){
        List<UUID> NewUuids = new ArrayList<>();
        for (Entity entity:entities) {
            NewUuids.add(addEntity(entity));
        }
        return NewUuids;
    }

    //Methode de rendu pour faire le rendu de chaque entité du jeu
    public static void render(){
        spriteBatch.begin();
        for (Entity entity:entities) {
            entity.render(spriteBatch);
        }
        spriteBatch.end();
    }

    //Suppression d'une entité de la liste
    //Ainsi que liberation des ressources
    public static void removeEntity(UUID uuid){
        entities.forEach(entity -> {
            if (entity.getUuid()  == uuid){
                entity.dispose();
            }
        });
        entities.removeIf(entity -> uuid == entity.getUuid());
    }
    //Suppression de toutes les entités de la liste
    public static void removeAll(){
        for (Entity entity:entities) {
            EntityManager.removeEntity(entity.getUuid());
        }
    }

    public static SpriteBatch getBatch(){
        return spriteBatch;
    }

    /**
     * Cherche une entité dans la liste des entités du jeu
     * @param uuid L'indentifiant de l'entité a chercher
     * @return Une entity si elle existe | Null si elle n'existe pas
     */
    public static Entity getEntity(UUID uuid){
        for (Entity entity:entities){
            if (entity.getUuid() == uuid){
                return entity;
            }
        }
        return null;
    }

    public static ArrayList<Entity> getEntities() {
        return entities;
    }
    //Liberation des ressources de toute les entités et du spritebatch
    public static void dispose(){
        removeAll();
        spriteBatch.dispose();
    }

}
