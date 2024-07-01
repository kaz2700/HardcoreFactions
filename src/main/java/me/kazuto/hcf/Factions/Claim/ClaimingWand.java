package me.kazuto.hcf.Factions.Claim;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.material.MaterialData;

import java.util.Arrays;

public class ClaimingWand extends ItemStack {
    public static ItemStack getClaimingWand() {
        ItemStack item = new ItemStack(Material.WOODEN_HOE, 1);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(ChatColor.LIGHT_PURPLE + "Claiming Wand");
        meta.setLore(Arrays.asList(ChatColor.GREEN + "Claiming wand for claiming stuff.", ChatColor.GRAY + "Dont abuse its power."));
        item.setItemMeta(meta);
        return item;
    }
}
