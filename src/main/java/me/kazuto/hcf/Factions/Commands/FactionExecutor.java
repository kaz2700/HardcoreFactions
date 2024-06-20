package me.kazuto.hcf.Factions.Commands;

import me.kazuto.hcf.Factions.Utils.ArgumentExecutor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

public class FactionExecutor extends ArgumentExecutor {
    public FactionExecutor() {
        addArgument(new FCreateArgument());
        addArgument(new FWhoArgument());
    }
}
//todo if command is only f then show hlep