package alex.mario.game.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import alex.mario.game.MyGdxGame;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();

        config.title = "CanColapi GDX - Marpolex";
        //config.useGL20 = true;
        config.height = 720;
        config.width = 1280;
        config.resizable = false;
		new LwjglApplication(new MyGdxGame(), config);
	}
}
