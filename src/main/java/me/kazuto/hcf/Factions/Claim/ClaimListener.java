/* (Copyright) 2024 github.com/kaz2700 */
package me.kazuto.hcf.Factions.Claim;

import me.kazuto.hcf.Config;
import me.kazuto.hcf.Factions.Faction;
import me.kazuto.hcf.Factions.FactionEvents.Events.FactionClaimEvent;
import me.kazuto.hcf.Factions.FactionManager;
import me.kazuto.hcf.Factions.Player.FactionPlayer;
import me.kazuto.hcf.Factions.Player.FactionPlayerManager;
import me.kazuto.hcf.Factions.Types.PlayerFaction;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.EquipmentSlot;

public class ClaimListener implements Listener {

	@EventHandler
	public void playerMove(PlayerMoveEvent event) {
		Player player = event.getPlayer();
		Faction factionFrom = FactionManager.getInstance().getFactionFromLocation(event.getFrom());
		Faction factionTo = FactionManager.getInstance().getFactionFromLocation(event.getTo());

		if (factionTo == null) {
			player.sendMessage(String.format("%sYou can't get out of the map.", Config.ERROR_COLOR));
			event.setCancelled(true);
			return;
		}

		if (factionFrom != factionTo) {
			player.sendMessage(String.format("%sLeaving %s.", Config.ERROR_COLOR, factionFrom.getName()));
			player.sendMessage(String.format("%sEntering %s.", Config.SUCCESS_COLOR, factionTo.getName()));
		}
	}

	@EventHandler
	public void onClickEvent(PlayerInteractEvent event) {
		Player player = event.getPlayer();
		FactionPlayer factionPlayer = FactionPlayerManager.getInstance().getPlayerFromUUID(player.getUniqueId());

		if (event.getHand() == EquipmentSlot.OFF_HAND)
			return;

		if (!player.getInventory().getItemInMainHand().isSimilar(ClaimingWand.getClaimingWand()))
			return;

		event.setCancelled(true);

		if (!factionPlayer.hasAFaction()) {
			player.sendMessage(
					String.format("%s%sYou need a faction to claim!", Config.ERROR_COLOR, Config.ERROR_PREFIX));
			return;
		}

		PlayerFaction faction = FactionManager.getInstance().getFactionFromPlayer(factionPlayer);

		if (faction.getLeader() != factionPlayer) {
			player.sendMessage(String.format("%s%sYou need to be the leader of the faction!", Config.ERROR_COLOR,
					Config.ERROR_PREFIX));
			return;
		}

		if (faction.getClaim() != null) {
			player.sendMessage(
					String.format("%s%sThe faction already has a claim!", Config.ERROR_COLOR, Config.ERROR_PREFIX));
			return;
		}

		if (event.getAction() == Action.LEFT_CLICK_BLOCK) {
			Faction clickedBlockLocationFaction = FactionManager.getInstance()
					.getFactionFromLocation(event.getClickedBlock().getLocation());
			assert (clickedBlockLocationFaction != null);

			if (clickedBlockLocationFaction.getClaimPriority() >= faction.getClaimPriority()) {
				player.sendMessage(String.format("%s%sYou cannot claim inside the territoy of %s!", Config.ERROR_COLOR,
						Config.ERROR_PREFIX, clickedBlockLocationFaction.getName()));
				return;
			}

			player.sendMessage("Location1 set");
			factionPlayer.setPreClaimPos1(event.getClickedBlock().getLocation());

			return;
		}

		if (event.getAction() == Action.RIGHT_CLICK_BLOCK) {
			player.sendMessage("Location2 set");
			factionPlayer.setPreClaimPos2(event.getClickedBlock().getLocation());
			return;
		}

		if (event.getAction() == Action.RIGHT_CLICK_AIR) {
			player.sendMessage("Claim cleared");
			factionPlayer.setPreClaimPos1(null);
			factionPlayer.setPreClaimPos2(null);
			return;
		}

		if (event.getAction() == Action.LEFT_CLICK_AIR && player.isSneaking() && factionPlayer.getPreClaimPos1() != null
				&& factionPlayer.getPreClaimPos2() != null) {
			Bukkit.getServer().getPluginManager().callEvent(new FactionClaimEvent(player, faction));
			player.getInventory().remove(ClaimingWand.getClaimingWand());
		}
	}
}
