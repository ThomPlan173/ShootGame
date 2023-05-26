package com.mygdx.game.Menu;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Slider;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.ScreenUtils;
import com.mygdx.game.DropGame;
import com.mygdx.game.GameScreen;

public class GameOverScreen implements Screen {
    private final DropGame game;
    private OrthographicCamera camera;
    private Stage stage;
    private BitmapFont font;
    private Texture background;
    private Music music;
    private Sound hoverSound;
    private Slider volumeSlider;
    private Slider soundEffectsSlider;
    private  int score ;


    public GameOverScreen(final DropGame game,int Score) {
        this.game = game;
        this.score=Score;

        camera = new OrthographicCamera();

        camera.setToOrtho(false, 1920, 990);

        stage = new Stage();
        Gdx.input.setInputProcessor(stage);

        font = new BitmapFont(Gdx.files.internal("font.fnt"), Gdx.files.internal("font.png"), false);
        background = new Texture("BlackBG.jpg");
        hoverSound = Gdx.audio.newSound(Gdx.files.internal("hover.wav"));

        music = Gdx.audio.newMusic(Gdx.files.internal("GOsound.mp3"));

    }

    @Override
    public void show() {

        //create score label
        Label ScoreLabel = new Label("Score : "+score, new Label.LabelStyle(font, Color.RED));
        ScoreLabel.setPosition(900, 800, Align.center);
        ScoreLabel.setFontScale(2f);
        // Create title label
        Label titleLabel = new Label("GAME OVER", new Label.LabelStyle(font, Color.RED));
        titleLabel.setPosition(900, 900, Align.center);
        titleLabel.setFontScale(2f);

        //Create TryAgainButton
        final TextButton TryAgainButton = new TextButton("Try Again", new TextButton.TextButtonStyle(null, null, null, font));
        TryAgainButton.setBounds(760, 500, 400, 100);



        // Create back button
        final TextButton backButton = new TextButton("Exit", new TextButton.TextButtonStyle(null, null, null, font));
        backButton.setBounds(760, 400, 400, 100);

        // Add button click listener to go back to the main menu
        backButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new MainMenuScreen(game));
                music.stop();
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
        // Add button click listener to restart Game

        TryAgainButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new GameScreen(game));
                music.stop();
            }
        });
        TryAgainButton.addListener(new ClickListener() {
            @Override
            public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                TryAgainButton.getLabel().setColor(Color.BLUE);
            }
            public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
                TryAgainButton.getLabel().setColor(Color.WHITE);
            }
        });
        // Add actors to the stage
        stage.addActor(titleLabel);
        stage.addActor(ScoreLabel);
        stage.addActor(backButton);
        stage.addActor(TryAgainButton);
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
        music.play();


        stage.act();
        stage.draw();
    }



    // Other methods and implementations

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
        font.dispose();
        background.dispose();
        music.dispose();
        hoverSound.dispose();
    }
}

