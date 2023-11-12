//Eclipse Evermore -> eclipse.blueprint -> Enemy.java
//Kealdor created Enemy.java on 10-28-2023 at 4:38:29 PM
package eclipse.blueprint;

import java.util.Random;

public class Enemy extends Creature {

    final int MONSTERS_CREATED = 7;
    Enemy_Spells e_spell = new Enemy_Spells();

    //for random encounters
    Enemy(int level) {
        Random random = new Random();
        Enemy = true;
        int enemy_to_spawn = random.nextInt(MONSTERS_CREATED);

        while (enemy_to_spawn == 0 || enemy_to_spawn > MONSTERS_CREATED) {
            random.nextInt(MONSTERS_CREATED);
        }

        switch (enemy_to_spawn) {
            case 1:
                Imp();
                break;
            case 2:
                Bandit();
                break;
            case 3:
                DarkImp();
                break;
            case 4:
                Goblin();
                break;
            case 5:
                Hobgoblin();
                break;
            case 6:
                Wolf(1);
                break;
            case 7:
                WolfPup(1);
                break;
        }
        scale_enemy(this, level);
    }

    //for arena encounters
    Enemy(int level, String monster) {
        Enemy = true;
        int enemy_to_spawn = 0;

        switch (monster) {
            case "Imp":
                enemy_to_spawn = 1;
                break;
            case "Bandit":
                enemy_to_spawn = 2;
                break;
            case "DarkImp":
                enemy_to_spawn = 3;
                break;
            case "Goblin":
                enemy_to_spawn = 4;
                break;
            case "Hobgoblin":
                enemy_to_spawn = 5;
                break;
            case "Wolf":
                enemy_to_spawn = 6;
                break;
            case "WolfPup":
                enemy_to_spawn = 7;
                break;
        }
        //Using the original original Alpha values for creatures the Arena
        switch (enemy_to_spawn) {
            case 1:
                Arena_Imp();
                break;
            case 2:
                Bandit();
                break; //was first created after alpha
            case 3:
                Arena_DarkImp();
                break;
            case 4:
                Arena_Goblin();
                break;
            case 5:
                Arena_Hobgoblin();
                break;
            case 6:
                Wolf(2);
                break; //only has values from alpha
            case 7:
                WolfPup(2);
                break;//partial values from alpha      
        }
        scale_enemy(this, level);
    }

    public void spawn_enemy(String s, int x) {
        if (Enemy == true) {
            name = s;
            set_xp(x);
            set_gp(get_xp() * 2);
            set_mhp((get_xp() / 2) + 1);
            set_chp(get_mhp());
            dmg = (get_mhp() + 2 / 2);
        }
    }

    private void scale_enemy(Enemy e, int scale) {
        int i = 0;
        for (i = 0; i < scale; i++) {
            e.set_xp(e.get_xp() + e.get_xp());
            e.set_gp(e.get_gp() + e.get_gp());
            e.set_chp(e.get_chp() + e.get_chp());
            e.mhp = e.get_chp(); //needed for combat screen/text
            e.dmg = e.dmg + e.dmg;
        }
    }

    private void Imp() {
        spawn_enemy("Imp", 10);
    }

    private void Bandit() {
        spawn_enemy("Bandit", 14);
    }

    private void DarkImp() {
        spawn_enemy("Dark Imp", 15);
    }

    private void Goblin() {
        spawn_enemy("Goblin", 12);
    }

    private void Hobgoblin() {
        spawn_enemy("Hobgoblin", 16);
    }

    //the only change for wolf from arena to ranom encounter is hp so just using one constructor for it
    private void Wolf(int x) {
        //build Wolf manually with values from Alpha
        name = "Wolf";
        set_xp(95);
        set_gp(42);
        if (x == 2) {
            set_chp(50);
        }//Arena mode
        if (x == 1) {
            set_chp(20);
        }//random encounter mode
        mhp = get_chp();
        dmg = 16;
    }

    private void WolfPup(int x) {
        //Wolf pup hp to be equal to 28 no other data from Alpha besides damage
        //arena mode
        if (x == 2) {
            spawn_enemy("Wolf Pup", 54);
            dmg = (get_chp() + 2) / 2; //formula used for this in alpha
        }

        if (x == 1) {
            spawn_enemy("Wolf Pup", 54);
        }//random encounter mode
    }

    private void Arena_Imp() {
        name = "Imp";
        set_xp(20);
        set_gp(15);
        set_chp(10);
        mhp = get_chp();
        dmg = 5;
    }

    private void Arena_Hobgoblin() {
        name = "Hobgoblin";
        set_xp(70);
        set_gp(28);
        set_chp(30);
        mhp = get_chp();
        dmg = 12;
    }

    private void Arena_Goblin() {
        name = "Goblin";
        set_xp(30);
        set_gp(20);
        set_chp(13);
        mhp = get_chp();
        dmg = 7;
    }

    private void Arena_DarkImp() {
        name = "Dark Imp";
        set_xp(40);
        set_gp(23);
        set_chp(17);
        mhp = get_chp();
        dmg = 9;
    }
}
