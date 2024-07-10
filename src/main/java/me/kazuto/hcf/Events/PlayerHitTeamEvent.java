/* (Copyright) 2024 github.com/kaz2700 */
package me.kazuto.hcf.Events;

import me.kazuto.hcf.Config;
import me.kazuto.hcf.Factions.FactionManager;
import me.kazuto.hcf.Factions.Player.FactionPlayer;
import me.kazuto.hcf.Factions.Player.FactionPlayerManager;
import me.kazuto.hcf.Factions.Types.PlayerFaction;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class PlayerHitTeamEvent implements Listener {
	FactionPlayerManager factionPlayerManager = FactionPlayerManager.getInstance();
	FactionManager factionManager = FactionManager.getInstance();

	@EventHandler
	public void playerHitTeam(EntityDamageByEntityEvent event) {
		if (Config.HIT_TEAMMATES)
			return;

		if (!(event.getDamager() instanceof Player agressor))
			return;

		if (!(event.getEntity() instanceof Player victim))
			return;

		FactionPlayer agressorFactionPlayer = factionPlayerManager.getPlayerFromUUID(agressor.getUniqueId());
		FactionPlayer victimFactionPlayer = factionPlayerManager.getPlayerFromUUID(victim.getUniqueId());

		PlayerFaction agressorFaction = factionManager.getFactionFromPlayer(agressorFactionPlayer);

		if (agressorFaction.contains(victimFactionPlayer)) {
			agressor.sendMessage(String.format("%sYou can't hit your faction members", Config.PRIMARY_COLOR));
			event.setCancelled(true);
		}
	}
}
