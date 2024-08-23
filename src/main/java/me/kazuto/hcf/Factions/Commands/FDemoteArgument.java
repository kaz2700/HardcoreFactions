/* (Copyright) 2024 github.com/kaz2700 */
package me.kazuto.hcf.Factions.Commands;

import me.kazuto.hcf.Config;
import me.kazuto.hcf.Factions.FactionManager;
import me.kazuto.hcf.Factions.Player.FactionPlayer;
import me.kazuto.hcf.Factions.Player.FactionPlayerManager;
import me.kazuto.hcf.Factions.Types.PlayerFaction;
import me.kazuto.hcf.Factions.Utils.CommandArgument;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class FDemoteArgument extends CommandArgument {
	public FDemoteArgument() {
		super("demote", "Demote a faction member.", "/f demote <player>");
	}

	public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
		if (!(commandSender instanceof Player player)) {
			commandSender.sendMessage(String.format("%s%sYou are the console so you don't have a faction!",
					Config.ERROR_COLOR, Config.ERROR_PREFIX));
			return false;
		}

		if (strings.length != 2) {
			player.sendMessage(String.format("%s%s%s", Config.ERROR_COLOR, Config.ERROR_PREFIX, command.getUsage()));
			return false;
		}

		FactionPlayer factionPlayer = FactionPlayerManager.getInstance().getPlayerFromUUID(player.getUniqueId());

		if (!factionPlayer.hasAFaction()) {
			player.sendMessage(String.format("%s%sYou are not in a faction.", Config.ERROR_COLOR, Config.ERROR_PREFIX));
			return false;
		}

		PlayerFaction faction = FactionManager.getInstance().getFactionFromPlayer(factionPlayer);

		if (faction.getLeader() != factionPlayer) {
			player.sendMessage(String.format("%s%sYou are not the leader of the faction.", Config.ERROR_COLOR,
					Config.ERROR_PREFIX));
			return false;
		}

		FactionPlayer demotedPlayer = FactionPlayerManager.getInstance().getPlayerFromName(strings[1]);

		if (demotedPlayer == null || !faction.getPlayers().contains(demotedPlayer)) {
			player.sendMessage(
					String.format("%s%sThat player is not in the faction.", Config.ERROR_COLOR, Config.ERROR_PREFIX));
			return false;
		}

		if (faction.getLeader() == demotedPlayer) {
			player.sendMessage(String.format("%s%sThe faction leader cannot be demoted.", Config.ERROR_COLOR,
					Config.ERROR_PREFIX));
			return false;
		} else if (faction.getColeaders().contains(demotedPlayer)) {
			faction.broadcastMessage(
					String.format("%s%s has been demoted to captain!", Config.INFO_COLOR, demotedPlayer.getName()));
			faction.removeColeader(demotedPlayer);
			faction.addCaptain(demotedPlayer);
			return false;
		} else if (faction.getCaptains().contains(demotedPlayer)) {
			faction.broadcastMessage(
					String.format("%s%s has been demoted to member!", Config.INFO_COLOR, demotedPlayer.getName()));
			faction.removeCaptain(demotedPlayer);
			return true;
		} else {
			player.sendMessage(String.format("%sIf you want to kick %s from the faction use /f kick %s!",
					Config.INFO_COLOR, demotedPlayer.getName(), demotedPlayer.getName()));
			return true;
		}
	}
}