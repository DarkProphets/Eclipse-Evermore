//Eclipse Evermore -> eclipse -> core.java
//Kealdor created core.java on 10-28-2023 at 7:55:28 PM
package eclipse;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Timestamp;
import java.util.Date;
import java.util.Random;

public class core {

    public static int pre_alpha_fight = 0;
    public static String game_root = null;
    public static String log_root = null;
    public final static String player_data = ".cha";
    public final static String player_feat_data = ".fea";
    public final static String player_inventory_data = ".inv";
    public final static String player_quest_data = ".qst";
    public final static String player_spell_data = ".spl";
    //public final static String player_bank_data = ".bnk";
    //public final static String shared_banked_data = "common.bnk";
    //public final static String shared_party_inventory_data = "common.inv";

    public static int Dice_Parser(String s) throws IOException {
        Random random = new Random();
        int result = 0;

        int dice_count = Integer.parseInt((s.substring(0, (s.indexOf("d")))));
        int dice_sides = Integer.parseInt((s.substring((s.indexOf("d") + 1), s.length())));

        log("dice roll - dice count", "About to roll " + dice_count + "d" + dice_sides);
        for (int i = 0; i < dice_count; i++) {
            int random_number = random.nextInt(dice_sides);
            log("dice roll - result", "Rolled a " + random_number);
            result = result + random_number;

            if (i == dice_count) {
                i = dice_count + 1;
                log("dice roll - final result", result + " as a result of " + s + " being rolled");
            }
        }

        return result;
    }

    public static void Folder_Creation(String folder_name) throws IOException {
        String home = System.getProperty("user.home");
        game_root = home + "./Evermore";
        File folder = new File(game_root + "/" + folder_name);
        log_root = folder.getCanonicalPath();
        if (!folder.exists()) {
            folder.mkdirs();
        }
    }

    public static void log(String function, String data) throws IOException {
        Timestamp time = new Timestamp(System.currentTimeMillis());
        Date date = new Date(time.getTime());
        String Date = date.toString();

        Folder_Creation("logs");
        //year needed this way because per function description of date.getYear() it subtracts 1900 from current year
        int year = date.getYear() + 1900;
        //File file = new File("./log" + "/debug_" + date.getMonth() + "_" + date.getDate() + "_" + year + ".log");
        File file = new File(log_root + "/debug_" + date.getMonth() + "_" + date.getDate() + "_" + year + ".log");
        file.createNewFile();

        try (FileWriter FileWriter = new FileWriter(file, true); PrintWriter PrintWriter = new PrintWriter(FileWriter)) {

            PrintWriter.println(Date.substring(0, 11)
                    + Date.substring(Date.length() - 4, Date.length()) + " "
                    + Date.substring(11, 20)
                    + Date.substring(Date.length() - 8, Date.length() - 5)
                    + " - " + function.substring(0, 1).toUpperCase()
                    + function.substring(1, function.length())
                    + " - " + data.substring(0, 1).toUpperCase()
                    + data.substring(1, data.length()) + " ");

            PrintWriter.close();
            FileWriter.close();
        }
    }

}
