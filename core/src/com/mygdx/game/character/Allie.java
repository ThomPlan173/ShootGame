package com.mygdx.game.character;

import com.mygdx.game.character.Character;
import com.mygdx.game.bullet.Bullet;

public class Allie extends Character {

    private int attackPower;

    public Allie(String name, int maxLife, int attackPower, Bullet bullet) {
        super(name,maxLife,attackPower, bullet);
        setMaxLife(maxLife);
        setLife(maxLife);
        this.attackPower = attackPower;
    }


    public int getAttackPower() {
        return attackPower;
    }

    public void setAttackPower(int attackPower) {
        this.attackPower = attackPower;
    }

    public void attack(Ennemi ennemi) {
        int damage = getAttackPower();
        ennemi.takeDamage(damage);
        System.out.println(getName() + " attaque " + ennemi.getName() + " et lui inflige " + damage + " points de dégâts !");
    }

    public void doubleDamage(){
        int damage = getAttackPower();
        damage = damage * 2;
    }

    public void doublespeedTirs(){
        int xspeed = getxSpeed();
        int yspeed = getySpeed();

        xspeed = 2 * xspeed;
        yspeed = 2 * yspeed;
    }
}


