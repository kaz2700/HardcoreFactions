package me.kazuto.hcf.Kits;

import com.destroystokyo.paper.event.player.PlayerArmorChangeEvent;
import me.kazuto.hcf.Config;
import me.kazuto.hcf.Scoreboard.Implementation.FastBoard;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;

public class KitListener implements Listener {

    @EventHandler
    public void onArmorChange(PlayerArmorChangeEvent event) {
        Player player = event.getPlayer();
        Bukkit.broadcastMessage("onArmorChange");
        Kit currentKit = KitManager.getInstance().getKitFromArmor(player.getInventory().getArmorContents()); //the kit that the player changed to wiht the armorchangeevent

        if(currentKit == null) {
            Kit kit = KitManager.getInstance().getKitFromPlayer(player);
            if (kit != null) {
                KitManager.getInstance().removeFromPlayerKits(player, kit);
                player.sendMessage(String.format("%sKit %s deactivated.", Config.WARNING_COLOR, kit.getName()));
            }
            return;
        }

        if(KitManager.getInstance().getKitFromPlayer(player) == currentKit) {
            return;
        }

        KitManager.getInstance().addToPlayerKits(player, currentKit);
        player.sendMessage(String.format("%sSwitched to %s kit.", Config.SUCCESS_COLOR, currentKit.getName()));
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();

        FastBoard fastBoard = new FastBoard(player);
        fastBoard.updateLines("beaner");

        Kit currentKit = KitManager.getInstance().getKitFromArmor(player.getInventory().getArmorContents()); //the kit that the player changed to wiht the armorchangeevent
        if (currentKit != null) {
            KitManager.getInstance().addToPlayerKits(player, currentKit);
            player.sendMessage(String.format("%sSwitched to %s kit.", Config.SUCCESS_COLOR, currentKit.getName()));
        }
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();

        Kit currentKit = KitManager.getInstance().getKitFromArmor(player.getInventory().getArmorContents()); //the kit that the player changed to wiht the armorchangeevent
        if (currentKit != null) {
            KitManager.getInstance().removeFromPlayerKits(player, currentKit);
            player.sendMessage(String.format("%sKit %s deactivated.", Config.WARNING_COLOR, currentKit.getName()));
        }
    }
}