package me.kazuto.hcf.Factions.Player;

import lombok.Getter;

import java.util.ArrayList;
import java.util.UUID;

public class FactionPlayerManager {
    @Getter
    ArrayList<FactionPlayer> players = new ArrayList<>();

    public void createFactionPlayer(UUID uuid) {
        FactionPlayer factionPlayer = new FactionPlayer(uuid);
        players.add(factionPlayer);
    }

    public FactionPlayer getPlayerFromUUID(UUID uuid) {
        for(FactionPlayer factionPlayer : players)
            if(factionPlayer.getUuid().equals(uuid))
                return factionPlayer;
        return null;
    }

    public FactionPlayer getPlayerFromName(String name) {
        for(FactionPlayer factionPlayer : getPlayers())
            if(factionPlayer.getName().equalsIgnoreCase(name))
                return factionPlayer;
        return null;
    }







    private static FactionPlayerManager instance = null;
    public static FactionPlayerManager getInstance() {
        if(instance == null)
            instance = new FactionPlayerManager();
        return instance;
    }
}
