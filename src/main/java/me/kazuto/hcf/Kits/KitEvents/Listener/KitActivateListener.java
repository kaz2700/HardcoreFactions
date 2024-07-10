/* (Copyright) 2024 github.com/kaz2700 */
package me.kazuto.hcf.Kits.KitEvents.Listener;

import me.kazuto.hcf.Config;
import me.kazuto.hcf.Kits.Kit;
import me.kazuto.hcf.Kits.KitEvents.Events.KitActivateEvent;
import me.kazuto.hcf.Kits.KitManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class KitActivateListener implements Listener {

	@EventHandler
	public void kitActivate(KitActivateEvent event) {
		Player player = event.getPlayer();
		Kit kit = event.getKit();
		KitManager.getInstance().addToPlayerKits(player, kit);
		player.sendMessage(String.format("%s%s activated.", Config.SUCCESS_COLOR, kit.getName()));
	}
}
