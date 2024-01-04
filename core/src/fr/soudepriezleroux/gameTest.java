package fr.soudepriezleroux;


import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.OrthographicCamera;
import fr.soudepriezleroux.entity.*;
import fr.soudepriezleroux.entity.ghost.Blinky;
import fr.soudepriezleroux.entity.ghost.Ghost;
import fr.soudepriezleroux.entity.ghost.Pinky;
import fr.soudepriezleroux.map.MapManager;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.UUID;

import static fr.soudepriezleroux.map.MatriceMap.getMatrice;

public class gameTest {
    /**
     * Embellit l'affichage lors des tests
     */
    private static String separateur = "------------------------------------------------";

    /**
     * String du test du pac-gum
     */
    private static String pacGumString = "Initialisation d'un Pac-Gum : ";

    /**
     * String de test du player
     */
    private static String playerString = "Initialisation d'un Player : ";

    /**
     * String de test du déplacement du joueur
     */
    private static String mooveString = "Deplacement Player : ";

    /**
     * String de test du score
     */
    private static String scoreString = "Ajout de points : ";

    /**
     * String du test d'invincibilité du joueur
     */
    private static String invicibleString = "Test Invincibilite Pac-Man : ";

    private static String playerEatGhostString = "Test ingestion fantome : ";

    private static String playerEatGhostsString = "Test ingestion plusieurs fantomes (combo) : ";
    private static String metGhostString = "Test rencontre Player-Ghost : ";

    private static String wallWalkedString = "Test arret au niveau d'un mur : ";

    /**
     * Liste des entités que Pac-Man peut manger (sert pour la création des entités ajoutant des points au score du joueur
     */
    private static String listeMangeable[] = {"cerise", "fraise", "orange", "pomme", "melon", "galaxian", "cloche", "clef", "pacGum", "ghost"};

    private static ArrayList<UUID> lstUUIDMangeable = new ArrayList<>();

    private static ArrayList<Integer> lstPtsMangeable = new ArrayList<>();

    /**
     * Savoir si le joueur a utilisé ses 3 vies
     */
    private static boolean isLost = false;

    private static boolean initGame(){
        try {
            OrthographicCamera camera;
            Music bgMusic;

            camera = new OrthographicCamera();
            camera.setToOrtho(false, 810,930);
            camera.update(true);

            //Initialisation des manager
            EntityManager.init(camera);
            MapManager.init();

            return true;
        }catch (Exception e){
            return false;
        }
    }

    /**
     * Crée un joueur puis regarde s'il existe dans la liste des entités
     * @return True s'il est présent | False s'il ne l'es pas
     */
    private static boolean createPlayer(){
        try {
            UUID player = EntityManager.addEntity(new Player("player",true,2,32,32,60,60,32,32, Facing.LEFT));
            if (EntityManager.getEntity(player) != null){
                return true;
            }

            throw new Exception();
        }catch (Exception e){
            
            return false;
        }
   }

    /**
     *
     * @param PacGum
     * @return
     */
    private static boolean updateScore(UUID PacGum){
        try {
            int indexEntite = lstUUIDMangeable.indexOf(PacGum);
            UUID player = EntityManager.addEntity(new Player("player",true,2,32,32,60,60,32,32, Facing.LEFT));
            Player joueur = ((Player) EntityManager.getEntity(player));
            Entity miammiam = EntityManager.getEntity(PacGum);
            joueur.eatCheese(miammiam);

            if (joueur.getPoints() == lstPtsMangeable.get(indexEntite)){
                return true;
            }else {
                throw new Exception();
            }
        }catch (Exception e){
            return false;
        }
    }

    /**
     * Crée un PacGum et vérifie s'il est bien dans la liste des entités
     * @return True s'il est dans la liste | False s'il ne l'es pas
     */
    private static boolean createPacGum(){
        try{
            UUID PacGum = EntityManager.addEntity(new PacGum("pacGum", 50, false, 1, 32,32, 700, 300, 32,32, Facing.LEFT));
            if(EntityManager.getEntity(PacGum) != null){
                
                return true;
            }
            throw new Exception();
        }catch (Exception e){
            return false;
        }
    }

    /**
     * On invoque la fonction de modification de joueur
     * @return True si les coordonnées ont été modifiées | False si elles ne l'ont pas été
     */
    private static boolean moovePLayer(){
        try {
            Player joueur = (Player) EntityManager.getEntity(
                    EntityManager.addEntity(
                            new Player("player",true,2,32,32,60,60,32,32, Facing.LEFT)));

            String coord = Arrays.toString(joueur.getScreenCoord());
            joueur.setCoord( 100, 100);
            String newCoord = Arrays.toString(joueur.getScreenCoord());

            if (!newCoord.equals(coord)){
                return true;
            }
            throw new Exception();
        }catch (Exception e){
            return false;
        }
    }

