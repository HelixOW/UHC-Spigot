package de.alpha.uhc.aclasses;

import de.alpha.uhc.Core;
import de.alpha.uhc.GState;
import de.alpha.uhc.files.SpawnFileManager;
import de.alpha.uhc.utils.LobbyPasteUtil;
import org.bukkit.*;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.scheduler.BukkitRunnable;

import java.io.File;
import java.lang.reflect.Field;

public class AWorld {
	
	public AWorld() {
		// TODO Auto-generated constructor stub
	}

    private static boolean lobbyAsSchematic;
    private static boolean wr;
    private static String worldName;

    public static boolean isLobbyAsSchematic() {
        return lobbyAsSchematic;
    }

    public static void setLobbyAsSchematic(boolean lobbyAsSchematic) {
        AWorld.lobbyAsSchematic = lobbyAsSchematic;
    }

    public static void setWr(boolean wr) {
        AWorld.wr = wr;
    }

    private static void unloadWorld(World world) {
        if (world != null) {
            Bukkit.getServer().unloadWorld(world, false);
        }
    }

    private static void deleteWorld(File path) {
        if (path.exists()) {
            File files[] = path.listFiles();
            for (int i = 0; i < (files != null ? files.length : 0); i++) {
                if (files[i].isDirectory()) {
                    deleteWorld(files[i]);
                } else {
                    files[i].delete();
                }
            }
        }
    }

    private static void changeBiome() {
        try {
            String mojangPath = "net.minecraft.server." + Bukkit.getServer().getClass().getPackage().getName().replace(".", ",").split(",")[3];
            Class<?> clazz = Class.forName(mojangPath + ".BiomeBase");

            Field plainsField = clazz.getDeclaredField("PLAINS");
            plainsField.setAccessible(true);
            Object plainsBiome = plainsField.get(null);

            Field biomesField = clazz.getDeclaredField("biomes");
            biomesField.setAccessible(true);
            Object[] biomes = (Object[]) biomesField.get(null);

            biomes[24] = plainsBiome;
            biomes[0] = plainsBiome;
            biomes[10] = plainsBiome;
            biomesField.set(null, biomes);

        } catch (Exception ignored) {
        }
    }

