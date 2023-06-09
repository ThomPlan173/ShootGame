package com.mygdx.game;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import com.mygdx.game.DropGame;

// Please note that on macOS your application needs to be started with the -XstartOnFirstThread JVM argument
//lancement de l'application, launcher
public class DesktopLauncher {
	public static void main (String[] arg) {
		Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
		config.setForegroundFPS(60);
		config.setTitle("ShootGame");
		config.setWindowIcon("ship.PNG");
		config.setFullscreenMode(Lwjgl3ApplicationConfiguration.getDisplayMode()); //fullscreen
		new Lwjgl3Application(new DropGame(), config);
	}
}
