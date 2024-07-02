package me.kazuto.hcf.Commands.Money;

import me.kazuto.hcf.Config;
import me.kazuto.hcf.Factions.Player.FactionPlayer;
import me.kazuto.hcf.Factions.Player.FactionPlayerManager;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class Balance implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String label, @NotNull String[] strings) {
        FactionPlayer factionPlayer;
        if (strings.length == 0) {
            if (!(commandSender instanceof Player player)) {
                commandSender.sendMessage(String.format("%s%sThe console is a broke boi!", Config.ERROR_COLOR, Config.ERROR_PREFIX));
                return false;
            }
            factionPlayer = FactionPlayerManager.getInstance().getPlayerFromUUID(player.getUniqueId());
        } else if (strings.length == 1) {
            factionPlayer = FactionPlayerManager.getInstance().getPlayerFromName(strings[0]);
            if(factionPlayer == null) {
                commandSender.sendMessage(String.format("%s%sPlayer not found!", Config.ERROR_COLOR, Config.ERROR_PREFIX));
                return false;
            }
        } else {
            commandSender.sendMessage(String.format("%s%s%s", Config.ERROR_COLOR, Config.ERROR_PREFIX, command.getUsage()));
            return false;
        }

        commandSender.sendMessage(String.format("%sBalance: $%s!", Config.SUCCESS_COLOR, factionPlayer.getBalance()));
        return true;
    }
}
