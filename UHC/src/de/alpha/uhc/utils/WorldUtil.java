package de.alpha.uhc.utils;

import java.io.File;
import java.lang.reflect.Field;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.WorldType;
import org.bukkit.scheduler.BukkitRunnable;

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
		
		changeBiome("PLAINS");
		
		new BukkitRunnable() {
			
			@Override
			public void run() {
				
				if(!(Bukkit.getPluginManager().isPluginEnabled("WorldEdit")) && lobbySchematic == true) {
					Bukkit.getConsoleSender().sendMessage(Core.getPrefix() + "§cWorldEdit hasn't been loaded!");
				}
				if(!(SpawnFileManager.getSpawnFile().isConfigurationSection("Spawn")) || !(SpawnFileManager.getSpawnFile().isConfigurationSection("Lobby"))) return;
				if(SpawnFileManager.getSpawnWorldName() == null) return;
				if(SpawnFileManager.getSpawnWorldName().equals(SpawnFileManager.getLobbyWorldName())) {
					if(lobbySchematic == false && !(Bukkit.getPluginManager().isPluginEnabled("WorldEdit"))) {
						return;
					}
				}
				
				String name = SpawnFileManager.getSpawnWorldName();
				if(name.equals("world")) {
					if(Bukkit.getWorld("UHC") == null) {
						Bukkit.createWorld(new WorldCreator("UHC")
								.generateStructures(false)
								.type(WorldType.NORMAL));
						
						Bukkit.getWorld("UHC").getPopulators().add(new WorldPopulator());
						Bukkit.getConsoleSender().sendMessage(Core.getPrefix() + "§aPlaying World loaded");
						if(lobbySchematic == true && Bukkit.getPluginManager().isPluginEnabled("WorldEdit")) {
							if(new File("plugins/UHC/schematics", "lobby.schematic") != null) {
								try {
									World w = Bukkit.getWorld("UHC");
									Location loc = new Location(w, 0, 200, 0, 0, 0);
									
									LobbyPasteUtil.pasteLobby(loc);
									SpawnFileManager.SetLobby(0, 200, 0, 0 , 0, w);
									return;
								} catch (Exception e) {
									Bukkit.getConsoleSender().sendMessage(Core.getPrefix() + "§cCouldn't load lobby.schematic inside UHC/schematics folder");
									return;
								}
							}
						} else {
							return;
						}
					} else {
						name = "UHC";
					}
				}
				
				Bukkit.createWorld(new WorldCreator(name)
						.generateStructures(false)
						.type(WorldType.NORMAL));
				
				Bukkit.getWorld(name).getPopulators().add(new WorldPopulator());
				Bukkit.getConsoleSender().sendMessage(Core.getPrefix() + "§aPlaying World reseted");
				
				if(lobbySchematic == true && Bukkit.getPluginManager().isPluginEnabled("WorldEdit")) {
					if(new File("plugins/UHC/schematics", "lobby.schematic") != null) {
					
						try {
							World w = Bukkit.getWorld(SpawnFileManager.getSpawnWorldName());
							Location loc = new Location(w, 0, 200, 0, 0, 0);
							
							LobbyPasteUtil.pasteLobby(loc);
							SpawnFileManager.SetLobby(0, 200, 0, 0, 0, w);
							return;
						} catch (Exception e) {
							Bukkit.getConsoleSender().sendMessage(Core.getPrefix() + "§cCouldn't load lobby.schematic inside UHC/schematics folder");
						}
					}
				}
				
			}
		}.runTaskLater(Core.getInstance(), 40);
	}
	
	private static World spawn;
	
	public static void WorldReset() {
		
		if(!(SpawnFileManager.getSpawnFile().isConfigurationSection("Spawn"))) return;
		if(SpawnFileManager.getSpawnWorldName() == null) return;
		if(SpawnFileManager.getSpawnFile().isConfigurationSection("Lobby")) {
			if(SpawnFileManager.getSpawnWorldName().equals(SpawnFileManager.getLobbyWorldName())) {
				if(lobbySchematic == false && !(Bukkit.getPluginManager().isPluginEnabled("WorldEdit"))) {
					return;
				}
			}
		}
		
		if(Bukkit.getWorld(SpawnFileManager.getSpawnWorldName()) != null) {
			spawn = Bukkit.getWorld(SpawnFileManager.getSpawnWorldName());
			if(spawn.getName().equals("world")) {
				if(Bukkit.getWorld("UHC") == null) {
					spawn = Bukkit.createWorld(new WorldCreator("UHC"));
					SpawnFileManager.SetSpawn(0, 200, 0, spawn);
				} else {
					spawn = Bukkit.getWorld("UHC");
					SpawnFileManager.SetSpawn(0, 200, 0, spawn);
				}
			}
		} else {
			if(Bukkit.getWorld("UHC") == null) {
				spawn = Bukkit.createWorld(new WorldCreator("UHC"));
				SpawnFileManager.SetSpawn(0, 200, 0, spawn);
			} else {
				spawn = Bukkit.getWorld("UHC");
				SpawnFileManager.SetSpawn(0, 200, 0, spawn);
			}
		}
			
		unloadWorld(spawn);
		delWorld(spawn.getWorldFolder());
		createWorld();
	}
	
	private static void changeBiome(String Biome){
        try {
                String mojangPath = "net.minecraft.server." + Bukkit.getServer().getClass().getPackage().getName().replace(".", ",").split(",")[3];
                Class<?> clazz = Class.forName(mojangPath + ".BiomeBase");

                Field plainsField = clazz.getDeclaredField(Biome);
                plainsField.setAccessible(true);
                Object plainsBiome = plainsField.get(null);

                // Biomes liste auslesen
                Field biomesField = clazz.getDeclaredField("biomes");
                biomesField.setAccessible(true);
                Object[] biomes = (Object[]) biomesField.get(null);

                // Ocean auf Plains setzen
                biomes[24] = plainsBiome;
                biomes[0] = plainsBiome;
                biomesField.set(null, biomes);
                
        } catch (Exception e) {
                // Error
        }
	}
}
