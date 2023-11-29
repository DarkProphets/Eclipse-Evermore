//Eclipse Evermore -> eclipse.blueprint -> Player.java
//Kealdor created Player.java on 10-28-2023 at 8:19:44 PM
package eclipse.blueprint;

import eclipse.core;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class Player extends Creature{

    public items item = new items();
    public feats feat = new feats();
    public Player_Spells p_spell = new Player_Spells();

    /**
     what type of creature the player is. example human or elf.
     */
    public int race;

    public int str;
    public int con;
    private int str_mod;
    private int con_mod;
    public int iq;

    public int damage_reduction;
    private int magic_atk_dmg;
    private int arena_dmg;
    public int crit_dmg;
    public int wis;

    public int wmage_lvl;
    public int bmage_lvl;
    public int wiz_lvl;
    public int ftr_lvl;
    public int dk_lvl;
    public int pal_lvl;

    public boolean new_character;
    public int lvl_up_class_choice;
    public int ftr_lvl_up_hp;
    public int mage_lvl_up_hp;
    public int mage_lvl_up_mp;

    /**
     used for quest system and to speed level up using alternative ways instead
     of grinding for xp fight after fight
     */
    public int encounters_completed;//used for questing and giving rewards
    public int arena_kills;//used for giving rewards from killing in arena
    public int alpha_kills;//used for giving rewards from killing in alpha arena

    /**
     logs the last screen the player was on
     */
    public int last_location;

    //buff spell bonuses
    public int protect_ac_bonus;
    public int tmp_str_bonus;
    public int tmp_iq_bonus;
    public int tmp_wis_bonus;
    public int tmp_con_bonus;
    public int tmp_hp_bonus;
    public int tmp_mp_bonus;
    public int tmp_ac_bonus;

    //Equipment profiency vars
    //weapons
    public boolean prof_s_sword = false;
    public boolean prof_l_sword = false;
    public boolean prof_g_sword = false;
    public boolean prof_dagger = false;
    public boolean prof_staff = false;
    public boolean prof_rod = false;
    public boolean prof_i_rod = false;
    public boolean prof_i_staff = false;
    //armor
    public boolean prof_robe = false;
    public boolean prof_l_armor = false;
    public boolean prof_m_armor = false;
    public boolean prof_h_armor = false;
    public boolean prof_s_armor = false;//scaled armor material
    public boolean prof_d_armor = false;//dragon armor material
    //shields
    public boolean prof_b_shield = false;//buckler
    public boolean prof_s_shield = false;
    public boolean prof_m_shield = false;
    public boolean prof_l_shield = false;
    public boolean prof_t_shield = false;//tower shield

    //feats
    //dual wield weapons
    public boolean prof_dual_wield = false; //-1 off penalty for dual wield damage
    public boolean prof_imp_dual_wield = false;//-2 off penalty for dual wield damage
    public boolean prof_perfect_dual_wield = false;//removes all the penalty for dual wield damage
    public int dual_wield_feat;

    //critic hits
    public boolean prof_crit = false;//ability to crit damage x2 damage
    public boolean prof_imp_crit = false;//improved crit damage to x4
    public boolean prof_perfect_crit = false;//improves crit damage to x8
    public int crit_dmg_feat;

    //extra stats on level up
    public boolean prof_hp = false;
    public boolean prof_imp_hp = false;
    //public boolean prof_perfect_hp;
    public int hp_feat = 0;
    public boolean prof_hp_first_grant = false;

    public boolean prof_mp = false;
    public boolean prof_imp_mp = false;
    //public boolean prof_perfect_mp;
    public int mp_feat = 0;
    public boolean prof_mp_first_grant = false;

//TODO use these for quest rewards
//    public boolean prof_str;
//    public boolean prof_con;
//    public boolean prof_iq;
//    public boolean prof_wis;
    //core spells - trainer
    public int s_cure_rank;
    public int s_protect_rank;
    public int s_tmp_str_rank;
    public int s_fire_rank;
    public int s_thunder_rank;
    public int s_blizzard_rank;
    public int s_poison_rank;
    //bought only  
    public int s_gravity;//demi
    public int s_earth_rank;//quake
    public int s_regen_rank;
    public int s_shell;
    public int s_cura;
    //arena rewards 
    public int s_curaga;
    public int s_wind_rank;//aero
    public int s_harm_rank;
    //quest rewards        
    public int s_death;//insta kill
    public int s_heal;//dm heal

    //limit breaks
    //public int lb_double;//doubles every stat in combat for 2 turns
    public Player() throws IOException{
        core.log("Dice roll - level up hit points",
                "about to roll for Mage Level Up MP during player object creation");

        mage_lvl_up_mp = core.Dice_Parser("1d10");

        if (mage_lvl_up_mp == 0 && new_character == true){
            while (mage_lvl_up_mp == 0){
                mage_lvl_up_mp = core.Dice_Parser("1d10");
            }
        }

        core.log("Dice roll - level up hit points",
                "about to roll for Mage Level Up HP during player object creation");

        mage_lvl_up_hp = core.Dice_Parser("1d4");

        if (mage_lvl_up_hp == 0 && new_character == true){
            while (mage_lvl_up_hp == 0){
                mage_lvl_up_mp = core.Dice_Parser("1d4");
            }
        }

        core.log("Dice roll - level up hit points",
                "about to roll for Fighter Level Up HP during player object creation");

        ftr_lvl_up_hp = core.Dice_Parser("1d8");

        if (ftr_lvl_up_hp == 0 && new_character == true){
            while (ftr_lvl_up_hp == 0){
                ftr_lvl_up_hp = core.Dice_Parser("1d8");
            }
        }

    }

    public int get_str(){
        return str;
    }

    public int get_con(){
        return con;
    }

    public int get_iq(){
        return iq;
    }

    public int get_magic_atk_dmg(){
        return magic_atk_dmg;
    }

    public void set_magic_atk_dmg() throws IOException{
        if (core.pre_alpha_fight == 1){
            magic_atk_dmg = get_magic_atk_dmg() + bmage_lvl + wiz_lvl + (get_iq() / 2) + (get_dmg() / 3);
        } else{
            if (pal_lvl > wiz_lvl){
                magic_atk_dmg = get_dmg() + pal_lvl + get_wis();
            }
            if (dk_lvl > wiz_lvl){
                magic_atk_dmg = get_dmg() + dk_lvl + get_iq();
            }
        }

        core.log("stat modification", name + " has a Magic "
                + "Attack combat damage of " + get_magic_atk_dmg());
    }

    public void set_str(int x) throws IOException{
        str = get_str() + x;

        core.log("stat modification", name + " has a "
                + "Strength of " + get_str());
    }

    public void set_con(int x) throws IOException{
        con = get_con() + x;

        core.log("stat modification", name + " has a "
                + "Constuition of " + get_con());
    }

    public int get_wis(){
        return wis;
    }

    public void set_wis(int x) throws IOException{
        wis = get_wis() + x;

        core.log("stat modification", name + " has a "
                + "Wisdom of " + get_wis());
    }

    public void set_iq(int x) throws IOException{
        iq = get_iq() + x;

        core.log("stat modification", name + " has a "
                + "IQ of " + get_iq());
    }

    /**
     used to tally all the characters levels together into one number used for
     scaling enemies to the same level as the character
     */
    public void set_lvl(Player player) throws IOException{
        switch (player.lvl_up_class_choice){
            case 1://fighter    
                player.ftr_lvl++;
                break;
            case 2://black mage  
                player.bmage_lvl++;
                break;
            case 3://white mage   
                player.wmage_lvl++;
                break;
            case 4://wizard   
                player.wiz_lvl++;
                break;
            case 5://dark knight
                player.dk_lvl++;
                break;
            case 6://paladin 
                player.pal_lvl++;
                break;
        }

        level = wmage_lvl + bmage_lvl + wiz_lvl + ftr_lvl + dk_lvl + pal_lvl;

        core.log("set level",
                name + " is level " + level);

        core.log("class list",
                System.lineSeparator()
                + name + " has " + wmage_lvl + " levels of White Mage"
                + System.lineSeparator()
                + name + " has " + bmage_lvl + " levels of Black Mage"
                + System.lineSeparator()
                + name + " has " + wiz_lvl + " levels of Wizard"
                + System.lineSeparator()
                + name + " has " + ftr_lvl + " levels of Fighter"
                + System.lineSeparator()
                + name + " has " + dk_lvl + " levels of Dark Knight"
                + System.lineSeparator()
                + name + " has " + pal_lvl + " levels of Paladin");

        if (level > 0){
            new_character = false;
        }
    }

    /**
     math to figure out armor class
     */
    public void set_dmg_reduction() throws IOException{
        con_mod = set_modifiers("con", get_con());
        item.equip_armor();
        item.equip_shield();

        //highest melee class level will be added to damage reduction
        int x = Math.max(ftr_lvl, dk_lvl);
        int y = Math.max(ftr_lvl, pal_lvl);
        int z = Math.max(x, y);

        damage_reduction = con_mod + item.armor_def + z + item.shield_def;

        core.log("set damage reduction", name + " has a damage reduction total of "
                + damage_reduction + " from the sum of con_mod + armor_def + shield_def + highest melee classes lvl");
    }

    /**
     sets up equipment profrencies and setup damage and armor class called from
     load character and level up
     */
    public void wpn_armor_setup(Player character) throws IOException{
        feat.set_wpn_profs(character);
        feat.set_shield_profs(character);
        feat.set_armor_profs(character);
        set_dmg_reduction();
        //recalculates damages because of feats
        character.item.equip_weapon();
    }

    /**
     refills health and mana. adjusts total level for accurate enemy scaling.
     checks if any profrencies need to be given based. then sends player back to
     the last location they were at before combat. the gui will be let the
     players make a choice as to what class they want to be. that gui screen
     will end up calling this function on click of the choice button after
     registering the selected class.
     */
    public void level_up(Player character) throws IOException{
        if (character.new_character == false){
            core.log("Dice roll - level up hit points",
                    "about to roll for Mage Level Up MP");
            mage_lvl_up_mp = core.Dice_Parser("1d10");

            core.log("Dice roll - level up hit points",
                    "about to roll for Mage Level Up HP");
            mage_lvl_up_hp = core.Dice_Parser("1d4");

            core.log("Dice roll - level up hit points",
                    "about to roll for Fighter Level Up HP");
            ftr_lvl_up_hp = core.Dice_Parser("1d8");
        }

        feat.level_up_hp(character);
        feat.level_up_mp(character);

        set_lvl(character);

        wpn_armor_setup(character);
        character.Save_Character(character);
    }

    /**
     math to figure out damage and also applies dual wield and crit feats
     */
    public void set_dmg() throws IOException{
        str_mod = set_modifiers("strength", get_str());
        item.equip_weapon();

        item.weapon_dmg_calc(1, core.Dice_Parser(item.weapon_damage_dice));
        item.weapon_dmg_calc(2, core.Dice_Parser(item.weapon_damage_dice2));

        dmg = item.weapon_dmg + str_mod + item.weapon2_dmg;
        arena_dmg = item.a_weapon_dmg + str_mod + item.a_weapon2_dmg;
        feat.dual_wield(this);

        core.log("weapon damage", name + " has a combat damage of "
                + dmg + " during Random Encounters");

        core.log("weapon damage", name + " has a combat damage of "
                + get_arena_dmg() + " during Arena Encounters");
    }

    public int get_arena_dmg(){
        return arena_dmg;
    }

    public void feat_dmg_set(int dmg_type, int x){
        switch (dmg_type){
            case 1:
                dmg = x;
                break;
            case 2:
                arena_dmg = x;
                break;
        }
    }

    /**
     Saves character data first, then feats, then inventory, and finally returns
     to last spot saved

     @param player

     @throws java.io.IOException
     */
    public void Save_Character(Player player) throws IOException{
        core.Folder_Creation(3, player.name);
        try (FileWriter FileWriter = new FileWriter(core.game_root + "/saves/vault/" + player.name + "/" + player.name + core.player_data);  PrintWriter PrintWriter = new PrintWriter(FileWriter);){

            PrintWriter.println(name);
            PrintWriter.println(race);
            PrintWriter.println(get_mhp());
            PrintWriter.println(get_mmp());
            PrintWriter.println(get_xp());
            PrintWriter.println(get_gp());
            PrintWriter.println(item.equipt_armor);
            PrintWriter.println(item.equipt_shield);
            PrintWriter.println(item.equipt_weapon);
            PrintWriter.println(item.equipt_weapon2);
            PrintWriter.println(get_str());
            PrintWriter.println(get_con());
            PrintWriter.println(get_wis());
            PrintWriter.println(get_iq());
            PrintWriter.println(ftr_lvl);
            PrintWriter.println(bmage_lvl);
            PrintWriter.println(wmage_lvl);
            PrintWriter.println(wiz_lvl);
            PrintWriter.println(dk_lvl);
            PrintWriter.println(pal_lvl);
            PrintWriter.println(last_location);

            PrintWriter.close();
            FileWriter.close();
        }

        core.log("Save Character",
                name + "s core data has now been saved");

        Save_Character_Feat(player);
        Save_Character_Inventory(player);
        Save_Character_Quest(player);
        Save_Character_Spells(player);

        //After all data is saved send character back to their last location
        //core.Travel(player);
    }

    public void Save_Character_Feat(Player player) throws IOException{
        try (FileWriter FileWriter = new FileWriter(core.game_root + "/saves/vault/" + player.name + "/" + player.name + core.player_feat_data);  PrintWriter PrintWriter = new PrintWriter(FileWriter);){

            //dual wield weapons
            if (prof_dual_wield == true){
                PrintWriter.println("true");
            }

            if (prof_dual_wield == false){
                PrintWriter.println("false");
            }

            if (prof_imp_dual_wield == true){
                PrintWriter.println("true");
            }

            if (prof_imp_dual_wield == false){
                PrintWriter.println("false");
            }

            if (prof_perfect_dual_wield == true){
                PrintWriter.println("true");
            }

            if (prof_perfect_dual_wield == false){
                PrintWriter.println("false");
            }

            PrintWriter.println(dual_wield_feat);

            //critical hits
            if (prof_crit == true){
                PrintWriter.println("true");
            }

            if (prof_crit == false){
                PrintWriter.println("false");
            }

            if (prof_imp_crit == true){
                PrintWriter.println("true");
            }

            if (prof_imp_crit == false){
                PrintWriter.println("false");
            }

            if (prof_perfect_crit == true){
                PrintWriter.println("true");
            }

            if (prof_perfect_crit == false){
                PrintWriter.println("false");
            }

            PrintWriter.println(crit_dmg_feat);

            //hp
            if (prof_hp == true){
                PrintWriter.println("true");
            }

            if (prof_hp == false){
                PrintWriter.println("false");
            }

            if (prof_imp_hp == true){
                PrintWriter.println("true");
            }

            if (prof_imp_hp == false){
                PrintWriter.println("false");
            }

            PrintWriter.println(hp_feat);

            if (prof_hp_first_grant == true){
                PrintWriter.println("true");
            }

            if (prof_hp_first_grant == false){
                PrintWriter.println("false");
            }

            //mp
            if (prof_mp == true){
                PrintWriter.println("true");
            }

            if (prof_mp == false){
                PrintWriter.println("false");
            }

            if (prof_imp_mp == true){
                PrintWriter.println("true");
            }

            if (prof_imp_mp == false){
                PrintWriter.println("false");
            }

            PrintWriter.println(mp_feat);

            if (prof_mp_first_grant == true){
                PrintWriter.println("true");
            }

            if (prof_mp_first_grant == false){
                PrintWriter.println("false");
            }

            PrintWriter.close();
            FileWriter.close();
        }

        core.log("Save Character Feats",
                name + "s feat data has now been saved");

    }

    public void Save_Character_Inventory(Player player) throws IOException{
        try (FileWriter FileWriter = new FileWriter(core.game_root + "/saves/vault/" + player.name + "/" + player.name + core.player_inventory_data);  PrintWriter PrintWriter = new PrintWriter(FileWriter);){

            PrintWriter.println(item.s_hp_pot);
            PrintWriter.println(item.m_hp_pot);
            PrintWriter.println(item.l_hp_pot);
            PrintWriter.println(item.f_hp_pot);

            PrintWriter.println(item.s_mp_pot);
            PrintWriter.println(item.m_mp_pot);
            PrintWriter.println(item.l_mp_pot);
            PrintWriter.println(item.f_mp_pot);

            PrintWriter.println(item.s_sword);
            PrintWriter.println(item.l_sword);
            PrintWriter.println(item.g_sword);
            PrintWriter.println(item.dagger);
            PrintWriter.println(item.w_staff);
            PrintWriter.println(item.i_staff);
            PrintWriter.println(item.rod);
            PrintWriter.println(item.i_rod);

            PrintWriter.println(item.robe);
            PrintWriter.println(item.l_armor);
            PrintWriter.println(item.m_armor);
            PrintWriter.println(item.h_armor);
            PrintWriter.println(item.s_armor);
            PrintWriter.println(item.d_armor);

            PrintWriter.println(item.b_shield);
            PrintWriter.println(item.s_shield);
            PrintWriter.println(item.m_shield);
            PrintWriter.println(item.l_shield);
            PrintWriter.println(item.t_shield);

            PrintWriter.close();
            FileWriter.close();
        }

        core.log("Save Inventory",
                name + "s inventory data has now been saved");

    }

    public void Save_Character_Quest(Player player) throws IOException{
        try (FileWriter FileWriter = new FileWriter(core.game_root + "/saves/vault/" + player.name + "/" + player.name + core.player_quest_data);  PrintWriter PrintWriter = new PrintWriter(FileWriter);){

            PrintWriter.println(encounters_completed);
            PrintWriter.println(alpha_kills);
            PrintWriter.println(arena_kills);

            PrintWriter.close();
            FileWriter.close();
        }

        core.log("Save Quest",
                name + "s quest data has now been saved");

    }

    public void Save_Character_Spells(Player player) throws IOException{
        try (FileWriter FileWriter = new FileWriter(core.game_root + "/saves/vault/" + player.name + "/" + player.name + core.player_spell_data);  PrintWriter PrintWriter = new PrintWriter(FileWriter);){

            PrintWriter.println(s_blizzard_rank);
            PrintWriter.println(s_cure_rank);
            PrintWriter.println(s_cura);
            PrintWriter.println(s_curaga);
            PrintWriter.println(s_death);
            PrintWriter.println(s_earth_rank);
            PrintWriter.println(s_fire_rank);
            PrintWriter.println(s_gravity);
            PrintWriter.println(s_harm_rank);
            PrintWriter.println(s_heal);
            PrintWriter.println(s_poison_rank);
            PrintWriter.println(s_protect_rank);
            PrintWriter.println(s_regen_rank);
            PrintWriter.println(s_shell);
            PrintWriter.println(s_thunder_rank);
            PrintWriter.println(s_tmp_str_rank);
            PrintWriter.println(s_wind_rank);

            PrintWriter.close();
            FileWriter.close();
        }

        core.log("Save Spell",
                name + "s spell data has now been saved");

    }

    /**
     Loads the stuff in the same order Save saves it.

     @param load_me
     @param player

     @throws java.io.FileNotFoundException
     @throws java.io.IOException
     */
    public void Load_Character(String load_me, Player player) throws FileNotFoundException, IOException{
//have to set this flag before reading hp or it will not work
        Enemy = false;

//calling this to get pathing of save data
        core.Folder_Creation(load_me);
        String load = core.game_root + "/saves/" + load_me;
//Read back the saved values from character
        BufferedReader br = new BufferedReader(new FileReader(load + core.player_data));

        String line = "";
        line = br.readLine();
        player.name = load_me;

        line = br.readLine();
        player.race = Integer.parseInt(line);

        line = br.readLine();
        player.set_mhp(Integer.parseInt(line));

        line = br.readLine();
        player.set_mmp(Integer.parseInt(line));

        line = br.readLine();
        player.set_xp(Integer.parseInt(line));

        line = br.readLine();
        player.set_gp(Integer.parseInt(line));

        line = br.readLine();
        player.item.equipt_armor = Integer.parseInt(line);

        line = br.readLine();
        player.item.equipt_shield = Integer.parseInt(line);

        line = br.readLine();
        player.item.equipt_weapon = Integer.parseInt(line);

        line = br.readLine();
        player.item.equipt_weapon2 = Integer.parseInt(line);

        line = br.readLine();
        player.set_str(Integer.parseInt(line));

        line = br.readLine();
        player.set_con(Integer.parseInt(line));

        line = br.readLine();
        player.set_wis(Integer.parseInt(line));

        line = br.readLine();
        player.set_iq(Integer.parseInt(line));

        line = br.readLine();
        player.ftr_lvl = Integer.parseInt(line);

        line = br.readLine();
        player.bmage_lvl = Integer.parseInt(line);

        line = br.readLine();
        player.wmage_lvl = Integer.parseInt(line);

        line = br.readLine();
        player.wiz_lvl = Integer.parseInt(line);

        line = br.readLine();
        player.dk_lvl = Integer.parseInt(line);

        line = br.readLine();
        player.pal_lvl = Integer.parseInt(line);

        line = br.readLine();
        player.last_location = Integer.parseInt(line);

        br.close();

        core.log("Load Character",
                name + "s core data has now been loaded");

        //Load Feats
        Load_Character_Feat(load, player);

        //Load Inventory
        Load_Character_Inventory(load, player);

        //Load Quest
        Load_Character_Quest(load, player);

        //Load Spells
        Load_Character_Spells(load, player);

        //Use the values read back to build the rest we need to which isnt saved
        player.set_lvl(player);

        core.log("stat modification - load",
                name + "s total level has been calculated");

        player.set_chp(player.DM_Heal());

        core.log("stat modification - load",
                name + "s has been fully healed");

        player.set_cmp(player.DM_Mana());

        core.log("stat modification - load",
                name + "s mana has been fully restored");

        //setup weapon damage & defense
        if (player.item.equipt_weapon2 > 0){
            player.item.dual_wield_dmg_flag = true;
        }

        if (player.item.equipt_weapon2 == 0){
            player.item.dual_wield_dmg_flag = false;
        }

        core.log("stat modification - load",
                name + "s dual wield flag set");

        player.wpn_armor_setup(player);

        core.log("stat modification - load",
                name + "s dmg reducation has been calculated");

        player.new_character = false;
        player.lvl_up_class_choice = 0;

        core.log("Load Character",
                name + " is setup and ready to go on next adventure");

        //Send player to last location
        //core.Travel(player);
    }

    public void Load_Character_Feat(String load_me, Player player) throws FileNotFoundException, IOException{
        //Read back the saved values from character
        BufferedReader br = new BufferedReader(new FileReader(load_me + core.player_feat_data));

        String line = "";

        //dual wield
        line = br.readLine();
        if ("true".equals(line)){
            player.prof_dual_wield = true;
        }
        if ("false".equals(line)){
            player.prof_dual_wield = false;
        }

        line = br.readLine();
        if ("true".equals(line)){
            player.prof_imp_dual_wield = true;
        }
        if ("false".equals(line)){
            player.prof_imp_dual_wield = false;
        }

        line = br.readLine();
        if ("true".equals(line)){
            player.prof_perfect_dual_wield = true;
        }
        if ("false".equals(line)){
            player.prof_perfect_dual_wield = false;
        }

        line = br.readLine();
        player.dual_wield_feat = Integer.parseInt(line);

        core.log("Load Feat",
                name + "s Dual Wield feat data has now been loaded");

        //crits
        line = br.readLine();
        if ("true".equals(line)){
            player.prof_crit = true;
        }
        if ("false".equals(line)){
            player.prof_crit = false;
        }

        line = br.readLine();
        if ("true".equals(line)){
            player.prof_imp_crit = true;
        }
        if ("false".equals(line)){
            player.prof_imp_crit = false;
        }

        line = br.readLine();
        if ("true".equals(line)){
            player.prof_perfect_crit = true;
        }
        if ("false".equals(line)){
            player.prof_perfect_crit = false;
        }

        line = br.readLine();
        player.crit_dmg_feat = Integer.parseInt(line);

        core.log("Load Feat",
                name + "s Crit Dmg feat data has now been loaded");
        //hp
        line = br.readLine();
        if ("true".equals(line)){
            player.prof_hp = true;
        }
        if ("false".equals(line)){
            player.prof_hp = false;
        }

        line = br.readLine();
        if ("true".equals(line)){
            player.prof_imp_hp = true;
        }
        if ("false".equals(line)){
            player.prof_imp_hp = false;
        }

        line = br.readLine();
        player.hp_feat = Integer.parseInt(line);

        line = br.readLine();
        if ("true".equals(line)){
            player.prof_hp_first_grant = true;
        }
        if ("false".equals(line)){
            player.prof_hp_first_grant = false;
        }

        core.log("Load Feat",
                name + "s HP Feat data has now been loaded");

        //mp
        line = br.readLine();
        if ("true".equals(line)){
            player.prof_mp = true;
        }
        if ("false".equals(line)){
            player.prof_mp = false;
        }

        line = br.readLine();
        if ("true".equals(line)){
            player.prof_imp_mp = true;
        }
        if ("false".equals(line)){
            player.prof_imp_mp = false;
        }

        line = br.readLine();
        player.mp_feat = Integer.parseInt(line);

        line = br.readLine();
        if ("true".equals(line)){
            player.prof_mp_first_grant = true;
        }
        if ("false".equals(line)){
            player.prof_mp_first_grant = false;
        }

        core.log("Load Feat",
                name + "s MP Feat data has now been loaded");

        br.close();

        core.log("Load Feat",
                name + "s feats have all been loaded now");
    }

    public void Load_Character_Inventory(String load_me, Player player) throws FileNotFoundException, IOException{
        //Read back the saved values from character
        BufferedReader br = new BufferedReader(new FileReader(load_me + core.player_inventory_data));

        String line = "";

        line = br.readLine();
        player.item.s_hp_pot = Integer.parseInt(line);

        line = br.readLine();
        player.item.m_hp_pot = Integer.parseInt(line);

        line = br.readLine();
        player.item.l_hp_pot = Integer.parseInt(line);

        line = br.readLine();
        player.item.f_hp_pot = Integer.parseInt(line);

        line = br.readLine();
        player.item.s_mp_pot = Integer.parseInt(line);

        line = br.readLine();
        player.item.m_mp_pot = Integer.parseInt(line);

        line = br.readLine();
        player.item.l_mp_pot = Integer.parseInt(line);

        line = br.readLine();
        player.item.f_mp_pot = Integer.parseInt(line);

        line = br.readLine();
        player.item.s_sword = Integer.parseInt(line);

        line = br.readLine();
        player.item.l_sword = Integer.parseInt(line);

        line = br.readLine();
        player.item.g_sword = Integer.parseInt(line);

        line = br.readLine();
        player.item.dagger = Integer.parseInt(line);

        line = br.readLine();
        player.item.w_staff = Integer.parseInt(line);

        line = br.readLine();
        player.item.i_staff = Integer.parseInt(line);

        line = br.readLine();
        player.item.rod = Integer.parseInt(line);

        line = br.readLine();
        player.item.i_rod = Integer.parseInt(line);

        line = br.readLine();
        player.item.robe = Integer.parseInt(line);

        line = br.readLine();
        player.item.l_armor = Integer.parseInt(line);

        line = br.readLine();
        player.item.m_armor = Integer.parseInt(line);

        line = br.readLine();
        player.item.h_armor = Integer.parseInt(line);

        line = br.readLine();
        player.item.s_armor = Integer.parseInt(line);

        line = br.readLine();
        player.item.d_armor = Integer.parseInt(line);

        line = br.readLine();
        player.item.b_shield = Integer.parseInt(line);

        line = br.readLine();
        player.item.s_shield = Integer.parseInt(line);

        line = br.readLine();
        player.item.m_shield = Integer.parseInt(line);

        line = br.readLine();
        player.item.l_shield = Integer.parseInt(line);

        line = br.readLine();
        player.item.t_shield = Integer.parseInt(line);

        br.close();

        core.log("Load Inventory",
                name + "s inventory data has now been loaded");
    }

    public void Load_Character_Quest(String load_me, Player player) throws FileNotFoundException, IOException{
//Read back the saved values from character
        BufferedReader br = new BufferedReader(new FileReader(load_me + core.player_quest_data));

        String line = "";

        line = br.readLine();
        player.encounters_completed = Integer.parseInt(line);

        line = br.readLine();
        player.alpha_kills = Integer.parseInt(line);

        line = br.readLine();
        player.arena_kills = Integer.parseInt(line);

        br.close();

        core.log("Load Quest",
                player.name + "s quest data has now been loaded");
    }

    public void Load_Character_Spells(String load_me, Player player) throws FileNotFoundException, IOException{
//Read back the saved values from character
        BufferedReader br = new BufferedReader(new FileReader(load_me + core.player_spell_data));

        String line = "";

        line = br.readLine();
        player.s_blizzard_rank = Integer.parseInt(line);
        line = br.readLine();
        player.s_cure_rank = Integer.parseInt(line);
        line = br.readLine();
        player.s_cura = Integer.parseInt(line);
        line = br.readLine();
        player.s_curaga = Integer.parseInt(line);
        line = br.readLine();
        player.s_death = Integer.parseInt(line);
        line = br.readLine();
        player.s_earth_rank = Integer.parseInt(line);
        line = br.readLine();
        player.s_fire_rank = Integer.parseInt(line);
        line = br.readLine();
        player.s_gravity = Integer.parseInt(line);
        line = br.readLine();
        player.s_harm_rank = Integer.parseInt(line);
        line = br.readLine();
        player.s_heal = Integer.parseInt(line);
        line = br.readLine();
        player.s_poison_rank = Integer.parseInt(line);
        line = br.readLine();
        player.s_protect_rank = Integer.parseInt(line);
        line = br.readLine();
        player.s_regen_rank = Integer.parseInt(line);
        line = br.readLine();
        player.s_shell = Integer.parseInt(line);
        line = br.readLine();
        player.s_thunder_rank = Integer.parseInt(line);
        line = br.readLine();
        player.s_tmp_str_rank = Integer.parseInt(line);
        line = br.readLine();
        player.s_wind_rank = Integer.parseInt(line);

        br.close();

        core.log("Load Spells",
                player.name + "s Spell data has now been loaded");
    }

    public void set_class_choice(String class_name){
        switch (class_name){
            case "fighter":
                ftr_lvl++;
                break;
            case "black mage":
                bmage_lvl++;
                break;
            case "white mage":
                wmage_lvl++;
                break;
            case "red mage":
                wiz_lvl++;
                break;
            case "dark knight":
                dk_lvl++;
                break;
            case "paladin":
                pal_lvl++;
                break;
        }

    }

    /**
     damage reduction modifier - con less then 10 is negative to your armor
     value. 10 is no change at all. 12 or higher is positive to your armor
     value. damage modifier.strength less then 10 is negative to your damage
     value. 10 is no change at all to the damage value. 12 or higher is positive
     to your damage value.
     */
    int set_modifiers(String stat_name, int value) throws IOException{
        int mod;

        switch (value){
            default:
                mod = 0;
                break;
            case 6:
            case 7:
                mod = -2;
                break;
            case 8:
            case 9:
                mod = -1;
                break;
            case 10:
            case 11:
                mod = 0;
                break;
            case 12:
            case 13:
                mod = 1;
                break;
            case 14:
            case 15:
                mod = 2;
                break;
            case 16:
            case 17:
                mod = 3;
                break;
            case 18:
            case 19:
                mod = 4;
                break;
            case 20:
            case 21:
                mod = 5;
                break;
            case 22:
            case 23:
                mod = 6;
                break;
            case 24:
            case 25:
                mod = 7;
                break;
        }

        core.log("stat modification",
                name + " now has a " + stat_name + " modifier of " + mod);

        return mod;

    }

}
