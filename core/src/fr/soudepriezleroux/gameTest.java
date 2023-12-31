package fr.soudepriezleroux;


import fr.soudepriezleroux.entity.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.UUID;

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

    /**
     * Liste des entités que Pac-Man peut manger (sert pour la création des entités ajoutant des points au score du joueur
     */
    private static String listeMangeable[] = {"cerise", "fraise", "orange", "pomme", "melon", "galaxian", "cloche", "clef", "pacGum", "ghost"};

    private static ArrayList<UUID> lstUUIDMangeable = new ArrayList<>();

    private static ArrayList<Integer> lstPtsMangeable = new ArrayList<>();

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

            if (Player.isIsInvincible()){
                return true;
            }

            throw new Exception();
        }catch (Exception e){
            return false;
        }
    }

    /**
     * Effectue les test et les affiches au propre
     */
    public static void hubTest(){
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

