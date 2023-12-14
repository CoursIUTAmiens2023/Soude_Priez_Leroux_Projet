package fr.soudepriezleroux.entity;

public enum Facing {
    DOWN(0f),
    UP(180f),
    LEFT(90f),
    RIGHT(270f);

    private final float degree;

    Facing(float degree){
        this.degree = degree;
    }

    public float get(){
        return degree;
    }
}
