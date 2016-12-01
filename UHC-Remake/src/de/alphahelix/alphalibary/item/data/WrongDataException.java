package de.alphahelix.alphalibary.item.data;

@SuppressWarnings("serial")
public class WrongDataException extends Exception {
    public WrongDataException(ItemData data) {
        super("Exception while trying to apply the DataType: " + data.getClass().getName());
    }

}