package me.kazuto.hcf.Factions.Commands;

import me.kazuto.hcf.Config;
import me.kazuto.hcf.Factions.FactionEvents.Events.FactionCreateEvent;
import me.kazuto.hcf.Factions.FactionManager;
import me.kazuto.hcf.Factions.Player.FactionPlayer;
import me.kazuto.hcf.Factions.Player.FactionPlayerManager;
import me.kazuto.hcf.Factions.Types.PlayerFaction;
import me.kazuto.hcf.Factions.Utils.CommandArgument;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class FCreateArgument extends CommandArgument {
    public FCreateArgument() {
        super("create", "Create a faction.", "/f create <name>");
    }

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        if(!(commandSender instanceof Player player)) {
            commandSender.sendMessage(String.format("%s%sYou need to be a player to execute this command.", Config.ERROR_COLOR, Config.ERROR_PREFIX));
            return false;
        }

        if(strings.length != 2) {
            player.sendMessage(String.format("%s%sWrong usage: %s.", Config.ERROR_COLOR, Config.ERROR_PREFIX, command.getUsage()));
            return false;
        }

        FactionPlayer factionPlayer = FactionPlayerManager.getInstance().getPlayerFromUUID(player.getUniqueId());

        String createdFactionName = strings[1];

        if(strings[1].length() < 3 || strings[1].length() > 16) {
            player.sendMessage(String.format("%s%sFaction name length must be between 3 and 16.", Config.ERROR_COLOR, Config.ERROR_PREFIX));
            return false;
        }

        if(factionPlayer.hasAFaction()) {
            player.sendMessage(String.format("%s%sYou cannot be in a faction.", Config.ERROR_COLOR, Config.ERROR_PREFIX));
            return false;
        }

        for(PlayerFaction playerFaction : FactionManager.getInstance().getPlayerFactions()) {
            if(playerFaction.getName().equalsIgnoreCase(createdFactionName)) {
                player.sendMessage(String.format("%s%sFaction already exists.", Config.ERROR_COLOR, Config.ERROR_PREFIX));
                return false;
            }
        }

        PlayerFaction faction = (PlayerFaction) FactionManager.getInstance().createPlayerFaction(strings[1], factionPlayer);
        Bukkit.getServer().getPluginManager().callEvent(new FactionCreateEvent(factionPlayer, faction));
        player.sendMessage(String.format("%sFaction %s created.", Config.SUCCESS_COLOR, strings[1]));
        return true;
    }
}
