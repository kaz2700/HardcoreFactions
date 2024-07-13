/* (Copyright) 2024 github.com/kaz2700 */
package me.kazuto.hcf.Factions.Types;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.*;

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
	private final UUID uuid;
	@Getter
	@Setter
	int balance;

	@Getter
	@Setter
	float dtr;

	@Getter
	@Setter
	String announcement;

	@Getter
	@Setter
	FactionPlayer leader;

	@Getter
	@Setter
	boolean isOpen;

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
			preparedStatement.setString(7, claim != null ? claim.serialize().toString() : null);

			preparedStatement.executeUpdate();

			preparedStatement.close();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
}
