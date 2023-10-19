package batsman;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Set;

import utility_classes.Pair2;
import utility_classes.StoredId;

//Q) highest strike rate in a year and venue;
public class StrikeRate{
    private static String deliveryPath = "data/deliveries.csv";
    private static Set<String> ids;
    private static Map<String,Integer>batsmanRuns = new HashMap<>();
    private static Map<String,Integer> batsmanBalls = new HashMap<>();

    private static void calculateStrikeRate(String batsman_name){
            int runs_scored = batsmanRuns.getOrDefault(batsman_name, 0);
            int balls_faced = batsmanBalls.getOrDefault(batsman_name, 0);
              float strikeRate = ((float)runs_scored/(float)balls_faced) * 100.0f;

  
          System.out.println(batsman_name + ", Stirke rate:- " + strikeRate);
    }

    private static void calculateMostStrikeRate(String year,String venue){
        PriorityQueue<Pair2> mostStrikeRates = new PriorityQueue<>(Collections.reverseOrder());

        for (Map.Entry<String, Integer> entry : batsmanRuns.entrySet()){
             int runs_scored = entry.getValue();
            int balls_faced = batsmanBalls.getOrDefault(entry.getKey(), 0);
              float strikeRate = ((float)runs_scored/(float)balls_faced) * 100.0f;
              mostStrikeRates.add(new Pair2(strikeRate,entry.getKey()));
        }

        System.out.println("Most strike batsman in " + year + " and " + venue + ":- ");
        System.out.println("Batsman:- " + mostStrikeRates.peek().getValue() + ", Strike Rate:- " + mostStrikeRates.peek().getPriority()) ;
    }
    
    //calculate runs and balls faced
    public static void getStrikeRate(String batsman,String year,String city){
       
        ids = StoredId.getIds(year, city);

        try (BufferedReader br = new BufferedReader(new FileReader(deliveryPath))) {   
            br.readLine();

            String line;
            while ((line = br.readLine()) != null) {
                
                String[] columns = line.split(",");
                String matchId = columns[0];
                String currBatsman = columns[6];
                Integer runs_scored = Integer.parseInt(columns[15]);

                if(ids.contains(matchId)){
                    int prevRuns = batsmanRuns.getOrDefault(currBatsman,0);
                    batsmanRuns.put(currBatsman,prevRuns + runs_scored);

                    int prevBalls = batsmanBalls.getOrDefault(currBatsman,0);
                    batsmanBalls.put(currBatsman,prevBalls + 1);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        calculateStrikeRate(batsman);

    }
   

     public static void mostStrikeRate(String year,String city){
       
        ids = StoredId.getIds(year, city);

        try (BufferedReader br = new BufferedReader(new FileReader(deliveryPath))) {   
            br.readLine();

            String line;
            while ((line = br.readLine()) != null) {
                
                String[] columns = line.split(",");
                String matchId = columns[0];
                String currBatsman = columns[6];
                Integer runs_scored = Integer.parseInt(columns[15]);

                if(ids.contains(matchId)){
                    int prevRuns = batsmanRuns.getOrDefault(currBatsman,0);
                    batsmanRuns.put(currBatsman,prevRuns + runs_scored);

                    int prevBalls = batsmanBalls.getOrDefault(currBatsman,0);
                    batsmanBalls.put(currBatsman,prevBalls + 1);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

       calculateMostStrikeRate(year, city);

    }

 
}