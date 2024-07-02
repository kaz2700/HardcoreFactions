package me.kazuto.hcf;

import lombok.Getter;
import me.kazuto.hcf.Commands.Money.Balance;
import me.kazuto.hcf.Commands.Money.Pay;
import me.kazuto.hcf.Commands.Test;
import me.kazuto.hcf.Factions.Claim.ClaimListener;
import me.kazuto.hcf.Events.ChatEvent;
import me.kazuto.hcf.Events.JoinEvent;
import me.kazuto.hcf.Factions.Commands.FactionExecutor;
import me.kazuto.hcf.Events.PlayerHitEnemyEvent;
import me.kazuto.hcf.Factions.FactionEvents.FactionCreateListener;
import me.kazuto.hcf.Kits.KitListener;
import me.kazuto.hcf.Kits.Types.Bard;
import me.kazuto.hcf.Kits.Types.Kamikaze;
import me.kazuto.hcf.Scoreboard.ScoreboardManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin implements Listener {
    @Getter
    private static Main instance;
    @Override
    public void onEnable() {
        instance = this;

        ScoreboardManager.getInstance();

        registerEvents();
        registerCommands();

        getServer().getConsoleSender().sendMessage(ChatColor.GREEN + "Hardcore Factions Plugin Enabled.");
    }

    public void onDisable() {
        getServer().getConsoleSender().sendMessage(ChatColor.GREEN + "Hardcore Factions Plugin Disabled.");
    }

    public void registerEvents() {
        Bukkit.getPluginManager().registerEvents(new PlayerHitEnemyEvent(), this);
        Bukkit.getPluginManager().registerEvents(new FactionCreateListener(), this);

        Bukkit.getPluginManager().registerEvents(new JoinEvent(), this);
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
}