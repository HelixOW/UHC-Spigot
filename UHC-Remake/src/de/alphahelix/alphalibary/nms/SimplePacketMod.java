package de.alphahelix.alphalibary.nms;

import java.lang.reflect.Field;

class SimplePacketMod {

    private final Class<?> clazz;
    private final Object instance;

    public SimplePacketMod(Class<?> clazz, Object instace) {
        this.clazz = clazz;
        this.instance = instace;
    }

    public void set(String name, Object value) {

        try {

            if (hasField(name)) {
                Field f = clazz.getField(name);
                f.setAccessible(true);
                f.set(instance, value);
            }

            if (hasDeclaredField(name)) {
                Field f = clazz.getDeclaredField(name);
                f.setAccessible(true);
                f.set(instance, value);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Object get(String name) {

        try {

            if (hasField(name)) {
                Field f = clazz.getField(name);
                f.setAccessible(true);
                return f.get(instance);
            }

            if (hasDeclaredField(name)) {
                Field f = clazz.getDeclaredField(name);
                f.setAccessible(true);
                return f.get(instance);
            }

            return null;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }

    private boolean hasField(String name) {

        for (Field f : clazz.getFields()) {
            if (f.getName().equals(name)) {
                return true;
            }
        }

        return false;
    }

    private boolean hasDeclaredField(String name) {

        for (Field f : clazz.getDeclaredFields()) {
            if (f.getName().equals(name)) {
                return true;
            }
        }

        return false;

    }

    public Object getPacket() {
        return instance;
    }

}
