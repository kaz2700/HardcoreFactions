package me.kazuto.hcf.Factions.Types;

import me.kazuto.hcf.Config;
import me.kazuto.hcf.Factions.Claim.Claim;
import me.kazuto.hcf.Factions.Faction;
import org.bukkit.Bukkit;
import org.bukkit.Location;

public class Wilderness extends Faction {
    public Wilderness() {
        super("Wilderness", new Claim(new Location(Bukkit.getServer().getWorlds().getFirst(), Config.MAP_RADIUS, 0, Config.MAP_RADIUS), new Location(Bukkit.getServer().getWorlds().getFirst(), -1*Config.MAP_RADIUS, 0, -1*Config.MAP_RADIUS)), 0);
    }
}
