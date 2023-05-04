package com.mygdx.game.Character;
import com.mygdx.game.Character.Character;
public class Ennemi extends Character {

    public Ennemi(String name, int maxLife, int damage, Weapon weapon) {
        super(name, maxLife, damage, weapon);
    }

    @Override
    public void attack(Character character) {
        int damage = getWeapon().getDamage();
        super.attack(character);
    }

    @Override
    public void takeDamage(int damage) {
        super.takeDamage(damage);
    }
}
