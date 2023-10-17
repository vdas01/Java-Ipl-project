import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class NoOfMatchesPlayed {
    public static void main(String[] args) {
        // Replace "your_csv_file.csv" with the actual path to your CSV file
        String csvFilePath = "matches.csv";

        try (BufferedReader br = new BufferedReader(new FileReader(csvFilePath))) {
            // Assuming the first line contains headers, if not, skip this line
            br.readLine();

            
            Map<String,Integer> dataMap = new HashMap<>();

            String line;
            while ((line = br.readLine()) != null) {
                // Split the line into columns
                String[] columns = line.split(",");
                for(int i=0;i<columns.length;i++){
                    System.out.print(columns[i] + " ");
                }
              System.out.println();
                // Assuming the first column is the key and the second column is the value
                String key = columns[1].trim();
                int value = dataMap.getOrDefault(key, 0);

                dataMap.put(key,value + 1);
            }

            // Print the content of the Map
            for (Map.Entry<String, Integer> entry : dataMap.entrySet()) {
                System.out.println("Year:- " + entry.getKey() + ", Matches Played:- " + entry.getValue());
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
