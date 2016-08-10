package de.popokaka.alphalibary.item.data;

@SuppressWarnings("serial")
class WrongDataException extends Exception {
	public WrongDataException(ItemData data) {
		super("Exception while trying to apply the DataType: " + data.getClass().getName());
	}
	
}