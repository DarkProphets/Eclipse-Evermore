//Eclipse Evermore -> eclipse.blueprint -> Player_Spells.java
//Kealdor created Player_Spells.java on 10-28-2023 at 10:19:53 PM
package eclipse.blueprint;

import java.io.IOException;

import eclipse.core;

public class Player_Spells extends Spells {
	public int spell_cost;
    public int buff_rank;

    public boolean protect;
    public boolean shell;
    public boolean regen;
    public boolean tmp_str;
    //public boolean dbl_stat;

    public int protect_turn;
    public int shell_turn;
    public int regen_turn;
    public int tmp_str_turn;
    //public int dbl_stat_turn;

    //needed for additional comparsions during caster level assignment for both spell systems
    public int compare1;
    public int compare2;
    //needed for magic attack
    public boolean spell_sword_spell_cost_override;

    public void buff_timers_update(Player player) throws IOException {
        effect_update("protect");
        effect_update("shell");
        effect_update("regen");
        effect_update("tmp_str");
        //effect_gain(player,"dbl_stat");

        effect_removal(player, "protect");
        effect_removal(player, "shell");
        effect_removal(player, "regen");
        effect_removal(player, "tmp_str");
        //effect_removal(player, "dbl_stat");

        //heals for regen every time - if its active else it just adds 0 to player hp
        player.set_chp(player.tmp_hp_bonus);
    }

    void effect_update(String spell) throws IOException {
        core.log("Combat - Buff", spell + " is about to have its timer increased by 1");

        if (protect == true && "protect".equals(spell)) {
            protect_turn++;
        }
        if (shell = true && "shell".equals(spell)) {
            shell_turn++;
        }

        if (regen = true && "regen".equals(spell)) {
            regen_turn++;
        }

        if (tmp_str = true && "tmp_str".equals(spell)) {
            tmp_str_turn++;
        }

//        if (dbl_stat = true && "dbl_stat".equals(spell)) {
//            dbl_stat_turn++;
//        }
        core.log("Buff Timers",
                System.lineSeparator()
                + "Protect timer is " + protect_turn
                + System.lineSeparator()
                + "Shell timer is " + shell_turn
                + System.lineSeparator()
                + "Regen timer is " + regen_turn
                + System.lineSeparator()
                + "Tmp_Str timer is " + tmp_str_turn
                + System.lineSeparator()
                //+ "Double Stat timer is " + dbl_stat_turn
                //+ System.lineSeparator()
        );
    }

    void effect_removal(Player player, String spell) throws IOException {
        if (protect == true && "protect".equals(spell) && protect_turn > 3) {
            protect_turn = 0;
            protect = false;
            player.damage_reduction = player.damage_reduction + player.protect_ac_bonus;
            player.protect_ac_bonus = 0;

            core.log("Combat - Buff", "Protect" + " has worn off from" + player.name
                    + " leaving them with " + player.damage_reduction + " damage reduction");
        }

        if (shell = true && "shell".equals(spell) && shell_turn > 3) {
            shell_turn = 0;
            shell = false;
        }

        if (regen = true && "regen".equals(spell) && regen_turn > 3) {
            regen_turn = 0;
            regen = false;
            player.tmp_hp_bonus = 0;
        }

        if (tmp_str = true && "tmp_str".equals(spell) && tmp_str_turn > 3) {
            tmp_str_turn = 0;
            tmp_str = false;
            player.set_str(-player.tmp_str_bonus);
            player.set_dmg();
        }

//        if (dbl_stat = true && "dbl_stat".equals(spell) && dbl_stat_turn > 3) {
//            dbl_stat_turn = 0;
//            dbl_stat = false;
//        }
        core.log("Combat - Buff", spell + " has ended");
    }

    public void buff_activate(Player player, String spell, int rank) throws IOException {
        if (protect == true) {
            Protect(player, rank);
        }

        if (shell = true) {
            shell_turn = 1;
        }

        if (regen = true) {
            Regen(player, rank);
        }

        if (tmp_str = true) {
            Tmp_Str(player, rank);
        }

//        if (dbl_stat = true) {
//            dbl_stat_turn++;
//        }
        core.log("Combat - Buff", spell + " has been started");
    }

