package Batsman;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Collections;
import java.util.HashSet;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Set;
import java.util.TreeMap;

//Q) find player who hit most sixes in a year by venue;
class Pair implements Comparable<Pair> {
    int priority;
    String value;

    Pair(int priority, String value) {
        this.priority = priority;
        this.value = value;
    }

    @Override
    public int compareTo(Pair other) {
        // Compare Pairs based on their priority
        return Integer.compare(this.priority, other.priority);
    }
}

public class FindPlayerMostSixes {

    public static Set<String> getIds(String year,String venue){
        Set<String> ids = new HashSet<String>();
         String matchPath = "matches.csv";

        try (BufferedReader br = new BufferedReader(new FileReader(matchPath))) {
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

    public static  Map<String,Integer> getBatsman (Set<String>ids){

        String deliveryPath = "deliveries.csv";
           Map<String,Integer> batsman_run = new TreeMap<>();

        try (BufferedReader br = new BufferedReader(new FileReader(deliveryPath))) {   
            br.readLine();

            String line;
            while ((line = br.readLine()) != null) {
                
                String[] columns = line.split(",");
                String matchId = columns[0];
                String batsman = columns[6];
                Integer runs_scored = Integer.parseInt(columns[15]);

                if(runs_scored.equals(6) && ids.contains(matchId)){
                    int prev = batsman_run.getOrDefault(batsman,0);
                    batsman_run.put(batsman,prev + 1);
                }
            }

            // Print the content of the Map
            // for (Map.Entry<String, Integer> entry : batsman_run.entrySet()) {
            //     System.out.println("Team:- " + entry.getKey() + ", Matches Won:- " + entry.getValue());
            // }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return batsman_run;
    }

    public static void mostSixBatsman(String year, String venue){
        Set<String> ids = getIds(year, venue);
        Map<String,Integer> batsman_runs = getBatsman(ids);
         
        PriorityQueue <Pair> sortedBatsman  = new PriorityQueue<>(Collections.reverseOrder());
         for (Map.Entry<String, Integer> entry : batsman_runs.entrySet()) {
              sortedBatsman.add(new Pair(entry.getValue(),entry.getKey()));
         }

        System.out.println("Most Sixes by a batsman in year " + year + " at " + venue + ":- " + sortedBatsman.peek().value + " " + sortedBatsman.peek().priority);
    }


    public static void main(String[] args) {
        mostSixBatsman("2015", "Bangalore");
    }
}
