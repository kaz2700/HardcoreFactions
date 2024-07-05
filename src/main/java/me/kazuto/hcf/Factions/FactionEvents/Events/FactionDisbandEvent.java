package me.kazuto.hcf.Factions.FactionEvents.Events;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class FactionDisbandEvent extends Event implements Cancellable {
  private static final HandlerList handlers = new HandlerList();
  @Getter @Setter private boolean cancelled;

  public HandlerList getHandlers() {
    return handlers;
  }

  public static HandlerList getHandlerList() {
    return handlers;
  }
}
