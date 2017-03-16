package essentials.future.code.ConfigManager;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;

import essentials.future.code.mysql.MySQLPlayer;

public class ApiManager extends JavaPlugin {

	public static Boolean takeAllEffectsFromPlayer(Player player) {
		if (player.getActivePotionEffects() != null) {
			for (PotionEffect effect : player.getActivePotionEffects()) {
				PotionEffectType effectType = effect.getType();
				player.removePotionEffect(effectType);
				return true;
			}
		} else {
			return false;
		}
		return false;
	}

	public static void repairArmorFromPlayer(Player player) {
		if (player.getInventory().getChestplate() != null) {
			ItemStack item = player.getInventory().getChestplate();
			item.setDurability((short) 0);
		}
		if (player.getInventory().getHelmet() != null) {
			ItemStack item = player.getInventory().getHelmet();
			item.setDurability((short) 0);
		}
		if (player.getInventory().getLeggings() != null) {
			ItemStack item = player.getInventory().getLeggings();
			item.setDurability((short) 0);
		}
		if (player.getInventory().getBoots() != null) {
			ItemStack item = player.getInventory().getBoots();
			item.setDurability((short) 0);
		}
	}

	public static Boolean killAllAnimalsInWorld(World world) {
		if (world.getEntities() != null) {
			for (Entity entity : world.getEntities()) {
				entity.remove();
				return true;
			}
		}
		return false;
	}

	public static Boolean isBoot(Material material) {
		List<Material> bootList = new ArrayList<Material>();
		bootList.add(Material.LEATHER_BOOTS);
		bootList.add(Material.GOLD_BOOTS);
		bootList.add(Material.CHAINMAIL_BOOTS);
		bootList.add(Material.IRON_BOOTS);
		bootList.add(Material.DIAMOND_BOOTS);
		if(bootList.contains(material)) {
			return true;
		}
		return false;
	}
	
	public static Boolean isLeggins(Material material) {
		List<Material> leginsList = new ArrayList<Material>();
		leginsList.add(Material.LEATHER_LEGGINGS);
		leginsList.add(Material.GOLD_LEGGINGS);
		leginsList.add(Material.CHAINMAIL_LEGGINGS);
		leginsList.add(Material.IRON_LEGGINGS);
		leginsList.add(Material.DIAMOND_LEGGINGS);
		if(leginsList.contains(material)) {
			return true;
		}
		return false;
	}
	
	public static Boolean isChestplate(Material material) {
		List<Material> chestPlateList = new ArrayList<Material>();
		chestPlateList.add(Material.LEATHER_CHESTPLATE);
		chestPlateList.add(Material.GOLD_CHESTPLATE);
		chestPlateList.add(Material.CHAINMAIL_CHESTPLATE);
		chestPlateList.add(Material.IRON_CHESTPLATE);
		chestPlateList.add(Material.DIAMOND_CHESTPLATE);
		if(chestPlateList.contains(material)) {
			return true;
		}
		return false;
	}
	
	public static Boolean isHelment(Material material) {
		List<Material> helmentList = new ArrayList<Material>();
		helmentList.add(Material.LEATHER_HELMET);
		helmentList.add(Material.GOLD_HELMET);
		helmentList.add(Material.CHAINMAIL_HELMET);
		helmentList.add(Material.IRON_HELMET);
		helmentList.add(Material.DIAMOND_HELMET);
		if(helmentList.contains(material)) {
			return true;
		}
		return false;
	}
	
	public static Boolean isArmor(Material material) {
		List<Material> armorList = new ArrayList<Material>();
		armorList.add(Material.LEATHER_BOOTS);
		armorList.add(Material.LEATHER_LEGGINGS);
		armorList.add(Material.LEATHER_CHESTPLATE);
		armorList.add(Material.LEATHER_HELMET);
		armorList.add(Material.GOLD_HELMET);
		armorList.add(Material.GOLD_CHESTPLATE);
		armorList.add(Material.GOLD_LEGGINGS);
		armorList.add(Material.GOLD_BOOTS);
		armorList.add(Material.CHAINMAIL_BOOTS);
		armorList.add(Material.CHAINMAIL_LEGGINGS);
		armorList.add(Material.CHAINMAIL_CHESTPLATE);
		armorList.add(Material.CHAINMAIL_HELMET);
		armorList.add(Material.IRON_HELMET);
		armorList.add(Material.IRON_CHESTPLATE);
		armorList.add(Material.IRON_LEGGINGS);
		armorList.add(Material.IRON_BOOTS);
		armorList.add(Material.DIAMOND_HELMET);
		armorList.add(Material.DIAMOND_CHESTPLATE);
		armorList.add(Material.DIAMOND_LEGGINGS);
		armorList.add(Material.DIAMOND_BOOTS);
		if (armorList.contains(material)) {
			return true;
		}
		return false;
	}

	public static Boolean healPlayer(Player player) {
		player.setFoodLevel(20);
		player.setHealth(20);
		takeAllEffectsFromPlayer(player);
		return true;
	}

	public static Boolean clearAllFromPlayer(Player player) {
		if (player.getInventory().getContents() != null) {
			player.getInventory().clear();
			player.getInventory().setHelmet(null);
			player.getInventory().setChestplate(null);
			player.getInventory().setLeggings(null);
			player.getEquipment().setBoots(null);
			return true;
		} else {
			return false;
		}
	}

