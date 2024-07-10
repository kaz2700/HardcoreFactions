/* (Copyright) 2024 github.com/kaz2700 */
package me.kazuto.hcf.Kits;

import java.util.List;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;

public class Kit {
	@Getter
	private String name;

	@Getter
	@Setter
	private double warmup;

	@Getter
	private List<PotionEffect> effects;

	@Getter
	private Material[] armor;

	public Kit(String name, List<PotionEffect> effects, Material[] armor) {
		this.name = name;
		this.effects = effects;
		this.armor = armor;
	}

	public boolean isActive(Player player) {
		Kit kit = KitManager.getInstance().getKitFromPlayer(player);
		if (kit == null)
			return false;
		return (kit.getClass().equals(this.getClass()));
	}
}
