package bowling;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
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

class Matches {
   String matchPath = "data/matches.csv";
    Set<String> matchIds = new HashSet<>();

    public void getIds(String year) {
        try (BufferedReader br = new BufferedReader(new FileReader(matchPath))){   
            br.readLine();

            String line;
            while ((line = br.readLine()) != null){
                
                String[] columns = line.split(",");
                String matchId = columns[0];
                String curr_year = columns[1];

                if(curr_year.equals(year)){
                    matchIds.add(matchId);
                }
            }
        } catch (IOException e){
            e.printStackTrace();
        }
    }
}

class Deliveries{
    String deliveryPath = "data/deliveries.csv";
    String prevId;
    boolean first = true;
    Map<String,Integer> bowler_wicket = new HashMap<String,Integer>();
    // PriorityQueue<Pair> five_wickets = new PriorityQueue<>();
    Map<String,Integer> threeWickets = new HashMap<>();

    public void allThreeWickets(Set<String>matchIds) {
         try (BufferedReader br = new BufferedReader(new FileReader(deliveryPath))){   
            br.readLine();

            String line;
            while ((line = br.readLine()) != null){
                
                String[] columns = line.split(",");
                String currId = columns[0];
                
                if(first || matchIds.contains(currId)){
                    
                   if(columns.length > 18){
                        
                      if(first || prevId.equals(currId)){
                        //  System.out.print("hi");
                        first = false;
                       String curr_bowler = columns[8];
                       String dismissal_kind = columns[19];
                       if(!dismissal_kind.equals("run out")){
                          int prev_wicket = bowler_wicket.getOrDefault(curr_bowler,0);
                          bowler_wicket.put(curr_bowler,prev_wicket + 1);
                        //   System.out.println(bowler_wicket);
                       }
                       prevId = currId;
                     }
                     else{
                        for (Map.Entry<String, Integer> entry : bowler_wicket.entrySet()){
                             if(entry.getValue().equals(3)){
                                String bowler = entry.getKey();
                                 int prev_five = threeWickets.getOrDefault(bowler, 0);
                                 threeWickets.put(bowler,prev_five + 1);
                             }
                        }
                        bowler_wicket.clear();
                        prevId = currId;
                        if(matchIds.contains(currId)){
                             String curr_bowler = columns[8];
                             String dismissal_kind = columns[19];
                             if(!dismissal_kind.equals("run out")){
                                int prev_wicket = bowler_wicket.getOrDefault(curr_bowler,0);
                                bowler_wicket.put(curr_bowler,prev_wicket + 1);
                             }
                        }
                     }

                   }
                }
               
            }
        } catch (IOException e){
            e.printStackTrace();
        }

        // System.out.println(threeWickets);
    }

    public void getMostThreeWickets() {
        PriorityQueue<Pair>  mostThreeWickets = new PriorityQueue<>(Collections.reverseOrder());

         for (Map.Entry<String, Integer> entry : threeWickets.entrySet()){
            mostThreeWickets.add(new Pair(entry.getValue(), entry.getKey()));
            // System.out.println(entry.getValue() + " " + entry.getKey());
         }

         System.out.println("Most Three wickets :- ");
         for(Pair elem:mostThreeWickets){
            System.out.println(elem.value + " " + elem.priority);
         }

         
    }
    
}

public class MostThreeWickets {
    


    public static void main(String[] args) {
        Matches match = new Matches();
        match.getIds("2016");

        Deliveries delivery = new Deliveries();
        delivery.allThreeWickets(match.matchIds);
        delivery.getMostThreeWickets();
    }
}
