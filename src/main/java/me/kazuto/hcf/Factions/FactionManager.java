/* (Copyright) 2024 github.com/kaz2700 */
package me.kazuto.hcf.Factions;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import lombok.Getter;
import me.kazuto.hcf.Config;
import me.kazuto.hcf.Database.DataBase;
import me.kazuto.hcf.Factions.Claim.Claim;
import me.kazuto.hcf.Factions.Player.FactionPlayer;
import me.kazuto.hcf.Factions.Types.*;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;

public class FactionManager {
	@Getter
	private ArrayList<Faction> factions = new ArrayList<>();

	public FactionManager() {
		createDefaultFactions();
	}

	public void createDefaultFactions() {
		factions.add(new Spawn());
		factions.add(new Warzone());
		factions.add(new Wilderness());

		factions.add(new Road("North Road",
				new Claim(new Location(Config.WORLD_OVERWORLD, -Config.ROAD_WIDTH, 0, -1 * (Config.SPAWN_RADIUS + 1)),
						new Location(Config.WORLD_OVERWORLD, Config.ROAD_WIDTH, 0, -1 * Config.MAP_RADIUS))));
		factions.add(new Road("South Road",
				new Claim(new Location(Config.WORLD_OVERWORLD, Config.ROAD_WIDTH, 0, Config.SPAWN_RADIUS + 1),
						new Location(Config.WORLD_OVERWORLD, -1 * Config.ROAD_WIDTH, 0, Config.MAP_RADIUS))));
		factions.add(new Road("West Road",
				new Claim(new Location(Config.WORLD_OVERWORLD, -1 * (Config.SPAWN_RADIUS + 1), 0, Config.ROAD_WIDTH),
						new Location(Config.WORLD_OVERWORLD, -Config.MAP_RADIUS, 0, -Config.ROAD_WIDTH))));
		factions.add(new Road("East Road",
				new Claim(new Location(Config.WORLD_OVERWORLD, Config.SPAWN_RADIUS + 1, 0, -Config.ROAD_WIDTH),
						new Location(Config.WORLD_OVERWORLD, Config.MAP_RADIUS, 0, Config.ROAD_WIDTH))));
	}

	public Faction createPlayerFaction(String name, FactionPlayer leader) {
		PlayerFaction playerFaction = new PlayerFaction(name, leader);
		factions.add(playerFaction);
		return playerFaction;
	}

	public List<PlayerFaction> getPlayerFactions() {
		return factions.stream().filter(faction -> faction instanceof PlayerFaction)
				.map(faction -> (PlayerFaction) faction).toList();
	}

	public PlayerFaction getFactionFromPlayer(FactionPlayer factionPlayer) {
		for (PlayerFaction playerFaction : getPlayerFactions())
			for (FactionPlayer factionPlayer1 : playerFaction.getPlayers())
				if (factionPlayer == factionPlayer1)
					return playerFaction;
		return null;
	}

	public void deleteFaction(PlayerFaction faction) {
		assert (factions.contains(faction));
		factions.remove(faction);
		try {
			String updatePlayersSql = "UPDATE players SET factionId = NULL WHERE factionId = '" + faction.getUuid()
					+ "'";
			PreparedStatement updatePlayersStmt = DataBase.getInstance().getConnection()
					.prepareStatement(updatePlayersSql);
			updatePlayersStmt.executeUpdate();
			updatePlayersStmt.close();

			String sql = "DELETE FROM factions WHERE uuid = '" + faction.getUuid() + "'";
			PreparedStatement preparedStatement = DataBase.getInstance().getConnection().prepareStatement(sql);
			preparedStatement.executeUpdate();
			Bukkit.getConsoleSender().sendMessage(ChatColor.DARK_RED + "faction delete");
			preparedStatement.close();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	public PlayerFaction getFactionFromName(String name) {
		for (PlayerFaction playerFaction : getPlayerFactions())
			if (playerFaction.getName().equalsIgnoreCase(name))
				return playerFaction;
		return null;
	}

	public Faction getFactionFromLocation(Location location) { // returns faction with highest claim priority
		Faction highestPriorityFaction = null;
		for (Faction faction : getFactions()) {
			if (faction.getClaim() == null)
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
			if (xMin <= x && x <= xMax && zMin <= z && z <= zMax) {
				if (highestPriorityFaction == null)
					highestPriorityFaction = faction;
				if (faction.getClaimPriority() > highestPriorityFaction.getClaimPriority())
					highestPriorityFaction = faction;
			}
		}
		return highestPriorityFaction;
	}

	public void saveFactions() {
		for (PlayerFaction faction : getPlayerFactions()) {
			faction.save();
		}
	}

	public void loadFactions() {
		try {
			String sql = "SELECT * FROM factions";
			PreparedStatement preparedStatement = DataBase.getInstance().getConnection().prepareStatement(sql);
			var result = preparedStatement.executeQuery();
			while (result.next()) {
				factions.add(PlayerFaction.getPlayerFactionFromDataBase(result));
			}
			preparedStatement.close();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	private static FactionManager instance;

	public static FactionManager getInstance() {
		if (instance == null)
			instance = new FactionManager();
		return instance;
	}
}
