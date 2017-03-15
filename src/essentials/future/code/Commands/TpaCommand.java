package essentials.future.code.Commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import essentials.future.code.main.main;

public class TpaCommand implements CommandExecutor {

	private main plugin;

	public TpaCommand(main plugin) {
		this.plugin = plugin;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String arg2, String[] args) {
		if (sender instanceof Player) {
			final Player player = (Player) sender;
			if (player.hasPermission("essentials.tpa")) {
				if (args.length == 1) {
					try {
						final Player target = plugin.getServer().getPlayer(args[0]);
						plugin.tpaMap.put(target, player);
						target.sendMessage("§8┌▬▬▬▬▬ Teleport ▬▬▬▬▬\n" + "§8│§a" + player.getName()
								+ " §ehat dir eine TP-Anfrage gesendet\n"
								+ "§8│§eDie anfrage ist §c§l120 §eSekunden Gültig\n"
								+ "§8│§8- §e/tpaccept §8- zum annehmen der Anfrage\n"
								+ "§8│§8- §e/tpdeny §8- zum ablehnen der Anfrage\n" + "§8└▬▬▬▬▬ Teleport ▬▬▬▬▬");
						player.sendMessage(
								plugin.prefix + "§eDu hast §c" + target.getName() + " §eeine TP-Anfrage gesendet");
						Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {

							@Override
							public void run() {
								if (plugin.tpaMap.containsKey(target)) {
									player.sendMessage(plugin.prefix + "§eDie TP-Anfrage ist abgelaufen");
									plugin.tpaMap.remove(target);
								}
							}
						}, 20 * 120);
					} catch (NullPointerException e) {
						player.sendMessage(plugin.playerNotFound);
					}
				} else {
					player.sendMessage(plugin.prefix + "§c/tpa <Spieler>");
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
