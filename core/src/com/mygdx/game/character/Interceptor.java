package com.mygdx.game.character;

import com.mygdx.game.bullet.Bullet;

public class Interceptor extends Ennemi {

    private static final int INTERCEPTOR_DAMAGE = 30;
    private static final float INTERCEPTOR_FIRE_RATE = 2f;

    private Weapon weapon = new Weapon("Bomber weapon", INTERCEPTOR_DAMAGE, INTERCEPTOR_FIRE_RATE);

    public Interceptor(String name, int maxLife, int damage, Bullet bullet) {
        super(name, maxLife, damage, bullet);
    }


}
