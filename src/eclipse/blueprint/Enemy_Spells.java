//Eclipse Evermore -> eclipse.blueprint -> Enemy_Spells.java
//Kealdor created Enemy_Spells.java on 10-28-2023 at 9:15:10 PM
package eclipse.blueprint;

import java.io.IOException;

import eclipse.core;

public class Enemy_Spells extends Spells {

    int rank_casted;

    void Spell_Crit_Roll(Enemy enemy) throws IOException {
        int crit_roll = core.Dice_Parser("1d100");
        //approximately we just want crits like a third of the time for Enemy
        if (crit_roll >= 66) {
            crit = true;
            core.log("Combat - Enemy Spell Crit roll", enemy.name
                    + " has just rolled to see if they crit casting " + spell_name);
        } else {
            crit = false;
        }

        if (crit = true) {
            spell_value = Spell_Crit_Value(enemy);
            core.log("Combat - Enemy Spell Crit", enemy.name
                    + " has casted a crit " + spell_name);
        }
    }

    int Spell_Crit_Value(Enemy enemy) throws IOException {
        spell_value = spell_value * 2;

        core.log("Combat - Enemy Spell Crit amount", enemy.name
                + " has a value of " + spell_value + " for " + spell_name);

        return spell_value;
    }

    void set_caster_lvl(Enemy enemy, Player player) throws IOException {
        caster_level = player.level;

        core.log("Combat - non_pre_alpha_fight", enemy.name
                + " has just had their caster level set to " + caster_level
                + " which is " + player.name + "s level");

    }

    void set_spell_rank() throws IOException {
        rank_casted = core.Dice_Parser("1d4");
    }

    void set_spell_value(int amt, int caster_level) {
        spell_value = amt + caster_level;
    }

    void heal_value_log(Enemy enemy) throws IOException {
        if (crit == false) {
            core.log("Combat", enemy.name
                    + " has just been healed for " + spell_value + " from "
                    + spell_name);
        }

        if (crit == true) {
            core.log("Combat", enemy.name
                    + " has just been critical healed for " + spell_value + " from "
                    + spell_name);
        }

        core.log("Combat", enemy.name
                + " has " + enemy.get_chp() + " of "
                + enemy.get_mhp() + " hp");
    }

    void combat_spell_damage_log(Player player, Enemy enemy) throws IOException {
        player.set_chp(spell_value);

        if (crit == false) {
            core.log("Combat", enemy.name
                    + " has just dealt " + spell_value + " damage from "
                    + spell_name + " to " + player.name);
        }

        if (crit == true) {
            core.log("Combat", enemy.name
                    + " has just dealt " + spell_value + " critical damage from "
                    + spell_name + " to " + player.name);
        }

        core.log("Combat", player.name
                + " has " + player.get_chp() + " hit points remaining of "
                + player.get_mhp());
    }

    public void E_Cure(Enemy enemy, Player player) throws IOException {
        set_spell_rank();
        spell_name = "Enemy Cure - rank " + rank_casted;
        set_caster_lvl(enemy, player);

        switch (rank_casted) {
            case 1:
                set_spell_value(3 + (player.get_wis() / 2), caster_level);
                break;
            case 2:
                set_spell_value(6 + (player.get_wis() / 2), caster_level);
                break;
            case 3:
                set_spell_value(10 + (player.get_wis() / 2), caster_level);
                break;
            case 4:
                set_spell_value(12 + (player.get_wis() / 2), caster_level);
                break;
        }

        Spell_Crit_Roll(enemy);

        enemy.set_chp(spell_value);

        if (enemy.get_chp() > enemy.get_mhp()) {
            enemy.set_chp(enemy.get_mhp());
            heal_value_log(enemy);
        }
    }

    public void E_Fire(Player player, Enemy enemy) throws IOException {
        set_spell_rank();
        spell_name = "Enemy Fire - rank " + rank_casted;
        set_caster_lvl(enemy, player);

        switch (rank_casted) {
            case 1:
                set_spell_value(6, caster_level);
                break;
            case 2:
                set_spell_value(8 + (player.get_iq() / 2), caster_level);
                break;
            case 3:
                set_spell_value(10 + (player.get_iq() / 2), caster_level);
                break;
            case 4:
                set_spell_value(12 + (player.get_iq() / 2), caster_level);
                break;
            case 5:
                set_spell_value(12 + player.get_iq(), caster_level);
                break;
        }
        Spell_Crit_Roll(enemy);

        combat_spell_damage_log(player, enemy);
    }
}
