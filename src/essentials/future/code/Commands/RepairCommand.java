package essentials.future.code.Commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import essentials.future.code.ConfigManager.ApiManager;
import essentials.future.code.main.main;

public class RepairCommand implements CommandExecutor {

	private main plugin;
	
	public RepairCommand(main plugin) {
		this.plugin = plugin;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String arg2, String[] args) {
		if(sender instanceof Player) {
			Player player = (Player) sender;
			if(player.hasPermission("essentials.repair")) {
				if(args.length == 1) {
					if(args[0].equalsIgnoreCase("hand")) {
						if(player.hasPermission("essentials.repair.hand")) {
							ItemStack item = player.getItemInHand();
							if(!item.getType().isBlock()) {
								item.setDurability((short) 0);
								player.sendMessage(plugin.prefix +"§eItem §c" + item.getType().toString() + " §ewurde erfolgreich repariert");
							} else {
								player.sendMessage(plugin.prefix +"§cDu darfst keine Blöcke reparieren");
							}
						} else {
							player.sendMessage(plugin.noAccess);
						}
					} else if(args[0].equalsIgnoreCase("armor")) {
						if(player.hasPermission("essentials.repair.armor")) {
							ApiManager.repairArmorFromPlayer(player);
							player.sendMessage(plugin.prefix + "§eDeine Rüstung wurde Repariert");
						}
					} else {
						player.sendMessage(plugin.prefix +"§c/repair [Hand | Armor]");
					}
				} else {
					player.sendMessage(plugin.prefix +"§c/repair [Hand | Armor]");
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
