package de.alphahelix.alphalibary.utils;

import com.google.gson.*;
import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import com.mojang.authlib.properties.PropertyMap;
import com.mojang.util.UUIDTypeAdapter;
import de.alphahelix.alphalibary.UUID.UUIDFetcher;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.UUID;

public class GameProfileFetcher {

    private static final String SERVICE_URL = "https://sessionserver.mojang.com/session/minecraft/profile/%s?unsigned=false";

    private static Gson gson = new GsonBuilder().disableHtmlEscaping().registerTypeAdapter(UUID.class, new UUIDTypeAdapter())
            .registerTypeAdapter(PropertyMap.class, new PropertyMap.Serializer())
            .registerTypeAdapter(GameProfile.class, new GameProfileSerelizer())
            .create();

    private static HashMap<UUID, JsonElement> cache = new HashMap<>();

    public static GameProfile getGameProfile(String name, String skinName) {

        UUID uuid = UUIDFetcher.getUUID(skinName);

        try {

            String value = UUIDTypeAdapter.fromUUID(uuid);

            HttpURLConnection connection = (HttpURLConnection) new URL(String.format(SERVICE_URL, value)).openConnection();
            connection.setReadTimeout(5000);

            if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {

                String json = new BufferedReader(new InputStreamReader(connection.getInputStream())).readLine();

                GameProfile profile = gson.fromJson(json, GameProfile.class);

                setDisplayName(profile, name);

                cache.put(profile.getId(), gson.toJsonTree(profile));

                return profile;
            } else {
                System.err.println("Cannont connect to Mojang Services");

                if (cache.containsKey(uuid)) {
                    GameProfile pr = gson.fromJson(cache.get(uuid), GameProfile.class);
                    setDisplayName(pr, name);
                    return pr;
                } else {
                    return null;
                }

            }

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }

    private static void setDisplayName(GameProfile profile, String name) {
        try {
            Field displayName = profile.getClass().getDeclaredField("name");
            displayName.setAccessible(true);
            displayName.set(profile, name);
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }
    }


    public static class GameProfileSerelizer implements JsonSerializer<GameProfile>, JsonDeserializer<GameProfile> {

        @Override
        public GameProfile deserialize(JsonElement json, Type type, JsonDeserializationContext context)
                throws JsonParseException {

            JsonObject object = (JsonObject) json;

            UUID uuid = (UUID) (object.has("id") ? context.deserialize(object.get("id"), UUID.class) : null);
            String name = object.has("name") ? object.getAsJsonPrimitive("name").getAsString() : null;

            GameProfile profile = new GameProfile(uuid, name);

            if (object.has("properties")) {

                for (Entry<String, Property> entry : ((PropertyMap) context.deserialize(object.get("properties"), PropertyMap.class)).entries()) {
                    profile.getProperties().put(entry.getKey(), entry.getValue());
                }

            }

            return profile;
        }

        @Override
        public JsonElement serialize(GameProfile profile, Type type, JsonSerializationContext context) {

            JsonObject result = new JsonObject();

            if (profile.getId() != null) result.add("id", context.serialize(profile.getId()));
            if (profile.getName() != null) result.addProperty("name", profile.getName());

            if (profile.getProperties() != null) result.add("properties", context.serialize(profile.getProperties()));

            return result;
        }


    }

}
