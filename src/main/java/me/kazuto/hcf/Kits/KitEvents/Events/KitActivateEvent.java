/* (Copyright) 2024 github.com/kaz2700 */
package me.kazuto.hcf.Kits.KitEvents.Events;

import lombok.Getter;
import lombok.Setter;
import me.kazuto.hcf.Kits.Kit;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class KitActivateEvent extends Event implements Cancellable {
	private static final HandlerList handlers = new HandlerList();

	@Getter
	@Setter
	private boolean cancelled;

	@Getter
	private Player player;

	@Getter
	private Kit kit;

	public KitActivateEvent(Player player, Kit kit) {
		this.player = player;
		this.kit = kit;
	}

	public HandlerList getHandlers() {
		return handlers;
	}

	public static HandlerList getHandlerList() {
		return handlers;
	}
}
