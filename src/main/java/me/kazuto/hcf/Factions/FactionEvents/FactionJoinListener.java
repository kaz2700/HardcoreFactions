package me.kazuto.hcf.Factions.FactionEvents;

import me.kazuto.hcf.Config;
import me.kazuto.hcf.Factions.FactionEvents.Events.FactionJoinEvent;
import me.kazuto.hcf.Factions.Types.PlayerFaction;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class FactionJoinListener implements Listener {

    @EventHandler
    public void onJoin (FactionJoinEvent event) {

        PlayerFaction faction = event.getFaction();
        Player player = event.getPlayer();
        faction.broadcastMessage(String.format("%s%s joined the faction.", Config.PRIMARY_COLOR, player.getName()));
    }
}
