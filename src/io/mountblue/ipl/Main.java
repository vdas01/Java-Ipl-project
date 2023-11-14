package io.mountblue.ipl;

import javax.swing.*;
import java.io.*;
import java.util.*;

public class Main {
    private static final String MATCHES_FILE_PATH = "data/matches.csv";
    private static final String DELIVERIES_FILE_PATH = "data/deliveries.csv";

    private static final int MATCH_ID = 0;
    private static final int MATCH_SEASON = 1;
    private static final int MATCH_WINNER = 10;
    private static final int MATCH_FIRST_VENUE = 14;
    private static final int MATCH_SECOND_VENUE = 15;

    private static final int DELIVERY_MATCH_ID = 0;
    private static  final int DELIVERY_BATTING_TEAM = 2;
    private static final int DELIVERY_BOWLING_TEAM = 3;
    private static final int DELIVERY_OVER = 4;
    private static final int DELIVERY_BATSMAN = 6;
    private static final int DELIVERY_NON_STRIKER = 7;
    private static final int DELIVERY_BOWLER = 8;
    public static final int DELIVERY_BATSMAN_RUNS = 15;
    private static final int DELIVERY_EXTRA_RUNS = 16;
    private static final int DELIVERY_TOTAL_RUNS = 17;
    private static final int DELIVERY_PLAYER_DISMISSED = 18;
    private static final int DELIVERY_DISMISSAL_KIND = 19;

    public static void main(String[] args) {
        List<Match> matches = getMatchesData();
        List<Delivery> deliveries = getDeliveriesData();

        findMatchesTeamsPlayedPerYear(matches);
        findMatchesWonByTeams(matches);
        findExtraRunsConcededPerTeamIn2016(matches,deliveries,"2016");
        findTopEconomicalBowlerIn2015("2015",matches,deliveries);
        findMostSixBatsmanByYearAndVenue("2016","Eden Gardens",matches,deliveries);
        findMostMaidenOverByBowlerInAYear("2016",matches,deliveries);
        findMostRunsByTeamInAMatch(deliveries);
        findMostMatchesInAVenue(matches);
        findHighestWicketTaker(matches,deliveries);
        findNoOfOutsInASeason("2017",deliveries,matches);
    }

