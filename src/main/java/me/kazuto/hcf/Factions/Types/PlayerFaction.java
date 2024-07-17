/* (Copyright) 2024 github.com/kaz2700 */
package me.kazuto.hcf.Factions.Types;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import lombok.Getter;
import lombok.Setter;
import me.kazuto.hcf.Database.DataBase;
import me.kazuto.hcf.Factions.Claim.Claim;
import me.kazuto.hcf.Factions.Faction;
import me.kazuto.hcf.Factions.FactionManager;
import me.kazuto.hcf.Factions.Player.FactionPlayer;
import me.kazuto.hcf.Factions.Player.FactionPlayerManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.jetbrains.annotations.NotNull;

public class PlayerFaction extends Faction {

	@Getter
	private ArrayList<FactionPlayer> players = new ArrayList<>();
	@Getter
	private ArrayList<FactionPlayer> coleaders = new ArrayList<>();
	@Getter
	private ArrayList<FactionPlayer> captains = new ArrayList<>();
	@Getter
	private ArrayList<FactionPlayer> invitedPlayers = new ArrayList<>();

	@Getter
	@Setter
	private UUID uuid;
	@Getter
	@Setter
	int balance;

	@Getter
	@Setter
	float dtr;

	@Getter
	@Setter
	String announcement;

	@Setter
	@Getter
	FactionPlayer leader;

	@Getter
	@Setter
	boolean isOpen;

	public static PlayerFaction getPlayerFactionFromDataBase(ResultSet row) throws SQLException {
		UUID uuid = UUID.fromString(row.getString(row.findColumn("uuid")));
		int balance = row.getInt(row.findColumn("balance"));
		float dtr = row.getFloat(row.findColumn("dtr"));
		String announcement = row.getString(row.findColumn("announcement"));
		boolean isOpen = row.getBoolean(row.findColumn("isOpen"));
		String name = row.getString(row.findColumn("name"));

		PlayerFaction faction = new PlayerFaction(name);

		Claim claim = null;
		String claimString = row.getString(row.findColumn("claim"));
		if (claimString != null) {
			claim = Claim.deserialize(claimString);
		}

		faction.setUuid(uuid);
		faction.setBalance(balance);
		faction.setDtr(dtr);
		faction.setAnnouncement(announcement);
		faction.setOpen(isOpen);
		faction.setName(name);
		faction.setClaim(claim);

		Bukkit.getConsoleSender().sendMessage("loaded facion: " + row.getString(row.findColumn("name")));

		return faction;
	}

	public PlayerFaction(String name) {
		super(name, 1);
		this.uuid = UUID.randomUUID();
	}

	public PlayerFaction(String name, FactionPlayer leader) {
		super(name, 1);
		this.leader = leader;
		this.uuid = UUID.randomUUID();
		addPlayer(leader);
	}

	public void addCaptain(FactionPlayer factionPlayer) {
		assert (!captains.contains(factionPlayer));
		captains.add(factionPlayer);
	}

	public void removeCaptain(FactionPlayer factionPlayer) {
		assert (!captains.contains(factionPlayer));
		captains.remove(factionPlayer);
	}

	public void invitePlayer(FactionPlayer factionPlayer) {
		assert (!invitedPlayers.contains(factionPlayer));
		invitedPlayers.add(factionPlayer);
	}

	public void uninvitePlayer(FactionPlayer factionPlayer) {
		assert (invitedPlayers.contains(factionPlayer));
		invitedPlayers.remove(factionPlayer);
	}

	public void addPlayer(FactionPlayer factionPlayer) {
		assert (!players.contains(factionPlayer));
		players.add(factionPlayer);
	}

	public void removePlayer(FactionPlayer factionPlayer) {
		assert (players.contains(factionPlayer));
		players.remove(factionPlayer);
	}

	@Override
	public String getInfo() {
		StringBuilder listOfPlayerNames = new StringBuilder();
		for (FactionPlayer factionPlayer : getPlayers()) {
			ChatColor color = ChatColor.GRAY;
			if (factionPlayer.getOfflinePlayer().isOnline()) {
				color = ChatColor.GREEN;
			}
			listOfPlayerNames.append(color).append(" ").append(factionPlayer.getName());
		}
		return String.format("Faction: %s\nPlayers: %s\nBalance: %s", getName(), listOfPlayerNames, getBalance());
	}

	public List<FactionPlayer> getOnlinePlayers() {
		return getPlayers().stream().filter(FactionPlayer::isOnline).toList();
	}

	public boolean isOnline() {
		return !getOnlinePlayers().isEmpty();
	}

	public void broadcastMessage(String message) {
		for (FactionPlayer factionPlayer : getOnlinePlayers())
			factionPlayer.getOfflinePlayer().getPlayer().sendMessage(message);
	}

	public boolean contains(FactionPlayer factionPlayer) {
		return getPlayers().contains(factionPlayer);
	}

	public void save() {
		try {
			String sql = "INSERT INTO factions (uuid, name, balance, dtr, announcement, isOpen, claim) "
					+ "VALUES (?, ?, ?, ?, ?, ?, ?) " + "ON CONFLICT (uuid) "
					+ "DO UPDATE SET name = EXCLUDED.name, balance = EXCLUDED.balance, dtr = EXCLUDED.dtr, announcement = EXCLUDED.announcement, isOpen = EXCLUDED.isOpen, claim = EXCLUDED.claim";
			PreparedStatement preparedStatement = DataBase.getInstance().getConnection().prepareStatement(sql);

			preparedStatement.setObject(1, getUuid());
			preparedStatement.setString(2, getName());
			preparedStatement.setInt(3, getBalance());
			preparedStatement.setFloat(4, getDtr());
			preparedStatement.setString(5, getAnnouncement());
			preparedStatement.setBoolean(6, isOpen());

			Claim claim = getClaim();
			preparedStatement.setString(7, claim != null ? claim.serialize() : null);

			preparedStatement.executeUpdate();

			preparedStatement.close();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
}
