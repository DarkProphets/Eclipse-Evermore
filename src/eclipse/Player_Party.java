//Eclipse Evermore -> eclipse -> Player_Party.java
//Kealdor created Player_Party.java on 10-28-2023 at 10:52:09 PM
package eclipse;

import eclipse.blueprint.Player;
import java.io.IOException;

public class Player_Party {

    public Player m1;
    public Player m2;
    public Player m3;
    public Player m4;

    public Player_Party() throws IOException {
        m1 = new Player();
        m2 = new Player();
        m3 = new Player();
        m4 = new Player();
    }

    public Player_Party(Player pm1, Player pm2, Player pm3, Player pm4) {
        m1 = pm1;
        m2 = pm2;
        m3 = pm3;
        m4 = pm4;
    }

    public void Add_Member(String name) throws IOException {
        if (m1.name == null) {
            core.log("Add party member",
                    "adding " + name + " to slot 1");
            m1.Load_Character(name, m1);
        }
        if (m1.name != null && m2.name == null) {
            core.log("Add party member",
                    "adding " + name + " to slot 2");
            m2.Load_Character(name, m2);
        }
        if (m1.name != null && m2.name != null && m3.name == null) {
            core.log("Add party member",
                    "adding " + name + " to slot 3");
            m3.Load_Character(name, m3);
        }
        if (m1.name != null && m2.name != null && m3.name != null && m4.name == null) {
            core.log("Add party member",
                    "adding " + name + " to slot 4");
            m4.Load_Character(name, m4);
        }

        if (m1.name != null && m2.name != null && m3.name != null && m4.name != null) {
            core.log("Add party member",
                    "out of space to add " + name + " to party");
            core.log("Add party member",
                    "removing first member to add " + name + " to party");
            Remove_Member(m1.name);
            Add_Member(name);
        }

    }

//    private void brute_force_add_member(String name) throws IOException {
//        if (m1.name != null && m2.name != null && m3.name != null && m4.name != null) {
//            core.log("Add party member",
//                    "saving " + m1.name + " from slot 1");
//            m1.Save_Character(m1);
//            core.log("Add party member",
//                    "removing " + m1.name + " from slot 1");
//            m1 = new Player();
//            core.log("Add party member",
//                    "adding " + name + " to slot 1");
//            m1.Load_Character(name, m1);
//        }
//    }
    public void Reset_Member_Slot(Player pm) throws IOException {
        pm = new Player();
        pm.name = null;
    }

    public void Remove_Member(String name) throws IOException {
        if (m1.name.equals(name)) {
            core.log("Remove party member",
                    "saving " + name + " from slot 1");
            m1.Save_Character(m1);
            core.log("Remove party member",
                    "removing " + m1.name + " from slot 1");
            Reset_Member_Slot(m1);
        }
        if (m2.name.equals(name)) {
            core.log("Remove party member",
                    "saving " + name + " from slot 2");
            m2.Save_Character(m2);
            core.log("Remove party member",
                    "removing " + m2.name + " from slot 2");
            Reset_Member_Slot(m2);
        }
        if (m3.name.equals(name)) {
            core.log("Remove party member",
                    "saving " + name + " from slot 3");
            m3.Save_Character(m3);
            core.log("Remove party member",
                    "removing " + m3.name + " from slot 3");
            Reset_Member_Slot(m3);
        }
        if (m4.name.equals(name)) {
            core.log("Remove party member",
                    "saving " + name + " from slot 4");
            m4.Save_Character(m4);
            core.log("Remove party member",
                    "removing " + m4.name + " from slot 4");
            Reset_Member_Slot(m4);
        }
    }
}
