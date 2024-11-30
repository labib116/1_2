package Database;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PlayerDatabase{

    private List<Player> database = new ArrayList<>();
    private int playercount = 0;
    public List<Player>newly_added = new ArrayList<>();
    private static final String INPUT_FILE_NAME = "players.txt";
    private static final String OUTPUT_FILE_NAME = "out.txt";

    private Player extractPlayerInfo(String line) {
        try {
            String[] parts = line.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)");

            if (parts.length != 8) {
                throw new IllegalArgumentException("Invalid number of fields in line");
            }

            // Extract and parse each field
            String name = parts[0].trim();
            String country = parts[1].trim();
            int age = Integer.parseInt(parts[2].trim());
            double height = Double.parseDouble(parts[3].trim()); // Changed to double
            String club = parts[4].trim();
            String position = parts[5].trim();
            int number = parts[6].trim().isEmpty() ? -1 : Integer.parseInt(parts[6].trim()); // Handle missing jersey numbe
            int weeklySalary = Integer.parseInt(parts[7].trim());

            // Return the Database.Database.Player object
            return new Player(name, age, country, height, club, position, number, weeklySalary);

        } catch (Exception e) {
            System.err.println("Error parsing line: " + line);
            return null;
        }
    }

    public void load() {
        try (BufferedReader br = new BufferedReader(new FileReader(INPUT_FILE_NAME))) {
            String line;

            // Read each line until the end of the file
            while ((line = br.readLine()) != null) {
                // Extract player information from the line
                Player player = extractPlayerInfo(line);

                // Add the player to the database if parsing was successful
                if (player != null) {
                    database.add(player);
                    //System.out.println("Added player: " + player.getName());
                } else {
                    System.err.println("Failed to add player from line: " + line);
                }
            }

        } catch (Exception e) {
            // Handle exceptions like file not found or read errors
            System.err.println("Error while creating the database: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void addPlayer(Player p) {
        if (p != null) {
            //System.out.println(p.getName() + " added to the database");
            for (Player i : database) {
                if (i.getName().equalsIgnoreCase(p.getName())) {
                    System.out.println("Player already exists");
                    return;
                }
            }
            database.add(p);
            newly_added.add(p);
            playercount++;
        } else {
            System.out.println("Player not added");
        }

        String text = p.getName() + "," + p.getCountry() + "," + p.getAge() + "," + p.getHeight() + "," + p.getClub() + "," + p.getPosition() + "," + p.getNumber() + "," + p.getWeekly_salary();
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(INPUT_FILE_NAME, true))) {
            bw.write(text);
            bw.newLine();
        } catch (Exception e) {
            System.err.println("Error while writing to the file: " + e.getMessage());
            e.printStackTrace();
        }
    }
    public void add_to_file(List<Player> players) {
        for (Player p : players) {
            String text = p.getName() + "," + p.getCountry() + "," + p.getAge() + "," + p.getHeight() + "," + p.getClub() + "," + p.getPosition() + "," + p.getNumber() + "," + p.getWeekly_salary();
            try (BufferedWriter bw = new BufferedWriter(new FileWriter(INPUT_FILE_NAME, true))) {
                bw.write(text);
                bw.newLine();
            } catch (Exception e) {
                System.err.println("Error while writing to the file: " + e.getMessage());
                e.printStackTrace();
            }
        }
    }
    public void exit(){
        add_to_file(newly_added);
    }

    public void printPlayers() {
        for (Player p : database) {
            showPlayerInfo(p.getName());
        }
    }

    private Player findbyName(String name) {
        for (Player p : database) {
            if (name.equalsIgnoreCase(p.getName())) {
                return p;
            }
        }
        return null;
    }

    public void showPlayerInfo(String name) {

        if (findbyName(name) != null) {
            Player p = findbyName(name);
            System.out.println(p);
        }
        else{
            System.out.println("Player not found");
        }
    }

    private List<Player> findbyClubAndCountry(String Club, String Country) {
        //System.out.println("Find by Club and Country func called successfully");
        List<Player> result = new ArrayList<>();
        for (Player p : database) {
            if (Club.equalsIgnoreCase(p.getClub())) {
                if (Country.equalsIgnoreCase(p.getCountry())) {
                    //System.out.println(p.getName());
                    result.add(p);
                } else if (Country.equalsIgnoreCase("Any")) {
                    System.out.println(p.getName());
                    result.add(p);
                }
            }

        }
        if (result.isEmpty()) {
            System.out.println("No Such Player Available");
        }
        return result;

    }

    public void showPlayerInfoByClubAndCountry(String Club, String Country) {
        //System.out.println("Show Database.Database.Player Info func called successfully");
        List<Player> answer = findbyClubAndCountry(Club, Country);
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

    private List<Player> findbyPosition(String position) {
        List<Player> result = new ArrayList<>();
        for (Player p : database) {
            if (position.equalsIgnoreCase(p.getPosition())) {
                result.add(p);
            }
        }
        return result;
    }

    public void showPlayerInfoByPosition(String position) {
        List<Player> answer = new ArrayList<>();
        answer = findbyPosition(position);
        if (findbyPosition(position) != null) {

            for (Player p : answer) {
               showPlayerInfo(p.getName());
            }
        } else {
            if (answer.size() == 0) {
                System.out.println("No Such Player Available");
            }

        }
    }

    private List<Player> findbySallaryRange(int start, int end) {
        List<Player> result = new ArrayList<>();
        for (Player p : database) {
            if (p.getWeekly_salary() >= start && p.getWeekly_salary() <= end) {
                result.add(p);
            }
        }
        return result;
    }

    public void showPlayerInfoBySallaryRange(int start, int end) {
        List<Player> answer = new ArrayList<>();
        answer = findbySallaryRange(start, end);
        if (!answer.isEmpty()) {

            answer.sort((p1, p2) -> p1.getWeekly_salary() - p2.getWeekly_salary());
            for (Player p : answer) {
                showPlayerInfo(p.getName());
            }
        } else {
            if (answer.size() == 0) {
                System.out.println("No Such Player Available");
            }
        }
    }

    public void showByCountry() {
        HashMap<String, Integer> countryCount = new HashMap<>();

        for (Player p : database) {
            countryCount.put(p.getCountry(), countryCount.getOrDefault(p.getCountry(), 0) + 1);
        }
        for (Map.Entry<String, Integer> entry : countryCount.entrySet()) {
            System.out.println("Country: " + entry.getKey() + ", Players: " + entry.getValue());
        }
    }
    private List<Player>find_max_sallary_players(String Club) {
        List<Player> result = new ArrayList<>();
        int max_sallary = 0;
        for (Player p : database) {
            if (p.getWeekly_salary() > max_sallary && Club.equalsIgnoreCase(p.getClub())) {
                max_sallary = p.getWeekly_salary();
            }
        }
        for (Player p : database) {
            if (p.getWeekly_salary() == max_sallary && Club.equalsIgnoreCase(p.getClub())) {
                result.add(p);
            }

        }
        return result;
    }
    public void show_max_sallary_players(String Club) {
        List<Player> answer = new ArrayList<>();
        answer= find_max_sallary_players(Club);
        if (!answer.isEmpty()) {
            for (Player p : answer) {

                System.out.println(p);
        }
        }
    }

    private List<Player>findMaxHeight(String Club){
        List<Player>answer=new ArrayList<>();
        double max_height = 0;
        for (Player p : database) {
            if (p.getHeight() > max_height && Club.equalsIgnoreCase(p.getClub())) {
                max_height = p.getHeight();
            }
        }
        for (Player p : database) {
            if (p.getHeight() == max_height && Club.equalsIgnoreCase(p.getClub())) {
                answer.add(p);
            }
        }
        return answer;
    }
    public void show_max_height_players(String Club) {
        List<Player>answer=new ArrayList<>();
        answer = findMaxHeight(Club);
        for (Player p : answer) {
            showPlayerInfo(p.getName());
        }
    }
    private List<Player>findMaxAge(String Club){
        List<Player>answer=new ArrayList<>();
        int max_age = 0;
        for (Player p : database) {
            if (p.getAge() > max_age && Club.equalsIgnoreCase(p.getClub())) {
                max_age = p.getAge();
            }
        }
        for (Player p : database) {
            if (p.getAge() == max_age && Club.equalsIgnoreCase(p.getClub())) {
                answer.add(p);
            }
        }
        return answer;
    }
    public void show_max_age_players(String Club) {
       List<Player>answer=new ArrayList<>();
       answer = findMaxAge(Club);
       for (Player p : answer) {
           showPlayerInfo(p.getName());
       }
    }

    public void show_total_yearly_sallary(String Club) {
        int total_sallary = 0;
        for (Player p : database) {
            if (Club.equalsIgnoreCase(p.getClub())) {
                total_sallary += p.getWeekly_salary() * 52;
            }
        }
        if (total_sallary == 0) {
            System.out.println("No Such Club Available");
        } else {
            System.out.println("Total Yearly Sallary of " + Club + " is " + total_sallary);
        }
    }
}
