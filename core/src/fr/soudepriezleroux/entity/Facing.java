package fr.soudepriezleroux.entity;

//Enum d'angle de l'orientation des sprite
public enum Facing {
    DOWN(0f),
    UP(180f),
    LEFT(270f),
    RIGHT(90f);

    private final float degree;

    Facing(float degree){
        this.degree = degree;
    }

    public float get(){
        return degree;
    }
}
