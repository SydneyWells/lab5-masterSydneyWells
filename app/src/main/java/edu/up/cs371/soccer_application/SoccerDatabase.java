package edu.up.cs371.soccer_application;

import android.util.Log;

import edu.up.cs371.soccer_application.soccerPlayer.SoccerPlayer;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

/**
 * Soccer player database -- presently, all dummied up
 * 
 * @author *** Sydney Wells ***
 * @version *** 26 Feb 2019 ***
 *
 */
public class SoccerDatabase implements SoccerDB {
    private HashMap<String, SoccerPlayer> ourMap;

    /**
     * add a player
     *
     * @see SoccerDB#addPlayer(String, String, int, String)
     */
    @Override
	public boolean addPlayer(String firstName, String lastName,
			int uniformNumber, String teamName) {
        ourMap = new HashMap<>();
        if(ourMap.containsKey(firstName+"##"+lastName)) {
            return false;
        }
        else
        {
            SoccerPlayer newPlayer = new SoccerPlayer(firstName, lastName, uniformNumber, teamName);
            ourMap.put(firstName+"##"+lastName, newPlayer);
            return true;
        }
	}

    /**
     * remove a player
     *
     * @see SoccerDB#removePlayer(String, String)
     */
    @Override
    public boolean removePlayer(String firstName, String lastName) {
        if(ourMap.containsKey(firstName+"##"+lastName)) {
            ourMap.remove(firstName+"##"+lastName);
            return true;
        }
        else {
            return false;
        }
    }

    /**
     * look up a player
     *
     * @see SoccerDB#getPlayer(String, String)
     */
    @Override
	public SoccerPlayer getPlayer(String firstName, String lastName) {
        if(ourMap.containsKey(firstName+"##"+lastName)) {
            return ourMap.get(firstName+"##"+lastName);
        }
        else {
            return null;
        }
    }

    /**
     * increment a player's goals
     *
     * @see SoccerDB#bumpGoals(String, String)
     */
    @Override
    public boolean bumpGoals(String firstName, String lastName) {
        if(ourMap.containsKey(firstName+"##"+lastName)) {
            ourMap.get(firstName+"##"+lastName).bumpGoals();
            return true;
        }
        else {
            return false;
        }
    }

    /**
     * increment a player's assists
     *
     * @see SoccerDB#bumpAssists(String, String)
     */
    @Override
    public boolean bumpAssists(String firstName, String lastName) {
        return false;
    }

    /**
     * increment a player's shots
     *
     * @see SoccerDB#bumpShots(String, String)
     */
    @Override
    public boolean bumpShots(String firstName, String lastName) {
        return false;
    }

    /**
     * increment a player's saves
     *
     * @see SoccerDB#bumpSaves(String, String)
     */
    @Override
    public boolean bumpSaves(String firstName, String lastName) {
        return false;
    }

    /**
     * increment a player's fouls
     *
     * @see SoccerDB#bumpFouls(String, String)
     */
    @Override
    public boolean bumpFouls(String firstName, String lastName) {
        return false;
    }

    /**
     * increment a player's yellow cards
     *
     * @see SoccerDB#bumpYellowCards(String, String)
     */
    @Override
    public boolean bumpYellowCards(String firstName, String lastName) {
        return false;
    }

    /**
     * increment a player's red cards
     *
     * @see SoccerDB#bumpRedCards(String, String)
     */
    @Override
    public boolean bumpRedCards(String firstName, String lastName) {
        return false;
    }

    /**
     * tells the number of players on a given team
     *
     * @see SoccerDB#numPlayers(String)
     */
    @Override
    // report number of players on a given team (or all players, if null)
	public int numPlayers(String teamName) {
        if(teamName == null)
        {
            return ourMap.size();
        }
        else {
            int counter = 0;
            for (SoccerPlayer player : ourMap.values()) {

                if (player.getTeamName().equals(teamName)) {
                    counter++;
                }
            }
            return counter;
        }
	}

    /**
     * gives the nth player on a the given team
     *
     * @see SoccerDB#playerNum(int, String)
     */
	// get the nTH player
	@Override
    public SoccerPlayer playerNum(int idx, String teamName) {
            int counter = 0;
            for (SoccerPlayer player : ourMap.values()) {

                if (teamName == null || player.getTeamName().equals(teamName)) {
                    if (counter == idx) {
                        return player;
                    } else {
                        counter++;
                    }
                }
            }
            return null;
    }

    /**
     * reads database data from a file
     *
     * @see SoccerDB#readData(java.io.File)
     */
	// read data from file
    @Override
	public boolean readData(File file) {
        try {
            Scanner heScan = new Scanner(file);
            while(heScan.hasNextLine()) {
                String firstName = heScan.nextLine();
                String lastName = heScan.nextLine();
                int uniform = heScan.nextInt();
                int goals = heScan.nextInt();
                int assists = heScan.nextInt();
                int shots = heScan.nextInt();
                int fouls = heScan.nextInt();
                int saves = heScan.nextInt();
                int yellowCards = heScan.nextInt();
                int redCards = heScan.nextInt();
                heScan.nextLine();
                String teamName = heScan.nextLine();
                SoccerPlayer player = new SoccerPlayer(firstName, lastName, uniform,teamName);
                for(int i = 0; i < goals; i++)
                {
                    player.bumpGoals();
                }
                for(int i = 0; i < assists; i++)
                {
                    player.bumpAssists();
                }
                for(int i = 0; i < shots; i++)
                {
                    player.bumpShots();
                }
                for(int i = 0; i < fouls; i++)
                {
                    player.bumpFouls();
                }
                for(int i = 0; i < saves; i++)
                {
                    player.bumpSaves();
                }
                for(int i = 0; i < yellowCards; i++)
                {
                    player.bumpYellowCards();
                }
                for(int i = 0; i < redCards; i++)
                {
                    player.bumpRedCards();
                }
                ourMap.put(firstName+"##"+lastName, player);
            }
            return true;
        }
        catch( FileNotFoundException e ) {
            return false;
        }
	}

    /**
     * write database data to a file
     *
     * @see SoccerDB#writeData(java.io.File)
     */
	// write data to file
    @Override
	public boolean writeData(File file) {
        try {
            PrintWriter pw = new PrintWriter(file);
            for (SoccerPlayer player : ourMap.values()) {
                pw.println(logString(player.getFirstName()));
                pw.println(logString(player.getLastName()));
                pw.println(logString(player.getUniform() + ""));
                pw.println(logString(player.getGoals() + ""));
                pw.println(logString(player.getAssists() + ""));
                pw.println(logString(player.getShots() + ""));
                pw.println(logString(player.getFouls() + ""));
                pw.println(logString(player.getSaves() + ""));
                pw.println(logString(player.getYellowCards() + ""));
                pw.println(logString(player.getRedCards() + ""));
                pw.println(logString(player.getTeamName() + ""));

            }
            pw.close();
            return true;
        }
        catch( FileNotFoundException e) {
            return false;
        }

	}

    /**
     * helper method that logcat-logs a string, and then returns the string.
     * @param s the string to log
     * @return the string s, unchanged
     */
    private String logString(String s) {
        Log.i("write string", s);
        return s;
    }

    /**
     * returns the list of team names in the database
     *
     * @see edu.up.cs371.soccer_application.SoccerDB#getTeams()
     */
	// return list of teams
    @Override
	public HashSet<String> getTeams() {
        HashSet<String> hashTeams = new HashSet<>();
        for(SoccerPlayer player : ourMap.values())
        {
            hashTeams.add(player.getTeamName());
        }
        return new HashSet<String>();
	}

}
