package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

// NB : il y a du code redondant dans Drop et dans Bucket : on peut surement faire mieux !
public class Bucket {

    private static final String TEXTURE_FILE_NAME = "ship.png" ;
    static final Texture texture = new Texture(Gdx.files.internal(TEXTURE_FILE_NAME)) ;

    Rectangle shape ;

    public Bucket(){
        shape = new Rectangle(0, 0, texture.getWidth(), texture.getHeight()) ;
    }

    public void draw(SpriteBatch batch){
        batch.draw(texture, shape.x, shape.y, shape.width, shape.height);
    }

}
