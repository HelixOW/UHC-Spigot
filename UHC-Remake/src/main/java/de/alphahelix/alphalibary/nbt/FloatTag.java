package de.alphahelix.alphalibary.nbt;

import com.google.gson.JsonPrimitive;
import de.alphahelix.alphalibary.nbt.stream.NBTInputStream;
import de.alphahelix.alphalibary.nbt.stream.NBTOutputStream;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class FloatTag extends NumberTag<Float> {

	private float value;

	public FloatTag() {
		this(0);
	}

	public FloatTag(float value) {
		super("");
		this.value = value;
	}

	public FloatTag(String name) {
		super(name);
	}

	public FloatTag(String name, float value) {
		super(name);
		this.value = value;
	}

	@Override
	public Float getValue() {
		return value;
	}

	@Override
	public void setValue(Float aFloat) {
		this.value = aFloat;
	}

	@Override
	public JsonPrimitive asJson() {
		return new JsonPrimitive(value);
	}

	@Override
	public void read(NBTInputStream nbtIn, DataInputStream in, int depth) throws IOException {
		value = in.readFloat();
	}

	@Override
	public void write(NBTOutputStream nbtOut, DataOutputStream out) throws IOException {
		out.writeFloat(value);
	}

	@Override
	public int getTypeId() {
		return TagID.TAG_FLOAT;
	}

	@Override
	public String getTypeName() {
		return "TAG_Float";
	}

	@Override
	public String getNMSClass() {
		return "NBTTagFloat";
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) { return true; }
		if (o == null || getClass() != o.getClass()) { return false; }

		FloatTag floatTag = (FloatTag) o;

		return Float.compare(floatTag.value, value) == 0;

	}

	@Override
	public int hashCode() {
		return (value != +0.0f ? Float.floatToIntBits(value) : 0);
	}
}
