package essentials.future.code.Commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import essentials.future.code.ConfigManager.ConfigManager;
import essentials.future.code.main.main;

public class TestCommand implements CommandExecutor {

	private main plugin;

	public TestCommand(main plugin) {
		this.plugin = plugin;
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String arg2, String[] args) {
		if (sender instanceof Player) {
			Player player = (Player) sender;
			if (player.hasPermission("essentials.test")) {
				player.getInventory().addItem(ConfigManager.getItemFromKit(ConfigManager.getKit("default").get(0)));
			} else {
				player.sendMessage(plugin.noAccess);
			}
		}
		return true;
	}

}
