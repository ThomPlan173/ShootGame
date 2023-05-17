package com.mygdx.game.character;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Array;
import com.mygdx.game.bullet.Drop;

public class Shooter {

    private static final String TEXTURE_FILE_NAME = "shooter.png";
    private static final Texture texture = new Texture(Gdx.files.internal(TEXTURE_FILE_NAME));

    public Rectangle shape;
    private float xVelocity;
    private float movementSpeed;

    private float yVelocity;
    public Array<Drop> lasers;
    private boolean movingRight;
    // Indicateur de direction du déplacement
    private boolean isMovingRight = true;
    public Shooter() {
        shape = new Rectangle(0, 0, texture.getWidth(), texture.getHeight());
        xVelocity = 200f; // Vitesse horizontale de déplacement (plus petite valeur pour ralentir)
        yVelocity = 200f; // Vitesse verticale de déplacement
        movingRight = true; // Commence en se déplaçant vers la droite

        // Position aléatoire à l'intérieur de l'écran
        float randomX = (float) (Math.random() * (Gdx.graphics.getWidth() - shape.width));
        float randomY = (float) (Math.random() * (Gdx.graphics.getHeight() - shape.height));
        shape.setPosition(randomX, randomY);
    }
    public void update(float deltaTime) {
        float screenWidth = Gdx.graphics.getWidth();
        float screenHeight = Gdx.graphics.getHeight();

        // Déplacement aléatoire
        float newX = shape.x + xVelocity * deltaTime;
        float newY = shape.y + yVelocity * deltaTime;

        // Vérifier les limites de l'écran
        if (newX < 0 || newX + shape.width > screenWidth) {
            // Inverser la direction horizontale
            xVelocity = -xVelocity;
            newX = MathUtils.clamp(newX, 0, screenWidth - shape.width); // Limiter la position dans les limites de l'écran
        }

        if (newY < 0 || newY + shape.height > screenHeight) {
            // Inverser la direction verticale
            yVelocity = -yVelocity;
            newY = MathUtils.clamp(newY, 0, screenHeight - shape.height); // Limiter la position dans les limites de l'écran
        }

        shape.setPosition(newX, newY);
    }

    private void shoot() {
        Drop laser = new Drop();
        laser.activate(shape.x + shape.width / 2 - laser.shape.width / 2, shape.y);
        lasers.add(laser); // Ajoute le laser à la liste des lasers tirés
    }

    void changeDirection() {
        isMovingRight = !isMovingRight; // Inverser la direction actuelle
    }

    public void setMovementSpeed(float speed) {
        movementSpeed = speed;
    }

    public float getMovementSpeed() {
        return movementSpeed;
    }


    public void draw(SpriteBatch batch) {
        batch.draw(texture, shape.x, shape.y, shape.width, shape.height);
    }
}
