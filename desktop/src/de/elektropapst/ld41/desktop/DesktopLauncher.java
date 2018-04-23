package de.elektropapst.ld41.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import de.elektropapst.ld41.LDGame;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.title = "Ludum Dare 41 - Combine 2 Incompatible Genres";
		config.width = 1280;
		config.height = 768;
		config.resizable = false;
		config.samples = 8;
		new LwjglApplication(new LDGame(), config);
	}
}
