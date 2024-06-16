package me.kazuto.hcf.Events;

import me.kazuto.hcf.Player.FactionPlayerManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.util.UUID;

public class JoinEvent implements Listener {

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        UUID uuid = event.getPlayer().getUniqueId();
        if(FactionPlayerManager.getInstance().getFactionPlayerFromUUID(uuid) == null)
            FactionPlayerManager.getInstance().createFactionPlayer(uuid);
    }
}
