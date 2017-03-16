package future.code.essentials.manager;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class ApiManager {

	public static ItemStack createItemStack(Material material, int amount, int meta, String name, Enchantment enchantment, int enchlvl, ItemFlag flag, String... lore) {
		ItemStack item = new ItemStack(material, amount, (short) meta);
		ItemMeta Itemmeta = item.getItemMeta();
		if(name != null) {
			Itemmeta.setDisplayName(name);
		}
		if(enchantment != null && enchlvl != 0) {
			Itemmeta.addEnchant(enchantment, enchlvl, true);
		}
		if(flag != null) {
			Itemmeta.addItemFlags(flag);
		}
		
		if(lore != null) {
			List<String> loreList = new ArrayList<String>();
			for(String loreText : lore) {
				loreList.add(loreText);
			}
			Itemmeta.setLore(loreList);
		}
		item.setItemMeta(Itemmeta);
		return item;
	}
	
	public static String encryptFormat(String format) {
		format = format.replace("%server%", MainManager.getName());
		format = format.replace("%prefix%", MainManager.getPrefix());
		format = format.replace("&", "§");
		return format;
	}
	
	public static String encryptPlayerFormat(String format, Player player) {
		format = format.replace("%server%", MainManager.getName());
		format = format.replace("%prefix%", MainManager.getPrefix());
		format = format.replace("%player%", player.getName());
		format = format.replace("%uuid%", player.getUniqueId().toString());
		if(player.getCustomName() != null) {
			format = format.replace("%nickname%", player.getCustomName());
		} else {
			format = format.replaceAll("%nickname%", player.getName());
		}
		format = format.replace("%heal%", Double.toString(player.getHealth()));
		format = format.replace("%food%", Double.toString(player.getFoodLevel()));
		format = format.replace("%location%", player.getLocation().toString());
		format = format.replace("%money%", Double.toString(PlayerManager.getMoney(player)));
		format = format.replace("%gamemode%", player.getGameMode().toString());
		format = format.replace("&", "§");
		return format;
	}
	
	public static String encryptTargetFormat(String format, Player target) {
		format = format.replace("%server%", MainManager.getName());
		format = format.replace("%prefix%", MainManager.getPrefix());
		format = format.replace("%target%", target.getName());
		format = format.replace("%targetuuid%", target.getUniqueId().toString());
		if(target.getCustomName() != null) {
			format = format.replace("%targetnickname%", target.getCustomName());
		} else {
			format = format.replaceAll("%targetnickname%", target.getName());
		}
		format = format.replace("%targetheal%", Double.toString(target.getHealth()));
		format = format.replace("%targetfood%", Double.toString(target.getFoodLevel()));
		format = format.replace("%targetlocation%", target.getLocation().toString());
		format = format.replace("%targetmoney%", Double.toString(PlayerManager.getMoney(target)));
		format = format.replace("%targetgamemode%", target.getGameMode().toString());
		format = format.replace("&", "§");
		return format;
	}
}
