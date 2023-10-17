package utility_classes;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

public class StoredId {
      private static String matchPath_ = "data/matches.csv";

     public static  Set<String> getIds(String year,String venue){
         Set<String> ids = new HashSet<String>();
        

        try (BufferedReader br = new BufferedReader(new FileReader(matchPath_))) {
            br.readLine();

            String line;
            while ((line = br.readLine()) != null) {
                
                String[] columns = line.split(",");
                String match_id = columns[0];
                String curr_year = columns[1];
                String curr_venue = columns[2];

                if(curr_venue.equals(venue) && curr_year.equals(year)){
                    ids.add(match_id);
                }               
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ids;
    }

    public static  Set<String> getIds(String year){
         Set<String> ids = new HashSet<String>();
        

        try (BufferedReader br = new BufferedReader(new FileReader(matchPath_))) {
            br.readLine();

            String line;
            while ((line = br.readLine()) != null) {
                
                String[] columns = line.split(",");
                String match_id = columns[0];
                String curr_year = columns[1];
                

                if(curr_year.equals(year)){
                    ids.add(match_id);
                }               
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ids;
    }

     public static  Set<String> getIds2(String venue){
         Set<String> ids = new HashSet<String>();
        

        try (BufferedReader br = new BufferedReader(new FileReader(matchPath_))) {
            br.readLine();

            String line;
            while ((line = br.readLine()) != null) {
                
                String[] columns = line.split(",");
                String match_id = columns[0];
                String curr_venue = columns[2];

                if(curr_venue.equals(venue)){
                    ids.add(match_id);
                }               
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ids;
    }
}
