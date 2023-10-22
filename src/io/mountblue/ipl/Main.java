package io.mountblue.ipl;

import javax.swing.*;
import java.io.*;
import java.util.*;

public class Main {
    public static final int MATCH_ID = 0;
    public static final int MATCH_SEASON = 1;
    public static final int MATCH_WINNER = 10;

    public static final int EXTRA_RUNS = 16;
    public static final int TOTAL_RUNS = 17;
    public static final int BOWLER = 8;

    private static final int OVER = 4;
    public static final int BATSMAN = 7;
    private static final int NON_STRIKER = 8;
    private static final int DISMISSAL_KIND = 19;

    private static final int PLAYER_DISMISSED = 18;


    public static final int BATSMAN_RUNS = 15;

    private static final int BOWLING_TEAM = 3;

    private static final int FIRST_VENUE = 14;
    private static final int SECOND_VENUE = 15;

    public static void main(String[] args) {
        List<Match> matches = getMatchesData();
        List<Delivery> deliveries = getDeliveriesData();
        int choice = -1;
        int stay = 1;
        String year = "2016";

        while(stay == 1) {
            System.out.println("Welcome To IPL Database Management");
            Scanner sc = new Scanner(System.in);
            System.out.println("Press 0 to exit");
            System.out.println("Press 1 for NO OF MATCHES TEAMS PLAYED PER YEAR");
            System.out.println("Press 2 for TOTAL NO OF MATCHES WON BY TEAMS");
            System.out.println("Press 3 for EXTRA RUNS CONCEDED PER TEAM IN A YEAR");
            System.out.println("Press 4 for MOST ECONOMICAL BOWLER IN A YEAR");
            System.out.println("Press 5 for MOST SIX BY A BATSMAN IN A YEAR AND IN A VENUE");
            System.out.println("Press 6  for MOST MAIDEN OVERS BY BOWLERS IN A YEAR");
            choice = sc.nextInt();
            if(choice == 0)
                break;
            switch (choice){
                case 1:
                    noOfMatchesTeamsPlayedPerYear(matches);
                    break;
                case 2:
                    totalMatchesWonByTeams(matches);
                    break;
                case 3:
                    System.out.println("Enter year");
                    year = sc.next();

                   if(!year.isEmpty()){
                       extraRunsConcededPerTeam(matches,deliveries,year);
                   }
                   else{
                       System.out.println("Enter valid input");
                   }
                    break;
                case 4:
                    System.out.println("Enter year");
                    year = sc.next();

                    if(!year.isEmpty()){
                       mostEconomicalBowlerInAYear(year,matches,deliveries);
                    }
                    else{
                        System.out.println("Enter valid input");
                    }

                    break;
                case 5:
                    String venue="";
                    System.out.println("Enter year");

                    year = sc.next();
                    sc.nextLine();
                    System.out.println("Enter venue:- ");
                    venue = sc.nextLine();

                    if(!year.isEmpty() && !venue.isEmpty()) {
                        mostSixBatsmanByYearAndVenue(year, venue, matches, deliveries);
                    }
                    else {
                        System.out.println("Enter valid input");
                    }

                    break;
                case 6:
                    System.out.println("Enter year");
                    year = sc.next();

                    if(!year.isEmpty()){
                        mostMaidenOverByBowlerInAYear(year,matches,deliveries);
                    }
                    else {
                        System.out.println("Enter valid input");
                    }

                    break;
                default:
                    System.out.println("Wrong choice.");
            }
            System.out.println("Press 0 for exit or 1 to start again");

            stay = sc.nextInt();
        }
//     noOfMatchesTeamsPlayedPerYear(matches);
//     totalMatchesWonByTeams(matches);
//     extraRunsConcededPerTeam(matches,deliveries,"2016");
//        mostEconomicalBowlerInAYear("2015",matches,deliveries);
//        mostSixBatsmanByYearAndVenue("2016","Eden Gardens",matches,deliveries);
//        mostMaidenOverByBowlerInAYear("2016",matches,deliveries);


    }

