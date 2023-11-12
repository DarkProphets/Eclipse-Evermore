//Eclipse Evermore -> eclipse.blueprint -> Creature.java
//Kealdor created Creature.java on 10-28-2023 at 4:35:57 PM
package eclipse.blueprint;

public abstract class Creature {

    public String name;
    public int chp;
    protected int mhp;
    protected int xp;
    protected int gp;
    protected int dmg;
    public boolean Enemy;
    protected int cmp;//current mp
    protected int mmp;//max mp
    public int level;//for scaling enemies etc

    public int get_chp() {
        return chp;
    }

    public int get_xp() {
        return xp;
    }

    public int get_gp() {
        return gp;
    }

    public int get_dmg() {
        return dmg;
    }

    public int get_mhp() {
        return mhp;
    }

    public void set_chp(int x) {
        chp = get_chp() + x;
    }

    public void set_mhp(int x) {
        if (Enemy == false) {
            mhp = get_mhp() + x;
        } else {
            mhp = x;
        }
    }

    public void set_gp(int x) {
        if (Enemy == false) {
            gp = get_gp() + x;
        }
    }

    public void set_xp(int x) {
        xp = get_xp() + x;
    }

    public int get_cmp() {
        return cmp;
    }

    public void set_cmp(int x) {
        if (Enemy == false) {
            cmp = get_cmp() + x;
        }
    }

    public int get_mmp() {
        return mmp;
    }

    public void set_mmp(int x) {
        if (Enemy == false) {
            mmp = get_mmp() + x;
        }
    }

    public int get_level() {
        return level;
    }

    public int DM_Heal() {
        set_chp(get_mhp());
        return get_chp();
    }

    public int DM_Mana() {
        set_cmp(get_mmp());
        return get_cmp();
    }
}
