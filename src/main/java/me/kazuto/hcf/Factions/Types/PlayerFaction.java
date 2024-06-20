package me.kazuto.hcf.Factions.Types;

import lombok.Getter;
import lombok.Setter;
import me.kazuto.hcf.Factions.Faction;
import me.kazuto.hcf.Factions.Player.FactionPlayer;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;

import java.util.ArrayList;
import java.util.List;

public class PlayerFaction extends Faction {
    @Getter
    ArrayList<FactionPlayer> factionPlayers = new ArrayList<>();

    @Getter
    ArrayList<FactionPlayer> invitedFactionPlayers = new ArrayList<>();

    @Getter
    @Setter
    int balance;

    @Getter
    @Setter
    float dtr;

    @Getter
    @Setter
    String announcement;

    @Getter
    @Setter
    FactionPlayer leader;

    public PlayerFaction(String name, FactionPlayer leader) {
        super(name);
        this.leader = leader;
        addFactionPlayer(leader);
    }
    public void inviteFactionPlayer(FactionPlayer factionPlayer) {
        assert(!invitedFactionPlayers.contains(factionPlayer));
        invitedFactionPlayers.add(factionPlayer);
    }
    public void addFactionPlayer(FactionPlayer factionPlayer) {
        assert(!factionPlayers.contains(factionPlayer));
        factionPlayers.add(factionPlayer);
    }

    public void removeFactionPlayer(FactionPlayer factionPlayer) {
        assert(factionPlayers.contains(factionPlayer));
        factionPlayers.remove(factionPlayer);
    }

    @Override
    public String getInfo() {
        StringBuilder listOfPlayerNames = new StringBuilder();
        for(FactionPlayer factionPlayer : getFactionPlayers()) {
            ChatColor color = ChatColor.GRAY;
            if(factionPlayer.getOfflinePlayer().isOnline()) {
                color = ChatColor.GREEN;
            }
            listOfPlayerNames.append(color).append(" ").append(factionPlayer.getName());
        }
        return String.format("Faction: %s\nPlayers:%s", getName(), listOfPlayerNames);
    }

    public List<FactionPlayer> getOnlinePlayers() {
        return getFactionPlayers().stream().filter(FactionPlayer::isOnline).toList();
    }

    public boolean isOnline() {
        return !getOnlinePlayers().isEmpty();
    }

}
