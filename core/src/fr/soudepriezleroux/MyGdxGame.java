package fr.soudepriezleroux;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.utils.ScreenUtils;
import fr.soudepriezleroux.entity.*;

import java.util.List;
import java.util.UUID;

public class MyGdxGame extends ApplicationAdapter {
	private OrthographicCamera camera;
	private Music bgMusic;

	@Override
	public void create () {

		camera = new OrthographicCamera();
		camera.setToOrtho(false, 1980, 980);

		Map.init(camera);
		EntityManager.init(camera);

		bgMusic = Gdx.audio.newMusic(Gdx.files.internal("bgMusic.mp3"));
		bgMusic.setLooping(true);
		bgMusic.play();


		UUID PacGum = EntityManager.addEntity(new PacGum("pacGum", 500, false, 1, 32,32, 700, 300, 32,32, Facing.LEFT));
		UUID fruit = EntityManager.addEntity(new Fruits("pacGum", 500, false, 1, 32,32, 762, 599, 32,32, Facing.LEFT));

		UUID player = EntityManager.addEntity(new Player("player",true,2,32,32,60,60,32,32, Facing.LEFT));

		new Map("map", 577, 0, 859, 950, 1369, 1513);

		CollisionManager.init(EntityManager.getEntities(), player);
	}

	@Override
	public void render () {
		//Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT |
		// 											(Gdx.graphics.getBufferFormat().coverageSampling?GL20.GL_COVERAGE_BUFFER_BIT_NV:0));
										//	Anti aliasing
		//ScreenUtils.clear(0, 0, 0.01f, 1);
		
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
