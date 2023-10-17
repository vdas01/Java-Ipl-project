package menu;
import java.util.Scanner;

public class Main {
   static Scanner scanner = new Scanner(System.in);
    public static void main(String[] args) {
          int choice;

       System.out.println("WELCOME TO VISHAL IPL DATABASE ");
       System.out.println("Press 1 for batsman stats, 2 for bowler stats and 3 for fielder/team stats:- ");
       
       choice = scanner.nextInt();
       switch(choice){
           case 1:
              Batsman.getBatsmanStats();
              break;
            case 2:
               Bowler.getBowlerStats();
               break;
            case 3:
               Fielder.getFielderStats();
               break;
            default:
               System.out.println("Wrong choice. Please choose between 1-3");

       }
    }
}