package future.code.essentials.manager;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import future.code.essentials.main.main;

public class MainManager {

	private File file;
	private main plugin;
	private static FileConfiguration config;
	public MainManager(main plugin) {
		this.plugin = plugin;
		file = new File(this.plugin.getDataFolder(), "config.yml");
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
			
			//Server settings
			config.addDefault("Server.Prefix", "&8[&eEssentials&8]");
			config.addDefault("Server.Name", "test-server");
			config.addDefault("Server.MOTD", "&8welcome &7- &6%player%");
			config.addDefault("Server.JoinMessage", "&e%player% joined the server");
			config.addDefault("Server.LeaveMessage", "&e%player% has left the server");
			config.addDefault("Server.FirstPlayer", "&ePlayer %player% is new on %server%");
			config.addDefault("Server.DefaultKit", "none");
			
			//MySQL
			config.addDefault("MySQL.Host", "localhost");
			config.addDefault("MySQL.Database", "minecraft");
			config.addDefault("MySQL.TableName", "Essentials");
			config.addDefault("MySQL.Username", "user");
			config.addDefault("MySQL.Password", "pass");
			
			//Teleport settings
			config.addDefault("Teleport.TpaDelay", 3);
			config.addDefault("Teleport.HomeDelay", 3);
			config.addDefault("Teleport.WarpDelay", 3);
			config.addDefault("Teleport.SpawnDelay", 3);
			config.addDefault("Teleport.TpaRequest", 120);
			
			//Home settings
			config.addDefault("Home.default", 3);
			config.addDefault("Home.premium", 4);
			
			//Economy
			config.addDefault("Economy.Format", "$");
			config.addDefault("Economy.DefaultAmount", 0.0);
			
			//Kit
			List<String> kitItemList = new ArrayList<String>();
			kitItemList.add("1:5, amount:1, name:&8Stone, lore:&8I am;a Stone, sharpness:1, noEnchants:true");
			config.addDefault("Kit.AutoArmor", false);
			config.addDefault("Kit.default.Delay", 30);
			config.addDefault("Kit.default.Cost", 0);
			config.addDefault("Kit.default.Items", kitItemList);
			
			//Chat
			config.addDefault("Chat.Format", "%group% &e%player% &8> &f%message%");
			config.addDefault("Chat.OPColor", "5");
			
			//Warps
			List<String> warpList = new ArrayList<String>();
			config.addDefault("Warps", warpList);
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
	
	public static int getEconomyFormat() {
		return config.getInt("Economy.DefaultAmount");
	}

	public static Double getDefaultAmount() {
		return config.getDouble("Economy.DefaultAmount");
	}
	
	public static String getPrefix() {
		return config.getString("Server.Prefix");
	}
	
	public static String getName() {
		return config.getString("Server.Name");
	}
	
	public static String getMOTD() {
		return config.getString("Server.MOTD");
	}
	
	public static String getJoinMessage() {
		return config.getString("Server.JoinMessage");
	}
	
	public static String getLeaveMessage() {
		return config.getString("Server.LeaveMessage");
	}
	
}
