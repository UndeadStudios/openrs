/**
* Copyright (c) Kyle Fricilone
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package net.openrs.cache.type.npcs;

import java.io.DataOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.Map;

import net.openrs.cache.type.Type;
import net.openrs.cache.util.ArrayUtils;
import net.openrs.util.BitUtils;
import net.openrs.util.ByteBufferUtils;

/**
 * @author Kyle Friz
 *
 * @since May 26, 2015
 */
public class NpcType implements Type {

	private final int id;
	private short[] colorToReplace;
	private int rotation = 32;
	public String name;
	int[] headIconArchiveIds = null;
	short[] headIconSpriteIndex = null;
	private String SGS = "null";
	private short[] colorToReplaceWith;
	public int[] models;
	public int[] models_2;
	static boolean field4275;
	private int stanceAnimation = -1;
	private int turnLeftSequence = -1;
	private int tileSpacesOccupied = -1;
	private int walkAnimation = -1;
	private short[] textureToReplaceWith;
	private int rotate90RightAnimation = -1;
	private boolean isClickable = true;
	private int resizeX = 128;
	private int contrast = 0;
	private int rotate180Animation = -1;
	private int varpId = -1;
	private String[] actions = new String[5];
	private boolean renderOnMinimap = true;
	private int combatLevel = -1;
	private int rotate90LeftAnimation = -1;
	private int resizeY = 128;
	private boolean visible = false;
	private int ambient = 0;
	private int headIcon = -1;
	private int[] configs;
	private short[] textureToReplace;
	private int varp32Id = -1;
	private boolean aBool107 = true;
	private int turnRightSequence = -1;
	private boolean aBool2190 = false;
	private int category;
	private Map<Integer, Object> params = null;
	public int field1914 = -1;
	public int field1919 = -1;
	public int field1918 = -1;
	public int field1938 = -1;
	public int field1920 = -1;
	public int field1933 = -1;
	public int field1922 = -1;
	public int field1923 = -1;
	private boolean is210 = true;
	public int archiveRevision = 1;
	public int REV_210_NPC_ARCHIVE_REV = 1493;

	private String unknown1 = "Property of titan vault.";
	public NpcType(int id) {
		this.id = id;
	}

