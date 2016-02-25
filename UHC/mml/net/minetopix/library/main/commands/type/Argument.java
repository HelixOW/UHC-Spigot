package net.minetopix.library.main.commands.type;

public abstract class Argument<T> {

	public abstract boolean isCorrect(String arg);
	
	public abstract T get(String arg);
	
}
