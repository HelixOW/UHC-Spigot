package de.alphahelix.uhc;

public enum GState {

	LOBBY, PERIOD_OF_PEACE, WARMUP, IN_GAME, DEATHMATCH_WARMUP, DEATHMATCH, END;

	private static GState currentState;

	public static String getGameStateName() {
		String name = "";
		switch (getCurrentState()) {
		case LOBBY:
			name = UHC.getInstance().getRegister().getStatusFile().getColorString("State.Lobby");
			break;
		case PERIOD_OF_PEACE:
			name = UHC.getInstance().getRegister().getStatusFile().getColorString("State.Period of peace");
			break;
		case WARMUP:
			name = UHC.getInstance().getRegister().getStatusFile().getColorString("State.Warmup");
			break;
		case IN_GAME:
			name = UHC.getInstance().getRegister().getStatusFile().getColorString("State.In game");
			break;
		case DEATHMATCH_WARMUP:
			name = UHC.getInstance().getRegister().getStatusFile().getColorString("State.Deathmatch warmup");
			break;
		case DEATHMATCH:
			name = UHC.getInstance().getRegister().getStatusFile().getColorString("State.Deathmatch");
			break;
		case END:
			name = UHC.getInstance().getRegister().getStatusFile().getColorString("State.End");
			break;
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
