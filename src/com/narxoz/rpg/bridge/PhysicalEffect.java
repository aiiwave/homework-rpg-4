package com.narxoz.rpg.bridge;

//добавила этот класс для реализации физического урона без элементального усиления
public class PhysicalEffect implements EffectImplementor {
    @Override
    public int computeDamage(int basePower) {
        return Math.max(0, basePower);
    }

    @Override
    public String getEffectName() {
        return "Physical";
    }
}
