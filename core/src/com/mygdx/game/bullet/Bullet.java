package com.mygdx.game.bullet;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

public class Bullet {

    private static final String TEXTURE_FILE_NAME = "bullet.png";
    private static final Texture texture = new Texture(Gdx.files.internal(TEXTURE_FILE_NAME));

    public Rectangle shape;
    private float xVelocity;
    private float yVelocity;

    private int damage;
    public int getSize() {
        return size;
    }

    int size;

    public Bullet() {
        shape = new Rectangle(0, 0, texture.getWidth(), texture.getHeight());
        xVelocity = 0;
        yVelocity = 0;
        damage=0;
    }
    public Bullet(int damage, float x, float y, int size, int xSpeed, int ySpeed){
        shape = new Rectangle(0, 0, texture.getWidth(), texture.getHeight()) ;
        this.damage = damage;
        this.shape.x = x;
        this.shape.y = y;
        this.size = size;
        this.xVelocity = xSpeed;
        this.yVelocity = ySpeed;


    }
    public void setVelocity(float xVelocity, float yVelocity) {
        this.xVelocity = xVelocity;
        this.yVelocity = yVelocity;
    }

    public void update(float deltaTime) {
        shape.x += xVelocity * deltaTime;
        shape.y += yVelocity * deltaTime;
    }

    public void draw(SpriteBatch batch) {
        batch.draw(texture, shape.x, shape.y, shape.width, shape.height);
    }

    public Rectangle getShape() {
        return shape;
    }




    public int getDamage() {
        return damage;
    }

    public boolean isOutOfBounds() {
        float screenWidth = Gdx.graphics.getWidth();
        float screenHeight = Gdx.graphics.getHeight();
        return shape.x + shape.width < 0 || shape.x > screenWidth || shape.y + shape.height < 0 || shape.y > screenHeight;
    }
}
