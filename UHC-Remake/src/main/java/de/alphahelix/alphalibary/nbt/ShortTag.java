package de.alphahelix.alphalibary.nbt;

import com.google.gson.JsonPrimitive;
import de.alphahelix.alphalibary.nbt.stream.NBTInputStream;
import de.alphahelix.alphalibary.nbt.stream.NBTOutputStream;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class ShortTag extends NumberTag<Short> {

	private short value;

	public ShortTag() {
		this((short) 0);
	}

	public ShortTag(short value) {
		super("");
		this.value = value;
	}

	public ShortTag(String name) {
		super(name);
	}

	public ShortTag(String name, short value) {
		super(name);
		this.value = value;
	}

	@Override
	public Short getValue() {
		return value;
	}

	@Override
	public void setValue(Short aShort) {
		this.value = aShort;
	}

	@Override
	public JsonPrimitive asJson() {
		return new JsonPrimitive(value);
	}

	@Override
	public void read(NBTInputStream nbtIn, DataInputStream in, int depth) throws IOException {
		value = in.readShort();
	}

	@Override
	public void write(NBTOutputStream nbtOut, DataOutputStream out) throws IOException {
		out.writeShort(value);
	}

	@Override
	public int getTypeId() {
		return TagID.TAG_SHORT;
	}

	@Override
	public String getTypeName() {
		return "TAG_Short";
	}

	@Override
	public String getNMSClass() {
		return "NBTTagShort";
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) { return true; }
		if (o == null || getClass() != o.getClass()) { return false; }

		ShortTag shortTag = (ShortTag) o;

		return value == shortTag.value;

	}

	@Override
	public int hashCode() {
		return (int) value;
	}
}
