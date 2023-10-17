package team;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;

import utility_classes.Pair2;

//Q) Highest % of toss win and match win by any team in a year;


public class TossWon{

      private static String team1_, team2_, tossWinner_;
     private static Map<String,Integer> tossWin_ = new HashMap<>();
     private static Map<String,Integer> matchesPlayed_ = new HashMap<>();
     private static PriorityQueue<Pair2> winPerctange_ = new PriorityQueue<>(Collections.reverseOrder());
      private static String matchPath_ = "data/matches.csv";

    public  static void  getWinPercentage(){
         for (Map.Entry<String, Integer> entry : tossWin_.entrySet()) {
                //no of toss/ no of matches * 100;
                int noOfToss = entry.getValue();
                int noOfMatchesPlayed = matchesPlayed_.get(entry.getKey());
                float winPerc = ((float)noOfToss/(float)noOfMatchesPlayed) * 100.0f;
                winPerctange_.add(new Pair2(winPerc,entry.getKey()));
            }
        System.out.println(winPerctange_.peek().getValue() + " " + winPerctange_.peek().getPriority());
    }

    public static void getTossWin(String year){

           try (BufferedReader br = new BufferedReader(new FileReader(matchPath_))) {   
            br.readLine();

            String line;
            while ((line = br.readLine()) != null) {
                
                String[] columns = line.split(",");
                team1_ = columns[4];
                team2_ = columns[5];
                tossWinner_ = columns[6];
                 
                if(tossWinner_.equals(team1_)){
                    int prevToss = tossWin_.getOrDefault(team1_,0);
                    tossWin_.put(team1_, prevToss + 1);
                }
                else{
                    int prevToss = tossWin_.getOrDefault(team2_, 0);
                    tossWin_.put(team2_, prevToss + 1);
                }

                int prevMatch1 = matchesPlayed_.getOrDefault(team1_, 0);
                int prevMatch2 = matchesPlayed_.getOrDefault(team2_, 0);

                matchesPlayed_.put(team1_, prevMatch1 + 1);
                matchesPlayed_.put(team2_, prevMatch2 + 1);
            }

            getWinPercentage();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

public static void main(String[] args) {
    getTossWin("2016");
}
    
}

