package essentials.future.code.Commands;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import essentials.future.code.main.main;
import essentials.future.code.mysql.MySQLPlayer;

public class BanCommand implements CommandExecutor {

	private main plugin;

	public BanCommand(main plugin) {
		this.plugin = plugin;
	}

	@SuppressWarnings("deprecation")
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String arg2, String[] args) {
		if (sender instanceof Player) {
			Player player = (Player) sender;
			if (player.hasPermission("essentials.ban")) {
				if (args.length >= 2) {
					try {
						Player target = plugin.getServer().getPlayer(args[0]);
						String uuid = target.getUniqueId().toString();
						if (target.isOp()) {
							player.sendMessage(plugin.prefix + "§cDu kann keinen Operator Bannen");
							target.sendMessage(
									plugin.prefix + "§c" + player.getName() + " §chat versucht dich zu Bannen");
							Bukkit.getConsoleSender().sendMessage(plugin.prefix + "§c" + player.getName()
									+ " §chat versucht §c" + target.getName() + " §czu bannen");
						} else {
							String grund = " ";
							for (int i = 1; i < args.length; i++) {
								grund += args[i] + " ";
							}
							MySQLPlayer.setBanned(uuid, true);
							MySQLPlayer.setBanReason(uuid, grund);
							target.kickPlayer("§8│§cDu wurdest permanant gebannt für den Grund:\n§8└§c" + grund);
							player.sendMessage(plugin.prefix + "§c " + target.getName()
									+ " §cwurde permanent gebannt für den Grund:\n§8└§c" + grund);

							Bukkit.getConsoleSender().sendMessage(plugin.prefix + "§c" + player.getName() + " §chat §c"
									+ target.getName() + " §cgebannt für den Grund: \n§8└§c" + grund);
						}
					} catch (NullPointerException e) {
						if (MySQLPlayer.userExistsByName(args[0])) {
							OfflinePlayer target = plugin.getServer().getOfflinePlayer(args[0]);
							String uuid = target.getUniqueId().toString();
							if (target.isOp()) {
								player.sendMessage(plugin.prefix + "§cDu kann keinen Operator Bannen");
								Bukkit.getConsoleSender().sendMessage(plugin.prefix + "§c" + player.getName()
										+ " §chat versucht §c" + target.getName() + " §czu bannen");
							} else {
								String grund = " ";
								for (int i = 1; i < args.length; i++) {
									grund += args[i] + " ";
								}
								MySQLPlayer.setBanned(uuid, true);
								MySQLPlayer.setBanReason(uuid, grund);
								player.sendMessage(plugin.prefix + "§c " + target.getName()
										+ " §cwurde permanent gebannt für den Grund:\n§8└§c" + grund);

								Bukkit.getConsoleSender().sendMessage(plugin.prefix + "§c" + player.getName()
										+ " §chat §c" + target.getName() + " §cgebannt für den Grund: \n§8└§c" + grund);
							}
						} else {
							player.sendMessage(plugin.playerNotFound);
						}
					}
				} else if (args.length == 1) {
					if (player.hasPermission("essentials.ban.noreason")) {

						// Bannen für keinen Grund

						try {
							Player target = plugin.getServer().getPlayer(args[0]);
							String uuid = target.getUniqueId().toString();
							// Wenn er op ist
							if (target.isOp()) {
								player.sendMessage(plugin.prefix + "§cDu kann keinen Operator Bannen");
								target.sendMessage(
										plugin.prefix + "§c" + player.getName() + " §chat versucht dich zu Bannen");
								Bukkit.getConsoleSender().sendMessage("§c" + player.getName() + " §chat versucht §c"
										+ target.getName() + " §czu bannen");
							} else {
								MySQLPlayer.setBanned(uuid, true);

								// Kicked
								target.kickPlayer("§8│§cDu wurdest permanent Gebannt");
								player.sendMessage(
										plugin.prefix + "§c" + target.getName() + " §cwurde permanent gebannt");
								Bukkit.getConsoleSender().sendMessage(
										"§c" + player.getName() + " §chat §c" + target.getName() + " §cgebannt");
							}
						} catch (NullPointerException e) {
							OfflinePlayer target = plugin.getServer().getOfflinePlayer(args[0]);
							String uuid = target.getUniqueId().toString();
							// Wenn er op ist
							if (target.isOp()) {
								player.sendMessage(plugin.prefix + "§cDu kann keinen Operator Bannen");
								Bukkit.getConsoleSender().sendMessage("§c" + player.getName() + " §chat versucht §c"
										+ target.getName() + " §czu bannen");
							} else {
								MySQLPlayer.setBanned(uuid, true);

								// Kicked
								player.sendMessage(
										plugin.prefix + "§c" + target.getName() + " §cwurde permanent gebannt");
								Bukkit.getConsoleSender().sendMessage(
										"§c" + player.getName() + " §chat §c" + target.getName() + " §cgebannt");
							}
						}
					} else {
						player.sendMessage(plugin.prefix + "§cBitte gebe einen Grund an");
					}
				} else {
					player.sendMessage(plugin.prefix + "§c/ban <Spieler> [Grund]");
				}
			} else {
				player.sendMessage(plugin.noAccess);
			}
		} else {
			if (args.length >= 2) {
				try {
					Player target = plugin.getServer().getPlayer(args[0]);
					String uuid = target.getUniqueId().toString();
					String grund = " ";
					for (int i = 1; i < args.length; i++) {
						grund += args[i] + " ";
					}
					MySQLPlayer.setBanned(uuid, true);
					MySQLPlayer.setBanReason(uuid, grund);
					target.kickPlayer("§8│§cDu wurdest permanant gebannt für den Grund:\n§8└§c" + grund);
					sender.sendMessage(
							"§c " + target.getName() + " §cwurde permanent gebannt für den Grund:\n§8└§c" + grund);

				} catch (NullPointerException e) {
					sender.sendMessage(plugin.playerNotFound);
				}
			} else if (args.length == 1) {
				try {
					Player target = plugin.getServer().getPlayer(args[0]);
					String uuid = target.getUniqueId().toString();
					MySQLPlayer.setBanned(uuid, true);
					target.kickPlayer("§8│§cDu wurdest permanent Gebannt");
					sender.sendMessage("§c " + target.getName() + " §cwurde permanent gebannt");
				} catch (NullPointerException e) {
					sender.sendMessage(plugin.playerNotFound);
				}
			} else {
				sender.sendMessage(plugin.prefix + "§c/ban <Spieler> [Grund]");
			}
		}
		return true;
	}

}
