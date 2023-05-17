package com.mygdx.game.character;

public class Interceptor extends Ennemi {

    private static final int INTERCEPTOR_DAMAGE = 30;
    private static final float INTERCEPTOR_FIRE_RATE = 2f;

    private Weapon weapon = new Weapon("Bomber weapon", INTERCEPTOR_DAMAGE, INTERCEPTOR_FIRE_RATE);

    public Interceptor(String name, int maxLife, int damage, Weapon weapon) {
        super(name, maxLife, damage, weapon);
    }


}
