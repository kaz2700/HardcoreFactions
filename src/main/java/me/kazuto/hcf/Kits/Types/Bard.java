package me.kazuto.hcf.Kits.Types;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import me.kazuto.hcf.Config;
import me.kazuto.hcf.Factions.Player.FactionPlayerManager;
import me.kazuto.hcf.Kits.Kit;
import me.kazuto.hcf.Kits.KitEffect;
import me.kazuto.hcf.Kits.KitManager;
import me.kazuto.hcf.Main;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class Bard extends Kit implements Listener {
  private static PotionEffect speed = new PotionEffect(PotionEffectType.SPEED, -1, 1);
  private static PotionEffect regen = new PotionEffect(PotionEffectType.REGENERATION, -1, 0);
  private static PotionEffect res = new PotionEffect(PotionEffectType.RESISTANCE, -1, 1);

  private static Material helm = Material.GOLDEN_HELMET;
  private static Material chest = Material.GOLDEN_CHESTPLATE;
  private static Material legs = Material.GOLDEN_LEGGINGS;
  private static Material boots = Material.GOLDEN_BOOTS;

  private ArrayList<KitEffect> kitEffects = new ArrayList<>();

  private Bard() {
    super(
        "Bard",
        Arrays.asList(speed, regen, res),
        new Material[] {
          boots, legs, chest, helm
        }); // add it in this order or bug, order matters stoopid

    kitEffects.add(
        new KitEffect(
            Material.IRON_INGOT,
            new PotionEffect(PotionEffectType.RESISTANCE, Config.BARD_EFFECT_DURATION_TICKS, 0),
            new PotionEffect(PotionEffectType.RESISTANCE, Config.BARD_EFFECT_DURATION_TICKS, 2)));
    kitEffects.add(
        new KitEffect(
            Material.SUGAR,
            new PotionEffect(PotionEffectType.SPEED, Config.BARD_EFFECT_DURATION_TICKS, 1),
            new PotionEffect(PotionEffectType.SPEED, Config.BARD_EFFECT_DURATION_TICKS, 2)));
    kitEffects.add(
        new KitEffect(
            Material.GHAST_TEAR,
            new PotionEffect(PotionEffectType.REGENERATION, Config.BARD_EFFECT_DURATION_TICKS, 0),
            new PotionEffect(PotionEffectType.REGENERATION, Config.BARD_EFFECT_DURATION_TICKS, 2)));
    kitEffects.add(
        new KitEffect(
            Material.BLAZE_POWDER,
            new PotionEffect(PotionEffectType.STRENGTH, Config.BARD_EFFECT_DURATION_TICKS, 0),
            new PotionEffect(PotionEffectType.STRENGTH, Config.BARD_EFFECT_DURATION_TICKS, 1)));
    kitEffects.add(
        new KitEffect(
            Material.FEATHER,
            new PotionEffect(PotionEffectType.JUMP_BOOST, Config.BARD_EFFECT_DURATION_TICKS, 1),
            new PotionEffect(PotionEffectType.JUMP_BOOST, Config.BARD_EFFECT_DURATION_TICKS, 6)));
    kitEffects.add(
        new KitEffect(
            Material.MAGMA_CREAM,
            new PotionEffect(
                PotionEffectType.FIRE_RESISTANCE, Config.BARD_EFFECT_DURATION_TICKS, 0),
            new PotionEffect(PotionEffectType.RESISTANCE, Config.BARD_EFFECT_DURATION_TICKS, 0)));

    checkItemInHand();
  }

  @EventHandler
  public void rightClickItemEvent(PlayerInteractEvent event) {
    if (!this.isActive(event.getPlayer())) return;
    if (!event.getAction().isRightClick()) return;
    for (KitEffect kitEffect : kitEffects) {
      if (event.getItem() != null && kitEffect.getItem() == event.getItem().getType())
        Bukkit.broadcastMessage("STRENGH 2 BABY" + event.getItem().getType().toString());
    }
  }

  public void checkItemInHand() {
    Bukkit.getScheduler()
        .scheduleSyncRepeatingTask(
            Main.getInstance(),
            () -> {
              HashMap<Player, Kit> playerKits = KitManager.getInstance().getPlayerKits();
              for (Player player : playerKits.keySet()) {
                if (this.isActive(player)) {
                  Material itemRightHand = player.getInventory().getItemInMainHand().getType();
                  Material itemLeftHand = player.getInventory().getItemInOffHand().getType();
                  for (KitEffect kitEffect : kitEffects) {
                    if (kitEffect.getItem() == itemRightHand || kitEffect.getItem() == itemLeftHand)
                      FactionPlayerManager.getInstance()
                          .getNearByPlayers(player, Config.BARD_EFFECT_RADIUS, false)
                          .forEach(player1 -> player1.addPotionEffect(kitEffect.getHoldEffect()));
                  }
                }
              }
            },
            0,
            Config.BARD_EFFECT_CHECK_HAND_TICKS);
  }

  private static Bard instance = null;

  public static Bard getInstance() {
    if (instance == null) instance = new Bard();
    return instance;
  }
}
