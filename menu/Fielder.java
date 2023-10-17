package menu;

import java.util.Scanner;

import team.ExtraRunConcededPerTeam;
import team.MostCatches;
import team.NoOfMatchesPlayed;
import team.NoOfMatchesWon;
import team.TossWon;

public class Fielder {
    static Scanner sc = new Scanner(System.in);
    public static void getFielderStats(){
        int choice = 0;
        String year;

        System.out.println("Here are some stats available in fielder stats");
        System.out.println("Press 1 for FIND EXTRA RUNS CONCEDED PER TEAM");
        System.out.println("Press 2 for MOST CATCHES IN A YEAR");
        System.out.println("Press 3 for NO OF MATCHES PLAYED IN IPL PER SEASON");
        System.out.println("Press 4 for NO OF MATCHES WON IN ALL SEASONS BY TEAMS");
        System.out.println("Press 5 for FIND PERCENTAGE OF MOST TOSS WON BY ANY TEAM");

        choice = sc.nextInt();
        switch(choice){
            case 1:
              ExtraRunConcededPerTeam.getExtraRunsConceded();
              break;
            case 2:
               System.out.println("Enter the year");
               year = sc.next();
               MostCatches.getMostCatchesPlayer(year);
               break;
            case 3:
               NoOfMatchesPlayed.getMatchesPlayed();
               break;
            case 4:
                NoOfMatchesWon.getMatchesWon();
                break;
            case 5:
                System.out.println("Enter year");
                year = sc.next();
                TossWon.getTossWin(year);
            default:
                 System.out.println("Wrong choice. Enter between 1-4");
        }

 }
}
