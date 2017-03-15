package essentials.future.code.Commands;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import essentials.future.code.ConfigManager.ConfigManager;
import essentials.future.code.main.main;

public class KitCommand implements CommandExecutor {

	private main plugin;

	public KitCommand(main plugin) {
		this.plugin = plugin;
	}

	public Map<Player, String> playerList = new HashMap<Player, String>();

	@Override
	public boolean onCommand(CommandSender sender, Command arg1, String arg2, String[] args) {
		if (sender instanceof Player) {
			Player player = (Player) sender;
			if (player.hasPermission("essentials.kit")) {
				if (args.length == 1) {
					if (ConfigManager.kitExist(args[0])) {
						if (player.hasPermission("essentials.kit." + args[0])) {
							if (playerList.containsValue(args[0]) && playerList.containsKey(player)) {
								player.sendMessage(plugin.prefix + "§cDu hattest dieses Kit schoneinmal");
							} else {

								for (String item : ConfigManager.getKit(args[0])) {
									player.getInventory().addItem(ConfigManager.getItemFromKit(item));
								}
								player.sendMessage(plugin.prefix + "§eDu hast das §c" + args[0] + "§e Kit bekommen");
								playerList.put(player, args[0]);
								startScheduler(player, ConfigManager.getKitAmount(args[0]));
							}
						} else {
							player.sendMessage(plugin.noAccess);
						}
					} else {
						player.sendMessage(plugin.prefix + "§cKit §l" + args[0] + " §cnicht gefunden");
					}
				} else if (args.length == 0) {
					List<String> kitList = ConfigManager.getKitList();
					if (kitList.isEmpty()) {
						player.sendMessage(plugin.prefix + "§cKeine Kits gefunden");
					} else {
						int i = 0;
						player.sendMessage("§8┌▬▬▬▬▬§eKits§8▬▬▬▬▬┐");
						do {
							player.sendMessage("§8├§e" + kitList.get(i));
							i++;
						} while (i < plugin.getConfigManager().getWarpList().size());
						player.sendMessage("§8└▬▬▬▬▬§eKits§8▬▬▬▬▬┘");
					}

				} else {
					player.sendMessage(plugin.prefix + "§c/kit [Name]");
				}
			} else {
				player.sendMessage(plugin.noAccess);
			}
		}
		return true;
	}

	public void startScheduler(final Player player, int amount) {
		Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {

			@Override
			public void run() {

				playerList.remove(player);

			}
		}, 20 * amount);
	}

}
