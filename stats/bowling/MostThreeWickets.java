package bowling;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Set;

import utility_classes.Pair;
import utility_classes.StoredId;



public class MostThreeWickets{
    private static String deliveryPath_ = "data/deliveries.csv";
   private static String prevId_;
   private static boolean first_ = true;
   private static Map<String,Integer> bowlerWicket_ = new HashMap<String,Integer>();
   private static Map<String,Integer> threeWickets_ = new HashMap<>();
   private static Set<String> matchIds_;


    private static void getMostThreeWickets() {
       
        PriorityQueue<Pair>  mostThreeWickets = new PriorityQueue<>(Collections.reverseOrder());
         for (Map.Entry<String, Integer> entry : threeWickets_.entrySet()){
            mostThreeWickets.add(new Pair(entry.getValue(), entry.getKey()));
            // System.out.println(entry.getValue() + " " + entry.getKey());
         }

         System.out.println("Most Three wickets :- ");
         for(Pair elem:mostThreeWickets){
            System.out.println(elem.getValue() + " " + elem.getPriority());
         }

         
    }

    public static void allThreeWickets(String year) {
             matchIds_ = StoredId.getIds(year);

         try (BufferedReader br = new BufferedReader(new FileReader(deliveryPath_))){   
            br.readLine();

            String line;
            while ((line = br.readLine()) != null){
                
                String[] columns = line.split(",");
                String currId = columns[0];
                
                if(first_ || matchIds_.contains(currId)){
                    
                   if(columns.length > 18){
                        
                      if(first_ || prevId_.equals(currId)){
                        //  System.out.print("hi");
                        first_ = false;
                       String curr_bowler = columns[8];
                       String dismissal_kind = columns[19];
                       if(!dismissal_kind.equals("run out")){
                          int prev_wicket = bowlerWicket_.getOrDefault(curr_bowler,0);
                          bowlerWicket_.put(curr_bowler,prev_wicket + 1);
                        //   System.out.println(bowlerWicket_);
                       }
                       prevId_ = currId;
                     }
                     else{
                        for (Map.Entry<String, Integer> entry : bowlerWicket_.entrySet()){
                             if(entry.getValue().equals(3)){
                                String bowler = entry.getKey();
                                 int prev_five = threeWickets_.getOrDefault(bowler, 0);
                                 threeWickets_.put(bowler,prev_five + 1);
                             }
                        }
                        bowlerWicket_.clear();
                        prevId_ = currId;
                        if(matchIds_.contains(currId)){
                             String curr_bowler = columns[8];
                             String dismissal_kind = columns[19];
                             if(!dismissal_kind.equals("run out")){
                                int prev_wicket = bowlerWicket_.getOrDefault(curr_bowler,0);
                                bowlerWicket_.put(curr_bowler,prev_wicket + 1);
                             }
                        }
                     }

                   }
                }
               
            }
        } catch (IOException e){
            e.printStackTrace();
        }

         getMostThreeWickets();
        // System.out.println(threeWickets);
    }
    
}

