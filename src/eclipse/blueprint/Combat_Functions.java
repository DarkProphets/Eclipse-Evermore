//Eclipse Evermore -> eclipse -> Combat_Functions.java
//Kealdor created Combat_Functions.java on 10-28-2023 at 10:29:33 PM
package eclipse.blueprint;

import java.io.IOException;

import eclipse.core;
import java.util.Random;

public class Combat_Functions {

    //spell objects created it enemy and pplayer classes now need to evalute this file for needed changes as of 11/6/23
    Enemy_Spells e_spell = new Enemy_Spells();
    Player_Spells p_spell = new Player_Spells();

    static boolean player_weapon_crit;
    static int magic_atk_crit_dmg;

    void Crit_Check(Player player) throws IOException {
        int crit_roll = core.Dice_Parser("1d21");

        if (crit_roll == 20) {
            core.log("Crit Check Dice roll", "player critted");

            player.feat.crit_dmg(player);

            if (core.pre_alpha_fight == 1) {
                magic_atk_crit_dmg = player.get_magic_atk_dmg() + player.bmage_lvl + player.wiz_lvl + (player.get_iq() / 2) + (player.crit_dmg / 3);
            } else {
                if (player.pal_lvl > player.wiz_lvl) {
                    magic_atk_crit_dmg = player.crit_dmg + player.pal_lvl + player.get_wis();
                }

                if (player.dk_lvl > player.wiz_lvl) {
                    magic_atk_crit_dmg = player.crit_dmg + player.dk_lvl + player.get_iq();
                }

                player_weapon_crit = true;
            }
        } else {
            player_weapon_crit = false;
        }
    }

    public void Combat(Player player, Enemy enemy, int type, int spell_number) throws IOException {
        player.p_spell.buff_timers_update(player);

        int coin_flip = core.Dice_Parser("1d100");

        //if >= 50 player goes first
        if (coin_flip >= 50) {
            Player_Combat_Action(type, player, enemy, spell_number);
            Enemy_Combat_Action(player, enemy);
        }

        if (coin_flip < 50) {
            Enemy_Combat_Action(player, enemy);
            Player_Combat_Action(type, player, enemy, spell_number);
        }

        Death_Check(enemy, player);
    }

    void Player_Combat_Action(int action, Player player, Enemy enemy, int number) throws IOException {
        switch (action) {
            case 1://reg atk
                Player_Attack(player, enemy, action);
                break;
            case 2://magic atk is built into this
                Player_Attack(player, enemy, action);
                break;
            case 3://spell sword both components are built into this
                Spell_Sword_Attack(player, enemy, number, p_spell);
                break;
            case 4://spell
                //handeled by gui screen
                break;
            case 5://use potions
                break;
            case 6://flee
                break;
        }
    }

    void Player_Attack(Player player, Enemy enemy, int type) throws IOException {
        player.set_dmg();
        Crit_Check(player);

        if (player_weapon_crit == true) {
            player.item.item_effect.Vorpal(player, enemy);
        }

        if (core.pre_alpha_fight == 1) {
            if (player_weapon_crit == false) {
                switch (type) {
                    case 1://regular attack
                        enemy.set_chp(enemy.get_chp() - player.get_arena_dmg());
                        player.item.item_effect.Vampiric_Regen_Calc(player, player.get_arena_dmg());
                        break;
                    case 2://magic attack changes in set_magic_atk_dmg() for arena formula
                        player.set_magic_atk_dmg();
                        enemy.set_chp(enemy.get_chp() - player.get_magic_atk_dmg());
                        player.item.item_effect.Vampiric_Regen_Calc(player, player.get_magic_atk_dmg());
                        break;
                }
            }

            if (player_weapon_crit == true) {
                switch (type) {
                    case 1://regular attack
                        int arena_crit_dmg = player.crit_dmg / 2;
                        enemy.set_chp(enemy.get_chp() - arena_crit_dmg);
                        player.item.item_effect.Vampiric_Regen_Calc(player, arena_crit_dmg);
                        break;
                    case 2://magic attack crit changes formula in crit_check()
                        player.set_magic_atk_dmg();
                        enemy.set_chp(enemy.get_chp() - magic_atk_crit_dmg);
                        player.item.item_effect.Vampiric_Regen_Calc(player, magic_atk_crit_dmg);
                        break;
                }
            }
        }

        if (core.pre_alpha_fight == 0) {
            if (player_weapon_crit == false) {
                switch (type) {
                    case 1:                    //regular attack
                        enemy.set_chp(enemy.get_chp() - player.get_dmg());
                        player.item.item_effect.Vampiric_Regen_Calc(player, player.get_dmg());
                        break;
                    case 2:                    //magic attack
                        player.set_magic_atk_dmg();
                        enemy.set_chp(enemy.get_chp() - player.get_magic_atk_dmg());
                        player.item.item_effect.Vampiric_Regen_Calc(player, player.get_magic_atk_dmg());
                        break;
                }
            }

            if (player_weapon_crit == true) {
                switch (type) {
                    case 1://regular attack crit
                        enemy.set_chp(enemy.get_chp() - player.crit_dmg);
                        player.item.item_effect.Vampiric_Regen_Calc(player, player.crit_dmg);
                        break;
                    case 2://magic attack crit
                        player.set_magic_atk_dmg();
                        enemy.set_chp(enemy.get_chp() - magic_atk_crit_dmg);
                        player.item.item_effect.Vampiric_Regen_Calc(player, magic_atk_crit_dmg);
                        break;
                }
            }
        }
        //vamp regen is called within elem attack so no need to code it in here to restore life from that attack
        player.item.item_effect.Elemental_Attack(player, enemy);
    }

