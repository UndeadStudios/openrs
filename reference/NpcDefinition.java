
public final class NpcDefinition {

    public static void unpackConfig(StreamLoader archive) {
        buffer = new Buffer(archive.getDataForName("npc.dat"));
        Buffer metaBuf = new Buffer(archive.getDataForName("npc.idx"));
        int totalNPCs = metaBuf.readUShort();

        System.out.println(String.format("Loaded: %d npcs", totalNPCs));

        offsets = new int[totalNPCs];
        int metaOffset = 2;
        for (int i = 0; i < totalNPCs; i++) {
            offsets[i] = metaOffset;
            metaOffset += metaBuf.readUShort();
        }

        cache = new NpcDefinition[20];

        for (int i = 0; i < 20; i++) {
            cache[i] = new NpcDefinition();
        }

       // ClassFieldPrinter printer = new ClassFieldPrinter();

//        try(PrintWriter writer = new PrintWriter(new File("npc_fields.txt"))) {
//            for (int i = 0; i < totalNPCs; i++) {
//                NpcDefinition def = lookup(i);
//                if (def == null || def.name == null) {
//                    continue;
//                }
//                try {
//                    printer.printFields(def, "", true);
//                } catch (Exception ex) {
//                    ex.printStackTrace();
//                    continue;
//                }
//
//            }
//            writer.print(printer.getBuilder().toString());
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        }

    }

    public static NpcDefinition lookup(int id) {
        for (int i = 0; i < 20; i++) {
            if (cache[i].interfaceType == (long) id) {
                return cache[i];
            }
        }

        anInt56 = (anInt56 + 1) % 20;
        NpcDefinition entityDef = cache[anInt56] = new NpcDefinition();
        buffer.currentOffset = offsets[id];
        entityDef.interfaceType = id;
        entityDef.decode(buffer);
        return entityDef;
    }

    public Model method160() {
        if (morphisms != null) {
            NpcDefinition entityDef = morph();
            if (entityDef == null)
                return null;
            else
                return entityDef.method160();
        }
        if (additionalModels == null)
            return null;
        boolean flag1 = false;
        for (int i = 0; i < additionalModels.length; i++)
            if (!Model.isCached(additionalModels[i]))
                flag1 = true;

        if (flag1)
            return null;
        Model aclass30_sub2_sub4_sub6s[] = new Model[additionalModels.length];
        for (int j = 0; j < additionalModels.length; j++)
            aclass30_sub2_sub4_sub6s[j] = Model.getModel(additionalModels[j]);

        Model model;
        if (aclass30_sub2_sub4_sub6s.length == 1)
            model = aclass30_sub2_sub4_sub6s[0];
        else
            model = new Model(aclass30_sub2_sub4_sub6s.length, aclass30_sub2_sub4_sub6s);
        if (recolorOriginal != null) {
            for (int k = 0; k < recolorOriginal.length; k++)
                model.recolor(recolorOriginal[k], recolorTarget[k]);

        }
        return model;
    }

    public Model getAnimatedModel(int primaryFrame, int secondaryFrame, int interleaveOrder[]) {
        if (morphisms != null) {
            NpcDefinition definition = morph();
            if (definition == null)
                return null;
            else
                return definition.getAnimatedModel(primaryFrame, secondaryFrame, interleaveOrder);
        }
        Model model = (Model) modelCache.get(interfaceType);
        if (model == null) {
            boolean flag = false;
            for (int index = 0; index < modelId.length; index++)
                if (!Model.isCached(modelId[index]))
                    flag = true;
            if (flag) {
                return null;
            }
            Model models[] = new Model[modelId.length];
            for (int index = 0; index < modelId.length; index++)
                models[index] = Model.getModel(modelId[index]);

            if (models.length == 1)
                model = models[0];
            else
                model = new Model(models.length, models);
            if (recolorOriginal != null) {
                for (int index = 0; index < recolorOriginal.length; index++)
                    model.recolor(recolorOriginal[index], recolorTarget[index]);

            }
            model.skin();
            model.light(64 + lightModifier, 850 + shadowModifier, -30, -50, -30, true);
            modelCache.put(model, interfaceType);
        }
        Model model_1 = Model.EMPTY_MODEL;
        model_1.method464(model, Frame.method532(secondaryFrame) & Frame.method532(primaryFrame));
        if (secondaryFrame != -1 && primaryFrame != -1)
            model_1.method471(interleaveOrder, primaryFrame, secondaryFrame);
        else if (secondaryFrame != -1)
            model_1.method470(secondaryFrame);
        if (scaleXZ != 128 || scaleY != 128)
            model_1.method478(scaleXZ, scaleXZ, scaleY);
        model_1.method466();
        model_1.anIntArrayArray1658 = null;
        model_1.anIntArrayArray1657 = null;
        if (size == 1)
            model_1.aBoolean1659 = true;
        return model_1;
    }

    public NpcDefinition morph() {
        int j = -1;
        if (varbit != -1) {
            Varbit varBit = Varbit.cache[varbit];
            int k = varBit.configId;
            int l = varBit.lsb;
            int i1 = varBit.msb;
            int j1 = Client.BIT_MASKS[i1 - l];
            j = clientInstance.settings[k] >> l & j1;
        } else if (varp != -1)
            j = clientInstance.settings[varp];
        if (j < 0 || j >= morphisms.length || morphisms[j] == -1)
            return null;
        else
            return lookup(morphisms[j]);
    }

    public static void clear() {
        modelCache = null;
        offsets = null;
        cache = null;
        buffer = null;
    }

    Model method164(int j, int currAnim, int nextAnim, int currCycle, int nextCycle, int ai[]) {
        if (morphisms != null) {
            final NpcDefinition type = morph();
            if (type == null) {
                return null;
            } else {
                return type.method164(j, currAnim, nextAnim, currCycle, nextCycle, ai);
            }
        }
        Model model = (Model) modelCache.get(interfaceType);
        if (model == null) {
            boolean flag = false;
            for (int i1 = 0; i1 < modelId.length; i1++) {
                if (!Model.isCached(modelId[i1])) {
                    flag = true;
                }
            }
            if (flag) {
                return null;
            }
            final Model[] parts = new Model[modelId.length];
            for (int j1 = 0; j1 < modelId.length; j1++) {
                parts[j1] = Model.getModel(modelId[j1]);
            }
            if (parts.length == 1) {
                model = parts[0];
            } else {
                model = new Model(parts.length, parts);
            }
            if (recolorOriginal != null) {
                for (int k1 = 0; k1 < recolorOriginal.length; k1++) {
                    model.recolor(recolorOriginal[k1], recolorTarget[k1]);
                }
            }
            model.skin();
            if (Settings.CUSTOM_LIGHTING) {
                model.light(84, 1000, -90, -580, -90, true);
            } else {
                model.light(64 + lightModifier, 850 + shadowModifier, -30, -50, -30, true);
            }
            modelCache.put(model, interfaceType);
        }
        final Model model_1 = Model.EMPTY_MODEL;
        model_1.method464(model, Frame.method532(currAnim) & Frame.method532(j));
        if (currAnim != -1 && j != -1) {
            model_1.method471(ai, j, currAnim);
        } else if (currAnim != -1) {
            if (Settings.TWEENING) {
                model_1.interpolateFrames(currAnim, nextAnim, nextCycle, currCycle);
            } else {
                model_1.method470(currAnim);
            }
        }
        if (scaleXZ != 128 || scaleY != 128) {
            model_1.method478(scaleXZ, scaleY, scaleXZ);
        }
        model_1.method466();
        model_1.anIntArrayArray1658 = null;
        model_1.anIntArrayArray1657 = null;
        if (size == 1) {
            model_1.aBoolean1659 = true;
        }
        return model_1;
    }

    public void decode(Buffer buffer) {
        while(true) {
            int opcode = buffer.readUByte();
            if (opcode == 0) {
                return;
            } else if (opcode == 1) {
                int len = buffer.readUByte();
                modelId = new int[len];
                for (int i = 0; i < len; i++) {
                    modelId[i] = buffer.readUShort();
                }
            } else if (opcode == 2) {
                name = buffer.readString();
            } else if (opcode == 12) {
                size = buffer.readUByte();
            } else if (opcode == 13) {
                standingAnimation = buffer.readUShort();
            } else if (opcode == 14) {
                walkingAnimation = buffer.readUShort();
            } else if (opcode == 15) {
                buffer.readUShort();
            } else if (opcode == 16) {
                buffer.readUShort();
            } else if (opcode == 17) {
                walkingAnimation = buffer.readUShort();
                halfTurnAnimation = buffer.readUShort();
                quarterClockwiseTurnAnimation = buffer.readUShort();
                quarterAnticlockwiseTurnAnimation = buffer.readUShort();
                if (halfTurnAnimation == 65535) {
                    halfTurnAnimation = walkingAnimation;
                }
                if (quarterClockwiseTurnAnimation == 65535) {
                    quarterClockwiseTurnAnimation = walkingAnimation;
                }
                if (quarterAnticlockwiseTurnAnimation == 65535) {
                    quarterAnticlockwiseTurnAnimation = walkingAnimation;
                }
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
                recolorOriginal = new int[len];
                recolorTarget = new int[len];
                for (int i = 0; i < len; i++) {
                    recolorOriginal[i] = buffer.readUShort();
                    recolorTarget[i] = buffer.readUShort();
                }

            } else if (opcode == 41) {
                int len = buffer.readUByte();

                for (int i = 0; i < len; i++) {
                    buffer.readUShort(); // textures
                    buffer.readUShort();
                }
            } else if (opcode == 60) {
                int len = buffer.readUByte();
                additionalModels = new int[len];
                for (int i = 0; i < len; i++) {
                    additionalModels[i] = buffer.readUShort();
                }
            } else if (opcode == 93) {
                aBoolean87 = false;
            } else if (opcode == 95)
                combatLevel = buffer.readUShort();
            else if (opcode == 97)
                scaleXZ = buffer.readUShort();
            else if (opcode == 98)
                scaleY = buffer.readUShort();
            else if (opcode == 99)
                aBoolean93 = true;
            else if (opcode == 100)
                lightModifier = buffer.readSignedByte();
            else if (opcode == 101)
                shadowModifier = buffer.readSignedByte();
            else if (opcode == 102)
                headIcon = buffer.readUShort();
            else if (opcode == 103)
                rotation = buffer.readUShort();
            else if (opcode == 106 || opcode == 118) {
                varbit = buffer.readUShort();

                if (varbit == 65535) {
                    varbit = -1;
                }

                varp = buffer.readUShort();

                if (varp == 65535) {
                    varp = -1;
                }

                int value = -1;

                if (opcode == 118) {
                    value = buffer.readUShort();
                }

                int len = buffer.readUByte();
                morphisms = new int[len + 2];
                for (int i = 0; i <= len; i++) {
                    morphisms[i] = buffer.readUShort();
                    if (morphisms[i] == 65535) {
                        morphisms[i] = -1;
                    }
                }
                morphisms[len + 1] = value;
            } else if (opcode == 109) {
                aBoolean84 = false;
            } else if (opcode == 107 || opcode == 111) {

            } else {
                System.out.println(String.format("npc def invalid opcode: %d", opcode));
            }
        }
    }

    public NpcDefinition() {
        quarterAnticlockwiseTurnAnimation = -1;
        varbit = -1;
        halfTurnAnimation = -1;
        varp = -1;
        combatLevel = -1;
        anInt64 = 1834;
        walkingAnimation = -1;
        size = 1;
        headIcon = -1;
        standingAnimation = -1;
        interfaceType = -1L;
        rotation = 32;
        quarterClockwiseTurnAnimation = -1;
        aBoolean84 = true;
        scaleY = 128;
        aBoolean87 = true;
        scaleXZ = 128;
        aBoolean93 = false;
    }

    public int quarterAnticlockwiseTurnAnimation;
    public static int anInt56;
    public int varbit;
    public int halfTurnAnimation;
    public int varp;
    public static Buffer buffer;
    public int combatLevel;
    public final int anInt64;
    public String name;
    public String actions[];
    public int walkingAnimation;
    public int size;
    public int[] recolorTarget;
    public static int[] offsets;
    public int[] additionalModels;
    public int headIcon;
    public int[] recolorOriginal;
    public int standingAnimation;
    public long interfaceType;
    public int rotation;
    public static NpcDefinition[] cache;
    public static Client clientInstance;
    public int quarterClockwiseTurnAnimation;
    public boolean aBoolean84;
    public int lightModifier;
    public int scaleY;
    public boolean aBoolean87;
    public int morphisms[];
    public byte description[];
    public int scaleXZ;
    public int shadowModifier;
    public boolean aBoolean93;
    public int[] modelId;
    public int interfaceZoom = 0;
    public static Cache modelCache = new Cache(30);

}
