package me.kazuto.hcf.Factions.Commands;

import me.kazuto.hcf.Config;
import me.kazuto.hcf.Factions.FactionManager;
import me.kazuto.hcf.Factions.Player.FactionPlayer;
import me.kazuto.hcf.Factions.Player.FactionPlayerManager;
import me.kazuto.hcf.Factions.Types.PlayerFaction;
import me.kazuto.hcf.Factions.Utils.CommandArgument;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class FCloseArgument extends CommandArgument {
    public FCloseArgument() {
        super("close", "Close your faction to the public.", "/f close");
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if(!(commandSender instanceof Player player)) {
            commandSender.sendMessage(String.format("%s%sYou are the console so you don't have a faction!", Config.ERROR_COLOR, Config.ERROR_PREFIX));
            return false;
        }

        FactionPlayer factionPlayer = FactionPlayerManager.getInstance().getPlayerFromUUID(player.getUniqueId());

        if(!factionPlayer.hasAFaction()) {
            player.sendMessage(String.format("%s%sYou are not in a faction.", Config.ERROR_COLOR, Config.ERROR_PREFIX));
            return false;
        }

        PlayerFaction faction = FactionManager.getInstance().getFactionFromPlayer(factionPlayer);

        if(faction.getLeader() != factionPlayer) {
            player.sendMessage(String.format("%s%sYou are not the leader of the faction.", Config.ERROR_COLOR, Config.ERROR_PREFIX));
            return false;
        }

        if(!faction.isFOpen()) {
            player.sendMessage(String.format("%s%sYour faction is not open.", Config.ERROR_COLOR, Config.ERROR_PREFIX));
            return false;
        }

        faction.broadcastMessage(String.format("%sYour faction has been closed.", Config.WARNING_COLOR));
        faction.setFOpen(false);

        return true;
    }
}
