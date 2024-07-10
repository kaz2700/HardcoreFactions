/* (Copyright) 2024 github.com/kaz2700 */
package me.kazuto.hcf.Factions.Types;

import me.kazuto.hcf.Config;
import me.kazuto.hcf.Factions.Claim.Claim;
import me.kazuto.hcf.Factions.Faction;
import org.bukkit.Location;

public class Warzone extends Faction {
	public Warzone() {
		super("Warzone",
				new Claim(new Location(Config.WORLD_OVERWORLD, Config.WARZONE_RADIUS, 0, Config.WARZONE_RADIUS),
						new Location(Config.WORLD_OVERWORLD, -Config.WARZONE_RADIUS, 0, -Config.WARZONE_RADIUS)),
				1);
	}
}
