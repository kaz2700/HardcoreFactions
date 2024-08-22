/* (Copyright) 2024 github.com/kaz2700 */
package me.kazuto.hcf.Events;

import me.kazuto.hcf.Factions.Player.FactionPlayerManager;
import org.bukkit.Bukkit;
import org.bukkit.entity.EnderPearl;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ProjectileLaunchEvent;

public class EnderPearlEvent implements Listener {
	@EventHandler
	public void onJoin(ProjectileLaunchEvent event) {
		Bukkit.broadcastMessage("worko");
		Bukkit.broadcastMessage(event.getEntity().getName());
		if (!(event.getEntity() instanceof EnderPearl))
			return;
		Bukkit.broadcastMessage("worked");
	}
}
