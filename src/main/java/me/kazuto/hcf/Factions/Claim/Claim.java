package me.kazuto.hcf.Factions.Claim;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.Location;

public class Claim {
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
}
