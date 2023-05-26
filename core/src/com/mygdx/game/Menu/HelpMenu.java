package com.mygdx.game.Menu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Slider;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.mygdx.game.DropGame;

public class HelpMenu implements Screen {
    private final DropGame game;
    private OrthographicCamera camera;
    private Stage stage;
    private BitmapFont font;
    private Texture background;
    private Music music;
    private Sound hoverSound;
    private Slider volumeSlider;
    private Slider soundEffectsSlider;

    public HelpMenu(final DropGame game) {
        this.game = game;

        camera = new OrthographicCamera();
        camera.setToOrtho(false, 1920, 990);

        stage = new Stage();
        Gdx.input.setInputProcessor(stage);

        font = new BitmapFont(Gdx.files.internal("font.fnt"), Gdx.files.internal("font.png"), false);
        background = new Texture("space.jpg");
        music = Gdx.audio.newMusic(Gdx.files.internal("Spacearray.ogg"));
        hoverSound = Gdx.audio.newSound(Gdx.files.internal("hover.wav"));
    }

    public void show() {
        Label helpText = new Label("Play the game to discover the secrets....", new Label.LabelStyle(font, Color.WHITE));
        helpText.setPosition(760, 900, Align.center);
        final TextButton backButton = new TextButton("Back", new TextButton.TextButtonStyle(null, null, null, font));
        backButton.setBounds(760, 400, 400, 100);

        // Add button click listener to go back to the main menu
        backButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new MainMenuScreen(game));
                dispose();
            }
        });
        backButton.addListener(new ClickListener() {
            @Override
            public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                backButton.getLabel().setColor(Color.BLUE);
            }
            public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
                backButton.getLabel().setColor(Color.WHITE);
            }
        });
        stage.addActor(helpText);
        stage.addActor(backButton);
        music.setLooping(true);
        music.play();
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0.2f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        camera.update();
        game.batch.setProjectionMatrix(camera.combined);

        game.batch.begin();
        game.batch.draw(background, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        font.getData().setScale(2f);
        game.batch.end();

        stage.act();
        stage.draw();

    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }
}