    void set_spell_cost(int x) throws IOException {
        spell_cost = 0 - x;

        if (spell_sword_spell_cost_override == false) {
            core.log("Combat", " spell cost is set to " + spell_cost);
        }
    }

    void set_spell_value(int base_value, int casted_lvl) throws IOException {
        spell_value = 0;
        core.log("Combat", " spell value now reset to 0 for next spell");
        spell_value = base_value + casted_lvl;
    }

    void set_arena_spells(Player player, String spell) throws IOException {
        if (core.pre_alpha_fight == 1) {
            int x = Math.max(player.wmage_lvl, player.bmage_lvl);
            x = Math.max(player.wiz_lvl, x);
            //added for comparaions for dark knight and paladin to the already existing comparsion that was in place now they exist
            compare1 = Math.max(x, player.dk_lvl);
            compare2 = Math.max(x, player.pal_lvl);
            x = Math.max(compare1, compare2);

            caster_level = player.get_level() - 2 + x;

            core.log("Combat - pre_alpha_fight", player.name
                    + " has just had their caster level set to " + caster_level);

            switch (spell) {
                case "cure":
                    if (player.wiz_lvl >= 1) {
                        set_spell_cost(3);
                        spell_value = 20;
                        spell_name = "Arena - Vare Cure";
                    } else {
                        set_spell_cost(5);
                        spell_value = 10;
                        spell_name = "Arena - Cure";
                    }
                    break;
                case "cura":
                    if (player.wiz_lvl >= 5) {
                        set_spell_cost(7);
                        spell_value = 40;
                        spell_name = "Arena - Vare Cura";
                    } else {
                        set_spell_cost(10);
                        spell_value = 20;
                        spell_name = "Arena - Cura";
                    }
                    break;
                case "curaga":
                    if (player.wmage_lvl >= 9) {
                        set_spell_cost(15);
                        spell_value = 30;
                        spell_name = "Arena - Curaga";
                    } else {
                        set_arena_spells(player, "cura");
                    }
                    break;
                case "protect":
                    if (player.wiz_lvl >= 3) {
                        set_spell_cost(4);
                        spell_value = 6;
                        spell_name = "Arena - Vare Protect";
                    } else {
                        set_spell_cost(7);
                        spell_value = 3;
                        spell_name = "Arena - Protect";
                    }
                    break;
                case "harm":
                    if (player.bmage_lvl >= 6) {
                        set_spell_cost(24);
                        spell_value = 30;
                        spell_name = "Arena - Harm";
                    } else {
                        set_spell_cost(7);
                        set_spell_value(3 + (player.get_iq() / 2), caster_level);
                        set_spell_cost(3);
                        spell_name = "Arena - Lesser Harm";
                    }
                    break;
                case "fire":
                    if (player.wiz_lvl >= 1) {
                        set_spell_cost(5);
                        spell_value = 20;
                        spell_name = "Arena - Vare Fire";
                    } else {
                        set_spell_cost(7);
                        spell_value = 10;
                        spell_name = "Arena - Fire";
                    }
                    break;
                case "fira":
                    if (player.wiz_lvl >= 4) {
                        set_spell_cost(9);
                        spell_value = 40;
                        spell_name = "Arena - Vare Fira";
                    } else {
                        set_spell_cost(14);
                        spell_value = 20;
                        spell_name = "Arena - Fira";
                    }
                    break;
                case "firaga":
                    if (player.bmage_lvl >= 8) {
                        set_spell_cost(21);
                        spell_value = 30;
                        spell_name = "Arena - Firaga";
                    } else {
                        set_arena_spells(player, "fira");
                    }
                    break;
                case "blizzard":
                    if (player.wiz_lvl >= 2) {
                        set_spell_cost(5);
                        spell_value = 22;
                        spell_name = "Arena - Vare Blizzard";
                    } else {
                        set_spell_cost(8);
                        spell_value = 11;
                        spell_name = "Arena - Blizzard";
                    }
                    break;
                case "blizzara":
                    if (player.wiz_lvl >= 8) {
                        set_spell_cost(11);
                        spell_value = 44;
                        spell_name = "Arena - Vare Blizzara";
                    } else {
                        set_spell_cost(16);
                        spell_value = 22;
                        spell_name = "Arena - Blizzara";
                    }
                    break;
                case "blizzaga":
                    if (player.bmage_lvl >= 9) {
                        set_spell_cost(24);
                        spell_value = 33;
                        spell_name = "Arena - Blizzaga";
                    } else {
                        set_arena_spells(player, "blizzara");
                    }
                    break;
                case "thunder":
                    if (player.wiz_lvl >= 3) {
                        set_spell_cost(6);
                        spell_value = 24;
                        spell_name = "Arena - Vare Thunder";
                    } else {
                        set_spell_cost(9);
                        spell_value = 12;
                        spell_name = "Arena - Thunder";
                    }
                    break;
                case "thundera":
                    if (player.bmage_lvl >= 7) {
                        set_spell_cost(18);
                        spell_value = 24;
                        spell_name = "Arena - Thundera";
                    } else {
                        set_arena_spells(player, "thunder");
                    }
                    break;
                case "thunderaga":
                    if (player.bmage_lvl >= 10) {
                        set_spell_cost(27);
                        spell_value = 36;
                        spell_name = "Arena - Thunderaga";
                    } else {
                        set_arena_spells(player, "thundera");
                    }
                    break;
            }
        }
    }

