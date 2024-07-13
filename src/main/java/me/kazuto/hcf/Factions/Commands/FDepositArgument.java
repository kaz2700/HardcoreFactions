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

import java.util.ArrayList;
import java.util.List;
public class FDepositArgument extends CommandArgument {
	public FDepositArgument() {
		super("deposit", "Deposit money to the faction.", "/f deposit <amount>", new ArrayList<>(List.of("d")));
	}

	@Override
	public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
		if (!(commandSender instanceof Player player)) {
			commandSender.sendMessage(String.format("%s%sYou are the console so you don't have a faction!",
					Config.ERROR_COLOR, Config.ERROR_PREFIX));
			return false;
		}

		if (strings.length != 2) {
			player.sendMessage(
					String.format("%s%sWrong usage: %s.", Config.ERROR_COLOR, Config.ERROR_PREFIX, command.getUsage()));
			return false;
		}

		FactionPlayer factionPlayer = FactionPlayerManager.getInstance().getPlayerFromUUID(player.getUniqueId());

		if (!factionPlayer.hasAFaction()) {
			player.sendMessage(String.format("%s%sYou are not in a faction.", Config.ERROR_COLOR, Config.ERROR_PREFIX));
			return false;
		}

		int playerBalance = factionPlayer.getBalance();

		int amount;
		try {
			if (strings[1].equalsIgnoreCase("all")) {
				amount = playerBalance;
			} else {
				amount = Integer.parseInt(strings[1]);
			}
		} catch (NumberFormatException exception) {
			player.sendMessage(
					String.format("%s%sAmount must be an integer!", Config.ERROR_COLOR, Config.ERROR_PREFIX));
			return false;
		}

		if (amount > playerBalance) {
			player.sendMessage(String.format("%s%sNot enough money!", Config.ERROR_COLOR, Config.ERROR_PREFIX));
			return false;
		}

		if (amount <= 0) {
			player.sendMessage(
					String.format("%s%sYou need to put in some money.", Config.ERROR_COLOR, Config.ERROR_PREFIX));
			return false;
		}

		PlayerFaction faction = FactionManager.getInstance().getFactionFromPlayer(factionPlayer);

		factionPlayer.setBalance(playerBalance - amount);
		faction.setBalance(faction.getBalance() + amount);
		faction.broadcastMessage(
				String.format("%s deposited $%s in the faction bank.", factionPlayer.getName(), amount));
		return true;
	}
}
