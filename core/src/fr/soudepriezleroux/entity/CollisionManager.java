package fr.soudepriezleroux.entity;

import com.badlogic.gdx.Gdx;
import fr.soudepriezleroux.MyGdxGame;
import fr.soudepriezleroux.entity.ghost.Ghost;
import fr.soudepriezleroux.gameTest;

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
        CollisionManager.player = (Player) EntityManager.getEntity(player);

        isEntity(false);
    }

    public static void render(){
        if (!entity_check){
            for (Entity entity : entities) {
                if (player != entity) {
                    if (player.getHitbox().overlaps(entity.getHitbox())) {
                        String nameClasse = entity.getClass().getSuperclass().getSimpleName();
                        isEntity(true);
                        if (nameClasse.equals("Ghost") ) {
                            if (CollisionManager.player.isIsInvincible()){
                                player.eatGhost();
                                Ghost ghost = (Ghost) entity;
                                float[] startCoord = ghost.getStartCoord();
                                int[] startPos = ghost.getStartPos();
                                ghost.setCoord(startCoord[0], startCoord[1]);
                                ghost.setPos(startPos);
                                isEntity(false);
                            }else {
                                player.hitGhost();
                                if (player.getLives() != 0){
                                    float[] newCoord = player.getStartCoord();
                                    player.setCoord(newCoord[0], newCoord[1]);
                                    for (Ghost ghost : EntityManager.getGhosts()){
                                        int[] startPos = ghost.getStartPos();
                                        float[] startCoord = ghost.getStartCoord();
                                        ghost.setCoord(startCoord[0], startCoord[1]);
                                        ghost.setPos(startPos);
                                    }
                                    //Reset des positions des ghosts
                                }else {
                                    if (MyGdxGame.getOnTest()){
                                        gameTest.setIsLost(true);
                                    }else {
                                        //Le joueur perd, l'appication se ferme
                                        Gdx.app.exit();
                                    }
                                }
                            }
                            isEntity(false);
                        } else if(!entity.getClass().getSimpleName().equals("Player")){
                            player.eatCheese(entity);
                            if (nameClasse.equals("Fruits")){
                                Fruits.resetFruit();

                            }
                            entity.dispose();
                            isEntity(false);
                        }

                    }
                }
            }
        }
    }


    private static void isEntity(Boolean isEntity) {
        CollisionManager.entity_check = isEntity;
    }
}