	public static ItemStack createItem(Material material, String name, int amount, int metaitem,
			Enchantment enchantment, int enchantmentLvL, ItemFlag itemFlag, String... lore) {
		ItemStack item = new ItemStack(material, amount, (short) metaitem);
		ItemMeta meta = item.getItemMeta();
		if (name != null) {
			meta.setDisplayName(name);
		}
		if (enchantment != null || enchantmentLvL != 0) {
			meta.addEnchant(enchantment, enchantmentLvL, true);
		}
		if (lore != null) {
			List<String> loreList = new ArrayList<>();
			for (String text : lore) {
				loreList.add(text);
			}
			meta.setLore(loreList);
		}
		if (itemFlag != null) {
			meta.addItemFlags(itemFlag);
		}
		item.setItemMeta(meta);
		return item;
	}

	public static ItemStack createSkull(String owner, String name, int amount, Enchantment enchantment,
			int enchantmentLvL, ItemFlag itemFlag, String... lore) {
		ItemStack item = new ItemStack(Material.SKULL_ITEM, amount);
		SkullMeta meta = (SkullMeta) item.getItemMeta();
		item.setDurability((short) 3);
		if (owner != null) {
			meta.setOwner(owner);
		}
		if (name != null) {
			meta.setDisplayName(name);
		}
		if (enchantment != null && enchantmentLvL != 0) {
			meta.addEnchant(enchantment, enchantmentLvL, true);
		}
		if (itemFlag != null) {
			meta.addItemFlags(itemFlag);
		}
		if (lore != null) {
			List<String> loreList = new ArrayList<>();
			for (String text : lore) {
				loreList.add(text);
			}
			meta.setLore(loreList);
		}
		if (itemFlag != null) {
			meta.addItemFlags(itemFlag);
		}
		item.setItemMeta(meta);
		return item;
	}

	public static ItemStack createLetherArmor(Material material, String name, int amount, int metaItem, Color color,
			Enchantment enchantment, int enchantmentLvL, ItemFlag itemFlag, String... lore) {
		ItemStack item = new ItemStack(material, amount, (short) metaItem);
		LeatherArmorMeta meta = (LeatherArmorMeta) item.getItemMeta();
		if (name != null) {
			meta.setDisplayName(name);
		}
		if (enchantment != null || enchantmentLvL != 0) {
			meta.addEnchant(enchantment, enchantmentLvL, true);
		}
		if (color != null) {
			meta.setColor(color);
		}
		if (lore != null) {
			List<String> loreList = new ArrayList<>();
			for (String text : lore) {
				loreList.add(text);
			}
			meta.setLore(loreList);
		}
		if (itemFlag != null) {
			meta.addItemFlags(itemFlag);
		}
		item.setItemMeta(meta);
		return item;
	}

	public static List<String> getAddList() {
		List<String> list = new ArrayList<String>();
		list.add("de");
		list.add("eu");
		list.add("net");
		list.add("ch");
		list.add("se");
		list.add("to");
		list.add("eu");
		return list;
	}

	@SuppressWarnings("deprecation")
	public static Scoreboard createScoreboard(Player player, String displayname, List<String> lines) {
		ScoreboardManager manager = Bukkit.getScoreboardManager();
		Scoreboard board = manager.getNewScoreboard();
		Objective obj = board.registerNewObjective("test", "dummy");
		obj.setDisplaySlot(DisplaySlot.SIDEBAR);
		obj.setDisplayName(displayname);
		int i = 0;
		for (String textLines : lines) {
			textLines = textLines.replace("&", "§");
			textLines = textLines.replace("%server%", ConfigManager.getServerName());
			textLines = textLines.replace("%player%", player.getName());
			textLines = textLines.replace("%kills%", Integer.toString(MySQLPlayer.getKills(player.getUniqueId())));
			textLines = textLines.replace("%deaths%", Integer.toString(MySQLPlayer.getDeaths(player.getUniqueId())));
			textLines = textLines.replace("%coins%",
					Integer.toString(MySQLPlayer.getCoins(player.getUniqueId().toString())));
			textLines = textLines.replace("%rang%", MySQLPlayer.getGroupFromPlayer(player.getUniqueId().toString()));
			if (textLines != null) {
				Score score = obj.getScore(Bukkit.getOfflinePlayer(textLines));
				score.setScore(i);
				i++;
			}
		}

		return board;
	}

	public static int getItemAmount(Material material, Player player) {
		int amount = 0;
		ItemStack item = null;
		for (int i = 0; i < player.getInventory().getSize(); i++) {
			item = player.getInventory().getItem(i);
			if (item != null && item.getType() == material) {
				amount += item.getAmount();
			} else {
			}
		}
		return amount;
	}

	public static void removeItemStack(Material material, Player player, int amount) {
		// player.getInventory().removeItem(new ItemStack(material, amount));
		ItemStack item = null;
		for (int i = 0; i < player.getInventory().getSize(); i++) {
			item = player.getInventory().getItem(i);
			if (item != null && item.getType() == material) {
				if (item.getAmount() >= amount) {
					if (item.getAmount() == amount) {
						player.getInventory().setItem(i, null);
					} else {
						item.setAmount((item.getAmount() - amount));
						// player.getInventory().setItem(i, item);
					}
					return;
				} else {
					amount -= item.getAmount();
					player.getInventory().setItem(i, null);
				}
			}
		}
	}

	public static boolean isInventoryFull(Player player) {
		int empty = -1;
		empty = player.getInventory().firstEmpty();
		if (empty != -1) {
			return false;
		} else {
			return true;
		}
	}

}
