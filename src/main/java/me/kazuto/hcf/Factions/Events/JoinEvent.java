package me.kazuto.hcf.Factions.Events;

import me.kazuto.hcf.Factions.Player.FactionPlayerManager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.util.UUID;

public class JoinEvent implements Listener {

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        event.setJoinMessage("");
        UUID uuid = event.getPlayer().getUniqueId();
        if(FactionPlayerManager.getInstance().getFactionPlayerFromUUID(uuid) == null)
            FactionPlayerManager.getInstance().createFactionPlayer(uuid);
    }
}
