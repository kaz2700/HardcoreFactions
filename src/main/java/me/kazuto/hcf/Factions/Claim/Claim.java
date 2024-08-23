/* (Copyright) 2024 github.com/kaz2700 */
package me.kazuto.hcf.Factions.Claim;

import java.util.ArrayList;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Location;
import org.bukkit.World;

@Setter
@Getter
public class Claim {
	Location pos1;
	Location pos2;
	Location home;

	public Claim(Location pos1, Location pos2) {
		this.pos1 = pos1;
		this.pos2 = pos2;
	}

	public Claim(Location pos1, Location pos2, Location home) {
		this.pos1 = pos1;
		this.pos2 = pos2;
		this.home = home;
	}

	public ArrayList<Location> getCorners() {
		Location pos1 = getPos1();
		Location pos2 = getPos2();

		World world = pos1.getWorld();

		Location pos3 = new Location(world, pos1.getBlockX(), 0, pos2.getBlockZ());
		Location pos4 = new Location(world, pos2.getBlockX(), 0, pos1.getBlockZ());

		ArrayList<Location> claimCorners = new ArrayList<>();

		claimCorners.add(pos1);
		claimCorners.add(pos2);
		claimCorners.add(pos3);
		claimCorners.add(pos4);

		return claimCorners;
	}

	public String serialize() {
		String homeString = null;
		if (getHome() != null)
			homeString = getHome().serialize().toString();

		return String.format("""
				    {
				        "pos1": "%s",
				        "pos2": "%s",
				        "home": "%s"
				    }
				""", getPos1().serialize(), getPos2().serialize(), homeString);
	}

	public static Claim deserialize(String serializedJson) {
		Map<String, String> mapClaim = new Gson().fromJson(serializedJson, new TypeToken<Map<String, String>>() {
		}.getType());

		String pos1Json = mapClaim.get("pos1");
		Location pos1 = Location.deserialize(new Gson().fromJson(pos1Json, new TypeToken<Map<String, String>>() {
		}.getType()));

		String pos2Json = mapClaim.get("pos2");
		Location pos2 = Location.deserialize(new Gson().fromJson(pos2Json, new TypeToken<Map<String, String>>() {
		}.getType()));

		String homeJson = mapClaim.get("home");
		Location home = Location.deserialize(new Gson().fromJson(homeJson, new TypeToken<Map<String, String>>() {
		}.getType()));

		return new Claim(pos1, pos2, home);
	}
}
