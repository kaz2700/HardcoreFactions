package me.kazuto.hcf.Factions.Utils;

import me.kazuto.hcf.Config;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class ArgumentExecutor implements CommandExecutor {
    private ArrayList<CommandArgument> arguments = new ArrayList<>();
    public void addArgument(CommandArgument argument) {
        arguments.add(argument);
    }
    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        if(strings.length == 0) {
            commandSender.sendMessage(String.format("%s%sNo sub-command provided.", Config.ERROR_COLOR, Config.ERROR_PREFIX));
            return false;
        }
        for(CommandArgument argument : arguments) {
            if(argument.getLabel().equalsIgnoreCase(strings[0]) || argument.getAliases().stream().anyMatch(strings[0]::equalsIgnoreCase)) {
                boolean commandSuccess = argument.onCommand(commandSender, command, s, strings);
                if(!commandSuccess)
                    commandSender.sendMessage(String.format("%s%s%s%s%s", Config.ERROR_COLOR, Config.ERROR_PREFIX, argument.getUsage(), "\n", argument.getDescription()));//TODO maybe remove???
                return commandSuccess;
            }
        }
        commandSender.sendMessage("TODO call HelpArgument");
        return false;
    }
}
