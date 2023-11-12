//Eclipse Evermore -> eclipse.blueprint -> items.java
//Kealdor created items.java on 10-28-2023 at 5:01:34 PM
package eclipse.blueprint;

import java.io.IOException;

import eclipse.core;

/*TODO: Setup platinium armor, plate armor, dragon shield, and mithril rod 
* 6 Magi Rod b_Dmg + 5
* 7 Oak Staff b_Dmg + 2
* 8 Holy Staff b_Dmg + 4
* 9 Hermit Staff b_Dmg + 6
 */
public class items {

    public Item_Effects item_effect = new Item_Effects();

    public int equipt_weapon = 0;
    public int equipt_weapon2 = 0;//used for dual wield weapon damage calculation

    public boolean dual_wield_dmg_flag;//used for two handed weapon and dual wield dmg calculations 

    public int weapon_dmg;
    protected int a_weapon_dmg;//used for arena and alpha fights
    protected int weapon2_dmg;//used for dual wield damage math accuracy
    protected int a_weapon2_dmg;//arena and alpha dual wield
    public String weapon_name;
    public String weapon_damage_dice;
    public String weapon_name2;
    public String weapon_damage_dice2;

    public int equipt_armor;
    protected int armor_def;

    public int equipt_shield;
    protected int shield_def;

    //item effect damage vars
    public String w1_f_dmg_dice;
    public String w2_f_dmg_dice;
    public String shield_f_guard_dice;
    public String armor_f_guard_dice;

    public String unarmed_gauntlet_f_dmg;
    public String dagger_f_dmg;
    public String rod_f_dmg;
    public String staff_f_dmg;
    public String shortsword_f_dmg;
    public String longsword_f_dmg;
    public String greatsword_f_dmg;
    public String ironstaff_f_dmg;
    public String ironrod_f_dmg;

    public boolean longsword_vorpal;
    public boolean greatsword_vorpal;

    public boolean b_unarmed_gauntlet_f_dmg;
    public boolean b_dagger_f_dmg;
    public boolean b_rod_f_dmg;
    public boolean b_staff_f_dmg;
    public boolean b_shortsword_f_dmg;
    public boolean b_longsword_f_dmg;
    public boolean b_greatsword_f_dmg;
    public boolean b_ironstaff_f_dmg;
    public boolean b_ironrod_f_dmg;

    //health potions
    public int s_hp_pot;//1/4th
    public int m_hp_pot;//1/3
    public int l_hp_pot;//2/3
    public int f_hp_pot;//full
    //mana potions
    public int s_mp_pot;//1/4th
    public int m_mp_pot;//1/3
    public int l_mp_pot;//2/3
    public int f_mp_pot;//full
    //weapons
    public int s_sword;
    public int l_sword;
    public int g_sword;
    public int dagger;
    public int w_staff;//wood staff
    public int i_staff;//iron staff
    public int rod;
    public int i_rod;//iron rod
    //armor
    public int robe;
    public int l_armor;
    public int m_armor;
    public int h_armor;
    public int s_armor;//scaled armor material
    public int d_armor;//dragon hide armor material
    //shields
    public int b_shield;//buckler
    public int s_shield;
    public int m_shield;
    public int l_shield;
    public int t_shield;//tower shield

    public void equip_armor() throws IOException {
        String armor_name;

        switch (equipt_armor) {
            default:
                armor_name = "none equipt";
                armor_def = 0;
                break;

            case 1:
                armor_name = "Robe";
                armor_def = 1;
                break;

            case 2:
                armor_name = "Light Armor";
                armor_def = 2;
                break;

            case 3:
                armor_name = "Medium Armor";
                armor_def = 3;
                break;

            case 4:
                armor_name = "Heavy Armor";
                armor_def = 4;
                break;

            case 5:
                armor_name = "Scale Armor";
                armor_def = 6;
                break;

            case 6:
                armor_name = "Dragonhide Armor";
                armor_def = 8;
                break;
        }

        core.log("armor defense info", "the equipt armor is "
                + armor_name + " and gives " + armor_def + " armor class");
    }

    public void equip_shield() throws IOException {
        String shield_name;
        switch (equipt_shield) {
            default:
                shield_name = "none equipt";
                shield_def = 0;
                break;

            case 1:
                shield_name = "Buckler";
                shield_def = 1;
                break;

            case 2:
                shield_name = "Small Shield";
                shield_def = 2;
                break;

            case 3:
                shield_name = "Medium Shield";
                shield_def = 3;
                break;

            case 4:
                shield_name = "Large Shield";
                shield_def = 4;
                break;

            case 5:
                shield_name = "Tower Shield";
                shield_def = 6;
                break;
        }

        core.log("shield defense info ", "the equipt shield is "
                + shield_name + " and gives " + shield_def + " armor class");
    }

