package me.kazuto.hcf.Kits.Types;

import me.kazuto.hcf.Config;
import me.kazuto.hcf.Kits.Kit;
import me.kazuto.hcf.Kits.KitManager;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemHeldEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class Bard extends Kit implements Listener {
    private static PotionEffect speed = new PotionEffect(PotionEffectType.SPEED, -1, 1);
    private static PotionEffect regen = new PotionEffect(PotionEffectType.REGENERATION, -1, 0);
    private static PotionEffect res = new PotionEffect(PotionEffectType.RESISTANCE, -1, 1);

    private static Material helm = Material.GOLDEN_HELMET;
    private static Material chest = Material.GOLDEN_CHESTPLATE;
    private static Material legs = Material.GOLDEN_LEGGINGS;
    private static Material boots = Material.GOLDEN_BOOTS;

    private HashMap<Material, PotionEffect> itemEffects = new HashMap<>();

    public Bard() {
        super("Bard", Arrays.asList(speed, regen, res), new Material[]{boots, legs, chest, helm}); //add it in this order or bug, order matters stoopid

        itemEffects.put(Material.IRON_INGOT, new PotionEffect(PotionEffectType.RESISTANCE, Config.BARD_EFFECT_DURATION, 0));
        itemEffects.put(Material.SUGAR, new PotionEffect(PotionEffectType.SPEED, Config.BARD_EFFECT_DURATION, 1));
        itemEffects.put(Material.GHAST_TEAR, new PotionEffect(PotionEffectType.REGENERATION, Config.BARD_EFFECT_DURATION, 0));
        itemEffects.put(Material.BLAZE_POWDER, new PotionEffect(PotionEffectType.STRENGTH, Config.BARD_EFFECT_DURATION, 0));
        itemEffects.put(Material.FEATHER, new PotionEffect(PotionEffectType.JUMP_BOOST, Config.BARD_EFFECT_DURATION, 1));
        itemEffects.put(Material.MAGMA_CREAM, new PotionEffect(PotionEffectType.FIRE_RESISTANCE, Config.BARD_EFFECT_DURATION, 0));
    }

    @EventHandler
    public void holdItemEvent(PlayerItemHeldEvent event) {
        Player player = event.getPlayer();
        if(!(KitManager.getInstance().getKitFromPlayer(player) instanceof Bard))
            return;

        Material item = player.getInventory().getItem(event.getNewSlot()).getType();
        if(itemEffects.containsKey(item)) {
            Bukkit.broadcastMessage(itemEffects.get(item).toString());
            player.addPotionEffect(itemEffects.get(item));

        }
    }
}