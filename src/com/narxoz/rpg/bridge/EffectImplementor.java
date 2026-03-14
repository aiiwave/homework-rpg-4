package com.narxoz.rpg.bridge;

// добавила этот интерфейс и внесла логику расчёта урона отдельно от скиллов
public interface EffectImplementor {
    int computeDamage(int basePower);
    String getEffectName();
}
