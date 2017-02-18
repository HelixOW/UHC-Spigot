package de.alphahelix.alphalibary.nbt.annotation;

public class NBTInfo implements Comparable<NBTInfo> {

	protected String[]    key;
	protected int         type;
    protected boolean     read;
    protected boolean     write;
    protected NBTPriority priority;

    public NBTInfo(String[] key, int type, boolean read, boolean write, NBTPriority priority) {
        this.key = key;
        this.type = type;
        this.read = read;
        this.write = write;
        this.priority = priority;
    }

    @Override
	public int compareTo(NBTInfo o) {
		return priority.ordinal() - o.priority.ordinal();
	}
}
