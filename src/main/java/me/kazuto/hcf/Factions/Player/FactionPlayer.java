/* (Copyright) 2024 github.com/kaz2700 */
package me.kazuto.hcf.Factions.Player;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import lombok.Getter;
import lombok.Setter;
import me.kazuto.hcf.Config;
import me.kazuto.hcf.Factions.Claim.Claim;
import me.kazuto.hcf.Factions.FactionManager;
import me.kazuto.hcf.Factions.Types.PlayerFaction;
import me.kazuto.hcf.Timers.Timer;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.jetbrains.annotations.NotNull;

public class FactionPlayer implements ConfigurationSerializable {
	@Getter
	@Setter
	UUID uuid;

	@Getter
	@Setter
	int balance;

	@Getter
	@Setter
	Location preClaimPos1;

	@Getter
	@Setter
	Location preClaimPos2;

	@Getter
	Timer pvpTimer;

	@Getter
	@Setter
	Timer classWarmUp;

	@Getter
	@Setter
	boolean activeFMap;

	public FactionPlayer(UUID uuid) {
		this.uuid = uuid;
		this.setBalance(Config.INITIAL_BALANCE);
		preClaimPos1 = null;
		preClaimPos2 = null;
		this.pvpTimer = new Timer(30.0);
		this.activeFMap = false;
	}

	public FactionPlayer(UUID uuid, int balance) {
		this.uuid = uuid;
		this.setBalance(balance);
		preClaimPos1 = null;
		preClaimPos2 = null;
		this.pvpTimer = new Timer(30.0);
		this.activeFMap = false;
	}

	public OfflinePlayer getOfflinePlayer() {
		return Bukkit.getOfflinePlayer(getUuid());
	}

	public String getName() {
		return getOfflinePlayer().getName();
	}

	public PlayerFaction getFaction() {
		return FactionManager.getInstance().getFactionFromPlayer(this);
	}

	public boolean hasAFaction() {
		return getFaction() != null;
	}

	public boolean isOnline() {
		return getOfflinePlayer().isOnline();
	}

	@Override
	public @NotNull Map<String, Object> serialize() {
		Map<String, Object> serializedMap = new HashMap<>();
		serializedMap.put("uuid", uuid);
		serializedMap.put("balance", balance);

		return serializedMap;
	}

	public static FactionPlayer deserialize(Map<String, Object> serializedMap) {
		UUID uuid = (UUID) serializedMap.get("uuid");
		int balance = (int) serializedMap.get("balance");
		return new FactionPlayer(uuid, balance);
	}
}
