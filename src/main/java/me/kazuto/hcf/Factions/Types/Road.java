/* (Copyright) 2024 github.com/kaz2700 */
package me.kazuto.hcf.Factions.Types;

import me.kazuto.hcf.Factions.Claim.Claim;
import me.kazuto.hcf.Factions.Faction;

public class Road extends Faction {
	public Road(String name, Claim claim) {
		super(name, claim, 2);
	}
}
