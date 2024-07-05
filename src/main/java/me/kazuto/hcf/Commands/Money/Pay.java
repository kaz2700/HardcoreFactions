package me.kazuto.hcf.Commands.Money;

import me.kazuto.hcf.Config;
import me.kazuto.hcf.Factions.Player.FactionPlayer;
import me.kazuto.hcf.Factions.Player.FactionPlayerManager;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class Pay implements CommandExecutor {
  @Override
  public boolean onCommand(
      @NotNull CommandSender commandSender,
      @NotNull Command command,
      @NotNull String label,
      @NotNull String[] strings) {
    if (strings.length != 2) {
      commandSender.sendMessage(
          String.format("%s%s%s", Config.ERROR_COLOR, Config.ERROR_PREFIX, command.getUsage()));
      return false;
    }

    if (!(commandSender instanceof Player player)) {
      commandSender.sendMessage(
          String.format(
              "%s%sThe console is a broke boi!", Config.ERROR_COLOR, Config.ERROR_PREFIX));
      return false;
    }

    FactionPlayer payedFactionPlayer =
        FactionPlayerManager.getInstance().getPlayerFromName(strings[0]);

    if (payedFactionPlayer == null) {
      player.sendMessage(
          String.format("%s%sPlayer not found!", Config.ERROR_COLOR, Config.ERROR_PREFIX));
      return false;
    }

    FactionPlayer factionPlayer =
        FactionPlayerManager.getInstance().getPlayerFromUUID(player.getUniqueId());
    int ammount = 0;
    try {
      ammount = Integer.parseInt(strings[1]);
      if (ammount <= 0) {
        player.sendMessage(
            String.format(
                "%s%sAmmount must be positive!", Config.ERROR_COLOR, Config.ERROR_PREFIX));
        return false;
      }
    } catch (NumberFormatException exception) {
      player.sendMessage(
          String.format("%s%sAmmount must be a number!", Config.ERROR_COLOR, Config.ERROR_PREFIX));
      return false;
    }

    int playerBalance = factionPlayer.getBalance();

    if (ammount > playerBalance) {
      player.sendMessage(
          String.format("%s%sNot enough money!", Config.ERROR_COLOR, Config.ERROR_PREFIX));
      return false;
    }

    factionPlayer.setBalance(playerBalance - ammount);
    payedFactionPlayer.setBalance(payedFactionPlayer.getBalance() + ammount);

    player.sendMessage("TODO MESSGES");

    FactionPlayerManager.getInstance().getNearByPlayers(player, 5, true).stream()
        .forEach(player1 -> Bukkit.broadcastMessage("player: " + player1.getName()));
    return true;
  }
}
