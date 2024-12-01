package IO;
import Database.Player;
import Database.PlayerDatabase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Printing {
    PlayerDatabase printdatabase = new PlayerDatabase();
    public Printing(PlayerDatabase playerDatabase){
        this.printdatabase = playerDatabase;
    }
    public void showPlayerInfo(String name) {
        if (printdatabase.findbyName(name) != null) {
            Player p = printdatabase.findbyName(name);
            System.out.println(p);
        }
        else{
            System.out.println("Player not found");
        }
    }
    public void showPlayerInfoByClubAndCountry(String Club, String Country) {
        //System.out.println("Show Database.Database.Player Info func called successfully");
        List<Player> answer = printdatabase.findbyClubAndCountry(Club,Country).getDatabase();
        if (!answer.isEmpty()) {
            //System.out.println("loop started successfully");
            for (Player p : answer) {
                //System.out.println("Database.Database.Player name is:" + p.getName());
                showPlayerInfo(p.getName());
            }
        } else {
            if (answer.isEmpty()) {
                System.out.println("No Such Player Available");
            }

        }
    }
    public void showPlayerInfoByPosition(String position) {
        List<Player> answer = new ArrayList<>();
        answer = printdatabase.findbyPosition(position).getDatabase();
        if (!answer.isEmpty()) {

            for (Player p : answer) {
                System.out.println(p);
            }
        } else {
            if (answer.size() == 0) {
                System.out.println("No Such Player Available");
            }

        }
    }
    public void showPlayerInfoBySallaryRange(int start, int end) {
        List<Player> answer = new ArrayList<>();
        answer = printdatabase.findbySallaryRange(start, end).getDatabase();
        if (!answer.isEmpty()) {

            answer.sort((p1, p2) -> p1.getWeekly_salary() - p2.getWeekly_salary());
            for (Player p : answer) {
                System.out.println(p);
            }
        } else {
            if (answer.size() == 0) {
                System.out.println("No Such Player Available");
            }
        }
    }
    public void show_max_sallary_players(String Club) {
        List<Player> answer = new ArrayList<>();
        answer= printdatabase.find_max_sallary_players(Club).getDatabase();
        if (!answer.isEmpty()) {
            for (Player p : answer) {

                System.out.println(p);
            }
        }
    }
    public void show_max_height_players(String Club) {
        List<Player>answer=new ArrayList<>();
        answer = printdatabase.findMaxHeight(Club).getDatabase();
        for (Player p : answer) {
            showPlayerInfo(p.getName());
        }
    }
    public void show_max_age_players(String Club) {
        List<Player>answer=new ArrayList<>();
        answer = printdatabase.findMaxAge(Club).getDatabase();
        for (Player p : answer) {
            showPlayerInfo(p.getName());
        }
    }
    public void show_total_yearly_sallary(String Club) {
       long total_sallary= printdatabase.find_total_sallary(Club);
       if(total_sallary>0){
           System.out.println("Total Sallary: "+total_sallary);
       }
       else {
           System.out.println("No Such Club Available");
       }
    }
    public void show_by_country() {
        // Assuming printdatabase.findByCountry() returns a HashMap<String, Integer>
        HashMap<String, Integer> answer = printdatabase.findByCountry();

        // Iterate through the HashMap and print the key-value pairs
        for (HashMap.Entry<String, Integer> entry : answer.entrySet()) {
            System.out.println("Country: " + entry.getKey() + ", Value: " + entry.getValue());
        }
    }
}



