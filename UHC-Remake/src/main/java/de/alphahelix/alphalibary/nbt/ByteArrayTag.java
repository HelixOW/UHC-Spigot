package de.alphahelix.alphalibary.nbt;

import com.google.common.primitives.Bytes;
import com.google.gson.JsonArray;
import com.google.gson.JsonPrimitive;
import de.alphahelix.alphalibary.nbt.stream.NBTInputStream;
import de.alphahelix.alphalibary.nbt.stream.NBTOutputStream;
import org.apache.commons.lang.ArrayUtils;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;

public class ByteArrayTag extends ArrayTag<byte[], Byte> {

	private byte[] value;

	public ByteArrayTag() {
		this(new byte[0]);
	}

	public ByteArrayTag(byte[] value) {
		super("");
		this.value = value;
	}

	public ByteArrayTag(String name) {
		super(name);
	}

	public ByteArrayTag(String name, byte[] value) {
		super(name);
		this.value = value;
	}

	public ByteArrayTag(String name, Collection<Byte> list) {
		super(name);
		this.value = ArrayUtils.toPrimitive(list.toArray(new Byte[list.size()]));
	}

	@Override
	public byte[] getValue() {
		return value;
	}

	@Override
	public void setValue(byte[] value) {
		this.value = value;
	}

	@Override
	public JsonArray asJson() {
		JsonArray jsonArray = new JsonArray();
		for (byte b : value) {
			jsonArray.add(new JsonPrimitive(b));
		}
		return jsonArray;
	}

	@Override
	public void read(NBTInputStream nbtIn, DataInputStream in, int depth) throws IOException {
		byte[] bytes = new byte[in.readInt()];
		in.readFully(bytes);
		value = bytes;
	}

	@Override
	public void write(NBTOutputStream nbtOut, DataOutputStream out) throws IOException {
		out.writeInt(value.length);
		out.write(value);
	}

	@Override
	public int getTypeId() {
		return TagID.TAG_BYTE_ARRAY;
	}

	@Override
	public String getTypeName() {
		return "TAG_Byte_Array";
	}

	@Override
	public Iterator<Byte> iterator() {
		return Bytes.asList(value).iterator();
	}

	@Override
	public String getNMSClass() {
		return "NBTTagByteArray";
	}

	@Override
	public String toString() {
		return getTypeName() + "(" + getName() + "): " + Arrays.toString(value);
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) { return true; }
		if (o == null || getClass() != o.getClass()) { return false; }

		ByteArrayTag bytes = (ByteArrayTag) o;

		return Arrays.equals(value, bytes.value);

	}

	@Override
	public int hashCode() {
		return Arrays.hashCode(value);
	}
}
