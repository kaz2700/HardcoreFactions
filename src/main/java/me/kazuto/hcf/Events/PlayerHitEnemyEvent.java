package me.kazuto.hcf.Events;

import me.kazuto.hcf.Factions.Player.FactionPlayer;
import me.kazuto.hcf.Factions.Player.FactionPlayerManager;
import me.kazuto.hcf.Timers.TimerManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class PlayerHitEnemyEvent implements Listener {
    @EventHandler
    public void playerHitPlayer(EntityDamageByEntityEvent event) {
        if(event.isCancelled())
            return;

        if(!(event.getDamager() instanceof Player agressor))
            return;

        if(!(event.getEntity() instanceof Player victim))
            return;

        FactionPlayer agressorFactionPlayer = FactionPlayerManager.getInstance().getPlayerFromUUID(agressor.getUniqueId());
        FactionPlayer victimFactionPlayer = FactionPlayerManager.getInstance().getPlayerFromUUID(victim.getUniqueId());

        TimerManager.getInstance().addTimer(agressorFactionPlayer.getPvpTimer());
        TimerManager.getInstance().addTimer(victimFactionPlayer.getPvpTimer());
    }
}
