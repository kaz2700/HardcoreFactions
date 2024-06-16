package me.kazuto.hcf.Player;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.entity.Player;

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
}
