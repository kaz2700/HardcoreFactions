package me.kazuto.hcf.Factions.Types;

import me.kazuto.hcf.Config;
import me.kazuto.hcf.Factions.Claim.Claim;
import me.kazuto.hcf.Factions.Faction;
import org.bukkit.Bukkit;
import org.bukkit.Location;

public class Spawn extends Faction {
  public Spawn() {
    super(
        "Spawn",
        new Claim(
            new Location(
                Bukkit.getServer().getWorlds().getFirst(),
                Config.SPAWN_RADIUS,
                0,
                Config.SPAWN_RADIUS),
            new Location(
                Bukkit.getServer().getWorlds().getFirst(),
                -1 * Config.SPAWN_RADIUS,
                0,
                -1 * Config.SPAWN_RADIUS)),
        2);
  }
}