    int set_caster_level(Player player, String magic_type) throws IOException {
        if ("white".equals(magic_type)) {
            compare1 = Math.max(player.wmage_lvl, player.wiz_lvl);
            compare2 = Math.max(player.pal_lvl, player.wiz_lvl);
            caster_level = Math.max(compare1, compare2);
        }

        if ("white_mage_only".equals(magic_type)) {
            caster_level = player.wmage_lvl;
        }

        if ("black".equals(magic_type)) {
            compare1 = Math.max(player.bmage_lvl, player.wiz_lvl);
            compare2 = Math.max(player.dk_lvl, player.wiz_lvl);
            caster_level = Math.max(compare1, compare2);
        }

        if ("black_mage_only".equals(magic_type)) {
            caster_level = player.bmage_lvl;
        }

        core.log("Combat - non_pre_alpha_fight", player.name
                + " has just had their caster level set to " + caster_level);

        return caster_level;
    }

    void Spell_Crit_Roll(Player player) throws IOException {
        int crit_roll = core.Dice_Parser("1d100");
        //approximately we just want crits like a 4th of the time
        if (crit_roll >= 75) {
            crit = true;
            core.log("Combat - Spell Crit roll", player.name
                    + " has just rolled to a crit for " + spell_name);
        }

        if (crit = true) {
            spell_value = Spell_Crit_Value(player);
            core.log("Combat - Spell Crit", player.name
                    + " has casted a crit " + spell_name);
        }
    }

    int Spell_Crit_Value(Player player) throws IOException {
        spell_value = spell_value * 2;

        core.log("Combat - Spell Crit amount", player.name
                + " has a value of " + spell_value + " for " + spell_name);

        return spell_value;
    }

    void Spell_Sword_Cost_Override() throws IOException {
        if (spell_sword_spell_cost_override == true && core.pre_alpha_fight == 0) {
            set_spell_cost(spell_cost / 2);
            core.log("Combat - Spell Sword Command", " spell cost is set to " + spell_cost + " for random encounter");
        }

        if (spell_sword_spell_cost_override == true && core.pre_alpha_fight == 1) {
            set_spell_cost(spell_cost / 4);
            core.log("Combat - Spell Sword Command", " spell cost is set to " + spell_cost + " for arena fights");
        }
    }

    void heal_value_log(Player player) throws IOException {
        if (crit == false) {
            core.log("Combat", player.name
                    + " has just been healed for " + spell_value + " from "
                    + spell_name);
        }

        if (crit == true) {
            core.log("Combat", player.name
                    + " has just been critical healed for " + spell_value + " from "
                    + spell_name);
        }
    }

    void total_hp_log(Player player) throws IOException {
        core.log("Combat", player.name
                + " has " + player.get_chp() + " of "
                + player.get_mhp() + " hp");
    }

