package me.kazuto.hcf.Factions.Commands;

import me.kazuto.hcf.Config;
import me.kazuto.hcf.Factions.FactionManager;
import me.kazuto.hcf.Factions.Types.PlayerFaction;
import me.kazuto.hcf.Factions.Utils.CommandArgument;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

public class FListArgument extends CommandArgument {
  public FListArgument() {
    super("list", "Show all online faction's.", "/f list");
  }

  @Override
  public boolean onCommand(
      CommandSender commandSender, Command command, String s, String[] strings) {
    StringBuilder fListString = new StringBuilder("Faction list:");

    if (strings.length != 1) {
      commandSender.sendMessage(
          String.format(
              "%s%sWrong usage: %s.", Config.ERROR_COLOR, Config.ERROR_PREFIX, command.getUsage()));
      return false;
    }

    for (PlayerFaction playerFaction : FactionManager.getInstance().getPlayerFactions()) {
      if (playerFaction.isOnline())
        fListString
            .append("\n")
            .append(playerFaction.getName())
            .append("[")
            .append(playerFaction.getOnlinePlayers().size())
            .append("]");
    }
    commandSender.sendMessage(fListString.toString());
    return true;
  }
}
