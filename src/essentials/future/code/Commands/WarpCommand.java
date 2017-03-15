package essentials.future.code.Commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import essentials.future.code.main.main;

public class WarpCommand implements CommandExecutor {

	private main plugin;

	public WarpCommand(main plugin) {
		this.plugin = plugin;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String arg2, String[] args) {
		if (sender instanceof Player) {
			Player player = (Player) sender;
			if (player.hasPermission("essentials.warp")) {
				if (args.length == 1) {
					if (plugin.getConfigManager().warpExist(args[0])) {
						if (player.hasPermission("essentials.warp." + args[0])) {
							plugin.onPlayerTeleport(player, plugin.getConfigManager().getWarp(args[0]),
									plugin.prefix + "§eDu wurdest zu dem Warp-Punkt §c" + args[0] + " §eteleportiert");
						} else {
							player.sendMessage(plugin.noAccess);
						}
					} else {
						player.sendMessage(plugin.prefix + "§cWarp konnte nicht gefunden werden");
					}
				} else if (args.length == 2) {
					if (player.hasPermission("essentials.warp.others")) {
						try {
							Player target = plugin.getServer().getPlayer(args[1]);
							if (plugin.getConfigManager().warpExist(args[0])) {
								target.teleport(plugin.getConfigManager().getWarp(args[0]));
								target.sendMessage(plugin.prefix + "§eDu wurdest zu dem Warp-Punkt §c" + args[0]
										+ " §ateleportiert");
								player.sendMessage(plugin.prefix + "§c" + target.getName()
										+ " §ewurde erfolgreich zu dem Warp-Punkt §c" + args[0] + " §eteleportiert");
							} else {
								player.sendMessage(plugin.prefix + "§cWarp konnte nicht gefunden werden");
							}
						} catch (NullPointerException e) {
							player.sendMessage(plugin.playerNotFound);
						}
					} else {
						player.sendMessage(plugin.noAccess);
					}
				} else if (args.length == 0) {

					if (plugin.getConfigManager().getWarpList().isEmpty()) {
						player.sendMessage(plugin.prefix + "§cEs sind keine Warp-Punkte gesetzt worden");
					} else {
						int i = 0;
						player.sendMessage("§8┌▬▬▬▬▬§eWarp-Punke§8▬▬▬▬▬┐");
						do {
							player.sendMessage("§8├§e" + plugin.getConfigManager().getWarpList().get(i));
							i++;
						} while (i < plugin.getConfigManager().getWarpList().size());
						player.sendMessage("§8└▬▬▬▬▬§eWarp-Punke§8▬▬▬▬▬┘");
					}
				} else {
					player.sendMessage(plugin.prefix + "§c/warp [Namen] <Spieler>");
				}
			} else {
				player.sendMessage(plugin.noAccess);
			}
		} else {
			if (args.length == 0) {
				sender.sendMessage(
						plugin.prefix + "§eAlle Warps: §6" + plugin.getConfigManager().getWarpList().toString());
			} else if (args.length == 2) {
				try {
					Player target = plugin.getServer().getPlayer(args[1]);
					if (plugin.getConfigManager().warpExist(args[0])) {
						target.teleport(plugin.getConfigManager().getWarp(args[0]));
						target.sendMessage(
								plugin.prefix + "§eDu wurdest zu dem Warp-Punkt §c" + args[0] + " §eteleportiert");
						sender.sendMessage(plugin.prefix + "§c" + target.getName()
								+ " §ewurde erfolgreich zu dem Warp-Punkt §c" + args[0] + " §eteleportiert");
					} else {
						sender.sendMessage(plugin.prefix + "§cWarp konnte nicht gefunden werden");
					}
				} catch (NullPointerException e) {
					sender.sendMessage(plugin.playerNotFound);
				}
			} else {
				sender.sendMessage(plugin.prefix + "§c/warp [Namen] <Spieler>");
			}
		}
		return true;
	}

}