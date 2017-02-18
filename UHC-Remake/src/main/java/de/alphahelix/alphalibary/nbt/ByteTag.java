package de.alphahelix.alphalibary.nbt;

import com.google.gson.JsonPrimitive;
import de.alphahelix.alphalibary.nbt.stream.NBTInputStream;
import de.alphahelix.alphalibary.nbt.stream.NBTOutputStream;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class ByteTag extends NumberTag<Byte> {

	private  byte value;

	public ByteTag() {
		this((byte) 0);
	}

	public ByteTag(byte value) {
		super("");
		this.value = value;
	}

	public ByteTag(String name) {
		super(name);
	}

	public ByteTag(String name, byte value) {
		super(name);
		this.value = value;
	}

	@Override
	public Byte getValue() {
		return value;
	}

	@Override
	public void setValue(Byte aByte) {
		this.value = aByte;
	}

	@Override
	public JsonPrimitive asJson() {
		return new JsonPrimitive(value);
	}

	@Override
	public void read(NBTInputStream nbtIn, DataInputStream in, int depth) throws IOException {
		value = in.readByte();
	}

	@Override
	public void write(NBTOutputStream nbtOut, DataOutputStream out) throws IOException {
		out.writeByte(value);
	}

	@Override
	public int getTypeId() {
		return TagID.TAG_BYTE;
	}

	@Override
	public String getTypeName() {
		return "TAG_Byte";
	}

	@Override
	public String getNMSClass() {
		return "NBTTagByte";
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) { return true; }
		if (o == null || getClass() != o.getClass()) { return false; }

		ByteTag byteTag = (ByteTag) o;

		return value == byteTag.value;

	}

	@Override
	public int hashCode() {
		return (int) value;
	}
}