    public void equip_weapon() throws IOException {
        switch (equipt_weapon) {
            default:
                weapon_name = "none";
                weapon_damage_dice = "1d0";
                break;
            case 1://dagger
                weapon_name = "dagger";
                break;
            case 2://rod
                weapon_name = "rod";
                break;
            case 3://quarter staff - 2h
                weapon_name = "quarterstaff";
                break;
            case 4://short sword
                weapon_name = "short sword";
                break;
            case 5://long sword
                weapon_name = "long sword";
                break;
            case 6://great sword - 2h
                weapon_name = "great sword";
                break;
            case 7://iron staff - 2h
                weapon_name = "iron staff";
                break;
            case 8://iron rod 
                weapon_name = "iron rod";
                break;
        }

        set_weapon_dmg(1, weapon_name);
        set_arena_weapon_dmg(1, weapon_name);

        core.log("weapon info slot 1",
                System.lineSeparator()
                + "the equipt weapon in slot 1 is " + weapon_name
                + System.lineSeparator()
                + weapon_name + " does " + weapon_damage_dice + " for weapon_dmg in random encounters"
                + System.lineSeparator()
                + weapon_name + " does " + a_weapon_dmg + "in arena encounters"
                + System.lineSeparator()
                + weapon_name + " does " + w1_f_dmg_dice + "Fire damage in both modes of encounters");

//dual wield damage section   
        if (dual_wield_dmg_flag == true && equipt_shield == 0) {
            core.log("weapon info - feat check", "dual wield is taken");

            switch (equipt_weapon2) {
                default:
                    weapon_name2 = "none equipt";
                    weapon_damage_dice2 = "1d0";
                    break;
                case 1://dagger
                    weapon_name2 = "dagger";
                    break;
                case 2://rod
                    weapon_name2 = "rod";
                    break;
                case 4://short sword
                    weapon_name2 = "short sword";
                    break;
                case 5://long sword
                    weapon_name2 = "long sword";
                    break;
                case 8://iron rod 
                    weapon_name2 = "iron rod";
                    break;
            }

            set_weapon_dmg(2, weapon_name2);
            set_arena_weapon_dmg(2, weapon_name2);

            core.log("weapon info slot 2",
                    System.lineSeparator()
                    + "the equipt weapon  in slot 2 is " + weapon_name2
                    + System.lineSeparator()
                    + weapon_name2 + " does " + weapon_damage_dice2 + " for weapon_dmg in random encounters"
                    + System.lineSeparator()
                    + weapon_name2 + " does " + a_weapon2_dmg + "in arena fights"
                    + System.lineSeparator()
                    + weapon_name2 + " does " + w2_f_dmg_dice + "Fire damage in both modes of encounters");
        }
    }

    public void set_weapon_dmg(int weapon_slot, String weapon) {
        String amt = null;

        switch (weapon) {
            case "dagger":
                amt = "1d4";
                break;
            case "rod":
                amt = "1d4";
                break;
            case "quarterstaff":
                amt = "1d6";
                break;
            case "short sword":
                amt = "1d6";
                break;
            case "long sword":
                amt = "1d8";
                break;
            case "great sword":
                amt = "2d6";
                break;
            case "iron staff":
                amt = "1d8";
                break;
            case "iron rod":
                amt = "1d8";
                break;
        }
        //2h weapons use slot 1
        if (weapon_slot == 1) {
            weapon_damage_dice = amt;
            set_elemental_dmg(1, weapon);
        }

        if (weapon_slot == 2) {
            weapon_damage_dice2 = amt;
            set_elemental_dmg(2, weapon);
        }
    }

    public void weapon_dmg_calc(int weapon_slot, int amt) {
        //2h weapons use slot 1
        if (weapon_slot == 1) {
            weapon_dmg = amt;
        }
        if (weapon_slot == 2) {
            weapon2_dmg = amt;
        }
    }

    public void set_arena_weapon_dmg(int weapon_slot, String weapon) throws IOException {
        int amt;

        switch (weapon) {
            case "dagger":
                amt = 2;
                break;

            case "rod":
                amt = 1;
                break;

            case "quarterstaff":
                amt = 3;
                break;

            case "short sword":
                amt = 3;
                break;

            case "long sword":
                amt = 5;
                break;

            case "great sword":
                amt = 8;
                break;

            case "iron staff":
                amt = 6;
                break;

            //this weapon didnt exist in Alpha so its going to use the same
            default:
                amt = core.Dice_Parser(weapon_damage_dice);
                break;
        }

        //2h weapons use slot 1
        if (weapon_slot == 1) {
            a_weapon_dmg = amt;
        }
        if (weapon_slot == 2) {
            a_weapon2_dmg = amt;
        }
    }

    public void set_elemental_dmg(int weapon_slot, String weapon) {
        if (weapon_slot == 1) {
            switch (weapon) {
                case "dagger":
                    w1_f_dmg_dice = dagger_f_dmg;
                    break;
                case "rod":
                    w1_f_dmg_dice = rod_f_dmg;
                    break;
                case "quarterstaff":
                    w1_f_dmg_dice = staff_f_dmg;
                    break;
                case "short sword":
                    w1_f_dmg_dice = shortsword_f_dmg;
                    break;
                case "long sword":
                    w1_f_dmg_dice = longsword_f_dmg;
                    break;
                case "great sword":
                    w1_f_dmg_dice = greatsword_f_dmg;
                    break;
                case "iron staff":
                    w1_f_dmg_dice = ironstaff_f_dmg;
                    break;
                case "iron rod":
                    w1_f_dmg_dice = ironrod_f_dmg;
                    break;
                default:
                    w1_f_dmg_dice = unarmed_gauntlet_f_dmg;
                    break;
            }
        }
        if (weapon_slot == 2) {
            switch (weapon) {
                case "dagger":
                    w2_f_dmg_dice = dagger_f_dmg;
                    break;
                case "rod":
                    w2_f_dmg_dice = rod_f_dmg;
                    break;
                case "quarterstaff":
                    w2_f_dmg_dice = staff_f_dmg;
                    break;
                case "short sword":
                    w2_f_dmg_dice = shortsword_f_dmg;
                    break;
                case "long sword":
                    w2_f_dmg_dice = longsword_f_dmg;
                    break;
                case "great sword":
                    w2_f_dmg_dice = greatsword_f_dmg;
                    break;
                case "iron staff":
                    w2_f_dmg_dice = ironstaff_f_dmg;
                    break;
                case "iron rod":
                    w2_f_dmg_dice = ironrod_f_dmg;
                    break;
                default:
                    w2_f_dmg_dice = unarmed_gauntlet_f_dmg;
                    break;
            }
        }
    }

}
