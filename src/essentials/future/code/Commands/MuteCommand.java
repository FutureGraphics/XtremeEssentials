package essentials.future.code.Commands;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import essentials.future.code.main.main;
import essentials.future.code.mysql.MySQLPlayer;

public class MuteCommand implements CommandExecutor {

	private main plugin;

	public MuteCommand(main plugin) {
		this.plugin = plugin;
	}

	@SuppressWarnings("deprecation")
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String arg2, String[] args) {
		if (sender instanceof Player) {
			Player player = (Player) sender;
			if (player.hasPermission("essentials.mute")) {
				if (args.length >= 2) {
					try {
						Player target = plugin.getServer().getPlayer(args[0]);
						if (target.isOp()) {
							player.sendMessage(plugin.prefix + "§cDu kannst keinen Operator muten");
							target.sendMessage(
									plugin.prefix + "§c" + player.getName() + " §chat versucht dich zu muten");
							Bukkit.getConsoleSender().sendMessage(plugin.prefix + "§c" + player.getName()
									+ " §chat versucht §c" + player.getName() + " §czu muten");
						} else {
							String grund = " ";
							for (int i = 1; i < args.length; i++) {
								grund += args[i] + " ";
							}
							MySQLPlayer.setMuted(target.getUniqueId().toString(), true);
							MySQLPlayer.setMuteReason(target.getUniqueId().toString(), grund);
							player.sendMessage("§8│§cDu hast §c" + target.getName() + " §cgemutet für den Grund: \n"
									+ "§8└§c" + grund);
							target.sendMessage("§8│§cDu wurdest permanent gemutet für den Grund\n" + "§8└§c" + grund);
						}
					} catch (NullPointerException e) {
						if (MySQLPlayer.userExistsByName(args[0])) {
							OfflinePlayer target = plugin.getServer().getOfflinePlayer(args[0]);
							if (target.isOp()) {
								player.sendMessage(plugin.prefix + "§cDu kannst keinen Operator muten");
								Bukkit.getConsoleSender().sendMessage(plugin.prefix + "§c" + player.getName()
										+ " §chat versucht §c" + player.getName() + " §czu muten");
							} else {
								String grund = " ";
								for (int i = 1; i < args.length; i++) {
									grund += args[i] + " ";
								}
								MySQLPlayer.setMuted(target.getUniqueId().toString(), true);
								MySQLPlayer.setMuteReason(target.getUniqueId().toString(), grund);
								player.sendMessage("§8│§cDu hast §c" + target.getName() + " §cgemutet für den Grund: \n"
										+ "§8└§c" + grund);
							}
						} else {
							player.sendMessage(plugin.playerNotFound);
						}
					}
				} else if (args.length == 1) {
					if (player.hasPermission("essentials.mute.noreason")) {
						try {
							Player target = plugin.getServer().getPlayer(args[0]);
							if (target.isOp()) {
								player.sendMessage(plugin.prefix + "§cDu kannst keinen Operator muten");
								target.sendMessage(
										plugin.prefix + "§c" + player.getName() + " §chat versucht dich zu muten");
								Bukkit.getConsoleSender().sendMessage("§c" + player.getName() + " §chat versucht §c"
										+ player.getName() + " §czu muten");
							} else {
								MySQLPlayer.setMuted(target.getUniqueId().toString(), true);
								player.sendMessage(plugin.prefix + "§cDu hast §c" + target.getName() + " §cgemutet");
								target.sendMessage(plugin.prefix + "§cDu wurdest permanent gemutet");
							}
						} catch (NullPointerException e) {
							if (MySQLPlayer.userExistsByName(args[0])) {
								OfflinePlayer target = plugin.getServer().getOfflinePlayer(args[0]);
								if (target.isOp()) {
									player.sendMessage(plugin.prefix + "§cDu kannst keinen Operator muten");
									Bukkit.getConsoleSender().sendMessage("§c" + player.getName() + " §chat versucht §c"
											+ player.getName() + " §czu muten");
								} else {
									MySQLPlayer.setMuted(target.getUniqueId().toString(), true);
									player.sendMessage(
											plugin.prefix + "§cDu hast §c" + target.getName() + " §cgemutet");
								}
							} else {
								player.sendMessage(plugin.playerNotFound);
							}
						}
					} else {
						player.sendMessage(plugin.prefix + "§cGebe einen Grund an");
					}
				} else {
					player.sendMessage(plugin.prefix + "§c/mute <Spieler> [Grund]");
				}
			} else {
				player.sendMessage(plugin.noAccess);
			}
		} else {
			if (args.length >= 2) {
				try {
					Player target = plugin.getServer().getPlayer(args[0]);
					String grund = " ";
					for (int i = 1; i < args.length; i++) {
						grund += args[i] + " ";
					}
					MySQLPlayer.setMuted(target.getUniqueId().toString(), true);
					MySQLPlayer.setMuteReason(target.getUniqueId().toString(), grund);
					sender.sendMessage(
							"§8│§cDu hast §c" + target.getName() + " §cgemutet für den Grund: \n" + "§8└§c" + grund);
					target.sendMessage("§8│§cDu wurdest permanent gemutet für den Grund\n" + "§8└§c" + grund);

				} catch (NullPointerException e) {
					if (MySQLPlayer.userExistsByName(args[0])) {
						OfflinePlayer target = plugin.getServer().getOfflinePlayer(args[0]);
							String grund = " ";
							for (int i = 1; i < args.length; i++) {
								grund += args[i] + " ";
							}
							MySQLPlayer.setMuted(target.getUniqueId().toString(), true);
							MySQLPlayer.setMuteReason(target.getUniqueId().toString(), grund);
							sender.sendMessage("§8│§cDu hast §c" + target.getName() + " §cgemutet für den Grund: \n"
									+ "§8└§c" + grund);
					} else {
						sender.sendMessage(plugin.playerNotFound);
					}
				}
			} else if (args.length == 1) {
				try {
					Player target = plugin.getServer().getPlayer(args[0]);
					MySQLPlayer.setMuted(target.getUniqueId().toString(), true);
					sender.sendMessage(plugin.prefix + "§cDu hast §c" + target.getName() + " §cgemutet");
					target.sendMessage(plugin.prefix + "§cDu wurdest permanent gemutet");
				} catch (NullPointerException e) {
					if (MySQLPlayer.userExistsByName(args[0])) {
						OfflinePlayer target = plugin.getServer().getOfflinePlayer(args[0]);
						MySQLPlayer.setMuted(target.getUniqueId().toString(), true);
						sender.sendMessage(plugin.prefix + "§cDu hast §c" + target.getName() + " §cgemutet");
					} else {
						sender.sendMessage(plugin.playerNotFound);
					}
				}
			} else {
				sender.sendMessage(plugin.prefix + "§c/mute <Spieler> [Grund]");
			}
		}
		return true;
	}

}
