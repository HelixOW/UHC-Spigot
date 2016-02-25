package net.minetopix.library.main.commands.type;

public class DoubleArgument extends Argument<Double>{
	
	public boolean isDouble(String str) {
		try {
			Double.parseDouble(str);
			return true;
		} catch (Exception e) {
			return false;
		}
	}
	
	public double getDouble(String arg) {
		if(isDouble(arg)) {
			try {
				return Double.parseDouble(arg);
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
		return isDouble(arg);
	}
	
	@Override
	public Double get(String arg) {
		return getDouble(arg);
	}

}
