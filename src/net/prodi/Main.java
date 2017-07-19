package net.prodi;

import java.util.HashMap;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import net.milkbowl.vault.economy.Economy;
import net.prodi.listeners.CustomEvents;
import net.prodi.listeners.PlayerListener;

public class Main extends JavaPlugin{
	
	public static String prefix = "§8[§cCore§8] §r";
	
	private ConfigManager cfgm;
	
	public HashMap<UUID, Integer> cdtime = new HashMap<UUID, Integer>();
	
	public int mastercd = 20;

	@Override
	public void onEnable() {
		loadConfig();
		loadConfigManager();
		setupEconomy();
		getServer().getPluginManager().registerEvents(new CustomEvents(), this);
		getServer().getPluginManager().registerEvents(new PlayerListener(), this);
		if(!setupEconomy()) {
			Bukkit.shutdown();
			Bukkit.getConsoleSender().sendMessage("§4[KRITISCHER FEHLER] §cDas Plugin §8'§6Vault§8' §cwurde nicht gefunden... Bitte installiere das Plugin um den Server optimal starten zu können!");
		}
	}
	
	@Override
	public void onDisable() {
		getServer().getConsoleSender().sendMessage("The plugin has been disabled...");
	}
	
	private boolean setupEconomy() {
        if (getServer().getPluginManager().getPlugin("Vault") == null) {
            return false;
        }
        RegisteredServiceProvider<Economy> rsp = getServer().getServicesManager().getRegistration(Economy.class);
        if (rsp == null) {
            return false;
        } else {
        	Bukkit.getConsoleSender().sendMessage("§aVault wurde gefunden!");
        	return true;
        }
    }
	
	public void loadConfig() {
		getConfig().options().copyDefaults(true);
		saveConfig();
	}
	
	public void loadConfigManager() {
		cfgm = new ConfigManager();
		cfgm.setupPlayers();
		cfgm.setupLanguage();
	}
	
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		
		Player p = (Player)sender;
		
		if(cmd.getName().equalsIgnoreCase("core")) {
			if(args.length == 0) {
				p.playSound(p.getLocation(), Sound.UI_BUTTON_CLICK, (float) 1.0, (float) 1.0);
				if(p.hasPermission("core.staff")) {
					for(String coremsg : getConfig().getStringList("Infos.Core")) {
						p.sendMessage(ChatColor.translateAlternateColorCodes('&', coremsg));
					}
				}
			} else if(args[0].equalsIgnoreCase("save")) {
				if(args.length == 1) {
					if(p.hasPermission("core.staff")) {
						reloadConfig();
						saveConfig();
						p.sendMessage(prefix+"§6Config.yml wurde §agespeichert§6!");
					}
				}
			} else if(args[0].equalsIgnoreCase("motd")) {
				if(args.length == 1) {
					if(p.hasPermission("core.staff")) {
						p.playSound(p.getLocation(), Sound.UI_BUTTON_CLICK, (float) 1.0, (float) 1.0);
						for(String motd : getConfig().getStringList("Infos.MOTD")) {
							motd = motd.replace("%player%", p.getName());
							p.sendMessage(ChatColor.translateAlternateColorCodes('&', motd));
						}
					}
				}
			} else if(args[0].equalsIgnoreCase("info")) {
				if(args.length == 1) {
					p.sendMessage("§8§m                                                 §m");
					p.sendMessage("§8➠§7 Plugin§8: §6" + getDescription().getName());
					p.sendMessage("§8➠§7 Version§8: §6" + getDescription().getVersion());
					p.sendMessage("§8➠§7 Author§8: §6" + getDescription().getAuthors().get(0));
					p.sendMessage("§8§m                                                 §m");
				}
			}
		}
		
		if(cmd.getName().equalsIgnoreCase("list")) {
			if(args.length == 0) {
				for(String sl : getConfig().getStringList("Teamlist.Staff")) {
					p.sendMessage("§b▰▰▰▰▰▰▰▰▰▰▰▰▰▰§8[§cTeamlist§8]§b▰▰▰▰▰▰▰▰▰▰▰▰▰▰");
					p.sendMessage("");
					p.sendMessage(ChatColor.translateAlternateColorCodes('&', sl));
					p.sendMessage("");
					p.sendMessage("§b▰▰▰▰▰▰▰▰▰▰▰▰▰▰▰▰▰▰▰▰▰▰▰▰▰▰▰▰▰▰▰▰▰▰▰▰▰▰");
					saveConfig();
				}
			} else if(args[0].equalsIgnoreCase("vip")) {
				if(args.length == 1) {
					for(String vl : getConfig().getStringList("Teamlist.Vip")) {
						p.sendMessage("§b▰▰▰▰▰▰▰▰▰▰▰▰▰▰▰▰§8[§cVIPlist§8]§b▰▰▰▰▰▰▰▰▰▰▰▰▰▰");
						p.sendMessage("");
						p.sendMessage(ChatColor.translateAlternateColorCodes('&', vl));
						p.sendMessage("");
						p.sendMessage("§b▰▰▰▰▰▰▰▰▰▰▰▰▰▰▰▰▰▰▰▰▰▰▰▰▰▰▰▰▰▰▰▰▰▰▰▰▰▰");
						saveConfig();
					}
				}
			}
		}
		
		
		return true;
		
	}
}
