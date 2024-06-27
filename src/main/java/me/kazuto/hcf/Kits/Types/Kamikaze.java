package me.kazuto.hcf.Kits.Types;

import me.kazuto.hcf.Kits.Kit;
import me.kazuto.hcf.Kits.KitManager;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.Arrays;

public class Kamikaze extends Kit implements Listener {
    private static PotionEffect jump = new PotionEffect(PotionEffectType.REGENERATION, -1, 1);
    private static PotionEffect weak = new PotionEffect(PotionEffectType.WEAKNESS, -1, 1);
    private static PotionEffect glow = new PotionEffect(PotionEffectType.GLOWING, -1, 1);
    private static Material helm = null;
    private static Material chest = Material.ELYTRA;
    private static Material legs = null;
    private static Material boots = null;

    public Kamikaze() {
        super("Kamikaze", Arrays.asList(glow, jump, weak), new Material[]{boots, legs, chest, helm});
    }



    private static Kamikaze instance = null;
    public static Kamikaze getInstance() {
        if(instance == null)
            instance = new Kamikaze();
        return instance;
    }

    @EventHandler
    public void playerDeath (PlayerDeathEvent event) {
        Player player = event.getPlayer();
        if(!(KitManager.getInstance().getKitFromPlayer(player) instanceof Kamikaze))
            return;

        Bukkit.broadcastMessage("" + player.getVelocity());
    }

    @EventHandler
    public void playerMove(PlayerMoveEvent event) {
        Bukkit.broadcastMessage("good speed: " + event.getFrom().distance(event.getTo())*20);
    }
}//todo hasmapi for player, shpeed n check on death to kaboom
