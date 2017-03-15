package essentials.future.code.ConfigManager;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import essentials.future.code.main.main;

public class ConfigManager {

	private main plugin;
	private File file;
	private static FileConfiguration config;

	public ConfigManager(main plugin) {
		this.plugin = plugin;
		this.file = new File(this.plugin.getDataFolder(), "config.yml");
		config = YamlConfiguration.loadConfiguration(this.file);
		load();
	}

	public void load() {
		if (!this.file.exists()) {
			config.options().copyDefaults(true);
			// Server configuration
			config.set("Server.PublicDeathMessage", true);
			config.set("Server.DeathMessage", "&c%killer% &4hat &c%player% &4getötet");
			config.set("Server.JoinMessage", "&8[&a+&8] &7%player%");
			config.set("Server.QuitMessage", "&8[&c-&8] &7%player%");
			config.set("Server.Name", "test-Server");
			
			config.set("Scoreboard.Enabled", true);
			config.set("Scoreboard.Displayname", "&c&lTest");
			List<String> scoreboard = new ArrayList<String>();
			scoreboard.add("&eHallo");
			scoreboard.add("&2ich");
			scoreboard.add("&abin ein");
			scoreboard.add("&dScoreboard");
			scoreboard.add("%player%");
			config.set("Scoreboard.List", scoreboard);
			
			List<String> kitList = new ArrayList<String>();
			kitList.add("159:1,amount:1,name:&8cool,lore:&8coole,enchant:1,lvl:1");
			config.set("Kit.default.List", kitList);
			config.set("Kit.default.Amount", 10);
			
			List<String> kits = new ArrayList<String>();
			kits.add("default");
			config.set("KitList", kits);
			
			config.set("MySQL.Host", "localhost");
			config.set("MySQL.Database", "minecraft");
			config.set("MySQL.Username", "test");
			config.set("MySQL.Password", "test");
			
			// Player configuration
			config.set("Player.Homes.Default", 3);
			config.set("Player.Homes.VIP", 5);
			config.set("Player.Msg", "&8[&eMSG&8] &e%sender% &8-> &e%player%&8: %message%");
			config.set("Player.TpDelay", 3);
			config.set("Player.PvPDelay", 15);
			config.set("Player.HealDelay", 60);
			config.set("Player.AmountOnKill", 10);
			config.set("Player.AmountOnDeath", 0);
			// Warp
			List<String> warpList = new ArrayList<String>();
			config.set("WarpList", warpList);
			save();
		} else {
			try {
				config.load(this.file);
			} catch (IOException | InvalidConfigurationException e) {
				e.printStackTrace();
			}
		}
	}

	public void save() {
		try {
			config.save(this.file);
		} catch (IOException localIOException) {
		}
	}
	
	@SuppressWarnings("deprecation")
	public static ItemStack getItemFromKit(String kitMeta) {
		String[] MainA = kitMeta.split(",");
		ItemStack item = null;
		
		String[] amountS = MainA[1].split(":");
		int amount = Integer.parseInt(amountS[1]);
		
		if(MainA[0].contains(":")) {
			String[] MainB = MainA[0].split(":");
			item = new ItemStack(Material.getMaterial(Integer.parseInt(MainB[0])), amount, (short) Integer.parseInt(MainB[1]));
		} else {
			int itemTyp = Integer.parseInt(MainA[0]);
			item = new ItemStack(Material.getMaterial(itemTyp));
		}
		
		item.setAmount(amount);
		
		String[] nameS = MainA[2].split(":");
		String[] loreS = MainA[3].split(":");
		String[] enchantS = MainA[4].split(":");
		String[] enChantLvLS = MainA[5].split(":");
		
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName(nameS[1].replace("&", "§"));
		
		List<String> lore = new ArrayList<String>();
		lore.add(loreS[1].replace("&", "§"));
		meta.setLore(lore);
		int enchant = Integer.parseInt(enchantS[1]);
		int enchantLvL = Integer.parseInt(enChantLvLS[1]);
		if(enchant != 0 && enChantLvLS != null) {
			meta.addEnchant(Enchantment.getById(enchant), enchantLvL, true);
		}
		item.setItemMeta(meta);
		return item;
	}
	
	public static Boolean kitExist(String name) {
		if(config.contains("Kit." + name + ".List")) {
			return true;
		}
		return false;
	}
	
	public static int getKitAmount(String name) {
		return config.getInt("Kit." + name + ".Amount");
	}
	
	public static String getScorboardDisplayname() {
		String displayname = config.getString("Scoreboard.Displayname");
		displayname = displayname.replace("&", "§");
		return displayname;
	}
	
	public static List<String> getKit(String name) {
		return config.getStringList("Kit." + name + ".List");
	}
	
	public static List<String> getKitList() {
		return config.getStringList("KitList");
	}
	
