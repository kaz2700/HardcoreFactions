package me.kazuto.hcf.Kits.Types;

import me.kazuto.hcf.Kits.Kit;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.Arrays;
import java.util.HashMap;

public class Kamikaze extends Kit implements Listener {
    private static PotionEffect jump = new PotionEffect(PotionEffectType.REGENERATION, -1, 1);
    private static PotionEffect weak = new PotionEffect(PotionEffectType.WEAKNESS, -1, 1);
    private static PotionEffect glow = new PotionEffect(PotionEffectType.GLOWING, -1, 1);
    private static Material helm = null;
    private static Material chest = Material.ELYTRA;
    private static Material legs = null;
    private static Material boots = null;
    private HashMap<Player, Double> kamikazeSpeed = new HashMap<>();

    private Kamikaze() {
        super("Kamikaze", Arrays.asList(glow, jump, weak), new Material[]{boots, legs, chest, helm});
    }

    @EventHandler
    public void playerDeath (PlayerDeathEvent event) {
        Player player = event.getPlayer();
        if(!isActive(player))
            return;
        Bukkit.broadcastMessage("active" + kamikazeSpeed.get(player));
        if((kamikazeSpeed.get(player) < 40))
            return;
        Bukkit.broadcastMessage("Damage: Center: " + (kamikazeSpeed.get(player)/3) + "Side: " + ((kamikazeSpeed.get(player)/3) - 5*2));
    }


    @EventHandler
    public void playerMove(PlayerMoveEvent event) {
        Player player = event.getPlayer();
        if(!isActive(player))
            return;
        kamikazeSpeed.put(player, event.getFrom().distance(event.getTo())*20); //ppl will always need to have open elytra for kamikaze to explode so doesnt matter that without elytra open the speed is weird on death
    }

    @EventHandler
    public void playerQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        kamikazeSpeed.remove(player);
    }



    private static Kamikaze instance = null;
    public static Kamikaze getInstance() {
        if(instance == null)
            instance = new Kamikaze();
        return instance;
    }
}
