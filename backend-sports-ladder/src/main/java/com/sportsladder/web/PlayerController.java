package com.sportsladder.web;

import com.sportsladder.domain.Player;
import com.sportsladder.service.PlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by Felipe Leite on 7/1/2017.
 */
@RestController
//    @Controller
@RequestMapping("/player")
public class PlayerController {

    @Autowired
    private PlayerService playerService;

    @RequestMapping(value = "/mockdata/")
    public List<Player> setup() {
        return playerService.mockPlayers();
    }


    @RequestMapping(value = "/")
    public List<Player> getPlayers() {
        return playerService.getAllPlayers();
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
