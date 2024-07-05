package me.kazuto.hcf.Commands;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.event.ClickEvent;
import net.kyori.adventure.text.event.HoverEvent;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class Test implements CommandExecutor {
  @Override
  public boolean onCommand(
      @NotNull CommandSender sender,
      @NotNull Command command,
      @NotNull String label,
      @NotNull String[] args) {

    if (!(sender instanceof Player)) return false;
    Bukkit.broadcastMessage("a");
    sender.sendMessage("doooonzo");

    TextComponent component =
        Component.text("click me")
            .clickEvent(ClickEvent.runCommand("/test"))
            .hoverEvent(HoverEvent.showText(Component.text("hi")));

    sender.sendMessage(component);
    sender.sendMessage("donzo");
    return true;
  }
}
