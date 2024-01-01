package fr.soudepriezleroux.entity.ghost;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import fr.soudepriezleroux.entity.EntityManager;
import fr.soudepriezleroux.entity.Facing;

import java.util.Arrays;

public class Pinky extends Ghost {

    private final int[] scatterTarget;
    private int[] chaseTarget;

    public Pinky(String prefix, boolean isAnimated, int nbrFrame, float width, float height, float x, float y, float textureSizeX, float textureSizeY, Facing facing, int[] pos, int direction, int speed, int[][] matrice) {
        super(prefix, isAnimated, nbrFrame, width, height, x, y, textureSizeX, textureSizeY, facing, pos, direction, speed, matrice);
        scatterTarget = new int[]{-3, 2};
    }

    // Mise à jour de la case cible en mode chase
    public void updateChaseTarget(int[] playerPos, Facing playerFacing) {
        chaseTarget = new int[]{playerPos[0], playerPos[1]};
        switch (playerFacing) {
            case UP:
                chaseTarget[0] = chaseTarget[0] - 4;
                break;
            case DOWN:
                chaseTarget[0] = chaseTarget[0] + 4;
                break;
            case LEFT:
                chaseTarget[1] = chaseTarget[1] - 4;
                break;
            case RIGHT:
                chaseTarget[1] = chaseTarget[1] + 4;
                break;
        }
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
                        updateChaseTarget(EntityManager.getPlayerPos(), EntityManager.getPlayerFacing());
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
                goChaseDirectionOut();
                setOut(false);
            }
            move();
        }
        super.render(spriteBatch);
    }
}
