/* (Copyright) 2024 github.com/kaz2700 */
package me.kazuto.hcf.Kits;

import lombok.Getter;
import org.bukkit.Material;
import org.bukkit.potion.PotionEffect;

public class KitEffect {
	@Getter
	Material item;
	@Getter
	PotionEffect holdEffect;
	@Getter
	PotionEffect clickEffect;

	public KitEffect(Material item, PotionEffect holdEffect, PotionEffect clickEffect) {
		this.item = item;
		this.holdEffect = holdEffect;
		this.clickEffect = clickEffect;
	}
	// we might need more constructors for more kits
}
