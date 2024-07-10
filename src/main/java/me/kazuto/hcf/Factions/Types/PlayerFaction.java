/* (Copyright) 2024 github.com/kaz2700 */
package me.kazuto.hcf.Factions.Types;

import java.util.*;

import lombok.Getter;
import lombok.Setter;
import me.kazuto.hcf.Factions.Claim.Claim;
import me.kazuto.hcf.Factions.Faction;
import me.kazuto.hcf.Factions.Player.FactionPlayer;
import me.kazuto.hcf.Factions.Player.FactionPlayerManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.jetbrains.annotations.NotNull;

public class PlayerFaction extends Faction implements ConfigurationSerializable {
	@Getter
	ArrayList<FactionPlayer> players = new ArrayList<>();
	@Getter
	ArrayList<FactionPlayer> coleaders = new ArrayList<>();
	@Getter
	ArrayList<FactionPlayer> captains = new ArrayList<>();

	@Getter
	ArrayList<FactionPlayer> invitedPlayers = new ArrayList<>();

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
		return String.format("Faction: %s\nPlayers:%s", getName(), listOfPlayerNames);
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

	@Override
	public @NotNull Map<String, Object> serialize() {
		Map<String, Object> serializedMap = new HashMap<>();
		serializedMap.put("name", getName());
		serializedMap.put("claimPriority", getClaimPriority());
		serializedMap.put("isRaidable", isRaidable());
		serializedMap.put("leaderUuid", getLeader().getUuid());

		Object claim = Optional.ofNullable(getClaim()).map(Claim::serialize).orElse(null);
		serializedMap.put("claim", claim);

		return serializedMap;
	}

	public static PlayerFaction deserialize(Map<String, Object> serializedMap) {
		String name = (String) serializedMap.get("name");
		int claimPriority = (int) serializedMap.get("claimPriority");
		boolean isRaidable = (boolean) serializedMap.get("isRaidable");
		UUID leaderUuid = (UUID) serializedMap.get("leaderUuid");

		FactionPlayer leader = new FactionPlayer(leaderUuid);
		PlayerFaction faction = new PlayerFaction(name, leader); // todo constructor that takes map stirng objcect and

		Bukkit.getServer().getConsoleSender().sendMessage("aaaaaaaaaaaaaaaaaaaaa\n" + serializedMap.get("claim"));
		if (serializedMap.get("claim") != null)
			faction.setClaim(Claim.deserialize((Map<String, Object>) serializedMap.get("claim")));
		faction.setRaidable(isRaidable);

		return faction;
	}
}
