package future.code.essentials.commands;

import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import future.code.essentials.main.main;
import future.code.essentials.manager.ApiManager;
import future.code.essentials.manager.LanguageManager;

public class GamemodeCommand implements CommandExecutor {

	private main plugin;
	
	public GamemodeCommand(main plugin) {
		this.plugin = plugin;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String arg2, String[] args) {
		if(sender instanceof Player) {
			Player player = (Player) sender;
			if(player.hasPermission("essentials.gamemode")) {
				if(args.length > 1) {
					if(player.hasPermission("essentials.gamemode.others")) {
						if(!player.getName().equals(args[1])) {
							try {
								Player target = plugin.getServer().getPlayer(args[1]);
								String gamemode = args[0];
								GameMode gm = null;
								if(gamemode.equalsIgnoreCase("0") || gamemode.equalsIgnoreCase("sr") || gamemode.equalsIgnoreCase("survival")) {
									gm = GameMode.SURVIVAL;
								} else if(gamemode.equalsIgnoreCase("1") || gamemode.equalsIgnoreCase("c") || gamemode.equalsIgnoreCase("creative")) {
									gm = GameMode.CREATIVE;
								} else if(gamemode.equalsIgnoreCase("2") || gamemode.equalsIgnoreCase("a") || gamemode.equalsIgnoreCase("adventure")) {
									gm = GameMode.ADVENTURE;
								} else if(gamemode.equalsIgnoreCase("3") || gamemode.equalsIgnoreCase("s") || gamemode.equalsIgnoreCase("spectator")) {
									gm = GameMode.SPECTATOR;
								}
								
								if(gm != null) {
									target.setGameMode(gm);
									target.sendMessage(ApiManager.encryptPlayerFormat(LanguageManager.GamemodeChanged(), target));
									String message = ApiManager.encryptTargetFormat(LanguageManager.OtherGamemodeChange(), target);
									player.sendMessage(message);
								} else {
									player.sendMessage(ApiManager.encryptFormat(LanguageManager.WrongFormat()));
								}
							} catch(NullPointerException e) {
								player.sendMessage(main.playerNotFound);
							}
						} else {
							player.sendMessage(main.wrongPlayer);
						}
					}  else {
						player.sendMessage(main.noAccess);
					}
				} else if(args.length > 0) {
					String gamemode = args[0];
					GameMode gm = null;
					if(gamemode.equalsIgnoreCase("0") || gamemode.equalsIgnoreCase("sr") || gamemode.equalsIgnoreCase("survival")) {
						gm = GameMode.SURVIVAL;
					} else if(gamemode.equalsIgnoreCase("1") || gamemode.equalsIgnoreCase("c") || gamemode.equalsIgnoreCase("creative")) {
						gm = GameMode.CREATIVE;
					} else if(gamemode.equalsIgnoreCase("2") || gamemode.equalsIgnoreCase("a") || gamemode.equalsIgnoreCase("adventure")) {
						gm = GameMode.ADVENTURE;
					} else if(gamemode.equalsIgnoreCase("3") || gamemode.equalsIgnoreCase("s") || gamemode.equalsIgnoreCase("spectator")) {
						gm = GameMode.SPECTATOR;
					}
					
					if(gm != null) {
						player.setGameMode(gm);
						player.sendMessage(ApiManager.encryptPlayerFormat(LanguageManager.GamemodeChanged(), player));
					} else {
						player.sendMessage(ApiManager.encryptFormat(LanguageManager.WrongFormat()));
					}
				} else {
					player.sendMessage(main.prefix + " §e[0 survival sr, 1 creative c, 2 adventure a, 3 spectator s] < player >");
				}
			} else {
				player.sendMessage(main.noAccess);
			}
		} else {
			if(args.length > 1) {
				try {
					Player target = plugin.getServer().getPlayer(args[1]);
					String gamemode = args[0];
					GameMode gm = null;
					if(gamemode.equalsIgnoreCase("0") || gamemode.equalsIgnoreCase("sr") || gamemode.equalsIgnoreCase("survival")) {
						gm = GameMode.SURVIVAL;
					} else if(gamemode.equalsIgnoreCase("1") || gamemode.equalsIgnoreCase("c") || gamemode.equalsIgnoreCase("creative")) {
						gm = GameMode.CREATIVE;
					} else if(gamemode.equalsIgnoreCase("2") || gamemode.equalsIgnoreCase("a") || gamemode.equalsIgnoreCase("adventure")) {
						gm = GameMode.ADVENTURE;
					} else if(gamemode.equalsIgnoreCase("3") || gamemode.equalsIgnoreCase("s") || gamemode.equalsIgnoreCase("spectator")) {
						gm = GameMode.SPECTATOR;
					}
					
					if(gm != null) {
						target.setGameMode(gm);
						target.sendMessage(ApiManager.encryptPlayerFormat(LanguageManager.GamemodeChanged(), target));
						String message = ApiManager.encryptTargetFormat(LanguageManager.OtherGamemodeChange(), target);
						sender.sendMessage(message);
					} else {
						sender.sendMessage(ApiManager.encryptFormat(LanguageManager.WrongFormat()));
					}
				} catch(NullPointerException e) {
					sender.sendMessage(main.playerNotFound);
				}
			} else {
				sender.sendMessage(main.prefix + " §e[0 survival sr, 1 creative c, 2 adventure a, 3 spectator s] < player >");
			}
		}
		return true;
	}

}
