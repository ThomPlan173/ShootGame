package com.mygdx.game.Character;

import com.mygdx.game.bullet.Bullet;

public class Character {

    private final static String MSG_ALIVE = "(ALIVE)";
    private final static String MSG_DEAD = "(DEAD)";
    private String name;
    private String xp;
    private int maxLife, life;
    private Bullet bullet;

    public Character( String name, int maxLife, int damage, Bullet bullet) {

        this.name = name;
        this.maxLife = maxLife;
        this.life = maxLife;
        this.bullet = bullet;
        this.bullet.setDamage(damage);
    }

    public String getXp() {
        return xp;
    }

    public void setXp(String xp) {
        this.xp = xp;
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
            System.out.printf("%s attacks %s for %d damage.\n", getName(), character.getName(), damage);
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

    public  Bullet getbullet() {
        return bullet;
    }

    public void setbullet(Bullet bullet) {
        this.bullet = bullet;
    }

    @Override
    public String toString() {
        return String.format("%s has %d/%d life, %s bullet equipped", getName(), getLife(), getMaxLife(), bullet.toString());
    }
}
