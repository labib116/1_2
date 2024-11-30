package IO;

import Database.Player;
import Database.PlayerDatabase;

import java.util.Scanner;
public class Menu {
    PlayerDatabase playerDatabase;
    public Menu(PlayerDatabase playerDatabase) {
        this.playerDatabase = playerDatabase;
    }


    public int  load() {
        System.out.println("Welcome to the player database!");
        System.out.println("Select an option:");
        System.out.println("1. Search for a player");
        System.out.println("2. Search Clubs");
        System.out.println("3. Add a new player");
        System.out.println("4. Exit System");
        Scanner sc=new Scanner(System.in);
        int choice=sc.nextInt();
        return choice;
    }
    public void Playersearch() {

        boolean playersearchflag=true;
        while(playersearchflag){
            System.out.println("Database.Player Searching options:");
            System.out.println("1. Search by name");
            System.out.println("2.Search by Club & Country");
            System.out.println("3.Search by position");
            System.out.println("4.Search by player's salary range");
            System.out.println("5.Country wise player count");
            System.out.println("6.Back to MainMenu");
            Scanner sc=new Scanner(System.in);
            int choice=sc.nextInt();
            switch (choice) {
                case 1:
                    System.out.println("Enter the name of the player you want to search:");
                    Scanner sc1=new Scanner(System.in);
                    String name=sc1.nextLine();
                    playerDatabase.showPlayerInfo(name);
                    break;
                case 2:
                    System.out.println("Enter the Club & Country of the player you want to search:");
                    System.out.println("Club:");
                    Scanner sc2=new Scanner(System.in);
                    String Club=sc2.nextLine();
                    System.out.println("Country:");
                    String Country=sc2.nextLine();
                    //System.out.println(Club+" "+Country);
                    playerDatabase.showPlayerInfoByClubAndCountry(Club, Country);
                    break;
                case 3:
                    System.out.println("Enter the position of the player you want to search:");
                    Scanner sc3=new Scanner(System.in);
                    String position=sc3.nextLine();
                    playerDatabase.showPlayerInfoByPosition(position);
                    break;
                case 4:
                    System.out.println("Enter the salary range of the player you want to search:");
                    Scanner sc4=new Scanner(System.in);
                    System.out.println("Minimum Salary:");
                    int min=sc4.nextInt();
                    System.out.println("Maximum Salary:");
                    int max=sc4.nextInt();
                    playerDatabase.showPlayerInfoBySallaryRange(min, max);

                    break;
                case 5:
                    playerDatabase.showByCountry();
                    break;
                case 6:
                    //load();

                    playersearchflag=false;
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }

    }
    public void Clubsearch() {


        boolean clubsearchflag=true;
        while(clubsearchflag){
            System.out.println("Club Searching options:");
            System.out.println("1. Player with the maximum salary in a club");
            System.out.println("2. Player with the maximum age of a club");
            System.out.println("3. Player with the maximum height of a club");
            System.out.println("4.Total yearly salary of a club");
            System.out.println("5.Back to Main Menu");
            String Club;
            Scanner sc=new Scanner(System.in);
            int choice=sc.nextInt();
            switch (choice) {
                case 1:
                    System.out.println("Enter the name of the Club you want to search:");
                    Scanner sc1=new Scanner(System.in);
                    Club=sc1.nextLine();
                    playerDatabase.show_max_sallary_players(Club);
                    break;
                case 2:
                    System.out.println("Enter the name of the Club you want to search:");
                    Scanner sc2=new Scanner(System.in);
                    Club=sc2.nextLine();
                    playerDatabase.show_max_age_players(Club);
                    break;
                case 3:
                    System.out.println("Enter the name of the Club you want to search:");
                    Scanner sc3=new Scanner(System.in);
                    Club=sc3.nextLine();
                    playerDatabase.show_max_height_players(Club);
                    break;
                case 4:
                    System.out.println("Enter the name of the Club you want to search:");
                    Scanner sc4=new Scanner(System.in);
                    Club=sc4.nextLine();
                    playerDatabase.show_total_yearly_sallary(Club);
                    break;
                case 5:
                    clubsearchflag=false;
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }
    public void add() {
        System.out.println("Enter the details of the new player:");
        System.out.println("Name:");
        Scanner sc1=new Scanner(System.in);
        String name=sc1.nextLine();
        System.out.println("Country:");
        Scanner sc2=new Scanner(System.in);
        String country=sc2.nextLine();
        System.out.println("Age:");
        Scanner sc3=new Scanner(System.in);
        int age=sc3.nextInt();
        System.out.println("Height:");
        Scanner sc4=new Scanner(System.in);
        double height=sc4.nextDouble();
        System.out.println("Club:");
        Scanner sc5=new Scanner(System.in);
        String club=sc5.nextLine();
        System.out.println("Position:");
        Scanner sc6=new Scanner(System.in);
        String position=sc6.nextLine();
        System.out.println("Jersey Number:");
        Scanner sc7=new Scanner(System.in);
        int number=sc7.nextInt();
        System.out.println("Weekly Salary:");
        Scanner sc8=new Scanner(System.in);
        int weeklySalary=sc8.nextInt();
        Player p=new Player(name, age, country, height, club, position, number, weeklySalary);
        playerDatabase.addPlayer(p);

    }
    public void exit(){
        playerDatabase.exit();
    }

}

