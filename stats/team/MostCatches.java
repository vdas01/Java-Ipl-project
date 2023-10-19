package team;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Set;
import utility_classes.StoredId;
import utility_classes.Pair;

//Q) Most catches by a player in a year

public class MostCatches {
   static Map<String,Integer> mostCatchPlayers = new HashMap<>();

    private static void getMostCatchesPlayers(String year){
        String deliveryPath = "data/deliveries.csv";
          Set<String> ids = StoredId.getIds(year);
           
           try (BufferedReader br = new BufferedReader(new FileReader(deliveryPath))) {   
            br.readLine();

            String line;
            while ((line = br.readLine()) != null) {
                
                String[] columns = line.split(",");
                String matchId = columns[0];

                if(columns.length > 20 && ids.contains(matchId)){
                    String fielder = columns[20];
                    int prevCatches = mostCatchPlayers.getOrDefault(fielder,0);
                    mostCatchPlayers.put(fielder, prevCatches + 1);
                }

               
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        // for (Map.Entry<String, Integer> entry : mostCatchPlayers.entrySet()){
        //     System.out.println(entry.getKey() + " " + entry.getValue());
        // }
        
    }

    public static void getMostCatchesPlayer(String year){
        getMostCatchesPlayers(year);
        PriorityQueue<Pair> mostCatchPlayer = new PriorityQueue<>(Collections.reverseOrder());
         
        for (Map.Entry<String, Integer> entry : mostCatchPlayers.entrySet()){
             mostCatchPlayer.add(new Pair(entry.getValue(),entry.getKey()));
        }

        System.out.println("Most catches in year " + year + " is:- ");
        System.out.println(mostCatchPlayer.peek().getValue() + ", Catches:- " + mostCatchPlayer.peek().getPriority());

    }
 
}
