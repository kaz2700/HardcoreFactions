package me.kazuto.hcf.Commands;

import me.kazuto.hcf.Config;
import me.kazuto.hcf.Faction.FactionManager;
import me.kazuto.hcf.Player.FactionPlayer;
import me.kazuto.hcf.Player.FactionPlayerManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class FCreateCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        if(strings[0].isEmpty())
            return false;

        if(!strings[0].toLowerCase().equals("create")) {
            return false;
        }

        if(!(commandSender instanceof Player)) {
            commandSender.sendMessage(String.format("%sYou need to be a player to execute this command.", Config.ERROR_COLOR));
            return false;
        }

        Player player = (Player) commandSender;
        FactionPlayer factionPlayer = FactionPlayerManager.getInstance().getFactionPlayerFromUUID(player.getUniqueId());

        if(strings[1].isEmpty()) {
            player.sendMessage(String.format("%s%sUse: /f create <name>", Config.ERROR_COLOR, Config.ERROR_PREFIX));
            return false;
        }

        if(strings[1].length() < 3 || strings[1].length() > 16) {
            player.sendMessage(String.format("%s%sFaction name length must be between 3 and 16.", Config.ERROR_COLOR, Config.ERROR_PREFIX));
            return false;
        }

        if(FactionManager.getInstance().getFactionFromFactionPlayer(factionPlayer) != null) {
            player.sendMessage(String.format("%s%sYou cannot be in a faction.", Config.ERROR_COLOR, Config.ERROR_PREFIX));
            return false;
        }

        FactionManager.getInstance().createPlayerFaction(strings[1], factionPlayer);
        player.sendMessage(String.format("%sFaction %s created.", Config.SUCCESS_COLOR, strings[1]));
        return true;
    }
}
