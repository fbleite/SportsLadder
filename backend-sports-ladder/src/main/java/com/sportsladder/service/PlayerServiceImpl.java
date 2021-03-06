package com.sportsladder.service;

import com.sportsladder.dataaccess.PlayerRepository;
import com.sportsladder.domain.Player;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

/**
 * Created by Felipe Leite on 7/1/2017.
 */
@Service
public class PlayerServiceImpl implements PlayerService {

    @Autowired
    PlayerRepository playerRepository;

    @Override
    public List<Player> getAllPlayers() {
        return playerRepository.findAll();
    }

    @Override
    public List<Player> sortPlayersByRankAscending(List<Player> players) {
        Collections.sort(players);
        return players;
    }

    @Override
    public List<Player> swapPlayersRank(Player player1, Player player2) {

        if (player1.getRank() == null || player2.getRank() == null) {
            throw new NullPointerException("A players rank cannot be set to null for the swap");
        }

        Player player1swapped = new Player();
        Player player2swapped = new Player();

        player1swapped.setId(player1.getId());
        player1swapped.setName(player1.getName());
        player2swapped.setId(player2.getId());
        player2swapped.setName(player2.getName());

        player1swapped.setRank(player2.getRank());
        player2swapped.setRank(player1.getRank());

        List <Player> swappedRank = new ArrayList<>();

        swappedRank.add(player1swapped);
        swappedRank.add(player2swapped);

        return swappedRank;
    }


    @Override
    public Player savePlayer(Player player) {
        playerRepository.save(player);
        return player;
    }

    @Override
    public List<Player> saveAllPlayers(List<Player> players) {
        playerRepository.saveAll(players);
        return players;
    }

    @Override
    public List<Player> updateRankOffset(Player updatedPlayer, List<Player> players) {

        Optional<Player> currentPlayer = players.stream().filter(
                listPlayer -> updatedPlayer.getId() == listPlayer.getId()).findFirst();

        if (currentPlayer.isPresent()) {
            Integer currentRank = currentPlayer.get().getRank();

            currentPlayer.get().setRank(updatedPlayer.getRank());
            currentPlayer.get().setName(updatedPlayer.getName());

            if (currentRank - updatedPlayer.getRank() > 0) {
                players.stream().filter(listPlayer -> (listPlayer.getRank() != null &&
                        listPlayer.getRank() >= updatedPlayer.getRank() && listPlayer.getRank() <= currentRank &&
                        listPlayer.getId() != updatedPlayer.getId())).
                        forEach(listPlayer -> listPlayer.setRank(listPlayer.getRank() + 1));
            } else {
                players.stream().filter(listPlayer -> (listPlayer.getRank() != null &&
                        listPlayer.getRank() <= updatedPlayer.getRank() && listPlayer.getRank() >= currentRank &&
                        listPlayer.getId() != updatedPlayer.getId())).
                        forEach(listPlayer -> listPlayer.setRank(listPlayer.getRank() - 1));
            }
        }
        return players;
    }

    @Override
    public boolean deletePlayer(Long playerId) {
        playerRepository.deleteById(playerId);
        return true;
    }

    @Override
    public List<Player> getUnrankedPlayers() {
        return playerRepository.findByRank(null);
    }

    @Override
    public List<Player> adjustRankGaps(List<Player> players) {
        Predicate<Player> predicate  = player -> checkRankGap(player, players);
        List<Player> adjustedPlayers = new ArrayList<>();
        adjustedPlayers.addAll(players);
        players.stream().filter(predicate).forEach(player -> player.setRank(player.getRank() - 1));
        return adjustedPlayers;
    }

    @PostConstruct
    @Override
    public List<Player> mockPlayers() {
        Player player1 = new Player();
        player1.setName("Duane Johnson");
        player1.setRank(1);

        Player player2 = new Player();
        player2.setName("Sam Sung");
        player2.setRank(2);

        Player player3 = new Player();
        player3.setName("Ball Smasher");
        player3.setRank(3);

        Player player4 = new Player();
        player4.setName("Paddle Breaker");
        player4.setRank(4);

        Player player5 = new Player();
        player5.setName("ACE getter");
        player5.setRank(null);

        List<Player> players = new ArrayList<>();
        players.add(player1);
        players.add(player2);
        players.add(player3);
        players.add(player4);
        players.add(player5);
        this.saveAllPlayers(players);
        return  players;
    }

    /**
     *
     * @param currentPlayer please filter out the first player as this function will collapse
     * @param players
     * @return
     */
    protected boolean checkRankGap(Player currentPlayer, List<Player> players) {
        if (currentPlayer.getRank ()== null  || ((Integer)1).equals(currentPlayer.getRank())) {
            return false;
        }
        return !players.stream().filter(player -> player.getRank() != null).
                anyMatch(player -> currentPlayer.getRank() -1 == player.getRank());
    }
}
