package batsman;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Collections;
import java.util.HashSet;
import java.util.PriorityQueue;
import java.util.Set;

import utility_classes.Pair;
import utility_classes.StoredId;




public class HighestParternship{
      
    public static void getPartnership(String year, String venue){
      PriorityQueue<Pair> partner = new PriorityQueue<>(Collections.reverseOrder());
      PriorityQueue<Pair> highPartner = new PriorityQueue<>(Collections.reverseOrder());
      String deliveryPath = "data/deliveries.csv";
      Set<String> ids = new HashSet<>();
     

      String prevId = "";
      String batsman1 = "";
      String batsman2 = "";
      int runs = 0;
      boolean c1 = false;
      boolean c2 = false;
      boolean first = true;

         ids = StoredId.getIds(year, venue);


         try (BufferedReader br = new BufferedReader(new FileReader(deliveryPath))) {   
            br.readLine();

            String line;
            while ((line = br.readLine()) != null) {
                
                String[] columns = line.split(",");
                String matchId = columns[0]; 
                String curr_batsman1 = columns[6]; //striker
                String curr_batsman2 = columns[7]; //non-striker;
                int curr_runs = Integer.parseInt(columns[15]);

                
                if(ids.contains(matchId)){
                   if(first || prevId.equals(matchId)){
                          
                    if(columns.length > 18){
                        String dismissed = columns[18];
                        if(dismissed.equals(batsman1))
                        c1 = true;
                        else
                        c2 = true;

                        partner.add(new Pair(runs,batsman1 + " and " + batsman2 + " scored:- "));
                        runs = 0;
                    }
                    else{
                        if(c1){
                            c1 = false;
                            batsman1 = (curr_batsman1.equals(batsman2) ? curr_batsman2 : curr_batsman1);
                        }
                        else if(c2){
                            c2 = false;
                            batsman2 = (curr_batsman1.equals(batsman1) ? curr_batsman2 : curr_batsman1);
                        }
                        else if(first){
                            batsman1 = curr_batsman1;
                            batsman2 = curr_batsman2;
                            first = false;
                            prevId = matchId;
                        }
                        runs += curr_runs;
                    }
                   }
                   else{
                          int v = partner.peek().getPriority();
                          String name = partner.peek().getValue();
                        //   System.out.print(v + " "+ name);
                          partner.clear();
                          highPartner.add(new Pair(v,name));

                          batsman1 = curr_batsman1;
                          batsman2 = curr_batsman2;
                          runs = curr_runs;
                          prevId = matchId;

                   }


                }

            }
            //highest parternship in a year and in a venue:-
            System.out.println("Highest Partenrnship in year " + year + " by:- ");
            System.out.println(highPartner.peek().getValue() + " " + highPartner.peek().getPriority());
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static void main(String[] args) {
        getPartnership("2016","Bangalore");
    }

}

 
