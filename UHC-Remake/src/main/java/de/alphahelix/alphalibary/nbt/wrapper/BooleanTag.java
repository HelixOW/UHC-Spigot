package de.alphahelix.alphalibary.nbt.wrapper;

import de.alphahelix.alphalibary.nbt.ByteTag;

public class BooleanTag extends ByteTag {

	public BooleanTag() {
		this(false);
	}

	public BooleanTag(boolean value) {
		super((byte) (value ? 1 : 0));
	}

	public BooleanTag(String name) {
		super(name);
	}

	public BooleanTag(String name, boolean value) {
		super(name, (byte) (value ? 1 : 0));
	}

	public boolean getBoolean() {
		return getValue() == 1;
	}

	public void setBoolean(boolean value) {
		setValue((byte) (value ? 1 : 0));
	}

}