    void Death_Check(Enemy e, Player p) throws IOException {
        if (e.get_chp() < 0) {
            e.chp = 0;
        }

        if (p.get_chp() < 0) {
            p.chp = 0;
        }

        if (e.get_chp() == 0) {
            loot_generation(e, p);
        }

        if (p.get_chp() == 0) {
            //RPG.Game_Over();
            p.set_chp(1);
        }

        if (p.get_chp() == 0 || e.get_chp() == 0) {
            //core.Travel(p);
        }
    }

    void Spell_Sword_Attack(Player player, Enemy enemy, int spell_number, Player_Spells player_Spells) throws IOException {
        Player_Attack(player, enemy, 2);

        player_Spells.spell_sword_spell_cost_override = true;
        switch (spell_number) {
            case 1:
                player_Spells.Cure(player, player.s_cure_rank);
                break;
            case 2:
                player_Spells.Fire(player, enemy, player.s_fire_rank);
                break;
            case 3:
                player_Spells.Thunder(player, enemy, player.s_thunder_rank);
                break;
            case 4:
                player_Spells.Blizzard(player, enemy, player.s_blizzard_rank);
                break;
            case 5:
                if (player.s_death == 1) {
                    player.item.item_effect.Insta_Death(enemy);
                }
                break;
            case 6:
                player_Spells.Poison(player, enemy, player.s_poison_rank);
                break;
            case 7:
                player_Spells.Quake(player, enemy, player.s_earth_rank);
                break;
            case 8:
                player_Spells.Aero(player, enemy, player.s_wind_rank);
                break;
        }
    }

    void loot_generation(Enemy e, Player p) throws IOException {
        Random random = new Random();
        int loot_category = random.nextInt(5);
        core.log("loot Generation", "Loot category is " + loot_category);
        int item_given = 0;
        switch (loot_category) {
            case 1:
                item_given = random.nextInt(4);
                break; //hp potions
            case 2:
                item_given = random.nextInt(4);
                break; //mp potions
            case 3:
                item_given = random.nextInt(8);
                break; //weapons
            case 4:
                item_given = random.nextInt(6);
                break; //armors
            case 5:
                item_given = random.nextInt(5);
                break; //shields
        }
        core.log("loot Generation", "Item from loot category is " + item_given);
        switch (loot_category) {
            case 1:
                switch (item_given) {
                    case 1:
                        p.item.s_hp_pot++;
                        break;
                    case 2:
                        p.item.m_hp_pot++;
                        break;
                    case 3:
                        p.item.l_hp_pot++;
                        break;
                    case 4:
                        p.item.f_hp_pot++;
                        break;
                }
                break;
            case 2:
                switch (item_given) {
                    case 1:
                        p.item.s_mp_pot++;
                        break;
                    case 2:
                        p.item.m_mp_pot++;
                        break;
                    case 3:
                        p.item.l_mp_pot++;
                        break;
                    case 4:
                        p.item.f_mp_pot++;
                        break;
                }
                break;
            case 3:
                switch (item_given) {
                    case 1:
                        p.item.s_sword++;
                        break;
                    case 2:
                        p.item.l_sword++;
                        break;
                    case 3:
                        p.item.g_sword++;
                        break;
                    case 4:
                        p.item.dagger++;
                        break;
                    case 5:
                        p.item.w_staff++;
                        break;
                    case 6:
                        p.item.rod++;
                        break;
                    case 7:
                        p.item.i_staff++;
                        break;
                    case 8:
                        p.item.i_rod++;
                        break;
                }
                break;
            case 4:
                switch (item_given) {
                    case 1:
                        p.item.robe++;
                        break;
                    case 2:
                        p.item.l_armor++;
                        break;
                    case 3:
                        p.item.m_armor++;
                        break;
                    case 4:
                        p.item.h_armor++;
                        break;
                    case 5:
                        p.item.s_armor++;
                        break;
                    case 6:
                        p.item.d_armor++;
                        break;
                }
                break;
            case 5:
                switch (item_given) {
                    case 1:
                        p.item.b_shield++;
                        break;
                    case 2:
                        p.item.s_shield++;
                        break;
                    case 3:
                        p.item.m_shield++;
                        break;
                    case 4:
                        p.item.l_shield++;
                        break;
                    case 5:
                        p.item.t_shield++;
                        break;
                }
                break;
        }
        core.log("loot Generation", p.name + "has had their item reward given");
        if (core.pre_alpha_fight == 1) {
            e.set_xp((int) (e.get_mhp() * 1.5));
        }
        core.log("loot Generation", p.name + "should recieve " + e.get_xp() + "xp and " + e.get_gp() + " gp");
        p.set_xp(e.get_xp());
        core.log("loot Generation", p.name + "has had their XP reward given");
        p.set_gp(e.get_gp());
        core.log("loot Generation", p.name + "has had their GP reward given");
        core.log("loot Generation", p.name + "is about go through level up check");
        p.level_up(p);
    }

