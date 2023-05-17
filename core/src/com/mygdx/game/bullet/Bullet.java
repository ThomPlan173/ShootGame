package com.mygdx.game.bullet;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

public class Bullet {

    private static final String TEXTURE_FILE_NAME = "bullet.png" ;
    static final Texture texture = new Texture(Gdx.files.internal(TEXTURE_FILE_NAME)) ;

    private int damage;
    Rectangle shape ;
    float x;
    float y;
    int size;
    int xSpeed;
    int ySpeed;

    public Bullet(int damage, float x, float y, int size, int xSpeed, int ySpeed){
        this.damage = damage;
        this.x = x;
        this.y = y;
        this.size = size;
        this.xSpeed = xSpeed;
        this.ySpeed = ySpeed;

        shape = new Rectangle(0, 0, texture.getWidth(), texture.getHeight()) ;
    }

    public void draw(SpriteBatch batch){
        batch.draw(texture, shape.x, shape.y, shape.width, shape.height);
    }

    public int getDamage() {
        return damage;
    }

    public void setDamage(int damage) {
        this.damage = damage;
    }
}
