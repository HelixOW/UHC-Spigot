package net.minetopix.library.main.commands;

import java.util.HashMap;

import net.minetopix.library.main.commands.type.Argument;

public class CommandSpec {

	private HashMap<Integer, Argument<?>> args = new HashMap<>();
	
	private String[] argsGiven = null;
	
	public CommandSpec(String[] args) {
		this.argsGiven = args;
	}
	
	public CommandSpec addArgument(Argument<?> arg) {
		args.put(args.size(), arg);
		return this;
	}
	
	public boolean isComperable() {
		
		// Argument Size
		if(this.args.size() != argsGiven.length) {
			return false;
		}
		
		for(int i = 0 ; i < argsGiven.length ; i++) {
			String currentArg = argsGiven[i];
			Argument<?> givenArgument = this.args.get(i);
			
			if(!givenArgument.isCorrect(currentArg)) {
				return false;
			}
		}
		
		return true;
	}
	
	@SuppressWarnings("unchecked")
	public <T> T getArgument(int index) {
		return ((Argument<T>) args.get(index)).get(argsGiven[index]);
	}
	
}
