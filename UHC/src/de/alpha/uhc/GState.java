package de.alpha.uhc;


public enum GState {
	
	LOBBY,
	GRACE,
	PREGAME,
	INGAME,
	PREDEATHMATCH,
	DEATHMATCH,
	RESTART;
	
	private static GState currentState;
	private static String lobby;
	private static String grace;
	private static String pregame;
	private static String ingame;
	private static String deathmatch;
	private static String restart;
	
	
	

	public static  void setCurrentState(GState currentState) {
		GState.currentState = currentState;
	}

	public static  void setLobby(String lobby) {
		GState.lobby = lobby;
	}

	public static  void setGrace(String grace) {
		GState.grace = grace;
	}

	public static  void setPregame(String pregame) {
		GState.pregame = pregame;
	}

	public static  void setIngame(String ingame) {
		GState.ingame = ingame;
	}

	public static  void setDeathmatch(String deathmatch) {
		GState.deathmatch = deathmatch;
	}

	public static  void setRestart(String restart) {
		GState.restart = restart;
	}

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
		if(isState(PREGAME)) {
			return pregame;
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
