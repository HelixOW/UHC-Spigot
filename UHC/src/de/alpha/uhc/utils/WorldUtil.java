package de.alpha.uhc.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.Map;
import java.util.zip.GZIPInputStream;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.WorldType;
import org.bukkit.block.Block;
import org.bukkit.scheduler.BukkitRunnable;
import org.jnbt.ByteArrayTag;
import org.jnbt.CompoundTag;
import org.jnbt.NBTInputStream;
import org.jnbt.ShortTag;
import org.jnbt.StringTag;
import org.jnbt.Tag;

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
					if(lobbySchematic == false) {
						return;
					}
				}
				if(SpawnFileManager.getSpawn() == null) return;
				if(SpawnFileManager.getSpawnWorldName().equals(SpawnFileManager.getLobbyWorldName())) {
					if(lobbySchematic == false) {
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
				
				if(lobbySchematic == true) {
					if(new File("plugins/UHC/schematics", "lobby.gz") != null) {
					
						try {
							StructureAPI schematic = loadSchematic(new File("plugins/UHC/schematics", "lobby.gz"));
							
							World w = Bukkit.getWorld(SpawnFileManager.getSpawnWorldName());
							Location loc = new Location(w, 0, 200, 0);
							
							pasteSchematic(w, loc, schematic);
							new SpawnFileManager().SetLobby(0, 200, 0, w);
							return;
						} catch (IOException e) {
							Bukkit.getConsoleSender().sendMessage(Core.getPrefix() + "§cCouldn't load lobby.gz inside UHC/schematics folder");
							e.printStackTrace();
						}
					}
				}
				
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
                for(int i = 0;i < biomes.length;i++){
                biomes[i] = plainsBiome;
                }
                biomesField.set(null, biomes);
                
        } catch (Exception e) {
                // Error
        }
       
	}
	
	
	
	@SuppressWarnings("deprecation")
	public static void pasteSchematic(World world, Location loc, StructureAPI schematic)
    {
        byte[] blocks = schematic.getBlocks();
        byte[] blockData = schematic.getData();
 
        short length = schematic.getLenght();
        short width = schematic.getWidth();
        short height = schematic.getHeight();
 
        for (int x = 0; x < width; ++x) {
            for (int y = 0; y < height; ++y) {
                for (int z = 0; z < length; ++z) {
                    int index = y * width * length + z * width + x;
                    Block block = new Location(world, x + loc.getX(), y + loc.getY(), z + loc.getZ()).getBlock();
                    block.setTypeIdAndData(blocks[index], blockData[index], true);
                }
            }
        }
    }
 
    @SuppressWarnings("resource")
	public static StructureAPI loadSchematic(File file) throws IOException
    {
        FileInputStream stream = new FileInputStream(file);
        NBTInputStream nbtStream = new NBTInputStream(new GZIPInputStream(stream));
 
        CompoundTag schematicTag = (CompoundTag) nbtStream.readTag();
        if (!schematicTag.getName().equals("Schematic")) {
            throw new IllegalArgumentException("Tag \"Schematic\" does not exist or is not first");
        }
 
        Map<String, Tag> schematic = schematicTag.getValue();
        if (!schematic.containsKey("Blocks")) {
            throw new IllegalArgumentException("Schematic file is missing a \"Blocks\" tag");
        }
 
        short width = getChildTag(schematic, "Width", ShortTag.class).getValue();
        short length = getChildTag(schematic, "Length", ShortTag.class).getValue();
        short height = getChildTag(schematic, "Height", ShortTag.class).getValue();
 
        String materials = getChildTag(schematic, "Materials", StringTag.class).getValue();
        if (!materials.equals("Alpha")) {
            throw new IllegalArgumentException("Schematic file is not an Alpha schematic");
        }
 
        byte[] blocks = getChildTag(schematic, "Blocks", ByteArrayTag.class).getValue();
        byte[] blockData = getChildTag(schematic, "Data", ByteArrayTag.class).getValue();
        return new StructureAPI(blocks, blockData, width, length, height);
    }
 
    /**
    * Get child tag of a NBT structure.
    *
    * @param items The parent tag map
    * @param key The name of the tag to get
    * @param expected The expected type of the tag
    * @return child tag casted to the expected type
    * @throws DataException if the tag does not exist or the tag is not of the
    * expected type
    */
    private static <T extends Tag> T getChildTag(Map<String, Tag> items, String key, Class<T> expected) throws IllegalArgumentException
    {
        if (!items.containsKey(key)) {
            throw new IllegalArgumentException("Schematic file is missing a \"" + key + "\" tag");
        }
        Tag tag = items.get(key);
        if (!expected.isInstance(tag)) {
            throw new IllegalArgumentException(key + " tag is not of tag type " + expected.getName());
        }
        return expected.cast(tag);
    }

}
