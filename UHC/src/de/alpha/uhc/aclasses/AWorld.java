package de.alpha.uhc.aclasses;

import java.io.File;
import java.lang.reflect.Field;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.WorldType;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.scheduler.BukkitRunnable;

import de.alpha.uhc.Core;
import de.alpha.uhc.GState;
import de.alpha.uhc.Registery;

public class AWorld {
	
	private Core pl;
	private Registery r;
	
	public AWorld(Core c) {
		this.pl = c;
		this.r = pl.getRegistery();
	}

    private boolean lobbyAsSchematic;
    private boolean wr;
    private String worldName;

    public boolean isLobbyAsSchematic() {
        return lobbyAsSchematic;
    }

    public void setLobbyAsSchematic(boolean lobbyAsSchematic) {
        this.lobbyAsSchematic = lobbyAsSchematic;
    }

    public void setWr(boolean wr) {
    	this.wr = wr;
    }

    private void unloadWorld(World world) {
        if (world != null) {
            Bukkit.getServer().unloadWorld(world, false);
        }
    }

    private void deleteWorld(File path) {
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

    private void changeBiome() {
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

    private void createNewWorld() {

        changeBiome();

        final ConsoleCommandSender p = Bukkit.getConsoleSender();

        new BukkitRunnable() {
            @Override
            public void run() {

                if (r.getSpawnFileManager().getSpawnFile().isConfigurationSection("Spawn")) {
                    if (r.getSpawnFileManager().getSpawnFile().isConfigurationSection("Lobby") && r.getSpawnFileManager().getLobbyWorldName().equals(r.getSpawnFileManager().getSpawnWorldName())) {
                        if (!(lobbyAsSchematic && Bukkit.getPluginManager().isPluginEnabled("WorldEdit"))) {
                            p.sendMessage(Core.getInstance().getPrefix() + "§cBecause your Lobby and Spawn is in one World. The World is not resetting itself.");
                            GState.setGameState(GState.LOBBY);
                            return;
                        }
                    }
                }

                if (!(r.getSpawnFileManager().getSpawnFile().isConfigurationSection("Spawn"))) {
                    p.sendMessage(Core.getInstance().getPrefix() + "§cYou haven't created a custom World spawnpoint.");
                    GState.setGameState(GState.LOBBY);
                    worldName = "UHC";
                } else {
                    worldName = r.getSpawnFileManager().getSpawnWorldName();
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

                                    r.getSpawnFileManager().SetSpawn(0, Bukkit.getWorld(worldName).getHighestBlockAt(0, 0).getLocation().getY(), 0, Bukkit.getWorld(worldName));

                                    if (!(Bukkit.getWorld(worldName).getSpawnLocation().getChunk().isLoaded()))
                                        Bukkit.getWorld(worldName).getSpawnLocation().getChunk().load();

                                    GState.setGameState(GState.LOBBY);

                                    if (lobbyAsSchematic && Bukkit.getPluginManager().isPluginEnabled("WorldEdit")) {
                                        World w = Bukkit.getWorld("UHC");
                                        Location loc = new Location(w, 0, 200, 0, 0, 0);

                                        pl.getRegistery().getLobbyPasteUtil().pasteLobby(loc);
                                        r.getSpawnFileManager().SetLobby(0, 200, 0, 0, 0, w);

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

                            r.getSpawnFileManager().SetSpawn(0, Bukkit.getWorld(worldName).getHighestBlockAt(0, 0).getLocation().getY(), 0, Bukkit.getWorld(worldName));

                            if (!(Bukkit.getWorld(worldName).getSpawnLocation().getChunk().isLoaded()))
                                Bukkit.getWorld(worldName).getSpawnLocation().getChunk().load();

                            GState.setGameState(GState.LOBBY);
                            if (lobbyAsSchematic && Bukkit.getPluginManager().isPluginEnabled("WorldEdit")) {
                                World w = Bukkit.getWorld("UHC");
                                Location loc = new Location(w, 0, 200, 0, 0, 0);

                                pl.getRegistery().getLobbyPasteUtil().pasteLobby(loc);
                                r.getSpawnFileManager().SetLobby(0, 200, 0, 0, 0, w);

                                p.sendMessage(Core.getInstance().getPrefix() + "§aLobbyschematic pasted sucessfully");
                            }
                        }
                    }.runTaskLater(Core.getInstance(), 20);
                }
            }
        }.runTaskLater(Core.getInstance(), 40);
    }

    public void performReset() {

        if (!wr) {
            GState.setGameState(GState.LOBBY);
            return;
        }

        World arena;
        if (Bukkit.getWorld(r.getSpawnFileManager().getSpawnWorldName()) != null) {

            arena = Bukkit.getWorld(r.getSpawnFileManager().getSpawnWorldName());

            if (arena.getName().equals("world")) {
                if (Bukkit.getWorld("UHC") == null) {
                    arena = Bukkit.createWorld(new WorldCreator("UHC"));
                    r.getSpawnFileManager().SetSpawn(0, arena.getHighestBlockAt(0, 0).getLocation().getY(), 0, arena);
                } else {
                    arena = Bukkit.getWorld("UHC");
                    r.getSpawnFileManager().SetSpawn(0, arena.getHighestBlockAt(0, 0).getLocation().getY(), 0, arena);
                }
            }
        } else {
            if (Bukkit.getWorld("UHC") == null) {
                arena = Bukkit.createWorld(new WorldCreator("UHC"));
                r.getSpawnFileManager().SetSpawn(0, arena.getHighestBlockAt(0, 0).getLocation().getY(), 0, arena);
            } else {
                arena = Bukkit.getWorld("UHC");
                r.getSpawnFileManager().SetSpawn(0, arena.getHighestBlockAt(0, 0).getLocation().getY(), 0, arena);
            }
        }

        unloadWorld(arena);
        deleteWorld(arena.getWorldFolder());
        createNewWorld();
    }
}
