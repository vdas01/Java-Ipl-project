package Team;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Collection;
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
      private String team1, team2, tossWinner,winner;
      Map<String,Integer> toss_win = new HashMap<>();
      Map<String,Integer> matches_played = new HashMap<>();
      PriorityQueue<Pair> win_perct = new PriorityQueue<>(Collections.reverseOrder());
      String matchPath = "matches.csv";

    public void getWinPercentage(){
         for (Map.Entry<String, Integer> entry : toss_win.entrySet()) {
                // System.out.println("Team:- " + entry.getKey() + ", Matches Won:- " + entry.getValue());
                //no of toss/ no of matches * 100;
                int noOfToss = entry.getValue();
                int noOfMatchesPlayed = matches_played.get(entry.getKey());
                float winPerc = ((float)noOfToss/(float)noOfMatchesPlayed) * 100.0f;
                win_perct.add(new Pair(winPerc,entry.getKey()));
            }
        System.out.println(win_perct.peek().value + " " + win_perct.peek().priority);
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
                winner = columns[10];
                 
                if(tossWinner.equals(team1)){
                    int prevToss = toss_win.getOrDefault(team1,0);
                    toss_win.put(team1, prevToss + 1);
                }
                else{
                    int prevToss = toss_win.getOrDefault(team2, 0);
                    toss_win.put(team2, prevToss + 1);
                }

                int prevMatch1 = matches_played.getOrDefault(team1, 0);
                int prevMatch2 = matches_played.getOrDefault(team2, 0);

                matches_played.put(team1, prevMatch1+1);
                matches_played.put(team2, prevMatch2 + 1);
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
