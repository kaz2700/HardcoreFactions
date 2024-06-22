package me.kazuto.hcf.Kits.Types;

import me.kazuto.hcf.Kits.Kit;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Bard extends Kit {
    private static PotionEffect speed = new PotionEffect(PotionEffectType.SPEED, -1, 1);
    private static PotionEffect regen = new PotionEffect(PotionEffectType.REGENERATION, -1, 0);
    private static PotionEffect res = new PotionEffect(PotionEffectType.RESISTANCE, -1, 1);

    private static ItemStack helm = new ItemStack(Material.GOLDEN_HELMET);
    private static ItemStack chest = new ItemStack(Material.GOLDEN_CHESTPLATE);
    private static ItemStack legs = new ItemStack(Material.GOLDEN_LEGGINGS);
    private static ItemStack boots = new ItemStack(Material.GOLDEN_BOOTS);

    public Bard() {
        super("Bard", Arrays.asList(speed, regen, res), new ItemStack[]{boots, legs, chest, helm}); //add it in this order or bug, order matters stoopid
    }
}
