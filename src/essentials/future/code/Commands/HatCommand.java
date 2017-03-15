package essentials.future.code.Commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import essentials.future.code.ConfigManager.ApiManager;
import essentials.future.code.main.main;

public class HatCommand implements CommandExecutor {

	private main plugin;
	
	public HatCommand(main plugin) {
		this.plugin = plugin;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command arg1, String arg2, String[] args) {
		if(sender instanceof Player) {
			Player player = (Player) sender;
			if(player.hasPermission("essentials.hat")) {
				if(args.length == 0) {

					ItemStack switchItem = player.getItemInHand();
					if(player.getInventory().getHelmet() != null) {
						ItemStack hatItem = player.getInventory().getHelmet();
						player.getInventory().addItem(hatItem);
					}
					player.getInventory().setHelmet(switchItem);
					ApiManager.removeItemStack(switchItem.getType(), player, switchItem.getAmount());
					player.sendMessage(plugin.prefix + "ßeViel spaﬂ mit deinem Neuen Hut");
				} else {
					player.sendMessage(plugin.prefix + "ßc/hat");
				}
			} else {
				player.sendMessage(plugin.noAccess);
			}
		} else {
			sender.sendMessage(plugin.noConsole);
		}
		return true;
	}

}
