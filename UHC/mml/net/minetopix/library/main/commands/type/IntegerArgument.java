package net.minetopix.library.main.commands.type;


public class IntegerArgument extends Argument<Integer>{
	
	public boolean isInt(String str) {
		try {
			Integer.parseInt(str);
			return true;
		} catch (Exception e) {
			return false;
		}
	}
	
	public int getInt(String arg) {
		if(isInt(arg)) {
			try {
				return Integer.parseInt(arg);
			} catch (Exception e) {
				e.printStackTrace();
				return 0;
			}
		} else {
			return 0;
		}
	}
	
	@Override
	public boolean isCorrect(String arg) {
		return isInt(arg);
	}
	
	@Override
	public Integer get(String arg) {
		return getInt(arg);
	}
	
}
