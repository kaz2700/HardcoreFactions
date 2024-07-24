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

public class FKickArgument extends CommandArgument {
	public FKickArgument() {
		super("kick", "Kick a member from the faction.", "/f kick <player>");
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

		FactionPlayer currentLeader = FactionPlayerManager.getInstance().getPlayerFromUUID(player.getUniqueId());

		if (!currentLeader.hasAFaction()) {
			player.sendMessage(String.format("%s%sYou are not in a faction.", Config.ERROR_COLOR, Config.ERROR_PREFIX));
			return false;
		}

		PlayerFaction faction = FactionManager.getInstance().getFactionFromPlayer(currentLeader);

		if (faction.getLeader() != currentLeader) {
			player.sendMessage(String.format("%s%sYou are not the leader of the faction.", Config.ERROR_COLOR,
					Config.ERROR_PREFIX));
			return false;
		}

		FactionPlayer kickedPlayer = FactionPlayerManager.getInstance().getPlayerFromName(strings[1]);

		if (kickedPlayer == null || !faction.getPlayers().contains(kickedPlayer)) {
			player.sendMessage(
					String.format("%s%sThat player is not in the faction.", Config.ERROR_COLOR, Config.ERROR_PREFIX));
			return false;
		}
		faction.broadcastMessage(
				String.format("%s%s has been kicked from the faction!", Config.INFO_COLOR, kickedPlayer.getName()));
		faction.removePlayer(kickedPlayer);
		return true;
	}
}