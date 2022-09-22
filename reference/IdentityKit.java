
public final class IdentityKit {

	public static int length;
	public static IdentityKit kits[];
	public int bodyPartId = -1;
	private int[] bodyModels;
	private final int[] originalColors = new int[6];
	private final int[] replacementColors = new int[6];
	private final int[] headModels = {-1, -1, -1, -1, -1};
	public boolean validStyle;

	private IdentityKit() {
	}

	public static void init(StreamLoader archive) {
		Buffer buffer = new Buffer(archive.getDataForName("idk.dat"));
		length = buffer.readUShort();

		System.out.println(String.format("Loaded: %d identity kits", length));

		if (kits == null) {
			kits = new IdentityKit[length];
		}

		for (int index = 0; index < length; index++) {
			if (kits[index] == null) {
				kits[index] = new IdentityKit();
			}

			IdentityKit kit = kits[index];
			kit.decode(buffer);
			kit.originalColors[0] = 55232;
			kit.replacementColors[0] = 6798;
		}
	}

	private void decode(Buffer buffer) {
		while(true) {
			final int opcode = buffer.readUByte();

			if (opcode == 0) {
				break;
			}

			if (opcode == 1) {
				bodyPartId = buffer.readUByte();
			} else if (opcode == 2) {
				final int length = buffer.readUByte();
				bodyModels = new int[length];
				for (int i = 0; i < length; i++) {
					bodyModels[i] = buffer.readUShort();
				}
			} else if (opcode == 3) {
				validStyle = true;
			} else if (opcode >= 40 && opcode < 50) {
				originalColors[opcode - 40] = buffer.readUShort();
			} else if (opcode >= 50 && opcode < 60) {
				replacementColors[opcode - 50] = buffer.readUShort();
			} else if (opcode >= 60 && opcode < 70) {
				headModels[opcode - 60] = buffer.readUShort();
			} else {
				System.out.println("Error unrecognised config code: " + opcode);
			}
		}
	}

	public boolean bodyLoaded() {
		if (bodyModels == null) {
			return true;
		}
		boolean ready = true;
		for (int part = 0; part < bodyModels.length; part++) {
			if (!Model.isCached(bodyModels[part]))
				ready = false;
		}
		return ready;
	}

	public Model method538() {
		if (bodyModels == null) {
			return null;
		}

		Model models[] = new Model[bodyModels.length];

		for (int i = 0; i < bodyModels.length; i++) {
			models[i] = Model.getModel(bodyModels[i]);
		}

		Model model;
		if (models.length == 1) {
			model = models[0];
		} else {
			model = new Model(models.length, models);
		}

		for (int part = 0; part < 6; part++) {
			if (originalColors[part] == 0) {
				break;
			}
			model.recolor(originalColors[part], replacementColors[part]);
		}

		return model;
	}

	public boolean headLoaded() {
		boolean ready = true;
		for (int i = 0; i < 5; i++) {
			if (headModels[i] != -1 && !Model.isCached(headModels[i]))
				ready = false;
		}
		return ready;
	}

	public Model headModel() {
		Model models[] = new Model[5];
		int count = 0;
		for (int i = 0; i < 5; i++) {
			if (headModels[i] != -1) {
				models[count++] = Model.getModel(headModels[i]);
			}
		}

		Model model = new Model(count, models);
		for (int i = 0; i < 6; i++) {
			if (originalColors[i] == 0) {
				break;
			}
			model.recolor(originalColors[i], replacementColors[i]);
		}

		return model;
	}

}
