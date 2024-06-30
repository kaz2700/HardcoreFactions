package me.kazuto.hcf.Factions.Commands;

import me.kazuto.hcf.Config;
import me.kazuto.hcf.Factions.FactionManager;
import me.kazuto.hcf.Factions.Player.FactionPlayer;
import me.kazuto.hcf.Factions.Player.FactionPlayerManager;
import me.kazuto.hcf.Factions.Types.PlayerFaction;
import me.kazuto.hcf.Factions.Utils.CommandArgument;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class FInviteArgument extends CommandArgument {
    public FInviteArgument() {
        super("invite", "Invite a player to the faction.", "/f invite <player>");
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if(!(commandSender instanceof Player player)) {
            commandSender.sendMessage(String.format("%s%sYou are the console so you don't have a faction!", Config.ERROR_COLOR, Config.ERROR_PREFIX));
            return false;
        }

        FactionPlayer factionPlayer = FactionPlayerManager.getInstance().getPlayerFromUUID(player.getUniqueId());

        if(FactionPlayerManager.getInstance().getPlayerFromName(strings[1]) == null) {
            player.sendMessage(String.format("%s%sThat player never joined the server.", Config.ERROR_COLOR, Config.ERROR_PREFIX));
            return false;
        }

        FactionPlayer invitedFactionPlayer = FactionPlayerManager.getInstance().getPlayerFromName(strings[1]);

        if(!factionPlayer.hasAFaction()) {
            player.sendMessage(String.format("%s%sYou are not in a faction.", Config.ERROR_COLOR, Config.ERROR_PREFIX));
            return false;
        }

        PlayerFaction faction = FactionManager.getInstance().getFactionFromPlayer(factionPlayer);

        if(faction.getInvitedPlayers().contains(invitedFactionPlayer)) {
            player.sendMessage(String.format("%s%s%s is already invited to the faction.", Config.ERROR_COLOR, Config.ERROR_PREFIX, invitedFactionPlayer.getName()));
            return false;
        }

        if(faction.getPlayers().contains(invitedFactionPlayer)) {
            player.sendMessage(String.format("%s%s%s is already in the faction.", Config.ERROR_COLOR, Config.ERROR_PREFIX, invitedFactionPlayer.getName()));
            return false;
        }

        if(invitedFactionPlayer.isOnline())
            invitedFactionPlayer.getOfflinePlayer().getPlayer().sendMessage(String.format("%s%s has invited you to join %s.", Config.SUCCESS_COLOR, factionPlayer.getName(), faction.getName()));
        player.sendMessage(String.format("%s You invited %s to the faction", Config.SUCCESS_COLOR, invitedFactionPlayer.getName()));
        faction.invitePlayer(invitedFactionPlayer);
        return true;
    }
}
