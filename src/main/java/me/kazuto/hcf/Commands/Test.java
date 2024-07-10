/* (Copyright) 2024 github.com/kaz2700 */
package me.kazuto.hcf.Commands;

import me.kazuto.hcf.Factions.Claim.Border.ClaimBorderManager;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.event.ClickEvent;
import net.kyori.adventure.text.event.HoverEvent;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class Test implements CommandExecutor {
	@Override
	public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String label,
			@NotNull String[] args) {

		if (!(commandSender instanceof Player))
			return false;
		Player player = (Player) commandSender;

		TextComponent component = Component.text("click me").clickEvent(ClickEvent.runCommand("/test"))
				.hoverEvent(HoverEvent.showText(Component.text("hi")));

		commandSender.sendMessage(component);
		return true;
	}
}
