/* (Copyright) 2024 github.com/kaz2700 */
package me.kazuto.hcf.Database;

import lombok.Getter;
import me.kazuto.hcf.Config;
import me.kazuto.hcf.Factions.Player.FactionPlayerManager;
import org.bukkit.Bukkit;

import java.sql.Connection;
import java.sql.DriverManager;

@Getter
public class DataBase {
	private final Connection connection;
	public DataBase() {
		try {
			Class.forName("org.postgresql.Driver");
			connection = DriverManager.getConnection(
					"jdbc:postgresql://aws-0-eu-west-2.pooler.supabase.com:6543/postgres?user=postgres.dtlnbnlqnuoyfiouhwwi&password=VWY8HkpoM5I7o0qx");
		} catch (Exception e) {
			Bukkit.getConsoleSender()
					.sendMessage(String.format("%sCould not connect to the database!", Config.ERROR_COLOR));
			throw new RuntimeException(e);
		}
	}

	private static DataBase instance = null;

	public static DataBase getInstance() {
		if (instance == null)
			instance = new DataBase();
		return instance;
	}
}