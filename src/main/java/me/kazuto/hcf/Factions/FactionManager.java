package me.kazuto.hcf.Factions;

import lombok.Getter;
import me.kazuto.hcf.Factions.Claim.Claim;
import me.kazuto.hcf.Factions.Types.PlayerFaction;
import me.kazuto.hcf.Factions.Player.FactionPlayer;
import me.kazuto.hcf.Factions.Types.Spawn;
import me.kazuto.hcf.Factions.Types.Wilderness;
import org.bukkit.Bukkit;
import org.bukkit.Location;

import java.util.ArrayList;
import java.util.List;

public class FactionManager {
    @Getter
    private ArrayList<Faction> factions = new ArrayList<>();

    public FactionManager() {
        factions.add(new Spawn());
        factions.add(new Wilderness());
    }

    public Faction createPlayerFaction(String name, FactionPlayer leader) {
        PlayerFaction playerFaction = new PlayerFaction(name, leader);
        factions.add(playerFaction);
        return playerFaction;
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

    public Faction getFactionFromLocation(Location location) {
        Faction highestPriorityFaction = null;
        for(Faction faction : getFactions()) {
            if(faction.getClaim() == null)
                continue;

            Location pos1 = faction.getClaim().getPos1();
            Location pos2 = faction.getClaim().getPos2();

            int xMax;
            int xMin;
            xMax = Math.max(pos1.getBlockX(), pos2.getBlockX());
            xMin = Math.min(pos1.getBlockX(), pos2.getBlockX());

            int zMax;
            int zMin;
            zMax = Math.max(pos1.getBlockZ(), pos2.getBlockZ());
            zMin = Math.min(pos1.getBlockZ(), pos2.getBlockZ());

            int x = location.getBlockX();
            int z = location.getBlockZ();
            if(xMin <= x && x <= xMax && zMin <= z && z <= zMax) {
                if(highestPriorityFaction == null)
                    highestPriorityFaction = faction;
                if(faction.getClaimPriority() > highestPriorityFaction.getClaimPriority())
                    highestPriorityFaction = faction;
            }
        }
        return highestPriorityFaction;
    }

    private static FactionManager instance;
    public static FactionManager getInstance() {
        if(instance == null)
            instance = new FactionManager();
        return instance;
    }
}