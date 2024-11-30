package IO;

import Database.Player;
import Database.PlayerDatabase;
public class MainMenu {
    public static void main(String[] args) {
        PlayerDatabase playerDatabase = new PlayerDatabase();
        playerDatabase.load();

        Menu menu = new Menu(playerDatabase);
        boolean main_menu_flag=true;
        while (true) {
            int x = menu.load();
            switch (x) {
                case 1:
                    menu.Playersearch();
                    break;
                case 2:
                    menu.Clubsearch();
                    break;
                case 3:
                    menu.add();
                    break;
                case 4:
                    menu.exit();
                    main_menu_flag=false;
                    return;

                default:
                    System.out.println("Invalid choice, please try again.");
                    break;
            }
        }
    }
}
