/* (Copyright) 2024 github.com/kaz2700 */
package me.kazuto.hcf;

import lombok.Getter;
import me.kazuto.hcf.Commands.Money.Balance;
import me.kazuto.hcf.Commands.Money.Pay;
import me.kazuto.hcf.Commands.Test;
import me.kazuto.hcf.Events.ChatEvent;
import me.kazuto.hcf.Events.CreateFactionPlayerOnJoinEvent;
import me.kazuto.hcf.Events.PlayerHitEnemyEvent;
import me.kazuto.hcf.Factions.Claim.Border.ClaimBorderManager;
import me.kazuto.hcf.Factions.Claim.Claim;
import me.kazuto.hcf.Factions.Claim.ClaimListener;
import me.kazuto.hcf.Factions.Commands.FactionExecutor;
import me.kazuto.hcf.Factions.Faction;
import me.kazuto.hcf.Factions.FactionEvents.FactionClaimListener;
import me.kazuto.hcf.Factions.FactionEvents.FactionCreateListener;
import me.kazuto.hcf.Factions.FactionManager;
import me.kazuto.hcf.Factions.Player.FactionPlayer;
import me.kazuto.hcf.Factions.Player.FactionPlayerManager;
import me.kazuto.hcf.Factions.Types.PlayerFaction;
import me.kazuto.hcf.Kits.KitEvents.Listener.KitActivateListener;
import me.kazuto.hcf.Kits.KitListener;
import me.kazuto.hcf.Kits.Types.Bard;
import me.kazuto.hcf.Kits.Types.Kamikaze;
import me.kazuto.hcf.Scoreboard.ScoreboardManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.WorldBorder;
import org.bukkit.configuration.serialization.ConfigurationSerialization;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Map;

public class Main extends JavaPlugin implements Listener {
	@Getter
	private static Main instance;

	@Override
	public void onEnable() {
		instance = this;

		try {
			Class.forName("org.postgresql.Driver");
			DriverManager.getConnection(
					"jdbc:postgresql://aws-0-eu-west-2.pooler.supabase.com:6543/postgres?user=postgres.dtlnbnlqnuoyfiouhwwi&password=VWY8HkpoM5I7o0qx");
			Bukkit.getConsoleSender().sendMessage("nigur sucesful");
		} catch (Exception e) {
			throw new RuntimeException(e);
		}

		registerSerialization(); // this has to run first
		registerEvents();
		registerCommands();

		ScoreboardManager.getInstance();
		ClaimBorderManager.getInstance();
		FactionManager.getInstance();

		setWorldBorder();

		getServer().getConsoleSender().sendMessage(ChatColor.GREEN + "Hardcore Factions Plugin Enabled.");
	}

	public void onDisable() {
		saveData();
		getServer().getConsoleSender().sendMessage(ChatColor.GREEN + "Hardcore Factions Plugin Disabled.");
	}

	public void registerEvents() {
		Bukkit.getPluginManager().registerEvents(new PlayerHitEnemyEvent(), this);
		Bukkit.getPluginManager().registerEvents(new KitActivateListener(), this);

		Bukkit.getPluginManager().registerEvents(new FactionCreateListener(), this);
		Bukkit.getPluginManager().registerEvents(new FactionClaimListener(), this);

		Bukkit.getPluginManager().registerEvents(new CreateFactionPlayerOnJoinEvent(), this);
		Bukkit.getPluginManager().registerEvents(new ChatEvent(), this);

		Bukkit.getPluginManager().registerEvents(ScoreboardManager.getInstance(), this);

		Bukkit.getPluginManager().registerEvents(new ClaimListener(), this);
		Bukkit.getPluginManager().registerEvents(new KitListener(), this);
		Bukkit.getPluginManager().registerEvents(Bard.getInstance(), this);
		Bukkit.getPluginManager().registerEvents(Kamikaze.getInstance(), this);
	}

	public void registerCommands() {
		this.getCommand("f").setExecutor(new FactionExecutor());
		this.getCommand("test").setExecutor(new Test());
		this.getCommand("balance").setExecutor(new Balance());
		this.getCommand("pay").setExecutor(new Pay());
	}

	public void registerSerialization() {
		ConfigurationSerialization.registerClass(PlayerFaction.class, "playerFaction");
		ConfigurationSerialization.registerClass(Claim.class, "claim");
	}

	public void setWorldBorder() {
		WorldBorder worldBorder = Bukkit.getWorlds().get(0).getWorldBorder();
		worldBorder.setSize(2 * Config.MAP_RADIUS);
	}

	public void saveData() {
		getServer().getConsoleSender().sendMessage("\nFactions: ");
		for (PlayerFaction faction : FactionManager.getInstance().getPlayerFactions()) {
			getServer().getConsoleSender().sendMessage("a:   " + faction.serialize());
			getServer().getConsoleSender()
					.sendMessage("b:    " + PlayerFaction.deserialize(faction.serialize()).serialize());
		}
		getServer().getConsoleSender().sendMessage("\nPlayers: ");
		for (FactionPlayer factionPlayer : FactionPlayerManager.getInstance().getPlayers()) {
			getServer().getConsoleSender().sendMessage("a:    " + factionPlayer.serialize());
			getServer().getConsoleSender()
					.sendMessage("b:    " + FactionPlayer.deserialize(factionPlayer.serialize()).serialize());
		}
	}
}
