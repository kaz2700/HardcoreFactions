/* (Copyright) 2024 github.com/kaz2700 */
package me.kazuto.hcf.Commands.Money;

import me.kazuto.hcf.Config;
import me.kazuto.hcf.Factions.Player.FactionPlayer;
import me.kazuto.hcf.Factions.Player.FactionPlayerManager;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class Pay implements CommandExecutor {
	FactionPlayerManager factionPlayerManager = FactionPlayerManager.getInstance();
	@Override
	public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String label,
			@NotNull String[] strings) {

		if (!(commandSender instanceof Player moneySender)) {
			commandSender.sendMessage(
					String.format("%s%sThe console is a broke boi!", Config.ERROR_COLOR, Config.ERROR_PREFIX));
			return false;
		}

		if (strings.length != 2) {
			commandSender
					.sendMessage(String.format("%s%s%s", Config.ERROR_COLOR, Config.ERROR_PREFIX, command.getUsage()));
			return false;
		}

		FactionPlayer payedFactionPlayer = factionPlayerManager.getPlayerFromName(strings[0]);

		if (payedFactionPlayer == null) {
			moneySender.sendMessage(String.format("%s%sPlayer not found!", Config.ERROR_COLOR, Config.ERROR_PREFIX));
			return false;
		}

		FactionPlayer moneySenderFactionPlayer = factionPlayerManager.getPlayerFromUUID(moneySender.getUniqueId());

		int amount;
		try {
			amount = Integer.parseInt(strings[1]);
		} catch (NumberFormatException exception) {
			moneySender.sendMessage(
					String.format("%s%sAmount must be an integer!", Config.ERROR_COLOR, Config.ERROR_PREFIX));
			return false;
		}

		if (amount <= 0) {
			moneySender.sendMessage(
					String.format("%s%sAmount must be positive!", Config.ERROR_COLOR, Config.ERROR_PREFIX));
			return false;
		}

		int playerBalance = moneySenderFactionPlayer.getBalance();

		if (amount > playerBalance) {
			moneySender.sendMessage(String.format("%s%sNot enough money!", Config.ERROR_COLOR, Config.ERROR_PREFIX));
			return false;
		}

		moneySenderFactionPlayer.setBalance(playerBalance - amount);
		payedFactionPlayer.setBalance(payedFactionPlayer.getBalance() + amount);

		moneySender.sendMessage(String.format("%sYou sent $%s to %s.", Config.NOTIFICATION_COLOR, amount,
				payedFactionPlayer.getName()));

		OfflinePlayer payedPlayer = payedFactionPlayer.getOfflinePlayer();
		if (payedPlayer.isOnline())
			payedPlayer.getPlayer().sendMessage(String.format("%s%s payed you $%s. Don't forget to say thanks!",
					Config.NOTIFICATION_COLOR, moneySenderFactionPlayer.getName(), amount));

		return true;
	}
}
