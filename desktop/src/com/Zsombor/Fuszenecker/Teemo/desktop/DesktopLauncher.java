package com.Zsombor.Fuszenecker.Teemo.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.Zsombor.Fuszenecker.Teemo.MyGdxGame;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
        config.title = "Temmo Runner";
        config.width = 1280;
        config.height = 630;
		new LwjglApplication(new MyGdxGame(), config);
	}
}
