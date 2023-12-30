package fr.soudepriezleroux;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import fr.soudepriezleroux.MyGdxGame;

// Please note that on macOS your application needs to be started with the -XstartOnFirstThread JVM argument
public class DesktopLauncher {
	public static void main (String[] arg) {
		Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
		config.setForegroundFPS(60);
		config.setTitle("PacMan");
		config.setResizable(false);
		config.setWindowedMode(810,930);
		config.setWindowSizeLimits(810,930,810,930);
		config.useVsync(true);
		new Lwjgl3Application(new MyGdxGame(), config);
	}
}
