/* (Copyright) 2024 github.com/kaz2700 */
package me.kazuto.hcf.Factions.FactionEvents;

import me.kazuto.hcf.Config;
import me.kazuto.hcf.Factions.Claim.Claim;
import me.kazuto.hcf.Factions.Claim.ClaimListener;
import me.kazuto.hcf.Factions.FactionEvents.Events.FactionClaimEvent;
import me.kazuto.hcf.Factions.Player.FactionPlayer;
import me.kazuto.hcf.Factions.Player.FactionPlayerManager;
import me.kazuto.hcf.Factions.Types.PlayerFaction;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class FactionClaimListener implements Listener {

	@EventHandler
	public void onClaim(FactionClaimEvent event) {
		Player player = event.getPlayer();
		FactionPlayer factionPlayer = FactionPlayerManager.getInstance().getPlayerFromUUID(player.getUniqueId());

		PlayerFaction faction = event.getFaction();

		Location pos1 = factionPlayer.getPreClaimPos1();
		Location pos2 = factionPlayer.getPreClaimPos2();

		int factionBalance = faction.getBalance();
		int claimCost = ClaimListener.getClaimCost(pos1, pos2);
		if (claimCost > factionBalance) {
			player.sendMessage(String.format(
					"%sNot enough money to claim this land. It costs %s$%d%s. Your faction is missing %s$%d%s.",
					Config.ERROR_COLOR, Config.SUCCESS_COLOR, claimCost, Config.ERROR_COLOR, Config.WARNING_COLOR,
					claimCost - factionBalance, Config.ERROR_COLOR));
			event.setCancelled(true);
			return;
		}

		faction.setBalance(factionBalance - claimCost);

		faction.broadcastMessage(String.format("%sThe faction claimed land.", Config.SUCCESS_COLOR));
		faction.setClaim(new Claim(pos1, pos2));
	}
}
