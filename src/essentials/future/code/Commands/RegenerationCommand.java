package essentials.future.code.Commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import essentials.future.code.main.main;

public class RegenerationCommand implements CommandExecutor {

	private main plugin;
	
	public RegenerationCommand(main plugin) {
		this.plugin = plugin;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command arg1, String arg2, String[] args) {
		if(sender instanceof Player) {
			Player player = (Player) sender;
			if(player.hasPermission("essentials.regeneration")) {
				if(args.length == 1) {
					if(player.hasPermission("essentials.regeneration")) {
						try {
							Player target = plugin.getServer().getPlayer(args[0]);
							target.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 20*180, 2));
							target.sendMessage(plugin.prefix + "§eRegeneration für §c3§e Minuten bekommen");
							player.sendMessage(plugin.prefix + "§c" + target.getName() + "§eRegeneration für §c3§e Minuten gegeben");
						} catch(NullPointerException e) {
							player.sendMessage(plugin.playerNotFound);
						}
					} else {
						player.sendMessage(plugin.noAccess);
					}
				} else if(args.length == 0) {
					player.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 20*180, 2));
					player.sendMessage(plugin.prefix + "§eRegeneration für §c3§e Minuten bekommen");
				} else {
					player.sendMessage(plugin.prefix + "§c/regeneration <Spieler>");
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
