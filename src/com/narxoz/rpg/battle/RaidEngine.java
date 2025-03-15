package com.narxoz.rpg.battle;

import com.narxoz.rpg.bridge.Skill;
import com.narxoz.rpg.composite.CombatNode;

import java.util.Random;

public class RaidEngine {
    private Random random = new Random(1L);

    public RaidEngine setRandomSeed(long seed) {
        this.random = new Random(seed);
        return this;
    }

    public RaidResult runRaid(CombatNode teamA, CombatNode teamB, Skill teamASkill, Skill teamBSkill) {
        if (teamA == null || teamB == null || teamASkill == null || teamBSkill == null) {
            throw new IllegalArgumentException("Arguments must not be null");
        }
        if (!teamA.isAlive() || !teamB.isAlive()) {
            throw new IllegalArgumentException("Both teams must be alive at start");
        }

        RaidResult result = new RaidResult();
        int round = 0;
        final int MAX_ROUNDS = 100;

        result.addLine("=== Raid begins: " + teamA.getName() + " vs " + teamB.getName() + " ===");

        while (teamA.isAlive() && teamB.isAlive() && round < MAX_ROUNDS) {
            round++;
            result.addLine("\n-- Round " + round + " --");

            // Team A attacks Team B
            result.addLine(teamA.getName() + " attacks " + teamB.getName() + ":");
            boolean critA = random.nextInt(100) < 10;
            if (critA) result.addLine("  ** CRITICAL STRIKE! **");
            teamASkill.cast(teamB);
            result.addLine("  " + teamB.getName() + " HP after: " + teamB.getHealth());

            // Team B attacks Team A (only if still alive)
            if (teamB.isAlive()) {
                result.addLine(teamB.getName() + " attacks " + teamA.getName() + ":");
                boolean critB = random.nextInt(100) < 10;
                if (critB) result.addLine("  ** CRITICAL STRIKE! **");
                teamBSkill.cast(teamA);
                result.addLine("  " + teamA.getName() + " HP after: " + teamA.getHealth());
            }
        }

        result.setRounds(round);
        if (teamA.isAlive() && !teamB.isAlive()) {
            result.setWinner(teamA.getName());
        } else if (teamB.isAlive() && !teamA.isAlive()) {
            result.setWinner(teamB.getName());
        } else {
            result.setWinner("Draw");
        }
        result.addLine("\n=== Battle over after " + round + " round(s). Winner: " + result.getWinner() + " ===");
        return result;
    }
}
