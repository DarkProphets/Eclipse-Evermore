//Eclipse Evermore -> eclipse.blueprint -> feats.java
//Kealdor created feats.java on 10-28-2023 at 8:47:36 PM
package eclipse.blueprint;

import java.io.IOException;

import eclipse.core;

public class feats {
	/**
     * the logic and math for the critical hit feats
     */
    public void crit_dmg(Player player) throws IOException {
        //base critical damage without feats will be 1 1/3 times regular damage
        player.crit_dmg = (int) (player.get_dmg() * 1.33);
        core.log("Critical Damage Feat", player.name + " has a base Crit dmg of " + player.crit_dmg);
        //base version of feat for crit damage increase
        if (player.prof_crit == true && player.crit_dmg_feat <= 1) {
            player.crit_dmg = player.get_dmg() * 2;
            player.crit_dmg_feat = 1;
            core.log("stat modification - Feat", player.name + " has crit damage thats 2x damage for having the first crit feat");
        }
        //improved version of feat
        if (player.prof_imp_crit == true && player.crit_dmg_feat <= 2) {
            player.crit_dmg = player.get_dmg() * 4;
            player.crit_dmg_feat = 2;
            core.log("stat modification - Feat", player.name + " has crit damage thats 4x damage for having the second crit feat");
        }
        //perfect version of feat
        if (player.prof_perfect_crit == true && player.crit_dmg_feat <= 3) {
            player.crit_dmg = player.get_dmg() * 8;
            player.crit_dmg_feat = 3;
            core.log("stat modification - Feat", player.name + " has crit damage thats 8x damage for having the third crit feat");
        }
        core.log("Critical Damage Feat", player.name + " has a Crit dmg of " + player.crit_dmg + " after feats applied");
    }

    /**
     * the logic and math for the dual wield feats
     *
     */
    public void dual_wield(Player player) throws IOException {
        //if we arent using a shield on weapons that can be dual hand
        if (player.item.equipt_shield == 0 && player.item.equipt_weapon2 >= 0) {
            core.log("Dual Wield Feat", player.name + " is dual wielding weapons");
            //gives penalty like most deungeons and dragons games does unless profiecent dual wielding
            if (player.item.equipt_weapon != 3 || player.item.equipt_weapon != 6 || player.item.equipt_weapon != 7) {

                player.feat_dmg_set(1, player.get_dmg() - 1);
                player.feat_dmg_set(2, player.get_arena_dmg() - 1);

                if (player.item.equipt_weapon2 != 0 || player.item.equipt_weapon2 != 3 || player.item.equipt_weapon2 != 6 || player.item.equipt_weapon2 != 7) {
                    player.feat_dmg_set(1, player.get_dmg() - 2);
                    player.feat_dmg_set(2, player.get_arena_dmg() - 2);
                    core.log("stat modification - Item", player.name + " has now taken the -3 penalty to damage for not having the feat");
                    //check if the player has the has the feats to reduce the penalty
                    //base version of feat
                    if (player.prof_dual_wield && player.dual_wield_feat <= 1) {
                        player.feat_dmg_set(1, player.get_dmg() + 1);
                        player.feat_dmg_set(2, player.get_arena_dmg() + 1);
                        player.dual_wield_feat = 1;
                        core.log("stat modification - Feat", player.name + " has now taken the -2 penalty to damage for having the " + "first dual wield feat");
                    }
                    //improved version of feat
                    if (player.prof_imp_dual_wield && player.dual_wield_feat <= 2) {
                        //raises collective reduction of penalty to 2 instead of just the initial 1
                        player.feat_dmg_set(1, player.get_dmg() + 1);
                        player.feat_dmg_set(2, player.get_dmg() + 1);
                        player.dual_wield_feat = 2;
                        core.log("stat modification - Feat", player.name + " has now taken the -1 penalty to damage for having the " + "second dual wield feat");
                    }
                    //perfect version of feat
                    if (player.prof_perfect_dual_wield && player.dual_wield_feat <= 3) {
                        //raises collective reduction of penalty to 3 which completely removes it
                        player.feat_dmg_set(1, player.get_dmg() + 1);
                        player.feat_dmg_set(2, player.get_dmg() + 1);
                        player.dual_wield_feat = 3;
                        core.log("stat modification - Feat", player.name + " has no penalty to damage for having the third dual wield feat");
                    }
                }
            }
        }
    }