    void total_mp_log(Player player) throws IOException {
        core.log("Combat", player.name
                + " has " + player.get_cmp() + " of "
                + player.get_mhp() + " mp");
    }

    void used_mp_log(Player player) throws IOException {
        core.log("Combat", player.name
                + " has just used " + spell_cost + " mana for "
                + spell_name);
    }

    void failed_cast_log(Player player) throws IOException {
        core.log("Combat", player.name
                + " does not have " + spell_cost + " mana to cast "
                + spell_name);
        core.log("Combat", player.name
                + " only has " + player.get_cmp() + " mana");
    }

    void combat_spell_damage_log(Player player, Enemy enemy) throws IOException {
        if (crit == false) {
            core.log("Combat", player.name
                    + " has just dealt " + spell_value + " damage from "
                    + spell_name + " to " + enemy.name);
        }

        if (crit == true) {
            core.log("Combat", player.name
                    + " has just dealt " + spell_value + " critical damage from "
                    + spell_name + " to " + enemy.name);
        }

        core.log("Combat", enemy.name
                + " has " + enemy.get_chp() + " hit points remaining of "
                + enemy.get_mhp());
    }

    void Damage_Spell_Cast(Player player, Enemy enemy) throws IOException {
        Spell_Crit_Roll(player);

        if (player.get_cmp() >= spell_cost) {
            enemy.set_chp(0 - spell_value);
            combat_spell_damage_log(player, enemy);

            player.set_cmp(spell_cost);
            used_mp_log(player);
            total_mp_log(player);
        } else {
            failed_cast_log(player);
        }
    }

    void Heal_Spell_Cast(Player player) throws IOException {
        Spell_Crit_Roll(player);

        if (player.get_cmp() >= spell_cost) {
            player.set_chp(spell_value);
            heal_value_log(player);

            total_hp_log(player);

            player.set_cmp(spell_cost);
            used_mp_log(player);
            total_mp_log(player);
        } else {
            failed_cast_log(player);
        }
    }

    public void Cure(Player player, int rank_casted) throws IOException {
        if (core.pre_alpha_fight == 1) {
            set_arena_spells(player, "cure");
        } else {
            spell_name = "Cure - rank " + rank_casted;
            caster_level = set_caster_level(player, "white");

            switch (rank_casted) {
                case 1:
                    set_spell_value(3, caster_level);
                    set_spell_cost(2);
                    break;
                case 2:
                    set_spell_value(6, caster_level);
                    spell_value = spell_value + (player.get_wis() / 2);
                    set_spell_cost(4);
                    break;
                case 3:
                    set_spell_value(10, caster_level);
                    spell_value = spell_value + (player.get_wis() / 2);
                    set_spell_cost(6);
                    break;
                case 4:
                    set_spell_value(12, caster_level);
                    spell_value = spell_value + (player.get_wis() / 2);
                    set_spell_cost(8);
                    break;
                case 5:
                    set_spell_value(12, caster_level);
                    spell_value = spell_value + player.get_wis();
                    set_spell_cost(10);
                    break;
            }
        }
        Heal_Spell_Cast(player);
    }

    public void Cura(Player player) throws IOException {
        if (core.pre_alpha_fight == 1) {
            set_arena_spells(player, "cura");
        } else {
            spell_name = "Cura";
            set_spell_value(20, set_caster_level(player, "white"));
            set_spell_cost(16);
        }
        Heal_Spell_Cast(player);
    }

    public void Curaga(Player player) throws IOException {
        if (core.pre_alpha_fight == 1) {
            set_arena_spells(player, "curaga");
        } else {
            spell_name = "Curaga";
            set_spell_value(30, set_caster_level(player, "white_mage_only"));
            set_spell_cost(15);
        }
        Heal_Spell_Cast(player);
    }

