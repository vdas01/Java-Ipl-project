package Batsman;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

public class MostDucks {
    public static void main(String[] args) {
         String csvFilePath = "deliveries.csv";

        try (BufferedReader br = new BufferedReader(new FileReader(csvFilePath))) {
           
            br.readLine();

            Map<String,Integer> runs_score = new HashMap<>();
            Map<String,Integer> dataDucks = new TreeMap<>();

            String line;
            while ((line = br.readLine()) != null) {
                
                String[] columns = line.split(",");
           
               
                String batsman = columns[6].trim();
                int runs = Integer.parseInt(columns[15]);
                 

               int prev_runs = runs_score.getOrDefault(batsman, 0);

               if(columns.length <= 18){
                     runs_score.put(batsman,prev_runs + runs);
               }
               else if(prev_runs == 0){
                  int prevDucks = dataDucks.getOrDefault(batsman,0);
                  dataDucks.put(batsman, prevDucks + 1);
               }

            }

            // Print the content of the Map
            for (Map.Entry<String, Integer> entry : dataDucks.entrySet()) {
                System.out.println("Player:- " + entry.getKey() + ", Ducks:- " + entry.getValue());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
