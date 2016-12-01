package de.alphahelix.alphalibary.nms;

import de.alphahelix.alphalibary.reflection.ReflectionUtil;

public enum REnumPlayerInfoAction {

    ADD_PLAYER(0), UPDATE_GAME_MODE(1), UPDATE_LATENCY(2), UPDATE_DISPLAY_NAME(3), REMOVE_PLAYER(4);

    private final int index;

    REnumPlayerInfoAction(int enumIndex) {
        this.index = enumIndex;
    }

    public Object getPlayerInfoAction() {
        return ReflectionUtil.getNmsClass("PacketPlayOutPlayerInfo$EnumPlayerInfoAction").getEnumConstants()[index];
    }
}