    public void set_armor_profs(Player player) throws IOException {
        player.prof_robe = true;
        core.log("feat grant - armor", player.name + " can now equip Robes");
        if (player.wmage_lvl >= 1 || player.wiz_lvl >= 1 || player.ftr_lvl >= 1 || player.dk_lvl >= 1 || player.pal_lvl >= 1) {
            player.prof_l_armor = true;
            core.log("feat grant - armor", player.name + " can now equip Light Armor");
        }
        if (player.ftr_lvl >= 1 || player.dk_lvl >= 1 || player.pal_lvl >= 1) {
            player.prof_m_armor = true;
            core.log("feat grant - armor", player.name + " can now equip Medium Armor");
            player.prof_h_armor = true;
            core.log("feat grant - armor", player.name + " can now equip Heavy Armor");
        }
        if (player.ftr_lvl >= 5 || player.dk_lvl >= 5) {
            player.prof_s_armor = true;
            core.log("feat grant - armor", player.name + " can now equip Scaled Armor");
        }
        if (player.ftr_lvl >= 7 || player.dk_lvl >= 7) {
            player.prof_d_armor = true;
            core.log("feat grant - armor", player.name + " can now equip Dragon Armor");
        }
    }

    public void set_shield_profs(Player player) throws IOException {
        if (player.wmage_lvl >= 1 || player.ftr_lvl >= 1 || player.dk_lvl >= 1 || player.pal_lvl >= 1) {
            player.prof_b_shield = true;
            core.log("feat grant - shield", player.name + " can now equip Bucklers");
        }
        if (player.wmage_lvl >= 2 || player.wiz_lvl >= 1 || player.ftr_lvl >= 1 || player.dk_lvl >= 1 || player.pal_lvl >= 1) {
            player.prof_s_shield = true;
            core.log("feat grant - shield", player.name + " can now equip Small Shields");
        }
        if (player.wmage_lvl >= 3 || player.ftr_lvl >= 1 || player.dk_lvl >= 1 || player.pal_lvl >= 1) {
            player.prof_m_shield = true;
            core.log("feat grant - shield", player.name + " can now equip Medium Shields");
        }
        if (player.ftr_lvl >= 1 || player.dk_lvl >= 1 || player.pal_lvl >= 1) {
            player.prof_l_shield = true;
            core.log("feat grant - shield", player.name + " can now equip Large Shields");
        }
        if (player.ftr_lvl >= 5 || player.dk_lvl >= 5) {
            player.prof_t_shield = true;
            core.log("feat grant - shield", player.name + " can now equip Tower Shields");
        }
    }

    public void set_wpn_profs(Player player) throws IOException {
        player.prof_dagger = true;
        core.log("feat grant - weapon", player.name + " can now equip Daggers");
        if (player.wmage_lvl >= 1 || player.wiz_lvl >= 1 || player.ftr_lvl >= 1) {
            player.prof_staff = true;
            core.log("feat grant - weapon", player.name + " can now equip Wooden Staffs");
        }
        if (player.bmage_lvl >= 1 || player.wiz_lvl >= 1 || player.ftr_lvl >= 1) {
            player.prof_rod = true;
            core.log("feat grant - weapon", player.name + " can now equip Wooden Rods");
        }
        if (player.wiz_lvl >= 3 || player.ftr_lvl >= 3) {
            player.prof_i_rod = true;
            core.log("feat grant - weapon", player.name + " can now equip Iron Rods");
        }
        if (player.wmage_lvl >= 3 || player.ftr_lvl >= 3) {
            player.prof_i_staff = true;
            core.log("feat grant - weapon", player.name + " can now equip Iron Staffs");
        }
        if (player.ftr_lvl >= 1) {
            player.prof_s_sword = true;
            core.log("feat grant - weapon", player.name + " can now equip Short Swords");
            player.prof_l_sword = true;
            core.log("feat grant - weapon", player.name + " can now equip Long Swords");
        }
        if (player.ftr_lvl >= 5) {
            player.prof_g_sword = true;
            core.log("feat grant - weapon", player.name + " can now equip Great Swords");
        }
    }

