/* (Copyright) 2024 github.com/kaz2700 */
package me.kazuto.hcf.Factions.Commands;

import me.kazuto.hcf.Config;
import me.kazuto.hcf.Factions.FactionEvents.Events.FactionJoinEvent;
import me.kazuto.hcf.Factions.FactionManager;
import me.kazuto.hcf.Factions.Player.FactionPlayer;
import me.kazuto.hcf.Factions.Player.FactionPlayerManager;
import me.kazuto.hcf.Factions.Types.PlayerFaction;
import me.kazuto.hcf.Factions.Utils.CommandArgument;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class FJoinArgument extends CommandArgument {

	public FJoinArgument() {
		super("join", "Join a faction where you have an invitation.", "/f join <faction>");
	}

	@Override
	public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
		if (!(commandSender instanceof Player player)) {
			commandSender.sendMessage(String.format("%s%sYou are the console so you can't join a faction!",
					Config.ERROR_COLOR, Config.ERROR_PREFIX));
			return false;
		}

		if (strings.length != 2) {
			player.sendMessage(
					String.format("%s%sWrong usage: %s.", Config.ERROR_COLOR, Config.ERROR_PREFIX, command.getUsage()));
			return false;
		}

		FactionPlayer joiningPlayer = FactionPlayerManager.getInstance().getPlayerFromUUID(player.getUniqueId());

		if (joiningPlayer.hasAFaction()) {
			player.sendMessage(String.format("%s%sYou must leave your current faction before joining a new one.",
					Config.ERROR_COLOR, Config.ERROR_PREFIX));
			return false;
		}

		PlayerFaction faction;

		if (FactionManager.getInstance().getFactionFromName(strings[1]) != null) {
			faction = FactionManager.getInstance().getFactionFromName(strings[1]);
		} else if (FactionManager.getInstance()
				.getFactionFromPlayer(FactionPlayerManager.getInstance().getPlayerFromName(strings[1])) != null) {
			faction = FactionManager.getInstance()
					.getFactionFromPlayer(FactionPlayerManager.getInstance().getPlayerFromName(strings[1]));
		} else {
			player.sendMessage(String.format("%s%sFaction not found.", Config.ERROR_COLOR, Config.ERROR_PREFIX));
			return false;
		}

		if (!faction.getInvitedPlayers().contains(joiningPlayer) && !faction.isOpen()) {
			player.sendMessage(
					String.format("%s%sYou are not invited to the faction.", Config.ERROR_COLOR, Config.ERROR_PREFIX));
			return false;
		}

		faction.addPlayer(joiningPlayer);
		Bukkit.getServer().getPluginManager().callEvent(new FactionJoinEvent(player, faction));
		return true;
	}
}