    void Enemy_Combat_Action(Player player, Enemy enemy) throws IOException {
        int action = core.Dice_Parser("1d6");

        switch (action) {
            case 1://normal attack deals damage from player guard on melee attack to enemy
                player.set_chp(player.get_chp() - enemy.get_dmg());
                player.item.item_effect.Elemental_Guard(player, enemy);
                break;

            case 2://Healing specific monsters only (bandit)
                switch (enemy.name) {
                    case "Bandit":
                        e_spell.E_Cure(enemy, player);
                        break;

                    default:
                        Enemy_Combat_Action(player, enemy);
                        break;
                }
                break;

            case 3://magic attack specific monsters only (imp, dark imp, bandit)
                if (p_spell.shell == false) {
                    switch (enemy.name) {
                        case "Bandit":
                            e_spell.E_Fire(player, enemy);
                            break;

                        case "Imp":
                            e_spell.E_Fire(player, enemy);
                            break;

                        case "Dark Imp":
                            e_spell.E_Fire(player, enemy);
                            break;

                        default:
                            Enemy_Combat_Action(player, enemy);
                            break;
                    }
                } else {//if players shell spell is active do nothing   
                }
                break;
            case 4://do nothing
                break;

            case 0://do nothing
                break;

            default:
                Enemy_Combat_Action(player, enemy);
                break;
        }

        Death_Check(enemy, player);

    }

    void White_Magic(Player player, int spell_number, int spell_rank, Player_Spells player_Spells) throws IOException {
        switch (spell_number) {
            case 1:
                player_Spells.Cure(player, spell_rank);
                break;
            case 2:
                player_Spells.Cura(player);
                break;
            case 3:
                player_Spells.Curaga(player);
                break;
            case 4:
                player_Spells.protect = true;
                if (player_Spells.protect_turn > 1) {
                    player_Spells.protect_turn = 0;
                }
                player_Spells.Protect(player, player.s_protect_rank);
                break;
            case 5:
                player_Spells.regen = true;
                if (player_Spells.regen_turn > 1) {
                    player_Spells.regen_turn = 0;
                }
                player_Spells.Regen(player, spell_rank);
                break;
            case 6:
                player_Spells.tmp_str = true;
                if (player_Spells.tmp_str_turn > 1) {
                    player_Spells.tmp_str_turn = 0;
                }
                player_Spells.Tmp_Str(player, spell_rank);
                break;
            case 7:
                player_Spells.shell = true;
                if (player_Spells.shell_turn > 1) {
                    player_Spells.shell_turn = 0;
                }
                player_Spells.Shell(player);
                break;
        }
    }

