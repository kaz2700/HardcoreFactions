/* (Copyright) 2024 github.com/kaz2700 */
package me.kazuto.hcf.Factions.FactionEvents.Events;

import lombok.Getter;
import lombok.Setter;
import me.kazuto.hcf.Factions.Types.PlayerFaction;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class FactionClaimEvent extends Event implements Cancellable {
	private static final HandlerList handlers = new HandlerList();

	@Getter
	@Setter
	private boolean cancelled;

	@Getter
	private Player player;

	@Getter
	private PlayerFaction faction;

	public FactionClaimEvent(Player player, PlayerFaction faction) {
		this.player = player;
		this.faction = faction;
	}

	public HandlerList getHandlers() {
		return handlers;
	}

	public static HandlerList getHandlerList() {
		return handlers;
	}
}
