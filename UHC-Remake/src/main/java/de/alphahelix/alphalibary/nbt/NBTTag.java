package de.alphahelix.alphalibary.nbt;

import com.google.gson.JsonElement;
import de.alphahelix.alphalibary.nbt.stream.NBTInputStream;
import de.alphahelix.alphalibary.nbt.stream.NBTOutputStream;
import de.alphahelix.alphalibary.reflection.ReflectionUtil;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;

import static de.alphahelix.alphalibary.nbt.TagID.*;

public abstract class NBTTag<V> {

	private final String name;

	public NBTTag(String name) {
		this.name = name;
	}

	public NBTTag(String name, Object nmsTag) {
		this.name = name;

	}

	public static NBTTag createType(int type) throws IllegalAccessException, InstantiationException {
		return forType(type).newInstance();
	}

	public static NBTTag createType(int type, String name) throws IllegalAccessException, InstantiationException, NoSuchMethodException, InvocationTargetException {
		return forType(type).getConstructor(String.class).newInstance(name);
	}

	public static Class<? extends NBTTag> forType(int type) {
		switch (type) {
			case TAG_BYTE_ARRAY:
				return ByteArrayTag.class;
			case TAG_BYTE:
				return ByteTag.class;
			case TAG_COMPOUND:
				return CompoundTag.class;
			case TAG_DOUBLE:
				return DoubleTag.class;
			case TAG_END:
				return EndTag.class;
			case TAG_FLOAT:
				return FloatTag.class;
			case TAG_INT_ARRAY:
				return IntArrayTag.class;
			case TAG_INT:
				return IntTag.class;
			case TAG_LIST:
				return ListTag.class;
			case TAG_LONG:
				return LongTag.class;
			case TAG_SHORT:
				return ShortTag.class;
			case TAG_STRING:
				return StringTag.class;
			default:
				throw new IllegalArgumentException("Invalid NBTTag type " + type);
		}
	}

	public String getName() {
		return name;
	}

	public abstract V getValue();

	public abstract void setValue(V v);

	// getAs methods

	public Number getAsNumber() {
		return 0;
	}

	public byte getAsByte() {
		return getAsNumber().byteValue();
	}

	public short getAsShort() {
		return getAsNumber().shortValue();
	}

	public int getAsInt() {
		return getAsNumber().intValue();
	}

	public long getAsLong() {
		return getAsNumber().longValue();
	}

	public float getAsFloat() {
		return getAsNumber().floatValue();
	}

	public double getAsDouble() {
		return getAsNumber().doubleValue();
	}

	public String getAsString() {
		return String.valueOf(getValue());
	}

	// /getAs methods

	public abstract JsonElement asJson();

	public abstract int getTypeId();

	public abstract String getTypeName();

	public void read(NBTInputStream nbtIn, DataInputStream in, int depth) throws IOException {
	}

	public void write(NBTOutputStream nbtOut, DataOutputStream out) throws IOException {
	}

	public String getNMSClass() {
		return "NBTBase";
	}

	public NBTTag fromNMS(Object nms) throws ReflectiveOperationException {
        Class<?> clazz = ReflectionUtil.getNmsClass(getNMSClass());
		Field field = clazz.getDeclaredField("data");
		field.setAccessible(true);
		setValue((V) field.get(nms));
		return this;
	}

	public Object toNMS() throws ReflectiveOperationException {
		Class<?> clazz = ReflectionUtil.getNmsClass(getNMSClass());
		Constructor constructor = null;
		for (Constructor constr : clazz.getConstructors()) {
			if (constr.getParameterTypes().length == 1) {
				constructor = constr;
				break;
			}
		}
		if (constructor == null) { return null; }
		return constructor.newInstance(getValue());
	}

	@Override
	public String toString() {
		return getTypeName() + "(" + getName() + "): " + getValue();
	}
}
