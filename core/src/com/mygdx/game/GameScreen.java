package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.TimeUtils;
import com.mygdx.game.bullet.Bullet;

import java.util.Iterator;

public class GameScreen implements Screen {
    private final DropGame game;

    private OrthographicCamera camera;

    private Ship ship;

    private Bullet bullet;

    private Character character;
    private Array<Drop> raindrops;
    private long lastDropTime;
    private int dropsGathered = 10;
    Rectangle shape ;

    // Dans ce constructeur : il y a des chiffres en dur -> on peut surement faire mieux !!!
    public GameScreen(final DropGame game) {
        this.game = game;
        this.game.addScreen(this);

        camera = new OrthographicCamera();
        camera.setToOrtho(false, 1920, 1080);

        ship = new Ship();

        ship.shape.setX(camera.viewportWidth / 2 - ship.shape.getWidth() / 2); // center the ship horizontally
        ship.shape.setY(20); // bottom left corner of the ship is 20 pixels above the bottom screen edge

        bullet = new Bullet(10,ship.shape.x,ship.shape.y,10,0,10);

        this.character = new Character("Thomas")
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
            if (Intersector.overlaps(raindrop.shape, ship.shape)) {
                dropsGathered--;
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

        // Modification des coordonnées du ship (avec celles de la souris)
        ship.shape.setX(touchPos.x - ship.shape.width / 2);
        ship.shape.setY(touchPos.y - ship.shape.height / 2);

        // ... empêcher que le ship sorte de l'écran
        if (ship.shape.x < 0) ship.shape.setX(0);
        if (ship.shape.x > camera.viewportWidth - ship.shape.width)
            ship.shape.setX( camera.viewportWidth - ship.shape.width);

        if (ship.shape.y < 0) ship.shape.setY(0);
        if (ship.shape.y > camera.viewportHeight - ship.shape.height)
            ship.shape.setY( camera.viewportHeight - ship.shape.height);

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
        game.font.draw(game.batch, "Nombre de vies restantes : " + character.toString() , 0, camera.viewportHeight - 10 );


        // Affichage du ship
        ship.draw(game.batch) ;

        // Affichage des drop
        for (Drop raindrop : raindrops) {
            raindrop.draw(game.batch);
        }
        bullet.draw(game.batch);

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
        ship.texture.dispose();
        Drop.texture.dispose();
    }

}