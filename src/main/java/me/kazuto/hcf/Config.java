package me.kazuto.hcf;

import org.bukkit.ChatColor;

public class Config {
    //prefixes and subfixes
    public static String ERROR_PREFIX = "Wrong usage. ";

    //strings
    public static String SERVER_NAME = "Hardcore Factions";

    //colors
    public static ChatColor PRIMARY_COLOR = ChatColor.GOLD;
    public static ChatColor SECONDARY_COLOR = ChatColor.AQUA;
    public static ChatColor BOLD = ChatColor.BOLD;
    public static ChatColor ERROR_COLOR = ChatColor.RED;
    public static ChatColor SUCCESS_COLOR = ChatColor.GREEN;
    public static ChatColor WARNING_COLOR = ChatColor.YELLOW;
    public static ChatColor BACKGROUND_COLOR = ChatColor.GRAY;

    //kits
    public static int BARD_EFFECT_DURATION_TICKS = 100;
    public static int BARD_EFFECT_CHECK_HAND_TICKS = 10;
    public static int BARD_EFFECT_RADIUS = 5;

    //distances
    public static int SPAWN_RADIUS = 5;
    public static int MAP_RADIUS = 50;


    //colored strings
    public static String SERVER_DISPLAY_NAME = String.format("%s%s%s", Config.PRIMARY_COLOR, Config.BOLD, Config.SERVER_NAME);
    public static final String SEPARATOR = "" + Config.BACKGROUND_COLOR + ChatColor.BOLD + ChatColor.STRIKETHROUGH + "                                ";

}
