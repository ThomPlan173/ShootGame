package com.mygdx.game.character;

import com.mygdx.game.bullet.Bullet;

public abstract class  Character {

    private static String MSG_ALIVE = "(ALIVE)";
    private static String MSG_DEAD = "(DEAD)";
    public  Bullet bullet;

    private String name;


    private int maxLife, life;


    public Character(String name, int maxLife, int damage, Bullet bullet) {
        this.name = name;
        this.maxLife = maxLife;
        this.life = maxLife;
        this.bullet = bullet;
    }

    public Character(String name) {
        this.name = name;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getMaxLife() {
        return maxLife;
    }

    protected void setMaxLife(int maxLife) {
        this.maxLife = maxLife;
    }

    public int getLife() {
        return life;
    }

    protected void setLife(int life) {
        this.life = life;
    }

    public boolean isAlive() {
        return life > 0;
    }

    public void printStats() {
        System.out.println(this);
    }

    public void attack(Character character) {
        int damage = bullet.getDamage();
        if (isAlive()) {
            character.takeDamage(damage);
        } else {
            System.out.printf("%s is dead and cannot attack.\n", getName());
        }
    }

    public void takeDamage(int damage) {
        setLife(Math.max(0, getLife() - damage));
        if (isAlive()) {
            System.out.println(getName() + " subit " + damage + " points de dégâts et a " + getLife() + " points de vie restants " + MSG_ALIVE);
        } else {
            System.out.println(getName() + " subit " + damage + " points de dégâts et est mort " + MSG_DEAD);
        }
    }


    @Override
    public String toString() {
        return String.format("%s has %d/%d life", getName(), getLife(), getMaxLife());
    }
}