	@Override
	public void decode(ByteBuffer buffer) {
			while (true) {
				final int opcode = buffer.get() & 0xFF;

				if (opcode == 0) {
					return;
				} else if (opcode == 1) {
					int length = buffer.get() & 0xFF;
					models = new int[length];

					for (int idx = 0; idx < length; ++idx) {
						models[idx] = buffer.getShort() & 0xFFFF;
					}

				} else if (opcode == 2) {
					name = ByteBufferUtils.getString(buffer);
				} else if (opcode == 12) {
					tileSpacesOccupied = buffer.get() & 0xFF;
				} else if (opcode == 13) {
					stanceAnimation = buffer.getShort() & 0xFFFF;
				} else if (opcode == 14) {
					walkAnimation = buffer.getShort() & 0xFFFF;
				} else if (opcode == 15) {
					turnLeftSequence = buffer.getShort() & 0xFFFF;
				} else if (opcode == 16) {
					turnRightSequence = buffer.getShort() & 0xFFFF;
				} else if (opcode == 17) {
					walkAnimation = buffer.getShort() & 0xFFFF;
					rotate180Animation = buffer.getShort() & 0xFFFF;
					rotate90RightAnimation = buffer.getShort() & 0xFFFF;
					rotate90LeftAnimation = buffer.getShort() & 0xFFFF;
				} else if (opcode == 18) {
					category = buffer.getShort() & 0xFFFF;
				} else if (opcode >= 30 && opcode < 35) {
					actions[opcode - 30] = ByteBufferUtils.getString(buffer);
					if (actions[opcode - 30].equalsIgnoreCase("Hidden")) {
						actions[opcode - 30] = null;
					}
				} else if (opcode == 40) {
					int length = buffer.get() & 0xFF;
					colorToReplace = new short[length];
					colorToReplaceWith = new short[length];

					for (int i = 0; i < length; ++i) {
						colorToReplace[i] = (short) (buffer.getShort() & 0xFFFF);
						colorToReplaceWith[i] = (short) (buffer.getShort() & 0xFFFF);
					}

				} else if (opcode == 41) {
					int length = buffer.get() & 0xFF;
					textureToReplace = new short[length];
					textureToReplaceWith = new short[length];

					for (int i = 0; i < length; ++i) {
						textureToReplace[i] = (short) (buffer.getShort() & 0xFFFF);
						textureToReplaceWith[i] = (short) (buffer.getShort() & 0xFFFF);
					}

				} else if (opcode == 60) {
					int length = buffer.get() & 0xFF;
					models_2 = new int[length];

					for (int idx = 0; idx < length; ++idx) {
						models_2[idx] = buffer.getShort() & 0xFFFF;
					}

				} else if (opcode == 93) {
					renderOnMinimap = false;
				} else if (opcode == 95) {
					combatLevel = buffer.getShort() & 0xFFFF;
				} else if (opcode == 97) {
					resizeX = buffer.getShort() & 0xFFFF;
				} else if (opcode == 98) {
					resizeY = buffer.getShort() & 0xFFFF;
				} else if (opcode == 99) {
					visible = true;
				} else if (100 == opcode) {
					ambient = buffer.get();
				} else if (101 == opcode) {
					contrast = buffer.get();
				} else if (opcode == 102) {
					if (archiveRevision >= REV_210_NPC_ARCHIVE_REV) {
						headIconArchiveIds = new int[1];
						headIconSpriteIndex = new short[1];
						headIconArchiveIds[0] = headIcon;
						headIconSpriteIndex[0] = (short) (buffer.getShort() & 0xFFFF);
					} else{
						int var3 = buffer.get() & 0xFF;
						int var4 = 0;

						for (int var5 = var3; var5 != 0; var5 >>= 1) {
							++var4;
						}

						headIconArchiveIds = new int[var4];
						headIconSpriteIndex = new short[var4];

						for (int var6 = 0; var6 < var4; ++var6) {
							if ((var3 & 1 << var6) == 0) {
								headIconArchiveIds[var6] = -1;
								headIconSpriteIndex[var6] = -1;
							} else {
								headIconArchiveIds[var6] = ByteBufferUtils.readNullableLargeSmart(buffer);
								headIconSpriteIndex[var6] = (short) ByteBufferUtils.readShortSmartSub(buffer);
							}
						}
					}
				} else if (103 == opcode) {
					rotation = buffer.getShort() & 0xFFFF;
				} else if (opcode == 106 || opcode == 118) {
					varpId = buffer.getShort() & 0xFFFF;
					if (0xFFFF == varpId) {
						varpId = -1;
					}

					varp32Id = buffer.getShort() & 0xFFFF;
					if (0xFFFF == varp32Id) {
						varp32Id = -1;
					}

					int var = -1;

					if (opcode == 118) {
						var = buffer.getShort() & 0xFFFF;

						if (var == 0xFFFF) {
							var = -1;
						}
					}

					int length = buffer.get() & 0xFF;
					configs = new int[length + 2];

					for (int idx = 0; idx <= length; ++idx) {
						configs[idx] = buffer.getShort() & 0xFFFF;
						if (configs[idx] == 0xFFFF) {
							configs[idx] = -1;
						}
					}

					configs[length + 1] = var;
				} else if (107 == opcode) {
					aBool107 = false;
				} else if (opcode == 109) {
					isClickable = false;
				} else if (opcode == 111) {
					aBool2190 = true;
				} else if (opcode == 114) {
					field1914 = (buffer.getShort() & 0xFFFF);
				} else if (opcode == 115) {
					field1914 = (buffer.getShort() & 0xFFFF);
					field1919 = (buffer.getShort() & 0xFFFF);
					field1918 = (buffer.getShort() & 0xFFFF);
					field1938 = (buffer.getShort() & 0xFFFF);
				} else if (opcode == 116) {
					field1920 = (buffer.getShort() & 0xFFFF);
				} else if (opcode == 117) {
					field1920 = (buffer.getShort() & 0xFFFF);
					field1933 = (buffer.getShort() & 0xFFFF);
					field1922 = (buffer.getShort() & 0xFFFF);
					field1923 = (buffer.getShort() & 0xFFFF);
				} else if (opcode == 249) {
					int length = buffer.get() & 0xFF;

					params = new HashMap<>(BitUtils.nextPowerOfTwo(length));
					for (int i = 0; i < length; i++) {
						boolean isString = (buffer.get() & 0xFF) == 1;
						int key = ByteBufferUtils.get24Int(buffer);
						Object value;

						if (isString) {
							value = ByteBufferUtils.getString(buffer);
						} else {
							value = buffer.getInt();
						}

						params.put(key, value);
					}
				}
			}
	}

