/* (Copyright) 2024 github.com/kaz2700 */
package me.kazuto.hcf.Factions.Claim.Border;

import me.kazuto.hcf.Config;
import me.kazuto.hcf.Factions.Claim.Claim;
import me.kazuto.hcf.Factions.Faction;
import me.kazuto.hcf.Factions.FactionManager;
import me.kazuto.hcf.Factions.Player.FactionPlayer;
import me.kazuto.hcf.Factions.Player.FactionPlayerManager;
import me.kazuto.hcf.Main;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class ClaimBorderManager {
	Random random = new Random();

	FactionPlayerManager factionPlayerManager = FactionPlayerManager.getInstance();
	List<Material> pillarMaterials = Arrays.asList(Material.COAL_ORE, Material.DIAMOND_ORE, Material.COPPER_ORE);
	FactionManager factionManager = FactionManager.getInstance();

	public ClaimBorderManager() {
		reloadClaimPillarsForPlayers();
	}

	public void reloadClaimPillarsForPlayers() {
		Bukkit.getScheduler().scheduleSyncRepeatingTask(Main.getInstance(), () -> {
			for (Player player : Bukkit.getOnlinePlayers()) {
				FactionPlayer factionPlayer = factionPlayerManager.getPlayerFromUUID(player.getUniqueId());
				renderClaimingPillars(player, factionPlayer.isActiveFMap());
			}
		}, 0, Config.RELOAD_CLAIM_PILLARS_TICKS);
	}

	public void renderClaimingPillars(Player player, boolean isActiveFMap) {
		Material pillarBlock;
		for (Faction faction : factionManager.getFactions()) {
			Claim claim = faction.getClaim();
			if (claim == null)
				continue;
			for (Location claimCorner : claim.getCorners()) {
				// if (claimCorner.distance(player.getLocation()) <=
				// Config.MAX_RENDER_DISTANCE_BLOCKS)
				renderClaimingPillar(player, claimCorner, isActiveFMap);
			}
		}
	}

	private void renderClaimingPillar(Player player, Location location, boolean isActiveFMap) {
		Material currentBlockMaterial;
		for (int i = -64; i <= 320; i++) { // -64 min y lvl, 320 max y lvl in new versions
			currentBlockMaterial = Material.AIR;
			location.setY(i);
			if (location.getBlock().getType() == Material.AIR) {
				if (isActiveFMap) {
					if (i % 3 == 0) {
						// Get random material for the claiming pillars
						currentBlockMaterial = pillarMaterials.get(random.nextInt(pillarMaterials.size()));
					} else {
						currentBlockMaterial = Material.GLASS;
					}
				}
				player.sendBlockChange(location, currentBlockMaterial.createBlockData());
			}
		}
	}

	private static ClaimBorderManager instance = null;

	public static ClaimBorderManager getInstance() {
		if (instance == null)
			instance = new ClaimBorderManager();
		return instance;
	}
}
