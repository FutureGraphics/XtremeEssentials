package essentials.future.code.main;

import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scoreboard.Scoreboard;

import essentials.future.code.Commands.BanCommand;
import essentials.future.code.Commands.DelHomeCommand;
import essentials.future.code.Commands.DelWarpCommand;
import essentials.future.code.Commands.ECOCommand;
import essentials.future.code.Commands.EnderchestCommand;
import essentials.future.code.Commands.EssentialsCommand;
import essentials.future.code.Commands.FeedCommand;
import essentials.future.code.Commands.FlyCommand;
import essentials.future.code.Commands.GamemodeCommand;
import essentials.future.code.Commands.GodCommand;
import essentials.future.code.Commands.HatCommand;
import essentials.future.code.Commands.HealCommand;
import essentials.future.code.Commands.HomeCommand;
import essentials.future.code.Commands.InvseeCommand;
import essentials.future.code.Commands.MoneyCommand;
import essentials.future.code.Commands.MsgCommand;
import essentials.future.code.Commands.MuteCommand;
import essentials.future.code.Commands.NickCommand;
import essentials.future.code.Commands.RegenerationCommand;
import essentials.future.code.Commands.RepairCommand;
import essentials.future.code.Commands.SetHomeCommand;
import essentials.future.code.Commands.SetSpawnCommand;
import essentials.future.code.Commands.SetWarpCommand;
import essentials.future.code.Commands.SkullCommand;
import essentials.future.code.Commands.SpawnCommand;
import essentials.future.code.Commands.TempbanCommand;
import essentials.future.code.Commands.TempmuteCommand;
import essentials.future.code.Commands.TestCommand;
import essentials.future.code.Commands.TpAcceptCommand;
import essentials.future.code.Commands.TpDenyCommand;
import essentials.future.code.Commands.TpHereCommand;
import essentials.future.code.Commands.TpaCommand;
import essentials.future.code.Commands.UnBanCommand;
import essentials.future.code.Commands.UnMuteCommand;
import essentials.future.code.Commands.VanishCommand;
import essentials.future.code.Commands.WarpCommand;
import essentials.future.code.Commands.WorkbenchCommand;
import essentials.future.code.ConfigManager.ApiManager;
import essentials.future.code.ConfigManager.ConfigManager;
import essentials.future.code.ConfigManager.PlayerManager;
import essentials.future.code.Listeners.InteractListener;
import essentials.future.code.Listeners.InventoryClickListener;
import essentials.future.code.Listeners.JoinListener;
import essentials.future.code.Listeners.PlayerChatListener;
import essentials.future.code.Listeners.PlayerDamageListener;
import essentials.future.code.Listeners.PlayerDeathListener;
import essentials.future.code.Listeners.PlayerMoveListener;
import essentials.future.code.Listeners.QuitListener;
import essentials.future.code.Listeners.SignChangeListener;
import essentials.future.code.mysql.MySQL;

public class main extends JavaPlugin {

	// Strings
	public String prefix = "§8|§aXZ§8 » ";
	public String noAccess = prefix + "§cKeine Berechtigung!";
	public String playerNotFound = prefix + "§cSpieler nicht gefunden";
	public String noConsole = prefix + "§cDie Console kann diesen Command nicht ausführen!";

	// Listen
	public List<Player> fightList = new ArrayList<Player>();
	public List<Player> teleportList = new ArrayList<Player>();
	public List<Player> vanishList = new ArrayList<Player>();
	public Map<Player, Player> tpaMap = new HashMap<Player, Player>();

	// Configurations Manager
	private ConfigManager configManager;
	private PlayerManager playerManager;
	// Listener
	private JoinListener joinListener;
	private QuitListener quitListener;
	private PlayerDamageListener playerDamageListener;
	private InventoryClickListener inventoryClickListener;
	private PlayerChatListener playerChatListener;
	private PlayerDeathListener playerDeathListener;
	private PlayerMoveListener playerMoveListener;
	private SignChangeListener signChangeListener;
	private InteractListener interactListener;
	