	@Override
	public void encode(DataOutputStream dos) throws IOException {
			if (models != null && models.length > 0) {
				dos.writeByte(1);
				dos.writeByte(models.length);

				for (int i = 0; i < models.length; i++) {
					dos.writeShort(models[i]);
				}
			}

			if (name != null) {
				dos.writeByte(2);
				dos.write(ArrayUtils.toByteArray(name));
				dos.writeByte(10);
			}

			if (tileSpacesOccupied != -1) {
				dos.writeByte(12);
				dos.writeByte(tileSpacesOccupied);
			}

			if (stanceAnimation != -1) {
				dos.writeByte(13);
				dos.writeShort(stanceAnimation);
			}

			if (walkAnimation != -1) {
				dos.writeByte(14);
				dos.writeShort(walkAnimation);
			}

            if (turnLeftSequence != -1) {
                dos.writeByte(15);
                dos.writeShort(turnLeftSequence);
            }

       		 if (turnRightSequence != -1) {
			    dos.writeByte(16);
			    dos.writeShort(turnRightSequence);
        	}

			if (walkAnimation != -1 || rotate180Animation != -1 || rotate90RightAnimation != -1
					|| rotate90LeftAnimation != -1) {
				dos.writeByte(17);
				dos.writeShort(walkAnimation);
				dos.writeShort(rotate180Animation);
				dos.writeShort(rotate90RightAnimation);
				dos.writeShort(rotate90LeftAnimation);
			}
			if(category != -1){
				dos.writeByte(18);
				dos.writeShort(category);
			}
			if (actions != null && !ArrayUtils.isEmpty(actions)) {
				for (int i = 0; i < actions.length; i++) {
					if (actions[i] == null) {
						continue;
					}

					dos.writeByte(30 + i);
					dos.write(ArrayUtils.toByteArray(actions[i]));
					dos.writeByte(10);
				}
			}

			if (colorToReplace != null && colorToReplaceWith != null) {
				dos.writeByte(40);
				dos.writeByte(colorToReplace.length);

				for (int i = 0; i < colorToReplace.length; i++) {
					dos.writeShort(colorToReplace[i]);
					dos.writeShort(colorToReplaceWith[i]);
				}
			}

			if (textureToReplace != null && textureToReplaceWith != null) {
				dos.writeByte(41);
				dos.writeByte(textureToReplace.length);

				for (int i = 0; i < textureToReplace.length; i++) {
					dos.writeShort(textureToReplace[i]);
					dos.writeShort(textureToReplaceWith[i]);
				}

			}

			if (models_2 != null) {
				dos.writeByte(60);
				dos.writeByte(models_2.length);

				for (int i = 0; i < models_2.length; i++) {
					dos.writeShort(models_2[i]);
				}
			}

			if (!renderOnMinimap) {
				dos.writeByte(93);
			}

			if (combatLevel != -1) {
				dos.writeByte(95);
				dos.writeShort(combatLevel);
			}

			if (resizeX != 128) {
				dos.writeByte(97);
				dos.writeShort(resizeX);
			}

			if (resizeY != 128) {
				dos.writeByte(98);
				dos.writeShort(resizeY);
			}

			if (visible) {
				dos.writeByte(99);
			}

			if (ambient != 0) {
				dos.writeByte(100);
				dos.writeByte(ambient);
			}

			if (contrast != 0) {
				dos.writeByte(101);
				dos.writeByte(contrast);
			}

			if (headIconSpriteIndex != null) {
				dos.writeByte(102);
					dos.writeShort(headIconSpriteIndex[0]);

			}

			if (rotation != 32) {
				dos.writeByte(103);
				dos.writeShort(rotation);
			}

			if ((varpId != -1 || varp32Id != -1) && configs != null) {

			    final int var = configs[configs.length - 1];

				dos.writeByte(var != -1 ? 118 : 106);
				dos.writeShort(varpId);
				dos.writeShort(varp32Id);

				if (var != -1) {
				    dos.writeShort(var);
                }

				dos.writeByte(configs.length - 2);

				for (int i = 0; i <= configs.length - 2; i++) {
					dos.writeShort(configs[i]);
				}
			}

			if (!aBool107) {
				dos.writeByte(107);
			}

			if (!isClickable) {
				dos.writeByte(109);
			}

			if (aBool2190) {
				dos.writeByte(111);
			}
		if (field1914 != -1) {
			dos.writeByte(114);
			dos.writeShort(field1914);
		}
		if (field1914 != -1 || field1919 != -1 || field1918 != -1
				|| field1938 != -1) {
			dos.writeByte(115);
			dos.writeShort(field1914);
			dos.writeShort(field1919);
			dos.writeShort(field1918);
			dos.writeShort(field1938);
		}
		if (field1920 != -1) {
			dos.writeByte(116);
			dos.writeShort(field1920);
		}
		if (field1920 != -1 || field1933 != -1 || field1918 != -1
				|| field1938 != -1) {
			dos.writeByte(117);
			dos.writeShort(field1920);
			dos.writeShort(field1933);
			dos.writeShort(field1922);
			dos.writeShort(field1923);
		}

	}

	public int getID() {
		return id;
	}

}
