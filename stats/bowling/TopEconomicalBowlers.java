package bowling;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Set;

import utility_classes.Pair2;
import utility_classes.StoredId;

public class TopEconomicalBowlers {

    public static void getTopEconomicalBowler(){
           String deliveriesPath = "data/deliveries.csv";

         Set<String> s2 =StoredId.getIds("2015");

        try (BufferedReader br = new BufferedReader(new FileReader(deliveriesPath))){
            // Assuming the first line contains headers, if not, skip this line
            br.readLine();

            //total runs of a bowler
            Map<String,Integer> runs = new HashMap<>();

            //total balls balled
            Map<String,Integer> balls = new HashMap<>();

            

            //Bolwer and economy
             Map<String,Float> economical = new HashMap<>();


            String line;
            while ((line = br.readLine()) != null) {
                
                String[] columns = line.split(",");

                String bowler = columns[8];
                int extraRuns = Integer.parseInt(columns[16]);
                int curr_run = Integer.parseInt(columns[17]);
                String id = columns[0];

                if(s2.contains(id) && extraRuns == 0){
                    int prev_runs = 0;
                    if(runs.containsKey(bowler)){

                        prev_runs = runs.get(bowler);

                    }

                    runs.put(bowler,prev_runs + curr_run);

              
                    int prev_balls = 0;
                    if(balls.containsKey(bowler)){
                       prev_balls = balls.get(bowler);
                    }
                    balls.put(bowler,prev_balls + 1);

              }

            }
            for(Map.Entry<String,Integer> temp: balls.entrySet()){
                String curr_bolwer = temp.getKey();
                int total_balls = temp.getValue();
                int total_overs = total_balls/6;
                int total_runs = runs.get(curr_bolwer);
                float economy = (float)total_runs/(float)total_overs;

                 economical.put(curr_bolwer,economy);
            }


           

            PriorityQueue<Pair2> sortedEconomy = new PriorityQueue<>();

           for(Map.Entry<String,Float> entry : economical.entrySet()){
               sortedEconomy.add(new Pair2(entry.getValue(),entry.getKey()));
           }

           
           System.out.println("Most Economical Bowler:- ");
          System.out.println(sortedEconomy.peek().getValue() + ", Economy :- " +sortedEconomy.peek().getPriority());

        //    for(Pair2 elem : sortedEconomy){
        //       System.out.println("Bowler :- " + elem.getValue() + ", " + "Economy :- " + elem.getPriority());
        //    }
            
           
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

   
}
