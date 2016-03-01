package de.alpha.uhc.utils;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.WorldType;
import org.bukkit.scheduler.BukkitRunnable;

import com.sk89q.worldedit.EditSession;
import com.sk89q.worldedit.MaxChangedBlocksException;
import com.sk89q.worldedit.Vector;
import com.sk89q.worldedit.bukkit.BukkitWorld;
import com.sk89q.worldedit.bukkit.WorldEditPlugin;
import com.sk89q.worldedit.schematic.MCEditSchematicFormat;
import com.sk89q.worldedit.world.DataException;

import de.alpha.uhc.Core;
import de.alpha.uhc.files.SpawnFileManager;

public class WorldUtil {
	
	public static boolean lobbySchematic;
	
	public static void unloadWorld(World world) {
	    if(!world.equals(null)) {
	        Bukkit.getServer().unloadWorld(world, true);
	    }
	}
	
	private static boolean deleteWorld(File path) {
	      if(path.exists()) {
	          File files[] = path.listFiles();
	          for(int i=0; i<files.length; i++) {
	              if(files[i].isDirectory()) {
	                  deleteWorld(files[i]);
	              } else {
	                  files[i].delete();
	              }
	          }
	      }
	      return(path.delete());
	}
	
	public static void delWorld(final File path) {
		new BukkitRunnable() {
			
			@Override
			public void run() {
				deleteWorld(path);
				
			}
		}.runTaskLater(Core.getInstance(), 20);
	}
	
	public static void createWorld() {
		
		new BukkitRunnable() {
			
			@Override
			public void run() {
				
				if(!(SpawnFileManager.getSpawnFile().isConfigurationSection("Spawn")) || !(SpawnFileManager.getSpawnFile().isConfigurationSection("Lobby"))) {
					if(lobbySchematic == false && !(Bukkit.getPluginManager().isPluginEnabled("WorldEdit"))) {
						return;
					}
				}
				if(SpawnFileManager.getSpawn() == null) return;
				if(SpawnFileManager.getSpawnWorldName().equals(SpawnFileManager.getLobbyWorldName())) {
					if(lobbySchematic == false && !(Bukkit.getPluginManager().isPluginEnabled("WorldEdit"))) {
						return;
					}
				}
				
				String name = SpawnFileManager.getSpawnWorldName();
				
				Bukkit.createWorld(new WorldCreator(name)
						.generateStructures(false)
						.type(WorldType.NORMAL));
				
				changeBiome("PLAINS");
				
				Bukkit.getWorld(name).getPopulators().add(new WorldPopulator());
				Bukkit.getConsoleSender().sendMessage("§aPlaying World reseted");
				
				if(lobbySchematic == true && Bukkit.getPluginManager().isPluginEnabled("WorldEdit")) {
					if(new File("plugins/UHC/schematics", "lobby.schematic") != null) {
					
						try {
							World w = Bukkit.getWorld(SpawnFileManager.getSpawnWorldName());
							Location loc = new Location(w, 0, 200, 0);
							
							pasteLobby(loc);
							new SpawnFileManager().SetLobby(0, 200, 0, w);
							return;
						} catch (Exception e) {
							Bukkit.getConsoleSender().sendMessage(Core.getPrefix() + "§cCouldn't load lobby.schematic inside UHC/schematics folder");
						}
					}
				}
				
			}
		}.runTaskLater(Core.getInstance(), 20*5);
	}
	
	
	
	public static void WorldReset() {
		
		if(!(SpawnFileManager.getSpawnFile().isConfigurationSection("Spawn")) || !(SpawnFileManager.getSpawnFile().isConfigurationSection("Lobby"))) {
			if(lobbySchematic == false && !(Bukkit.getPluginManager().isPluginEnabled("WorldEdit"))) {
				return;
			}
		}
		if(SpawnFileManager.getSpawn() == null) return;
		if(SpawnFileManager.getSpawnWorldName().equals(SpawnFileManager.getLobbyWorldName())) {
			if(lobbySchematic == false && !(Bukkit.getPluginManager().isPluginEnabled("WorldEdit"))) {
				return;
			}
		}
		World spawn = Bukkit.getWorld(SpawnFileManager.getSpawnWorldName());
		
		unloadWorld(spawn);
		delWorld(spawn.getWorldFolder());
		createWorld();
	}
	
	@SuppressWarnings("rawtypes")
	private static void changeBiome(String Biome){
        try {
                String mojangPath = "net.minecraft.server." + Bukkit.getServer().getClass().getPackage().getName().replace(".", ",").split(",")[3];
                Class clazz = Class.forName(mojangPath + ".BiomeBase");

                Field plainsField = clazz.getDeclaredField(Biome);
                plainsField.setAccessible(true);
                Object plainsBiome = plainsField.get(null);

                // Biomes liste auslesen
                Field biomesField = clazz.getDeclaredField("biomes");
                biomesField.setAccessible(true);
                Object[] biomes = (Object[]) biomesField.get(null);

                // Ocean auf Plains setzen
                for(int i = 0;i < biomes.length;i++){
                biomes[i] = plainsBiome;
                }
                biomesField.set(null, biomes);
                
        } catch (Exception e) {
                // Error
        }
	}
	
	@SuppressWarnings("deprecation")
	private static void pasteLobby(Location loc) {
		try {
	        WorldEditPlugin we = (WorldEditPlugin) Bukkit.getPluginManager().getPlugin("WorldEdit");
	        File schematic = new File("plugins/UHC/schematics/lobby.schematic");
			EditSession session = we.getWorldEdit().getEditSessionFactory().getEditSession(new BukkitWorld(loc.getWorld()), 999999);
	        try {
	            MCEditSchematicFormat.getFormat(schematic).load(schematic).paste(session, new Vector(0,200,0), false);
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

}
