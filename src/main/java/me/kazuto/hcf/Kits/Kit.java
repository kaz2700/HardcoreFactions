package me.kazuto.hcf.Kits;

import lombok.Getter;
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
    private ItemStack[] armor;

    @Getter
    private ArrayList<Player> players;
    public Kit(String name, List<PotionEffect> effects, ItemStack[] armor) {
        this.name = name;
        this.effects = effects;
        this.armor = armor;
    }

    public void addPlayers(Player player) {
        players.add(player);
    }
}
