/* (Copyright) 2024 github.com/kaz2700 */
package me.kazuto.hcf.Events;

import me.kazuto.hcf.Factions.Player.FactionPlayer;
import me.kazuto.hcf.Factions.Player.FactionPlayerManager;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

public class KillEvent implements Listener {

	@EventHandler
	public void onKill(PlayerDeathEvent event) {
		Bukkit.broadcastMessage("maybe error in killevent");
		FactionPlayer factionPlayer = FactionPlayerManager.getInstance()
				.getPlayerFromUUID(event.getEntity().getUniqueId());
		factionPlayer.setKills(factionPlayer.getKills() + 1);
	}
}
