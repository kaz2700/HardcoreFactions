/* (Copyright) 2024 github.com/kaz2700 */
package me.kazuto.hcf.Factions.Player;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.UUID;
import lombok.Getter;
import lombok.Setter;
import me.kazuto.hcf.Config;
import me.kazuto.hcf.Database.DataBase;
import me.kazuto.hcf.Factions.FactionManager;
import me.kazuto.hcf.Factions.Types.PlayerFaction;
import me.kazuto.hcf.Timers.Timer;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.OfflinePlayer;

public class FactionPlayer {
	@Getter
	@Setter
	private UUID uuid;

	@Getter
	@Setter
	private int balance;

	@Getter
	@Setter
	private Location preClaimPos1;

	@Getter
	@Setter
	private Location preClaimPos2;

	@Getter
	private Timer pvpTimer;

	@Getter
	@Setter
	private Timer classWarmUp;

	@Getter
	@Setter
	private boolean activeFMap;

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

	public void save() {
		try {
			String sql = "INSERT INTO players (uuid, balance, factionId) " + "VALUES (?, ?, ?) " + "ON CONFLICT (uuid) "
					+ "DO UPDATE SET balance = EXCLUDED.balance, factionId = EXCLUDED.factionId";
			PreparedStatement preparedStatement = DataBase.getInstance().getConnection().prepareStatement(sql);

			preparedStatement.setObject(1, getUuid());
			preparedStatement.setInt(2, getBalance());

			PlayerFaction faction = getFaction();
			preparedStatement.setObject(3, faction != null ? faction.getUuid() : null);

			preparedStatement.executeUpdate();

			preparedStatement.close();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
}
