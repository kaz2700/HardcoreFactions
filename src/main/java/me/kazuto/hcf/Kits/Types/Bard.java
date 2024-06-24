package me.kazuto.hcf.Kits.Types;

import me.kazuto.hcf.Config;
import me.kazuto.hcf.Kits.Kit;
import me.kazuto.hcf.Kits.KitManager;
import me.kazuto.hcf.Main;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.Arrays;
import java.util.HashMap;

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

        itemEffects.put(Material.IRON_INGOT, new PotionEffect(PotionEffectType.RESISTANCE, Config.BARD_EFFECT_DURATION_TICKS, 0));
        itemEffects.put(Material.SUGAR, new PotionEffect(PotionEffectType.SPEED, Config.BARD_EFFECT_DURATION_TICKS, 1));
        itemEffects.put(Material.GHAST_TEAR, new PotionEffect(PotionEffectType.REGENERATION, Config.BARD_EFFECT_DURATION_TICKS, 0));
        itemEffects.put(Material.BLAZE_POWDER, new PotionEffect(PotionEffectType.STRENGTH, Config.BARD_EFFECT_DURATION_TICKS, 0));
        itemEffects.put(Material.FEATHER, new PotionEffect(PotionEffectType.JUMP_BOOST, Config.BARD_EFFECT_DURATION_TICKS, 1));
        itemEffects.put(Material.MAGMA_CREAM, new PotionEffect(PotionEffectType.FIRE_RESISTANCE, Config.BARD_EFFECT_DURATION_TICKS, 0));

        checkItemInHand();
    }

    @EventHandler
    public void rightClickItemEvent(PlayerInteractEvent event) {
        if(!event.getAction().isRightClick())
            return;
        if(!itemEffects.containsKey(event.getItem().getType()))
            return;
        Bukkit.broadcastMessage("STRENGH 2 BABY");
    }

    public void checkItemInHand() {
        Bukkit.getScheduler().scheduleSyncRepeatingTask(Main.getInstance(), () -> {
            HashMap<Player, Kit> playerKits = KitManager.getInstance().getPlayerKits();
            for(Player player : playerKits.keySet())
                if(playerKits.get(player) instanceof Bard) {
                    Material itemRightHand = player.getInventory().getItemInMainHand().getType();
                    if(itemEffects.containsKey(itemRightHand))
                        player.addPotionEffect(itemEffects.get(itemRightHand));

                    Material itemLeftHand = player.getInventory().getItemInOffHand().getType();
                    if(itemEffects.containsKey(itemLeftHand))
                        player.addPotionEffect(itemEffects.get(itemLeftHand));
                }
        }, 0, Config.BARD_EFFECT_CHECK_HAND_TICKS);
    }

    private static Bard instance = null;
    public static Bard getInstance() {
        if(instance == null)
            instance = new Bard();
        return instance;
    }
}