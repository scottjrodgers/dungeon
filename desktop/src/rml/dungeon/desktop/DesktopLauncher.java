package rml.dungeon.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import rml.dungeon.DungeonGame;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
        boolean fullscreen = false;

		if(!fullscreen){
            config.width = 1280;
            config.height = 1024;
            config.useGL30 = false;
            config.fullscreen = false;

        } else {
            config.width = 1920;
            config.height = 1200;
            config.useGL30 = false;
            config.fullscreen = true;
        }
		new LwjglApplication(new DungeonGame(), config);
	}
}
