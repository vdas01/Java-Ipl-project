package bowling;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

public class TopEconomicalBowlers {

     private static Set<String> matchYear() {
          String matchPath = "data/matches.csv";
          Set<String> s1 = new TreeSet<String>();

             try (BufferedReader br = new BufferedReader(new FileReader(matchPath))) {
            
            br.readLine();
            String line;
            while ((line = br.readLine()) != null){
                String[] columns = line.split(",");
                // System.out.print(columns[1] + " ");
               if(columns[1].equals("2015")){
                   s1.add(columns[0]);
               }
            }
         
           
        } catch (IOException e) {
            e.printStackTrace();
        } 
        return s1;
    }

    public static void getTopEconomicalBowler(){
           String deliveriesPath = "data/deliveries.csv";

         Set<String> s2 = matchYear();

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
                int curr_run = Integer.parseInt(columns[17]);
                String id = columns[0];

                if(s2.contains(id)){
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


            //Sort accoridng to values or economy:-
            List<Map.Entry<String, Float>> entryList = new ArrayList<>(economical.entrySet());

        // Sort the list based on values (in ascending order)
        entryList.sort(Comparator.comparing(Map.Entry::getValue));

        for (Map.Entry<String, Float> entry : entryList) {
           System.out.println(entry.getKey() + " " + entry.getValue());
        }
            
           
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
   
}
