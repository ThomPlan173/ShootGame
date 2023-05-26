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

public class OptionMenu implements Screen {
    private final DropGame game;
    private OrthographicCamera camera;
    private Stage stage;
    private BitmapFont font;
    private Texture background;
    private Music music;
    private Sound hoverSound;
    private Slider volumeSlider;
    private Slider soundEffectsSlider;
    private MainMenuScreen mainMenuScreen;


    public OptionMenu(final DropGame game) {
        this.game = game;
        this.mainMenuScreen = mainMenuScreen;

        camera = new OrthographicCamera();
        camera.setToOrtho(false, 1920, 990);

        stage = new Stage();
        Gdx.input.setInputProcessor(stage);

        font = new BitmapFont(Gdx.files.internal("font.fnt"), Gdx.files.internal("font.png"), false);
        background = new Texture("space.jpg");
        music = Gdx.audio.newMusic(Gdx.files.internal("Spacearray.ogg"));
        hoverSound = Gdx.audio.newSound(Gdx.files.internal("hover.wav"));
    }

    @Override
    public void show() {
        // Create labels
        Label titleLabel = new Label("Options : ", new Label.LabelStyle(font, Color.WHITE));
        titleLabel.setPosition(900, 900, Align.center);

        Label volumeLabel = new Label("Volume:", new Label.LabelStyle(font, Color.WHITE));
        volumeLabel.setPosition(600, 700);

        Label soundEffectsLabel = new Label("Sound Effects:", new Label.LabelStyle(font, Color.WHITE));
        soundEffectsLabel.setPosition(600, 600);

        // Create sliders
        volumeSlider = createSlider(0, 1, 0.1f, music.getVolume());
        volumeSlider.setBounds(950, 690, 400, 50);

        soundEffectsSlider = createSlider(0, 1, 0.1f, 1.0f); // Set initial value
        soundEffectsSlider.setBounds(950, 590, 400, 50);

        // Create back button
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

        // Add actors to the stage
        stage.addActor(titleLabel);
        stage.addActor(volumeLabel);
        stage.addActor(soundEffectsLabel);
        stage.addActor(volumeSlider);
        stage.addActor(soundEffectsSlider);
        stage.addActor(backButton);
        music.setLooping(true);
        music.play();

        // Set up input listeners
        setupInputListeners();
    }

    private void setupInputListeners() {
        volumeSlider.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                // Update the music volume based on the slider value
                float volume = volumeSlider.getValue();
                music.setVolume(volume);
            }
        });

        soundEffectsSlider.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                // Play a sound effect based on the slider value
                float volume = soundEffectsSlider.getValue();
                hoverSound.play(volume);
            }
        });
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

    private Slider createSlider(float min, float max, float stepSize, float initialValue) {
        Slider.SliderStyle style = new Slider.SliderStyle();

        // Create background drawable using shape
        TextureRegionDrawable background = new TextureRegionDrawable();
        background.setRegion(new TextureRegion(createSliderBackgroundTexture()));
        style.background = background;

        // Create knob drawable using shape
        TextureRegionDrawable knob = new TextureRegionDrawable();
        knob.setRegion(new TextureRegion(createSliderKnobTexture()));
        style.knob = knob;

        style.knob.setMinHeight(40);
        style.knob.setMinWidth(40);

        Slider slider = new Slider(min, max, stepSize, false, style);
        slider.setValue(initialValue);
        return slider;
    }

    private Texture createSliderBackgroundTexture() {
        Pixmap pixmap = new Pixmap(400, 10, Pixmap.Format.RGBA8888);
        pixmap.setColor(Color.GRAY);
        pixmap.fill();
        Texture texture = new Texture(pixmap);
        pixmap.dispose();
        return texture;
    }

    private Texture createSliderKnobTexture() {
        Pixmap pixmap = new Pixmap(40, 40, Pixmap.Format.RGBA8888);
        pixmap.setColor(Color.WHITE);
        pixmap.fillCircle(20, 20, 20);
        Texture texture = new Texture(pixmap);
        pixmap.dispose();
        return texture;
    }
    public float getVolumeMusic(){
        return volumeSlider.getValue();
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
        stage.dispose();
        font.dispose();
        background.dispose();
        music.dispose();
        hoverSound.dispose();
    }
}

