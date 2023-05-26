package com.mygdx.game.Menu;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
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
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.ButtonGroup;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.ScreenUtils;
import com.mygdx.game.DropGame;
import com.mygdx.game.GameScreen;


public class MainMenuScreen implements Screen {
    final DropGame game;
    OrthographicCamera camera;
    Stage stage;
    BitmapFont font;
    Texture background;
    Music music;
    Sound hoverSound;
    private float musicVolume;
    private  OptionMenu optionMenu;



    public MainMenuScreen(final DropGame game) {
        this.game = game;

        camera = new OrthographicCamera();
        camera.setToOrtho(false, 1920, 990);

        stage = new Stage();
        Gdx.input.setInputProcessor(stage);


        font = new BitmapFont(Gdx.files.internal("font.fnt"), Gdx.files.internal("font.png"), false);
        background = new Texture(Gdx.files.internal("space.jpg"));
        music = Gdx.audio.newMusic(Gdx.files.internal("Spacearray.ogg"));
        hoverSound = Gdx.audio.newSound(Gdx.files.internal("hover.wav"));

    }

    @Override
    public void show() {
        // Create buttons
        final TextButton startButton = new TextButton("Start",  createTextButtonStyle());
        final TextButton optionButton = new TextButton("Option", createTextButtonStyle());
        final TextButton helpButton = new TextButton("Help", createTextButtonStyle());
        final TextButton exitButton = new TextButton("Exit", createTextButtonStyle());

        // Set button positions and sizes
        float buttonWidth = 400;
        float buttonHeight = 100;
        float buttonSpacing = 40;
        float startY = 540;

        startButton.setBounds(760, startY, buttonWidth, buttonHeight);
        optionButton.setBounds(760, startY - buttonHeight - buttonSpacing, buttonWidth, buttonHeight);
        helpButton.setBounds(760, startY - 2 * (buttonHeight + buttonSpacing), buttonWidth, buttonHeight);
        exitButton.setBounds(760, startY - 3 * (buttonHeight + buttonSpacing), buttonWidth, buttonHeight);


        // Set button text font
        Label.LabelStyle labelStyle = new Label.LabelStyle(font, font.getColor());
        startButton.getLabel().setStyle(labelStyle);
        optionButton.getLabel().setStyle(labelStyle);
        helpButton.getLabel().setStyle(labelStyle);
        exitButton.getLabel().setStyle(labelStyle);

        // Add button click listeners
        startButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new GameScreen(game));
                music.stop();

            }
        });

        optionButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                OptionMenu optionMenu = new OptionMenu(game);
                game.setScreen(optionMenu);
                setMusicVolume(optionMenu.getVolumeMusic());
                dispose();
                // optionMenu.setMainMenuScreen(MainMenuScreen.this);
            }
        });


        helpButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                HelpMenu helpMenu=new HelpMenu(game);
                game.setScreen(helpMenu);
            }
        });

        exitButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.exit();
            }
        });
        startButton.addListener(new ClickListener() {
            @Override
            public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                startButton.getLabel().setColor(Color.BLUE);
                hoverSound.play();
            }

            @Override
            public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
                startButton.getLabel().setColor(Color.WHITE);
            }
        });
        optionButton.addListener(new ClickListener() {
            @Override
            public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                optionButton.getLabel().setColor(Color.BLUE);
                hoverSound.play();
            }

            @Override
            public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
                optionButton.getLabel().setColor(Color.WHITE);

            }
        });
        exitButton.addListener(new ClickListener() {
            @Override
            public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                exitButton.getLabel().setColor(Color.BLUE);
                hoverSound.play();
            }

            @Override
            public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
                exitButton.getLabel().setColor(Color.WHITE);
            }
        });
        helpButton.addListener(new ClickListener() {
            @Override
            public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                helpButton.getLabel().setColor(Color.BLUE);
                hoverSound.play();
            }

            @Override
            public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
                helpButton.getLabel().setColor(Color.WHITE);
            }
        });



        // Add buttons to stage
        stage.addActor(startButton);
        stage.addActor(optionButton);
        stage.addActor(helpButton);
        stage.addActor(exitButton);
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
        font.draw(game.batch, "WELCOME TO SHOOT GAME!",650 , 900);
        game.batch.end();
        music.play();
        stage.act();
        stage.draw();
    }

    private TextButton.TextButtonStyle createTextButtonStyle() {
        Skin skin = new Skin();

        // Create default style
        TextButton.TextButtonStyle style = new TextButton.TextButtonStyle();
        style.font = font;

        // Create pressed style
        TextButton.TextButtonStyle pressedStyle = new TextButton.TextButtonStyle();
        pressedStyle.font = font;

        // Create checked style
        TextButton.TextButtonStyle checkedStyle = new TextButton.TextButtonStyle();
        checkedStyle.font = font;

        // Apply styles to the skin
        skin.add("default", style);
        skin.add("pressed", pressedStyle);
        skin.add("checked", checkedStyle);

        return style;
    }
    public void setMusicVolume(float volume) {
        musicVolume = volume;
        music.setVolume(musicVolume);
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
        stage.dispose();
        font.dispose();
        background.dispose();
        music.dispose();
        hoverSound.dispose();
    }
}