	// Commands
	private GamemodeCommand gamemodeCommand;
	private FlyCommand flyCommand;
	private BanCommand banCommand;
	private MuteCommand muteCommand;
	private SetHomeCommand setHomeCommand;
	private DelHomeCommand delHomeCommand;
	private HomeCommand homeCommand;
	private SetSpawnCommand setSpawnCommand;
	private SpawnCommand spawnCommand;
	private SetWarpCommand setWarpCommand;
	private WarpCommand warpCommand;
	private DelWarpCommand delWarpCommand;
	private TpaCommand tpaCommand;
	private TpAcceptCommand tpAcceptCommand;
	private TpDenyCommand tpDenyCommand;
	private RepairCommand repairCommand;
	private HealCommand healCommand;
	private FeedCommand feedCommand;
	private GodCommand godCommand;
	private VanishCommand vanishCommand;
	private WorkbenchCommand workbenchCommand;
	private InvseeCommand invseeCommand;
	private TempbanCommand tempbanCommand;
	private TempmuteCommand tempmuteCommand;
	private TpHereCommand tpHereCommand;
	private TestCommand testCommand;
	private MsgCommand msgCommand;
	private UnBanCommand unBanCommand;
	private UnMuteCommand unMuteCommand;
	private MoneyCommand moneyCommand;
	private ECOCommand ecoCommand;
	private SkullCommand skullCommand;
	private EssentialsCommand essentialsCommand;
	private RegenerationCommand regenerationCommand;
	private EnderchestCommand enderchestCommand;
	private HatCommand hatCommand;
	private NickCommand nickCommand;
	//private KitCommand kitCommand;
	
	@Override
	public void onEnable() {
		System.out.println("+-------------------------+");
		System.out.println("| Essentials by FutureCode|");
		System.out.println("| Statsu: Enabled         |");
		System.out.println("+-------------------------+");
		// Load
		loadConfigManger();
		MySQL.connect();
		loadMySQL();
		loadCommands();
		loadListeners();
		
		for(Player players : Bukkit.getOnlinePlayers()) {
			if (ConfigManager.isScoreboardEnabled()) {
				Scoreboard board = ApiManager.createScoreboard(players,ConfigManager.getScorboardDisplayname(),ConfigManager.getScorboardList());
				players.setScoreboard(board);
			}
		}
		
		Bukkit.getScheduler().scheduleSyncRepeatingTask(this, new Runnable() {
			
			@Override
			public void run() {
				connectionScheduler();
			}
		}, 20*300, 20*300);
	}

	public void onPlayerTeleport(final Player player, final Location loc, final String message) {
		if (!player.isOp() || !player.hasPermission("essentials.tpflag")) {
			if (this.getConfigManager().TpDelay() != 0) {
				final Location forloc = player.getLocation();
				player.sendMessage(
						"§8|§aXZ§8 » §eBitte bewege dich §c" + this.getConfigManager().TpDelay() + " §eSekunden nicht");
				Bukkit.getScheduler().scheduleSyncDelayedTask(this, new Runnable() {
					@Override
					public void run() {
						if (forloc.distance(player.getLocation()) < 0.5) {
							player.teleport(loc);
							player.sendMessage(message);
						} else {
							player.sendMessage(prefix + "§cTeleportation abgebrochen");
						}
					}
				}, 20 * this.getConfigManager().TpDelay());
			} else {
				player.teleport(loc);
				player.sendMessage(message);
			}
		} else {
			player.teleport(loc);
			player.sendMessage(message);
		}
	}

