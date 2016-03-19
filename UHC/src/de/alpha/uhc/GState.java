package de.alpha.uhc;


public enum GState {
	
	LOBBY,
	GRACE,
	INGAME,
	PREDEATHMATCH,
	DEATHMATCH,
	RESTART;
	
	private static GState currentState;
	public static String lobby;
	public static String grace;
	public static String ingame;
	public static String deathmatch;
	public static String restart;
	

	public static void setGameState(GState newGState) {
		currentState = newGState;
	}
	
	public static GState getGState() {
		return currentState;
	}
	
	public static String getGStateName() {
		
		if(isState(GState.LOBBY)) {
			return lobby;
		}
		if(isState(GState.GRACE)) {
			return grace;
		}
		if(isState(GState.INGAME)) {
			return ingame;
		}
		if(isState(DEATHMATCH) || isState(PREDEATHMATCH)) {
			return deathmatch;
		}
		if(isState(GState.RESTART)) {
			return restart;
		}
		return "";
		
	}
	
	public static boolean isState(GState toCompare) {
		return toCompare == currentState;
	}

}
