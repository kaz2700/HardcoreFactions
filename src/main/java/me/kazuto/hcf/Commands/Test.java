package me.kazuto.hcf.Commands;

import me.kazuto.hcf.Factions.Player.FactionPlayerManager;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class Test implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if(!(sender instanceof Player player))
            return false;

        FactionPlayerManager.getInstance().getNearByPlayers(player, 5, true).stream().forEach(player1 -> Bukkit.broadcastMessage("player: " + player1.getName()));
        return true;
    }
}
