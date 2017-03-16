package future.code.essentials.main;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import future.code.essentials.commands.EssentialsCommand;
import future.code.essentials.commands.GamemodeCommand;
import future.code.essentials.listener.JoinListener;
import future.code.essentials.manager.ApiManager;
import future.code.essentials.manager.LanguageManager;
import future.code.essentials.manager.MainManager;
import future.code.essentials.manager.PlayerManager;

public class main extends JavaPlugin{

	//Variables
	
	public static String prefix;
	public static String noAccess;
	public static String playerNotFound;
	public static String wrongPlayer;
	
	//Listener
	
	private JoinListener joinListener;
	
	//Manager
	
	private MainManager mainManager;
	private PlayerManager playerManager;
	private LanguageManager languageManager;
	
	//Commands
	
	private EssentialsCommand essentialsCommand;
	private GamemodeCommand gamemodeCommand;
	
	@Override
	public void onEnable() {
		loadManager();
		Bukkit.getConsoleSender().sendMessage("§8Essentials-§7Manager: §aOK");
		prefix = ApiManager.encryptFormat(MainManager.getPrefix());
		noAccess = LanguageManager.noAccess();
		playerNotFound = ApiManager.encryptFormat(LanguageManager.PlayerNotFound());
		wrongPlayer = ApiManager.encryptFormat(LanguageManager.WrongPlayer());
		loadListener();
		Bukkit.getConsoleSender().sendMessage("§8Essentials-§7Listener: §aOK");
		loadCommands();
		Bukkit.getConsoleSender().sendMessage("§8Essentials-§7Commands: §aOK");
		Bukkit.getConsoleSender().sendMessage("§8Essentials: §aEnabled");
	}
	
	private void loadManager() {
		mainManager = new MainManager(this);
		playerManager = new PlayerManager(this);
		languageManager = new LanguageManager(this);
	}
	
	private void loadListener() {
		joinListener = new JoinListener(this);
	}
	
	private void loadCommands() {
		gamemodeCommand = new GamemodeCommand(this);
		getCommand("gm").setExecutor(gamemodeCommand);
		
		essentialsCommand = new EssentialsCommand(this);
		getCommand("essentials").setExecutor(essentialsCommand);
	}
	
	//Geter setter
	
	public GamemodeCommand getGamemodeCommand() {
		return gamemodeCommand;
	}
	
	public LanguageManager getLanguageManager() {
		return languageManager;
	}
	
	public PlayerManager getPlayerManager() {
		return playerManager;
	}
	
	public JoinListener getJoinListener() {
		return joinListener;
	}
	
	public MainManager getMainManager() {
		return mainManager;
	}
	
}
