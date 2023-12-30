package fr.soudepriezleroux;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;
import fr.soudepriezleroux.entity.EntityManager;
import fr.soudepriezleroux.entity.Facing;
import fr.soudepriezleroux.entity.Player;
import fr.soudepriezleroux.map.MapManager;

public class MyGdxGame extends ApplicationAdapter {
	private OrthographicCamera camera;

	@Override
	public void create () {
		camera = new OrthographicCamera();
		camera.setToOrtho(false, 810,930);
		camera.update(true);

		EntityManager.init(camera);
		MapManager.init();

		EntityManager.addEntity(new Player("player",false,2,16,16,405,405,16,16, Facing.UP));
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
