package fr.soudepriezleroux.entity;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.TimeUtils;

import java.awt.*;
import java.util.UUID;

public class MiniCheese extends Entity{

    /**
     * Les points que vaut l'entité
     */
    private int points;

    public MiniCheese(String prefix,int pointsEarned, boolean isAnimated, int nbrFrame, float width, float height,
                      float x, float y, float textureSizeX, float textureSizeY, Facing facing){
        super(prefix, isAnimated, nbrFrame, width, height, x, y, textureSizeX, textureSizeY, facing);

        //  On attribu les points en fonction du fruit demandé ou set up les points si
        //  ce n'est pas un fruit qui est créé
        switch (prefix){
            case "cerise":
                points = 100;
                break;
            case "fraise":
                points = 300;
                break;
            case "orange":

                points = 500;
                break;
            case "pomme":

                points = 700;
                break;
            case "melon":
                points = 1000;
                break;
            case "galaxian":
                points = 2000;
                break;
            case "cloche":
                points = 3000;
                break;
            case "clef":
                points = 5000;
                break;
            default:
                points = pointsEarned;
                break;
        }
    }

    public int getPoints() {
        return points;
    }
}
