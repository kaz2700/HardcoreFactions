package me.kazuto.hcf;

import lombok.Getter;
import me.kazuto.hcf.Commands.Test;
import me.kazuto.hcf.Factions.Events.ChatEvent;
import me.kazuto.hcf.Factions.Events.JoinEvent;
import me.kazuto.hcf.Factions.Commands.FactionExecutor;
import me.kazuto.hcf.Kits.KitListener;
import me.kazuto.hcf.Kits.KitManager;
import me.kazuto.hcf.Kits.Types.Bard;
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
        getServer().getConsoleSender().sendMessage(ChatColor.GREEN + "Hardcore Factions Plugin Enabled.");

        registerEvents();
        registerCommands();
    }

    public void onDisable() {
        getServer().getConsoleSender().sendMessage(ChatColor.GREEN + "Hardcore Factions Plugin Disabled.");
    }

    public void registerEvents() {
        Bukkit.getPluginManager().registerEvents(new JoinEvent(), this);
        Bukkit.getPluginManager().registerEvents(new ChatEvent(), this);

        Bukkit.getPluginManager().registerEvents(new KitListener(), this);
        Bukkit.getPluginManager().registerEvents(new Bard(), this);
    }
    public void registerCommands() {
        this.getCommand("f").setExecutor(new FactionExecutor());
        this.getCommand("test").setExecutor(new Test());
    }
}