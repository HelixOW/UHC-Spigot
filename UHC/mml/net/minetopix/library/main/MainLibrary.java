package net.minetopix.library.main;

import org.bukkit.Bukkit;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.java.JavaPlugin;

import net.minetopix.library.main.netty.NettyAPI;
import net.minetopix.library.main.netty.NettyListener;
import net.minetopix.library.main.netty.PacketHandler;

public class MainLibrary extends JavaPlugin implements Listener{
	
	private static ConsoleCommandSender console;
	private static MainLibrary c;
	
	@Override
	public void onEnable() {
		c = this;
		console = getServer().getConsoleSender();
		NettyAPI.initialize(getName());
		NettyAPI.addListener(new PacketListenerClass());
		for(Player p : Bukkit.getOnlinePlayers()) NettyAPI.injectPlayer(p);
	}
	
	public static MainLibrary getC() {
		return c;	
	}
	
	@Override
	public void onDisable() {
		for(Player p : Bukkit.getOnlinePlayers()) NettyAPI.uninject(p);
	}
	
	@EventHandler
	public void onJoin(PlayerJoinEvent e) {
		NettyAPI.injectPlayer(e.getPlayer());
	}
	
	@EventHandler
	public void onLeft(PlayerQuitEvent e) {
		NettyAPI.uninject(e.getPlayer());
	}
	
	public static ConsoleCommandSender getConsole() {
		return console;
	}
	
	public class PacketListenerClass extends PacketHandler {
		
		@NettyListener(packetName = "PacketPlayInUpdateSign")
		public Object onSignChange(Object packet , Player p) {
			return packet;
		}
		
	}
}
