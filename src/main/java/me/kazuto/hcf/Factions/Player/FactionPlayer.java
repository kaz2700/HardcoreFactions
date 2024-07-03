package me.kazuto.hcf.Factions.Player;

import lombok.Getter;
import lombok.Setter;
import me.kazuto.hcf.Factions.FactionManager;
import me.kazuto.hcf.Factions.Types.PlayerFaction;
import me.kazuto.hcf.Timers.Timer;
import me.kazuto.hcf.Timers.TimerManager;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.OfflinePlayer;
import java.util.UUID;

public class FactionPlayer {
    @Getter
    @Setter
    UUID uuid;

    @Getter
    @Setter
    int balance;

    @Getter
    @Setter
    int kills;

    @Getter
    @Setter
    int deaths;

    @Getter
    @Setter
    int playtime;

    @Getter
    @Setter
    Location preClaimPos1;

    @Getter
    @Setter
    Location preClaimPos2;

    @Getter
    Timer pvpTimer;

    @Getter
    @Setter
    Timer classWarmUp;

    @Getter
    Timer pearlCoolDown;

    public FactionPlayer(UUID uuid) {
        this.uuid = uuid;
        this.setBalance(100);
        preClaimPos1 = null;
        preClaimPos2 = null;
        this.pvpTimer = new Timer(30.0);
    }

    public OfflinePlayer getOfflinePlayer() {
        return Bukkit.getOfflinePlayer(getUuid());
    }
    public String getName() {
        return getOfflinePlayer().getName();
    }

    public PlayerFaction getFaction() {
        return FactionManager.getInstance().getFactionFromPlayer(this);
    }

    public boolean hasAFaction() {
        return getFaction() != null;
    }

    public boolean isOnline() {
        return getOfflinePlayer().isOnline();
    }
}
