package essentials.future.code.Commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Scoreboard;

import essentials.future.code.ConfigManager.ApiManager;
import essentials.future.code.ConfigManager.ConfigManager;
import essentials.future.code.main.main;
import essentials.future.code.mysql.MySQLPlayer;

public class ECOCommand implements CommandExecutor {

	private main plugin;
	
	public ECOCommand(main plugin) {
		this.plugin = plugin;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String arg2, String[] args) {
		if(sender instanceof Player) {
			Player player = (Player) sender;
			if(player.hasPermission("essentials.eco")) {
				if(args.length == 3) {
					if(args[0].equals("set")) {
						try {
							Player target = plugin.getServer().getPlayer(args[2]);
							try {
								int amount = Integer.parseInt(args[1]);
								MySQLPlayer.updateCoins(target.getUniqueId(), amount, false, target.getName());
								player.sendMessage(plugin.prefix + "§c" + amount + "§e wurdem §c" + target.getName() + " §egesetzt");
								for(Player players : Bukkit.getOnlinePlayers()) {
									if (ConfigManager.isScoreboardEnabled()) {
										Scoreboard board = ApiManager.createScoreboard(players,ConfigManager.getScorboardDisplayname(),ConfigManager.getScorboardList());
										players.setScoreboard(board);
									}
								}
							} catch(NumberFormatException e) {
								player.sendMessage(plugin.prefix + "§cUnbekanntes format");
							}
						} catch(NullPointerException e) {
							player.sendMessage(plugin.playerNotFound);
						}
					} else if(args[0].equalsIgnoreCase("add")) {
						try {
							Player target = plugin.getServer().getPlayer(args[2]);
							try {
								int amount = Integer.parseInt(args[1]);
								int coins = MySQLPlayer.getCoins(player.getUniqueId().toString());
								MySQLPlayer.updateCoins(target.getUniqueId(), amount+coins, false, target.getName());
								player.sendMessage(plugin.prefix + "§c" + amount + "§e wurdem §c" + target.getName() + " §ehinzugefügt");
								for(Player players : Bukkit.getOnlinePlayers()) {
									if (ConfigManager.isScoreboardEnabled()) {
										Scoreboard board = ApiManager.createScoreboard(players,ConfigManager.getScorboardDisplayname(),ConfigManager.getScorboardList());
										players.setScoreboard(board);
									}
								}
							} catch(NumberFormatException e) {
								player.sendMessage(plugin.prefix + "§cUnbekanntes format");
							}
						} catch(NullPointerException e) {
							player.sendMessage(plugin.playerNotFound);
						}
					} else if(args[0].equalsIgnoreCase("remove")) {
						try {
							Player target = plugin.getServer().getPlayer(args[2]);
							try {
								int amount = Integer.parseInt(args[1]);
								MySQLPlayer.updateCoins(target.getUniqueId(), amount, true, target.getName());
								player.sendMessage(plugin.prefix + "§c" + amount + "§e wurdem §c" + target.getName() + " §eabgezogen");
								for(Player players : Bukkit.getOnlinePlayers()) {
									if (ConfigManager.isScoreboardEnabled()) {
										Scoreboard board = ApiManager.createScoreboard(players,ConfigManager.getScorboardDisplayname(),ConfigManager.getScorboardList());
										players.setScoreboard(board);
									}
								}
							} catch(NumberFormatException e) {
								player.sendMessage(plugin.prefix + "§cUnbekanntes format");
							}
						} catch(NullPointerException e) {
							player.sendMessage(plugin.playerNotFound);
						}
					} else {
						player.sendMessage(plugin.prefix + "§c/eco [set|add|remove] {Amount} <Spieler>");
					}
				} else {
					player.sendMessage(plugin.prefix + "§c/eco [set|add|remove] {Amount} <Spieler>");
				}
			} else {
				player.sendMessage(plugin.noAccess);
			}
		}
		return true;
	}

}
