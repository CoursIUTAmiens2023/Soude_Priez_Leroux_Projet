package fr.soudepriezleroux;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.utils.ScreenUtils;
import fr.soudepriezleroux.entity.Entity;
import fr.soudepriezleroux.entity.EntityManager;
import fr.soudepriezleroux.entity.Facing;
import fr.soudepriezleroux.map.MapManager;

public class MyGdxGame extends ApplicationAdapter {
	private OrthographicCamera camera;


	@Override
	public void create () {
		camera = new OrthographicCamera();
		camera.setToOrtho(false, 800,886);
		camera.update(true);

		EntityManager.init(camera);
		MapManager.init();

		EntityManager.addEntity(new Entity("player",true,2,64,64,60,60,64,64, Facing.LEFT));
		EntityManager.addEntity(new Entity("player",true,2,64,64,60,180,64,64, Facing.UP));
	}

	@Override
	public void render () {
		ScreenUtils.clear(0, 0, 0, 1);

		MapManager.render();

		EntityManager.render();

		camera.update(true);
	}
	
	@Override
	public void dispose () {
		EntityManager.dispose();
	}
}
