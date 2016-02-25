package de.alpha.uhc;


public enum GState {
	
	LOBBY,
	INGAME;
	
	private static GState currentState;

	public static void setGameState(GState newGState) {
		currentState = newGState;
	}
	
	public static GState getGState() {
		return currentState;
	}
	
	public static boolean isState(GState toCompare) {
		return toCompare == currentState;
	}

}
