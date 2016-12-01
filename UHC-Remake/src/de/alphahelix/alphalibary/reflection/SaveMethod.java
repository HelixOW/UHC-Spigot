package de.alphahelix.alphalibary.reflection;

import java.lang.reflect.Method;

public class SaveMethod {

    private Method m;

    public SaveMethod(Method m) {
        try {
            m.setAccessible(true);
            this.m = m;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Object invoke(Object instance, Boolean stackTrace, Object... args) {
        try {
            return m.invoke(instance, args);
        } catch (Exception e) {
            if (stackTrace) e.printStackTrace();
        }
        return null;
    }

}
