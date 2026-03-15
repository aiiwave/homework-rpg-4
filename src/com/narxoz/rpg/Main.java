package com.narxoz.rpg;

import com.narxoz.rpg.battle.RaidEngine;
import com.narxoz.rpg.battle.RaidResult;
import com.narxoz.rpg.bridge.*;
import com.narxoz.rpg.composite.*;

public class Main {
    public static void main(String[] args) {
        demoBridge();
        demoComposite();
        demoRaid();
    }

    static void demoBridge() {
        System.out.println("BRIDGE PATTERN");

        EffectImplementor physical = new PhysicalEffect();
        EffectImplementor fire     = new FireEffect();
        EffectImplementor ice      = new IceEffect();
        EffectImplementor shadow   = new ShadowEffect();

        System.out.println("\n[Same skill 'Slash'/4 different effects]");
        Skill[] slashes = {
            new SingleTargetSkill("Slash", 20, physical),
            new SingleTargetSkill("Slash", 20, fire),
            new SingleTargetSkill("Slash", 20, ice),
            new SingleTargetSkill("Slash", 20, shadow),
        };
        for (Skill s : slashes)
            System.out.printf("  %-8s + %-10s -> %d dmg%n",
                s.getSkillName(), s.getEffectName(), s.resolvedDamagePublic());

        System.out.println("\n[Same effect 'Shadow' — SingleTarget vs Area (base 30)]");
        Skill sSingle = new SingleTargetSkill("Dark Bolt", 30, shadow);
        Skill sArea   = new AreaSkill(        "Dark Nova",  30, shadow);
        System.out.printf("  SingleTarget %-10s -> %d dmg%n", shadow.getEffectName(), sSingle.resolvedDamagePublic());
        System.out.printf("  Area         %-10s -> %d dmg (per target)%n", shadow.getEffectName(), sArea.resolvedDamagePublic());

        System.out.println("\n[Full combination matrix: 2 skill types × 4 effects]");
        System.out.printf("  %-14s %-10s %-12s %-12s%n", "Skill", "Effect", "SingleTarget", "Area");
        System.out.println("  " + "─".repeat(52));
        EffectImplementor[] effects = {physical, fire, ice, shadow};
        for (EffectImplementor ef : effects) {
            Skill st = new SingleTargetSkill("Strike", 25, ef);
            Skill ar = new AreaSkill("Storm",  25, ef);
            System.out.printf("  %-14s %-10s %-12d %-12d%n",
                "Strike/Storm", ef.getEffectName(),
                st.resolvedDamagePublic(), ar.resolvedDamagePublic());
        }
    }

    static void demoComposite() {
        System.out.println("\nCOMPOSITE PATTERN");

        HeroUnit warrior  = new HeroUnit("Arthas",    160, 35);
        HeroUnit paladin  = new HeroUnit("Uther",     130, 25);
        HeroUnit mage     = new HeroUnit("Jaina",      90, 50);
        HeroUnit ranger   = new HeroUnit("Sylvanas",  110, 40);
        HeroUnit priest   = new HeroUnit("Anduin",     80, 20);

        PartyComposite frontHeroes = new PartyComposite("Front Line");
        frontHeroes.add(warrior);
        frontHeroes.add(paladin);

        PartyComposite backHeroes = new PartyComposite("Back Line");
        backHeroes.add(mage);
        backHeroes.add(ranger);
        backHeroes.add(priest);

        RaidGroup heroes = new RaidGroup("Alliance Raid");
        heroes.add(frontHeroes);
        heroes.add(backHeroes);

        EnemyUnit goblin1  = new EnemyUnit("Goblin Scout",    60, 18);
        EnemyUnit goblin2  = new EnemyUnit("Goblin Shaman",   55, 22);
        EnemyUnit orc1     = new EnemyUnit("Orc Warrior",    130, 28);
        EnemyUnit orc2     = new EnemyUnit("Orc Berserker",  110, 35);
        EnemyUnit troll    = new EnemyUnit("Troll Witch",     90, 30);
        EnemyUnit warchief = new EnemyUnit("Warchief",        200, 45);

        PartyComposite goblins = new PartyComposite("Goblin Pack");
        goblins.add(goblin1);
        goblins.add(goblin2);

        PartyComposite orcs = new PartyComposite("Orc Clan");
        orcs.add(orc1);
        orcs.add(orc2);

        RaidGroup horde = new RaidGroup("Horde Vanguard");
        horde.add(goblins);
        horde.add(orcs);

        PartyComposite elites = new PartyComposite("Elite Guard");
        elites.add(troll);
        elites.add(warchief);

        RaidGroup enemies = new RaidGroup("Horde Raid");
        enemies.add(horde);   
        enemies.add(elites);

        System.out.println("\n[Hero team hierarchy]");
        heroes.printTree("");

        System.out.println("\n[Enemy team hierarchy — 3 levels deep]");
        enemies.printTree("");

        System.out.printf("%nAlliance — HP: %d, ATK: %d%n", heroes.getHealth(), heroes.getAttackPower());
        System.out.printf("Horde    — HP: %d, ATK: %d%n", enemies.getHealth(), enemies.getAttackPower());

        System.out.println("\n[AOE 'Ice Storm' cast on Goblin Pack composite]");
        Skill iceStorm = new AreaSkill("Ice Storm", 30, new IceEffect());
        iceStorm.cast(goblins);
        System.out.println("Goblins alive: " + goblins.isAlive() + "  HP remaining: " + goblins.getHealth());
    }

    static void demoRaid() {
        System.out.println("\nRAID SIMULATION");

        HeroUnit warrior = new HeroUnit("Arthas",   160, 35);
        HeroUnit mage    = new HeroUnit("Jaina",     90, 50);
        HeroUnit ranger  = new HeroUnit("Sylvanas", 110, 40);

        PartyComposite frontH = new PartyComposite("Front Line");
        frontH.add(warrior);
        PartyComposite backH = new PartyComposite("Back Line");
        backH.add(mage);
        backH.add(ranger);
        RaidGroup heroes = new RaidGroup("Alliance");
        heroes.add(frontH);
        heroes.add(backH);

        EnemyUnit orc      = new EnemyUnit("Orc Warrior",   130, 28);
        EnemyUnit troll    = new EnemyUnit("Troll Witch",    90, 30);
        EnemyUnit warchief = new EnemyUnit("Warchief",       180, 42);

        PartyComposite vanguard = new PartyComposite("Vanguard");
        vanguard.add(orc);
        vanguard.add(troll);
        PartyComposite command = new PartyComposite("Command");
        command.add(warchief);
        RaidGroup enemies = new RaidGroup("Horde");
        enemies.add(vanguard);
        enemies.add(command);

        Skill heroSkill  = new AreaSkill(        "Shadow Storm", 22, new ShadowEffect());
        Skill enemySkill = new SingleTargetSkill("Flame Strike", 25, new FireEffect());

        RaidEngine engine = new RaidEngine().setRandomSeed(42L);
        RaidResult result = engine.runRaid(heroes, enemies, heroSkill, enemySkill);

        System.out.println();
        for (String line : result.getLog())
            System.out.println(line);

        System.out.println("\n  Winner : " + result.getWinner());
        System.out.println("  Rounds : " + result.getRounds());
    }
}
