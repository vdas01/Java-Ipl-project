import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;



public class ExtraRunConcededPerTeam {
    //matchid, year
    public static Set<String> matchYear(){
          String matchPath = "matches.csv";
          Set<String> s1 = new TreeSet<String>();

             try (BufferedReader br = new BufferedReader(new FileReader(matchPath))) {
            
            br.readLine();
            String line;
            while ((line = br.readLine()) != null) {
                String[] columns = line.split(",");
                // System.out.print(columns[1] + " ");
               if(columns[1].equals("2016")){
                   s1.add(columns[0]);
               }
            }
           
            for(String id: s1){
                System.out.println(id + " ");
            }
           
        } catch (IOException e) {
            e.printStackTrace();
        } 
        return s1;
    }

    public static void main(String[] args) {
        

        //  String matchPath = "matches.csv";
         String deliveriesPath = "deliveries.csv";

         Set<String> s2 = matchYear();

        try (BufferedReader br = new BufferedReader(new FileReader(deliveriesPath))) {
            // Assuming the first line contains headers, if not, skip this line
            br.readLine();

            
            Map<String,Integer> dataMap = new HashMap<>();

            String line;
            while ((line = br.readLine()) != null) {
                
                String[] columns = line.split(",");
                String id = columns[0];
                String team = columns[3];
                int extra_runs = Integer.parseInt(columns[16]);

                if(extra_runs == 1 && s2.contains(id)){
                    int prev = dataMap.get(team);
                    dataMap.put(team,prev+1);
                }
                else if(!dataMap.containsKey(team) && s2.contains(id)){
                    dataMap.put(team,0);
                }

            }
  

            //print:-
            for(Map.Entry<String,Integer> temp: dataMap.entrySet()){
                System.out.println(temp.getKey() + " " + temp.getValue());
            }
           
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