    private static void createNewWorld() {

        changeBiome();

        final ConsoleCommandSender p = Bukkit.getConsoleSender();

        new BukkitRunnable() {
            @Override
            public void run() {

                if (SpawnFileManager.getSpawnFile().isConfigurationSection("Spawn")) {
                    if (SpawnFileManager.getSpawnFile().isConfigurationSection("Lobby") && SpawnFileManager.getLobbyWorldName().equals(SpawnFileManager.getSpawnWorldName())) {
                        if (!(lobbyAsSchematic && Bukkit.getPluginManager().isPluginEnabled("WorldEdit"))) {
                            p.sendMessage(Core.getInstance().getPrefix() + "§cBecause your Lobby and Spawn is in one World. The World is not resetting itself.");
                            GState.setGameState(GState.LOBBY);
                            return;
                        }
                    }
                }

                if (!(SpawnFileManager.getSpawnFile().isConfigurationSection("Spawn"))) {
                    p.sendMessage(Core.getInstance().getPrefix() + "§cYou haven't created a custom World spawnpoint.");
                    GState.setGameState(GState.LOBBY);
                    worldName = "UHC";
                } else {
                    worldName = SpawnFileManager.getSpawnWorldName();
                    if (worldName.equals("world")) {
                        if (Bukkit.getWorld("UHC") == null) {

                            WorldCreator wc = new WorldCreator("UHC");
                            wc.type(WorldType.NORMAL);
                            wc.generateStructures(false);
                            wc.createWorld();

                            new BukkitRunnable() {
                                @Override
                                public void run() {
                                    worldName = "UHC";
                                    Bukkit.getConsoleSender().sendMessage(Core.getInstance().getPrefix() + " §aUHC World loaded");

                                    SpawnFileManager.SetSpawn(0, Bukkit.getWorld(worldName).getHighestBlockAt(0, 0).getLocation().getY(), 0, Bukkit.getWorld(worldName));

                                    if (!(Bukkit.getWorld(worldName).getSpawnLocation().getChunk().isLoaded()))
                                        Bukkit.getWorld(worldName).getSpawnLocation().getChunk().load();

                                    GState.setGameState(GState.LOBBY);

                                    if (lobbyAsSchematic && Bukkit.getPluginManager().isPluginEnabled("WorldEdit")) {
                                        World w = Bukkit.getWorld("UHC");
                                        Location loc = new Location(w, 0, 200, 0, 0, 0);

                                        LobbyPasteUtil.pasteLobby(loc);
                                        SpawnFileManager.SetLobby(0, 200, 0, 0, 0, w);

                                        p.sendMessage(Core.getInstance().getPrefix() + "§aLobbyschematic pasted sucessfully");
                                    }
                                }
                            }.runTaskLater(Core.getInstance(), 20);
                        } else {
                            worldName = "UHC";
                        }
                    }

                    WorldCreator wc = new WorldCreator(worldName);
                    wc.type(WorldType.NORMAL);
                    wc.generateStructures(false);
                    wc.createWorld();

                    new BukkitRunnable() {
                        @Override
                        public void run() {
                            Bukkit.getConsoleSender().sendMessage(Core.getInstance().getPrefix() + " §aUHC World reseted");

                            SpawnFileManager.SetSpawn(0, Bukkit.getWorld(worldName).getHighestBlockAt(0, 0).getLocation().getY(), 0, Bukkit.getWorld(worldName));

                            if (!(Bukkit.getWorld(worldName).getSpawnLocation().getChunk().isLoaded()))
                                Bukkit.getWorld(worldName).getSpawnLocation().getChunk().load();

                            GState.setGameState(GState.LOBBY);
                            if (lobbyAsSchematic && Bukkit.getPluginManager().isPluginEnabled("WorldEdit")) {
                                World w = Bukkit.getWorld("UHC");
                                Location loc = new Location(w, 0, 200, 0, 0, 0);

                                LobbyPasteUtil.pasteLobby(loc);
                                SpawnFileManager.SetLobby(0, 200, 0, 0, 0, w);

                                p.sendMessage(Core.getInstance().getPrefix() + "§aLobbyschematic pasted sucessfully");
                            }
                        }
                    }.runTaskLater(Core.getInstance(), 20);
                }
            }
        }.runTaskLater(Core.getInstance(), 40);
    }

    public static void performReset() {

        if (!wr) {
            GState.setGameState(GState.LOBBY);
            return;
        }

        World arena;
        if (Bukkit.getWorld(SpawnFileManager.getSpawnWorldName()) != null) {

            arena = Bukkit.getWorld(SpawnFileManager.getSpawnWorldName());

            if (arena.getName().equals("world")) {
                if (Bukkit.getWorld("UHC") == null) {
                    arena = Bukkit.createWorld(new WorldCreator("UHC"));
                    SpawnFileManager.SetSpawn(0, arena.getHighestBlockAt(0, 0).getLocation().getY(), 0, arena);
                } else {
                    arena = Bukkit.getWorld("UHC");
                    SpawnFileManager.SetSpawn(0, arena.getHighestBlockAt(0, 0).getLocation().getY(), 0, arena);
                }
            }
        } else {
            if (Bukkit.getWorld("UHC") == null) {
                arena = Bukkit.createWorld(new WorldCreator("UHC"));
                SpawnFileManager.SetSpawn(0, arena.getHighestBlockAt(0, 0).getLocation().getY(), 0, arena);
            } else {
                arena = Bukkit.getWorld("UHC");
                SpawnFileManager.SetSpawn(0, arena.getHighestBlockAt(0, 0).getLocation().getY(), 0, arena);
            }
        }

        unloadWorld(arena);
        deleteWorld(arena.getWorldFolder());
        createNewWorld();
    }
}
