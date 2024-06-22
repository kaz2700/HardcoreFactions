package me.kazuto.hcf.Kits;

import lombok.Getter;
import me.kazuto.hcf.Factions.Player.FactionPlayer;
import me.kazuto.hcf.Factions.Player.FactionPlayerManager;
import me.kazuto.hcf.Kits.Types.Bard;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class KitManager {
    @Getter
    HashMap<Player, Kit> playerKits = new HashMap<>();
    @Getter
    List<Kit> kits = Arrays.asList(new Bard());


    public void addToPlayerToKits(Player player, Kit kit) {
        assert(!playerKits.containsKey(player));
        assert(player.isOnline());
        playerKits.put(player, kit);
        addEffectsToPlayer(player, kit);
    }

    public void addEffectsToPlayer(Player player, Kit kit) {
        for(PotionEffect potionEffect : kit.getEffects())
            player.addPotionEffect(potionEffect);
    }




    private static KitManager instance;
    public static KitManager getInstance() {
        if(instance == null)
            return new KitManager();
        return instance;
    }
}
