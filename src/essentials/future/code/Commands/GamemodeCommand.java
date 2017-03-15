package essentials.future.code.Commands;

import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import essentials.future.code.main.main;

public class GamemodeCommand implements CommandExecutor {

	private main plugin;
	
	public GamemodeCommand(main plugin) {
		this.plugin = plugin;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command arg1, String arg2, String[] args) {
		if(sender instanceof Player) {
			Player player = (Player) sender;
			if(player.hasPermission("essentials.gamemode")) {
				if(args.length == 2) {
					if(player.hasPermission("essentials.gamemode.others")) {
						try {
							Player target = plugin.getServer().getPlayer(args[1]);
							GameMode gameMode = null;
							if(args[0].equalsIgnoreCase("1") || args[0].equals("c") || args[0].equals("creative")) {
								gameMode = GameMode.CREATIVE;
							} else if(args[0].equalsIgnoreCase("2") || args[0].equals("a") || args[0].equals("adventure")) {
								gameMode = GameMode.ADVENTURE;
							} else if(args[0].equalsIgnoreCase("3") || args[0].equals("spectator")) {
								gameMode = GameMode.SPECTATOR;
							} else if(args[0].equalsIgnoreCase("0") || args[0].equals("s") || args[0].equals("survival")) {
								gameMode = GameMode.SURVIVAL;
							} else if(gameMode == null){
								player.sendMessage(plugin.prefix +"§c/gamemode [Gamemode] <Spieler>");
							}
							
							if(gameMode != null) {
								target.setGameMode(gameMode);
								player.sendMessage(plugin.prefix +"§c" + target.getName() + " §ewurde in §c" + gameMode.toString() + " §egesezt");
								target.sendMessage(plugin.prefix +"§eDu wurdest in den Gamemode §c" + gameMode.toString() + " §egesetzt");
							}
						} catch (NullPointerException e) {
							player.sendMessage(plugin.playerNotFound);
						}
					} else {
						GameMode gameMode = null;
						if(args[0].equalsIgnoreCase("1") || args[0].equals("c") || args[0].equals("creative")) {
							gameMode = GameMode.CREATIVE;
						} else if(args[0].equalsIgnoreCase("2") || args[0].equals("a") || args[0].equals("adventure")) {
							gameMode = GameMode.ADVENTURE;
						} else if(args[0].equalsIgnoreCase("3") || args[0].equals("spectator")) {
							gameMode = GameMode.SPECTATOR;
						} else if(args[0].equalsIgnoreCase("0") || args[0].equals("s") || args[0].equals("survival")) {
							gameMode = GameMode.SURVIVAL;
						} else if(gameMode == null){
							player.sendMessage(plugin.prefix +"§c/gamemode [Gamemode] <Spieler>");
						}
						if(gameMode != null) {
							player.setGameMode(gameMode);
							player.sendMessage(plugin.prefix +"§eDu wurdest in den Gamemode §c" + gameMode.toString() + " §egesetzt");
						}
					}
				} else if(args.length == 1) {
					GameMode gameMode = null;
					if(args[0].equalsIgnoreCase("1") || args[0].equals("c") || args[0].equals("creative")) {
						gameMode = GameMode.CREATIVE;
					} else if(args[0].equalsIgnoreCase("2") || args[0].equals("a") || args[0].equals("adventure")) {
						gameMode = GameMode.ADVENTURE;
					} else if(args[0].equalsIgnoreCase("3") || args[0].equals("spectator")) {
						gameMode = GameMode.SPECTATOR;
					} else if(args[0].equalsIgnoreCase("0") || args[0].equals("s") || args[0].equals("survival")) {
						gameMode = GameMode.SURVIVAL;
					} else if(gameMode == null){
						player.sendMessage(plugin.prefix +"§c/gamemode [Gamemode] <Spieler>");
					}
					
					if(gameMode != null) {
						player.setGameMode(gameMode);
						player.sendMessage(plugin.prefix +"§eDu wurdest in den Gamemode §c" + gameMode.toString() + " §egesetzt");
					}
				} else {
					player.sendMessage(plugin.prefix +"§c/gamemode [Gamemode] <Spieler>");
				}
			} else {
				player.sendMessage(plugin.noAccess);
			}
		} else {
			if(args.length == 2) {
				try {
					Player target = plugin.getServer().getPlayer(args[1]);
					GameMode gameMode = null;
					if(args[0].equalsIgnoreCase("1") || args[0].equals("c") || args[0].equals("creative")) {
						gameMode = GameMode.CREATIVE;
					} else if(args[0].equalsIgnoreCase("2") || args[0].equals("a") || args[0].equals("adventure")) {
						gameMode = GameMode.ADVENTURE;
					} else if(args[0].equalsIgnoreCase("3") || args[0].equals("spectator")) {
						gameMode = GameMode.SPECTATOR;
					} else if(args[0].equalsIgnoreCase("0") || args[0].equals("s") || args[0].equals("survival")) {
						gameMode = GameMode.SURVIVAL;
					} else if(gameMode == null){
						sender.sendMessage(plugin.prefix +"§c/gamemode [Gamemode] <Spieler>");
					}
					if(gameMode != null) {
						target.setGameMode(gameMode);
						sender.sendMessage(plugin.prefix +"§c" + target.getName() + " §ewurde in §c" + gameMode.toString() + " §egesezt");
						target.sendMessage(plugin.prefix +"§eDu wurdest in den Gamemode §c" + gameMode.toString() + " §egesetzt");
					}
				} catch (NullPointerException e) {
					sender.sendMessage(plugin.playerNotFound);
				}
			} else {
				sender.sendMessage(plugin.prefix +"§c/gamemode [Gamemode] <Spieler>");
			}
		}
		return true;
	}

}
