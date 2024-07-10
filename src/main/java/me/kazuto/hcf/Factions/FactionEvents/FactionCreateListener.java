/* (Copyright) 2024 github.com/kaz2700 */
package me.kazuto.hcf.Factions.FactionEvents;

import me.kazuto.hcf.Config;
import me.kazuto.hcf.Factions.FactionEvents.Events.FactionCreateEvent;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class FactionCreateListener implements Listener {
	@EventHandler
	public void onCreate(FactionCreateEvent event) {
		Bukkit.broadcastMessage(
				String.format("%s%s %swas created by %s%s", Config.SECONDARY_COLOR, event.getFaction().getName(),
						Config.PRIMARY_COLOR, Config.SECONDARY_COLOR, event.getPlayer().getName()));
	}
}
