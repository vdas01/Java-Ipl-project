import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

public class NoOfMatchesWon {
    public static void main(String[] args) {
         String csvFilePath = "matches.csv";

        try (BufferedReader br = new BufferedReader(new FileReader(csvFilePath))) {
            // Assuming the first line contains headers, if not, skip this line
            br.readLine();

            
            Map<String,Integer> dataMap = new TreeMap<>();

            String line;
            while ((line = br.readLine()) != null) {
                
                String[] columns = line.split(",");
                String key = columns[10].trim();
                int value = dataMap.getOrDefault(key, 0);

                dataMap.put(key,value + 1);
            }

            // Print the content of the Map
            for (Map.Entry<String, Integer> entry : dataMap.entrySet()) {
                System.out.println("Team:- " + entry.getKey() + ", Matches Won:- " + entry.getValue());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
