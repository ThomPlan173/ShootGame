package com.mygdx.game.character;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

// NB : il y a du code redondant dans Drop et dans Ship : on peut surement faire mieux !
public class Ship {

    private static final String TEXTURE_FILE_NAME = "ship.png" ;
    public static final Texture texture = new Texture(Gdx.files.internal(TEXTURE_FILE_NAME));

    public Rectangle shape ;

    public Ship(){
        shape = new Rectangle(0, 0, texture.getWidth(), texture.getHeight()) ;
    }

    public void draw(SpriteBatch batch){
        batch.draw(texture, shape.x, shape.y, shape.width, shape.height);
    }


}
