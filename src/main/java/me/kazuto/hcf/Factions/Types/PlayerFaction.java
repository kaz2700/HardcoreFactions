package me.kazuto.hcf.Factions.Types;

import lombok.Getter;
import lombok.Setter;
import me.kazuto.hcf.Factions.Faction;
import me.kazuto.hcf.Factions.Player.FactionPlayer;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.ChatColor;

import java.util.ArrayList;
import java.util.List;

public class PlayerFaction extends Faction {
    @Getter
    ArrayList<FactionPlayer> players = new ArrayList<>();

    @Getter
    ArrayList<FactionPlayer> invitedPlayers = new ArrayList<>();

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

    @Getter
    @Setter
    boolean isFOpen;

    public PlayerFaction(String name, FactionPlayer leader) {
        super(name, 1);
        this.leader = leader;
        addPlayer(leader);
    }
    public void invitePlayer(FactionPlayer factionPlayer) {
        assert(!invitedPlayers.contains(factionPlayer));
        invitedPlayers.add(factionPlayer);
    }

    public void uninvitePlayer(FactionPlayer factionPlayer) {
        assert(invitedPlayers.contains(factionPlayer));
        invitedPlayers.remove(factionPlayer);
    }

    public void addPlayer(FactionPlayer factionPlayer) {
        assert(!players.contains(factionPlayer));
        players.add(factionPlayer);
    }

    public void removePlayer(FactionPlayer factionPlayer) {
        assert(players.contains(factionPlayer));
        players.remove(factionPlayer);
    }

    @Override
    public String getInfo() {
        StringBuilder listOfPlayerNames = new StringBuilder();
        for(FactionPlayer factionPlayer : getPlayers()) {
            ChatColor color = ChatColor.GRAY;
            if(factionPlayer.getOfflinePlayer().isOnline()) {
                color = ChatColor.GREEN;
            }
            listOfPlayerNames.append(color).append(" ").append(factionPlayer.getName());
        }
        return String.format("Faction: %s\nPlayers:%s", getName(), listOfPlayerNames);
    }

    public List<FactionPlayer> getOnlinePlayers() {
        return getPlayers().stream().filter(FactionPlayer::isOnline).toList();
    }

    public boolean isOnline() {
        return !getOnlinePlayers().isEmpty();
    }

    public void broadcastMessage(String message) {
        for(FactionPlayer factionPlayer : getOnlinePlayers())
            factionPlayer.getOfflinePlayer().getPlayer().sendMessage(message);
    }
}
