package fr.soudepriezleroux;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.utils.ScreenUtils;
import fr.soudepriezleroux.entity.Entity;
import fr.soudepriezleroux.entity.Facing;

public class MyGdxGame extends ApplicationAdapter {
	private OrthographicCamera camera;
	private Entity entity;
	@Override
	public void create () {
		camera = new OrthographicCamera();
		camera.setToOrtho(false, 1980, 980);

		entity = new Entity("player",true,2,64,64,990,432,64,64, Facing.LEFT);
	}

	@Override
	public void render () {
		ScreenUtils.clear(0, 0, 0.01f, 1);

		camera.update();

		entity.render(camera.combined);
	}
	
	@Override
	public void dispose () {
		entity.dispose();
	}
}
