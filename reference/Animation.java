import java.util.Arrays;

public final class Animation {

	public static void unpackConfig(StreamLoader archive) {
		Buffer buffer = new Buffer(archive.getDataForName("seq.dat"));

		final int length = buffer.readUShort();

		if (animations == null) {
			animations = new Animation[length];
		}

		for (int i = 0; i < length; i++) {
			if (animations[i] == null) {
				animations[i] = new Animation();
			}

			animations[i].decode(buffer);
		}
	}


	public int method258(int i) {
		int j = durations[i];
		if (j == 0) {
			Frame class36 = Frame.method531(primaryFrames[i] );
			if (class36 != null)
				j = durations[i] = class36.anInt636;
		}
		if (j == 0)
			j = 1;
		return j;
	}

	private void decode(Buffer buffer) {
		while(true) {
			final int opcode = buffer.readUByte();

			if (opcode == 0) {
				break;
			} else if (opcode == 1) {
				frameCount = buffer.readUShort();
				primaryFrames = new int[frameCount];
				secondaryFrames = new int[frameCount];
				durations = new int[frameCount];

				for (int i = 0; i < frameCount; i++) {
					durations[i] = buffer.readUShort();
				}

				for (int i = 0; i < frameCount; i++) {
					primaryFrames[i] = buffer.readUShort();
					secondaryFrames[i] = -1;
				}

				for (int i = 0; i < frameCount; i++) {
					primaryFrames[i] += buffer.readUShort() << 16;
				}

//				for (int i = 0; i < frameCount; i++) { // walking works but godswords break
//					primaryFrames[i] = buffer.readInt();
//					secondaryFrames[i] = -1;
//				}

//                for (int i = 0; i < frameCount; i++) { // walking breaks godswords work
//                    primaryFrames[i] = buffer.readUShort();
//                    secondaryFrames[i] = -1;
//                }
//
//                for (int i = 0; i < frameCount; i++) {
//                    primaryFrames[i] =+ buffer.readUShort() << 16;
//                }




			} else if (opcode == 2) {
				loopOffset = buffer.readUShort();
			} else if (opcode == 3) {
				int len = buffer.readUByte();
				interleaveOrder = new int[len + 1];
				for (int i = 0; i < len; i++) {
					interleaveOrder[i] = buffer.readUByte();
				}
				interleaveOrder[len] = 9999999;
			} else if (opcode == 4) {
				stretches = true;
			} else if (opcode == 5) {
				forcedPriority = buffer.readUByte();
			} else if (opcode == 6) {
				playerOffhand = buffer.readUShort();
			} else if (opcode == 7) {
				playerMainhand = buffer.readUShort();
			} else if (opcode == 8) {
				maxLoops = buffer.readUByte();
			} else if (opcode == 9) {
				animatingPrecedence = buffer.readUByte();
			} else if (opcode == 10) {
				walkingPrecedence = buffer.readUByte();
			} else if (opcode == 11) {
				replayMode = buffer.readUByte();
			} else if (opcode == 12) {
				int len = buffer.readUByte();

				for (int i = 0; i < len; i++) {
					buffer.readUShort();
				}

				for (int i = 0; i < len; i++) {
					buffer.readUShort();
				}
			} else if (opcode == 13) {
				int len = buffer.readUByte();

				for (int i = 0; i < len; i++) {
					buffer.read24Int();
				}
			}
		}

		if (frameCount == 0) {
			frameCount = 1;
			primaryFrames = new int[1];
			primaryFrames[0] = -1;
			secondaryFrames = new int[1];
			secondaryFrames[0] = -1;
			durations = new int[1];
			durations[0] = -1;
		}

		if (animatingPrecedence == -1) {
			animatingPrecedence = (interleaveOrder == null) ? 0 : 2;
		}

		if (walkingPrecedence == -1) {
			walkingPrecedence = (interleaveOrder == null) ? 0 : 2;
		}
	}

	private Animation() {
		animatingPrecedence = -1; //Stops character from moving
		walkingPrecedence = -1;
		replayMode = 1;
	}

	public static Animation animations[];
	public int frameCount;
	public int primaryFrames[];
	public int secondaryFrames[];
	public int[] durations;
	public int loopOffset = -1;
	public int interleaveOrder[];
	public boolean stretches;
	public int forcedPriority = 5;
	public int playerOffhand = -1;
	public int playerMainhand = -1;
	public int maxLoops = 99;
	public int animatingPrecedence;
	public int walkingPrecedence;
	public int replayMode;

}
