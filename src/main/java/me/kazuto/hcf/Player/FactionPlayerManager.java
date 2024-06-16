package me.kazuto.hcf.Player;

import java.util.ArrayList;
import java.util.UUID;

public class FactionPlayerManager {
    ArrayList<FactionPlayer> factionPlayers = new ArrayList<>();

    public void createFactionPlayer(UUID uuid) {
        FactionPlayer factionPlayer = new FactionPlayer(uuid);
        factionPlayers.add(factionPlayer);
    }

    public FactionPlayer getFactionPlayerFromUUID(UUID uuid) {
        for(FactionPlayer factionPlayer : factionPlayers)
            if(factionPlayer.getUuid().equals(uuid))
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
