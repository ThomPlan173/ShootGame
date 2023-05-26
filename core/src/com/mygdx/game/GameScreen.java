package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.TimeUtils;
import com.mygdx.game.Menu.MainMenuScreen;
import com.mygdx.game.bullet.Bullet;
import com.mygdx.game.bullet.Drop;
import com.mygdx.game.character.Allie;
import com.mygdx.game.character.Ship;
import com.mygdx.game.character.Shooter;
import com.mygdx.game.bullet.Bullet;

import java.util.Iterator;

public class GameScreen implements Screen {
    private final DropGame game;
    private OrthographicCamera camera;
    private SpriteBatch batch;
    private Ship ship;

    private Character character;
    private Array<Drop> drops;
    private long lastDropTime;
    private int dropsGathered = 0;

    private long lastBulletTime = 0;
    private Allie allie;
    private int actualLife;
    private Bullet bullet;
    private Array<Drop> raindrops;
    private Array<Bullet> bulletTirs;
    private Shooter[] shooters; // Tableau de shooters
    private boolean paused;
    private Texture pauseIcon;
    private Texture background;
    private float elapsedTime = 0;



    public GameScreen(final DropGame game) {
        this.game = game;
        this.game.addScreen(this);

        camera = new OrthographicCamera();
        camera.setToOrtho(false, 1920, 1080);

        batch = new SpriteBatch();
        ship = new Ship();

        drops = new Array<Drop>();
        bulletTirs = new Array<>();

        bullet = new Bullet();
        bullet.setVelocity(200, 300);

        allie = new Allie("thomas", 20, 10, bullet);
        actualLife = allie.getMaxLife();

        shooters = new Shooter[4]; // Tableau de 4 shooters

        paused = false;   // Initialize the pause state to false
        pauseIcon = new Texture(Gdx.files.internal("pause.png"));

        for (int i = 0; i < 4; i++) {
            shooters[i] = new Shooter();
            shooters[i].shape.x = MathUtils.random(0, 1920 - shooters[i].shape.width);
            shooters[i].shape.y = MathUtils.random(200, 1080 - shooters[i].shape.height);
            // Les autres paramètres du shooter, comme la vitesse et l'intervalle de tir, peuvent être initialisés ici
        }
        background = new Texture(Gdx.files.internal("space.jpg"));

    }

    private void spawnRaindrop() {
        Drop raindrop = new Drop();
        raindrop.shape.x = MathUtils.random(0, 1920 - 64);
        raindrop.shape.y = 1080;
        raindrop.shape.width = 64;
        raindrop.shape.height = 64;
        drops.add(raindrop);
        lastDropTime = TimeUtils.nanoTime();
    }
    private void spawnBullet() {

        Bullet newBullet = new Bullet(bullet.getDamage(), ship.shape.x - (ship.shape.getHeight()/2), ship.shape.y - (ship.shape.getHeight()/2), bullet.getSize(), 10, 0);
        bulletTirs.add(newBullet);
        lastBulletTime = TimeUtils.nanoTime();
    }

