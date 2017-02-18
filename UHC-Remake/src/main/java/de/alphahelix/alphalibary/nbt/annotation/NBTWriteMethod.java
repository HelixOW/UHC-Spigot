package de.alphahelix.alphalibary.nbt.annotation;

import de.alphahelix.alphalibary.nbt.NBTTag;

import java.lang.reflect.Method;

public class NBTWriteMethod extends NBTMember {

	protected final Method method;

	public NBTWriteMethod(String[] key, int type, boolean write, NBTPriority priority, Object obj, Method method) {
		super(key, type, false, write, priority, obj);
		this.method = method;
	}

	@Override
	public void read(NBTTag tag) {
		throw new UnsupportedOperationException();
	}

	@Override
	public NBTTag write(NBTTag tag) {
		try {
			Object value = toNbtValue(method.invoke(this.obj), method.getReturnType());
			if (value != null) {
				tag.setValue(value);
			}
		} catch (ReflectiveOperationException e) {
			throw new RuntimeException(e);
		}
		return tag;
	}
}
