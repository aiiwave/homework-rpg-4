package com.narxoz.rpg.composite;

// добавлены конкретные листья HeroUnit и EnemyUnit
public class HeroUnit extends UnitLeaf {
    public HeroUnit(String name, int health, int attackPower) {
        super(name, health, attackPower);
    }
}
