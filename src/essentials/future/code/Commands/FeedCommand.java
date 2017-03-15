package essentials.future.code.Commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import essentials.future.code.main.main;

public class FeedCommand implements CommandExecutor {

	private main plugin;
	
	public FeedCommand(main plugin) {
		this.plugin = plugin;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String arg2, String[] args) {
		if(sender instanceof Player) {
			Player player = (Player) sender;
			if(player.hasPermission("essentials.feed")) {
				if(args.length == 1) {
					if(player.hasPermission("essentials.feed.others")) {
						try {
							Player target = plugin.getServer().getPlayer(args[0]);
							target.setFoodLevel(20);
							target.sendMessage(plugin.prefix +"§eDein Hunger wurde von §c" + player.getName() + " §egestillt");
							player.sendMessage(plugin.prefix +"§eDu hast §c" + target.getName() + " §ehunger gestillt");
						} catch(NullPointerException e) {
							player.sendMessage(plugin.playerNotFound);
						}
					}  else {
						player.sendMessage(plugin.noAccess);
					}
				} else if(args.length == 0) {
					player.setFoodLevel(20);
					player.sendMessage(plugin.prefix +"§eDein Hunger wurde gestillt");
				} else {
					player.sendMessage(plugin.prefix +"§c/feed <Spieler>");
				}
			} else {
				player.sendMessage(plugin.noAccess);
			}
		} else {
			if(args.length == 1 ) {
				try {
					Player target = plugin.getServer().getPlayer(args[0]);
					target.setFoodLevel(20);
					target.sendMessage(plugin.prefix +"§eDein Hunger wurde von §cKonsole §egestillt");
					sender.sendMessage(plugin.prefix +"§eDu hast §c" + target.getName() + " §ehunger gestillt");
				} catch(NullPointerException e) {
					sender.sendMessage(plugin.playerNotFound);
				}
			} else {
				sender.sendMessage(plugin.prefix +"§c/feed <Spieler>");
			}
		}
		return true;
	}

}
