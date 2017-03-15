package essentials.future.code.ConfigManager;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import essentials.future.code.main.main;

public class PlayerManager {

	private static main plugin;
	private static File file;
	private static FileConfiguration config;

	@SuppressWarnings("static-access")
	public PlayerManager(main plugin) {
		this.plugin = plugin;
		this.file = new File(this.plugin.getDataFolder(), "player.yml");
		this.config = YamlConfiguration.loadConfiguration(this.file);
		load();
	}

	public static void load() {
		if (!file.exists()) {
			save();
		} else {
			try {
				config.load(file);
			} catch (IOException | InvalidConfigurationException e) {
				e.printStackTrace();
			}
		}
	}

	public static void save() {
		try {
			config.save(file);
		} catch (IOException localIOException) {
		}
	}

	public static Boolean playerExist(String uuid) {
		if (config.contains(uuid + ".HomeList")) {
			return true;
		}
		return false;
	}

	public static Boolean createPlayerConfiguration(Player player) {
		if (!playerExist(player.getUniqueId().toString())) {
			List<String> homeList = new ArrayList<String>();
			config.set(player.getUniqueId().toString() + ".Name", player.getName());
			config.set(player.getUniqueId().toString() + ".HomeList", homeList);
			config.set(player.getUniqueId().toString() + ".God", false);
			save();
			return true;
		}
		return false;
	}

	public static Boolean removeHome(Player player , String name) {
		if(name == null) {
			name = "home";
		}
		String path = player.getUniqueId().toString() + ".Home." + name;
		config.set(path + ".World", null);
		config.set(path + ".X", null);
		config.set(path + ".Y", null);
		config.set(path + ".Z", null);
		config.set(path + ".Yaw", null);
		config.set(path + ".Pitch", null);
		removeEntryFromHomeList(player, name);
		save();
		return true;
	}
	
	public static Boolean removeEntryFromHomeList(Player player, String name) {
		if (config.contains(player.getUniqueId().toString() + ".HomeList")) {
			List<String> homeList = config.getStringList(player.getUniqueId().toString() + ".HomeList");
			if (homeList.contains(name)) {
				homeList.remove(name);
				config.set(player.getUniqueId().toString() + ".HomeList", homeList);
				save();
				return true;
			}
		}
		return false;
	}
	
	public static Boolean addEntryToHomeList(Player player, String name) {
		if (config.contains(player.getUniqueId().toString() + ".HomeList")) {
			List<String> homeList = config.getStringList(player.getUniqueId().toString() + ".HomeList");
			if (!homeList.contains(name)) {
				homeList.add(name);
				config.set(player.getUniqueId().toString() + ".HomeList", homeList);
				save();
				return true;
			}
		}
		return false;
	}

	public static Boolean setHome(Player player, String name, Location loc) {
		if(name == null) {
			name = "home";
		}
		String path = player.getUniqueId().toString() + ".Home." + name;
		config.set(path + ".World", loc.getWorld().getName());
		config.set(path + ".X", loc.getX());
		config.set(path + ".Y", loc.getY());
		config.set(path + ".Z", loc.getZ());
		config.set(path + ".Yaw", loc.getYaw());
		config.set(path + ".Pitch", loc.getPitch());
		addEntryToHomeList(player, name);
		save();
		return true;
	}

	public static Location getHome(Player player, String name) {
		if (homeExist(player, name)) {
			String path = player.getUniqueId().toString() + ".Home." + name;
			Location loc = new Location(Bukkit.getWorld(config.getString(path + ".World")),
					config.getDouble(path + ".X"), config.getDouble(path + ".Y"), config.getDouble(path + ".Z"));
			loc.setYaw(config.getInt(path + ".Yaw"));
			loc.setPitch(config.getInt(path + ".Pitch"));
			return loc;
		}
		return null;
	}

	public static List<String> getHomeList(Player player) {
		List<String> homeList = config.getStringList(player.getUniqueId().toString() + ".HomeList");
		return homeList;
	}

	public static Boolean homeExist(Player player, String name) {
		if (config.contains(player.getUniqueId().toString() + ".Home." + name + ".World")) {
			return true;
		}
		return false;
	}

	public static void setGod(Player player, Boolean state) {
		config.set(player.getUniqueId().toString() + ".God", state);
	}

	public static Boolean isGod(Player player) {
		return config.getBoolean(player.getUniqueId().toString() + ".God");
	}

}
