package me.kazuto.hcf.Factions.Events;

import me.kazuto.hcf.Factions.FactionManager;
import me.kazuto.hcf.Factions.Player.FactionPlayer;
import me.kazuto.hcf.Factions.Player.FactionPlayerManager;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
public class ChatEvent implements Listener {
    @EventHandler
    public void onChat(AsyncPlayerChatEvent event) {
        event.setCancelled(true);
        Player player = event.getPlayer();
        String playerName = player.getName();
        FactionPlayer factionPlayer = FactionPlayerManager.getInstance().getFactionPlayerFromUUID(player.getUniqueId());

        if(!factionPlayer.hasAFaction()) {
            Bukkit.broadcastMessage(String.format("%s: %s", playerName, event.getMessage()));
            return;
        }

        String factionName = FactionManager.getInstance().getFactionFromPlayer(factionPlayer).getName();

        Bukkit.broadcastMessage(String.format("[%s]%s: %s", factionName, playerName, event.getMessage()));
    }
}
