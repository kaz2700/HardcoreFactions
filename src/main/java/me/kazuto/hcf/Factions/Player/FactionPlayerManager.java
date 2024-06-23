package me.kazuto.hcf.Factions.Player;

import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
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

    public List<Player> getNearByPlayers(Player player, int radius) {
        return Bukkit.getWorld(player.getWorld().getUID()).getPlayers().stream().filter(player1 -> player1.getLocation().distance(player.getLocation()) < radius).toList();
    }







    private static FactionPlayerManager instance = null;
    public static FactionPlayerManager getInstance() {
        if(instance == null)
            instance = new FactionPlayerManager();
        return instance;
    }
}