    public void level_up_hp(Player player) throws IOException {
        //need to set this flag before trying to give hp or set_mhp(int x) doesnt work
        player.Enemy = false;

//do give hp the first time for regular level up
        if (player.new_character == true) {
            player.set_mhp(player.get_con() / 2);
            core.log("stat modification", player.name + " has just been created and assigned " + player.get_mhp() + " Base HP");
        }

        switch (player.lvl_up_class_choice) {
            case 1:
                //fighter
                player.set_mhp(player.ftr_lvl_up_hp);
                break;
            case 2:
                //black mage
                player.set_mhp(player.mage_lvl_up_hp);
                break;
            case 3:
                //white mage
                player.set_mhp(player.mage_lvl_up_hp);
                break;
            case 4:
                //wizard
                player.set_mhp(player.mage_lvl_up_hp);
                break;
            case 5:
                //dark knight
                player.set_mhp((player.mage_lvl_up_hp + player.ftr_lvl_up_hp) / 2);
                break;
            case 6:
                //paladin
                player.set_mhp((player.mage_lvl_up_hp + player.ftr_lvl_up_hp) / 2);
                break;
        }
        core.log("stat modification", player.name + " has just been granted additional HP based on their class selection");
        core.log("stat modification", player.name + " now has " + player.get_mhp() + "HP in total");
        //start of feat
        //base version
        if (player.prof_hp == true && player.hp_feat <= 1) {
            if (player.prof_hp_first_grant == false) {
                switch (player.lvl_up_class_choice) {
                    case 1:
                        //fighter
                        player.set_mhp(player.ftr_lvl_up_hp);
                        break;
                    case 2:
                        //black mage
                        player.set_mhp(player.mage_lvl_up_hp);
                        break;
                    case 3:
                        //white mage
                        player.set_mhp(player.mage_lvl_up_hp);
                        break;
                    case 4:
                        //wizard
                        player.set_mhp(player.mage_lvl_up_hp);
                        break;
                    case 5:
                        //dark knight
                        player.set_mhp((player.mage_lvl_up_hp + player.ftr_lvl_up_hp) / 2);
                        break;
                    case 6:
                        //paladin
                        player.set_mhp((player.mage_lvl_up_hp + player.ftr_lvl_up_hp) / 2);
                        break;
                }
                core.log("stat modification", player.name + " has just been granted additional HP based on having prof_hp feat for the first time");
            }
            if (player.prof_hp_first_grant == true) {
                switch (player.lvl_up_class_choice) {
                    case 1:
                        //fighter
                        player.set_mhp(player.ftr_lvl_up_hp / 3);
                        break;
                    case 2:
                        //black mage
                        player.set_mhp(player.mage_lvl_up_hp / 3);
                        break;
                    case 3:
                        //white mage
                        player.set_mhp(player.mage_lvl_up_hp / 3);
                        break;
                    case 4:
                        //wizard
                        player.set_mhp(player.mage_lvl_up_hp / 3);
                        break;
                    case 5:
                        //dark knight
                        player.set_mhp(((player.mage_lvl_up_hp + player.ftr_lvl_up_hp) / 2) / 3);
                        break;
                    case 6:
                        //paladin
                        player.set_mhp(((player.mage_lvl_up_hp + player.ftr_lvl_up_hp) / 2) / 3);
                        break;
                }
                core.log("stat modification", player.name + " has just been granted additional HP based on having prof_hp feat");
            }
            if (player.prof_hp_first_grant == false) {
                player.prof_hp_first_grant = true;
            }
            if (player.hp_feat < 1) {
                player.hp_feat = 1;
            }
            core.log("stat modification", player.name + " now has " + player.get_mhp() + "HP in total");
        }
        //improved version adds half the con as extra hp per level
        if (player.prof_imp_hp == true && player.hp_feat <= 2) {
            player.set_mhp(player.get_con() / 2);
            player.hp_feat = 2;
            core.log("stat modification", player.name + " has just been granted additional HP based on having prof_imp_hp feat");
            core.log("stat modification", player.name + " now has " + player.get_mhp() + "HP in total");
        }
        ////perfect version adds players max hp to their hp per level
        //        if(prof_perfect_hp == true){
        //           set_mhp(get_mhp());
        //            core.log("stat modification", name +
        //                    " has just been granted additional HP based on having prof_perfect_hp feat");
        //
        //        core.log("stat modification", name +
        //                    " now has " + get_mhp() + "HP in total");
        //        }
        player.set_chp(player.DM_Heal());
        core.log("stat modification", player.name + " has just been healed to full health upon completion of the level up hp grant");
    }

