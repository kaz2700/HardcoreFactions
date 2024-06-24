package me.kazuto.hcf.Kits;

import lombok.Getter;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;

import java.util.ArrayList;
import java.util.List;

public class Kit {
    @Getter
    private String name;

    @Getter
    private List<PotionEffect> effects;

    @Getter
    private Material[] armor;

    @Getter
    private ArrayList<Player> players = new ArrayList<>();

    public Kit(String name, List<PotionEffect> effects, Material[] armor) {
        this.name = name;
        this.effects = effects;
        this.armor = armor;
    }

    public void addToPlayers(Player player) {
        assert(!players.contains(player));
        players.add(player);
    }

    public void removeToPlayers(Player player) {
        assert(players.contains(player));
        players.add(player);
    }
}
