package fr.soudepriezleroux.entity.ghost;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import fr.soudepriezleroux.entity.Entity;
import fr.soudepriezleroux.entity.Facing;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public abstract class Ghost extends Entity {

    private int[] pos;      // coordonnées matrice
    private int[] startPos; //Coordonnée au au lancement de l'appli

    private float[] startCoord;
    private int direction;      // HAUT 0 DROITE 1 BAS 2 GAUCHE 3
    private int baseSpeed;
    private int speed;
    private int[][] matrice;
    private int mode;       // 0 = chase, 1 = scatter, 2 = frightened
    private int lastMode;
    private boolean in;
    private boolean out;
    private boolean ready;
    private int centreX;
    private int centreY;

    public Ghost(String prefix, boolean isAnimated, int nbrFrame, float width, float height, float x, float y, float textureSizeX, float textureSizeY, Facing facing, int[] pos, int direction, int speed, int[][] matrice) {
        super(prefix, isAnimated, nbrFrame, width, height, x, y, textureSizeX, textureSizeY, facing);
        this.pos = pos;
        this.startPos = pos;
        this.startCoord = new float[]{x,y};
        this.direction = direction;
        this.baseSpeed = speed;
        this.speed = speed;
        this.matrice = matrice;
        this.mode = 0;
        this.lastMode = 0;
        this.in = false;
        this.out = true;
        this.ready = false;
        this.centreX = (int)width/2;
        this.centreY = (int)height/2;
    }

    public int[] getPos() {
        return pos;
    }

    public int[] getStartPos() {
        return startPos;
    }

    public void setPos(int[] pos) {
        this.pos = pos;
    }

    public float[] getStartCoord() {
        return startCoord;
    }

    public int getDirection() {
        return direction;
    }

    public void setDirection(int direction) {
        this.direction = direction;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public int getMode() {
        return mode;
    }

    public void setMode(int mode) {
        this.mode = mode;
    }

    public int getLastMode() {
        return lastMode;
    }

    public void setLastMode(int lastMode) {
        this.lastMode = lastMode;
    }

    public void setChase(){
        mode = 0;
        speed = baseSpeed;
    }

    public void setScatter(){
        mode = 1;
        speed = baseSpeed;
    }

    public void setFrightened(){
        mode = 2;
        speed = 2*baseSpeed/3;
    }

    public boolean isIn() {
        return in;
    }

    public void setIn(boolean in) {
        this.in = in;
    }

    public boolean isOut() {
        return out;
    }

    public void setOut(boolean out) {
        this.out = out;
    }

    public boolean isReady() {
        return ready;
    }

    public void setReady(boolean ready) {
        this.ready = ready;
    }

    public int getCentreX() {
        return centreX;
    }

    public void setCentreX(int centreX) {
        this.centreX = centreX;
    }

    public int getCentreY() {
        return centreY;
    }

    public void setCentreY(int centreY) {
        this.centreY = centreY;
    }

    // Liste des directions valides pour le ghost
    public ArrayList<Integer> getValidDirections(){
        // HAUT 0 DROITE 1 BAS 2 GAUCHE 3

        Set<Integer> exclusions = new HashSet<>();
        exclusions.add(4);
        exclusions.add(5);

        ArrayList<Integer> validDirections = new ArrayList<>();
        if (!exclusions.contains(matrice[pos[0]-1][pos[1]]) && direction != 2) validDirections.add(0);
        if (!exclusions.contains(matrice[pos[0]][pos[1]+1]) && direction != 3) validDirections.add(1);
        if (!exclusions.contains(matrice[pos[0]+1][pos[1]]) && direction != 0) validDirections.add(2);
        if (!exclusions.contains(matrice[pos[0]][pos[1]-1]) && direction != 1) validDirections.add(3);
        return validDirections;
    }

    public ArrayList<Integer> getValidDirectionsOut(){
        // HAUT 0 DROITE 1 BAS 2 GAUCHE 3

        Set<Integer> exclusions = new HashSet<>();
        exclusions.add(4);

        ArrayList<Integer> validDirections = new ArrayList<>();
        if (!exclusions.contains(matrice[pos[0]-1][pos[1]]) && direction != 2) validDirections.add(0);
        if (!exclusions.contains(matrice[pos[0]][pos[1]+1]) && direction != 3) validDirections.add(1);
        if (!exclusions.contains(matrice[pos[0]+1][pos[1]]) && direction != 0) validDirections.add(2);
        if (!exclusions.contains(matrice[pos[0]][pos[1]-1]) && direction != 1) validDirections.add(3);
        return validDirections;
    }

    // Détermine une direction aléatoire pour le ghost
    public void goRandomDirection() {
        Random rand = new Random();
        ArrayList<Integer> validDirections = getValidDirections();
        direction = validDirections.get(rand.nextInt(validDirections.size()));
    }

    // Détermine la direction valide dont la case est la plus proche de la cible
    public void goChaseDirection(int[] target) {
        ArrayList<Integer> validDirections = getValidDirections();

        double minDistance = Double.MAX_VALUE;
        int bestDirection = -1;

        for (int direction : validDirections) {
            int[] testPos = getTestPos(direction);
            double distance = getDistance(testPos, target);

            if (distance < minDistance) {
                minDistance = distance;
                bestDirection = direction;
            }
        }

        if (bestDirection != -1) {
            direction = bestDirection;
        }else{
            demiTour();
        }
    }

    public void goChaseDirectionOut() {
        int[] target = new int[]{11, 13};
        ArrayList<Integer> validDirections = getValidDirectionsOut();

        double minDistance = Double.MAX_VALUE;
        int bestDirection = -1;

        for (int direction : validDirections) {
            int[] testPos = getTestPos(direction);
            double distance = getDistance(testPos, target);

            if (distance < minDistance) {
                minDistance = distance;
                bestDirection = direction;
            }
        }

        if (bestDirection != -1) {
            direction = bestDirection;
        }
    }

    // Fonction rapide pour tester une nouvelle position possible
    private int[] getTestPos(int direction) {
        int[] testPos = new int[]{pos[0], pos[1]};

        switch (direction) {
            case 0: // HAUT
                testPos[0]--;
                break;
            case 1: // DROITE
                testPos[1]++;
                break;
            case 2: // BAS
                testPos[0]++;
                break;
            case 3: // GAUCHE
                testPos[1]--;
                break;
        }

        return testPos;
    }

    // Fonction de distance entre 2 points
    private double getDistance(int[] pos1, int[] pos2) {
        double distance = Math.sqrt(Math.pow(pos1[0] - pos2[0], 2) + Math.pow(pos1[1] - pos2[1], 2));
        return distance;
    }

    // Adopte une direction pour faire demi tour si possible
    public void demiTour() {
        int directionInv = (direction + 2) % 4; // Direction inverse

        int[] invPos = getTestPos(directionInv);

        // Vérifie si la case de demi tour est valide
        if (invPos[0] >= 0 && invPos[0] <= 30 && invPos[1] >= 0 && invPos[1] <= 26 ) {
            if (matrice[invPos[0]][invPos[1]] != 4 && matrice[invPos[0]][invPos[1]] != 5) {
                direction = directionInv;
            }
        }
    }

    // Déplacement en fonction de la direction actuelle
    public void move(){
        switch (direction){
            case 0: // HAUT
                this.screenCoord[1] += speed * Gdx.graphics.getDeltaTime();
                break;
            case 1: // DROITE
                this.screenCoord[0] += speed * Gdx.graphics.getDeltaTime();
                break;
            case 2: // BAS
                this.screenCoord[1] -= speed * Gdx.graphics.getDeltaTime();
                break;
            case 3: // GAUCHE
                this.screenCoord[0] -= speed * Gdx.graphics.getDeltaTime();
                break;
        }
    }

    // Vérifier si les coordonnées actuelles correspondent à un checkpoint de coordonnées matrice
    public void checkPos() {
        int pixelX = (int)screenCoord[0]+centreX;
        int pixelY = 930-((int)screenCoord[1]+centreY); // les Y partent du bas de les coordonnées pixel

        //System.out.println("PixelX : " + pixelX + " | PixelY : " + pixelY);

        // Taille d'une zone matrice
        int zoneSize = 30;
        // Taille du checkpoint de la zone
        int checkpointSize = 4;

        // Identification de la zone actuelle
        int zoneX = pixelX / zoneSize;
        int zoneY = pixelY / zoneSize;

        //System.out.println("ZoneX : " + zoneX + " | ZoneY : " + zoneY);

        // Coordonnées du checkpoint de la zone
        int checkpointX = zoneX * zoneSize + (zoneSize - checkpointSize) / 2;
        int checkpointY = zoneY * zoneSize + (zoneSize - checkpointSize) / 2;

        //System.out.println("CheckpointX : " + checkpointX + " | CheckpointY : " + checkpointY);;
        //System.out.println("CheckpointX : " + (checkpointX+checkpointSize) + " | CheckpointY : " + (checkpointY+checkpointSize));;

        // Vérifier si les coordonnées se trouvent dans le checkpoint
        if (pixelX >= checkpointX && pixelX < checkpointX + checkpointSize &&
                pixelY >= checkpointY && pixelY < checkpointY + checkpointSize) {
            in = true;
            pos = new int[]{zoneY, zoneX}; // Coordonnées du checkpoint (inversion des X et Y)
            //System.out.println("Checkpoint");
        } else {
            in = false;
            out = true;
            pos = new int[]{-1, -1}; // Aucun checkpoint trouvé
            //System.out.println("No checkpoint");
        }
    }

    @Override
    public void render(SpriteBatch spriteBatch) {
        super.render(spriteBatch);
    }
}