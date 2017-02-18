package de.alphahelix.alphalibary.nbt;

import com.google.gson.JsonPrimitive;
import de.alphahelix.alphalibary.nbt.stream.NBTInputStream;
import de.alphahelix.alphalibary.nbt.stream.NBTOutputStream;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class LongTag extends NumberTag<Long> {

	private long value;

	public LongTag() {
		this(0);
	}

	public LongTag(long value) {
		super("");
		this.value = value;
	}

	public LongTag(String name) {
		super(name);
	}

	public LongTag(String name, long value) {
		super(name);
		this.value = value;
	}

	@Override
	public Long getValue() {
		return value;
	}

	@Override
	public void setValue(Long aLong) {
		this.value = aLong;
	}

	@Override
	public JsonPrimitive asJson() {
		return new JsonPrimitive(value);
	}

	@Override
	public void read(NBTInputStream nbtIn, DataInputStream in, int depth) throws IOException {
		value = in.readLong();
	}

	@Override
	public void write(NBTOutputStream nbtOut, DataOutputStream out) throws IOException {
		out.writeLong(value);
	}

	@Override
	public int getTypeId() {
		return TagID.TAG_LONG;
	}

	@Override
	public String getTypeName() {
		return "TAG_Long";
	}

	@Override
	public String getNMSClass() {
		return "NBTTagLong";
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) { return true; }
		if (o == null || getClass() != o.getClass()) { return false; }

		LongTag longTag = (LongTag) o;

		return value == longTag.value;

	}

	@Override
	public int hashCode() {
		return (int) (value ^ (value >>> 32));
	}
}
