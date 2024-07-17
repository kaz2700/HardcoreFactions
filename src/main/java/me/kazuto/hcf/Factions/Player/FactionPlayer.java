/* (Copyright) 2024 github.com/kaz2700 */
package me.kazuto.hcf.Factions.Player;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;
import lombok.Getter;
import lombok.Setter;
import me.kazuto.hcf.Config;
import me.kazuto.hcf.Database.DataBase;
import me.kazuto.hcf.Factions.Claim.Claim;
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
			String sql = "INSERT INTO players (uuid, balance, factionId, factionRank) " + "VALUES (?, ?, ?, ?) "
					+ "ON CONFLICT (uuid) "
					+ "DO UPDATE SET balance = EXCLUDED.balance, factionId = EXCLUDED.factionId, factionRank = EXCLUDED.factionRank";
			PreparedStatement preparedStatement = DataBase.getInstance().getConnection().prepareStatement(sql);

			preparedStatement.setObject(1, getUuid());
			preparedStatement.setInt(2, getBalance());
			// Todo clean
			PlayerFaction faction = getFaction();
			UUID factionId;
			if (faction != null) {
				factionId = faction.getUuid();
			} else {
				factionId = null;
			}
			preparedStatement.setObject(3, factionId);

			int factionRank;
			if (faction != null && faction.getLeader() == this) {
				factionRank = 2;
			} else {
				factionRank = 0;
			}

			preparedStatement.setInt(4, factionRank);

			preparedStatement.executeUpdate();
			preparedStatement.close();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	public static FactionPlayer getFactionPlayerFromDataBase(ResultSet row) throws SQLException {
		UUID uuid = UUID.fromString(row.getString(row.findColumn("uuid")));
		int balance = row.getInt(row.findColumn("balance"));
		UUID factionId = UUID.fromString(row.getString(row.findColumn("factionId")));
		int factionRank = row.getInt(row.findColumn("factionRank"));
		Bukkit.getConsoleSender().sendMessage("1");
		FactionPlayer player = new FactionPlayer(uuid);

		player.setBalance(balance);
		loadPlayerToFaction(player, factionId, factionRank);
		Bukkit.getConsoleSender().sendMessage(
				"loaded player: " + Bukkit.getOfflinePlayer(row.getString(row.findColumn("uuid"))).getName());
		return player;
	}

	public static void loadPlayerToFaction(FactionPlayer player, UUID factionId, int factionRank) {
		for (PlayerFaction faction : FactionManager.getInstance().getPlayerFactions()) {
			Bukkit.getConsoleSender().sendMessage("not equals");
			if (!faction.getUuid().toString().equals(factionId.toString()))
				continue;
			Bukkit.getConsoleSender().sendMessage("equals");
			faction.addPlayer(player);
			Bukkit.getConsoleSender().sendMessage("added");

			switch (factionRank) {
				case 3 :
					faction.setLeader(player);
					break;
				case 2 : // todo set coleader
					break;
				case 1 : // todo set captain
					break;
			}
		}
	}
}
