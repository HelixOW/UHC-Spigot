package de.popokaka.alphalibary.UUID;

import java.io.BufferedReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.libs.jline.internal.InputStreamReader;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mojang.util.UUIDTypeAdapter;

public class UUIDFetcher {
	
	private static HashMap<UUID, String> names = new HashMap<>();
	private static HashMap<String, UUID> uuids = new HashMap<>();
	
	private static final Gson gson = new GsonBuilder().registerTypeAdapter(UUID.class, new UUIDTypeAdapter()).create();

	private static final String UUID_URL = "https://api.mojang.com/users/profiles/minecraft/%s?at=%d";
	private static final String NAME_URL = "https://api.mojang.com/user/profiles/%s/names";

	@SuppressWarnings("deprecation")
	public static UUID getUUID(String name) {
		name = name.toLowerCase();
		
		if(uuids.containsKey(name)) return uuids.get(name);
		
		try {
			if (Bukkit.getOnlineMode()) {
				HttpURLConnection connection = (HttpURLConnection) new URL(
						String.format(UUID_URL, name, System.currentTimeMillis() / 1000)).openConnection();
				connection.setReadTimeout(5000);

				PlayerUUID player = gson.fromJson(
						new BufferedReader(new InputStreamReader(connection.getInputStream())), PlayerUUID.class);
				
				uuids.put(name, player.getId());
				
				return player.getId();
			} else {
				uuids.put(name, Bukkit.getOfflinePlayer(name).getUniqueId());
				return Bukkit.getOfflinePlayer(name).getUniqueId();
			}
		} catch (Exception e) {
			Bukkit.getConsoleSender()
					.sendMessage("Your server has no connection to the mojang servers or is runnig slowly.");
			Bukkit.getConsoleSender().sendMessage("Therefore the UUID cannot be parsed.");
			return null;
		}
	}

	public static String getName(UUID uuid) {
		
		if(names.containsKey(uuid)) return names.get(uuid);

		try {
			if (Bukkit.getOnlineMode()) {
				HttpURLConnection connection = (HttpURLConnection) new URL(
						String.format(NAME_URL, UUIDTypeAdapter.fromUUID(uuid))).openConnection();
				connection.setReadTimeout(5000);

				PlayerUUID[] allUserNames = gson.fromJson(
						new BufferedReader(new InputStreamReader(connection.getInputStream())), PlayerUUID[].class);
				PlayerUUID currentName = allUserNames[allUserNames.length - 1];
				
				names.put(uuid, currentName.getName());
				
				return currentName.getName();
			} else {
				names.put(uuid, Bukkit.getOfflinePlayer(uuid).getName());
				return Bukkit.getOfflinePlayer(uuid).getName();
			}
		} catch (Exception e) {
			Bukkit.getConsoleSender()
					.sendMessage("§cYour server has no connection to the mojang servers or is runnig slow.");
			Bukkit.getConsoleSender().sendMessage("§cTherefore the UUID cannot be parsed.");
			return null;
		}

	}

}

class PlayerUUID {

	private String name;

	public String getName() {
		return name;
	}

	public UUID getId() {
		return id;
	}

	private UUID id;

}
