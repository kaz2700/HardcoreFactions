/* (Copyright) 2024 github.com/kaz2700 */
package me.kazuto.hcf.Kits;

import com.destroystokyo.paper.event.player.PlayerArmorChangeEvent;
import me.kazuto.hcf.Config;
import me.kazuto.hcf.Factions.Player.FactionPlayer;
import me.kazuto.hcf.Factions.Player.FactionPlayerManager;
import me.kazuto.hcf.Kits.KitEvents.Events.KitActivateEvent;
import me.kazuto.hcf.Timers.Timer;
import me.kazuto.hcf.Timers.TimerManager;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class KitListener implements Listener {
	private final KitManager kitManager = KitManager.getInstance();
	private final FactionPlayerManager factionPlayerManager = FactionPlayerManager.getInstance();
	private final TimerManager timerManager = TimerManager.getInstance();

	@EventHandler
	public void onArmorChange(PlayerArmorChangeEvent event) {
		Player player = event.getPlayer();
		Kit currentKit = kitManager.getKitFromArmor(player.getInventory().getArmorContents()); // the kit that the
																								// player changed to
																								// wiht the
		// armorchangeevent
		Kit existingKit = kitManager.getKitFromPlayer(player);

		if (existingKit == currentKit) {
			return;
		}

		// If the new kit is null, remove the existing kit and notify the player
		if (currentKit == null) {
			kitManager.removeFromPlayerKits(player, existingKit);
			player.sendMessage(String.format("%sKit %s deactivated.", Config.WARNING_COLOR, existingKit.getName()));
			return;
		}

		FactionPlayer factionPlayer = factionPlayerManager.getPlayerFromUUID(player.getUniqueId());
		Timer classWarmUp = new Timer(Config.KIT_WARMUP_SECONDS, () -> {
			Bukkit.getServer().getPluginManager().callEvent(new KitActivateEvent(player, currentKit));
		});
		factionPlayer.setClassWarmUp(classWarmUp);
		timerManager.addTimer(classWarmUp);
	}

	@EventHandler
	public void onJoin(PlayerJoinEvent event) {
		Player player = event.getPlayer();

		Kit currentKit = kitManager.getKitFromArmor(player.getInventory().getArmorContents()); // the kit that the
																								// player changed to
																								// wiht the
		// armorchangeevent
		if (currentKit != null) {
			KitManager.getInstance().addToPlayerKits(player, currentKit);
			player.sendMessage(String.format("%sSwitched to %s kit.", Config.SUCCESS_COLOR, currentKit.getName()));
		}
	}

	@EventHandler
	public void onQuit(PlayerQuitEvent event) {
		Player player = event.getPlayer();

		Kit currentKit = kitManager.getKitFromArmor(player.getInventory().getArmorContents()); // the kit that the
																								// player changed to
																								// wiht the
		// armorchangeevent
		if (currentKit != null) {
			KitManager.getInstance().removeFromPlayerKits(player, currentKit);
			player.sendMessage(String.format("%sKit %s deactivated.", Config.WARNING_COLOR, currentKit.getName()));
		}
	}
}
