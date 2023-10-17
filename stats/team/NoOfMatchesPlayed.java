package team;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class NoOfMatchesPlayed {

    public static void getMatchesPlayed(){
         String csvFilePath = "data/matches.csv";

        try (BufferedReader br = new BufferedReader(new FileReader(csvFilePath))) {
        
            br.readLine();

            
            Map<String,Integer> dataMap = new HashMap<>();

            String line;
            while ((line = br.readLine()) != null) {
                
                String[] columns = line.split(","); 
                String key = columns[1].trim();
                int value = dataMap.getOrDefault(key, 0);

                dataMap.put(key,value + 1);
            }

            
            for (Map.Entry<String, Integer> entry : dataMap.entrySet()) {
                System.out.println("Year:- " + entry.getKey() + ", Matches Played:- " + entry.getValue());
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
  
}
