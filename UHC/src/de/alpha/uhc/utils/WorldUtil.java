package de.alpha.uhc.utils;

import java.io.File;
import java.lang.reflect.Field;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.WorldType;
import org.bukkit.scheduler.BukkitRunnable;

import de.alpha.uhc.Core;
import de.alpha.uhc.files.SpawnFileManager;

public class WorldUtil {
	
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
				
				if(!(SpawnFileManager.getSpawnFile().isConfigurationSection("Spawn")) || !(SpawnFileManager.getSpawnFile().isConfigurationSection("Lobby"))) return;
				if(SpawnFileManager.getSpawn() == null) return;
				if(SpawnFileManager.getSpawnWorldName().equals(SpawnFileManager.getLobbyWorldName())) return;
				
				String name = SpawnFileManager.getSpawnWorldName();
				
				Bukkit.createWorld(new WorldCreator(name)
						.generateStructures(false)
						.type(WorldType.NORMAL));
				
				changeBiome("PLAINS");
				
				Bukkit.getWorld(name).getPopulators().add(new WorldPopulator());
				Bukkit.getConsoleSender().sendMessage("§aPlaying World reseted");
				
			}
		}.runTaskLater(Core.getInstance(), 20*5);
	}
	
	
	
	public static void WorldReset() {
		
		if(!(SpawnFileManager.getSpawnFile().isConfigurationSection("Spawn")) || !(SpawnFileManager.getSpawnFile().isConfigurationSection("Lobby"))) return;
		if(SpawnFileManager.getSpawn() == null) return;
		if(SpawnFileManager.getSpawnWorldName().equals(SpawnFileManager.getLobbyWorldName())) return;
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
                for(int i = 0;i<biomes.length;i++){
                biomes[i] = plainsBiome;
                }
                biomesField.set(null, biomes);
                
        } catch (Exception e) {
                // Error
        }
       
        }

}
