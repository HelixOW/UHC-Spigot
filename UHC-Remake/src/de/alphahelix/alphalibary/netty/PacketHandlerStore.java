package de.alphahelix.alphalibary.netty;

import java.util.ArrayList;

public class PacketHandlerStore {

    private ArrayList<PacketHandler> allHandler = new ArrayList<>();

    public void addHandler(PacketHandler handler) {

        if (!allHandler.contains(handler)) {
            allHandler.add(handler);
        }

    }

    public void removeHandler(Class<?> clazz) {
        for (int i = 0; i < allHandler.size(); i++) {
            if (clazz.isAssignableFrom(allHandler.get(i).getClass())) {
                allHandler.remove(i);
            }
        }
    }

    public ArrayList<PacketHandler> getAllHandler() {
        return allHandler;
    }

}
