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

import static java.util.Arrays.asList;

public class FWithdrawArgument extends CommandArgument {
	public FWithdrawArgument() {
		super("withdraw", "Withdraw money from the faction.", "/f withdraw <amount>",
				new ArrayList<>(asList("w", "finesse")));
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
		// Todo be leader

		PlayerFaction faction = FactionManager.getInstance().getFactionFromPlayer(factionPlayer);

		int factionBalance = faction.getBalance();

		int amount;

		try {
			if (strings[1].equalsIgnoreCase("all")) {
				amount = factionBalance;
			} else {
				amount = Integer.parseInt(strings[1]);
			}
		} catch (NumberFormatException exception) {
			player.sendMessage(
					String.format("%s%sAmount must be an integer!", Config.ERROR_COLOR, Config.ERROR_PREFIX));
			return false;
		}

		if (amount > factionBalance) {
			player.sendMessage(String.format("%s%sThe faction doesn't have enough money!", Config.ERROR_COLOR,
					Config.ERROR_PREFIX));
			return false;
		}

		if (amount <= 0) {
			player.sendMessage(
					String.format("%s%sYou need to take out some money.", Config.ERROR_COLOR, Config.ERROR_PREFIX));
			return false;
		}

		factionPlayer.setBalance(factionPlayer.getBalance() + amount);
		faction.setBalance(faction.getBalance() - amount);
		faction.broadcastMessage(
				String.format("%s withdrew $%s from the faction bank.", factionPlayer.getName(), amount));
		return true;
	}
}
