package me.kazuto.hcf.Factions.Player;

import lombok.Getter;
import lombok.Setter;
import me.kazuto.hcf.Factions.FactionManager;
import me.kazuto.hcf.Factions.Types.PlayerFaction;
import org.bukkit.Bukkit;
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

    public FactionPlayer(UUID uuid) {
        this.uuid = uuid;
        this.setBalance(100);
    }

    public OfflinePlayer offlinePlayer() {
        return Bukkit.getOfflinePlayer(getUuid());
    }
    public String getName() {
        return offlinePlayer().getName();
    }

    public PlayerFaction getFaction() {
        return FactionManager.getInstance().getFactionFromPlayer(this);
    }

    public boolean hasAFaction() {
        return getFaction() != null;
    }
}
