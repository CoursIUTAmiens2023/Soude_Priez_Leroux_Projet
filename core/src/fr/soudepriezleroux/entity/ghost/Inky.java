package fr.soudepriezleroux.entity.ghost;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import fr.soudepriezleroux.entity.EntityManager;
import fr.soudepriezleroux.entity.Facing;

import java.util.Arrays;

public class Inky extends Ghost{

    private final int[] scatterTarget;
    private int[] chaseTarget;

    public Inky(String prefix, boolean isAnimated, int nbrFrame, float width, float height, float x, float y, float textureSizeX, float textureSizeY, Facing facing, int[] pos, int direction, int speed, int[][] matrice) {
        super(prefix, isAnimated, nbrFrame, width, height, x, y, textureSizeX, textureSizeY, facing, pos, direction, speed, matrice);
        scatterTarget = new int[]{matrice.length + 1, matrice[0].length - 1};
    }

    // Mise à jour de la case cible en mode chase
    public void updateChaseTarget(int[] playerPos, Facing playerFacing, int[] blinkyPos) {
        int[] middlePos = getMiddlePos(playerPos, playerFacing);
        int[] blinkyToMiddle = getBlinkyToMiddle(blinkyPos, middlePos);
        chaseTarget = new int[]{blinkyPos[0]+2*blinkyToMiddle[0], blinkyPos[1]+2*blinkyToMiddle[1]};
    }

    // Point de symétrie du vecteur
    public int[] getMiddlePos(int[] playerPos, Facing playerFacing) {
        int[] middlePos = new int[]{playerPos[0], playerPos[1]};
        switch (playerFacing) {
            case UP:
                middlePos[0] = middlePos[0] - 2;
                break;
            case DOWN:
                middlePos[0] = middlePos[0] + 2;
                break;
            case LEFT:
                middlePos[1] = middlePos[1] - 2;
                break;
            case RIGHT:
                middlePos[1] = middlePos[1] + 2;
                break;
        }
        return middlePos;
    }

    // Calcul du vecteur pour aller de Blinky au point de symétrie
    public int[] getBlinkyToMiddle(int[] blinkyPos, int[] middlePos){
        int diffX = middlePos[0] - blinkyPos[0];
        int diffY = middlePos[1] - blinkyPos[1];
        return new int[]{diffX, diffY};
    }

    @Override
    public void render(SpriteBatch spriteBatch) {
        if (EntityManager.getPointsMiam() >= 30) {
            if (isReady()) {
                int mode = getMode();
                int lastMode = getLastMode();
                if (lastMode != mode && (lastMode == 0 || lastMode == 1)) demiTour();

                checkPos();
                if (isIn() && isOut()) {
                    switch (mode) {
                        case 0:
                            updateChaseTarget(EntityManager.getPlayerPos(), EntityManager.getPlayerFacing(), EntityManager.getBlinkyPos());
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
        }
        super.render(spriteBatch);
    }
}