    public void level_up_mp(Player player) throws IOException {
        //need to set this flag before trying to give mp or set_mmp(int x) doesnt work
        player.Enemy = false;
        if (player.new_character == true && player.lvl_up_class_choice > 1) {
            player.set_mmp(player.mage_lvl_up_mp);
            core.log("stat modification", player.name + " has just been created and assigned " + player.get_mmp() + " Base MP");
        }
        if (player.new_character == true && player.lvl_up_class_choice == 1) {
            player.set_mmp(0);
            core.log("stat modification", player.name + " has just been created and assigned " + player.get_mmp() + " Base MP");
        }
        //do give mp the first time for regular level up
        switch (player.lvl_up_class_choice) {
            case 1:
                //fighter
                break;
            case 2:
                //black mage
                player.set_mmp(player.get_iq() / 2);
                break;
            case 3:
                //white mage
                player.set_mmp(player.get_wis() / 2);
                break;
            case 4:
                //wizard
                player.set_mmp((player.get_iq() / 2) + (player.get_wis() / 2));
                break;
            case 5:
                //dark knight
                player.set_mmp(player.get_iq() / 2);
                break;
            case 6:
                //paladin
                player.set_mmp(player.get_wis() / 2);
                break;
        }
        core.log("stat modification", player.name + " has just been granted additional MP based on their class selection");
        core.log("stat modification", player.name + " now has " + player.get_mmp() + "MP in total");
        //start of feat
        //base version
        if (player.prof_mp == true && player.mp_feat <= 1) {
            if (player.prof_mp_first_grant == false) {
                player.set_mmp(player.mage_lvl_up_mp);
                core.log("stat modification", player.name + " has just been granted additional MP the first time based on having prof_MP feat");
            }
            if (player.prof_mp_first_grant == true) {
                player.set_mmp(player.mage_lvl_up_mp / 3);
                core.log("stat modification", player.name + " has just been granted additional MP based on having prof_MP feat");
            }
            if (player.prof_mp_first_grant == false) {
                player.prof_mp_first_grant = true;
            }
            if (player.mp_feat <= 1) {
                player.mp_feat = 1;
            }
            core.log("stat modification", player.name + " now has " + player.get_mmp() + "MP in total");
        }
        //improved version adds half the stat relevant as extra mp per level
        if (player.prof_imp_mp == true && player.mp_feat <= 2) {
            switch (player.lvl_up_class_choice) {
                case 1:
                    //fighter
                    player.set_mmp((player.get_iq() / 2) + (player.get_wis() / 2));
                    break;
                case 2:
                    //black mage
                    player.set_mmp(player.get_iq() / 2);
                    break;
                case 3:
                    //white mage
                    player.set_mmp(player.get_wis() / 2);
                    break;
                case 4:
                    //wizard
                    player.set_mmp((player.get_iq() / 2) + (player.get_wis() / 2));
                    break;
                case 5:
                    //dark knight
                    player.set_mmp(player.get_iq() / 2);
                    break;
                case 6:
                    //paladin
                    player.set_mmp(player.get_wis() / 2);
                    break;
            }
            core.log("stat modification", player.name + " has just been granted additional MP based on having prof_imp_MP feat");
            core.log("stat modification", player.name + " now has " + player.get_mmp() + "MP in total");
        }
        ////perfect version adds players max hp to their hp per level
        //        if(prof_perfect_hp == true){
        //           set_mhp(get_mhp());
        //            core.log("stat modification", name +
        //                    " has just been granted additional MP based on having prof_perfect_MP feat");
        //
        //        core.log("stat modification", name +
        //                    " now has " + get_mmp() + "MP in total");
        //        }
        player.set_cmp(player.DM_Mana());
        core.log("stat modification", player.name + " has just had their MP restored upon completion of the level up MP grant");
    }

}


