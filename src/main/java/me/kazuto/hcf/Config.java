/* (Copyright) 2024 github.com/kaz2700 */
package me.kazuto.hcf;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.World;

public class Config {

	public static boolean HIT_TEAMMATES = false;

	public static int INITIAL_BALANCE = 100;

	// prefixes and subfixes
	public static String ERROR_PREFIX = "Wrong usage. ";

	// strings
	public static String SERVER_NAME = "Hardcore Factions";

	// colors
	public static ChatColor PRIMARY_COLOR = ChatColor.GOLD;
	public static ChatColor SECONDARY_COLOR = ChatColor.AQUA;
	public static ChatColor BOLD = ChatColor.BOLD;
	public static ChatColor ERROR_COLOR = ChatColor.RED;
	public static ChatColor SUCCESS_COLOR = ChatColor.GREEN;
	public static ChatColor WARNING_COLOR = ChatColor.YELLOW;
	public static ChatColor BACKGROUND_COLOR = ChatColor.GRAY;
	public static ChatColor INFO_COLOR = ChatColor.BLUE;
	public static ChatColor ACTIVATE_COLOR = ChatColor.DARK_GREEN;
	public static ChatColor DEACTIVATE_COLOR = ChatColor.BLACK;
	public static ChatColor NOTIFICATION_COLOR = ChatColor.DARK_PURPLE;

	// kits
	public static int KAMIKAZE_DAMAGE_RADIUS = 10;
	public static int KAMIKAZE_DAMAGE_CENTER = 10;
	public static int KAMIKAZE_REQUIRED_SPEED = 20;
	public static boolean KAMIKAZE_DAMAGE_TEAMMATES = true;
	public static double KIT_WARMUP_SECONDS = 1;
	public static int BARD_EFFECT_DURATION_TICKS = 100;
	public static int BARD_EFFECT_CHECK_HAND_TICKS = 10;
	public static int BARD_EFFECT_RADIUS = 5;

	// distances
	public static int SPAWN_RADIUS = 50;
	public static int MAP_RADIUS = 3000;
	public static int ROAD_WIDTH = 4;
	public static int WARZONE_RADIUS = 300;

	// colored strings
	public static String SERVER_DISPLAY_NAME = String.format("%s%s%s", Config.PRIMARY_COLOR, Config.BOLD,
			Config.SERVER_NAME);
	public static final String SEPARATOR = "" + Config.BACKGROUND_COLOR + ChatColor.BOLD + ChatColor.STRIKETHROUGH
			+ "                                ";

	public static final World WORLD_OVERWORLD = Bukkit.getWorlds().get(0);

	public static final int RELOAD_CLAIM_PILLARS_TICKS = 60;

	public static final int SAVE_DATA_TICKS = 600;
}
