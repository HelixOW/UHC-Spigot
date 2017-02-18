package de.alphahelix.alphalibary.nbt.annotation;

import de.alphahelix.alphalibary.nbt.NBTTag;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;

public class NBTParameter extends NBTMember {

	protected final Parameter parameter;

	public NBTParameter(String[] key, int type, boolean read, boolean write, NBTPriority priority, Method method, Parameter parameter) {
		super(key, type, read, write, priority, method);
		this.parameter = parameter;
	}

	@Override
	public void read(NBTTag tag) {
	}

	@Override
	public NBTTag write(NBTTag tag) {
		throw new UnsupportedOperationException();
	}

}
