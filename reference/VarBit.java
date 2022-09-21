
public final class Varbit {

	public static void unpackConfig(StreamLoader archive) {
		final Buffer buffer = new Buffer(archive.getDataForName("varbit.dat"));
		final int len = buffer.readUShort();

		if (cache == null) {
			cache = new Varbit[len];
		}

		System.out.println(String.format("Loaded: %d varbits", len));

		for (int i = 0; i < len; i++) {
			if (cache[i] == null) {
				cache[i] = new Varbit();
			}

			cache[i].decode(buffer);

			if (cache[i].aBoolean651) {
				Varp.varps[cache[i].configId].aBoolean713 = true;
			}
		}

		if (buffer.currentOffset != buffer.buffer.length) {
			System.out.println("varbit mismatch! " + buffer.currentOffset + " " + buffer.buffer.length);
		}
	}

	private void decode(Buffer buffer) {
		int opcode = buffer.readUByte();

		if (opcode == 0) {
			return;
		} else if (opcode == 1) {
			configId = buffer.readUShort();
			lsb = buffer.readUByte();
			msb = buffer.readUByte();
		} else {
			System.out.println(opcode);
		}
	}

	private Varbit() {
		aBoolean651 = false;
	}

	public static Varbit cache[];
	public int configId;
	public int lsb;
	public int msb;
	private boolean aBoolean651;
}
