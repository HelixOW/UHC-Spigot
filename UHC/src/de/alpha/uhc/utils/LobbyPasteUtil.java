package de.alpha.uhc.utils;

import java.io.File;
import java.io.IOException;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;

import com.sk89q.worldedit.EditSession;
import com.sk89q.worldedit.MaxChangedBlocksException;
import com.sk89q.worldedit.Vector;
import com.sk89q.worldedit.bukkit.BukkitWorld;
import com.sk89q.worldedit.bukkit.WorldEditPlugin;
import com.sk89q.worldedit.schematic.SchematicFormat;
import com.sk89q.worldedit.world.DataException;

import de.alpha.uhc.Core;
import de.alpha.uhc.files.SpawnFileManager;

public class LobbyPasteUtil {
	
	@SuppressWarnings("deprecation")
	public static void pasteLobby(Location loc) {
		try {
	        WorldEditPlugin we = (WorldEditPlugin) Bukkit.getPluginManager().getPlugin("WorldEdit");
	        File schematic = new File("plugins/UHC/schematics/lobby.schematic");
			EditSession session = we.getWorldEdit().getEditSessionFactory().getEditSession(new BukkitWorld(loc.getWorld()), 999999);
	        try {
	            SchematicFormat.getFormat(schematic).load(schematic).paste(session, new Vector(0,200,0), false);
	            return;
	        } catch (MaxChangedBlocksException
	                | DataException | IOException e) {
	        	Bukkit.getConsoleSender().sendMessage(Core.getPrefix() + "§cCouldn't load lobby.schematic inside UHC/schematics folder");
				return;
	        }
		} catch (Exception ignore) {
			Bukkit.getConsoleSender().sendMessage(Core.getPrefix() + "§cCouldn't load lobby.schematic inside UHC/schematics folder");
			
		}
	}
	
	
	public static void removeLobby() {
		
		if(SpawnFileManager.getLobby() == null) return;
		
		World w = Bukkit.getWorld(SpawnFileManager.getLobbyWorldName());
		Location l1 = new Location(w, -50, 155, -50);
		Location l2 = new Location(w, 50, 255, 50);
				
		for(Block b : new Cuboid(l1, l2).getBlocks()) {
			b.setType(Material.AIR);
		}
				
	}
}
