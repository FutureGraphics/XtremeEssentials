package essentials.future.code.Commands;

import java.util.Date;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import essentials.future.code.main.main;
import essentials.future.code.mysql.MySQLPlayer;

public class TempmuteCommand implements CommandExecutor {
	private main plugin;

	public TempmuteCommand(main plugin) {
		this.plugin = plugin;
	}

	@SuppressWarnings("deprecation")
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String arg2, String[] args) {
		if (sender instanceof Player) {
			Player player = (Player) sender;
			if (player.hasPermission("essentials.tempmute")) {
				if (args.length >= 3) {
					try {
						Player target = plugin.getServer().getPlayer(args[0]);
						String grund = " ";
						for(int i = 2; i < args.length; i++) {
							grund += args[i] + " ";
						}
						
						String uuid = target.getUniqueId().toString();
						
						if(target.isOp()) {
							player.sendMessage(plugin.prefix +"§eDu kannst keine Operator muten");
							target.sendMessage(plugin.prefix +"§c" + player.getName() + " §ehat versucht dich zu muten");
							Bukkit.getConsoleSender().sendMessage(plugin.prefix +"§c" + player.getName() + " §ehat versucht §c" + target.getName() + " §ezu muten");
						} else {
							String zeit = args[1];
							if(zeit.startsWith("s")) {
								int secunde = Integer.parseInt(zeit.replaceAll("s", ""));
								Date now = new Date();
								Date date = new Date(now.getYear(), now.getMonth(), now.getDate(), now.getHours(), now.getMinutes(), now.getSeconds() + secunde);
								MySQLPlayer.setMuted(uuid, true);
								MySQLPlayer.setMuteTime(uuid, date.getTime());
								MySQLPlayer.setMuteReason(uuid, grund);
								target.sendMessage("§8│§eDu wurdest für §c" + secunde + " §eSekunden stumm geschalten wegen:/n§8└§c" + grund);
								player.sendMessage("§8│§eDu hast §c" + target.getName() + " §efür §c" + secunde + " §eSekunden stumm geschalten für den Grund\n"
										+ "§8└§c" + grund);
							} else if(zeit.startsWith("m")) {
								int minute = Integer.parseInt(zeit.replaceAll("m", ""));
								Date now = new Date();
								Date date = new Date(now.getYear(), now.getMonth(), now.getDate(), now.getHours(), now.getMinutes() + minute, now.getSeconds());
								MySQLPlayer.setMuted(uuid, true);
								MySQLPlayer.setMuteTime(uuid, date.getTime());
								MySQLPlayer.setMuteReason(uuid, grund);
								target.sendMessage("§8│§eDu wurdest für §c" + minute + " §eMinuten stumm geschalten wegen:/n§8└§c" + grund);
								player.sendMessage("§8│§eDu hast §c" + target.getName() + " §efür §c" + minute + " §eMinuten stumm geschalten für den Grund\n"
										+ "§8└§c" + grund);
							} else if(zeit.startsWith("h")) {
								int stunde = Integer.parseInt(zeit.replaceAll("h", ""));
								Date now = new Date();
								Date date = new Date(now.getYear(), now.getMonth() , now.getDate(), now.getHours() + stunde, now.getMinutes(), now.getSeconds());
								MySQLPlayer.setMuted(uuid, true);
								MySQLPlayer.setMuteTime(uuid, date.getTime());
								MySQLPlayer.setMuteReason(uuid, grund);
								target.sendMessage("§8│§eDu wurdest für §c" + stunde + " §eStunden stumm geschalten wegen:/n§8└§c" + grund);
								player.sendMessage("§8│§eDu hast §c" + target.getName() + " §efür §c" + stunde + " §eStunden stumm geschalten für den Grund\n"
										+ "§8└§c" + grund);
							} else if(zeit.startsWith("w")) {
								int wochen = Integer.parseInt(zeit.replaceAll("w", ""));
								Date now = new Date();
								Date date = new Date(now.getYear(), now.getMonth() + wochen , now.getDate(), now.getHours(), now.getMinutes(), now.getSeconds());
								MySQLPlayer.setMuted(uuid, true);
								MySQLPlayer.setMuteTime(uuid, date.getTime());
								MySQLPlayer.setMuteReason(uuid, grund);
								target.sendMessage("§8│§eDu wurdest für §c" + wochen + " §eMonate stumm geschalten wegen:/n§8└§c" + grund);
								player.sendMessage("§8│§eDu hast §c" + target.getName() + " §efür §c" + wochen + " §eMonate stumm geschalten für den Grund\n"
										+ "§8└§c" + grund);
							} else if(zeit.startsWith("y")) {
								int jahre = Integer.parseInt(zeit.replaceAll("y", ""));
								Date now = new Date();
								Date date = new Date(now.getYear() + jahre, now.getMonth() , now.getDate(), now.getHours(), now.getMinutes(), now.getSeconds());
								MySQLPlayer.setMuted(uuid, true);
								MySQLPlayer.setMuteTime(uuid, date.getTime());
								MySQLPlayer.setMuteReason(uuid, grund);
								target.sendMessage("§8│§eDu wurdest für §c" + jahre + " §eJahre stumm geschalten wegen:/n§8└§c" + grund);
								player.sendMessage("§8│§eDu hast §c" + target.getName() + " §efür §c" + jahre + " §eJahre stumm geschalten für den Grund\n"
										+ "§8└§c" + grund);
							} else {
								player.sendMessage("§8┌▬▬▬§eMute Format§8▬▬▬┐\n"
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
					if(player.hasPermission("essentials.tempmute.noreason")) {
						try {
							Player target = plugin.getServer().getPlayer(args[0]);
							String uuid = target.getUniqueId().toString();
							if(target.isOp()) {
								player.sendMessage(plugin.prefix +"§eDu kannst keine Operator muten");
								target.sendMessage(plugin.prefix +"§c" + player.getName() + " §ehat versucht dich zu muten");
								Bukkit.getConsoleSender().sendMessage(plugin.prefix +"§c" + player.getName() + " §ehat versucht §c" + target.getName() + " §ezu muten");
							} else {
								String zeit = args[1];
								if(zeit.startsWith("s")) {
									int secunde = Integer.parseInt(zeit.replaceAll("s", ""));
									Date now = new Date();
									Date date = new Date(now.getYear(), now.getMonth(), now.getDate(), now.getHours(), now.getMinutes(), now.getSeconds() + secunde);
									MySQLPlayer.setMuted(uuid, true);
									MySQLPlayer.setMuteTime(uuid, date.getTime());
									target.sendMessage(plugin.prefix +"§eDu wurdest für §c" + secunde + " §eSekunden stumm geschalten");
									player.sendMessage(plugin.prefix +"§eDu hast §c" + target.getName() + " §efür §c" + secunde + " §eSekunden stumm geschalten");
								} else if(zeit.startsWith("m")) {
									int minute = Integer.parseInt(zeit.replaceAll("m", ""));
									Date now = new Date();
									Date date = new Date(now.getYear(), now.getMonth(), now.getDate(), now.getHours(), now.getMinutes() + minute, now.getSeconds());
									MySQLPlayer.setMuted(uuid, true);
									MySQLPlayer.setMuteTime(uuid, date.getTime());
									target.sendMessage(plugin.prefix +"§eDu wurdest für §c" + minute + " §eMinuten stumm geschalten");
									player.sendMessage(plugin.prefix +"§eDu hast §c" + target.getName() + " §efür §c" + minute + " §eMinuten stumm geschalten");
								} else if(zeit.startsWith("h")) {
									int stunde = Integer.parseInt(zeit.replaceAll("h", ""));
									Date now = new Date();
									Date date = new Date(now.getYear(), now.getMonth() , now.getDate(), now.getHours() + stunde, now.getMinutes(), now.getSeconds());
									MySQLPlayer.setMuted(uuid, true);
									MySQLPlayer.setMuteTime(uuid, date.getTime());
									target.sendMessage(plugin.prefix +"§eDu wurdest für §c" + stunde + " §eStunden stumm geschalten");
									player.sendMessage(plugin.prefix +"§eDu hast §c" + target.getName() + " §efür §c" + stunde + " §eStunden stumm geschalten");
								} else if(zeit.startsWith("w")) {
									int wochen = Integer.parseInt(zeit.replaceAll("w", ""));
									Date now = new Date();
									Date date = new Date(now.getYear(), now.getMonth() + wochen , now.getDate(), now.getHours(), now.getMinutes(), now.getSeconds());
									MySQLPlayer.setMuted(uuid, true);
									MySQLPlayer.setMuteTime(uuid, date.getTime());
									target.sendMessage(plugin.prefix +"§eDu wurdest für §c" + wochen + " §eMonate stumm geschalten");
									player.sendMessage(plugin.prefix +"§eDu hast §c" + target.getName() + " §efür §c" + wochen + " §eMonate stumm geschalten");
								} else if(zeit.startsWith("y")) {
									int jahre = Integer.parseInt(zeit.replaceAll("y", ""));
									Date now = new Date();
									Date date = new Date(now.getYear() + jahre, now.getMonth() , now.getDate(), now.getHours(), now.getMinutes(), now.getSeconds());
									MySQLPlayer.setMuted(uuid, true);
									MySQLPlayer.setMuteTime(uuid, date.getTime());
									target.sendMessage(plugin.prefix +"§eDu wurdest für §c" + jahre + " §eJahre stumm geschalten");
									player.sendMessage(plugin.prefix +"§eDu hast §c" + target.getName() + " §efür §c" + jahre + " §eJahre stumm geschalten");
								} else {
									player.sendMessage("§8┌▬▬▬§eMute Format§8▬▬▬┐\n"
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
					player.sendMessage(plugin.prefix +"§c/tempmute <Spieler> [Zeit] [Grund]");
				} else {
					player.sendMessage(plugin.prefix +"§c/tempmute <Spieler> [Zeit] [Grund]");
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
