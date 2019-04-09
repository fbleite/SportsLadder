package com.sportsladder.web;

import com.sportsladder.domain.Player;
import com.sportsladder.service.PlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Felipe Leite on 7/1/2017.
 */
//@RestController
    @Controller
@RequestMapping("/player")
public class PlayerController {

    @Autowired
    private PlayerService playerService;

    @RequestMapping(value = "/mockdata/")
    public String setup(ModelMap modelMap) {
        Player player1 = new Player();
        player1.setName("Chris Diehl");
        player1.setRank(1);

        Player player2 = new Player();
        player2.setName("Felipe Leite");
        player2.setRank(2);

        Player player3 = new Player();
        player3.setName("Michael Valdes");
        player3.setRank(3);

        Player player4 = new Player();
        player4.setName("Jide Laoye");
        player4.setRank(4);

        Player player5 = new Player();
        player5.setName("Tong Luo");
        player5.setRank(null);

        List<Player> players = new ArrayList<Player>();
        players.add(player1);
        players.add(player2);
        players.add(player3);
        players.add(player4);
        players.add(player5);
        playerService.saveAllPlayers(players);
        modelMap.put("players", players);
        return "players";

    }



    @RequestMapping(value = "/")
    public String getPlayers(ModelMap modelMap) {
        List<Player> players = playerService.getAllPlayers();
        modelMap.put("players",  playerService.sortPlayersByRankAscending(players));
        return "players";
    }

    @RequestMapping(value = "/add/{name}/")
    public Player addPlayer(@PathVariable String name) {
        Player player = new Player();
        player.setName(name);
        return playerService.savePlayer(player);
    }

    @RequestMapping(value = "/update/{id}/{name}/{rank}/")
    public List<Player> updatePlayer(@PathVariable Long id,
                               @PathVariable String name,
                               @PathVariable Integer rank) {
        Player player = new Player();
        player.setId(id);
        player.setName(name);
        player.setRank(rank);
        return playerService.sortPlayersByRankAscending(playerService.saveAllPlayers(
                playerService.updateRankOffset(player, playerService.getAllPlayers())));

    }
}
