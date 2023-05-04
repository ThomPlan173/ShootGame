package com.mygdx.game.Character;

public class Weapon {

    private String name;
    private int damage;
    private float fireRate;

    public Weapon(String name, int damage, float fireRate) {
        this.name = name;
        this.damage = damage;
        this.fireRate = fireRate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getDamage() {
        return damage;
    }

    public void setDamage(int damage) {
        this.damage = damage;
    }

    public float getFireRate() {
        return fireRate;
    }

    public void setFireRate(float fireRate) {
        this.fireRate = fireRate;
    }

}