	public static List<String> getScorboardList() {
		return config.getStringList("Scoreboard.List");
	}
	
	public static Boolean isScoreboardEnabled() {
		return config.getBoolean("Scoreboard.Enabled");
	}
	
	public static String getServerName() {
		return config.getString("Server.Name");
	}
	
	public static int getHealDelay() {
		return config.getInt("Player.HealDelay");
	}
	
	public static int getAmountOnKill() {
		return config.getInt("Player.AmountOnKill");
	}
	
	public static int getAmountOnDeath() {
		return config.getInt("Player.AmountOnDeath");
	}

	public void removeWarp(String name) {
		config.set("Warp." + name + ".World", null);
		config.set("Warp." + name + ".X", null);
		config.set("Warp." + name + ".Y", null);
		config.set("Warp." + name + ".Z", null);
		config.set("Warp." + name + ".Yaw", null);
		config.set("Warp." + name + ".Pitch", null);
		removeEntryFromWarpList(name);
		save();
	}

	public Boolean warpExist(String name) {
		if (config.contains("Warp." + name + ".World")) {
			return true;
		} else {
			return false;
		}
	}
	

	public Location getWarp(String name) {
		Location loc = null;
		if (config.contains("Warp." + name + ".World")) {
			loc = new Location(plugin.getServer().getWorld(config.getString("Warp." + name + ".World")),
					config.getDouble("Warp." + name + ".X"), config.getDouble("Warp." + name + ".Y"),
					config.getDouble("Warp." + name + ".Z"));
			loc.setYaw((float) config.getDouble("Warp." + name + ".Yaw"));
			loc.setPitch((float) config.getDouble("Warp." + name + ".Pitch"));
		}
		return loc;
	}

	public void setWarp(String name, Location loc) {
		config.set("Warp." + name + ".World", loc.getWorld().getName());
		config.set("Warp." + name + ".X", loc.getX());
		config.set("Warp." + name + ".Y", loc.getY());
		config.set("Warp." + name + ".Z", loc.getZ());
		config.set("Warp." + name + ".Yaw", loc.getYaw());
		config.set("Warp." + name + ".Pitch", loc.getPitch());
		addEntryToWarpList(name);
		save();
	}

	public void removeEntryFromWarpList(String name) {
		if (getWarpList().contains(name)) {
			List<String> warpList = getWarpList();
			warpList.remove(name);
			config.set("WarpList", warpList);
			save();
		}
	}
	
	public void addEntryToWarpList(String name) {
		if (!getWarpList().contains(name)) {
			List<String> warpList = getWarpList();
			warpList.add(name);
			config.set("WarpList", warpList);
			save();
		}
	}

	public List<String> getWarpList() {
		return config.getStringList("WarpList");
	}

	public void setSpawn(Location loc) {
		config.set("Spawn.World", loc.getWorld().getName());
		config.set("Spawn.X", loc.getX());
		config.set("Spawn.Y", loc.getY());
		config.set("Spawn.Z", loc.getZ());
		config.set("Spawn.Yaw", loc.getYaw());
		config.set("Spawn.Pitch", loc.getPitch());
		save();
	}

	public Location getSpawn() {
		Location loc = null;
		if (config.contains("Spawn.World")) {
			loc = new Location(plugin.getServer().getWorld(config.getString("Spawn.World")),
					config.getDouble("Spawn.X"), config.getDouble("Spawn.Y"), config.getDouble("Spawn.Z"));
			loc.setYaw((float) config.getDouble("Spawn.Yaw"));
			loc.setPitch((float) config.getDouble("Spawn.Pitch"));
		}
		return loc;
	}

	public int getPvPDelay() {
		return config.getInt("Player.PvPDelay");
	}

	public int TpDelay() {
		return config.getInt("Player.TpDelay");
	}

	public String getMsg() {
		return config.getString("Player.Msg");
	}

	public int getHomes(String group) {
		return config.getInt("Player.Homes." + group);
	}

	public int getMoneyOnStart() {
		return config.getInt("Player.MoneyOnStart");
	}

	public String getQuitMessage() {
		return config.getString("Server.QuitMessage");
	}

	public String getJoinMessage() {
		return config.getString("Server.JoinMessage");
	}

	public String getDeathMessage() {
		return config.getString("Server.DeathMessage");
	}

	public Boolean getPublicDeathMessage() {
		return config.getBoolean("Server.PublicDeathMessage");
	}
	
	public static String getMySQLHost() {
		return config.getString("MySQL.Host");
	}
	
	public static String getMySQLUsername() {
		return config.getString("MySQL.Username");
	}
	
	public static String getMySQLDatabase() {
		return config.getString("MySQL.Database");
	}
	
	public static String getMySQLPassword() {
		return config.getString("MySQL.Password");
	}
}