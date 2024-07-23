/* (Copyright) 2024 github.com/kaz2700 */
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

public class FUnclaimArgument extends CommandArgument {
	public FUnclaimArgument() {
		super("unclaim", "Unclaim the faction land.", "/f unclaim");
	}

	@Override
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

		if (faction.getLeader() != factionPlayer) {
			player.sendMessage(String.format("%s%sYou must be the leader of the faction.", Config.ERROR_COLOR,
					Config.ERROR_PREFIX));
			return false;
		}
		faction.broadcastMessage(
				String.format("%s%s has unclaimed the faction land.", Config.INFO_COLOR, factionPlayer.getName()));
		faction.setClaim(null);
		return true;
	}
}
