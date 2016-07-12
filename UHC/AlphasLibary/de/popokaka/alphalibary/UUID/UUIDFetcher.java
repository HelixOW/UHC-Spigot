package de.popokaka.alphalibary.UUID;

import java.io.BufferedReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.libs.jline.internal.InputStreamReader;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mojang.util.UUIDTypeAdapter;

public class UUIDFetcher {

	private static final Gson gson = new GsonBuilder().registerTypeAdapter(UUID.class, new UUIDTypeAdapter()).create();

	private static final String UUID_URL = "https://api.mojang.com/users/profiles/minecraft/%s?at=%d";
	private static final String NAME_URL = "https://api.mojang.com/user/profiles/%s/names";

	@SuppressWarnings("deprecation")
	public static UUID getUUID(String name) {
		name = name.toLowerCase();

		try {
			if (Bukkit.getOnlineMode()) {
				HttpURLConnection connection = (HttpURLConnection) new URL(
						String.format(UUID_URL, name, System.currentTimeMillis() / 1000)).openConnection();
				connection.setReadTimeout(5000);

				PlayerUUID player = gson.fromJson(
						new BufferedReader(new InputStreamReader(connection.getInputStream())), PlayerUUID.class);

				return player.getId();
			} else {
				return Bukkit.getOfflinePlayer(name).getUniqueId();
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public static String getName(UUID uuid) {

		try {
			if (Bukkit.getOnlineMode()) {
				HttpURLConnection connection = (HttpURLConnection) new URL(
						String.format(NAME_URL, UUIDTypeAdapter.fromUUID(uuid))).openConnection();
				connection.setReadTimeout(5000);
	
				PlayerUUID[] allUserNames = gson.fromJson(
						new BufferedReader(new InputStreamReader(connection.getInputStream())), PlayerUUID[].class);
				PlayerUUID currentName = allUserNames[allUserNames.length - 1];
	
				return currentName.getName();
			} else {
				return Bukkit.getOfflinePlayer(uuid).getName();
			}
		} catch (Exception e) {
			e.printStackTrace();
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
