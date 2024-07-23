/* (Copyright) 2024 github.com/kaz2700 */
package me.kazuto.hcf.Factions.Commands;

import me.kazuto.hcf.Config;
import me.kazuto.hcf.Factions.FactionManager;
import me.kazuto.hcf.Factions.Player.FactionPlayer;
import me.kazuto.hcf.Factions.Player.FactionPlayerManager;
import me.kazuto.hcf.Factions.Types.PlayerFaction;
import me.kazuto.hcf.Factions.Utils.CommandArgument;
import me.kazuto.hcf.Timers.TimerManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class FHomeArgument extends CommandArgument {
	public FHomeArgument() {
		super("home", "Teleport to your faction home.", "/f home");
	}

	public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
		if (!(commandSender instanceof Player player)) {
			commandSender.sendMessage(String.format("%s%sYou are the console so you don't have a faction!",
					Config.ERROR_COLOR, Config.ERROR_PREFIX));
			return false;
		}

		FactionPlayer factionPlayer = FactionPlayerManager.getInstance().getPlayerFromUUID(player.getUniqueId());

		if (!factionPlayer.hasAFaction()) {
			player.sendMessage(String.format("%s%sYou are not in a faction.", Config.ERROR_COLOR, Config.ERROR_PREFIX));
			return false;
		}

		PlayerFaction faction = FactionManager.getInstance().getFactionFromPlayer(factionPlayer);

		if (faction.getClaim() == null || faction.getClaim().getHome() == null) {
			player.sendMessage(String.format("%sYour faction doesn't have a home set.", Config.ERROR_COLOR));
			return false;
		}

		if (TimerManager.getInstance().isActive(factionPlayer.getPvpTimer())) {
			player.sendMessage(String.format("%sYou cannot teleport while on combat.", Config.ERROR_COLOR));
			return false;
		}
		// check if is in safe zone

		TimerManager.getInstance().addTimer(factionPlayer.getFHomeTimer());
		player.sendMessage(
				String.format("%sDon't move for 10 seconds to get teleported to the faction home.", Config.INFO_COLOR));
		return true;
	}
}