package de.alphahelix.uhc.instances;

import de.alphahelix.uhc.UHC;

import java.util.*;
import java.util.logging.Logger;

public class Util {

    private UHC uhc;
    private Logger log;

    public Util(UHC uhc) {
        this.uhc = uhc;
        this.log = getUhc().getLog();
    }

    public UHC getUhc() {
        return uhc;
    }

    public Logger getLog() {
        return log;
    }

    public <T> ArrayList<T> toList(T... args) {
        ArrayList<T> toReturn = new ArrayList<>();
        Collections.addAll(toReturn, args);
        return toReturn;
    }

    public double round(double value, int precision) {
        int scale = (int) Math.pow(10, precision);
        return (double) Math.round(value * scale) / scale;
    }

    public double calcMin(int sec) {
        return (sec / (3600 / 60.0));
    }

    public double calcHours(int sec) {
        return sec / 3600;
    }

    public <T, E> Set<T> getKeysByValue(Map<T, E> map, E value) {
        Set<T> keys = new HashSet<>();
        for (Map.Entry<T, E> entry : map.entrySet()) {
            if (Objects.equals(value, entry.getValue())) {
                keys.add(entry.getKey());
            }
        }
        return keys;
    }

    public <T, E> T getKeyByValue(Map<T, E> map, E value) {
        for (Map.Entry<T, E> entry : map.entrySet()) {
            if (Objects.equals(value, entry.getValue())) {
                return entry.getKey();
            }
        }
        return null;
    }
}
