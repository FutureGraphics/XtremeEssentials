package essentials.future.code.Commands;

import java.util.Date;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import essentials.future.code.main.main;
import essentials.future.code.mysql.MySQLPlayer;

public class TempbanCommand implements CommandExecutor{
	private main plugin;
	
	public TempbanCommand(main plugin) {
		this.plugin = plugin;
	}

	@SuppressWarnings("deprecation")
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String arg2, String[] args) {
		if (sender instanceof Player) {
			Player player = (Player) sender;
			if (player.hasPermission("essentials.tempban")) {
				if (args.length >= 3) {
					
					//Wenn es einen Grund gibt
					
					try {
						Player target = plugin.getServer().getPlayer(args[0]);
						String uuid = target.getUniqueId().toString();
						String grund = " ";
						for(int i = 2; i < args.length; i++) {
							grund += args[i] + " ";
						}
						if(target.isOp()) {
							
							//Wenn spieler op ist
							
							player.sendMessage(plugin.prefix +"§eDu kannst keine Operator bannen");
							target.sendMessage(plugin.prefix +"§c" + player.getName() + " §ehat versucht dich zu bannen");
							Bukkit.getConsoleSender().sendMessage(plugin.prefix +"§c" + player.getName() + " §ehat versucht §c" + target.getName() + " §ezu bannen");
						} else {
							
							
							String zeit = args[1];
							if(zeit.startsWith("s")) {
								int sekunden = Integer.parseInt(zeit.replaceAll("s", ""));
								Date now = new Date();
								Date date = new Date(now.getYear(), now.getMonth(), now.getDate(), now.getHours(), now.getMinutes(), now.getSeconds() + sekunden);
								MySQLPlayer.setBanned(uuid, true);
								MySQLPlayer.setBanReason(uuid, grund);
								MySQLPlayer.setBanTime(uuid, date.getTime());
								target.kickPlayer("§8│§cDu wurdest für §c§l" + sekunden + " §cSekunden gebannt für den Grund\n§8└§c"+grund);
								player.sendMessage("§8│§eDu hast §c" + target.getName() + "§efür §c" + sekunden + " §eSekunden gebannt für den Grund\n"
										+ "§8└§c" + grund);
							} else if(zeit.startsWith("m")) {
								int minuten = Integer.parseInt(zeit.replaceAll("m", ""));
								Date now = new Date();
								Date date = new Date(now.getYear(), now.getMonth(), now.getDate(), now.getHours(), now.getMinutes() + minuten, now.getSeconds());
								MySQLPlayer.setBanned(uuid, true);
								MySQLPlayer.setBanReason(uuid, grund);
								MySQLPlayer.setBanTime(uuid, date.getTime());
								target.kickPlayer("§8│§cDu wurdest für §c§l" + minuten + " §cMinuten gebannt für den Grund\n§8└§c"+grund);
								player.sendMessage("§8│§eDu hast §c" + target.getName() + "§efür §c" + minuten + " §eMinuten gebannt für den Grund\n"
										+ "§8└§c" + grund);
							} else if(zeit.startsWith("h")) {
								int stunden = Integer.parseInt(zeit.replaceAll("h", ""));
								Date now = new Date();
								Date date = new Date(now.getYear(), now.getMonth() , now.getDate(), now.getHours() + stunden, now.getMinutes(), now.getSeconds());
								MySQLPlayer.setBanned(uuid, true);
								MySQLPlayer.setBanReason(uuid, grund);
								MySQLPlayer.setBanTime(uuid, date.getTime());
								target.kickPlayer("§8│§cDu wurdest für §c§l" + stunden + " §cStunden gebannt für den Grund\n§8└§c"+grund);
								player.sendMessage("§8│§eDu hast §c" + target.getName() + "§efür §c" + stunden + " §eStunden gebannt für den Grund\n"
										+ "§8└§c" + grund);
							} else if(zeit.startsWith("w")) {
								int monate = Integer.parseInt(zeit.replaceAll("w", ""));
								Date now = new Date();
								Date date = new Date(now.getYear(), now.getMonth() + monate , now.getDate(), now.getHours(), now.getMinutes(), now.getSeconds());
								MySQLPlayer.setBanned(uuid, true);
								MySQLPlayer.setBanReason(uuid, grund);
								MySQLPlayer.setBanTime(uuid, date.getTime());
								target.kickPlayer("§8│§cDu wurdest für §c§l" + monate + " §cMonate gebannt für den Grund\n§8└§c"+grund);
								player.sendMessage("§8│§eDu hast §c" + target.getName() + "§efür §c" + monate + " §eMonate gebannt für den Grund\n"
										+ "§8└§c" + grund);
							} else if(zeit.startsWith("y")) {
								int jahre = Integer.parseInt(zeit.replaceAll("y", ""));
								Date now = new Date();
								Date date = new Date(now.getYear() + jahre, now.getMonth() , now.getDate(), now.getHours(), now.getMinutes(), now.getSeconds());
								MySQLPlayer.setBanned(uuid, true);
								MySQLPlayer.setBanReason(uuid, grund);
								MySQLPlayer.setBanTime(uuid, date.getTime());
								target.kickPlayer("§8│§cDu wurdest für §c§l" + jahre + " §cJahre gebannt für den Grund\n§8└§c"+grund);
								player.sendMessage("§8│§eDu hast §c" + target.getName() + "§efür §c" + jahre + " §eJahre gebannt für den Grund\n"
										+ "§8└§c" + grund);
							} else {
								player.sendMessage("§8┌▬▬▬§eBann Format§8▬▬▬┐\n"
								+ "§8│§es §7- Sekunde\n"
								+ "§8│§em §7- Minute\n"
								+ "§8│§eh §7- Stunde\n"
								+ "§8│§ew §7- Monate\n"
								+ "§8│§ey §7- Jahr\n"
								+ "§8└ §aBeispiel: §e/tempban TestSpieler s10 hacking\n"
								+ " ");
							}
						}
					} catch(NullPointerException e) {
						player.sendMessage(plugin.playerNotFound);
					}
				} else if(args.length == 2) {
					if(player.hasPermission("essentials.tempban.noreason")) {
						try {
							Player target = plugin.getServer().getPlayer(args[0]);
							String uuid = target.getUniqueId().toString();
							if(target.isOp()) {
								player.sendMessage(plugin.prefix +"§eDu kannst keine Operator bannen");
								target.sendMessage(plugin.prefix +"§c" + player.getName() + " §ehat versucht dich zu bannen");
								Bukkit.getConsoleSender().sendMessage(plugin.prefix +"§c" + player.getName() + " §ehat versucht §c" + target.getName() + " §ezu bannen");
							} else {
								String zeit = args[1];
								if(zeit.startsWith("s")) {
									int sekunden = Integer.parseInt(zeit.replaceAll("s", ""));
									Date now = new Date();
									Date date = new Date(now.getYear(), now.getMonth(), now.getDate(), now.getHours(), now.getMinutes(), now.getSeconds() + sekunden);
									MySQLPlayer.setBanned(uuid, true);
									MySQLPlayer.setBanTime(uuid, date.getTime());
									target.kickPlayer("§cDu wurdest für §c§l" + sekunden + " §cSekunden gebannt");
									player.sendMessage(plugin.prefix +"§eDu hast §c" + target.getName() + " §efür §c" + sekunden + " §cSekunden gebannt");
								} else if(zeit.startsWith("m")) {
									int minuten = Integer.parseInt(zeit.replaceAll("m", ""));
									Date now = new Date();
									Date date = new Date(now.getYear(), now.getMonth(), now.getDate(), now.getHours(), now.getMinutes() + minuten, now.getSeconds());
									MySQLPlayer.setBanned(uuid, true);
									MySQLPlayer.setBanTime(uuid, date.getTime());
									target.kickPlayer("§cDu wurdest für §c§l" + minuten + " §cMinuten gebannt");
									player.sendMessage(plugin.prefix +"§eDu hast §c" + target.getName() + " §efür §c" + minuten + " §cMinuten gebannt");
								} else if(zeit.startsWith("h")) {
									int stunden = Integer.parseInt(zeit.replaceAll("h", ""));
									Date now = new Date();
									Date date = new Date(now.getYear(), now.getMonth() , now.getDate(), now.getHours() + stunden, now.getMinutes(), now.getSeconds());
									MySQLPlayer.setBanned(uuid, true);
									MySQLPlayer.setBanTime(uuid, date.getTime());
									target.kickPlayer("§cDu wurdest für §c§l" + stunden + " §cStunden gebannt");
									player.sendMessage(plugin.prefix +"§eDu hast §c" + target.getName() + " §efür §c" + stunden + " §cStunden gebannt");
								} else if(zeit.startsWith("w")) {
									int monate = Integer.parseInt(zeit.replaceAll("w", ""));
									Date now = new Date();
									Date date = new Date(now.getYear(), now.getMonth() + monate , now.getDate(), now.getHours(), now.getMinutes(), now.getSeconds());
									MySQLPlayer.setBanned(uuid, true);
									MySQLPlayer.setBanTime(uuid, date.getTime());
									target.kickPlayer("§cDu wurdest für §c§l" + monate + " §cMonate gebannt");
									player.sendMessage(plugin.prefix +"§eDu hast §c" + target.getName() + " §efür §c" + monate + " §cMonate gebannt");
								} else if(zeit.startsWith("y")) {
									int jahre = Integer.parseInt(zeit.replaceAll("y", ""));
									Date now = new Date();
									Date date = new Date(now.getYear() + jahre, now.getMonth() , now.getDate(), now.getHours(), now.getMinutes(), now.getSeconds());
									MySQLPlayer.setBanned(uuid, true);
									MySQLPlayer.setBanTime(uuid, date.getTime());
									target.kickPlayer("§cDu wurdest für §c§l" + jahre + " §cJahre gebannt");
									player.sendMessage(plugin.prefix +"§eDu hast §c" + target.getName() + " §efür §c" + jahre + " §cJahre gebannt");
								} else {
									player.sendMessage("§8┌▬▬▬§eBann Format§8▬▬▬┐\n"
											+ "§8│§es §7- Sekunde\n"
											+ "§8│§em §7- Minute\n"
											+ "§8│§eh §7- Stunde\n"
											+ "§8│§ew §7- Monate\n"
											+ "§8│§ey §7- Jahr\n"
											+ "§8└ §aBeispiel: §e/tempban TestSpieler s10 hacking\n"
											+ " ");
								}
							}
						} catch(NullPointerException e) {
							player.sendMessage(plugin.playerNotFound);
						}
					} else {
						player.sendMessage(plugin.prefix +"§cGebe einen Grund an");
					}
				} else if (args.length == 0) {
					player.sendMessage(plugin.prefix +"§c/tempban <Spieler> [Zeit] [Grund]");
				} else {
					player.sendMessage(plugin.prefix +"§c/tempban <Spieler> [Zeit] [Grund]");
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
