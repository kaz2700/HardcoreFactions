package me.kazuto.hcf;

import me.kazuto.hcf.Commands.Test;
import me.kazuto.hcf.Factions.Events.ChatEvent;
import me.kazuto.hcf.Factions.Events.JoinEvent;
import me.kazuto.hcf.Factions.Commands.FactionExecutor;
import me.kazuto.hcf.Kits.KitListener;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin implements Listener {

    @Override
    public void onEnable() {
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
    }
    public void registerCommands() {
        this.getCommand("f").setExecutor(new FactionExecutor());
        this.getCommand("test").setExecutor(new Test());
    }
}