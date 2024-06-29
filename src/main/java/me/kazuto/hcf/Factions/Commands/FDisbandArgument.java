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

public class FDisbandArgument extends CommandArgument {

    public FDisbandArgument() {
        super("disband", "Disband your faction.", "/f disband");
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings){
        if(!(commandSender instanceof Player)) {
            commandSender.sendMessage(String.format("%s%sYou are the console so you don't have a faction to disband.", Config.ERROR_COLOR, Config.ERROR_PREFIX));
            return false;
        }

        Player player = (Player) commandSender;
        FactionPlayer factionPlayer = FactionPlayerManager.getInstance().getPlayerFromUUID(player.getUniqueId());

        if(!factionPlayer.hasAFaction()) {
            player.sendMessage(String.format("%s%sYou are not in a faction.", Config.ERROR_COLOR, Config.ERROR_PREFIX));
            return false;
        }

        PlayerFaction playerFaction = FactionManager.getInstance().getFactionFromPlayer(factionPlayer);

        if(playerFaction.getLeader() != factionPlayer) {
            player.sendMessage(String.format("%s%sYou must be the faction leader to disband the faction.", Config.ERROR_COLOR, Config.ERROR_PREFIX));
            return false;
        }
        player.sendMessage(String.format("%sYou disbanded %s.", Config.SUCCESS_COLOR, playerFaction.getName()));
        FactionManager.getInstance().deleteFaction(playerFaction);
        return true;
    }
}
