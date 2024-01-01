package fr.soudepriezleroux.map;

import fr.soudepriezleroux.entity.*;

public class MapManager {

    //Declaration de la map unique
    static Map map;

    private static int[] posFruit;
    private static int fruitCompteur = 0;

    private static long timeSpawnFruit;

    /**
     * Temps avant apparition du fruit
     */
    private static long randomTime = 0;
    /**
     * Si un fruit va spawn ou non
     */
    private static boolean spawnFruit = false;

    //Initialisation de la dite map
    public static void init(){
        map = new Map();
        MatriceMap.init();
        for (int i = 0; i < 27; i++) {
            for (int j = 0; j < 31; j++) {
                switch (getData()[j][i]){
                    case 1:
                        EntityManager.addEntity(new MiniCheese("pacGum", 10, false, 1, 12,12, i*30+10, 910 - j*30, 12,12, Facing.LEFT));
                        break;
                    case 2:
                        EntityManager.addEntity(new PacGum("pacGum", 50, false, 1, 24,24, i*30+5, 905 - j*30, 24,24, Facing.LEFT));
                        break;
                    case 3:
                        posFruit = new int[]{i*30+5, 905 - j*30};
                        break;

                }
            }
        }
    }

    public static void spawnFuit(){
        Player joueur = EntityManager.getPlayer();
        int pointsPlayer = joueur.getPoints();
        if (fruitCompteur != 2){
            if (pointsPlayer == 70 || pointsPlayer > 170){

                if (!spawnFruit){
                    if (!Fruits.getUnFruit()){
                        timeSpawnFruit =  System.currentTimeMillis();
                        randomTime = 9 + (int)(Math.random() * ((10 - 9) + 1));
                        spawnFruit = true;
                    }
                }
            }

            if (spawnFruit){
                if (System.currentTimeMillis() - timeSpawnFruit > randomTime*1000){
                    EntityManager.addEntity(new Fruits("cerise", false, 1, 24,24, posFruit[0], posFruit[1], 24,24, Facing.DOWN));
                    spawnFruit = false;
                    fruitCompteur++;
                }
            }
        }
    }

    //Rendu de la map
    public static void render(){
        spawnFuit();
        EntityManager.getBatch().begin();
        map.render();
        EntityManager.getBatch().end();
    }

    //Matrice d'entier qui stock les informations de la map (les murs, les packgum etc)
    public static int[][] getData(){
        return new int[][]{
                {4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4},
                {4,1,1,1,1,1,1,1,1,1,1,1,1,4,1,1,1,1,1,1,1,1,1,1,1,1,4},
                {4,1,4,4,4,4,1,4,4,4,4,4,1,4,1,4,4,4,4,4,1,4,4,4,4,1,4},
                {4,2,4,4,4,4,1,4,4,4,4,4,1,4,1,4,4,4,4,4,1,4,4,4,4,2,4},
                {4,1,4,4,4,4,1,4,4,4,4,4,1,4,1,4,4,4,4,4,1,4,4,4,4,1,4},
                {4,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,4},
                {4,1,4,4,4,4,1,4,4,1,4,4,4,4,4,4,4,1,4,4,1,4,4,4,4,1,4},
                {4,1,4,4,4,4,1,4,4,1,4,4,4,4,4,4,4,1,4,4,1,4,4,4,4,1,4},
                {4,1,1,1,1,1,1,4,4,1,1,1,1,4,1,1,1,1,4,4,1,1,1,1,1,1,4},
                {4,4,4,4,4,4,1,4,4,4,4,4,0,4,0,4,4,4,4,4,1,4,4,4,4,4,4},
                {0,0,0,0,0,4,1,4,4,4,4,4,0,4,0,4,4,4,4,4,1,4,0,0,0,0,0},
                {0,0,0,0,0,4,1,4,4,0,0,0,0,0,0,0,0,0,4,4,1,4,0,0,0,0,0},
                {0,0,0,0,0,4,1,4,4,0,4,4,4,5,4,4,4,0,4,4,1,4,0,0,0,0,0},
                {4,4,4,4,4,4,1,4,4,0,4,5,5,5,5,5,4,0,4,4,1,4,4,4,4,4,4},
                {0,0,0,0,0,0,1,0,0,0,4,5,5,5,5,5,4,0,0,0,1,0,0,0,0,0,0},
                {4,4,4,4,4,4,1,4,4,0,4,5,5,5,5,5,4,0,4,4,1,4,4,4,4,4,4},
                {0,0,0,0,0,4,1,4,4,0,4,4,4,4,4,4,4,0,4,4,1,4,0,0,0,0,0},
                {0,0,0,0,0,4,1,4,4,0,0,0,0,3,0,0,0,0,4,4,1,4,0,0,0,0,0},
                {0,0,0,0,0,4,1,4,4,0,4,4,4,4,4,4,4,0,4,4,1,4,0,0,0,0,0},
                {4,4,4,4,4,4,1,4,4,0,4,4,4,4,4,4,4,0,4,4,1,4,4,4,4,4,4},
                {4,1,1,1,1,1,1,1,1,1,1,1,1,4,1,1,1,1,1,1,1,1,1,1,1,1,4},
                {4,1,4,4,4,4,1,4,4,4,4,4,1,4,1,4,4,4,4,4,1,4,4,4,4,1,4},
                {4,1,4,4,4,4,1,4,4,4,4,4,1,4,1,4,4,4,4,4,1,4,4,4,4,1,4},
                {4,2,1,1,4,4,1,1,1,1,1,1,1,0,1,1,1,1,1,1,1,4,4,1,1,2,4},
                {4,4,4,1,4,4,1,4,4,1,4,4,4,4,4,4,4,1,4,4,1,4,4,1,4,4,4},
                {4,4,4,1,4,4,1,4,4,1,4,4,4,4,4,4,4,1,4,4,1,4,4,1,4,4,4},
                {4,1,1,1,1,1,1,4,4,1,1,1,1,4,1,1,1,1,4,4,1,1,1,1,1,1,4},
                {4,1,4,4,4,4,4,4,4,4,4,4,1,4,1,4,4,4,4,4,4,4,4,4,4,1,4},
                {4,1,4,4,4,4,4,4,4,4,4,4,1,4,1,4,4,4,4,4,4,4,4,4,4,1,4},
                {4,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,4},
                {4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4}
        };
    }
}
