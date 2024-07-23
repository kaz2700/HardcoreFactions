/* (Copyright) 2024 github.com/kaz2700 */
package me.kazuto.hcf.Events;

import me.kazuto.hcf.Config;
import me.kazuto.hcf.Factions.Player.FactionPlayerManager;
import me.kazuto.hcf.Timers.Timer;
import me.kazuto.hcf.Timers.TimerManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerMoveEvent;

public class CancelFHomeEvents implements Listener {
	FactionPlayerManager factionPlayerManager = FactionPlayerManager.getInstance();
	TimerManager timerManager = TimerManager.getInstance();
	@EventHandler
	public void playerHitPlayer(EntityDamageByEntityEvent event) {
		if (event.isCancelled()) // its cancelled if its in safe zone/same faction
			return;

		if (!(event.getDamager() instanceof Player damager))
			return;

		if (!(event.getEntity() instanceof Player victim))
			return;

		Timer agressorFHomeTimer = factionPlayerManager.getPlayerFromUUID(damager.getUniqueId()).getFHomeTimer();
		Timer victimFHomeTimer = factionPlayerManager.getPlayerFromUUID(victim.getUniqueId()).getFHomeTimer();

		if (TimerManager.getInstance().isActive(victimFHomeTimer)) {
			timerManager.cancelTimer(victimFHomeTimer);
			victim.sendMessage(String.format("%sYour f home timer has been cancelled because %s hit you.",
					Config.WARNING_COLOR, damager.getName()));
		}
		if (TimerManager.getInstance().isActive(agressorFHomeTimer)) {
			timerManager.cancelTimer(agressorFHomeTimer);
			victim.sendMessage(String.format("%sYour f home timer has been cancelled because you hit %s.",
					Config.WARNING_COLOR, victim.getName()));
		}
	}

	@EventHandler
	public void onMove(PlayerMoveEvent event) {
		if (event.isCancelled())
			return;

		Player player = event.getPlayer();
		Timer fHomeTimer = factionPlayerManager.getPlayerFromUUID(player.getUniqueId()).getFHomeTimer();

		if (TimerManager.getInstance().isActive(fHomeTimer)) {
			timerManager.cancelTimer(fHomeTimer);
			player.sendMessage(
					String.format("%sYour f home timer has been cancelled because you moved.", Config.WARNING_COLOR));
		}

	}
}