    public void Protect(Player player, int rank) throws IOException {
        if (core.pre_alpha_fight == 1) {
            set_arena_spells(player, "protect");
        } else {
            spell_name = "protect";

            if (protect == true && protect_turn == 0) {
                effect_update("protect");
            }

            if (protect_turn == 1) {
                switch (rank) {
                    case 1:
                        set_spell_value(2, set_caster_level(player, "white"));
                        break;
                    case 2:
                        set_spell_value(4, set_caster_level(player, "white"));
                        break;
                    case 3:
                        set_spell_value(6, set_caster_level(player, "white"));
                        break;
                }

                set_spell_cost(15);

                if (player.get_cmp() >= spell_cost) {
                    player.set_cmp(spell_cost);
                    used_mp_log(player);
                    total_mp_log(player);

                    Spell_Crit_Roll(player);

                    player.protect_ac_bonus = spell_value;
                    core.log("Combat - Buff", spell_name + " has been casted at rank " + buff_rank
                            + "by " + player.name + " which gives a bonus of " + spell_value + " to damage reduction");

                    player.damage_reduction = player.damage_reduction + player.protect_ac_bonus;
                    core.log("Combat - Buff stat mod", player.name + " now temparily has a damage reduction of "
                            + player.damage_reduction + " for 3 turns");
                } else {
                    failed_cast_log(player);
                }
            }
        }
    }

    public void Regen(Player player, int rank) throws IOException {
        spell_name = "regen";

        if (regen == true && regen_turn == 0) {
            effect_update("regen");
        }

        if (regen_turn == 1) {
            switch (rank) {
                case 1:
                    set_spell_value(8, set_caster_level(player, "white_mage_only"));
                    break;
                case 2:
                    set_spell_value(16, set_caster_level(player, "white_mage_only"));
                    break;
                case 3:
                    set_spell_value(24, set_caster_level(player, "white_mage_only"));
                    break;
            }

            Spell_Crit_Roll(player);

            player.tmp_hp_bonus = spell_value;
            core.log("Combat - Buff", spell_name + " has been casted"
                    + "by " + player.name + " which gives a heal of " + spell_value);

            core.log("Combat - Buff stat mod", player.name + " now temparily has a on going heal of "
                    + spell_value + " for 3 turns");
        }
    }

    public void Tmp_Str(Player player, int rank) throws IOException {
        spell_name = "tmp_str_buff";

        if (tmp_str == true && tmp_str_turn == 0) {
            effect_update("tmp_str");
        }

        if (tmp_str_turn == 1) {
            switch (rank) {
                case 1:
                    set_spell_value(2, set_caster_level(player, "white"));
                    break;
                case 2:
                    set_spell_value(4, set_caster_level(player, "white"));
                    break;
                case 3:
                    set_spell_value(6, set_caster_level(player, "white"));
                    break;
            }

            Spell_Crit_Roll(player);

            player.tmp_str_bonus = spell_value;
            core.log("Combat - Buff", spell_name + " has been casted"
                    + "by " + player.name + " which gives a str boost of " + spell_value);

            player.set_str(player.tmp_str_bonus);
            player.set_dmg();

            core.log("Combat - Buff stat mod", player.name + " now temparily has a str "
                    + player.get_str() + " for 3 turns");
            core.log("Combat - Buff stat mod", player.name + " will now temparily deal "
                    + player.get_dmg() + " damage while " + spell_name + " is active");
        }
    }

    public void Harm(Player player, Enemy enemy, int rank_casted) throws IOException {
        if (core.pre_alpha_fight == 1) {
            set_arena_spells(player, "harm");
        } else {
            spell_name = "Harm - rank " + rank_casted;
            caster_level = set_caster_level(player, "black_mage_only");

            switch (rank_casted) {
                //using rank 1 for the else statment of arena spell version
                case 1:
                    set_spell_value(3 + (player.get_iq() / 2), caster_level);
                    set_spell_cost(3);
                    break;
                case 2:
                    set_spell_value(6 + (player.get_iq() / 2), caster_level);
                    set_spell_cost(5);
                    break;
                case 3:
                    set_spell_value(9 + (player.get_iq() / 2), caster_level);
                    set_spell_cost(8);
                    break;
                case 4:
                    set_spell_value(12 + (player.get_iq() / 2), caster_level);
                    set_spell_cost(11);
                    break;
                case 5:
                    set_spell_value(15 + player.get_iq(), caster_level);
                    set_spell_cost(14);
                    break;
            }
        }
        Damage_Spell_Cast(player, enemy);
    }

