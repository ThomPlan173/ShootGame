package com.mygdx.game.Character;
import com.mygdx.game.Character.Character;
import com.mygdx.game.bullet.Bullet;

public class Ennemi extends Character {

    public Ennemi(String name, int maxLife, int damage, Bullet bullet) {
        super(name, maxLife, damage, bullet);
    }

    @Override
    public void attack(Character character) {
        int damage = getbullet().getDamage();
        super.attack(character);
    }

    @Override
    public void takeDamage(int damage) {
        super.takeDamage(damage);
    }
}
