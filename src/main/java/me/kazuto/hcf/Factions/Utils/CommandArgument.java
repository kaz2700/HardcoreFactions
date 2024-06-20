package me.kazuto.hcf.Factions.Utils;

import lombok.Getter;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import java.util.ArrayList;

public class CommandArgument {
    @Getter
    final private String label;

    @Getter
    final private String description;

    @Getter
    final private String usage;

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
