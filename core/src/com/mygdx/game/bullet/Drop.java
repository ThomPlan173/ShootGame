package com.mygdx.game.bullet;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

public class Drop {

    private static final String TEXTURE_FILE_NAME = "lazer.png";
    static final Texture texture = new Texture(Gdx.files.internal(TEXTURE_FILE_NAME));

    Rectangle shape;
    private float movementSpeed;
    private boolean isActive;

    public Drop() {
        shape = new Rectangle(0, 0, texture.getWidth(), texture.getHeight());
        movementSpeed = 50000f; // Augmentation de la vitesse de déplacement vertical des lasers
        isActive = false;
    }

    public void activate(float x, float y) {
        shape.setPosition(x, y);
        isActive = true;
    }

    public void update(float deltaTime) {
        if (isActive) {
            shape.y += movementSpeed * deltaTime;
            if (shape.y > Gdx.graphics.getHeight()) {
                isActive = false;
            }
        }
    }

    public void draw(SpriteBatch batch) {
        if (isActive) {
            batch.draw(texture, shape.x, shape.y, shape.width, shape.height);
        }
    }

    public boolean isActive() {
        return isActive;
    }
}
