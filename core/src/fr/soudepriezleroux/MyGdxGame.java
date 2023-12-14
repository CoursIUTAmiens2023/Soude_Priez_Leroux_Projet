package fr.soudepriezleroux;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.utils.ScreenUtils;
import fr.soudepriezleroux.entity.*;

import java.util.List;
import java.util.UUID;

public class MyGdxGame extends ApplicationAdapter {
	private OrthographicCamera camera;

	@Override
	public void create () {

		camera = new OrthographicCamera();
		camera.setToOrtho(false, 1980, 980);

		Map.init(camera);
		EntityManager.init(camera);

		UUID entity1 = EntityManager.addEntity(new Entity("player",true,2,64,64,60,60,64,64, Facing.LEFT));
		UUID entity2 = EntityManager.addEntity(new Entity("player",true,2,64,64,60,180,64,64, Facing.UP));



		UUID PacGum = EntityManager.addEntity(new PacGum("pacGum", 500, false, 1, 32,32, 500, 300, 32,32, Facing.LEFT));

		UUID player = EntityManager.addEntity(new Player("player",true,2,32,32,60,60,32,32, Facing.LEFT));

		new Map("map", 577, 0, 859, 950, 1369, 1513);

		CollisionManager.init(EntityManager.getEntities(), player);
	}

	@Override
	public void render () {
		ScreenUtils.clear(0, 0, 0.01f, 1);
		
		camera.update();

		Map.render();
		EntityManager.render();
		CollisionManager.render();

	}
	
	@Override
	public void dispose () {
		EntityManager.dispose();
	}
}
