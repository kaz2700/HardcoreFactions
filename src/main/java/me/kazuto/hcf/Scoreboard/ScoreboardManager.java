package me.kazuto.hcf.Scoreboard;

import java.util.ArrayList;
import java.util.HashMap;
import me.kazuto.hcf.Config;
import me.kazuto.hcf.Factions.FactionManager;
import me.kazuto.hcf.Factions.Player.FactionPlayer;
import me.kazuto.hcf.Factions.Player.FactionPlayerManager;
import me.kazuto.hcf.Kits.Kit;
import me.kazuto.hcf.Kits.KitManager;
import me.kazuto.hcf.Main;
import me.kazuto.hcf.Scoreboard.Implementation.FastBoard;
import me.kazuto.hcf.Timers.Timer;
import me.kazuto.hcf.Timers.TimerManager;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class ScoreboardManager implements Listener {

  private HashMap<Player, FastBoard> playerScoreboards = new HashMap<>();
  private ArrayList<String> scoreboardString = new ArrayList<>();

  private ScoreboardManager() {
    Bukkit.getScheduler()
        .scheduleSyncRepeatingTask(Main.getInstance(), this::updateScoreboard, 0, 2);
  }

  public void updateScoreboard() {
    for (Player player : Bukkit.getOnlinePlayers()) {
      FastBoard fastBoard = playerScoreboards.get(player);
      assert (fastBoard != null);

      scoreboardString.clear();

      Kit kit = KitManager.getInstance().getKitFromPlayer(player);
      if (kit != null)
        scoreboardString.add(
            String.format(
                "%sKit: %s%s", Config.PRIMARY_COLOR, Config.SECONDARY_COLOR, kit.getName()));

      FactionPlayer factionPlayer =
          FactionPlayerManager.getInstance()
              .getPlayerFromUUID(player.getUniqueId()); // maybe bug cuz factionplayer dont exist?
      if (factionPlayer.hasAFaction())
        scoreboardString.add(
            String.format(
                "%sFaction: %s%s",
                Config.PRIMARY_COLOR,
                Config.SECONDARY_COLOR,
                FactionManager.getInstance().getFactionFromPlayer(factionPlayer).getName()));

      Timer pvpTimer = factionPlayer.getPvpTimer();
      if (TimerManager.getInstance().isActive(pvpTimer))
        scoreboardString.add(
            String.format(
                "%sCombat Timer: %s%.1fs",
                Config.PRIMARY_COLOR,
                Config.SECONDARY_COLOR,
                TimerManager.getInstance().getTimers().get(pvpTimer)));

      Timer classWarmUpTimer = factionPlayer.getClassWarmUp();
      if (TimerManager.getInstance().isActive(classWarmUpTimer))
        scoreboardString.add(
            String.format(
                "%sClass Warmup: %s%.1fs",
                Config.PRIMARY_COLOR,
                Config.SECONDARY_COLOR,
                TimerManager.getInstance().getTimers().get(classWarmUpTimer)));

      if (scoreboardString.isEmpty()) {
        fastBoard.updateTitle("");
      } else {
        fastBoard.updateTitle(
            String.format("%s", Config.SERVER_DISPLAY_NAME)); // remove darker line
        scoreboardString.addFirst(Config.SEPARATOR);
        scoreboardString.add(Config.SEPARATOR);
      }
      fastBoard.updateLines(scoreboardString);
    }
  }

  @EventHandler
  public void onJoin(PlayerJoinEvent event) {
    Player player = event.getPlayer();
    FastBoard fastBoard = new FastBoard(player);
    playerScoreboards.put(player, fastBoard);
  }

  @EventHandler
  public void onLeave(PlayerQuitEvent event) {
    Player player = event.getPlayer();
    assert (playerScoreboards.get(player) != null);
    playerScoreboards.remove(player);
  }

  private static ScoreboardManager instance = null;

  public static ScoreboardManager getInstance() {
    if (instance == null) instance = new ScoreboardManager();
    return instance;
  }
}
