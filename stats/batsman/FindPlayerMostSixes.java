package batsman;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Collections;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Set;
import java.util.TreeMap;

import utility_classes.Pair;
import utility_classes.StoredId;

//Q) find player who hit most sixes in a year by venue;


public class FindPlayerMostSixes {

    private static  Map<String,Integer> getBatsmans(Set<String>ids){

        String deliveryPath = "data/deliveries.csv";
           Map<String,Integer> batsmanRuns = new TreeMap<>();

        try (BufferedReader br = new BufferedReader(new FileReader(deliveryPath))) {   
            br.readLine();

            String line;
            while ((line = br.readLine()) != null) {
                
                String[] columns = line.split(",");
                String matchId = columns[0];
                String batsman = columns[6];
                Integer runs_scored = Integer.parseInt(columns[15]);

                if(runs_scored.equals(6) && ids.contains(matchId)){
                    int prev = batsmanRuns.getOrDefault(batsman,0);
                    batsmanRuns.put(batsman,prev + 1);
                }
            }

            // Print the content of the Map
            // for (Map.Entry<String, Integer> entry : batsmanRuns.entrySet()) {
            //     System.out.println("Team:- " + entry.getKey() + ", Matches Won:- " + entry.getValue_());
            // }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return batsmanRuns;
    }

    
    public static void mostSixBatsman(String year, String venue){
       
        Set<String> ids = StoredId.getIds(year, venue);
        Map<String,Integer> batsmanRuns = getBatsmans(ids);
         
        PriorityQueue <Pair> sortedBatsmans  = new PriorityQueue<>(Collections.reverseOrder());
         for (Map.Entry<String, Integer> entry : batsmanRuns.entrySet()) {
              sortedBatsmans.add(new Pair(entry.getValue(),entry.getKey()));
         }

        System.out.println("Most Sixes by a batsman in year " + year + " at " + venue + ":- " 
        + sortedBatsmans.peek().getValue() + " " + sortedBatsmans.peek().getPriority());
    }


 
}
