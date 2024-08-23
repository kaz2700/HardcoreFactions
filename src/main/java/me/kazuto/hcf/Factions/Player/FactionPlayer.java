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
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

@Getter
public class FactionPlayer {
	@Setter
	private UUID uuid;

	@Setter
	private int kills;

	@Setter
	private int balance = Config.INITIAL_BALANCE;

	@Setter
	private Location preClaimPos1 = null;

	@Setter
	private Location preClaimPos2 = null;

	private Timer pvpTimer = new Timer(30.0);;
	private Timer fHomeTimer = new Timer(10.0, () -> {
		// todo make sure it cant bug if player leaves on timer etc
		Location factionHome = getFaction().getClaim().getHome();
		Player player = Bukkit.getPlayer(getUuid());
		player.teleport(factionHome);
		player.sendMessage(String.format("%sYou have been teleported to your faction home.", Config.WARNING_COLOR));
	});
	private Timer pearlTimer = new Timer(16.0);

	@Setter
	private Timer classWarmUp;

	@Setter
	private boolean activeFMap = false;

	public FactionPlayer(UUID uuid) {
		this.uuid = uuid;
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

			String factionRank = "member";
			if (faction != null) {
				if (faction.getLeader() == this) {
					factionRank = "leader";
				} else if (faction.getColeaders().contains(this)) {
					factionRank = "coleader";
				} else if (faction.getCaptains().contains(this)) {
					factionRank = "captain";
				}
			}

			preparedStatement.setString(4, factionRank);

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
		String factionRank = row.getString(row.findColumn("factionRank"));
		FactionPlayer player = new FactionPlayer(uuid);

		player.setBalance(balance);
		loadPlayerToFaction(player, factionId, factionRank);
		return player;
	}

	public static void loadPlayerToFaction(FactionPlayer player, UUID factionId, String factionRank) {
		for (PlayerFaction faction : FactionManager.getInstance().getPlayerFactions()) {
			if (!faction.getUuid().toString().equals(factionId.toString()))
				continue;

			faction.addPlayer(player);

			switch (factionRank) {
				case "leader" :
					faction.setLeader(player);
					break;
				case "coleader" : // todo set coleader
					faction.addColeader(player);
					break;
				case "captain" : // todo set captain
					faction.addCaptain(player);
					break;
			}
		}
	}
}
