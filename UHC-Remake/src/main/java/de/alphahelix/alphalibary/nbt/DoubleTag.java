package de.alphahelix.alphalibary.nbt;

import com.google.gson.JsonPrimitive;
import de.alphahelix.alphalibary.nbt.stream.NBTInputStream;
import de.alphahelix.alphalibary.nbt.stream.NBTOutputStream;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class DoubleTag extends NumberTag<Double> {

	private double value;

	public DoubleTag() {
		this(0);
	}

	public DoubleTag(double value) {
		super("");
		this.value = value;
	}

	public DoubleTag(String name) {
		super(name);
	}

	public DoubleTag(String name, double value) {
		super(name);
		this.value = value;
	}

	@Override
	public Double getValue() {
		return value;
	}

	@Override
	public void setValue(Double aDouble) {
		this.value = aDouble;
	}

	@Override
	public JsonPrimitive asJson() {
		return new JsonPrimitive(value);
	}

	@Override
	public void read(NBTInputStream nbtIn, DataInputStream in, int depth) throws IOException {
		value = in.readDouble();
	}

	@Override
	public void write(NBTOutputStream nbtOut, DataOutputStream out) throws IOException {
		out.writeDouble(value);
	}

	@Override
	public int getTypeId() {
		return TagID.TAG_DOUBLE;
	}

	@Override
	public String getTypeName() {
		return "TAG_Double";
	}

	@Override
	public String getNMSClass() {
		return "NBTTagDouble";
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) { return true; }
		if (o == null || getClass() != o.getClass()) { return false; }

		DoubleTag doubleTag = (DoubleTag) o;

		return Double.compare(doubleTag.value, value) == 0;

	}

	@Override
	public int hashCode() {
		long temp = Double.doubleToLongBits(value);
		return (int) (temp ^ (temp >>> 32));
	}
}
