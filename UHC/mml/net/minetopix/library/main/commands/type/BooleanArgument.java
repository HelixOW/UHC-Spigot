package net.minetopix.library.main.commands.type;

import java.util.ArrayList;

public class BooleanArgument extends Argument<Boolean>{

	private ArrayList<String> trueString = new ArrayList<String>();
	private ArrayList<String> falseString = new ArrayList<String>();
	
	public BooleanArgument() {
		//####### Add default booleans
		addBooleans("true","false");
		addBooleans("yes", "no");
		addBooleans("1", "0");
		addBooleans("ja","nein");
		addBooleans("y", "n");
	}
	
	public boolean isBoolean(String arg) {
		return trueString.contains(arg.toLowerCase()) || falseString.contains(arg.toLowerCase());
	}
	
	public void addBooleans(String newTrueString, String newFalseString) {
		if(!trueString.contains(newTrueString.toLowerCase())) {
			trueString.add(newTrueString.toLowerCase());
		}
		if(!falseString.contains(newFalseString.toLowerCase())) {
			falseString.add(newFalseString.toLowerCase());
		}
	}
	
	public boolean getBoolean(String arg) {
		if(!isBoolean(arg)) {
			return trueString.contains(arg);
		}
		return false;
	}
	
	@Override
	public boolean isCorrect(String arg) {
		return isBoolean(arg);
	}

	@Override
	public Boolean get(String arg) {
		return getBoolean(arg);
	}

	
}
