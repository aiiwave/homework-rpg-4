package com.narxoz.rpg.bridge;

//  добавлен IceEffect (x1.1)
public class IceEffect implements EffectImplementor {
    @Override
    public int computeDamage(int basePower) {
        return Math.max(0, (int) Math.round(basePower * 1.1));
    }

    @Override
    public String getEffectName() {
        return "Ice";
    }
}
