package com.narxoz.rpg.bridge;

// добавлен ShadowEffect (x1.3)
public class ShadowEffect implements EffectImplementor {
    @Override
    public int computeDamage(int basePower) {
        return Math.max(0, (int) Math.round(basePower * 1.3));
    }

    @Override
    public String getEffectName() {
        return "Shadow";
    }
}
