/* (Copyright) 2024 github.com/kaz2700 */
package me.kazuto.hcf.Factions.Claim;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.jetbrains.annotations.NotNull;

public class Claim implements ConfigurationSerializable {
	@Getter
	@Setter
	Location pos1;
	@Getter
	@Setter
	Location pos2;

	public Claim(Location pos1, Location pos2) {
		this.pos1 = pos1;
		this.pos2 = pos2;
	}

	public ArrayList<Location> getCorners() {
		Location pos1 = getPos1();
		Location pos2 = getPos2();

		World world = pos1.getWorld();

		Location pos3 = new Location(world, pos1.getBlockX(), 0, pos2.getBlockZ());
		Location pos4 = new Location(world, pos2.getBlockX(), 0, pos1.getBlockZ());

		ArrayList<Location> claimCorners = new ArrayList<Location>();

		claimCorners.add(pos1);
		claimCorners.add(pos2);
		claimCorners.add(pos3);
		claimCorners.add(pos4);

		return claimCorners;
	}

	@Override
	public @NotNull Map<String, Object> serialize() {
		Map<String, Object> serializedMap = new HashMap<>();
		serializedMap.put("pos1", getPos1());
		serializedMap.put("pos2", getPos2());
		return serializedMap;
	}

	public static Claim deserialize(Map<String, Object> serializedMap) {
		Location pos1 = (Location) serializedMap.get("pos1");
		Location pos2 = (Location) serializedMap.get("pos2");
		return new Claim(pos1, pos2);
	}
}
