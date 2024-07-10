/* (Copyright) 2024 github.com/kaz2700 */
package me.kazuto.hcf.Factions.Utils;

import java.util.ArrayList;
import lombok.Getter;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

public class CommandArgument {
	@Getter
	private final String label;

	@Getter
	private final String description;

	@Getter
	private final String usage;

	@Getter
	private ArrayList<String> aliases = new ArrayList<>();

	public CommandArgument(String label, String description, String usage, ArrayList<String> aliases) {
		this.label = label;
		this.description = description;
		this.usage = usage;
		this.aliases = aliases;
	}

	public CommandArgument(String label, String description, String usage) {
		this.label = label;
		this.description = description;
		this.usage = usage;
	}

	public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
		return false;
	}
}
