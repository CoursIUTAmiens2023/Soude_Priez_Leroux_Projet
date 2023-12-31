package fr.soudepriezleroux.entity.ghost;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import fr.soudepriezleroux.entity.EntityManager;
import fr.soudepriezleroux.entity.Facing;

public class Clyde extends Ghost{

    private final int[] scatterTarget;
    int[] chaseTarget;

    public Clyde(String prefix, boolean isAnimated, int nbrFrame, float width, float height, float x, float y, float textureSizeX, float textureSizeY, Facing facing, int[] pos, int direction, int speed, int[][] matrice) {
        super(prefix, isAnimated, nbrFrame, width, height, x, y, textureSizeX, textureSizeY, facing, pos, direction, speed, matrice);
        scatterTarget = new int[]{matrice.length + 1, 0};
    }

    // Mise à jour de la case cible en mode chase
    public void updateChaseTarget(int[] playerPos) {
        int[] pos = this.getPos();
        // Distance entre Clyde et le joueur
        double distance = Math.sqrt(Math.pow(pos[0] - playerPos[0], 2) + Math.pow(pos[1] - playerPos[1], 2));

        // Si la distance est supérieur à 8 cibler le joueur sinon cibler la scatterTarget
        if (distance > 8) {
            chaseTarget = new int[]{playerPos[0], playerPos[1]};
        } else {
            chaseTarget = new int[]{scatterTarget[0], scatterTarget[1]};
        }
    }

    @Override
    public void render(SpriteBatch spriteBatch) {
        if (EntityManager.getPointsMiam() > 80) {
            if (isReady()) {
                int mode = getMode();
                int lastMode = getLastMode();
                if (lastMode != mode && (lastMode == 0 || lastMode == 1)) demiTour();

                checkPos();
                if (isIn() && isOut()) {
                    switch (mode) {
                        case 0:
                            updateChaseTarget(EntityManager.getPlayerPos());
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
        }
        super.render(spriteBatch);
    }
}
