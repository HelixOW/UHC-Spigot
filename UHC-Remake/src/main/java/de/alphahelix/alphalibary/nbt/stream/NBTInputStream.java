package de.alphahelix.alphalibary.nbt.stream;

import de.alphahelix.alphalibary.nbt.NBTTag;
import de.alphahelix.alphalibary.nbt.TagID;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.zip.GZIPInputStream;
import java.util.zip.ZipException;

public class NBTInputStream implements AutoCloseable {

	public static final Charset UTF_8 = Charset.forName("UTF-8");

	private final DataInputStream in;

	public NBTInputStream(InputStream inputStream, boolean gzip) throws IOException {
		if (gzip) {
			this.in = new DataInputStream(new GZIPInputStream(inputStream));
		} else {
			this.in = new DataInputStream(inputStream);
		}
	}

	public NBTInputStream(InputStream inputStream) throws IOException {
		this.in = new DataInputStream(inputStream);
	}

	public NBTInputStream(DataInputStream dataInputStream) {
		this.in = dataInputStream;
	}

	public static NBTInputStream optionalGzip(InputStream inputStream) throws IOException {
		try {
			return new NBTInputStream(new DataInputStream(new GZIPInputStream(inputStream)));
		} catch (ZipException e) {
			return new NBTInputStream(new DataInputStream(inputStream));
		}
	}

	public NBTTag readNBTTag() throws IOException {
		return readNBTTag(0);
	}

	public NBTTag readNBTTag(int depth) throws IOException {
		int tagType = in.readByte() & 0xFF;
		String tagName = "";
		if (tagType != TagID.TAG_END) {
			int length = in.readShort() & 0xFFFF;
			byte[] nameBytes = new byte[length];
			in.readFully(nameBytes);
			tagName = new String(nameBytes, UTF_8);
		}

		return readTagContent(tagType, tagName, depth);
	}

	public NBTTag readTagContent(int tagType, String tagName, int depth) throws IOException {
		try {
			NBTTag nbtTag = NBTTag.forType(tagType).getConstructor(String.class).newInstance(tagName);
			nbtTag.read(this, in, depth);
			return nbtTag;
		} catch (ReflectiveOperationException e) {
			throw new IOException("Could not instantiate NBTTag class for type " + tagType + "'" + tagName + "'", e);
		}
	}

	@Override
	public void close() throws Exception {
		in.close();
	}
}
