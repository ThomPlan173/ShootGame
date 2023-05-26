package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.TimeUtils;
import com.mygdx.game.bullet.Bullet;
import com.mygdx.game.bullet.Drop;
import com.mygdx.game.character.Allie;
import com.mygdx.game.character.Ship;
import com.mygdx.game.character.Shooter;
import com.mygdx.game.bullet.Bullet;

import java.util.Arrays;
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
    private Array<Shooter> shooters;
    // Tableau de shooters

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

        shooters = new Array<Shooter>();
        // Tableau de 4 shooters

        for (int i = 0; i < 4; i++) {
            Shooter shooter = new Shooter();

            float randomX = MathUtils.random(0, 1920 - shooter.shape.width);
            float randomY = MathUtils.random(200, 1080 - shooter.shape.height);
            shooter.shape.set(randomX, randomY, shooter.shape.width, shooter.shape.height);
            shooters.add(shooter);
        }
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
        Bullet newBullet = new Bullet(bullet.getDamage(), ship.shape.x - (ship.shape.getHeight() / 2),
                ship.shape.y - (ship.shape.getHeight() / 2), bullet.getSize(), 10, 0);
        bulletTirs.add(newBullet);
        lastBulletTime = TimeUtils.nanoTime();
    }

    private void checkCollisions() {
        Array<Drop> dropsCopy = new Array<>(drops);

        for (Drop raindrop : dropsCopy) {
            raindrop.shape.y -= 200 * Gdx.graphics.getDeltaTime();
            if (raindrop.shape.y + 64 < 0) {
                drops.removeValue(raindrop, true);
            }
            if (Intersector.overlaps(raindrop.shape, ship.shape)) {
                actualLife--;
                drops.removeValue(raindrop, true);
            }
        }

        Array<Bullet> bulletTirsCopy = new Array<>(bulletTirs);

        for (Bullet bulletTir : bulletTirsCopy) {
            bulletTir.shape.y += 25;
            if (bulletTir.shape.y > 1080) {
                bulletTirs.removeValue(bulletTir, true);
            }

            for (int i = shooters.size - 1; i >= 0; i--) {
                Shooter shooter = shooters.get(i);
                if (Intersector.overlaps(bulletTir.shape, shooter.shape)) {
                    shooter.takeDamage(bulletTir.getDamage());
                    bulletTirs.removeValue(bulletTir, true);

                    if (shooter.getHealth() <= 0) {
                        shooters.removeIndex(i);
                    }

                    break; // Sortir de la boucle si une collision est détectée
                }
            }
        }
    }




    private void checkTirs() {
        for (int i = bulletTirs.size - 1; i >= 0; i--) {
            Bullet bulletTir = bulletTirs.get(i);
            bulletTir.shape.y += 25;
            if (bulletTir.shape.y > 1080) {
                bulletTirs.removeIndex(i);
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
            ship.shape.setX(camera.viewportWidth - ship.shape.width);

        long timeDiff = TimeUtils.nanoTime() - lastDropTime;
        if (timeDiff > 1000000000) {
            spawnRaindrop();
            spawnBullet();
        }
        checkCollisions();
        checkTirs();

        if (ship.shape.y < 0) ship.shape.setY(0);
        if (ship.shape.y > camera.viewportHeight - ship.shape.height)
            ship.shape.setY(camera.viewportHeight - ship.shape.height);

        bullet.update(delta);

        for (int i = 0; i < shooters.size; i++) {
            Shooter shooter = shooters.get(i);
            shooter.update(delta);

            // Vérifier les collisions avec les autres shooters
            for (int j = i + 1; j < shooters.size; j++) {
                Shooter otherShooter = shooters.get(j);
                if (Intersector.overlaps(shooter.shape, otherShooter.shape)) {
                    // Changer la direction du shooter pour éviter la collision
                    shooter.changeDirection();
                    otherShooter.changeDirection();
                }
            }

            // Vérifier la distance minimale entre les shooters
            float minDistance = 100f; // Distance minimale souhaitée entre les shooters
            for (int j = i + 1; j < shooters.size; j++) {
                Shooter otherShooter = shooters.get(j);
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
                int randomShooterIndex = MathUtils.random(0, shooters.size - 1);

                Shooter randomShooter = shooters.get(randomShooterIndex);

                // Activer le drop à partir de la position du shooter sélectionné
                float dropX = randomShooter.shape.x + randomShooter.shape.width / 2 - drop.shape.width / 2;
                float dropY = randomShooter.shape.y;
                drop.activate(dropX, dropY);
                randomShooter.setMovementSpeed(randomShooter.getMovementSpeed() + MathUtils.random(-50, 50));
                break;
            }
        }
    }


    @Override
    public void render(float delta) {
        ScreenUtils.clear(0, 0, 0.2f, 1);

        update(delta);

        camera.update();
        game.batch.setProjectionMatrix(camera.combined);

        game.batch.begin();

        ship.draw(game.batch);
        game.font.draw(game.batch, "Life: " + actualLife, 20, 1080 - 20);

        update(delta);

        for (Drop raindrop : drops) {
            raindrop.draw(game.batch);
        }

        for (Bullet bullet : bulletTirs) {
            bullet.draw(game.batch);
        }

        for (Shooter shooter : shooters) {
            shooter.draw(game.batch);
        }

        game.batch.end();

        // Vérifier la condition après la mise à jour
        if (actualLife <= 0 && !Gdx.graphics.isFullscreen()) {
            Gdx.graphics.setWindowedMode(800, 600); // Définir une taille de fenêtre par défaut
            actualLife = 20; // Réinitialiser la vie du joueur
        }
    }



    @Override
    public void resize(int width, int height) {
    }

    @Override
    public void show() {
         }

    @Override
    public void hide()
    {
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
        batch.dispose();
    }
}
