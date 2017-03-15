package essentials.future.code.Commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Scoreboard;

import essentials.future.code.ConfigManager.ApiManager;
import essentials.future.code.ConfigManager.ConfigManager;
import essentials.future.code.ConfigManager.PlayerManager;
import essentials.future.code.main.main;

public class EssentialsCommand implements CommandExecutor {

	private main plugin;
	
	public EssentialsCommand(main plugin) {
		this.plugin = plugin;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command arg1, String arg2, String[] args) {
		if(sender instanceof Player) {
			Player player = (Player) sender;
			if(player.hasPermission("essentials.command")) {
				if(args.length == 1) {
					if(args[0].equalsIgnoreCase("reload")) {
						plugin.getConfigManager().load();
						player.sendMessage("§8[§eConfigManager§8] §a§lOK");
						PlayerManager.load();
						player.sendMessage("§8[§ePlayerManager§8] §a§lOK");
					} else if(args[0].equalsIgnoreCase("scoreboard")) {
						for(Player players : Bukkit.getOnlinePlayers()) {
							if (ConfigManager.isScoreboardEnabled()) {
								Scoreboard board = ApiManager.createScoreboard(players,ConfigManager.getScorboardDisplayname(),ConfigManager.getScorboardList());
								players.setScoreboard(board);
							}
						}
						player.sendMessage("§8[§eScoreboard§8] §a§lGeladen");
					} else if(args[0].equalsIgnoreCase("author")) {
						player.sendMessage("§eEssentials coded by FutureCode");
						player.sendMessage("Author: future_graphics");
					}
				} else {
					player.sendMessage(plugin.prefix + "§c/essentials [reload | scoreboard | author]");
				}
			} else {
				player.sendMessage(plugin.noAccess);
			}
		} else {
			if(args.length == 1) {
				if(args[0].equalsIgnoreCase("reload")) {
					plugin.getConfigManager().load();
					sender.sendMessage("§8[§eConfigManager§8] §a§lOK");
					PlayerManager.load();
					sender.sendMessage("§8[§ePlayerManager§8] §a§lOK");
				} else if(args[0].equalsIgnoreCase("scoreboard")) {
					for(Player players : Bukkit.getOnlinePlayers()) {
						if (ConfigManager.isScoreboardEnabled()) {
							Scoreboard board = ApiManager.createScoreboard(players,ConfigManager.getScorboardDisplayname(),ConfigManager.getScorboardList());
							players.setScoreboard(board);
						}
					}
					sender.sendMessage("§8[§eScoreboard§8] §a§lGeladen");
				} else if(args[0].equalsIgnoreCase("author")) {
					sender.sendMessage("§eEssentials coded by FutureCode");
					sender.sendMessage("Author: future_graphics");
				}
			} else {
				sender.sendMessage(plugin.prefix + "§c/essentials [reload | scoreboard | author]");
			}
		}
		return true;
	}

}
