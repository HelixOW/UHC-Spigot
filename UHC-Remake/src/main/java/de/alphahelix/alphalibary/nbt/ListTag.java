package de.alphahelix.alphalibary.nbt;

import com.google.gson.JsonArray;
import de.alphahelix.alphalibary.nbt.stream.NBTInputStream;
import de.alphahelix.alphalibary.nbt.stream.NBTOutputStream;
import de.alphahelix.alphalibary.reflection.ReflectionUtil;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ListTag<V extends NBTTag> extends NBTTag<List<V>> implements Iterable<V> {

    private final List<V> value;
    private int tagType;

    public ListTag() {
        super("");
        this.value = new ArrayList<>();
    }

    public ListTag(String name) {
        super(name);
        this.value = new ArrayList<>();
    }

    public ListTag(int tagType) {
        this(tagType, new ArrayList<V>());
    }

    public ListTag(int tagType, List<V> value) {
        super("");
        this.tagType = tagType;
        this.value = value;
    }

    public ListTag(int tagType, String name) {
        super(name);
        this.tagType = tagType;
        this.value = new ArrayList<>();
    }

    public ListTag(String name, int tagType, List<V> value) {
        super(name);
        this.tagType = tagType;
        this.value = new ArrayList<>(value);
    }

    public int getTagType() {
        return tagType;
    }

    public void setTagType(int tagType) {
        this.tagType = tagType;
    }

    @Override
    public List<V> getValue() {
        return value;
    }

    @Override
    public void setValue(List<V> value) {
        this.value.addAll(value);
    }

    public V get(int index) {
        return value.get(index);
    }

    public void add(V tag) {
        if (tag.getTypeId() != getTagType()) {
            throw new IllegalArgumentException("Invalid Tag type (List: " + getTagType() + ", Tag: " + tag.getTypeId() + ")");
        }
        value.add(tag);
    }

    public void add(int index, V tag) {
        if (tag.getTypeId() != getTagType()) {
            throw new IllegalArgumentException("Invalid Tag type (List: " + getTagType() + ", Tag: " + tag.getTypeId() + ")");
        }
        value.add(index, tag);
    }

    public void set(int index, V tag) {
        if (tag.getTypeId() != getTagType()) {
            throw new IllegalArgumentException("Invalid Tag type (List: " + getTagType() + ", Tag: " + tag.getTypeId() + ")");
        }
        value.set(index, tag);
    }

    public int size() {
        return value.size();
    }

    @Override
    public JsonArray asJson() {
        JsonArray jsonArray = new JsonArray();
        for (NBTTag<?> tag : value) {
            jsonArray.add(tag.asJson());
        }
        return jsonArray;
    }

    @Override
    public void read(NBTInputStream nbtIn, DataInputStream in, int depth) throws IOException {
        int type = in.readByte();
        int length = in.readInt();
        setTagType(type);
        for (int i = 0; i < length; i++) {
            NBTTag<?> tag = nbtIn.readTagContent(type, "", depth + 1);
            if (tag.getTypeId() == TagID.TAG_END) {
                throw new IOException("Invalid TAG_End in TAG_List (not allowed)");
            }
            add((V) tag);
        }
    }

    @Override
    public void write(NBTOutputStream nbtOut, DataOutputStream out) throws IOException {
        out.writeByte(getTagType());
        out.writeInt(value.size());
        for (NBTTag<?> tag : value) {
            nbtOut.writeTagContent(tag);
        }
    }

    @Override
    public int getTypeId() {
        return TagID.TAG_LIST;
    }

    @Override
    public String getTypeName() {
        return "TAG_List";
    }

    @Override
    public Iterator<V> iterator() {
        return value.iterator();
    }

    @Override
    public String getNMSClass() {
        return "NBTTagList";
    }

    @Override
    public ListTag<?> fromNMS(Object nms) throws ReflectiveOperationException {
        Class<?> clazz = ReflectionUtil.getNmsClass(getNMSClass());

        Field typeField = clazz.getDeclaredField("type");
        typeField.setAccessible(true);
        byte typeId = typeField.getByte(nms);
        setTagType(typeId);

        Field field = clazz.getDeclaredField("list");
        field.setAccessible(true);
        List<Object> nmsList = (List<Object>) field.get(nms);

        for (Object o : nmsList) {
            NBTTag<?> nbtTag = NBTTag.forType(typeId).newInstance();
            if (nbtTag.getTypeId() == TagID.TAG_END) {
                continue;
            }
            add((V) nbtTag.fromNMS(o));
        }
        return this;
    }

    @Override
    public Object toNMS() throws ReflectiveOperationException {
        Class<?> clazz = ReflectionUtil.getNmsClass(getNMSClass());

        Field field = clazz.getDeclaredField("list");
        field.setAccessible(true);

        Object nms = clazz.newInstance();

        Field typeField = clazz.getDeclaredField("type");
        typeField.setAccessible(true);
        typeField.setByte(nms, (byte) getTagType());

        List<Object> list = (List<Object>) field.get(nms);
        for (NBTTag<?> tag : this) {
            list.add(tag.toNMS());
        }
        field.set(nms, list);
        return nms;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ListTag<?> listTag = (ListTag<?>) o;

        if (tagType != listTag.tagType) {
            return false;
        }
        return value != null ? value.equals(listTag.value) : listTag.value == null;

    }

    @Override
    public int hashCode() {
        int result = tagType;
        result = 31 * result + (value != null ? value.hashCode() : 0);
        return result;
    }
}