    void Black_Magic(Player player, Enemy enemy, int spell_number, int spell_rank, Player_Spells player_Spells) throws IOException {
        switch (spell_number) {
            case 1:
                player_Spells.Harm(player, enemy, spell_rank);
                break;
            case 2:
                player_Spells.Fire(player, enemy, spell_rank);
                break;
            case 3:
                player_Spells.Fira(player, enemy);
                break;
            case 4:
                player_Spells.Firaga(player, enemy);
                break;
            case 5:
                player_Spells.Blizzard(player, enemy, spell_rank);
                break;
            case 6:
                player_Spells.Blizzara(player, enemy);
                break;
            case 7:
                player_Spells.Blizzaga(player, enemy);
                break;
            case 8:
                player_Spells.Thunder(player, enemy, spell_rank);
                break;
            case 9:
                player_Spells.Thundera(player, enemy);
                break;
            case 10:
                player_Spells.Thunderaga(player, enemy);
                break;
            case 11:
                player_Spells.Poison(player, enemy, spell_rank);
                break;
            case 12:
                player_Spells.Quake(player, enemy, spell_rank);
                break;
            case 13:
                player_Spells.Aero(player, enemy, spell_rank);
                break;
        }
    }

    void Player_Cast_Spell(Player player, Enemy enemy, String magic_type, int spell_number, int spell_rank, Player_Spells player_Spells) throws IOException {
        switch (magic_type) {
            case "white":
                White_Magic(player, spell_number, spell_rank, player_Spells);
                break;
            case "black":
                Black_Magic(player, enemy, spell_number, spell_rank, player_Spells);
                break;
        }
    }

    public void use_f_hp_pot(Player player) throws IOException {
        if (player.get_chp() < player.get_mhp()) {
            player.set_chp(player.get_mhp());
            player.item.f_hp_pot--;
            core.log("stat modification", player.name + " has used a Full Restorative HP Potion");
        }
        core.log("inventory modification", player.name + " has " + player.item.f_hp_pot + "Full Restorative HP Potions remaining in inventory");
    }

    public void use_f_mp_pot(Player player) throws IOException {
        if (player.get_cmp() < player.get_mmp()) {
            player.set_cmp(player.get_mmp());
            player.item.f_mp_pot--;
            core.log("stat modification", player.name + " has used a Full Restortative MP Potion");
        }
        core.log("inventory modification", player.name + " has " + player.item.f_mp_pot + "Full Restorative MP Potions remaining in inventory");
    }

    public void use_l_hp_pot(Player player) throws IOException {
        if (player.get_chp() < player.get_mhp()) {
            player.set_chp(player.get_chp() + (player.get_mhp() / 3) + (player.get_mhp() / 3));
            player.item.l_hp_pot--;
            core.log("stat modification", player.name + " has used a Large HP Potion");
        }
        core.log("inventory modification", player.name + " has " + player.item.l_hp_pot + "Large HP Potions remaining in inventory");
    }

    public void use_l_mp_pot(Player player) throws IOException {
        if (player.get_cmp() < player.get_mmp()) {
            player.set_chp(player.get_cmp() + (player.get_mmp() / 3) + (player.get_mmp() / 3));
            player.item.l_hp_pot--;
            core.log("stat modification", player.name + " has used a Large MP Potion");
        }
        core.log("inventory modification", player.name + " has " + player.item.l_mp_pot + "Large MP Potions remaining in inventory");
    }

    public void use_m_hp_pot(Player player) throws IOException {
        if (player.get_chp() < player.get_mhp()) {
            player.set_chp(player.get_chp() + (player.get_mhp() / 3));
            player.item.m_hp_pot--;
            core.log("stat modification", player.name + " has used a Medium HP Potion");
        }
        core.log("inventory modification", player.name + " has " + player.item.m_hp_pot + "Medium HP Potions remaining in inventory");
    }

    public void use_m_mp_pot(Player player) throws IOException {
        if (player.get_cmp() < player.get_mmp()) {
            player.set_cmp(player.get_cmp() + (player.get_mmp() / 3));
            player.item.m_mp_pot--;
            core.log("stat modification", player.name + " has used a Medium MP Potion");
        }
        core.log("inventory modification", player.name + " has " + player.item.m_mp_pot + "Medium MP Potions remaining in inventory");
    }

    public void use_s_hp_pot(Player player) throws IOException {
        if (player.get_chp() < player.get_mhp()) {
            player.set_chp(player.get_chp() + (player.get_mhp() / 4));
            player.item.s_hp_pot--;
            core.log("stat modification", player.name + " has used a Small HP Potion");
        }
        core.log("inventory modification", player.name + " has " + player.item.s_hp_pot + "Small HP Potions remaining in inventory");
    }

    public void use_s_mp_pot(Player player) throws IOException {
        if (player.get_cmp() < player.get_mmp()) {
            player.set_chp(player.get_cmp() + (player.get_mmp() / 4));
            player.item.s_mp_pot--;
            core.log("stat modification", player.name + " has used a Small MP Potion");
        }
        core.log("inventory modification", player.name + " has " + player.item.s_mp_pot + "Small MP Potions remaining in inventory");
    }

}
