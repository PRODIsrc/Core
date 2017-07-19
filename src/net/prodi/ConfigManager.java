package net.prodi;

import java.io.File;
import java.io.IOException;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

public class ConfigManager {

	private Main plugin = Main.getPlugin(Main.class);
	
	//Files & File Configs hier!
	public FileConfiguration playerscfg;
	public File playersfile;
	
	public FileConfiguration langcfg;
	public File langfile;
	//-------------------------
	
	public void setupPlayers() {
		if(!plugin.getDataFolder().exists()) {
			plugin.getDataFolder().mkdir();
		}
		
		playersfile = new File(plugin.getDataFolder(), "players.yml");
		
		if(!playersfile.exists()) {
			try {
				playersfile.createNewFile();
			} catch (Exception e) {
				Bukkit.getConsoleSender().sendMessage(Main.prefix+"§7Spieler Config§8: §c[FEHLER BEIM ERSTELLEN]");
			}
		}
		
		playerscfg = YamlConfiguration.loadConfiguration(playersfile);
		Bukkit.getConsoleSender().sendMessage(Main.prefix+"§7Spieler Config§8: §a[Vorhanden]");
		try {
			plugin.getConfig().save(playersfile);
			Bukkit.getConsoleSender().sendMessage(Main.prefix+"§7Spieler Config§8: §a[Gespeichert]");
		} catch (IOException e) {
			Bukkit.getConsoleSender().sendMessage(Main.prefix+"§7Spieler Config§8: §c[FEHLER BEIM SPEICHERN]");
		}
	}
	
	//////////////////////////////////////////////////////////////////////////////////////
	
	public void setupLanguage() {
		if(!plugin.getDataFolder().exists()) {
			plugin.getDataFolder().mkdir();
		}
		
		langfile = new File(plugin.getDataFolder(), "lang.yml");
		
		if(!langfile.exists()) {
			try {
				langfile.createNewFile();
			} catch (Exception e) {
				Bukkit.getConsoleSender().sendMessage(Main.prefix+"§7Language Config§8: §c[FEHLER BEIM ERSTELLEN]");
			}
		}
		
		langcfg = YamlConfiguration.loadConfiguration(langfile);
		Bukkit.getConsoleSender().sendMessage(Main.prefix+"§7Language Config§8: §a[Vorhanden]");
		langcfg.options().copyDefaults(true);
		try {
			plugin.getConfig().save(langfile);
			Bukkit.getConsoleSender().sendMessage(Main.prefix+"§7Language Config§8: §a[Gespeichert]");
		} catch (IOException e) {
			Bukkit.getConsoleSender().sendMessage(Main.prefix+"§7Language Config§8: §c[FEHLER BEIM SPEICHERN]");
		}
	}
}

