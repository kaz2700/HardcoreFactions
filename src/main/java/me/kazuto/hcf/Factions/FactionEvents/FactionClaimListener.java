/* (Copyright) 2024 github.com/kaz2700 */
package me.kazuto.hcf.Factions.FactionEvents;

import me.kazuto.hcf.Config;
import me.kazuto.hcf.Factions.Claim.Claim;
import me.kazuto.hcf.Factions.FactionEvents.Events.FactionClaimEvent;
import me.kazuto.hcf.Factions.Player.FactionPlayer;
import me.kazuto.hcf.Factions.Player.FactionPlayerManager;
import me.kazuto.hcf.Factions.Types.PlayerFaction;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class FactionClaimListener implements Listener {

	@EventHandler
	public void onClaim(FactionClaimEvent event) {
		Player player = event.getPlayer();
		FactionPlayer factionPlayer = FactionPlayerManager.getInstance().getPlayerFromUUID(player.getUniqueId());

		PlayerFaction faction = event.getFaction();

		faction.broadcastMessage(String.format("%sThe faction claimed land.", Config.SUCCESS_COLOR));
		faction.setClaim(new Claim(factionPlayer.getPreClaimPos1(), factionPlayer.getPreClaimPos2()));
	}
}
