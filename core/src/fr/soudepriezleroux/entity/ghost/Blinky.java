package fr.soudepriezleroux.entity.ghost;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import fr.soudepriezleroux.entity.EntityManager;
import fr.soudepriezleroux.entity.Facing;
import fr.soudepriezleroux.map.MatriceMap;

import java.util.Arrays;
import java.util.List;

public class Blinky extends Ghost{

    private final int[] scatterTarget;
    private int[] chaseTarget;

    public Blinky(String prefix, boolean isAnimated, int nbrFrame, float width, float height, float x, float y, float textureSizeX, float textureSizeY, Facing facing, int[] pos, int direction, int speed, int[][] matrice) {
        super(prefix, isAnimated, nbrFrame, width, height, x, y, textureSizeX, textureSizeY, facing, pos, direction, speed, matrice);
        scatterTarget = new int[]{-3, matrice[0].length - 3};
    }

    // Mise à jour de la case cible en mode chase
    public void updateChaseTarget(int[] playerPos){
        chaseTarget = new int[]{playerPos[0], playerPos[1]};
    }

    @Override
    public void render(SpriteBatch spriteBatch) {
        if (isReady()) {
            int mode = getMode();
            int lastMode = getLastMode();
            if (lastMode != mode && (lastMode == 0 || lastMode == 1)) demiTour();

            checkPos();
            if (isIn() && isOut()) {
                switch (mode) {
                    case 0:
                        updateChaseTarget(/*getPlayerPos()*/new int[]{0, 0});
                        goChaseDirection(chaseTarget);
                        break;
                    case 1:
                        goChaseDirection(scatterTarget);
                        break;
                    case 2:
                        goRandomDirection();
                        break;
                }
                setOut(false);
            }

            move();
            setLastMode(mode);
        } else {
            checkPos();
            int[] pos = getPos();
            if (pos[0] == 11 && pos[1] == 13) setReady(true);
            else if (isIn() && isOut()) {
                goChaseDirection(new int[]{11, 13});
                setOut(false);
            }
            move();
        }
        super.render(spriteBatch);
    }
}

