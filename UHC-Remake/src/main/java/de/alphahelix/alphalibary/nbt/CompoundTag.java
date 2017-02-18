package de.alphahelix.alphalibary.nbt;

import com.google.gson.JsonObject;
import de.alphahelix.alphalibary.nbt.stream.NBTInputStream;
import de.alphahelix.alphalibary.nbt.stream.NBTOutputStream;
import de.alphahelix.alphalibary.reflection.ReflectionUtil;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class CompoundTag extends NBTTag<Map<String, NBTTag>> implements Iterable<Map.Entry<String, NBTTag>> {

	private final Map<String, NBTTag> value;

	public CompoundTag() {
		super("");
		this.value = new HashMap<>();
	}

	public CompoundTag(Map<String, NBTTag> value) {
		super("");
		this.value = new HashMap<>(value);
	}

	public CompoundTag(String name) {
		super(name);
		this.value = new HashMap<>();
	}

	public CompoundTag(String name, Map<String, NBTTag> value) {
		super(name);
		this.value = new HashMap<>(value);
	}

	@Override
	public Map<String, NBTTag> getValue() {
		return value;
	}

	@Override
	public void setValue(Map<String, NBTTag> value) {
		this.value.putAll(value);
	}

	public NBTTag get(String name) {
		return value.get(name);
	}

	public void set(String name, NBTTag tag) {
		this.value.put(name, tag);
	}

	public void set(String name, byte b) {
		set(name, new ByteTag(name, b));
	}

	public void set(String name, short s) {
		set(name, new ShortTag(name, s));
	}

	public void set(String name, int i) {
		set(name, new IntTag(name, i));
	}

	public void set(String name, long l) {
		set(name, new LongTag(name, l));
	}

	public void set(String name, float f) {
		set(name, new FloatTag(name, f));
	}

	public void set(String name, double d) {
		set(name, new DoubleTag(name, d));
	}

	public void set(String name, String string) {
		set(name, new StringTag(name, string));
	}

	public void set(String name, byte[] b) {
		set(name, new ByteArrayTag(name, b));
	}

	public void set(String name, int[] i) {
		set(name, new IntArrayTag(name, i));
	}

	public void set(String name, boolean b) {
		set(name, (byte) (b ? 1 : 0));
	}

	public <T extends Enum<T>> void set(String name, T enm) {
		set(name, enm.name());
	}

	public boolean has(String name) {
		return value.containsKey(name);
	}

	public String getString(String name) {
		NBTTag tag = get(name);
		if (tag == null) { return null; }
		Object value = tag.getValue();
		if (value == null) { return null; }
		return value.toString();
	}

	public Number getNumber(String name) {
		NBTTag tag = get(name);
		if (tag == null) { return null; }
		if (tag instanceof NumberTag) {
			return ((NumberTag) tag).getValue();
		}
		return null;
	}

	public boolean getBoolean(String name) {
		NBTTag tag = get(name);
		if (tag == null) { return false; }
		if (tag instanceof ByteTag) {
			return ((ByteTag) tag).getValue() == 1;
		}
		return false;
	}

	public CompoundTag getCompound(String name) {
		NBTTag tag = get(name);
		if (tag == null) { return null; }
		if (tag instanceof CompoundTag) {
			return (CompoundTag) tag;
		}
		return null;
	}

	public CompoundTag getOrCreateCompound(String name) {
		if (has(name)) {
			return getCompound(name);
		}
		CompoundTag compoundTag = new CompoundTag(name);
		set(name, compoundTag);
		return compoundTag;
	}

	public ListTag getList(String name) {
		NBTTag tag = get(name);
		if (tag == null) { return null; }
		if (tag instanceof ListTag) {
			return (ListTag) tag;
		}
		return null;
	}

	public <V extends NBTTag> ListTag<V> getList(String name, Class<V> type) {
		ListTag list = getList(name);
		if (list.getTagType() != TagID.forClass(type)) {
			throw new IllegalArgumentException("ListTag(" + name + ") is not of type " + type.getSimpleName());
		}
		return (ListTag<V>) list;
	}

	public <V extends NBTTag> ListTag<V> getOrCreateList(String name, Class<V> type) {
		if (has(name)) {
			return getList(name, type);
		}
		ListTag<V> listTag = new ListTag<>(name, TagID.forClass(type), new ArrayList<V>());
		set(name, listTag);
		return listTag;
	}

	public <T extends Enum<T>> T getEnum(String name, Class<T> clazz) {
		String string = getString(name);
		if (string == null) { return null; }
		return Enum.valueOf(clazz, string);
	}

	@Override
	public JsonObject asJson() {
		JsonObject jsonObject = new JsonObject();
		for (Map.Entry<String, NBTTag> entry : value.entrySet()) {
			jsonObject.add(entry.getKey(), entry.getValue().asJson());
		}
		return jsonObject;
	}

	@Override
	public void read(NBTInputStream nbtIn, DataInputStream in, int depth) throws IOException {
		while (true) {
			NBTTag tag = nbtIn.readNBTTag(depth + 1);
			if (tag.getTypeId() == TagID.TAG_END) {
				break;
			} else {
				set(tag.getName(), tag);
			}
		}
	}

	@Override
	public void write(NBTOutputStream nbtOut, DataOutputStream out) throws IOException {
		for (NBTTag tag : value.values()) {
			nbtOut.writeTag(tag);
		}
		out.writeByte(TagID.TAG_END);
	}

	@Override
	public int getTypeId() {
		return TagID.TAG_COMPOUND;
	}

	@Override
	public String getTypeName() {
		return "TAG_Compound";
	}

	@Override
	public Iterator<Map.Entry<String, NBTTag>> iterator() {
		return value.entrySet().iterator();
	}

	public String getNMSClass() {
		return "NBTTagCompound";
	}

	@Override
	public CompoundTag fromNMS(Object nms) throws ReflectiveOperationException {
		Class<?> clazz = ReflectionUtil.getNmsClass(getNMSClass());
		Class<?> nbtBaseClass = ReflectionUtil.getNmsClass("NBTBase");
		Field field = clazz.getDeclaredField("map");
		field.setAccessible(true);
		Map<String, Object> nmsMap = (Map<String, Object>) field.get(nms);

		for (Map.Entry<String, Object> nmsEntry : nmsMap.entrySet()) {
			byte typeId = (byte) nbtBaseClass.getMethod("getTypeId").invoke(nmsEntry.getValue());
			if (typeId == TagID.TAG_END) { continue; }
			set(nmsEntry.getKey(), NBTTag.forType(typeId).getConstructor(String.class).newInstance(nmsEntry.getKey()).fromNMS(nmsEntry.getValue()));
		}
		return this;
	}

	@Override
	public Object toNMS() throws ReflectiveOperationException {
		Class<?> clazz = ReflectionUtil.getNmsClass(getNMSClass());
		Field field = clazz.getDeclaredField("map");
		field.setAccessible(true);
		Object nms = clazz.newInstance();
		Map map = (Map) field.get(nms);
		for (Map.Entry<String, NBTTag> entry : this) {
			map.put(entry.getKey(), entry.getValue().toNMS());
		}
		field.set(nms, map);// I don't quite get why this doesn't complain about illegal access (the field is private final)
		return nms;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) { return true; }
		if (o == null || getClass() != o.getClass()) { return false; }

		CompoundTag that = (CompoundTag) o;

		return value != null ? value.equals(that.value) : that.value == null;

	}

	@Override
	public int hashCode() {
		return value != null ? value.hashCode() : 0;
	}
}
