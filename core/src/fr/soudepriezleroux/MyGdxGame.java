package fr.soudepriezleroux;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.utils.ScreenUtils;
import fr.soudepriezleroux.entity.Entity;
import fr.soudepriezleroux.entity.EntityManager;
import fr.soudepriezleroux.entity.Facing;
import fr.soudepriezleroux.entity.ghost.Blinky;
import fr.soudepriezleroux.entity.ghost.Ghost;
import fr.soudepriezleroux.map.MatriceMap;

import java.util.UUID;

import static fr.soudepriezleroux.map.MatriceMap.getMatrice;

public class MyGdxGame extends ApplicationAdapter {
	private OrthographicCamera camera;

	@Override
	public void create () {

		camera = new OrthographicCamera();
		camera.setToOrtho(false, 1980, 980);

		EntityManager.init(camera);

		MatriceMap.init();

		UUID entity1 = EntityManager.addEntity(new Entity("player",true,2,64,64,60,60,64,64, Facing.LEFT));
		UUID entity2 = EntityManager.addEntity(new Entity("player",true,2,64,64,60,180,64,64, Facing.UP));
		UUID monGhost = EntityManager.addEntity(new Blinky("ghost", false, 1, 64, 64, 40, 230, 64, 64, Facing.UP,
				new int[] {2, 2}, 0, 60, getMatrice()));

	}

	@Override
	public void render () {
		ScreenUtils.clear(0, 0, 0.01f, 1);

		camera.update();

		EntityManager.render();

	}
	
	@Override
	public void dispose () {
		EntityManager.dispose();
	}
}
