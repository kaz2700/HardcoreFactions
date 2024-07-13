/* (Copyright) 2024 github.com/kaz2700 */
package me.kazuto.hcf.Factions;

import lombok.Getter;
import lombok.Setter;
import me.kazuto.hcf.Factions.Claim.Claim;

public class Faction {
	@Getter
	@Setter
	private String name;
	@Getter
	@Setter
	private Claim claim;
	@Getter
	int claimPriority;

	public Faction(String name, int claimPriority) {
		this.name = name;
		this.claimPriority = claimPriority;
	}

	public Faction(String name, Claim claim, int claimPriority) {
		this.name = name;
		this.claim = claim;
		this.claimPriority = claimPriority;
	}

	public String getInfo() {
		return String.format("Faction: %s", getName());
	}
}
