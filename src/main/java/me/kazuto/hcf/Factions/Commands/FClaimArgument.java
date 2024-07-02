package me.kazuto.hcf.Factions.Commands;

import me.kazuto.hcf.Config;
import me.kazuto.hcf.Factions.Claim.ClaimingWand;
import me.kazuto.hcf.Factions.FactionManager;
import me.kazuto.hcf.Factions.Player.FactionPlayer;
import me.kazuto.hcf.Factions.Player.FactionPlayerManager;
import me.kazuto.hcf.Factions.Types.PlayerFaction;
import me.kazuto.hcf.Factions.Utils.CommandArgument;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;

public class FClaimArgument extends CommandArgument {
    public FClaimArgument() {
        super("claim", "Claim land for the faction.", "/f claim");
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if(!(commandSender instanceof Player player)) {
            commandSender.sendMessage(String.format("%s%sYou are the console so you don't have a faction!", Config.ERROR_COLOR, Config.ERROR_PREFIX));
            return false;
        }

        if(strings.length != 1) {
            player.sendMessage(String.format("%s%sWrong usage: %s.", Config.ERROR_COLOR, Config.ERROR_PREFIX, command.getUsage()));
            return false;
        }

        FactionPlayer factionPlayer = FactionPlayerManager.getInstance().getPlayerFromUUID(player.getUniqueId());
        PlayerFaction faction = FactionManager.getInstance().getFactionFromPlayer(factionPlayer);

        if(faction == null) {
            player.sendMessage(String.format("%s%sYou don't have a faction!", Config.ERROR_COLOR, Config.ERROR_PREFIX));
            return false;
        }

        if(faction.getLeader() != factionPlayer) {
            player.sendMessage(String.format("%s%sYou are not the leader of the faction!", Config.ERROR_COLOR, Config.ERROR_PREFIX));
            return false;
        }

        if(faction.getClaim() != null) {
            player.sendMessage(String.format("%s%sThe faction already has a claim!", Config.ERROR_COLOR, Config.ERROR_PREFIX));
            return false;
        }

        boolean clearHotbar = false;
        for(int i = 0; i <= 8; i++) {
            if(player.getInventory().getItem(i) == null) {
                clearHotbar = true;
            }
        }

        if(player.getInventory().contains(ClaimingWand.getClaimingWand())) {
            player.sendMessage(String.format("%s%sYou already have a claiming wand!", Config.ERROR_COLOR, Config.ERROR_PREFIX));
            return false;
        }

        if(!clearHotbar) {
            player.sendMessage(String.format("%s%sYou must have a clear slot in your hotbar!", Config.ERROR_COLOR, Config.ERROR_PREFIX));
            return false;
        }

        player.getInventory().addItem(ClaimingWand.getClaimingWand());
        player.sendMessage(String.format("%sThe gods gifted you a claiming wand.", Config.SUCCESS_COLOR));
        return true;
    }
}
