package menu;

import java.util.Scanner;
import bowling.MostThreeWickets;
import bowling.TopEconomicalBowlers;

public class Bowler {
    static Scanner scanner = new Scanner(System.in);

    public static void getBowlerStats(){
        int choice = 0;
        String year;

        System.out.println("Here are some stats available in bowler stats");
        System.out.println("Press 1 for FIND MOST THREE WICKETS");
        System.out.println("Press 2 for FIND TOP ECONOMICAL BOWLERS");

        choice = scanner.nextInt();
        switch(choice){
           case 1:
              System.out.println("Enter year");
              year = scanner.next();
              MostThreeWickets.allThreeWickets(year);
              break;
            case 2:
               TopEconomicalBowlers.getTopEconomicalBowler();
                break;
            default:
            System.out.println("Wrong choice. Choose between 1-2");             
        }

   }
}