	public static void loadMySQL() {
		MySQL.connect();

		if (MySQL.isConnected()) {
			try {
				PreparedStatement ps = MySQL.getConnection().prepareStatement(
						"CREATE TABLE IF NOT EXISTS Players (UUID VARCHAR(100), "
								+ "Name VARCHAR(100),"
								+ "Baned INT(10)," 
								+ "BanTime BIGINT(100)," 
								+ "BanReason VARCHAR(100),"
								+ "Muted INT(10)," 
								+ "MuteTime BIGINT(100)," 
								+ "MuteReason VARCHAR(100),"
								+ "Coins INT(100)," 
								+ "Rang VARCHAR(100),"
								+ "Kills INT,"
								+ "Deaths INT)");
				ps.executeUpdate();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

	}

	@Override
	public void onDisable() {
		System.out.println("+-------------------------+");
		System.out.println("| Essentials by FutureCode|");
		System.out.println("| Statsu: Disabled        |");
		System.out.println("+-------------------------+");
	}

	public void loadConfigManger() {
		configManager = new ConfigManager(this);
		playerManager = new PlayerManager(this);
	}

	public void loadListeners() {
		playerDamageListener = new PlayerDamageListener(this);
		joinListener = new JoinListener(this);
		quitListener = new QuitListener(this);
		inventoryClickListener = new InventoryClickListener(this);
		playerChatListener = new PlayerChatListener(this);
		playerDeathListener = new PlayerDeathListener(this);
		playerMoveListener = new PlayerMoveListener(this);
		signChangeListener = new SignChangeListener(this);
		interactListener = new InteractListener(this);
	}

	public void loadCommands() {
		gamemodeCommand = new GamemodeCommand(this);
		getCommand("gm").setExecutor(gamemodeCommand);

		flyCommand = new FlyCommand(this);
		getCommand("fly").setExecutor(flyCommand);

		banCommand = new BanCommand(this);
		getCommand("ban").setExecutor(banCommand);

		muteCommand = new MuteCommand(this);
		getCommand("mute").setExecutor(muteCommand);

		setHomeCommand = new SetHomeCommand(this);
		getCommand("sethome").setExecutor(setHomeCommand);

		delHomeCommand = new DelHomeCommand(this);
		getCommand("delhome").setExecutor(delHomeCommand);

		homeCommand = new HomeCommand(this);
		getCommand("home").setExecutor(homeCommand);

		setSpawnCommand = new SetSpawnCommand(this);
		getCommand("setspawn").setExecutor(setSpawnCommand);

		spawnCommand = new SpawnCommand(this);
		getCommand("spawn").setExecutor(spawnCommand);

		setWarpCommand = new SetWarpCommand(this);
		getCommand("setwarp").setExecutor(setWarpCommand);

		warpCommand = new WarpCommand(this);
		getCommand("warp").setExecutor(warpCommand);

		delWarpCommand = new DelWarpCommand(this);
		getCommand("delwarp").setExecutor(delWarpCommand);

		tpaCommand = new TpaCommand(this);
		getCommand("tpa").setExecutor(tpaCommand);

		tpAcceptCommand = new TpAcceptCommand(this);
		getCommand("tpaccept").setExecutor(tpAcceptCommand);

		tpDenyCommand = new TpDenyCommand(this);
		getCommand("tpdeny").setExecutor(tpDenyCommand);

		repairCommand = new RepairCommand(this);
		getCommand("repair").setExecutor(repairCommand);

		feedCommand = new FeedCommand(this);
		getCommand("feed").setExecutor(feedCommand);

		healCommand = new HealCommand(this);
		getCommand("heal").setExecutor(healCommand);

		godCommand = new GodCommand(this);
		getCommand("god").setExecutor(godCommand);

		vanishCommand = new VanishCommand(this);
		getCommand("vanish").setExecutor(vanishCommand);

		workbenchCommand = new WorkbenchCommand(this);
		getCommand("workbench").setExecutor(workbenchCommand);

		invseeCommand = new InvseeCommand(this);
		getCommand("invsee").setExecutor(invseeCommand);

		tempbanCommand = new TempbanCommand(this);
		getCommand("tempban").setExecutor(tempbanCommand);

		tempmuteCommand = new TempmuteCommand(this);
		getCommand("tempmute").setExecutor(tempmuteCommand);

		testCommand = new TestCommand(this);
		getCommand("test").setExecutor(testCommand);

		tpHereCommand = new TpHereCommand(this);
		getCommand("tphere").setExecutor(tpHereCommand);

		msgCommand = new MsgCommand(this);
		getCommand("msg").setExecutor(msgCommand);

		unBanCommand = new UnBanCommand(this);
		getCommand("unban").setExecutor(unBanCommand);

		unMuteCommand = new UnMuteCommand(this);
		getCommand("unmute").setExecutor(unMuteCommand);

		moneyCommand = new MoneyCommand(this);
		getCommand("money").setExecutor(moneyCommand);

		ecoCommand = new ECOCommand(this);
		getCommand("eco").setExecutor(ecoCommand);
		
		skullCommand = new SkullCommand(this);
		getCommand("skull").setExecutor(skullCommand);
		
		essentialsCommand = new EssentialsCommand(this);
		getCommand("essentials").setExecutor(essentialsCommand);
		
		regenerationCommand = new RegenerationCommand(this);
		getCommand("regeneration").setExecutor(regenerationCommand);
		
		enderchestCommand = new EnderchestCommand(this);
		getCommand("enderchest").setExecutor(enderchestCommand);
		
		hatCommand = new HatCommand(this);
		getCommand("hat").setExecutor(hatCommand);
		
		nickCommand = new NickCommand(this);
		getCommand("nick").setExecutor(nickCommand);
		
	}

	public void connectionScheduler() {
        if(MySQL.con != null)
        {
            try
            {
                MySQL.con.close();
                System.out.println("Essentials: MySQL > Disconneted");
                }
       
            catch (SQLException e)
            {
                System.err.println("Essentials: MySQL > Disconnet ERROR");
                e.printStackTrace();
            }
        }
   
        Bukkit.getScheduler().scheduleSyncDelayedTask(this, new Runnable()
        {
            public void run()
            {
                try
                {
    				MySQL.con = DriverManager.getConnection(
    						"jdbc:mysql://" + MySQL.host + "/" + MySQL.database + "?" + "user=" + MySQL.username + "&password=" + MySQL.password);
                    System.out.println("Essentials: MySQL > Connected (New)");
                }
           
                catch (SQLException e)
                {
                    System.err.println("Essentials: MySQL > Connection ERROR");
                    e.printStackTrace();
                }
            }
        }, 1L);
	}

	//Geter | Setter
	
	public HatCommand getHatCommand() {
		return hatCommand;
	}
	
	public EnderchestCommand getEnderchestCommand() {
		return enderchestCommand;
	}
	
	public RegenerationCommand getRegenerationCommand() {
		return regenerationCommand;
	}
	
	public EssentialsCommand getEssentialsCommand() {
		return essentialsCommand;
	}
	
	public SkullCommand getSkullCommand() {
		return skullCommand;
	}
	
	public InteractListener getInteractListener() {
		return interactListener;
	}
	
	public SignChangeListener getSignChangeListener() {
		return signChangeListener;
	}
	
	public ECOCommand getEcoCommand() {
		return ecoCommand;
	}

	public MoneyCommand getMoneyCommand() {
		return moneyCommand;
	}

	public PlayerManager getPlayerManager() {
		return playerManager;
	}

	public UnMuteCommand getUnMuteCommand() {
		return unMuteCommand;
	}

	public UnBanCommand getUnBanCommand() {
		return unBanCommand;
	}

	public MsgCommand getMsgCommand() {
		return msgCommand;
	}

	public PlayerMoveListener getPlayerMoveListener() {
		return playerMoveListener;
	}

	public TpHereCommand getTpHereCommand() {
		return tpHereCommand;
	}

	public PlayerDeathListener getPlayerDeathListener() {
		return playerDeathListener;
	}

	public TestCommand getTestCommand() {
		return testCommand;
	}

	public TempmuteCommand getTempmuteCommand() {
		return tempmuteCommand;
	}

	public InvseeCommand getInvseeCommand() {
		return invseeCommand;
	}

	public TempbanCommand getTempbanCommand() {
		return tempbanCommand;
	}

	public PlayerChatListener getPlayerChatListener() {
		return playerChatListener;
	}

	public InventoryClickListener getInventoryClickListener() {
		return inventoryClickListener;
	}

	public PlayerDamageListener getPlayerDamageListener() {
		return playerDamageListener;
	}

	public WorkbenchCommand getWorkbenchCommand() {
		return workbenchCommand;
	}

	public VanishCommand getVanishCommand() {
		return vanishCommand;
	}

	public GodCommand getGodCommand() {
		return godCommand;
	}

	public HealCommand getHealCommand() {
		return healCommand;
	}

	public FeedCommand getFeedCommand() {
		return feedCommand;
	}

	public RepairCommand getRepairCommand() {
		return repairCommand;
	}

	public TpAcceptCommand getTpAcceptCommand() {
		return tpAcceptCommand;
	}

	public TpaCommand getTpaCommand() {
		return tpaCommand;
	}

	public TpDenyCommand getTpDenyCommand() {
		return tpDenyCommand;
	}

	public WarpCommand getWarpCommand() {
		return warpCommand;
	}

	public SetWarpCommand getSetWarpCommand() {
		return setWarpCommand;
	}

	public DelWarpCommand getDelWarpCommand() {
		return delWarpCommand;
	}

	public SpawnCommand getSpawnCommand() {
		return spawnCommand;
	}

	public SetSpawnCommand getSetSpawnCommand() {
		return setSpawnCommand;
	}

	public HomeCommand getHomeCommand() {
		return homeCommand;
	}

	public DelHomeCommand getDelHomeCommand() {
		return delHomeCommand;
	}

	public SetHomeCommand getSetHomeCommand() {
		return setHomeCommand;
	}

	public MuteCommand getMuteCommand() {
		return muteCommand;
	}

	public BanCommand getBanCommand() {
		return banCommand;
	}

	public FlyCommand getFlyCommand() {
		return flyCommand;
	}

	public GamemodeCommand getGamemodeCommand() {
		return gamemodeCommand;
	}

	public QuitListener getQuitListener() {
		return quitListener;
	}

	public JoinListener getJoinListener() {
		return joinListener;
	}

	public ConfigManager getConfigManager() {
		return configManager;
	}


}
