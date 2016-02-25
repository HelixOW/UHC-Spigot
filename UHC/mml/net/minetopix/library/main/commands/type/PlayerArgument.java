package net.minetopix.library.main.commands.type;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class PlayerArgument extends Argument<Player>{
	
	public Player getPlayer(String args) {
		return Bukkit.getPlayer(args);
	}
	
	@Override
	public boolean isCorrect(String arg) {
		return getPlayer(arg) != null;
	}

	@Override
	public Player get(String arg) {
		return getPlayer(arg);
	}

}
