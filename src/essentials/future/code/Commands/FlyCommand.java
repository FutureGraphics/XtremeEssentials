package essentials.future.code.Commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import essentials.future.code.main.main;

public class FlyCommand implements CommandExecutor {

	private main plugin;
	
	public FlyCommand(main plugin) {
		this.plugin = plugin;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command arg1, String arg2, String[] args) {
		if(sender instanceof Player) {
			Player player = (Player) sender;
			if(player.hasPermission("essentials.fly")) {
				if(args.length == 1) {
					if(player.hasPermission("essentials.fly.others")) {
						try {
							Player target = plugin.getServer().getPlayer(args[0]);
							if(target.getAllowFlight()) {
								target.setAllowFlight(false);
								target.setFlying(false);
								target.sendMessage(plugin.prefix +"§cDir wurde Fly weggenommen");
								player.sendMessage(plugin.prefix +"§c" + target.getName() + " §cwurde Fly entzogen");
							} else {
								target.setAllowFlight(true);
								target.setFlying(true);
								target.sendMessage(plugin.prefix +"§eDir wurde Fly gesetzt");
								player.sendMessage(plugin.prefix +"§c" + target.getName() + " §ewurde Fly gesetzt");
							}
						} catch(NullPointerException e) {
							player.sendMessage(plugin.playerNotFound);
						}
					} else {
						if(player.getAllowFlight()) {
							player.setAllowFlight(false);
							player.setFlying(false);
							player.sendMessage(plugin.prefix +"§cDir wurde Fly weggenommen");
						} else {
							player.setAllowFlight(true);
							player.setFlying(true);
							player.sendMessage(plugin.prefix +"§eDir wurde Fly gesetzt");
						}
					}
				} else if(args.length == 0){
					if(player.getAllowFlight()) {
						player.setAllowFlight(false);
						player.setFlying(false);
						player.sendMessage(plugin.prefix +"§cDir wurde Fly weggenommen");
					} else {
						player.setAllowFlight(true);
						player.setFlying(true);
						player.sendMessage(plugin.prefix +"§eDir wurde Fly gesetzt");
					}
				} else {
					player.sendMessage(plugin.prefix +"§c/fly <Spieler>");
				}
			} else {
				player.sendMessage(plugin.noAccess);
			}
		} else {
			if(args.length == 1) {
				try {
					Player target = plugin.getServer().getPlayer(args[0]);
					if(target.getAllowFlight()) {
						target.setAllowFlight(false);
						target.setFlying(false);
						target.sendMessage(plugin.prefix +"§cDir wurde Fly weggenommen");
						sender.sendMessage(plugin.prefix +"§c" + target.getName() + " §cwurde Fly entzogen");
					} else {
						target.setAllowFlight(true);
						target.setFlying(true);
						target.sendMessage(plugin.prefix +"§eDir wurde Fly gesetzt");
						sender.sendMessage(plugin.prefix +"§c" + target.getName() + " §ewurde Fly gesetzt");
					}
				} catch(NullPointerException e) {
					sender.sendMessage(plugin.playerNotFound);
				}
			} else {
				sender.sendMessage(plugin.prefix +"§c/fly <Spieler>");
			}
		}
		return true;
	}

}
