package me.kazuto.hcf.Kits;

import com.destroystokyo.paper.event.player.PlayerArmorChangeEvent;
import me.kazuto.hcf.Kits.Types.Bard;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;
import java.util.List;

public class KitListener implements Listener {

    List<Kit> kits = Arrays.asList(new Bard());

    @EventHandler
    public void onArmorChange(PlayerArmorChangeEvent event) {
        Player player = event.getPlayer();
        for(Kit kit : kits) {
            if(Arrays.equals(player.getInventory().getArmorContents(), kit.getArmor())) {
                Bukkit.broadcastMessage("hallejujah");
            }
        }
    }
}
