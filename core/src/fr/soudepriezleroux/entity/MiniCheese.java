package fr.soudepriezleroux.entity;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.TimeUtils;

import java.awt.*;
import java.util.UUID;

public class MiniCheese extends Entity{
    private int points;

    public MiniCheese(String prefix, int pointsEarned, boolean isAnimated, int nbrFrame, float width, float height,
                      float x, float y, float textureSizeX, float textureSizeY, Facing facing){
        super(prefix, isAnimated, nbrFrame, width, height, x, y, textureSizeX, textureSizeY, facing);

        points = pointsEarned;
    }

    public int getPoints() {
        return points;
    }
}
