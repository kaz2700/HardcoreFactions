/* (Copyright) 2024 github.com/kaz2700 */
package me.kazuto.hcf.Events;

import me.kazuto.hcf.Factions.FactionManager;
import me.kazuto.hcf.Factions.Player.FactionPlayer;
import me.kazuto.hcf.Factions.Player.FactionPlayerManager;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class ChatEvent implements Listener {
	FactionPlayerManager factionPlayerManager = FactionPlayerManager.getInstance();
	FactionManager factionManager = FactionManager.getInstance();
	@EventHandler
	public void onChat(AsyncPlayerChatEvent event) {
		event.setCancelled(true);
		Player player = event.getPlayer();
		String playerName = player.getName();
		FactionPlayer factionPlayer = factionPlayerManager.getPlayerFromUUID(player.getUniqueId());

		if (!factionPlayer.hasAFaction()) {
			Bukkit.broadcastMessage(String.format("%s: %s", playerName, event.getMessage()));
			return;
		}

		String factionName = factionManager.getFactionFromPlayer(factionPlayer).getName();

		Bukkit.broadcastMessage(String.format("[%s]%s: %s", factionName, playerName, event.getMessage()));
	}
}
