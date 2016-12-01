package de.alphahelix.uhc.util;

import de.alphahelix.uhc.UHC;
import de.alphahelix.uhc.instances.Util;
import org.bukkit.entity.Player;

import java.util.LinkedList;

public class LobbyUtil extends Util {

    private LinkedList<String> builders = new LinkedList<>();

    public LobbyUtil(UHC uhc) {
        super(uhc);
    }

    public void grandBuildPermission(Player p) {
        if (!builders.contains(p.getName())) builders.add(p.getName());
    }

    public void revokeBuildPermission(Player p) {
        if (builders.contains(p.getName())) builders.remove(p.getName());
    }

    public boolean hasBuildPermission(Player p) {
        return builders.contains(p.getName());
    }

}
