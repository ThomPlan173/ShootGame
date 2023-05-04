package com.mygdx.game.Character;

public class Bomber extends Ennemi {

    private static final int BOMBER_DAMAGE = 20;
    private static final float BOMBER_FIRE_RATE = 1.75f;

    private Weapon weapon = new Weapon("Bomber weapon", BOMBER_DAMAGE, BOMBER_FIRE_RATE);

    public Bomber(String name, int maxLife, int damage, Weapon weapon) {
        super(name, maxLife, damage, weapon);
    }
}
