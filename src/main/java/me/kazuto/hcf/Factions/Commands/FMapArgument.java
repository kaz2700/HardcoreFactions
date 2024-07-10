/* (Copyright) 2024 github.com/kaz2700 */
package me.kazuto.hcf.Factions.Commands;

import me.kazuto.hcf.Config;
import me.kazuto.hcf.Factions.Player.FactionPlayer;
import me.kazuto.hcf.Factions.Player.FactionPlayerManager;
import me.kazuto.hcf.Factions.Utils.CommandArgument;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class FMapArgument extends CommandArgument {
	public FMapArgument() {
		super("map", "Show faction territories.", "/f map");
	}

	@Override
	public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {

		if (!(commandSender instanceof Player player)) {
			commandSender.sendMessage(String.format("%s%sYou are the console so you don't have a faction!",
					Config.ERROR_COLOR, Config.ERROR_PREFIX));
			return false;
		}

		FactionPlayer factionPlayer = FactionPlayerManager.getInstance().getPlayerFromUUID(player.getUniqueId());

		boolean hasActiveFMap = !factionPlayer.isActiveFMap(); // on f map command it switches from false to true or
																// true to false
		factionPlayer.setActiveFMap(hasActiveFMap);

		if (hasActiveFMap) {
			player.sendMessage(String.format("%sShowing claiming pillars", Config.SUCCESS_COLOR));
		} else {
			player.sendMessage(String.format("%sHiding claiming pillars", Config.ERROR_COLOR));
		}
		return true;
	}
}
