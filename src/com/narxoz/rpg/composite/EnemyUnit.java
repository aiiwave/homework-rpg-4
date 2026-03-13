package com.narxoz.rpg.composite;

// добавлены конкретные листья HeroUnit и EnemyUnit
public class EnemyUnit extends UnitLeaf {
    public EnemyUnit(String name, int health, int attackPower) {
        super(name, health, attackPower);
    }
}