    private static List<Match> getMatchesData() {
        List<Match> matches = new ArrayList<>();


        try (BufferedReader reader = new BufferedReader(new FileReader("data/matches.csv"))) {
            reader.readLine();

            while (reader.ready()) {
                String line = reader.readLine();
                String[] data = line.split(",");

                Match match = new Match();
                match.setId(data[MATCH_ID]);
                match.setSeason(data[MATCH_SEASON]);
                match.setWinner(data[MATCH_WINNER]);
                if (data.length > 17) {
                    match.setVenue(data[FIRST_VENUE].trim().replace("\"", "") +
                            "," + data[SECOND_VENUE].trim().replace("\"", ""));
                } else {
                    match.setVenue(data[FIRST_VENUE].trim().replace("\"", ""));
                }

                matches.add(match);
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return matches;
    }

    private static List<Delivery> getDeliveriesData() {
        List<Delivery> deliveries = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader("data/deliveries.csv"))) {
            reader.readLine();
            while (reader.ready()) {
                String line = reader.readLine();
                String[] data = line.split(",");

                Delivery delivery = new Delivery();
                delivery.setId(data[MATCH_ID]);
                delivery.setBowlingTeam(data[BOWLING_TEAM]);
                delivery.setExtraRuns(data[EXTRA_RUNS]);
                delivery.setBowler(data[BOWLER]);
                delivery.setBatsman(data[BATSMAN]);
                delivery.setNonStriker(data[NON_STRIKER]);
                delivery.setTotalRuns(data[TOTAL_RUNS]);
                delivery.setBatsmanRuns(data[BATSMAN_RUNS]);
                delivery.setOver(data[OVER]);
                if (data.length > 19) {
                    delivery.setPlayerDismissed(data[PLAYER_DISMISSED]);
                    delivery.setDismissalKind(data[DISMISSAL_KIND]);
                }


                deliveries.add(delivery);
            }
        } catch (IOException e) {
            System.out.println(e);
        }
        return deliveries;
    }

    private static void noOfMatchesTeamsPlayedPerYear(List<Match> matches) {
        Map<String, Integer> noOfMatches = new TreeMap<>();

        for (Match match : matches) {
            String matchSeason = match.getSeason();
            int prevMatches = noOfMatches.getOrDefault(matchSeason, 0);
            noOfMatches.put(matchSeason, prevMatches + 1);
        }

        System.out.println("No of matches played in a year:- ");

        for (Map.Entry<String, Integer> elem : noOfMatches.entrySet()) {
            System.out.println("Season:- " + elem.getKey() + ", No of matches played:- " + elem.getValue());
        }
    }

    private static void totalMatchesWonByTeams(List<Match> matches) {
        Map<String, Integer> totalNoOfMatchesWonByTeams = new TreeMap<>();

        for (Match match : matches) {
            String teamName = match.getWinner();
            int prevWin = totalNoOfMatchesWonByTeams.getOrDefault(teamName, 0);
            totalNoOfMatchesWonByTeams.put(teamName, prevWin + 1);
        }

        System.out.println("Total no of matches won by teams in a year:- ");

        for (Map.Entry<String, Integer> elem : totalNoOfMatchesWonByTeams.entrySet()) {
            System.out.println("Team:- " + elem.getKey() + ", No of matches won:- " + elem.getValue());
        }

    }

    private static Set<String> getMatchYearId(List<Match> matches, String year) {
        Set<String> matchYearId = new HashSet<>();

        for (Match match : matches) {
            if (match.getSeason().equals(year)) {
                matchYearId.add(match.getId());
            }
        }

        return matchYearId;
    }

    private static Set<String> getMatchYearId(List<Match> matches, String year, String venue) {
        Set<String> matchYearId = new HashSet<>();

        for (Match match : matches) {
            if (match.getSeason().equals(year) && match.getVenue().equals(venue)) {
                matchYearId.add(match.getId());
            }
        }

        return matchYearId;
    }

    private static void extraRunsConcededPerTeam(List<Match> matches, List<Delivery> deliveries, String year) {
        Set<String> matchYearId = getMatchYearId(matches, year);
        Map<String, Integer> teamsConcededExtraRuns = new TreeMap<>();

        for (Delivery delivery : deliveries) {
            String matchId = delivery.getId();

            if (matchYearId.contains(matchId)) {
                int extraRuns = Integer.parseInt(delivery.getExtraRuns());
                String team = delivery.getBowlingTeam();
                int prevRuns = teamsConcededExtraRuns.getOrDefault(team, 0);
                teamsConcededExtraRuns.put(team, extraRuns + prevRuns);
            }
        }

        for (Map.Entry<String, Integer> team : teamsConcededExtraRuns.entrySet()) {
            System.out.println("Team:- " + team.getKey() + ", Value:-  " + team.getValue());
        }
    }

