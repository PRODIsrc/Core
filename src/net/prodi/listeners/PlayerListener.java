package net.prodi.listeners;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import net.prodi.Main;

public class PlayerListener implements Listener {
	
	private Main plugin = Main.getPlugin(Main.class);
	
	@EventHandler
	public void onLogin(PlayerLoginEvent e) {
		Player p = e.getPlayer();
		String join = plugin.getConfig().getString("Messages.Join_Message");
		join = join.replace("%player%", p.getName());
		Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes('&', join));
	}
	
	@EventHandler
	public void onJoin(PlayerJoinEvent e) {
		Player p = e.getPlayer();
		e.setJoinMessage(null);
		if(plugin.getConfig().getBoolean("Settings.Enable_MOTD", true)) {
			for(String motd : plugin.getConfig().getStringList("Infos.MOTD")) {
				motd = motd.replace("%player%", p.getName());
				p.sendMessage(ChatColor.translateAlternateColorCodes('&', motd));
			}
		}
	}
	
	@EventHandler
	public void onLeave(PlayerQuitEvent e) {
		Player p = e.getPlayer();
		String leave = plugin.getConfig().getString("Messages.Leave_Message");
		leave = leave.replace("%player%", p.getName());
		e.setQuitMessage(ChatColor.translateAlternateColorCodes('&', leave));
	}

}
