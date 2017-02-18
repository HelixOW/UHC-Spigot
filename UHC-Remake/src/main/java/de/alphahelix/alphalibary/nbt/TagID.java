package de.alphahelix.alphalibary.nbt;

import java.util.List;
import java.util.Map;

public class TagID {

	public static final int TAG_END        = 0;
	public static final int TAG_BYTE       = 1;
	public static final int TAG_SHORT      = 2;
	public static final int TAG_INT        = 3;
	public static final int TAG_LONG       = 4;
	public static final int TAG_FLOAT      = 5;
	public static final int TAG_DOUBLE     = 6;
	public static final int TAG_BYTE_ARRAY = 7;
	public static final int TAG_STRING     = 8;
	public static final int TAG_LIST       = 9;
	public static final int TAG_COMPOUND   = 10;
	public static final int TAG_INT_ARRAY  = 11;

	public static int forClass(Class<? extends NBTTag> clazz) {
		return forName(clazz.getSimpleName());
	}

	public static int forValueClass(Class<?> clazz) {
		if (byte.class.isAssignableFrom(clazz) || Byte.class.isAssignableFrom(clazz) || boolean.class.isAssignableFrom(clazz)) {
			return TAG_BYTE;
		}
		if (short.class.isAssignableFrom(clazz) || Short.class.isAssignableFrom(clazz)) {
			return TAG_SHORT;
		}
		if (int.class.isAssignableFrom(clazz) || Integer.class.isAssignableFrom(clazz)) {
			return TAG_INT;
		}
		if (long.class.isAssignableFrom(clazz) || Long.class.isAssignableFrom(clazz)) {
			return TAG_LONG;
		}
		if (float.class.isAssignableFrom(clazz) || Float.class.isAssignableFrom(clazz)) {
			return TAG_FLOAT;
		}
		if (double.class.isAssignableFrom(clazz) || Double.class.isAssignableFrom(clazz)) {
			return TAG_DOUBLE;
		}
		if (String.class.isAssignableFrom(clazz)) {
			return TAG_STRING;
		}
		if (byte[].class.isAssignableFrom(clazz) || Byte[].class.isAssignableFrom(clazz)) {
			return TAG_BYTE_ARRAY;
		}
		if (int[].class.isAssignableFrom(clazz) || Integer[].class.isAssignableFrom(clazz)) {
			return TAG_INT_ARRAY;
		}
		if (List.class.isAssignableFrom(clazz)) {
			return TAG_LIST;
		}
		if (Map.class.isAssignableFrom(clazz)) {
			return TAG_COMPOUND;
		}
		return forName(clazz.getSimpleName());
	}

	public static int forName(String name) {
		switch (name) {
			case "ByteArrayTag":
			case "Tag_Byte_Array":
				return TAG_BYTE_ARRAY;
			case "ByteTag":
			case "Tag_Byte":
				return TAG_BYTE;
			case "CompoundTag":
			case "TAG_Compound":
				return TAG_COMPOUND;
			case "DoubleTag":
			case "TAG_Double":
				return TAG_DOUBLE;
			case "EndTag":
			case "TAG_End":
				return TAG_END;
			case "FloatTag":
			case "TAG_Float":
				return TAG_FLOAT;
			case "IntArrayTag":
			case "TAG_Int_Array":
				return TAG_INT_ARRAY;
			case "IntTag":
			case "TAG_Int":
				return TAG_INT;
			case "ListTag":
			case "TAG_List":
				return TAG_LIST;
			case "LongTag":
			case "TAG_Long":
				return TAG_LONG;
			case "ShortTag":
			case "TAG_Short":
				return TAG_SHORT;
			case "StringTag":
			case "TAG_String":
				return TAG_STRING;
			default:
				throw new IllegalArgumentException("Invalid NBTTag name: " + name);
		}
	}

}
