package me.kazuto.hcf.Kits;

import com.destroystokyo.paper.event.player.PlayerArmorChangeEvent;
import me.kazuto.hcf.Config;
import me.kazuto.hcf.Factions.Player.FactionPlayer;
import me.kazuto.hcf.Factions.Player.FactionPlayerManager;
import me.kazuto.hcf.Kits.Types.Bard;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import java.util.Arrays;
import java.util.List;

public class KitListener implements Listener {
    @EventHandler
    public void onArmorChange(PlayerArmorChangeEvent event) {
        Player player = event.getPlayer();
        for(Kit kit : KitManager.getInstance().getKits()) {
            if(Arrays.equals(player.getInventory().getArmorContents(), kit.getArmor())) {
                KitManager.getInstance().addToPlayerToKits(player, kit);
                player.sendMessage(String.format("%sSwitched to %s kit.", Config.SUCCESS_COLOR, kit.getName()));
            }
        }
    }
}