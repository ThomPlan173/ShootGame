package com.mygdx.game.character;

import com.mygdx.game.bullet.Bullet;

public class Bomber extends Ennemi {

    private static final int BOMBER_DAMAGE = 20;
    private static final float BOMBER_FIRE_RATE = 1.75f;

    private Weapon weapon= new Weapon("Bomber Weapon", BOMBER_DAMAGE, BOMBER_FIRE_RATE);

    public Bomber(String name, int maxLife, int damage, Bullet Bullet) {
        super(name, maxLife, damage, Bullet);
    }
}
