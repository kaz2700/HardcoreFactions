package me.kazuto.hcf.Kits;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;

import java.util.ArrayList;
import java.util.List;

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
        if(kit == null)
            return false;
        return (kit.getClass().equals(this.getClass()));
    }
}
