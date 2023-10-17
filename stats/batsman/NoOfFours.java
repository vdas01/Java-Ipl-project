package batsman;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

public class NoOfFours {

    public static void getMostFours(){
          String csvFilePath = "data/deliveries.csv";

        try (BufferedReader br = new BufferedReader(new FileReader(csvFilePath))) {
           
            br.readLine();

            Map<String,Integer> fourCount = new HashMap<>();
            Map<String,Integer> sixCount = new TreeMap<>();

            String line;
            while ((line = br.readLine()) != null) {
                
                String[] columns = line.split(",");
           
               
                String batsman = columns[6].trim();
                int runs = Integer.parseInt(columns[15]);
                if(runs == 4){
                   int prev = fourCount.getOrDefault(batsman, 0);
                   fourCount.put(batsman, prev + 1);
                }
                else if(runs == 6){
                    int prev = sixCount.getOrDefault(batsman, 0);
                   sixCount.put(batsman, prev + 1);
                }

            }

            // Print the content of the Map
            for (Map.Entry<String, Integer> entry : fourCount.entrySet()) {
                System.out.println("Player:- " + entry.getKey() + ", Fours:- " + 
                entry.getValue() + ", Six:- " + sixCount.getOrDefault(entry.getKey(), 0));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
   
}
