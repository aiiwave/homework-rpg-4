package com.narxoz.rpg.bridge;

import com.narxoz.rpg.composite.CombatNode;

// добавила этот класс чтобы +скилл бьющий лишь одну цель
public class SingleTargetSkill extends Skill {
    public SingleTargetSkill(String skillName, int basePower, EffectImplementor effect) {
        super(skillName, basePower, effect);
    }

    @Override
    public void cast(CombatNode target) {
        int damage = resolvedDamage();
        target.takeDamage(damage);
        System.out.println("  [" + getSkillName() + "/" + getEffectName() + "] deals " + damage + " dmg to " + target.getName());
    }
}
