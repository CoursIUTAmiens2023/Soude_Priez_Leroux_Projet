package fr.soudepriezleroux.entity.ghost;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import fr.soudepriezleroux.entity.Entity;
import fr.soudepriezleroux.entity.Facing;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Ghost extends Entity {

    private int[] pos;
    private int direction;
    private int speed;

    public Ghost(String prefix, boolean isAnimated, int nbrFrame, float width, float height, float x, float y, float textureSizeX, float textureSizeY, Facing facing, int[] pos, int direction,int speed) {
        super(prefix, isAnimated, nbrFrame, width, height, x, y, textureSizeX, textureSizeY, facing);
        this.pos = pos;
        this.direction = direction;
        this.speed = speed;
    }

    public int[] getPos() {
        return pos;
    }

    public void setPos(int[] pos) {
        this.pos = pos;
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

    public List<Integer> getValidDirections(int[][] plan){
        // HAUT 0 DROITE 1 BAS 2 GAUCHE 3
        List<Integer> validDirections = new ArrayList<>();
        if (plan[pos[0]-1][pos[1]] == 0) validDirections.add(0);
        if (plan[pos[0]][pos[1]+1] == 0) validDirections.add(1);
        if (plan[pos[0]+1][pos[1]] == 0) validDirections.add(2);
        if (plan[pos[0]][pos[1]-1] == 0) validDirections.add(3);
        return validDirections;
    }

    public int getRandomDirection(int[][] plan) {
        Random rand = new Random();
        List<Integer> validDirections = getValidDirections(plan);
        return validDirections.get(rand.nextInt(validDirections.size()));
    }

    public void moveLeft(){
        this.screenCoord[0] -= speed * Gdx.graphics.getDeltaTime();
    }

    public void moveRight(){
        this.screenCoord[0] += speed * Gdx.graphics.getDeltaTime();
    }

    public void moveUp(){
        this.screenCoord[1] -= speed * Gdx.graphics.getDeltaTime();
    }

    public void moveDown(){
        this.screenCoord[1] += speed * Gdx.graphics.getDeltaTime();
    }

    @Override
    public void render(SpriteBatch spriteBatch) {

        moveRight();
        super.render(spriteBatch);
    }
}