    private static List<Match> getMatchesData() {
        List<Match> matches = new ArrayList<>();


        try (BufferedReader reader = new BufferedReader(new FileReader(MATCHES_FILE_PATH))) {
            reader.readLine();

            while (reader.ready()) {
                String line = reader.readLine();
                String[] data = line.split(",");

                Match match = new Match();
                match.setId(data[MATCH_ID]);
                match.setSeason(data[MATCH_SEASON]);
                match.setWinner(data[MATCH_WINNER]);
                if (data.length > 17) {
                    match.setVenue(data[MATCH_FIRST_VENUE ].trim().replace("\"", "") +
                            "," + data[MATCH_SECOND_VENUE ].trim().replace("\"", ""));
                } else {
                    match.setVenue(data[MATCH_SECOND_VENUE ].trim().replace("\"", ""));
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

        try (BufferedReader reader = new BufferedReader(new FileReader(DELIVERIES_FILE_PATH))) {
            reader.readLine();
            while (reader.ready()) {
                String line = reader.readLine();
                String[] data = line.split(",");

                Delivery delivery = new Delivery();
                delivery.setId(data[DELIVERY_MATCH_ID]);
                delivery.setBowlingTeam(data[DELIVERY_BOWLING_TEAM]);
                delivery.setExtraRuns(data[DELIVERY_EXTRA_RUNS]);
                delivery.setBowler(data[DELIVERY_BOWLER]);
                delivery.setBatsman(data[DELIVERY_BATSMAN]);
                delivery.setNonStriker(data[DELIVERY_NON_STRIKER]);
                delivery.setTotalRuns(data[DELIVERY_TOTAL_RUNS]);
                delivery.setBatsmanRuns(data[DELIVERY_BATSMAN_RUNS]);
                delivery.setOver(data[DELIVERY_OVER]);
                delivery.setBattingTeam(data[DELIVERY_BATTING_TEAM]);
                if (data.length > 19) {
                    delivery.setPlayerDismissed(data[DELIVERY_PLAYER_DISMISSED]);
                    delivery.setDismissalKind(data[DELIVERY_DISMISSAL_KIND]);
                }

                deliveries.add(delivery);
            }
        } catch (IOException e) {
            System.out.println(e);
        }
        return deliveries;
    }

    private static void findMatchesTeamsPlayedPerYear(List<Match> matches) {
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

    private static void findMatchesWonByTeams(List<Match> matches) {
        Map<String, Integer> totalNoOfMatchesWonByTeams = new TreeMap<>();

        for (Match match : matches) {
            String teamName = match.getWinner();
            int prevWin = totalNoOfMatchesWonByTeams.getOrDefault(teamName, 0);
            totalNoOfMatchesWonByTeams.put(teamName, prevWin + 1);
        }

        System.out.println("Total no of matches won by teams in a year:- ");
        for (Map.Entry<String, Integer> elem : totalNoOfMatchesWonByTeams.entrySet()) {
            if(elem.getKey().isEmpty())
                continue;
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

    private static void findExtraRunsConcededPerTeamIn2016(List<Match> matches, List<Delivery> deliveries, String year) {
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

    private static void findTopEconomicalBowlerIn2015(String year, List<Match> matches, List<Delivery> deliveries) {
        Set<String> matchYearId = getMatchYearId(matches, year);
        Map<String, Integer> bowlerRuns = new HashMap<>();
        Map<String, Integer> bowlerBalls = new HashMap<>();
        Map<String, Float> bowlersEconomy = new HashMap<>();

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
        Map<String, Float> sortedBowlersEconomy = new LinkedHashMap<>();

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

    private static void findMostSixBatsmanByYearAndVenue(String year, String venue, List<Match> matches,
                                                     List<Delivery> deliveries) {
        Set<String> matchYearId = getMatchYearId(matches, year, venue);
        Map<String, Integer> batsmanScoredSix = getBatsmansScoredSix(matchYearId, deliveries);

        List<Map.Entry<String, Integer>> batsmanScoredSixSortedList = new ArrayList<>(batsmanScoredSix.entrySet());
        batsmanScoredSixSortedList.sort(Map.Entry.comparingByValue(Collections.reverseOrder()));
        Map<String, Integer> batsmanScoredSixSorted = new LinkedHashMap<>();

        for (Map.Entry<String, Integer>batsman : batsmanScoredSixSortedList) {
            batsmanScoredSixSorted.put(batsman.getKey(), batsman.getValue());
        }

        System.out.println("Most sixes by a batsman in year " + year + " at " + venue + " :- ");
        for (Map.Entry<String, Integer> batsman : batsmanScoredSixSorted.entrySet()) {
            System.out.println("Batsman:- " + batsman.getKey() + ", Sixes:- " + batsman.getValue());
            break;
        }
    }

    private static void findMostMaidenOverByBowlerInAYear(String year, List<Match> matches, List<Delivery> deliveries) {
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
            break;
        }

    }

    private static void findMostRunsByTeamInAMatch(List<Delivery>deliveries){
        String id = "0";
        int runs = 0;
        String team = "";
        Map<String,Integer> teamScore = new HashMap<>();

        for(Delivery delivery : deliveries){
            if( (id.equals("0") || id.equals(delivery.getId())) && (team.isEmpty() || team.equals(delivery.getBattingTeam()))){
                runs += Integer.parseInt(delivery.getTotalRuns());
                team = delivery.getBattingTeam();
                id = delivery.getId();
            }
            else{
                if(runs > teamScore.getOrDefault(team,0))
                  teamScore.put(team,runs);
                id = delivery.getId();
                runs = Integer.parseInt(delivery.getTotalRuns());
                team = delivery.getBattingTeam();
            }
        }

        if(runs > 0 && !team.isEmpty() && !id.equals("0")){
            teamScore.put(team,runs);
        }

        for(String elem: teamScore.keySet()){
            System.out.println("Team:-" + elem + ", Runs:-"+ teamScore.get(elem));
        }
    }

    private static void findMostMatchesInAVenue(List<Match> matches){

        Map<String,Set<String>> matchesPlayedInVenue = new HashMap<>();

        for(Match match:matches){
            Set<String> elem = matchesPlayedInVenue.getOrDefault(match.getVenue(), new HashSet<>());
            elem.add(match.getId());
            matchesPlayedInVenue.put(match.getVenue(), elem);
        }

        List<Map.Entry<String,Set<String>>> list = new ArrayList<>(matchesPlayedInVenue.entrySet());
        Collections.sort(list, Comparator.comparing(entry -> entry.getValue().size()));
        Collections.reverse(list);

        for (Map.Entry<String, Set<String>> entry : list) {
            System.out.println("Venue:-" +  entry.getKey() + ", No of matches:-" + entry.getValue().size());
        }
    }

    private static void findHighestWicketTaker(List<Match> matches,List<Delivery> deliveries){
        String id = "0";
        //season,wicket
        Map<String,Integer> seasonsHighestBowler = new HashMap<>();
        Map<String,String> seasonId = new HashMap<>();
        Map<String,Integer> matchHighestWicketTakerBowler = new HashMap<>();
        Map<String,String> seasonsHighestBowlerName = new HashMap<>();

        for(Match match:matches){
            seasonId.put(match.getId(),match.getSeason());
        }
        String match_id = "";
        int highestWicket = 0;
        String highestBowler = "";
        for(Delivery delivery : deliveries){
            if(match_id.isEmpty() || match_id.equals(delivery.getId())){
                if(delivery.getPlayerDismissed() != null && !delivery.getDismissalKind().equals("run out")){
                    int prevWicket = matchHighestWicketTakerBowler.getOrDefault(delivery.getBowler(),0);
                    matchHighestWicketTakerBowler.put(delivery.getBowler(), prevWicket + 1);
                    if(prevWicket + 1 > highestWicket){
                        highestWicket = prevWicket + 1;
                        highestBowler = delivery.getBowler();
                    }
                }
                match_id = delivery.getId();
            }
            else{
                String year = seasonId.get(match_id);
                if(seasonsHighestBowler.getOrDefault(year,0) < highestWicket){
                    seasonsHighestBowler.put(year,highestWicket);
                    seasonsHighestBowlerName.put(year,highestBowler);
                }
                highestBowler = "";
                highestWicket = 0;
                match_id = delivery.getId();
                matchHighestWicketTakerBowler.clear();
                matchHighestWicketTakerBowler.put(delivery.getBowler(),1);
            }
        }

        for(Map.Entry<String,Integer> elem: seasonsHighestBowler.entrySet()){
            System.out.println("Season:-" + elem.getKey() + ", Bowler:-" + seasonsHighestBowlerName.get(elem.getKey())
            + ", Wickets:-" + elem.getValue());
        }
    }

    public static void findNoOfOutsInASeason(String year,List<Delivery> deliveries,List<Match> matches){
        Set<String> matchIds = getMatchYearId(matches,year);
        Map<String,Integer> countMatches = new HashMap<>();
        Map<String,Integer> result = new HashMap<>();

        for(Delivery delivery:deliveries){
            if(matchIds.contains(delivery.getId())){
                if(delivery.getDismissalKind() != null) {
                    if (delivery.getDismissalKind().equals("caught") || delivery.getDismissalKind().equals("run out")) {
                        int prevCount = countMatches.getOrDefault(delivery.getPlayerDismissed(), 0);
                        countMatches.put(delivery.getPlayerDismissed(), prevCount + 1);
                    }
                }
            }
        }

        for(Map.Entry<String,Integer> elem: countMatches.entrySet()){
           if(elem.getValue() > 10){
               result.put(elem.getKey(),elem.getValue());
           }
        }

        for(Map.Entry<String,Integer> elem:result.entrySet()){
            System.out.println("player:-" + elem.getKey() + ",matches " + elem.getValue());
        }
    }
}