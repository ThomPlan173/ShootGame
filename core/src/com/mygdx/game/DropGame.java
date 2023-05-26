package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;
import com.mygdx.game.Menu.MainMenuScreen;
import javafx.scene.layout.Background;

public class DropGame extends Game {

	public SpriteBatch batch;
	public BitmapFont font;
	private float musicVolume = 1.0f;

	private Array<Screen> screens = new Array<>();
	public ScrollingBackground scrollingBackground;

	public void addScreen(Screen screen){
		this.screens.add(screen);
	}

	public void create() {
		batch = new SpriteBatch();
		this.scrollingBackground=new ScrollingBackground();
		font = new BitmapFont(); // use libGDX's default Arial font
		Screen mm = new MainMenuScreen(this) ;
		this.addScreen(mm);
		this.setScreen(mm);
	}

	public void render() {
		super.render(); // important!
	}

	public void dispose() {
		batch.dispose();
		font.dispose();
		for(Screen s: screens){
			s.dispose();
		}
	}
	public void setMusicVolume(float volume) {
		musicVolume = volume;
	}

	@Override
	public void resize(int width, int height) {
		super.resize(width, height);
	}
}
