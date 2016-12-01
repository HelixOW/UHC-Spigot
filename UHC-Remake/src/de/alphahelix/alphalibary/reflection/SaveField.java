package de.alphahelix.alphalibary.reflection;

import java.lang.reflect.Field;

public class SaveField {

    private Field f;

    public SaveField(Field f) {
        try {
            f.setAccessible(true);
            this.f = f;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Object get(Object instance) {
        try {
            return f.get(instance);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public Object get(Object instance, boolean stackTrace) {
        try {
            return f.get(instance);
        } catch (Exception e) {
            if (stackTrace) e.printStackTrace();
        }
        return null;
    }

    public void set(Object instance, Object value, boolean stackTrace) {
        try {
            f.set(instance, value);
        } catch (Exception e) {
            if (stackTrace) e.printStackTrace();
        }
    }
}
