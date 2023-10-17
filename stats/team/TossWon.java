package team;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;

//Q) Highest % of toss win and match win by any team in a year;
class Pair implements Comparable<Pair> {
    float priority;
    String value;

    Pair(float priority, String value) {
        this.priority = priority;
        this.value = value;
    }

    @Override
    public int compareTo(Pair other) {
        // Compare Pairs based on their priority
        return Float.compare(this.priority, other.priority);
    }
}

class Toss{
      private String team1, team2, tossWinner;
      Map<String,Integer> tossWin = new HashMap<>();
      Map<String,Integer> matchesPlayed = new HashMap<>();
      PriorityQueue<Pair> winPerctange = new PriorityQueue<>(Collections.reverseOrder());
      String matchPath = "data/matches.csv";

    public void getWinPercentage(){
         for (Map.Entry<String, Integer> entry : tossWin.entrySet()) {
                // System.out.println("Team:- " + entry.getKey() + ", Matches Won:- " + entry.getValue());
                //no of toss/ no of matches * 100;
                int noOfToss = entry.getValue();
                int noOfMatchesPlayed = matchesPlayed.get(entry.getKey());
                float winPerc = ((float)noOfToss/(float)noOfMatchesPlayed) * 100.0f;
                winPerctange.add(new Pair(winPerc,entry.getKey()));
            }
        System.out.println(winPerctange.peek().value + " " + winPerctange.peek().priority);
    }

    public void getTossWin(String year){

           try (BufferedReader br = new BufferedReader(new FileReader(matchPath))) {   
            br.readLine();

            String line;
            while ((line = br.readLine()) != null) {
                
                String[] columns = line.split(",");
                team1 = columns[4];
                team2 = columns[5];
                tossWinner = columns[6];
                 
                if(tossWinner.equals(team1)){
                    int prevToss = tossWin.getOrDefault(team1,0);
                    tossWin.put(team1, prevToss + 1);
                }
                else{
                    int prevToss = tossWin.getOrDefault(team2, 0);
                    tossWin.put(team2, prevToss + 1);
                }

                int prevMatch1 = matchesPlayed.getOrDefault(team1, 0);
                int prevMatch2 = matchesPlayed.getOrDefault(team2, 0);

                matchesPlayed.put(team1, prevMatch1 + 1);
                matchesPlayed.put(team2, prevMatch2 + 1);
            }

            getWinPercentage();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    
}


public class TossWon {
    
    public static void main(String[] args) {
        Toss t = new Toss();
        t.getTossWin("2017");
    }
}
