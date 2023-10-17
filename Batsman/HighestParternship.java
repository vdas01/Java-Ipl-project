package Batsman;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Collections;
import java.util.HashSet;
import java.util.PriorityQueue;
import java.util.Set;

class Pair implements Comparable<Pair> {
    int priority;
    String value;

    Pair(int priority, String value) {
        this.priority = priority;
        this.value = value;
    }

    @Override
    public int compareTo(Pair other) {
        // Compare Pairs based on their priority
        return Integer.compare(this.priority, other.priority);
    }
}

class Partnership{
      Set<String> ids = new HashSet<>();
      PriorityQueue<Pair> partner = new PriorityQueue<>(Collections.reverseOrder());
      PriorityQueue<Pair> highPartner = new PriorityQueue<>(Collections.reverseOrder());
      String deliveryPath = "deliveries.csv";
      String matchPath = "matches.csv";

      String prevId;
      String batsman1;
      String batsman2;
      int runs = 0;
      boolean c1 = false;
      boolean c2 = false;
      boolean first = true;

    public void addId(String year,String venue){
           try (BufferedReader br = new BufferedReader(new FileReader(matchPath))) {   
            br.readLine();

            String line;
            while ((line = br.readLine()) != null) {
                
                String[] columns = line.split(",");
                String matchId = columns[0];
                String curr_year = columns[1];
                String curr_venue = columns[2];

                if(curr_year.equals(year) && curr_venue.equals(venue)){
                    ids.add(matchId);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void getPartnership(String year, String venue){
         addId(year, venue);


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
                          int v = partner.peek().priority;
                          String name = partner.peek().value;
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
            System.out.println(highPartner.peek().value + " " + highPartner.peek().priority);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}

public class HighestParternship {

    public static void main(String[] args) {
         Partnership p = new Partnership();
        p.getPartnership("2015","Bangalore");
    }   
}
