public final class ObjectDefinition {

	public static void unpackConfig(RSArchive archive) {
		dataBuf = new Buffer(archive.getDataForName("loc.dat"));
		Buffer metaBuf = new Buffer(archive.getDataForName("loc.idx"));
		int totalObjects = metaBuf.readUShort();

		System.out.println(String.format("Loaded: %d objects", totalObjects));

		offsets = new int[totalObjects];

		int metaOffset = 2;
		for (int i = 0; i < totalObjects; i++) {
			offsets[i] = metaOffset;
			metaOffset += metaBuf.readUShort();
		}

		cache = new ObjectDefinition[20];

		for (int index = 0; index < 20; index++) {
			cache[index] = new ObjectDefinition();
		}
	}

	static ObjectDefinition lookup(int id) {
		if (id > offsets.length) {
			id = offsets.length - 1;
		}

		for (int j = 0; j < 20; j++) {
			if (cache[j].type == id) {
				return cache[j];
			}
		}

		cacheIndex = (cacheIndex + 1) % 20;
		ObjectDefinition def = cache[cacheIndex];
		dataBuf.currentOffset = offsets[id];
		def.type = id;
		def.setDefaults();
		def.decode(dataBuf);
		return def;
	}

	public void setDefaults() {
		modelIds = null;
		modelTypes = null;
		description = null;
		modifiedModelColors = null;
		originalModelColors = null;
		originalTexture = null;
		modifiedTexture = null;
		width = 1;
		length = 1;
		solid = true;
		impenetrable = true;
		interactive = false;
		contouredGround = false;
		nonFlatShading = false;
		modelClipped = false;
		animation = -1;
		decorDisplacement = 16;
		ambientLighting = 0;
		contrast = 0;
		actions = null;
		mapIcon = -1;
		mapscene = -1;
		inverted = false;
		castsShadow = true;
		scaleX = 128;
		scaleY = 128;
		scaleZ = 128;
		surroundings = 0;
		translateX = 0;
		translateY = 0;
		translateZ = 0;
		obstructsGround = false;
		removeClipping = false;
		supportItems = -1;
		varbit = -1;
		varp = -1;
		morphisms = null;
	}

	public void method574(ResourceProvider provider) {
		if (modelIds == null)
			return;
		for (int j = 0; j < modelIds.length; j++) {
			System.out.println(modelIds[j] & 0xffff);
			provider.method560(modelIds[j] & 0xffff, 0);
		}
	}

	public static void nullLoader() {
		mruNodes1 = null;
		mruNodes2 = null;
		offsets = null;
		cache = null;
		dataBuf = null;
	}

	public boolean method577(int i) {
		Model model = (Model) mruNodes2.get(type);
		if (modelTypes == null) {
			if (modelIds == null)
				return true;
			if (i != 10)
				return true;
			boolean flag1 = true;
			for (int k = 0; k < modelIds.length; k++)
				flag1 &= Model.isCached(modelIds[k] & 0xffff);
			return flag1;
		}
		for (int j = 0; j < modelTypes.length; j++)
			if (modelTypes[j] == i)
				return Model.isCached(modelIds[j] & 0xffff);
		return true;
	}

	public Model method578(int i, int j, int k, int l, int i1, int j1, int k1) {
		Model model = method581(i, k1, j);
		if (model == null)
			return null;
		if (contouredGround || nonFlatShading)
			model = new Model(contouredGround, nonFlatShading, model);
		if (contouredGround) {
			int l1 = (k + l + i1 + j1) / 4;
			for (int i2 = 0; i2 < model.anInt1626; i2++) {
				int j2 = model.anIntArray1627[i2];
				int k2 = model.anIntArray1629[i2];
				int l2 = k + ((l - k) * (j2 + 64)) / 128;
				int i3 = j1 + ((i1 - j1) * (j2 + 64)) / 128;
				int j3 = l2 + ((i3 - l2) * (k2 + 64)) / 128;
				model.anIntArray1628[i2] += j3 - l1;
			}
			model.method467();
		}
		return model;
	}

	public boolean method579() {
		if (modelIds == null)
			return true;
		boolean flag1 = true;
		for (int i = 0; i < modelIds.length; i++)
			flag1 &= Model.isCached(modelIds[i] & 0xffff);
		return flag1;
	}

	public ObjectDefinition morph() {
		int morphismIndex = -1;
		if (varbit != -1) {
			Varbit bits = Varbit.cache[varbit];
			int variable = bits.configId;
			int low = bits.lsb;
			int high = bits.msb;
			int mask = Client.BIT_MASKS[high - low];
			morphismIndex = clientInstance.settings[variable] >> low & mask;
		} else if (varp != -1)
			morphismIndex = clientInstance.settings[varp];

		int var;

		if (morphismIndex >= 0 && morphismIndex < morphisms.length) {
			var = morphisms[morphismIndex];
		} else {
			var = morphisms[morphisms.length - 1];
		}

		return var != -1 ? lookup(var) : null;
	}

