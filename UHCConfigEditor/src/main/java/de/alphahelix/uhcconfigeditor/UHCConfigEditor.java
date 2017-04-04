package de.alphahelix.uhcconfigeditor;

import de.alphahelix.alphaapi.AlphaAPI;
import de.alphahelix.uhcconfigeditor.frames.MainFrame;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class UHCConfigEditor extends AlphaAPI {

    @Override
    public void onEnable() {
        new MainFrame();
    }

    public static Object[][] toArray(HashMap map) {
        Object[][] arr = new Object[map.size()][2];
        Set entries = map.entrySet();
        Iterator entriesIterator = entries.iterator();

        int i = 0;
        while (entriesIterator.hasNext()) {
            Map.Entry mapping = (Map.Entry) entriesIterator.next();

            arr[i][0] = mapping.getKey();
            arr[i][1] = mapping.getValue();

            i++;
        }

        return arr;
    }
}
