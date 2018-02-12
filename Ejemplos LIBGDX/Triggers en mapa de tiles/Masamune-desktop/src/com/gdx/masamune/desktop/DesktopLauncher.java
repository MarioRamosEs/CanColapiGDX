package com.gdx.masamune.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.badlogic.gdx.tools.texturepacker.TexturePacker;
import com.badlogic.gdx.tools.texturepacker.TexturePacker.Settings;
import com.gdx.masamune.Masamune;

public class DesktopLauncher {
	public static void main(String[] arg) {
//		 Settings settings = new Settings();
//		 settings.combineSubdirectories = true;
//		 TexturePacker.process(settings, "../Masamune-core/assets/characters", "../Masamune-core/assets/packedGraphics", "characters");
//		 TexturePacker.process(settings, "../Masamune-core/assets/effects", "../Masamune-core/assets/packedGraphics", "effects");
//		 TexturePacker.process(settings, "../Masamune-core/assets/tilesets", "../Masamune-core/assets/packedGraphics", "tilesets");
//		 settings.duplicatePadding = true;

		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		new LwjglApplication(new Masamune(), config);
	}
}