    private void checkCollisions() {
        Iterator<Drop> iter = drops.iterator();
        while (iter.hasNext()) {
            Drop raindrop = iter.next();
            raindrop.shape.y -= 200 * Gdx.graphics.getDeltaTime();
            if (raindrop.shape.y + 64 < 0) {
                iter.remove();
            }
            if (Intersector.overlaps(raindrop.shape, ship.shape)) {
                actualLife--;
                iter.remove();
            }
        }
    }
    private void checkTirs() {
        Iterator<Bullet> iterBull = bulletTirs.iterator();
        while (iterBull.hasNext()) {
            Bullet bulletTir = iterBull.next();
            bulletTir.shape.y += 25;
            if (bulletTir.shape.y > 1080) {
                iterBull.remove();
            }
        }
    }
    private void update(float delta) {
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

        long timeDiff = TimeUtils.nanoTime() - lastDropTime;
        if (timeDiff > 1000000000){
            spawnRaindrop();
            spawnBullet();
        }
        checkCollisions();
        checkTirs();

        if (ship.shape.y < 0) ship.shape.setY(0);
        if (ship.shape.y > camera.viewportHeight - ship.shape.height)
            ship.shape.setY( camera.viewportHeight - ship.shape.height);

        bullet.update(delta);

        for (Shooter shooter : shooters) {
            shooter.update(delta);

            // Vérifier les collisions avec les autres shooters
            for (Shooter otherShooter : shooters) {
                if (shooter != otherShooter && Intersector.overlaps(shooter.shape, otherShooter.shape)) {
                    // Changer la direction du shooter pour éviter la collision
                    shooter.changeDirection();
                }

                // Vérifier la distance minimale entre les shooters
                float minDistance = 100f; // Distance minimale souhaitée entre les shooters
                if (shooter != otherShooter) {
                    float distanceX = shooter.shape.x - otherShooter.shape.x;
                    float distanceY = shooter.shape.y - otherShooter.shape.y;
                    float distance = (float) Math.sqrt(distanceX * distanceX + distanceY * distanceY);

                    if (distance < minDistance) {
                        // Calculer le vecteur de déplacement pour s'éloigner de l'autre shooter
                        float deltaX = (distanceX / distance) * (minDistance - distance);
                        float deltaY = (distanceY / distance) * (minDistance - distance);

                        // Déplacer le shooter pour s'éloigner de l'autre shooter
                        shooter.shape.x += deltaX;
                        shooter.shape.y += deltaY;
                    }
                }
            }
        }


        for (Drop drop : drops) {
            if (!drop.isActive()) {
                // Sélectionner un shooter aléatoire pour le tir
                int randomShooterIndex = MathUtils.random(0, shooters.length - 1);
                Shooter randomShooter = shooters[randomShooterIndex];
                // Activer le drop à partir de la position du shooter sélectionné
                drop.activate(randomShooter.shape.x + randomShooter.shape.width / 2 - drop.shape.width / 2, randomShooter.shape.y);
                randomShooter.setMovementSpeed(randomShooter.getMovementSpeed() + MathUtils.random(-50, 50));

                break;
            }
        }
    }


    @Override
    public void render(float delta) {
        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
            paused = !paused;

        }

        if (paused) {
            // Render the pause icon at the center of the screen
            game.batch.begin();
            game.batch.draw(pauseIcon, camera.viewportWidth / 2 - pauseIcon.getWidth() / 2 , camera.viewportHeight / 2 - pauseIcon.getHeight() / 2 -100);

            Texture exitButton = new Texture(Gdx.files.internal("exit.png"));
            float buttonWidth = 400; // Adjust the button width as needed
            float buttonHeight = 200; // Adjust the button height as needed
            float buttonX = camera.viewportWidth / 2 - buttonWidth / 2 +150;
            float buttonY = camera.viewportHeight / 2 - buttonHeight / 2 - 200; // Adjust the button Y position as needed
            game.batch.draw(exitButton, buttonX, buttonY, buttonWidth, buttonHeight);

            game.batch.end();

            // Handle input for the exit button
            if (Gdx.input.justTouched()) {
                float touchX = Gdx.input.getX();
                float touchY = Gdx.input.getY();
                Vector3 worldCoordinates = camera.unproject(new Vector3(touchX, touchY, 0));
                if (worldCoordinates.x >= buttonX && worldCoordinates.x <= buttonX + buttonWidth &&
                        worldCoordinates.y >= buttonY && worldCoordinates.y <= buttonY + buttonHeight) {
                    game.setScreen(new MainMenuScreen(game));
                }
            }
            return;  // Skip the game logic and rendering when paused
        }

        ScreenUtils.clear(0, 0, 0.2f, 1);

        update(delta);


        camera.update();
        batch.setProjectionMatrix(camera.combined);
        game.batch.setProjectionMatrix(camera.combined);

        // Démarrage des affichages
        game.batch.begin();
        game.scrollingBackground.updateAndRender(delta,game.batch);
        // Affichage du ship
        ship.draw(game.batch) ;
        game.font.draw(game.batch, "Life: " + actualLife, 20, 1080 - 20);
        game.batch.end();
        batch.begin();
        for (Drop raindrop : drops) {
            raindrop.draw(batch);
        }

        for (Bullet bullet : bulletTirs) {
            bullet.draw(batch);
        }

        for (Shooter shooter : shooters) {
            shooter.draw(batch);
        }
        batch.end();
        if (actualLife <= 0) {
            //game.setScreen(new GameOverScreen(game));
            dispose();
        }
    }

    @Override
    public void resize(int width, int height) {
    }

    @Override
    public void show() {
    }

    @Override
    public void hide() {
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void dispose() {
        ship.texture.dispose();
        Drop.texture.dispose();
        pauseIcon.dispose();
        background.dispose();
    }
}