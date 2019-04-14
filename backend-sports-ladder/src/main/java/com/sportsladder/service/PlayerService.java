package com.sportsladder.service;

import com.sportsladder.domain.Player;

import java.util.List;

/**
 * Created by Felipe Leite on 7/1/2017.
 */
public interface PlayerService {

    /**
     * Gets the list of all players in the DB
     * @return the unordered list of players
     */
    public List<Player> getAllPlayers();

    /**
     * Sorts the players ascending by Rank
     *
     * @param players list of players to be sorted
     * @return Sorted list of players
     */
    public List <Player> sortPlayersByRankAscending(List<Player> players);

    /**
     * Swaps the ranks of the players
     * @param player1 rank of the player must not be null
     * @param player2 rank of the player must not be null
     * @return returns the two new players with the swapped ranks
     */
    public List<Player> swapPlayersRank (Player player1, Player player2);

    /**
     * Saves Player and does NOT adjust the other players around him.
     * @param player
     * @return
     */
    public Player savePlayer(Player player);

    public List<Player> saveAllPlayers(List<Player> players);

    public List<Player> updateRankOffset(Player player, List<Player> players);

    public boolean deletePlayer (Long playerId);

    public List<Player> getUnrankedPlayers ();

    public List<Player> adjustRankGaps (List<Player> players);

    public List<Player> mockPlayers();
}
