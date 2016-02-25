package net.minetopix.library.main.commands.type;

public class StringArgument extends Argument<String>{

	public String getString(String arg) {
		return arg;
	}
	
	@Override
	public boolean isCorrect(String arg) {
		return true;
	}

	@Override
	public String get(String arg) {
		return getString(arg);
	}
	
}
