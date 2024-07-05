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

public class FLeaveArgument extends CommandArgument {

  public FLeaveArgument() {
    super("leave", "Leave the faction.", "/f leave");
  }

  @Override
  public boolean onCommand(
      CommandSender commandSender, Command command, String s, String[] strings) {
    if (!(commandSender instanceof Player player)) {
      commandSender.sendMessage(
          String.format(
              "%s%sYou are the console so you can't leave a faction!",
              Config.ERROR_COLOR, Config.ERROR_PREFIX));
      return false;
    }

    if (strings.length != 1) {
      player.sendMessage(
          String.format(
              "%s%sWrong usage: %s.", Config.ERROR_COLOR, Config.ERROR_PREFIX, command.getUsage()));
      return false;
    }

    FactionPlayer leavingPlayer =
        FactionPlayerManager.getInstance().getPlayerFromUUID(player.getUniqueId());

    if (!leavingPlayer.hasAFaction()) {
      player.sendMessage(
          String.format(
              "%s%sYou must have a faction before leaving.",
              Config.ERROR_COLOR, Config.ERROR_PREFIX));
      return false;
    }

    PlayerFaction faction = FactionManager.getInstance().getFactionFromPlayer(leavingPlayer);

    if (faction.getLeader() == leavingPlayer) {
      player.sendMessage(
          String.format(
              "%s%sYou are the leader of the faction. Use: /f disband or make someone else a leader before leaving.",
              Config.ERROR_COLOR, Config.ERROR_PREFIX));
      return false;
    }

    faction.removePlayer(leavingPlayer);
    faction.broadcastMessage(
        String.format("%s%s left the faction.", Config.ERROR_COLOR, leavingPlayer.getName()));
    return true;
  }
}
