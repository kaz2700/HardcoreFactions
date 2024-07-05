package me.kazuto.hcf.Factions.FactionEvents.Events;

import lombok.Getter;
import lombok.Setter;
import me.kazuto.hcf.Factions.Faction;
import me.kazuto.hcf.Factions.Player.FactionPlayer;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class FactionCreateEvent extends Event implements Cancellable {
  private static final HandlerList handlers = new HandlerList();
  @Getter @Setter private boolean cancelled;
  @Getter FactionPlayer player;
  @Getter Faction faction;

  public FactionCreateEvent(FactionPlayer player, Faction faction) {
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