	public Model method581(int j, int k, int l) {
		Model model = null;
		long l1;
		if (modelTypes == null) {
			if (j != 10)
				return null;
			l1 = (long) ((type << 8) + l) + ((long) (k + 1) << 32);
			Model model_1 = (Model) mruNodes2.get(l1);
			if (model_1 != null)
				return model_1;
			if (modelIds == null)
				return null;
			boolean flag1 = inverted ^ (l > 3);
			int k1 = modelIds.length;
			for (int i2 = 0; i2 < k1; i2++) {
				int l2 = modelIds[i2];
				if (flag1)
					l2 += 0x10000;
				model = (Model) mruNodes1.get(l2);
				if (model == null) {
					model = Model.getModel(l2 & 0xffff);
					if (model == null)
						return null;
					if (flag1)
						model.method477();
					mruNodes1.put(model, l2);
				}
				if (k1 > 1)
					aModelArray741s[i2] = model;
			}

			if (k1 > 1)
				model = new Model(k1, aModelArray741s);
		} else {
			int i1 = -1;
			for (int j1 = 0; j1 < modelTypes.length; j1++) {
				if (modelTypes[j1] != j)
					continue;
				i1 = j1;
				break;
			}

			if (i1 == -1)
				return null;
			l1 = (long) ((type << 8) + (i1 << 3) + l) + ((long) (k + 1) << 32);
			Model model_2 = (Model) mruNodes2.get(l1);
			if (model_2 != null)
				return model_2;
			int j2 = modelIds[i1];
			boolean flag3 = inverted ^ (l > 3);
			if (flag3)
				j2 += 0x10000;
			model = (Model) mruNodes1.get(j2);
			if (model == null) {
				model = Model.getModel(j2 & 0xffff);
				if (model == null)
					return null;
				if (flag3)
					model.method477();
				mruNodes1.put(model, j2);
			}
		}
		boolean flag;
		flag = scaleX != 128 || scaleY != 128 || scaleZ != 128;
		boolean flag2;
		flag2 = translateX != 0 || translateY != 0 || translateZ != 0;
		Model model_3 = new Model(modifiedModelColors == null, Skeleton.isInvalid(k), l == 0 && k == -1 && !flag && !flag2, modifiedTexture == null, model);
		if (k != -1) {
			model_3.skin();
			model_3.method470(k);
			model_3.anIntArrayArray1658 = null;
			model_3.anIntArrayArray1657 = null;
		}
		while (l-- > 0)
			model_3.method473();
		if (modifiedModelColors != null) {
			for (int k2 = 0; k2 < modifiedModelColors.length; k2++)
				model_3.recolor(modifiedModelColors[k2], originalModelColors[k2]);

		}
		if (modifiedTexture != null) {
			for (int k2 = 0; k2 < modifiedTexture.length; k2++)
				model_3.retexture(modifiedTexture[k2], originalTexture[k2]);

		}
		if (flag)
			model_3.method478(scaleX, scaleZ, scaleY);
		if (flag2)
			model_3.method475(translateX, translateY, translateZ);

		if (Settings.CUSTOM_LIGHTING) {
			model_3.light(84, 1500, -90, -280, -70, !nonFlatShading);
		} else {
			model_3.light(64 + ambientLighting, 768 + contrast * 5, -50, -10, -50, !nonFlatShading);
		}
		if (supportItems == 1)
			model_3.anInt1654 = model_3.modelHeight;
		mruNodes2.put(model_3, l1);
		return model_3;
	}

