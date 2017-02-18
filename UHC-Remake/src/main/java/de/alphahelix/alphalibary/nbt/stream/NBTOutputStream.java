package de.alphahelix.alphalibary.nbt.stream;

import de.alphahelix.alphalibary.nbt.NBTTag;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.util.zip.GZIPOutputStream;

public class NBTOutputStream implements AutoCloseable {

	public static final Charset UTF_8 = Charset.forName("UTF-8");

	private final DataOutputStream out;

	public NBTOutputStream(OutputStream outputStream, boolean gzip) throws IOException {
		if (gzip) {
			this.out = new DataOutputStream(new GZIPOutputStream(outputStream));
		} else {
			this.out = new DataOutputStream(outputStream);
		}
	}

	public NBTOutputStream(OutputStream outputStream) throws IOException {
		this.out = new DataOutputStream(outputStream);
	}

	public NBTOutputStream(DataOutputStream dataOutputStream) {
		this.out = dataOutputStream;
	}

	public void writeTag(NBTTag tag) throws IOException {
		String name = tag.getName();
		byte[] nameBytes = name.getBytes(UTF_8);

		out.writeByte(tag.getTypeId());
		out.writeShort(nameBytes.length);
		out.write(nameBytes);

		writeTagContent(tag);
	}

	public void writeTagContent(NBTTag tag) throws IOException {
		tag.write(this, out);
	}

	@Override
	public void close() throws Exception {
		out.close();
	}
}
