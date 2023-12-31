package fr.soudepriezleroux.entity;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import fr.soudepriezleroux.entity.ghost.Blinky;
import fr.soudepriezleroux.entity.ghost.Ghost;
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

    private static Player player;

    private static Blinky blinky;

    private static ArrayList<Ghost> ghosts = new ArrayList<Ghost>();

    //Initialisation les sprites avec la camera
    public static void init(Camera camera){
        entities = new ArrayList<>();
        spriteBatch = new SpriteBatch();

        spriteBatch.setProjectionMatrix(camera.combined);
    }

    public static void setPlayer(UUID uuid){
        entities.forEach(entity -> {
            if (entity.getUuid()  == uuid){
                player = (Player) entity;
            }
        });
    }

    public static void setBlinky(UUID uuid){
        entities.forEach(entity -> {
            if (entity.getUuid()  == uuid){
                blinky = (Blinky) entity;
            }
        });
    }

    public static void addGhost(UUID uuid){
        entities.forEach(entity -> {
            if (entity.getUuid()  == uuid){
                ghosts.add((Ghost) entity);
            }
        });
    }

    public static int[] getPlayerPos(){
        int pixelX = (int)player.getScreenCoord()[0]+ player.getCentreX();
        int pixelY = 930-((int)player.getScreenCoord()[1]+ player.getCentreY());

        // Taille d'une zone matrice
        int zoneSize = 30;

        // Identification de la zone actuelle
        int zoneX = pixelX / zoneSize;
        int zoneY = pixelY / zoneSize;

        return new int[]{zoneY, zoneX};
    }

    public static int[] getBlinkyPos(){
        int pixelX = (int)blinky.getScreenCoord()[0]+ blinky.getCentreX();
        int pixelY = 930-((int)blinky.getScreenCoord()[1]+ blinky.getCentreY());

        // Taille d'une zone matrice
        int zoneSize = 30;

        // Identification de la zone actuelle
        int zoneX = pixelX / zoneSize;
        int zoneY = pixelY / zoneSize;

        return new int[]{zoneY, zoneX};
    }

    public static Facing getPlayerFacing(){
        return player.getFacing();
    }

    public static int getPointsMiam(){
        return player.getPointsMiam();
    }

    public static ArrayList<Ghost> getGhosts() {
        return ghosts;
    }

    public static void setGhosts(ArrayList<Ghost> ghosts) {
        EntityManager.ghosts = ghosts;
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

    public static ArrayList<Entity> getEntities() {
        return entities;
    }
    //Liberation des ressources de toute les entités et du spritebatch
    public static void dispose(){
        removeAll();
        spriteBatch.dispose();
    }

}
