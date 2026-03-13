package com.narxoz.rpg.composite;

import java.util.List;


// добавила этот интерфейс чтобы RaidEngine мог работать с одиночным юнитом и с целой группой одинаково
public interface CombatNode {
    String getName();
    int getHealth();
    int getAttackPower();
    void takeDamage(int amount);
    boolean isAlive();
    List<CombatNode> getChildren();
    void printTree(String indent);
}
