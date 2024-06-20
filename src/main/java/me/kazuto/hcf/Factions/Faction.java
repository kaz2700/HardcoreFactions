package me.kazuto.hcf.Factions;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.Bukkit;

public class Faction {
    @Getter
    @Setter
    private String name;
    //claim
    //isbreakable
    public Faction(String name) {
        this.name = name;
    }

    public String getInfo() {
        return String.format("Faction: %s", getName());
    }
}
