package com.mygdx.game.bullet;

import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Rectangle;


public class BulletManager {
    private static BulletManager instance;

    private Array<Bullet> bullets;

    private BulletManager() {
        bullets = new Array<>();
    }

    public static BulletManager getInstance() {
        if (instance == null) {
            instance = new BulletManager();
        }
        return instance;
    }

    public void update(float deltaTime) {
        for (int i = bullets.size - 1; i >= 0; i--) {
            Bullet bullet = bullets.get(i);
            bullet.update(deltaTime);
            if (isBulletOutOfBounds(bullet)) {
                despawnBullet(bullet);
            }
        }
    }

    public void draw(SpriteBatch batch) {
        for (Bullet bullet : bullets) {
            bullet.draw(batch);
        }
    }

    public void spawnBullet(float x, float y, float xVelocity, float yVelocity) {
        Bullet bullet = new Bullet();
        bullet.setVelocity(xVelocity, yVelocity);
        bullet.getShape().setPosition(x, y);
        bullets.add(bullet);
    }

    public void despawnBullet(Bullet bullet) {
        bullets.removeValue(bullet, true);
    }

    private boolean isBulletOutOfBounds(Bullet bullet) {
        float screenWidth = Gdx.graphics.getWidth();
        float screenHeight = Gdx.graphics.getHeight();
        Rectangle shape = bullet.getShape();
        return shape.x + shape.width < 0 || shape.x > screenWidth || shape.y + shape.height < 0 || shape.y > screenHeight;
    }
}