    /**
     * Test la rencontre entre Player et un ghost
     * @return True si le joueur et les fantomes reviennet à l'emplacement de départ et si
     * une vie a bien été soustraite | False si une des conditions n'est pas remplie
     */
    private static boolean metGhost(){
       try {
           MapManager.init();

           int[] coord = {300, 400};
           Player joueur = (Player) EntityManager.getEntity(
                   EntityManager.addEntity(
                           new Player("player", true, 2, 32, 32, coord[0], coord[1], 32, 32, Facing.LEFT)));
           Blinky monBlinky = (Blinky) EntityManager.getEntity(
                   EntityManager.addEntity(new Blinky("blinky", true, 1, 24, 24, coord[0], coord[1], 24, 24, Facing.DOWN,
                           new int[]{2, 2}, -1, 100, getMatrice()))
           );

           EntityManager.addGhost(monBlinky.getUuid());
           CollisionManager.init(EntityManager.getEntities(), joueur.getUuid());
           CollisionManager.render();

           if (!Arrays.toString(joueur.getScreenCoord()).equals(Arrays.toString(joueur.getStartCoord()))) {
               throw new Exception("Repositionnement du joueur faux");
           }

           if (joueur.getLives() != 2) {
               throw new Exception("Compteur de vie faux");
           }

           for (Ghost ghost : EntityManager.getGhosts()) {
               if (!Arrays.toString(ghost.getStartCoord()).equals(Arrays.toString(ghost.getScreenCoord())) ||
                       !Arrays.toString(ghost.getStartPos()).equals(Arrays.toString(ghost.getPos()))) {
                   throw new Exception("Erreur dans le repositionnement des fantomes");
               }
           }

           return true;
       }catch (Exception e){
           System.out.println(e);
           return false;
       }
    }

    /**
     * Test de déplacement
     * @param distance La distance que parcours le joueur
     * @return True s'il a réussi a se déplacer | False s'il a rencontré un mur
     */
    private static boolean wallWalked(float distance){
        try {
            Player joueur = (Player) EntityManager.getEntity(
                    EntityManager.addEntity(
                            new Player("player",true,2,32,32,60,60,32,32, Facing.LEFT)));

            if(joueur.move(distance)){
                return true;
            }
            throw new Exception();
        }catch (Exception e){
            return false;
        }
    }

    /**
     * Test d'ingestion ghost
     * @return True si le Pac-Man est bien invinsible ET que les points du fantomes ont été ajouté | False si les conditions ne sont pas rencontrées
     */
    private static boolean playerEatGhost(){
        try {
            Player joueur = (Player) EntityManager.getEntity(
                    EntityManager.addEntity(
                            new Player("player",true,2,32,32,60,60,32,32, Facing.LEFT))
            );
            EntityManager.setPlayer(joueur.getUuid());
            PacGum pacGum = (PacGum) EntityManager.getEntity(
                    EntityManager.addEntity(
                            new PacGum("pacGum", 50, false, 1, 24,24,
                                    60, 60, 24,24, Facing.LEFT)
                    )
            );
            CollisionManager.init(EntityManager.getEntities(), joueur.getUuid());
            CollisionManager.render();

            if (!joueur.isIsInvincible()){
                throw new Exception("Le joueur n'est pas invincible");
            }

            Blinky monBlinky = (Blinky) EntityManager.getEntity(
                    EntityManager.addEntity(new Blinky("blinky", true, 1, 24, 24, 50, 50, 24, 24, Facing.DOWN,
                            new int[]{2, 2}, -1, 100, getMatrice()))
            );

            EntityManager.addGhost(monBlinky.getUuid());

            int ptsTemp = EntityManager.getPlayer().getPoints();

            joueur.setCoord(50,50);
            CollisionManager.render();

            ptsTemp = joueur.getPoints() - ptsTemp;
            if (ptsTemp != 200){
                throw new Exception("Calcule des points après ingestion fantome invalide");
            }

            return true;
        }catch (Exception e){
            System.out.println(e);
            return false;
        }
    }

    private static boolean playerEatGhosts(){
        try {
            Player joueur = (Player) EntityManager.getEntity(
                    EntityManager.addEntity(
                            new Player("player",true,2,32,32,60,60,32,32, Facing.LEFT))
            );
            EntityManager.setPlayer(joueur.getUuid());
            PacGum pacGum = (PacGum) EntityManager.getEntity(
                    EntityManager.addEntity(
                            new PacGum("pacGum", 50, false, 1, 24,24,
                                    60, 60, 24,24, Facing.LEFT)
                    )
            );
            CollisionManager.init(EntityManager.getEntities(), joueur.getUuid());
            CollisionManager.render();

            if (!joueur.isIsInvincible()){
                throw new Exception("Le joueur n'est pas invincible");
            }

            Blinky monBlinky = (Blinky) EntityManager.getEntity(
                    EntityManager.addEntity(new Blinky("blinky", true, 1, 24, 24, 50, 50, 24, 24, Facing.DOWN,
                            new int[]{2, 2}, -1, 100, getMatrice()))
            );

            Pinky monPinky = (Pinky) EntityManager.getEntity(
                    EntityManager.addEntity(new Pinky("pinky", true, 1, 24, 24, 60, 60, 24, 24, Facing.DOWN,
                            new int[]{2, 2}, -1, 100, getMatrice()))
            );
            EntityManager.resetGhosts();
            EntityManager.addGhost(monBlinky.getUuid());
            EntityManager.addGhost(monPinky.getUuid());

            monPinky.setCoord(90,90);
            monBlinky.setCoord(90,90);

            int ptsTemp = EntityManager.getPlayer().getPoints();

            joueur.setCoord(90,90);

            joueur.setPoints(0);
            CollisionManager.render();
            ptsTemp = joueur.getPoints() - ptsTemp;
            if (ptsTemp < 600){
                throw new Exception("Calcule des points après ingestion fantome invalide");
            }

            return true;
        }catch (Exception e){
            System.out.println(e);
            return false;
        }
    }

