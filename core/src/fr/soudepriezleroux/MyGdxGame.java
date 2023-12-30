package fr.soudepriezleroux;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.utils.ScreenUtils;
import fr.soudepriezleroux.entity.EntityManager;
import fr.soudepriezleroux.entity.Facing;
import fr.soudepriezleroux.entity.ghost.Blinky;
import fr.soudepriezleroux.entity.ghost.Ghost;
import fr.soudepriezleroux.map.MatriceMap;

import java.util.UUID;
import fr.soudepriezleroux.entity.Player;
import fr.soudepriezleroux.map.MapManager;

public class MyGdxGame extends ApplicationAdapter {
	//Creation de la camera qui permet de voir le jeu
	private OrthographicCamera camera;

	@Override
	public void create () {
		//Initialisation de la camera
		camera = new OrthographicCamera();
		camera.setToOrtho(false, 810,930);
		camera.update(true);

		//Initialisation des manager
		EntityManager.init(camera);
		MapManager.init();

		//Creation du player au coordonnées X Y de la fenetre
		EntityManager.addEntity(new Player("player",false,2,16,16,405,405,16,16, Facing.UP));
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
		//Update de la camera
		camera.update(true);
	}

	//Liberation des ressources
	@Override
	public void dispose () {
		EntityManager.dispose();
	}
}
