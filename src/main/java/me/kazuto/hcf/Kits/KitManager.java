/* (Copyright) 2024 github.com/kaz2700 */
package me.kazuto.hcf.Kits;

import java.util.HashMap;
import java.util.List;
import lombok.Getter;
import me.kazuto.hcf.Kits.Types.Bard;
import me.kazuto.hcf.Kits.Types.Kamikaze;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;

public class KitManager {
	@Getter
	private HashMap<Player, Kit> playerKits = new HashMap<>();

	@Getter
	private List<Kit> kits = List.of(Bard.getInstance(), Kamikaze.getInstance());

	public Kit getKitFromPlayer(Player player) {
		return playerKits.get(player);
	}

	public void addToPlayerKits(Player player, Kit kit) {
		assert (!playerKits.containsKey(player));
		assert (player.isOnline());
		playerKits.put(player, kit);
		addEffectsToPlayer(player, kit);
	}

	public void removeFromPlayerKits(Player player, Kit kit) {
		assert (playerKits.containsKey(player));
		playerKits.remove(player);
		removeEffectsFromPlayer(player, kit);
	}

	private void addEffectsToPlayer(Player player, Kit kit) {
		for (PotionEffect potionEffect : kit.getEffects())
			player.addPotionEffect(potionEffect);
	}

	private void removeEffectsFromPlayer(Player player, Kit kit) {
		for (PotionEffect potionEffect : kit.getEffects())
			player.removePotionEffect(potionEffect.getType());
	}

	public Kit getKitFromArmor(ItemStack[] armor) {
		for (Kit kit : getKits()) {
			boolean matches = true;
			for (int i = 0; i < armor.length; i++) {
				if (armor[i] == null) {
					if (kit.getArmor()[i] != null) {
						matches = false;
						break;
					}
				} else {
					if (armor[i].getType() != kit.getArmor()[i]) {
						matches = false;
						break;
					}
				}
			}
			if (matches)
				return kit;
		}
		return null;
	}

	private static KitManager instance = null;

	public static KitManager getInstance() {
		if (instance == null)
			instance = new KitManager();
		return instance;
	}
}
