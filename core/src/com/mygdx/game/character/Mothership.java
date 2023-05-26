package com.mygdx.game.character;

import com.mygdx.game.bullet.Bullet;

public class Mothership extends Ennemi {

    private static final int MOTHERSHIP_DAMAGE = 40;
    private static final float MOTHERSHIP_FIRE_RATE = 2.5f;

    private Weapon weapon = new Weapon("Bomber weapon", MOTHERSHIP_DAMAGE, MOTHERSHIP_FIRE_RATE);

    public Mothership(String name, int maxLife, int damage, Bullet bullet) {
        super(name, maxLife, damage, bullet);
    }


}
