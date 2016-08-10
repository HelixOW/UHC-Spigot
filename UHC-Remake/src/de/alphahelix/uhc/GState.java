package de.alphahelix.uhc;

public enum GState {
	
	LOBBY(1),
	PERIOD_OF_PEACE(2),
	WARMUP(3),
	IN_GAME(4),
	DEATHMATCH_WARMUP(5),
	DEATHMATCH(6),
	END(7);
	
	private static GState currentState;
    private static String lobby;
    private static String periodOfPeace;
    private static String warmup;
    private static String ingame;
    private static String deathmatchWarmup;
    private static String deathmatch;
    private static String end;
    
    private int id;
    
    GState(int count) {
    	id = count;
	}
    
    public String getGameStateName() {
    	String name = "";
    	switch (id) {
		case 1:
			name = lobby;
		case 2:
			name = periodOfPeace;
		case 3:
			name = warmup;
		case 4:
			name = ingame;
		case 5:
			name = deathmatchWarmup;
		case 6:
			name = deathmatch;
		case 7:
			name = end;
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

	public static String getLobby() {
		return lobby;
	}

	public static void setLobby(String lobby) {
		GState.lobby = lobby;
	}

	public static String getPeriodOfPeace() {
		return periodOfPeace;
	}

	public static void setPeriodOfPeace(String periodOfPeace) {
		GState.periodOfPeace = periodOfPeace;
	}

	public static String getWarmup() {
		return warmup;
	}

	public static void setWarmup(String warmup) {
		GState.warmup = warmup;
	}

	public static String getIngame() {
		return ingame;
	}

	public static void setIngame(String ingame) {
		GState.ingame = ingame;
	}

	public static String getDeathmatchWarmup() {
		return deathmatchWarmup;
	}

	public static void setDeathmatchWarmup(String deathmatchWarmup) {
		GState.deathmatchWarmup = deathmatchWarmup;
	}

	public static String getDeathmatch() {
		return deathmatch;
	}

	public static void setDeathmatch(String deathmatch) {
		GState.deathmatch = deathmatch;
	}

	public static String getEnd() {
		return end;
	}

	public static void setEnd(String end) {
		GState.end = end;
	}
}