    public void Fire(Player player, Enemy enemy, int rank_casted) throws IOException {
        if (core.pre_alpha_fight == 1) {
            set_arena_spells(player, "fire");
        } else {
            spell_name = "Fire - rank " + rank_casted;
            caster_level = set_caster_level(player, "black");

            switch (rank_casted) {
                case 1:
                    set_spell_value(6, caster_level);
                    set_spell_cost(3);
                    break;
                case 2:
                    set_spell_value(8 + (player.get_iq() / 2), caster_level);
                    set_spell_cost(6);
                    break;
                case 3:
                    set_spell_value(10 + (player.get_iq() / 2), caster_level);
                    set_spell_cost(10);
                    break;
                case 4:
                    set_spell_value(12 + (player.get_iq() / 2), caster_level);
                    set_spell_cost(14);
                    break;
                case 5:
                    set_spell_value(12 + player.get_iq(), caster_level);
                    set_spell_cost(16);
                    break;
            }
        }
        Spell_Sword_Cost_Override();
        Damage_Spell_Cast(player, enemy);
    }

    public void Fira(Player player, Enemy enemy) throws IOException {
        if (core.pre_alpha_fight == 1) {
            set_arena_spells(player, "fira");
        } else {
            spell_name = "Fira";
            caster_level = set_caster_level(player, "black");
            set_spell_value(20, caster_level);
            set_spell_cost(18);
        }
        Damage_Spell_Cast(player, enemy);
    }

    public void Firaga(Player player, Enemy enemy) throws IOException {
        if (core.pre_alpha_fight == 1) {
            set_arena_spells(player, "firaga");
        } else {
            spell_name = "Firaga";
            caster_level = set_caster_level(player, "black_mage_only");
            set_spell_value(20, caster_level);
            set_spell_cost(18);
        }
        Damage_Spell_Cast(player, enemy);
    }

    public void Blizzard(Player player, Enemy enemy, int rank_casted) throws IOException {
        if (core.pre_alpha_fight == 1) {
            set_arena_spells(player, "blizzard");
        } else {
            spell_name = "Blizzard - rank " + rank_casted;
            caster_level = set_caster_level(player, "black");

            switch (rank_casted) {
                case 1:
                    set_spell_value(8, caster_level);
                    set_spell_cost(3);
                    break;
                case 2:
                    set_spell_value(10 + (player.get_iq() / 2), caster_level);
                    set_spell_cost(6);
                    break;
                case 3:
                    set_spell_value(12 + (player.get_iq() / 2), caster_level);
                    set_spell_cost(10);
                    break;
                case 4:
                    set_spell_value(14 + (player.get_iq() / 2), caster_level);
                    set_spell_cost(14);
                    break;
                case 5:
                    set_spell_value(14 + player.get_iq(), caster_level);
                    set_spell_cost(16);
                    break;
            }
        }
        Damage_Spell_Cast(player, enemy);
    }

    public void Blizzara(Player player, Enemy enemy) throws IOException {
        if (core.pre_alpha_fight == 1) {
            set_arena_spells(player, "blizzara");
        } else {
            spell_name = "Blizzara";
            caster_level = set_caster_level(player, "black");
            set_spell_value(22, caster_level);
            set_spell_cost(20);
        }
        Damage_Spell_Cast(player, enemy);
    }

    public void Blizzaga(Player player, Enemy enemy) throws IOException {
        if (core.pre_alpha_fight == 1) {
            set_arena_spells(player, "blizzaga");
        } else {
            spell_name = "Blizzaga";
            caster_level = set_caster_level(player, "black_mage_only");
            set_spell_value(20, caster_level);
            set_spell_cost(18);
        }
        Damage_Spell_Cast(player, enemy);
    }

    public void Thunder(Player player, Enemy enemy, int rank_casted) throws IOException {
        if (core.pre_alpha_fight == 1) {
            set_arena_spells(player, "thunder");
        } else {
            spell_name = "Thunder - rank " + rank_casted;
            caster_level = set_caster_level(player, "black");

            switch (rank_casted) {
                case 1:
                    set_spell_value(10, caster_level);
                    set_spell_cost(4);
                    break;
                case 2:
                    set_spell_value(12 + (player.get_iq() / 2), caster_level);
                    set_spell_cost(7);
                    break;
                case 3:
                    set_spell_value(14 + (player.get_iq() / 2), caster_level);
                    set_spell_cost(11);
                    break;
                case 4:
                    set_spell_value(16 + (player.get_iq() / 2), caster_level);
                    set_spell_cost(15);
                    break;
                case 5:
                    set_spell_value(16 + player.get_iq(), caster_level);
                    set_spell_cost(19);
                    break;
            }
        }
        Damage_Spell_Cast(player, enemy);
    }