    private static void mostEconomicalBowlerInAYear(String year, List<Match> matches, List<Delivery> deliveries) {
        Set<String> matchYearId = getMatchYearId(matches, year);
        Map<String, Integer> bowlerRuns = new HashMap<>();
        Map<String, Integer> bowlerBalls = new HashMap<>();
        Map<String, Float> bowlersEconomy = new HashMap<>();
        Map<String, Float> sortedBowlersEconomy = new LinkedHashMap<>();
        float lowestEconomy = Float.MAX_VALUE;

        for (Delivery delivery : deliveries) {
            String id = delivery.getId();

            if (matchYearId.contains(id)) {
                String bowler = delivery.getBowler();

                int currRuns = Integer.parseInt(delivery.getTotalRuns());

                int prevRuns = bowlerRuns.getOrDefault(bowler, 0);
                int prevBalls = bowlerBalls.getOrDefault(bowler, 0);

                bowlerRuns.put(bowler, prevRuns + currRuns);
                bowlerBalls.put(bowler, prevBalls + 1);
            }
        }

        bowlerRuns.forEach((bowler, runs) -> {
            int balls = bowlerBalls.get(bowler);
            int overs = balls / 6;
            float economy = ((float) runs / (float) overs);

            bowlersEconomy.put(bowler, economy);
        });

        List<Map.Entry<String, Float>> sortEconomyList = new ArrayList<>(bowlersEconomy.entrySet());
        sortEconomyList.sort(Map.Entry.comparingByValue());


        for (Map.Entry<String, Float> bowlerEconomy : sortEconomyList) {
            sortedBowlersEconomy.put(bowlerEconomy.getKey(), bowlerEconomy.getValue());
        }

        System.out.println("Top economical Bowler:-");


       for(Map.Entry<String,Float> bowlerEconomy: sortedBowlersEconomy.entrySet()){
            if(lowestEconomy == Float.MAX_VALUE || lowestEconomy == bowlerEconomy.getValue()) {
                System.out.println("Bowler:- " + bowlerEconomy.getKey() + ", Economy:- " + bowlerEconomy.getValue());

                lowestEconomy = bowlerEconomy.getValue();
            }
            else{
                break;
            }
        };
    }

    private static Map<String, Integer> getBatsmansScoredSix(Set<String> matchYearId, List<Delivery> deliveries) {
        Map<String, Integer> batsmanScoredSix = new HashMap<>();

        for (Delivery delivery : deliveries) {
            String id = delivery.getId();
            String batsmanRuns = delivery.getBatsmanRuns();
            if (batsmanRuns.equals("6") && matchYearId.contains(id)) {
                String batsman = delivery.getBatsman();
                int prevSixCount = batsmanScoredSix.getOrDefault(batsman, 0);
                batsmanScoredSix.put(batsman, prevSixCount + 1);
            }
        }

        return batsmanScoredSix;
    }

    private static void mostSixBatsmanByYearAndVenue(String year, String venue, List<Match> matches,
                                                     List<Delivery> deliveries) {
        Set<String> matchYearId = getMatchYearId(matches, year, venue);
        Map<String, Integer> batsmanScoredSix = getBatsmansScoredSix(matchYearId, deliveries);
        Map<Integer, String> mostSixBatsman = new TreeMap<>(Collections.reverseOrder());

        for (Map.Entry<String, Integer> batsman : batsmanScoredSix.entrySet()) {
            mostSixBatsman.put(batsman.getValue(), batsman.getKey());
        }

        System.out.println("Most sixes by a batsman in year " + year + " at " + venue + " :- ");
        for (Map.Entry<Integer, String> batsman : mostSixBatsman.entrySet()) {
            System.out.println("Batsman:- " + batsman.getValue() + ", Sixes:- " + batsman.getKey());
        }
    }

    private static void mostMaidenOverByBowlerInAYear(String year, List<Match> matches, List<Delivery> deliveries) {
        Set<String> matchYearId = getMatchYearId(matches, year);
        String over = "";
        int runs = 0;
        String bowler = "";
        Map<String, Integer> maidenOverByBowler = new HashMap<>();
 

        for (Delivery delivery : deliveries) {
            String id = delivery.getId();
            String currOver = delivery.getOver();
            String currBowler = delivery.getBowler();
            String currRun = delivery.getBatsmanRuns();


            if (matchYearId.contains(id)) {
                if (!over.equals(currOver) && !over.isEmpty()) {
                    if (runs == 0 && !bowler.isEmpty()) {
                        int prevOvers = maidenOverByBowler.getOrDefault(bowler, 0);

                        maidenOverByBowler.put(bowler, prevOvers + 1);

                    }
                    runs = Integer.parseInt(currRun);
                } else {
                    runs += Integer.parseInt(currRun);

                }
                bowler = currBowler;
                over = currOver;
            }
        }

        List<Map.Entry<String, Integer>> sortMaidenBowlerList = new ArrayList<>(maidenOverByBowler.entrySet());
        sortMaidenBowlerList.sort(Map.Entry.comparingByValue(Collections.reverseOrder()));
        Map<String, Integer> sortedMaidenBowler = new LinkedHashMap<>();

        for (Map.Entry<String, Integer> bowlerEconomy : sortMaidenBowlerList) {
            sortedMaidenBowler.put(bowlerEconomy.getKey(), bowlerEconomy.getValue());
        }

        System.out.println("Most Maiden Overs Bowled in " + year);

        for (Map.Entry<String, Integer> sortedBowler : sortedMaidenBowler.entrySet()) {
            System.out.println("Bowler:- " + sortedBowler.getKey() + ", Maiden Overs:- " +
                    sortedBowler.getValue());
        }
    }



}