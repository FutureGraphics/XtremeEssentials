package essentials.future.code.Listeners;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scoreboard.Scoreboard;

import essentials.future.code.ConfigManager.ApiManager;
import essentials.future.code.ConfigManager.ConfigManager;
import essentials.future.code.main.main;
import essentials.future.code.mysql.MySQLPlayer;

public class InteractListener implements Listener {

	private main plugin;

	public InteractListener(main plugin) {
		this.plugin = plugin;
		plugin.getServer().getPluginManager().registerEvents(this, this.plugin);
	}

	@SuppressWarnings("deprecation")
	@EventHandler
	public void onInteract(PlayerInteractEvent e) {
		Player player = e.getPlayer();
		if (e.getClickedBlock() != null) {
			if (e.getAction() == Action.RIGHT_CLICK_BLOCK) {
				if (e.getClickedBlock().getState() instanceof Sign) {
					Sign sign = (Sign) e.getClickedBlock().getState();
					if (sign.getLine(0).equals("§8[§2Free§8]")) {
						if (!sign.getLine(1).equals("") && !sign.getLine(1).equals("§cERROR")) {
							try {
								ItemStack item = null;

								if (sign.getLine(1).contains(":")) {
									String[] main = sign.getLine(1).split(":");
									int mainA = Integer.parseInt(main[0]);
									int meta = Integer.parseInt(main[1]);
									item = new ItemStack(Material.getMaterial(mainA), 1, (short) meta);
								} else {
									int main = Integer.parseInt(sign.getLine(1));
									item = new ItemStack(Material.getMaterial(main), 1);
								}
								Inventory inv = Bukkit.createInventory(player, 27, "Free");
								int i = 0;
								while (i < 27) {
									inv.setItem(i, item);
									i++;
								}
								player.openInventory(inv);
							} catch (NumberFormatException e1) {
								player.sendMessage(plugin.prefix + "§cERROR bitte Kontaktiere einen Admin");
							}
						} else {
							player.sendMessage(plugin.prefix + "§cERROR bitte Kontaktiere einen Admin");
						}
					} else if(sign.getLine(0).equals("§8[§2Sell§8]")) {
						if(!sign.getLine(1).equals("") && !sign.getLine(2).equals("") && !sign.getLine(3).equals("") || !sign.getLine(1).equals("§cERROR")) {
							try {
								ItemStack item = null;

								if (sign.getLine(1).contains(":")) {
									String[] main = sign.getLine(1).split(":");
									int mainA = Integer.parseInt(main[0]);
									int meta = Integer.parseInt(main[1]);
									item = new ItemStack(Material.getMaterial(mainA), 64, (short) meta);
								} else {
									int main = Integer.parseInt(sign.getLine(1));
									item = new ItemStack(Material.getMaterial(main), 64);
								}
								
								int amount = Integer.parseInt(sign.getLine(2));
								int coins = Integer.parseInt(sign.getLine(3));
								
								if(ApiManager.getItemAmount(item.getType(), player) >= amount) {
									ApiManager.removeItemStack(item.getType(), player, amount);
									MySQLPlayer.updateCoins(player.getUniqueId(), MySQLPlayer.getCoins(player.getUniqueId().toString())+coins, false, player.getName());
									for(Player players : Bukkit.getOnlinePlayers()) {
										if (ConfigManager.isScoreboardEnabled()) {
											Scoreboard board = ApiManager.createScoreboard(players,ConfigManager.getScorboardDisplayname(),ConfigManager.getScorboardList());
											players.setScoreboard(board);
										}
									}
									player.sendMessage(plugin.prefix + "§eDu hast erfolgreich §c" + amount + "§ex §c" + item.getType().name() + " §efür §c" + coins + " §eCoins verkauft");
								} else {
									player.sendMessage(plugin.prefix + "§cDu hast nicht genug Material");
								}
							} catch (NumberFormatException e1) {
								player.sendMessage(plugin.prefix + "§cERROR bitte Kontaktiere einen Admin");
							}
						} else {
							player.sendMessage(plugin.prefix + "§cERROR bitte Kontaktiere einen Admin");
						}
					} else if(sign.getLine(0).equals("§8[§2Buy§8]")) {
						if(!sign.getLine(1).equals("") && !sign.getLine(2).equals("") && !sign.getLine(3).equals("") || !sign.getLine(1).equals("§cERROR")) {
							try {
								ItemStack item = null;
								int amount = Integer.parseInt(sign.getLine(2));
								if (sign.getLine(1).contains(":")) {
									String[] main = sign.getLine(1).split(":");
									int mainA = Integer.parseInt(main[0]);
									int meta = Integer.parseInt(main[1]);
									item = new ItemStack(Material.getMaterial(mainA), amount, (short) meta);
								} else {
									int main = Integer.parseInt(sign.getLine(1));
									item = new ItemStack(Material.getMaterial(main), amount);
								}
								
								
								int coins = Integer.parseInt(sign.getLine(3));
								
								if(MySQLPlayer.getCoins(player.getUniqueId().toString()) >= coins) {
									if(!ApiManager.isInventoryFull(player)) {
										MySQLPlayer.updateCoins(player.getUniqueId(), coins, true, player.getName());
										for(Player players : Bukkit.getOnlinePlayers()) {
											if (ConfigManager.isScoreboardEnabled()) {
												Scoreboard board = ApiManager.createScoreboard(players,ConfigManager.getScorboardDisplayname(),ConfigManager.getScorboardList());
												players.setScoreboard(board);
											}
										}
										player.getInventory().addItem(item);
										player.sendMessage(plugin.prefix + "§eDu hast §c"+amount+"§ex §c" + item.getType().name() + " §efür §c" + coins + " §eCoins gekauft");
									} else {
										player.sendMessage(plugin.prefix + "§cDein Inventar ist voll");
									}
								} else {
									player.sendMessage(plugin.prefix + "§cDu hast nicht genügend Coins");
								}
								
							} catch(NumberFormatException n) {
								sign.setLine(1, "§cERROR");
							}
						} else {	
							player.sendMessage(plugin.prefix + "§cERROR bitte Kontaktiere einen Admin");
						}
					} else if(sign.getLine(0).equals("§8[§2Warp§8]")) {
						if(!sign.getLine(1).equals("")) {
							String warp = sign.getLine(1);
							if(plugin.getConfigManager().warpExist(warp)) {
								plugin.onPlayerTeleport(player, plugin.getConfigManager().getWarp(warp),
										plugin.prefix + "§eDu wurdest zu dem Warp-Punkt §c" + warp + " §eteleportiert");
							} else {
								player.sendMessage(plugin.prefix + "§cERROR bitte Kontaktiere einen Admin");
							}
						}
					}
				}
			}

		}
	}

}
