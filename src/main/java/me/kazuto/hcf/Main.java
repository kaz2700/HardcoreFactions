/* (Copyright) 2024 github.com/kaz2700 */
package me.kazuto.hcf;

import lombok.Getter;
import me.kazuto.hcf.Commands.Money.Balance;
import me.kazuto.hcf.Commands.Money.Pay;
import me.kazuto.hcf.Commands.Test;
import me.kazuto.hcf.Database.DataBase;
import me.kazuto.hcf.Events.*;
import me.kazuto.hcf.Factions.Claim.Border.ClaimBorderManager;
import me.kazuto.hcf.Factions.Claim.ClaimListener;
import me.kazuto.hcf.Factions.Commands.FactionExecutor;
import me.kazuto.hcf.Factions.FactionEvents.FactionClaimListener;
import me.kazuto.hcf.Factions.FactionEvents.FactionCreateListener;
import me.kazuto.hcf.Factions.FactionManager;
import me.kazuto.hcf.Factions.Player.FactionPlayerManager;
import me.kazuto.hcf.Kits.KitEvents.Listener.KitActivateListener;
import me.kazuto.hcf.Kits.KitListener;
import me.kazuto.hcf.Kits.Types.Bard;
import me.kazuto.hcf.Kits.Types.Kamikaze;
import me.kazuto.hcf.Scoreboard.ScoreboardManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.WorldBorder;
import org.bukkit.event.Listener;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

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

		Bukkit.getScheduler().scheduleSyncRepeatingTask(this, this::saveData, 0, Config.SAVE_DATA_TICKS);

		setWorldBorder();

		loadData();
		getServer().getConsoleSender()
				.sendMessage(String.format("%s%sHardcore Factions Plugin Enabled.", Config.SUCCESS_COLOR, Config.BOLD));
	}

	public void onDisable() {
		saveData();
		getServer().getConsoleSender().sendMessage(
				String.format("%s%sHardcore Factions Plugin Disabled.", Config.SUCCESS_COLOR, Config.BOLD));
	}

	public void registerEvents() {
		PluginManager pluginManager = Bukkit.getPluginManager();

		pluginManager.registerEvents(new CancelFHomeEvents(), this);
		pluginManager.registerEvents(new ChatFormatEvent(), this);
		pluginManager.registerEvents(new CreateFactionPlayerOnJoinEvent(), this);
		pluginManager.registerEvents(new EnderPearlEvent(), this);
		pluginManager.registerEvents(new KillEvent(), this);
		pluginManager.registerEvents(new AddPvpTimerEvent(), this);
		pluginManager.registerEvents(new PlayerHitTeamEvent(), this);

		pluginManager.registerEvents(new KitActivateListener(), this);
		pluginManager.registerEvents(new FactionCreateListener(), this);
		pluginManager.registerEvents(new FactionClaimListener(), this);

		pluginManager.registerEvents(ScoreboardManager.getInstance(), this);

		pluginManager.registerEvents(new ClaimListener(), this);
		pluginManager.registerEvents(new KitListener(), this);
		pluginManager.registerEvents(Bard.getInstance(), this);
		pluginManager.registerEvents(Kamikaze.getInstance(), this);
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
		getServer().getConsoleSender().sendMessage(ChatColor.DARK_GREEN + "Data loaded.");
	}
}
