/* (Copyright) 2024 github.com/kaz2700 */
package me.kazuto.hcf.Events;

import me.kazuto.hcf.Factions.Player.FactionPlayer;
import me.kazuto.hcf.Factions.Player.FactionPlayerManager;
import me.kazuto.hcf.Timers.TimerManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class PlayerHitEnemyEvent implements Listener {
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

		FactionPlayer agressorFactionPlayer = factionPlayerManager.getPlayerFromUUID(damager.getUniqueId());
		FactionPlayer victimFactionPlayer = factionPlayerManager.getPlayerFromUUID(victim.getUniqueId());

		timerManager.addTimer(agressorFactionPlayer.getPvpTimer());
		timerManager.addTimer(victimFactionPlayer.getPvpTimer());
	}
}
