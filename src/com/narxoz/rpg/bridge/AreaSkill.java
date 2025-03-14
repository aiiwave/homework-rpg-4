package com.narxoz.rpg.bridge;

import com.narxoz.rpg.composite.CombatNode;

public class AreaSkill extends Skill {
    public AreaSkill(String skillName, int basePower, EffectImplementor effect) {
        super(skillName, basePower, effect);
    }

    @Override
    public void cast(CombatNode target) {
        int damage = resolvedDamage();
        System.out.println("  [" + getSkillName() + "/" + getEffectName() + "] AOE deals " + damage + " dmg to " + target.getName());
        applyToLeaves(target, damage);
    }

    private void applyToLeaves(CombatNode node, int damage) {
        if (node.getChildren().isEmpty()) {
            // leaf node
            if (node.isAlive()) {
                node.takeDamage(damage);
                System.out.println("    -> " + node.getName() + " takes " + damage + " dmg (HP left: " + node.getHealth() + ")");
            }
        } else {
            for (CombatNode child : node.getChildren()) {
                applyToLeaves(child, damage);
            }
        }
    }
}
