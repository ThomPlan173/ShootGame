package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.TimeUtils;

import java.util.Iterator;

/*
    Ce code est librement inspiré du tutoriel officiel de LibGDX
    disponible ici : https://libgdx.com/wiki/start/a-simple-game
    -> le code ci-dessous doit subir de nombreuses améliorations !!!
 */

public class GameScreen implements Screen {
    private final DropGame game;

    private OrthographicCamera camera;

    private Bucket bucket;


    private Array<Drop> raindrops;
    private long lastDropTime;
    private int dropsGathered;
    Rectangle shape ;

    // Dans ce constructeur : il y a des chiffres en dur -> on peut surement faire mieux !!!
    public GameScreen(final DropGame game) {
        this.game = game;
        this.game.addScreen(this);

        camera = new OrthographicCamera();
        camera.setToOrtho(false, 800, 480);

        bucket = new Bucket();

        bucket.shape.setX(camera.viewportWidth / 2 - bucket.shape.getWidth() / 2); // center the bucket horizontally
        bucket.shape.setY(20); // bottom left corner of the bucket is 20 pixels above the bottom screen edge

        // create the raindrops array and spawn the first raindrop
        raindrops = new Array<Drop>();
    }

    // Dans cette méthode il y a des chiffres en dur : on peut surement faire mieux !!!
    private void spawnRaindrop() {
        Drop raindrop = new Drop();
        raindrop.shape.x = MathUtils.random(0, 800 - 64);
        raindrop.shape.y = 480;
        raindrop.shape.width = 64;
        raindrop.shape.height = 64;
        raindrops.add(raindrop);
        lastDropTime = TimeUtils.nanoTime();
    }

    // Dans cette méthode il y a des chiffres en dur : on peut surement faire mieux !!!
    private void checkCollisions(){
        Iterator<Drop> iter = raindrops.iterator();
        while (iter.hasNext()) {
            Drop raindrop = iter.next();
            raindrop.shape.y -= 200 * Gdx.graphics.getDeltaTime();
            if (raindrop.shape.y + 64 < 0)
                iter.remove();
            if (Intersector.overlaps(raindrop.shape, bucket.shape)) {
                dropsGathered++;
                iter.remove();
            }
        }
    }

    // Dans cette méthode il y a des chiffres en dur : on peut surement faire mieux !!!
    private void update(){

        // Récupération des coordonnées de la souris
        Vector3 touchPos = new Vector3();
        touchPos.set(Gdx.input.getX(), Gdx.input.getY(), 0);
        camera.unproject(touchPos);

        // Modification des coordonnées du bucket (avec celles de la souris)
        bucket.shape.setX(touchPos.x - bucket.shape.width / 2);

        // ... em pêcher que le bucket sorte de l'écran
        if (bucket.shape.x < 0) bucket.shape.setX(0);
        if (bucket.shape.x > camera.viewportWidth - bucket.shape.width)
            bucket.shape.setX( camera.viewportWidth - bucket.shape.width);

        // On génère une nouvelle goutte toutes les 1000000000 ns
        if (TimeUtils.nanoTime() - lastDropTime > 1000000000) spawnRaindrop();

    }

    @Override
    public void render(float delta) {

        // Nettoyage de l'écran
        ScreenUtils.clear(0, 0, 0.2f, 1);

        // Calcul des coordonnées des objets de la scène
        this.update();

        // Calcul des collisions
        checkCollisions();

        // tell the SpriteBatch to render in the
        // coordinate system specified by the camera.
        camera.update();
        game.batch.setProjectionMatrix(camera.combined);

        // Démarrage des affichages
        game.batch.begin();

        // Affichage du texte
        game.font.draw(game.batch, "Drops Collected: " + dropsGathered, 0, 480);

        // Affichage du bucket
        bucket.draw(game.batch) ;

        // Affichage des drop
        for (Drop raindrop : raindrops) {
            raindrop.draw(game.batch);
        }

        // Fin des affichages
        game.batch.end();


    }

    @Override
    public void resize(int width, int height) {
    }

    @Override
    public void show() {
        // start the playback of the background music
        // when the screen is shown
//        rainMusic.play();
    }

    // Du fait que la classe implements Screen, on doit implémenter
    // les méthodes suivantes : même si on les laisse vides...
    @Override
    public void hide() {}
    @Override
    public void pause() {}
    @Override
    public void resume() {}

    @Override
    // A la fin du jeu, il est important de bien vider la mémoire de l'ordi
    // en "disposant" les ressources allouées (en particulier pour les textures)
    public void dispose() {
        Bucket.texture.dispose();
        Drop.texture.dispose();
    }

}