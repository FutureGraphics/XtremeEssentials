package future.code.essentials.manager;

import java.io.File;
import java.io.IOException;

import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import future.code.essentials.main.main;

public class LanguageManager {

	private File file;
	private main plugin;
	private static FileConfiguration config;
	public LanguageManager(main plugin) {
		this.plugin = plugin;
		file = new File(this.plugin.getDataFolder(), "language.yml");
		config = YamlConfiguration.loadConfiguration(file);
		load();
	}
	
	public void load() {
		if(file.exists()) {
			try {
				config.load(file);
			} catch (IOException | InvalidConfigurationException e) {
				System.out.println("Essentials: Config-Error");
			}
		} else {
			config.options().copyDefaults(true);
			config.addDefault("NoAccess", "%prefix% &cYou have no access");
			config.addDefault("ReloadMessage", "%prefix% &eReload complete");
			config.addDefault("ConsoleCreatePlayer", "New player entry createt %player% with the uuid %uuid%");
			config.addDefault("PlayerNotFound", "%prefix% &cPlayer not found");
			config.addDefault("WrongFormat", "%prefix% &cWrong format");
			config.addDefault("GamemodeChanged", "%prefix% &eYour gamode has been change to %gamemode%");
			config.addDefault("OtherGamemodeChange", "%prefix% &eYou have change the gamemode from %target% to %targetgamemode%");
			config.addDefault("WrongPlayer", "%prefix% &eYou can't type in your self");
			save();
		}
	}
	
	public void save() {
		try {
			config.save(file);
		} catch (IOException e) {
			System.out.println("Essentials: Config-Error");
		}
	}
	
	public static String WrongPlayer() {
		return config.getString("WrongPlayer");
	}
	
	public static String OtherGamemodeChange() {
		return config.getString("OtherGamemodeChange");
	}
	
	public static String GamemodeChanged() {
		return config.getString("GamemodeChanged");
	}
	
	public static String WrongFormat() {
		return config.getString("WrongFormat");
	}
	
	public static String PlayerNotFound() {
		return config.getString("PlayerNotFound");
	}
	
	public static String ConsoleCreatePlayer() {
		return config.getString("ConsoleCreatePlayer");
	}
	
	public static String ReloadMessage() {
		return ApiManager.encryptFormat(config.getString("ReloadMessage"));
	}
	
	public static String noAccess() {
		return ApiManager.encryptFormat(config.getString("NoAccess"));
	}
	
}
