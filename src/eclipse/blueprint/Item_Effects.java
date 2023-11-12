//Eclipse Evermore -> eclipse.blueprint -> Item_Effects.java
//Kealdor created Item_Effects.java on 10-28-2023 at 8:01:47 PM
package eclipse.blueprint;

import java.io.IOException;

import eclipse.core;

public class Item_Effects {
	public int Elem_Atk_Parser(String s) throws IOException {
        int result = 0;

        int dice_count = Integer.parseInt((s.substring(0, (s.indexOf("d")))));

        if (dice_count > 0) {
            core.log("elem attack dice roll - dice count", "About to roll " + s);
            core.Dice_Parser(s);
        } else {
            result = Integer.parseInt((s.substring((s.indexOf("d") + 1), s.length())));
        }

        return result;
    }

    public void Vampiric_Regen_Calc(Player player, int amt) {//amt will always be player damage total
        player.set_chp(amt);
    }

    public void Insta_Death(Enemy enemy) {//will always kill enemy
        enemy.set_chp(0 - enemy.get_mhp());
    }

    public void Vorpal(Player player, Enemy enemy) throws IOException {
        int vorpal_roll = core.Dice_Parser("1d20");
        core.log("vorpal roll info ", "the roll for checking if vorpal triggers was a " + vorpal_roll);

        if (vorpal_roll == 20) {
            core.log("vorpal", "triggers for insta killing");
            if ("long sword".equals(player.item.weapon_name) || "great sword".equals(player.item.weapon_name)) {
                Insta_Death(enemy);
            }
            if ("long sword".equals(player.item.weapon_name2) && player.item.equipt_weapon2 > 0) {
                Insta_Death(enemy);
            }
        } else {
            core.log("vorpal", "did not trigger for insta killing");
//6d8 if it doesnt insta kill according to dnd 5.0
            int v_dmg = core.Dice_Parser("6d8");
            core.log("vorpal", enemy.name + " will take " + v_dmg + " damage instead");
            enemy.set_chp(0 - v_dmg);

        }
    }

    public void Elemental_Attack(Player player, Enemy enemy) throws IOException {
        Element_Attack_Calc(player, enemy, "fire");
    }

    void Element_Attack_Calc(Player player, Enemy enemy, String element) throws IOException {
        switch (element) {
            case "fire":
                enemy.set_chp(enemy.get_chp() - Elem_Atk_Parser(player.item.w1_f_dmg_dice));

                if (player.item.equipt_weapon2 > 0) {
                    enemy.set_chp(enemy.get_chp() - Elem_Atk_Parser(player.item.w2_f_dmg_dice));
                }
                break;
        }

        Vampiric_Regen_Calc(player, Elem_Atk_Parser(player.item.w1_f_dmg_dice));
        if (player.item.equipt_weapon2 > 0) {
            Vampiric_Regen_Calc(player, Elem_Atk_Parser(player.item.w2_f_dmg_dice));
        }
    }

    public void Elemental_Guard(Player player, Enemy enemy) throws IOException {
        Elemental_Guard_Calc(player, enemy, "fire");
    }

    void Elemental_Guard_Calc(Player player, Enemy enemy, String element) throws IOException {
        switch (element) {
            case "fire":
                enemy.set_chp(enemy.get_chp() - core.Dice_Parser(player.item.shield_f_guard_dice) - core.Dice_Parser(player.item.armor_f_guard_dice));
                break;
        }
    }
}


