package me.kazuto.hcf.Faction;

import lombok.Getter;
import lombok.Setter;

public class Faction {
    @Getter
    @Setter
    private String name;
    //claim
    //isbreakable
    public Faction(String name) {
        this.name = name;
    }
}
