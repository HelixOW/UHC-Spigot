package de.alphahelix.alphalibary.nbt.annotation;

import de.alphahelix.alphalibary.nbt.CompoundTag;
import de.alphahelix.alphalibary.nbt.NBTTag;
import de.alphahelix.alphalibary.nbt.TagID;

import java.lang.reflect.Method;

public class NBTReadMethod extends NBTMember {

	private final Method         method;
	private final NBTParameter[] parameters;

	public NBTReadMethod(String[] key, int type, boolean read, NBTPriority priority, Object obj, Method method, NBTParameter[] parameters) {
		super(key, type, read, false, priority, obj);
		this.method = method;
		this.parameters = parameters;
	}

	@Override
	public void read(NBTTag tag) {
		Object[] args = new Object[parameters.length];
		for (int i = 0; i < args.length; i++) {
			NBTTag paramTag;
			if (tag.getTypeId() == TagID.TAG_COMPOUND) {
				paramTag = AnnotatedNBTHandler.digTag((CompoundTag) tag, parameters[i].key, 0);
			} else {
				paramTag = tag;
			}
			args[i] = paramTag == null ? null : fromNbtValue(paramTag, parameters[i].parameter.getType());
		}
		try {
			method.invoke(this.obj, args);
		} catch (ReflectiveOperationException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public NBTTag write(NBTTag tag) {
		throw new UnsupportedOperationException();
	}
}
