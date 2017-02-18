package de.alphahelix.alphalibary.nbt.annotation;

import de.alphahelix.alphalibary.nbt.CompoundTag;
import de.alphahelix.alphalibary.nbt.NBTTag;
import de.alphahelix.alphalibary.nbt.TagID;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class AnnotatedNBTHandler {

	private final Object toHandle;
	private final List<NBTMember> members = new ArrayList<>();

	public AnnotatedNBTHandler(Object toHandle) {
		this(toHandle, true);
	}

	public AnnotatedNBTHandler(Object toHandle, boolean searchSuper) {
		this.toHandle = toHandle;
		if (searchSuper) {
			Class superClazz = toHandle.getClass();
			while (superClazz != null) {
				register(superClazz);
				superClazz = superClazz.getSuperclass();
			}
		} else {
			register(toHandle.getClass());
		}
	}

	static NBTTag digTag(CompoundTag parent, String[] key, int index) {
		if (key.length == 0) { return parent; }
		NBTTag tag = parent.get(key[index]);
		if (tag == null) { return null; }
		if (tag.getTypeId() != TagID.TAG_COMPOUND) {
			if (index != key.length - 1) {
				throw new IllegalStateException("Found non-compound tag before key end (index " + index + "/" + key.length + ")");
			}
			return tag;
		}
		if (index == key.length - 1) { return tag; }// Return anything (even compound) if we're at the final index
		return digTag((CompoundTag) tag, key, index + 1);
	}

	static NBTTag buildTag(CompoundTag parent, int type, String[] key) {
		try {
			for (int i = 0; i < key.length - 1; i++) {
				parent = parent.getOrCreateCompound(key[i]);
			}
			NBTTag tag = NBTTag.createType(type, key[key.length - 1]);
			parent.set(key[key.length - 1], tag);
			return tag;
		} catch (ReflectiveOperationException e) {
			throw new RuntimeException(e);
		}
	}

	void register(Class<?> clazz) {
		for (Field field : clazz.getDeclaredFields()) {
			NBT annotation = field.getAnnotation(NBT.class);
			if (annotation != null) {
				field.setAccessible(true);

				String[] key = annotation.value();
				if (key == null || key.length == 0) { key = new String[] { field.getName() }; }
				int type = annotation.type();
				if (type == -1) { type = TagID.forValueClass(field.getType()); }
				boolean write = annotation.write();
				boolean read = annotation.read();
				NBTPriority priority = annotation.priority();

				members.add(new NBTField(key, type, read, write, priority, this.toHandle, field));
			}
		}

		for (Method method : clazz.getDeclaredMethods()) {
			NBT annotation = method.getAnnotation(NBT.class);
			if (annotation != null) {
				method.setAccessible(true);

				String[] key = annotation.value();
				if (key == null || key.length == 0) { key = new String[] { method.getName() }; }
				int type = annotation.type();
				boolean write = annotation.write();
				boolean read = annotation.read();
				NBTPriority priority = annotation.priority();

				if (method.getParameterTypes().length == 0) {// Method that returns the value to write
					if (type == -1) { type = TagID.forValueClass(method.getReturnType()); }
					members.add(new NBTWriteMethod(key, type, write, priority, this.toHandle, method));
				} else {
					NBTParameter[] nbtParameters = new NBTParameter[method.getParameters().length];
					for (int i = 0; i < method.getParameters().length; i++) {
						Parameter parameter = method.getParameters()[i];

						NBT paramAnnotation = parameter.getAnnotation(NBT.class);
						if (paramAnnotation == null) { throw new IllegalArgumentException("Missing @NBT parameter annotation for @NBT method " + method.getName()); }
						String[] paramKey = paramAnnotation.value();
						int paramType = paramAnnotation.type();
						if (paramType == -1) { type = TagID.forValueClass(parameter.getType()); }
						boolean paramRead = paramAnnotation.read();
						NBTPriority paramPriority = paramAnnotation.priority();

						nbtParameters[i] = new NBTParameter(/*fullKey*/paramKey, paramType, paramRead, false, paramPriority, method, parameter);
					}
					members.add(new NBTReadMethod(key, type, read, priority, this.toHandle, method, nbtParameters));
				}
			}
		}
		Collections.sort(members);
	}

	public void onRead(CompoundTag compoundTag) {
		for (NBTMember member : members) {
			if (member.read) {
				NBTTag tag = digTag(compoundTag, member.key, 0);
				if (tag != null) { member.read(tag); }
			}
		}
	}

	public void onWrite(CompoundTag compoundTag) {
		for (NBTMember member : members) {
			if (member.write) {
				NBTTag tag = buildTag(compoundTag, member.type, member.key);
				member.write(tag);
			}
		}
	}

}
