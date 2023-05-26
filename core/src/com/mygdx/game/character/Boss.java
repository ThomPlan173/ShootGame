package com.mygdx.game.character;


import com.mygdx.game.bullet.Bullet;

public class Boss extends Ennemi {

    private static final int BOSS_DAMAGE = 50;

    private static final float BOSS_FIRE_RATE = 3f;
    Weapon weapon=new Weapon("Boss weapon", BOSS_DAMAGE,BOSS_FIRE_RATE);

    public Boss(String name, int maxLife, int damage, Bullet bullet) {
        super(name, maxLife, damage,  bullet);
    }


    public void specialAttack(Character character) {
        int damage = bullet.getDamage() * 2;
        System.out.printf("%s uses special attack on %s for %d damage.\n", getName(), character.getName(), damage);
        character.takeDamage(damage);
    }
}
