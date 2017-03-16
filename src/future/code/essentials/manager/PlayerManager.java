package future.code.essentials.manager;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import future.code.essentials.main.main;

public class PlayerManager {

	private static File file;
	private main plugin;
	private static FileConfiguration config;

	public PlayerManager(main plugin) {
		this.plugin = plugin;
		file = new File(this.plugin.getDataFolder(), "players.yml");
		config = YamlConfiguration.loadConfiguration(file);
		load();
	}

	public void load() {
		if (file.exists()) {
			try {
				config.load(file);
			} catch (IOException | InvalidConfigurationException e) {
				System.out.println("Essentials: Config-Error");
			}
		} else {
			config.options().copyDefaults(true);

			save();
		}
	}

	public static void save() {
		try {
			config.save(file);
		} catch (IOException e) {
			System.out.println("Essentials: Config-Error");
		}
	}

	public static Boolean playerExist(Player player) {
		if (config.contains(player.getUniqueId().toString() + ".Money")) {
			return true;
		}
		return false;
	}

	public static void createPlayerConfiguration(Player player, Double defaultMoney) {
		if (!playerExist(player)) {
			String uuid = player.getUniqueId().toString();
			config.set(uuid + ".Money", defaultMoney);
			config.set(uuid + ".God", false);
			List<String> homeList = new ArrayList<String>();
			config.set(uuid + ".Homes", homeList);
			config.set(uuid + ".NickName", player.getName());
			config.set(uuid + ".Mute", false);
			config.set(uuid + ".MuteReason", "null");
			config.set(uuid + ".MuteTime", 0);
			config.set(uuid + ".Baned", false);
			config.set(uuid + ".banReason", "null");
			config.set(uuid + ".BanTime", 0);
			save();
			System.out.println(ApiManager.encryptPlayerFormat(LanguageManager.ConsoleCreatePlayer(), player));
		}
	}

	public static Double getMoney(Player player) {
		if(playerExist(player)) {
			String uuid = player.getUniqueId().toString();
			return config.getDouble(uuid + ".Money");
		}
		return 0.0;
	}

}
