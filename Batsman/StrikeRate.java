package Batsman;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

public class StrikeRate {
    //  public static int runsScored(String batsman_name){
    //       int runs = 0;
    //       String filePath = "deliveries.csv";
    //        Map<String,Integer>batsman_runs = new HashMap<>();
    //    try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
    //          br.readLine();
    //         String line;
    //         while ((line = br.readLine()) != null) {
                
    //             String[] columns = line.split(",");
    //            String batsman = columns[6].trim();
    //            int batsman_run = Integer.parseInt(columns[15]);
               
    //             int value = batsman_runs.getOrDefault(batsman, 0);

    //             batsman_runs.put(batsman,value + batsman_run);
    //         }
    //      } catch (Exception e) {
    //           System.out.println(e);
    //    } 
    //       runs = batsman_runs.getOrDefault(batsman_name,0);
    //       return runs;
    //  } 

    //  public static int ballsFaced(String batsman_name){
    //     int balls_faced = 0;
    //      String filePath = "deliveries.csv";
    //        Map<String,Integer>batsman_balls = new HashMap<>();
    //    try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
    //          br.readLine();
    //         String line;
    //         while ((line = br.readLine()) != null) {
                
    //             String[] columns = line.split(",");
    //            String batsman = columns[6].trim();
               
    //             int value = batsman_balls.getOrDefault(batsman, 0);

    //             batsman_balls.put(batsman,value + 1);
    //         }
    //      } catch (Exception e) {
    //           System.out.println(e);
    //    } 
    //   balls_faced = batsman_balls.getOrDefault(batsman_name, 0);
    //     return balls_faced;
    //  }
    

    // public static float getStrikeRate(String batsman_name){
    //         int runs_scored = runsScored(batsman_name);
    //         int balls_faced = ballsFaced(batsman_name);
    //           float strikeRate = ((float)runs_scored/(float)balls_faced) * 100.0f;

  
    //         return strikeRate;
    // }
    
    // public static void allStrikeRates(){
    //     String filePath = "deliveries.csv";
        
    //    Map<String,Float> batsman = new TreeMap<>();

    //    try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
    //          br.readLine();
    //         String line;
    //         while ((line = br.readLine()) != null) {
                
    //             String[] columns = line.split(",");
    //            String batsman_name = columns[6].trim();
               
    //            if(!batsman.containsKey(batsman_name)){
    //                 batsman.put(batsman_name, getStrikeRate(batsman_name));
    //            }
    //         }
    //      } catch (Exception e) {
    //         e.printStackTrace();
    //    } 

    //       for (Map.Entry<String, Float> entry : batsman.entrySet()) {
    //         System.out.println("Player:- " + entry.getKey() + ", Strike Rate:- " + entry.getValue());
    //     }

    // }

    public static void main(String[] args) {
          String csvFilePath = "deliveries.csv";

        try (BufferedReader br = new BufferedReader(new FileReader(csvFilePath))) {
            // Assuming the first line contains headers, if not, skip this line
            br.readLine();

            
            Map<String,Integer> balls_faced = new HashMap<>();
            Map<String,Integer> runs_scored = new HashMap<>();
            Map<String,Float> strike_rate = new TreeMap<>();

            String line;
            while ((line = br.readLine()) != null) {
                
                String[] columns = line.split(",");
                 String batsman = columns[6].trim();
            
               int batsman_run = Integer.parseInt(columns[15]);

               int prevRuns = runs_scored.getOrDefault(batsman,0);
               int prevBalls = balls_faced.getOrDefault(batsman, 0);
               int curr_balls = prevBalls + 1;
               int curr_score = prevRuns + batsman_run;
                
               balls_faced.put(batsman, curr_balls);
               runs_scored.put(batsman,prevRuns + curr_score);

               
               strike_rate.put(batsman, ((float)curr_score/(float)curr_balls)*100.0f);

            }

         

        //   System.out.println("Player:- " + entry.getKey() + ", Strike Rate:- " + entry.getValue());
            // Print the content of the Map
            for (Map.Entry<String, Integer> entry : balls_faced.entrySet()) {
                 String batsman_name = entry.getKey();
                 int balls = entry.getValue();
                 int runs = runs_scored.get(batsman_name);
                 float strike =  ((float)runs/(float)balls) * 100.0f;
                 strike_rate.put(batsman_name,strike);
                   System.out.println("Player:- " + batsman_name + ", Strike Rate:- " + strike);
            }
           


        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
