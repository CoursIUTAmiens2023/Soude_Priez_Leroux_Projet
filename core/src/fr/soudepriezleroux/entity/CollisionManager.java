package fr.soudepriezleroux.entity;

import java.util.ArrayList;
import java.util.UUID;

public class CollisionManager {
    /**
     * La liste des entités présentes dans le jeu
     */
    private static ArrayList<Entity> entities;
    /**
     * Le joueur
     */
    private static Player player;
    /**
     * Boolean permettant de ne traiter qu'une fois une entité autre que le joueur
     */
    private static Boolean entity_check;
    public static void init(ArrayList<Entity> entities, UUID player){
        CollisionManager.entities = entities;
        for (Entity entity: entities){
            if (entity.getUuid().equals(player)){
                CollisionManager.player = (Player)entity;
            }
        }

        isEntity(false);
    }

    public static void render(){
        if (!entity_check){
            for (Entity entity : entities) {
                if (player != entity) {
                    if (player.getHitbox().overlaps(entity.getHitbox())) {
                        String nameClasse = entity.getClass().getSimpleName();
                        isEntity(true);

                        if(nameClasse.equals("Ghost")) {
                            player.eatGhost();

                        }else {
                            player.eatCheese(entity);
                            if (nameClasse.equals("Fruits")){
                                Fruits.resetFruit();
                            }
                        }

                        entity.dispose();
                        isEntity(false);
                    }
                }
            }
        }
    }


    private static void isEntity(Boolean isEntity) {
        CollisionManager.entity_check = isEntity;
    }
}
