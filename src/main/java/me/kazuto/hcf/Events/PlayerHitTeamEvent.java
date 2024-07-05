package me.kazuto.hcf.Events;

import me.kazuto.hcf.Config;
import me.kazuto.hcf.Factions.FactionManager;
import me.kazuto.hcf.Factions.Player.FactionPlayer;
import me.kazuto.hcf.Factions.Player.FactionPlayerManager;
import me.kazuto.hcf.Factions.Types.PlayerFaction;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class PlayerHitTeamEvent implements Listener {
  @EventHandler(priority = EventPriority.HIGH)
  public void playerHitTeam(EntityDamageByEntityEvent event) {

    if (!(event.getDamager() instanceof Player agressor)) return;

    if (!(event.getEntity() instanceof Player victim)) return;

    FactionPlayer agressorFactionPlayer =
        FactionPlayerManager.getInstance().getPlayerFromUUID(agressor.getUniqueId());
    FactionPlayer victimFactionPlayer =
        FactionPlayerManager.getInstance().getPlayerFromUUID(victim.getUniqueId());

    PlayerFaction agressorFaction =
        FactionManager.getInstance().getFactionFromPlayer(agressorFactionPlayer);
    PlayerFaction victimFaction =
        FactionManager.getInstance().getFactionFromPlayer(victimFactionPlayer);

    if (agressorFaction == victimFaction) {
      agressor.sendMessage(
          String.format("%sYou can't hit your faction members", Config.PRIMARY_COLOR));
      event.setCancelled(true);
    }
  }
}
