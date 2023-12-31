package fr.soudepriezleroux.entity;

public class Fruits extends MiniCheese{

    /**
     * Savoir si le fruit a spawn ou non
     */
    private static boolean unFruit = false;

    public Fruits(String prefix, boolean isAnimated, int nbrFrame, float width, float height, float x, float y, float textureSizeX, float textureSizeY, Facing facing) {
        super(prefix,0,  isAnimated, nbrFrame, width, height, x, y, textureSizeX, textureSizeY, facing);

        unFruit = true;
    }

    public static void resetFruit(){
        unFruit = false;
    }

    public static boolean getUnFruit() {
        return unFruit;
    }
}