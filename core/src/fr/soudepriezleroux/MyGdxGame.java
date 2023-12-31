package fr.soudepriezleroux;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.utils.ScreenUtils;
import fr.soudepriezleroux.entity.CollisionManager;
import fr.soudepriezleroux.entity.EntityManager;
import fr.soudepriezleroux.entity.Facing;
import fr.soudepriezleroux.entity.Player;
import fr.soudepriezleroux.map.MapManager;
import java.util.UUID;
import fr.soudepriezleroux.entity.ghost.*;
import fr.soudepriezleroux.entity.ghost.Blinky;


import static fr.soudepriezleroux.map.MatriceMap.getMatrice;

public class MyGdxGame extends ApplicationAdapter {
	//Creation de la camera qui permet de voir le jeu
	private OrthographicCamera camera;
	private Music bgMusic;
	private Boolean onTest = false;

	@Override
	public void create () {
		//Initialisation de la camera
		camera = new OrthographicCamera();
		camera.setToOrtho(false, 810,930);
		camera.update(true);

		//Initialisation des manager
		EntityManager.init(camera);


        if (!onTest){
            MapManager.init();

            bgMusic = Gdx.audio.newMusic(Gdx.files.internal("bgMusic.mp3"));
            bgMusic.setVolume((float) 0.1);
            bgMusic.setLooping(true);
            bgMusic.play();


            UUID monBlinky = EntityManager.addEntity(new Blinky("blinky", true, 1, 24, 24, 3+30*13, 3+30*(30-11), 24, 24, Facing.DOWN,
                    new int[] {2, 2}, -1, 100, getMatrice()));
            UUID monPinky = EntityManager.addEntity(new Pinky("pinky", true, 1, 24, 24, 3+30*13, 3+30*(30-14), 24, 24, Facing.DOWN,
                    new int[] {2, 2}, -1, 100, getMatrice()));
            UUID monInky = EntityManager.addEntity(new Inky("inky", true, 1, 24, 24, 3+30*11, 3+30*(30-14), 24, 24, Facing.DOWN,
                    new int[] {2, 2}, -1, 100, getMatrice()));
            UUID monClyde = EntityManager.addEntity(new Clyde("clyde", true, 1, 24, 24, 3+30*15, 3+30*(30-14), 24, 24, Facing.DOWN,
                    new int[] {2, 2}, -1, 100, getMatrice()));

            //Creation du player au coordonnées X Y de la fenetre
            UUID player = EntityManager.addEntity(new Player("player",false,2,16,16,395,210,16,16, Facing.UP));
            EntityManager.setPlayer(player);
            EntityManager.setBlinky(monBlinky);
            EntityManager.addGhost(monBlinky);
            EntityManager.addGhost(monPinky);
            EntityManager.addGhost(monInky);
            EntityManager.addGhost(monClyde);

            CollisionManager.init(EntityManager.getEntities(), player);
        } else {
            gameTest.hubTest();
        }
	}

	//Methode call a chaque frame
	@Override
	public void render () {
		if (!onTest){
			//Nettoyage de l'ecran avant le rendu de la prochaine frame
			ScreenUtils.clear(0, 0, 0, 1);
			//Rendu de la map
			MapManager.render();

			//Rendu de toute les entités
			EntityManager.render();
			//Update de la camera
			camera.update(true);
			CollisionManager.render();
		}
	}

	//Liberation des ressources
	@Override
	public void dispose () {
		EntityManager.dispose();
	}
}
