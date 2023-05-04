package com.mygdx.game.Character;

import com.mygdx.game.Character.Character;
public class Allie extends Character {

    private int attackPower;

    public Allie(String name, int maxLife, int attackPower) {
        super(name);
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
}

