package me.kazuto.hcf.Factions;

import lombok.Getter;
import me.kazuto.hcf.Factions.Types.PlayerFaction;
import me.kazuto.hcf.Factions.Player.FactionPlayer;

import java.util.ArrayList;
import java.util.List;

public class FactionManager {
    @Getter
    private ArrayList<Faction> factions = new ArrayList<>();
    public void createPlayerFaction(String name, FactionPlayer leader) {
        PlayerFaction playerFaction = new PlayerFaction(name, leader);
        factions.add(playerFaction);
    }

    public List<PlayerFaction> getPlayerFactions() {
        return factions.stream().filter(faction -> faction instanceof PlayerFaction).map(faction -> (PlayerFaction) faction).toList();
    }

    public PlayerFaction getFactionFromPlayer(FactionPlayer factionPlayer) {
        for (PlayerFaction playerFaction : getPlayerFactions())
            for(FactionPlayer factionPlayer1 : playerFaction.getPlayers())
                if(factionPlayer == factionPlayer1)
                    return playerFaction;
        return null;
    }

    public void deleteFaction(Faction faction) {
        assert(factions.contains(faction));
        factions.remove(faction);
    }

    public PlayerFaction getFactionFromName(String name) {
        for(PlayerFaction playerFaction : getPlayerFactions())
            if(playerFaction.getName().equalsIgnoreCase(name))
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