    public void Thundera(Player player, Enemy enemy) throws IOException {
        if (core.pre_alpha_fight == 1) {
            set_arena_spells(player, "thundera");
        } else {
            spell_name = "Thundera";
            caster_level = set_caster_level(player, "black");
            set_spell_value(24, caster_level);
            set_spell_cost(23);
        }
        Damage_Spell_Cast(player, enemy);
    }

    public void Thunderaga(Player player, Enemy enemy) throws IOException {
        if (core.pre_alpha_fight == 1) {
            set_arena_spells(player, "thunderaga");
        } else {
            spell_name = "Thunderaga";
            caster_level = set_caster_level(player, "black_mage_only");
            set_spell_value(30, caster_level);
            set_spell_cost(26);
        }
        Damage_Spell_Cast(player, enemy);
    }

    public void Poison(Player player, Enemy enemy, int rank_casted) throws IOException {
        spell_name = "Poison - rank " + rank_casted;
        caster_level = set_caster_level(player, "black");
        int spell_value_roll = core.Dice_Parser("1d10");

        switch (rank_casted) {
            case 1:
                set_spell_value(spell_value_roll, caster_level);
                break;
            case 2:
                set_spell_value(spell_value_roll + 2, caster_level);
                break;
            case 3:
                set_spell_value(spell_value_roll + 4, caster_level);
                break;
            case 4:
                set_spell_value(spell_value_roll + 6, caster_level);
                break;
            case 5:
                set_spell_value(spell_value_roll + player.get_iq(), caster_level);
                break;
        }
        set_spell_cost(spell_value - 3);
        Spell_Sword_Cost_Override();
        Damage_Spell_Cast(player, enemy);
    }

    public void Quake(Player player, Enemy enemy, int rank_casted) throws IOException {
        spell_name = "Quake - rank " + rank_casted;
        caster_level = set_caster_level(player, "black");
        int spell_value_roll = core.Dice_Parser("1d20");

        switch (rank_casted) {
            case 1:
                set_spell_value(spell_value_roll, caster_level);
                break;
            case 2:
                set_spell_value(spell_value_roll + (player.get_iq() / 2), caster_level);
                break;
            case 3:
                set_spell_value(spell_value_roll + player.get_iq(), caster_level);
                break;
        }
        set_spell_cost(spell_value - 7);
        Spell_Sword_Cost_Override();
        Damage_Spell_Cast(player, enemy);
    }

    public void Aero(Player player, Enemy enemy, int rank_casted) throws IOException {
        spell_name = "Aero - rank " + rank_casted;
        caster_level = set_caster_level(player, "black");
        int spell_value_roll = core.Dice_Parser("1d15");

        switch (rank_casted) {
            case 1:
                set_spell_value(spell_value_roll, caster_level);
                break;
            case 2:
                set_spell_value(spell_value_roll + (player.get_iq() / 4), caster_level);
                break;
            case 3:
                set_spell_value(spell_value_roll + (player.get_iq() / 3), caster_level);
                break;
            case 4:
                set_spell_value(spell_value_roll + (player.get_iq() / 2), caster_level);
                break;
            case 5:
                set_spell_value(spell_value_roll + player.get_iq(), caster_level);
                break;
        }
        set_spell_cost(spell_value - 5);
        Spell_Sword_Cost_Override();
        Damage_Spell_Cast(player, enemy);
    }

    public void Shell(Player player) throws IOException {
        spell_name = "shell";

        if (shell == true && shell_turn == 0) {
            effect_update("shell");
        }

        core.log("Combat - Buff", spell_name + " has been casted"
                + "by " + player.name + " which gives causes enemy to fail to magic attack them for 3 turns");

    }
}


