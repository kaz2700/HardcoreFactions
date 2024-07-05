package me.kazuto.hcf.Events;

import java.util.UUID;
import me.kazuto.hcf.Factions.Player.FactionPlayerManager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class JoinEvent implements Listener {

  @EventHandler
  public void onJoin(PlayerJoinEvent event) {
    event.setJoinMessage("");
    UUID uuid = event.getPlayer().getUniqueId();
    if (FactionPlayerManager.getInstance().getPlayerFromUUID(uuid) == null)
      FactionPlayerManager.getInstance().createFactionPlayer(uuid);
  }
}
