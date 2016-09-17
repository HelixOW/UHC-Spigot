package de.alphahelix.uhc;

public enum GState {
	
	LOBBY,
	PERIOD_OF_PEACE,
	WARMUP,
	IN_GAME,
	DEATHMATCH_WARMUP,
	DEATHMATCH,
	END;
	
	private static GState currentState;
    
    public static String getGameStateName() {
    	String name = "";
    	GState gs = getCurrentState();
    	switch (gs) {
		case LOBBY:
			name = UHC.getInstance().getRegister().getStatusFile().getColorString("State.Lobby");
		case PERIOD_OF_PEACE:
			name = UHC.getInstance().getRegister().getStatusFile().getColorString("State.Period of peace");
		case WARMUP:
			name = UHC.getInstance().getRegister().getStatusFile().getColorString("State.Warmup");
		case IN_GAME:
			name = UHC.getInstance().getRegister().getStatusFile().getColorString("State.In game");
		case DEATHMATCH_WARMUP:
			name = UHC.getInstance().getRegister().getStatusFile().getColorString("State.Deathmatch warmup");
		case DEATHMATCH:
			name = UHC.getInstance().getRegister().getStatusFile().getColorString("State.Deathmatch");
		case END:
			name = UHC.getInstance().getRegister().getStatusFile().getColorString("State.End");
		}
    	return name;
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