	public void decode(Buffer buffer) {
		while(true) {
			int opcode = buffer.readUByte();

			if (opcode == 0) {
				break;
			} else if (opcode == 1) {
				int len = buffer.readUByte();
				if (len > 0) {
					if (modelIds == null) {
						modelTypes = new int[len];
						modelIds = new int[len];

						for (int i = 0; i < len; i++) {
							modelIds[i] = buffer.readUShort();
							modelTypes[i] = buffer.readUByte();
						}
					} else {
						buffer.currentOffset += len * 3;
					}
				}
			} else if (opcode == 2) {
				name = buffer.readString();
			} else if (opcode == 5) {
				int len = buffer.readUByte();
				if (len > 0) {
					if (modelIds == null) {
						modelTypes = null;
						modelIds = new int[len];
						for (int i = 0; i < len; i++) {
							modelIds[i] = buffer.readUShort();
						}
					} else {
						buffer.currentOffset += len * 2;
					}
				}
			} else if (opcode == 14) {
				width = buffer.readUByte();
			} else if (opcode == 15) {
				length = buffer.readUByte();
			} else if (opcode == 17) {
				solid = false;
			} else if (opcode == 18) {
				impenetrable = false;
			} else if (opcode == 19) {
				interactive = (buffer.readUByte() == 1);
			} else if (opcode == 21) {
				contouredGround = true;
			} else if (opcode == 22) {
				nonFlatShading = true;
			} else if (opcode == 23) {
				modelClipped = true;
			} else if (opcode == 24) {
				animation = buffer.readUShort();
				if (animation == 0xFFFF) {
					animation = -1;
				}
			} else if (opcode == 27) {
				clipType = 1;
			} else if (opcode == 28) {
				decorDisplacement = buffer.readUByte();
			} else if (opcode == 29) {
				ambientLighting = buffer.readSignedByte();
			} else if (opcode == 39) {
				contrast = buffer.readSignedByte() * 25;
			} else if (opcode >= 30 && opcode < 35) {
				if (actions == null) {
					actions = new String[5];
				}
				actions[opcode - 30] = buffer.readString();
				if (actions[opcode - 30].equalsIgnoreCase("Hidden")) {
					actions[opcode - 30] = null;
				}
			} else if (opcode == 40) {
				int len = buffer.readUByte();
				modifiedModelColors = new int[len];
				originalModelColors = new int[len];
				for (int i = 0; i < len; i++) {
					modifiedModelColors[i] = buffer.readUShort();
					originalModelColors[i] = buffer.readUShort();
				}
			} else if (opcode == 41) {
				int len = buffer.readUByte();
				modifiedTexture = new short[len];
				originalTexture = new short[len];
				for (int i = 0; i < len; i++) {
					modifiedTexture[i] = (short) buffer.readUShort();
					originalTexture[i] = (short) buffer.readUShort();
				}

			} else if (opcode == 62) {
				inverted = true;
			} else if (opcode == 64) {
				castsShadow = false;
			} else if (opcode == 65) {
				scaleX = buffer.readUShort();
			} else if (opcode == 66) {
				scaleY = buffer.readUShort();
			} else if (opcode == 67) {
				scaleZ = buffer.readUShort();
			} else if (opcode == 68) {
				mapscene = buffer.readUShort();
			} else if (opcode == 69) {
				surroundings = buffer.readUByte();
			} else if (opcode == 70) {
				translateX = buffer.readUShort();
			} else if (opcode == 71) {
				translateY = buffer.readUShort();
			} else if (opcode == 72) {
				translateZ = buffer.readUShort();
			} else if (opcode == 73) {
				obstructsGround = true;
			} else if (opcode == 74) {
				removeClipping = true;
			} else if (opcode == 75) {
				supportItems = buffer.readUByte();
			} else if (opcode == 78) {
				buffer.readUShort(); // ambient sound id
				buffer.readUByte();
			} else if (opcode == 79) {
				buffer.readUShort();
				buffer.readUShort();
				buffer.readUByte();
				int len = buffer.readUByte();

				for (int i = 0; i < len; i++) {
					buffer.readUShort();
				}
			} else if (opcode == 81) {
				buffer.readUByte();
			} else if (opcode == 82) {
				mapIcon = buffer.readUShort();

				if (mapIcon == 0xFFFF) {
					mapIcon = -1;
				}
			} else if (opcode == 77 || opcode == 92) {
				varbit = buffer.readUShort();

				if (varp == 0xFFFF) {
					varp = -1;
				}

				varp = buffer.readUShort();

				if (varbit == 0xFFFF) {
					varbit = -1;
				}

				int value = -1;

				if (opcode == 92) {
                    value = buffer.readUShort();

                    if (value == 0xFFFF) {
                        value = -1;
                    }
                }

				int len = buffer.readUByte();

				morphisms = new int[len + 2];
				for (int i = 0; i <= len; ++i) {
					morphisms[i] = buffer.readUShort();
					if (morphisms[i] == 0xFFFF) {
						morphisms[i] = -1;
					}
				}
				morphisms[len + 1] = value;
			} else {
				System.out.println("invalid opcode: " + opcode);
			}

		}

		if (!interactive) {
			this.interactive = ((modelIds != null && (modelTypes == null || modelTypes[0] == 10)) || actions != null);
		}

		if (removeClipping) {
			solid = false;
			impenetrable = false;
		}

		if (supportItems == -1) {
			supportItems = solid ? 1 : 0;
		}
	}

	public ObjectDefinition() {
		type = -1;
	}

	private short[] originalTexture;
	private short[] modifiedTexture;
	public boolean obstructsGround;
	public byte ambientLighting;
	public int translateX;
	public String name;
	public int scaleZ;
	public static final Model[] aModelArray741s = new Model[4];
	public int contrast;
	public int width;
	public int translateY;
	public int mapIcon;
	public int[] originalModelColors;
	public int scaleX;
	public int varp;
	public boolean inverted;
	public static boolean lowMem;
	public static Buffer dataBuf;
	public int type;
	public static int[] offsets;
	public boolean impenetrable;
	public int mapscene;
	public int morphisms[];
	public int supportItems;
	public int length;
	public boolean contouredGround;
	public boolean modelClipped;
	public static Client clientInstance;
	public boolean removeClipping;
	public boolean solid;
	public int surroundings;
	public boolean nonFlatShading;
	public static int cacheIndex;
	public int scaleY;
	public int[] modelIds;
	public int varbit;
	public int decorDisplacement;
	public int[] modelTypes;
	public byte description[];
	public boolean interactive;
	public boolean castsShadow;
	public static Cache mruNodes2 = new Cache(30);
	public int animation;
	public static ObjectDefinition[] cache;
	public int translateZ;
	public int[] modifiedModelColors;
	public static Cache mruNodes1 = new Cache(500);
	public String[] actions;
	int clipType = 2;
}