    /**
     * Test invincibilité Pac-Man
     * @return True si le joueur est invincible après l'ingestion d'un Pac-Gum | False s'il ne l'est pas
     */
    private static boolean pacManIsInvincible(){
        try {
            Player joueur = (Player) EntityManager.getEntity(
                    EntityManager.addEntity(
                            new Player("player",true,2,32,32,60,60,32,32, Facing.LEFT)));
            PacGum pacGum = (PacGum) EntityManager.getEntity(
                    EntityManager.addEntity(
                            new PacGum("pacGum", 50, false, 1, 32,32, 700, 300, 32,32, Facing.LEFT)));
            joueur.eatCheese(pacGum);

            if (joueur.isIsInvincible()){
                return true;
            }
            throw new Exception();
        }catch (Exception e){
            return false;
        }
    }

    public static void setIsLost(boolean isLost) {
        gameTest.isLost = isLost;
    }

    /**
     * Effectue les test et les affiches au propre
     */
    public static void hubTest(){
        System.out.println(separateur);

        //Initialisation jeu
       System.out.println("Initialisation : " + initGame());
        System.out.println(separateur);


        //Création Pac-Gum Test
        System.out.println(pacGumString + createPacGum());
        System.out.println(separateur);



        //Création Player Test
        System.out.println(playerString + createPlayer());
        System.out.println(separateur);



        //Ajout points test
        setLstUUIDMangeable();
        for(UUID uuid:lstUUIDMangeable){
            System.out.println(scoreString + '(' +lstPtsMangeable.get(lstUUIDMangeable.indexOf(uuid)) + "pts) " + updateScore(uuid));
            System.out.println(separateur);
        }

        //Test déplacement joueur
        System.out.println(mooveString + moovePLayer());
        System.out.println(separateur);



        //Test Invincibilité joueur
        System.out.println(invicibleString + pacManIsInvincible());
        System.out.println(separateur);


        //Test metGhost
        System.out.println(metGhostString + metGhost());
        System.out.println(separateur);


        //Test rencontre mur
        if(wallWalked((float) 1)){
            System.out.println(wallWalkedString + "true" + " (true Attendu)");
        }else {
            System.out.println(wallWalkedString + "false " + "(true Attendu)");
        }
        if(!wallWalked((float) 110)){
            System.out.println(wallWalkedString + "true" + " (false Attendu)");
        }else {
            System.out.println(wallWalkedString + "false " + "(false Attendu)");
        }
        System.out.println(separateur);


        //Test Pac-Man mange un fantome
        System.out.println(playerEatGhostString + playerEatGhost());
        System.out.println(separateur);

        //Test Pac-Man mange 2 fantomes
        System.out.println(playerEatGhostsString + playerEatGhosts());
        System.out.println(separateur);


    }

    /**
     * Initialise les données pour l'ajout des points
     */
    private static void setLstUUIDMangeable() {
        for (String entity: listeMangeable){
            if(entity.equals("pacGum")){
                lstUUIDMangeable.add(EntityManager.addEntity(new PacGum("pacGum", 50, false, 1, 32,32, 700, 300, 32,32, Facing.LEFT)));
                lstPtsMangeable.add(50);
                lstUUIDMangeable.add(EntityManager.addEntity(new PacGum("pacGum", 10, false, 1, 32,32, 700, 300, 32,32, Facing.LEFT)));
                lstPtsMangeable.add(10);
            } else if (entity.equals("ghost")) {
                //Ajout des fantomes
            }else {
                lstUUIDMangeable.add(EntityManager.addEntity(new Fruits(entity, false, 1, 32,32, 700, 300, 32,32, Facing.LEFT)));
                switch (entity){
                    case "cerise":
                        lstPtsMangeable.add(100);
                        break;
                    case "fraise":
                        lstPtsMangeable.add(300);
                        break;
                    case "orange":
                        lstPtsMangeable.add(500);
                        break;
                    case "pomme":
                        lstPtsMangeable.add(700);
                        break;
                    case "melon":
                        lstPtsMangeable.add(1000);
                        break;
                    case "galaxian":
                        lstPtsMangeable.add(2000);
                        break;
                    case "cloche":
                        lstPtsMangeable.add(3000);
                        break;
                    case "clef":
                        lstPtsMangeable.add(5000);
                        break;
                }
            }
        }
    }
}

