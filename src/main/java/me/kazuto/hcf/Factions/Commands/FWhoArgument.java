/* (Copyright) 2024 github.com/kaz2700 */
package me.kazuto.hcf.Factions.Commands;

import static java.util.Arrays.asList;

import java.util.ArrayList;
import me.kazuto.hcf.Config;
import me.kazuto.hcf.Factions.Faction;
import me.kazuto.hcf.Factions.FactionManager;
import me.kazuto.hcf.Factions.Player.FactionPlayer;
import me.kazuto.hcf.Factions.Player.FactionPlayerManager;
import me.kazuto.hcf.Factions.Types.PlayerFaction;
import me.kazuto.hcf.Factions.Utils.CommandArgument;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class FWhoArgument extends CommandArgument {
	public FWhoArgument() {
		super("who", "Show faction information.", "/f who <faction/player>",
				new ArrayList<>(asList("i", "info", "information", "show")));
	}

	@Override
	public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
		// imma do so the console can f who niggas thats how cool i am
		switch (strings.length) {
			case 1 : // /F who
				if (!(commandSender instanceof Player)) {
					commandSender.sendMessage(String.format("%s%sYou are the console so you dont have a faction",
							Config.ERROR_COLOR, Config.ERROR_PREFIX));
					return false;
				}

				Player player = (Player) commandSender;
				FactionPlayer factionPlayer = FactionPlayerManager.getInstance()
						.getPlayerFromUUID(player.getUniqueId());

				if (FactionManager.getInstance().getFactionFromPlayer(factionPlayer) == null) {
					player.sendMessage(
							String.format("%s%sYou are not in a faction.", Config.ERROR_COLOR, Config.ERROR_PREFIX));
					return false;
				}

				PlayerFaction playerFaction = FactionManager.getInstance().getFactionFromPlayer(factionPlayer);
				player.sendMessage(playerFaction.getInfo());
				break;

			case 2 :
				StringBuilder output = new StringBuilder();
				for (FactionPlayer factionPlayer1 : FactionPlayerManager.getInstance().getPlayers()) {
					if (factionPlayer1.getName().equalsIgnoreCase(strings[1]) && factionPlayer1.hasAFaction()) {
						output.append(FactionManager.getInstance().getFactionFromPlayer(factionPlayer1).getInfo());
					}
				}

				for (Faction faction : FactionManager.getInstance().getFactions()) {
					if (faction.getName().equalsIgnoreCase(strings[1])) {
						if (!output.isEmpty())
							output.append("\n");
						output.append(faction.getInfo());
					}
				}

				if (output.isEmpty()) {
					commandSender.sendMessage(
							String.format("%s%sNo faction found.", Config.ERROR_COLOR, Config.ERROR_PREFIX));
				}
				commandSender.sendMessage(output.toString());
				break;

			default :
				commandSender.sendMessage(String.format("%s%s/f who <name>.", Config.ERROR_COLOR, Config.ERROR_PREFIX));
				return false;
		}
		return true;
	}
}
