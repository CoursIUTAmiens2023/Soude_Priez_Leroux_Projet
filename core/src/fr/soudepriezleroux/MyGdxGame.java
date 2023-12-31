package fr.soudepriezleroux;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.utils.ScreenUtils;
import fr.soudepriezleroux.entity.EntityManager;
import fr.soudepriezleroux.entity.Facing;
import fr.soudepriezleroux.map.MatriceMap;
import fr.soudepriezleroux.entity.*;

import java.util.List;
import java.util.UUID;
import fr.soudepriezleroux.entity.Player;
import fr.soudepriezleroux.map.MapManager;

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
		Map.init(camera);
		EntityManager.init(camera);
		MapManager.init();

		//Creation du player au coordonnées X Y de la fenetre
		EntityManager.addEntity(new Player("player",false,2,16,16,405,405,16,16, Facing.UP));
		bgMusic = Gdx.audio.newMusic(Gdx.files.internal("bgMusic.mp3"));
		bgMusic.setLooping(true);
		bgMusic.play();


		UUID PacGum = EntityManager.addEntity(new PacGum("pacGum", 50, false, 1, 32,32, 700, 300, 32,32, Facing.LEFT));
		UUID cerise = EntityManager.addEntity(new Fruits("cerise", false, 1, 32,32, 762, 599, 32,32, Facing.DOWN));
		UUID fraise = EntityManager.addEntity(new Fruits("fraise", false, 1, 32,32, 862, 699, 32,32, Facing.DOWN));
		UUID orange = EntityManager.addEntity(new Fruits("orange", false, 1, 32,32, 962, 899, 32,32, Facing.DOWN));
		UUID pomme = EntityManager.addEntity(new Fruits("pomme", false, 1, 32,32, 662, 499, 32,32, Facing.DOWN));

		UUID player = EntityManager.addEntity(new Player("player",true,2,32,32,60,60,32,32, Facing.LEFT));

		new Map("map", 577, 0, 859, 950, 1369, 1513);

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
