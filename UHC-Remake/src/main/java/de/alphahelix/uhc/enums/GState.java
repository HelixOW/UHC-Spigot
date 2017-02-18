package de.alphahelix.uhc.enums;

import de.alphahelix.uhc.register.UHCFileRegister;

public enum GState {

    LOBBY, PERIOD_OF_PEACE, WARMUP, IN_GAME, DEATHMATCH_WARMUP, DEATHMATCH, END;

    private static GState currentState;

    public static String getGameStateName() {
        return UHCFileRegister.getStatusFile().getStatus(getCurrentState());
    }

    public static boolean isState(GState toCompare) {
        return toCompare == currentState;
    }

    public static GState getCurrentState() {
        return currentState;
    }

    public static void setCurrentState(GState currentState) {
        GState.currentState = currentState;
    }
}
