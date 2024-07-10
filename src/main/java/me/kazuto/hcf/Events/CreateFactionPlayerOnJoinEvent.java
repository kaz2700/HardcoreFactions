/* (Copyright) 2024 github.com/kaz2700 */
package me.kazuto.hcf.Events;

import java.util.UUID;
import me.kazuto.hcf.Factions.Player.FactionPlayerManager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class CreateFactionPlayerOnJoinEvent implements Listener {
	FactionPlayerManager factionPlayerManager = FactionPlayerManager.getInstance();

	@EventHandler
	public void onJoin(PlayerJoinEvent event) {
		event.setJoinMessage("");
		UUID playerUuid = event.getPlayer().getUniqueId();
		if (factionPlayerManager.getPlayerFromUUID(playerUuid) == null)
			factionPlayerManager.createFactionPlayer(playerUuid);
	}
}
