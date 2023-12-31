package fr.soudepriezleroux;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.utils.ScreenUtils;
import fr.soudepriezleroux.entity.*;
import fr.soudepriezleroux.entity.ghost.Blinky;
import fr.soudepriezleroux.map.MapManager;

import java.util.UUID;

import static fr.soudepriezleroux.map.MatriceMap.getMatrice;

public class MyGdxGame extends ApplicationAdapter {
	//Creation de la camera qui permet de voir le jeu
	private OrthographicCamera camera;
	private Music bgMusic;

	@Override
	public void create () {
		//Initialisation de la camera
		camera = new OrthographicCamera();
		camera.setToOrtho(false, 810,930);
		camera.update(true);

		//Initialisation des manager
		EntityManager.init(camera);
		MapManager.init();

		UUID monGhost = EntityManager.addEntity(new Blinky("blinky", true, 1, 24, 24, 40, 230, 24, 24, Facing.UP,
				new int[] {2, 2}, 0, 100, getMatrice()));

		//Creation du player au coordonnées X Y de la fenetre
		UUID player = EntityManager.addEntity(new Player("player",false,2,16,16,395,210,16,16, Facing.UP));
		EntityManager.setPlayer(player);
		CollisionManager.init(EntityManager.getEntities(), player);
	}

	//Methode call a chaque frame
	@Override
	public void render () {
		//Nettoyage de l'ecran avant le rendu de la prochaine frame
		ScreenUtils.clear(0, 0, 0, 1);
		//Rendu de la map
		MapManager.render();
		//Rendu de toute les entités
		EntityManager.render();
		CollisionManager.render();
		//Update de la camera
		camera.update(true);
	}

	//Liberation des ressources
	@Override
	public void dispose () {
		EntityManager.dispose();
	}
}
