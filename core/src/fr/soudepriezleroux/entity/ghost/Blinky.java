package fr.soudepriezleroux.entity.ghost;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import fr.soudepriezleroux.entity.EntityManager;
import fr.soudepriezleroux.entity.Facing;
import fr.soudepriezleroux.map.MatriceMap;

import java.util.List;

public class Blinky extends Ghost{

    public Blinky(String prefix, boolean isAnimated, int nbrFrame, float width, float height, float x, float y, float textureSizeX, float textureSizeY, Facing facing, int[] pos, int direction, int speed) {
        super(prefix, isAnimated, nbrFrame, width, height, x, y, textureSizeX, textureSizeY, facing, pos, direction, speed);
    }

    // Phase de poursuite, Blinky poursuit sa clible directement via le chemin le plus rapide
    public void chase(){
        int[] playerPos = {29, 26};//player.getPos();
        int[] pos = {1, 1};//getPos();
        List<String> path = MatriceMap.findShortestPath(pos[0], pos[1], playerPos[0], playerPos[1]);
        System.out.println(path);
    }

    @Override
    public void render(SpriteBatch spriteBatch) {
        super.render(spriteBatch);
    }
}

