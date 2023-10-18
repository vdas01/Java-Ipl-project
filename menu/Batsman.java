package menu;

import java.util.Scanner;

import batsman.FindPlayerMostSixes;
import batsman.HighestParternship;
import batsman.MostDucks;
import batsman.NoOfFours;
import batsman.StrikeRate;

public class Batsman {
    static Scanner scanner = new Scanner(System.in);

    public static void getBatsmanStats(){
        int choice = 0;
        String year,venue;
        System.out.println("Here are some stats available in batsman stats");
        System.out.println("Press 1 for FIND PLAYER WITH MOST SIXES");
        System.out.println("Press 2 for FIND HIGHEST PARTNERSHIP");
        System.out.println("Press 3 for FIND PLAYER WITH MOST DUCKS");
        System.out.println("Press 4 for FIND PLAYER WITH MOST FOURS");
        System.out.println("Press 5 for FIND PLAYER WITH MOST STRIKE RATE");

       choice = scanner.nextInt();
       switch(choice){
        case 1:
           System.out.println("Enter year and venue");
           year = scanner.next();
           scanner.nextLine();
           venue = scanner.next();
           FindPlayerMostSixes.mostSixBatsman(year,venue);
           break;
        case 2:
            System.out.println("Enter year and venue");
           year = scanner.next();
           scanner.nextLine();
           venue = scanner.next();
           HighestParternship.getPartnership(year, venue);
           break;
        case 3:
          MostDucks.getMostDucks();
          break;
        case 4:
           NoOfFours.getMostFours();
        break;
        case 5:
         System.out.println("Enter year and venue");
           year = scanner.next();
           scanner.nextLine();
           venue = scanner.next();
        StrikeRate.mostStrikeRate(year, venue);;
        break;
        default:
        System.out.println("Wrong choice. Choose between 1-5");

           
       }
   }
   
}
