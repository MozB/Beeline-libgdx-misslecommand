package org.beelinelibgdx.misslecommand.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import org.beelinelibgdx.misslecommand.MissleCommandGame;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.width = 1920/2;
		config.height = 1080/2;
		new LwjglApplication(new MissleCommandGame(), config);
	}
}
