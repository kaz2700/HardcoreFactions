/* (Copyright) 2024 github.com/kaz2700 */
package me.kazuto.hcf.Factions.Player;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import lombok.Getter;
import me.kazuto.hcf.Database.DataBase;
import me.kazuto.hcf.Factions.Types.PlayerFaction;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

@Getter
public class FactionPlayerManager {
	ArrayList<FactionPlayer> players = new ArrayList<>();

	public void createFactionPlayer(UUID uuid) {
		FactionPlayer factionPlayer = new FactionPlayer(uuid);
		players.add(factionPlayer);
	}

	public FactionPlayer getPlayerFromUUID(UUID uuid) {
		for (FactionPlayer factionPlayer : players)
			if (factionPlayer.getUuid().equals(uuid))
				return factionPlayer;
		return null;
	}

	public FactionPlayer getPlayerFromName(String name) {
		for (FactionPlayer factionPlayer : getPlayers())
			if (factionPlayer.getName().equalsIgnoreCase(name))
				return factionPlayer;
		return null;
	}

	public List<Player> getNearByPlayers(Player player, int radius, boolean ignoreSelf) {
		return Bukkit.getWorld(player.getWorld().getUID()).getPlayers().stream().filter(player1 -> {
			boolean withinRadius = player1.getLocation().distance(player.getLocation()) < radius;
			boolean isNotSelf = ignoreSelf ? !player1.getUniqueId().equals(player.getUniqueId()) : true;
			return withinRadius && isNotSelf;
		}).toList();
	}

	public void savePlayers() {
		for (FactionPlayer player : getPlayers()) {
			player.save();
		}
	}

	public void loadPlayers() {
		try {
			String sql = "SELECT * FROM players";
			PreparedStatement preparedStatement = DataBase.getInstance().getConnection().prepareStatement(sql);
			var result = preparedStatement.executeQuery();
			while (result.next()) {
				players.add(FactionPlayer.getFactionPlayerFromDataBase(result));
			}
			Bukkit.getConsoleSender().sendMessage(ChatColor.BLUE + "testing players");
			preparedStatement.close();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	private static FactionPlayerManager instance = null;

	public static FactionPlayerManager getInstance() {
		if (instance == null)
			instance = new FactionPlayerManager();
		return instance;
	}
}
