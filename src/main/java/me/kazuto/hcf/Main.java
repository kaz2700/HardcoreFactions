/* (Copyright) 2024 github.com/kaz2700 */
package me.kazuto.hcf;

import lombok.Getter;
import me.kazuto.hcf.Commands.Money.Balance;
import me.kazuto.hcf.Commands.Money.Pay;
import me.kazuto.hcf.Commands.Test;
import me.kazuto.hcf.Database.DataBase;
import me.kazuto.hcf.Events.ChatEvent;
import me.kazuto.hcf.Events.CreateFactionPlayerOnJoinEvent;
import me.kazuto.hcf.Events.PlayerHitEnemyEvent;
import me.kazuto.hcf.Factions.Claim.Border.ClaimBorderManager;
import me.kazuto.hcf.Factions.Claim.ClaimListener;
import me.kazuto.hcf.Factions.Commands.FactionExecutor;
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
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Main extends JavaPlugin implements Listener {
	@Getter
	private static Main instance;

	@Override
	public void onEnable() {
		instance = this;
		registerEvents();
		registerCommands();

		ScoreboardManager.getInstance();
		ClaimBorderManager.getInstance();
		FactionManager.getInstance();
		DataBase.getInstance();

		Bukkit.getScheduler().scheduleSyncRepeatingTask(this, this::saveData, 0, 100);

		setWorldBorder();

		loadData();
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

	public void setWorldBorder() {
		WorldBorder worldBorder = Bukkit.getWorlds().get(0).getWorldBorder();
		worldBorder.setSize(2 * Config.MAP_RADIUS);
	}

	public void saveData() {
		FactionManager.getInstance().saveFactions();
		FactionPlayerManager.getInstance().savePlayers();
		Bukkit.broadcastMessage(String.format("%sData saved successfully.", Config.SUCCESS_COLOR));
	}

	public void loadData() {
		FactionManager.getInstance().loadFactions();
		FactionPlayerManager.getInstance().loadPlayers();
		getServer().getConsoleSender().sendMessage(ChatColor.GREEN + "Data loaded.");
	}
}
