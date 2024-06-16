package me.kazuto.hcf.Faction;

import me.kazuto.hcf.Player.FactionPlayer;

import java.util.ArrayList;

public class FactionManager {
    ArrayList<PlayerFaction> playerFactions = new ArrayList<>();
    public void createPlayerFaction(String name, FactionPlayer leader) {
        PlayerFaction playerFaction = new PlayerFaction(name, leader);
        playerFactions.add(playerFaction);
    }

    public Faction getFactionFromFactionPlayer(FactionPlayer factionPlayer) {
        for (PlayerFaction playerFaction : playerFactions)
            for(FactionPlayer factionPlayer1 : playerFaction.getFactionPlayers())
                if(factionPlayer == factionPlayer1)
                    return playerFaction;
        return null;
    }

    private static FactionManager instance;
    public static FactionManager getInstance() {
        if(instance == null)
            instance = new FactionManager();
        return instance;
    }
}
