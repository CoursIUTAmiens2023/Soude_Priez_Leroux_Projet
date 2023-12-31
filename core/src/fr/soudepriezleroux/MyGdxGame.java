package fr.soudepriezleroux;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.utils.ScreenUtils;
import fr.soudepriezleroux.entity.CollisionManager;
import fr.soudepriezleroux.entity.EntityManager;
import fr.soudepriezleroux.entity.Facing;
import fr.soudepriezleroux.entity.Player;
import fr.soudepriezleroux.map.MapManager;

import java.util.UUID;

public class MyGdxGame extends ApplicationAdapter {
	//Creation de la camera qui permet de voir le jeu
	private OrthographicCamera camera;
	private Music bgMusic;
	private Boolean onTest = true;

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
			//Creation du player au coordonnées X Y de la fenetre
			UUID player = EntityManager.addEntity(new Player("player",false,2,16,16,395,210,16,16, Facing.UP));
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
			CollisionManager.render();
			//Update de la camera
			camera.update(true);
		}
	}

	//Liberation des ressources
	@Override
	public void dispose () {
		EntityManager.dispose();
	}
}
