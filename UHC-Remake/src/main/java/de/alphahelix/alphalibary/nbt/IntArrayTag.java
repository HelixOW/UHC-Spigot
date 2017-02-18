package de.alphahelix.alphalibary.nbt;

import com.google.common.primitives.Ints;
import com.google.gson.JsonArray;
import com.google.gson.JsonPrimitive;
import de.alphahelix.alphalibary.nbt.stream.NBTInputStream;
import de.alphahelix.alphalibary.nbt.stream.NBTOutputStream;
import org.apache.commons.lang.ArrayUtils;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public class IntArrayTag extends ArrayTag<int[], Integer> {

	private int[] value;

	public IntArrayTag() {
		this(new int[0]);
	}

	public IntArrayTag(int[] value) {
		super("");
		this.value = value;
	}

	public IntArrayTag(String name) {
		super(name);
	}

	public IntArrayTag(String name, int[] value) {
		super(name);
		this.value = value;
	}

	public IntArrayTag(String name, List<Integer> list) {
		super(name);
		this.value = ArrayUtils.toPrimitive(list.toArray(new Integer[list.size()]));
	}

	@Override
	public int[] getValue() {
		return value;
	}

	@Override
	public void setValue(int[] value) {
		this.value = value;
	}

	@Override
	public JsonArray asJson() {
		JsonArray jsonArray = new JsonArray();
		for (int i : value) {
			jsonArray.add(new JsonPrimitive(i));
		}
		return jsonArray;
	}

	@Override
	public void read(NBTInputStream nbtIn, DataInputStream in, int depth) throws IOException {
		int[] ints = new int[in.readInt()];
		for (int i = 0; i < ints.length; i++) {
			ints[i] = in.readInt();
		}
		value = ints;
	}

	@Override
	public void write(NBTOutputStream nbtOut, DataOutputStream out) throws IOException {
		out.writeInt(value.length);
		for (int i = 0; i < value.length; i++) {
			out.writeInt(value[i]);
		}
	}

	@Override
	public int getTypeId() {
		return TagID.TAG_INT_ARRAY;
	}

	@Override
	public String getTypeName() {
		return "TAG_Int_Array";
	}

	@Override
	public Iterator<Integer> iterator() {
		return Ints.asList(value).iterator();
	}

	@Override
	public String getNMSClass() {
		return "NBTTagIntArray";
	}

	@Override
	public String toString() {
		return getTypeName() + "(" + getName() + "): " + Arrays.toString(value);
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) { return true; }
		if (o == null || getClass() != o.getClass()) { return false; }

		IntArrayTag integers = (IntArrayTag) o;

		return Arrays.equals(value, integers.value);

	}

	@Override
	public int hashCode() {
		return Arrays.hashCode(value);
	}
}
