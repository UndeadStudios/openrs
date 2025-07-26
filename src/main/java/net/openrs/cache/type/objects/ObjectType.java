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
package net.openrs.cache.type.objects;

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
 * @since Oct 18, 2015
 */
public class ObjectType implements Type {

	public final int id;
	public short[] textureToFind;
	public int decorDisplacement = 16;
	public boolean isHollow = false;
	public String name;
	public int[] modelIds;
	public int[] modelTypes;
	public short[] recolorToFind;
	public short[] textureToReplace;
	public int width = 1;
	public int length = 1;
	public int anInt2083 = 0;
	public int[] anIntArray2084;
	public int offsetX = 0;
	public boolean nonFlatShading = false;
	public int interactive = -1;
	public int animation = -1;
	private String SGS = "null";
	public int varbit = -1;
	public int ambientLighting = 0;
	public int contrast = 0;
	public String[] actions = new String[5];
	public boolean clipType = true;
	public int mapscene = -1;
	public int surroundings;
	public short[] recolorToReplace;
	public boolean castsShadow = true;
	public boolean field2222 = true;
	public int scaleX = 128;
	public int scaleY = 128;
	public int scaleZ = 128;
	public int objectID;
	public int offsetZ = 0;
	public int offsetY = 0;
	public boolean obstructsGround = false;
	public int contouredGround = -1;
	public int supportItems = -1;
	public int[] morphisms;
	public boolean inverted = false;
	public int varp = -1;
	public int ambientSoundId = -1;
	public boolean modelClipped = false;
	public int anInt2112 = 0;
	public int anInt2113 = 0;
	public boolean blocksProjectile = true;
	public boolean field3621 = true;
	public int mapIcon = -1;
	public Map<Integer, Object> params = null;
	public int category = -1;
	private boolean field2118 = false;
	private int field2130 = 0;
	private short[] colorplateToFind;
	private short[] colorplateToReplace;

	public ObjectType(int id) {
		this.id = id;
	}

	@Override
	public void decode(ByteBuffer buffe) {
        decode(buffe, true);
    }
					   public void decode(ByteBuffer buffer, boolean pre220) {
		while (true) {
			int opcode = buffer.get() & 0xFF;

			if (opcode == 0) {
				break;
			}

			if (opcode == 1) {
				int length = buffer.get() & 0xFF;
				if (length > 0) {
					modelTypes = new int[length];
					modelIds = new int[length];

					for (int index = 0; index < length; ++index) {
						modelIds[index] = buffer.getShort() & 0xFFFF;
						modelTypes[index] = buffer.get() & 0xFF;
					}
				}
			} else if (opcode == 2) {
				name = ByteBufferUtils.getString(buffer);
			} else if (opcode == 5) {
				int length = buffer.get() & 0xFF;
				if (length > 0) {
					modelTypes = null;
					modelIds = new int[length];

					for (int index = 0; index < length; ++index) {
						modelIds[index] = buffer.getShort() & 0xFFFF;
					}
				}
			} else if (opcode == 14) {
				width = buffer.get() & 0xFF;
			} else if (opcode == 15) {
				length = buffer.get() & 0xFF;
			} else if (opcode == 17) {
				clipType = false;
				blocksProjectile = false;
			} else if (opcode == 18) {
				blocksProjectile = false;
			} else if (opcode == 19) {
				interactive = buffer.get() & 0xFF;
			} else if (opcode == 21) {
				contouredGround = 0;
			} else if (opcode == 22) {
				nonFlatShading = true;
			} else if (opcode == 23) {
				modelClipped = true;
			} else if (opcode == 24) {
				animation = buffer.getShort() & 0xFFFF;
				if (animation == 0xFFFF) {
					animation = -1;
				}
			} else if (opcode == 27) {
				clipType = true;
			} else if (opcode == 28) {
				decorDisplacement = buffer.get() & 0xFF;
			} else if (opcode == 29) {
				ambientLighting = buffer.get();
			} else if (opcode >= 30 && opcode < 35) {
				actions[opcode - 30] = ByteBufferUtils.getString(buffer);
				if (actions[opcode - 30].equalsIgnoreCase("Hidden")) {
					actions[opcode - 30] = null;
				}
			} else if (opcode == 39) {
				contrast = buffer.get() * 5;
			} else if (opcode == 40) {
				int length = buffer.get() & 0xFF;
				recolorToFind = new short[length];
				recolorToReplace = new short[length];

				for (int index = 0; index < length; ++index) {
					recolorToFind[index] = (short) (buffer.getShort() & 0xFFFF);
					recolorToReplace[index] = (short) (buffer.getShort() & 0xFFFF);
				}

			} else if (opcode == 41) {
				int length = buffer.get() & 0xFF;
				textureToFind = new short[length];
				textureToReplace = new short[length];

				for (int index = 0; index < length; ++index) {
					textureToFind[index] = (short) (buffer.getShort() & 0xFFFF);
					textureToReplace[index] = (short) (buffer.getShort() & 0xFFFF);
				}
			} else if (opcode == 44) {
				int length = buffer.get() & 0xFF;
				colorplateToFind = new short[length];
				colorplateToReplace = new short[length];
				for (int index = 0; index < length; ++index) {
					colorplateToFind[index] = (short) (buffer.getShort() & 0xFFFF);
					colorplateToReplace[index] = (short) (buffer.getShort() & 0xFFFF);
				}
			} else if(opcode == 61) {
				category = buffer.getShort() & 0xFFFF;
			} else if (opcode == 62) {
				inverted = true;
			} else if (opcode == 64) {
				castsShadow = false;
			} else if (opcode == 65) {
				scaleX = buffer.getShort() & 0xFFFF;
			} else if (opcode == 66) {
				scaleY = buffer.getShort() & 0xFFFF;
			} else if (opcode == 67) {
				scaleZ = buffer.getShort() & 0xFFFF;
			} else if (opcode == 68) {
				mapscene = buffer.getShort() & 0xFFFF;
			} else if (opcode == 69) {
				surroundings = buffer.get() & 0xFF;
			} else if (opcode == 70) {
				offsetX = buffer.getShort() & 0xFFFF;
			} else if (opcode == 71) {
				offsetZ = buffer.getShort() & 0xFFFF;
			} else if (opcode == 72) {
				offsetY = buffer.getShort() & 0xFFFF;
			} else if (opcode == 73) {
				obstructsGround = true;
			} else if (opcode == 74) {
				isHollow = true;
			} else if (opcode == 75) {
				supportItems = buffer.get() & 0xFF;
			} else if (opcode == 77) {
				varbit = buffer.getShort() & 0xFFFF;
				if (varbit == 0xFFFF) {
					varbit = -1;
				}

				varp = buffer.getShort() & 0xFFFF;
				if (varp == 0xFFFF) {
					varp = -1;
				}

				int length = buffer.get() & 0xFF;
				morphisms = new int[length + 2];

				for (int index = 0; index <= length; ++index) {
					morphisms[index] = buffer.getShort() & 0xFFFF;
					if (0xFFFF == morphisms[index]) {
						morphisms[index] = -1;
					}
				}

				morphisms[length + 1] = -1;
			} else if (opcode == 78) {
				ambientSoundId = buffer.getShort() & 0xFFFF;
				anInt2083 = buffer.get() & 0xFF;
				if(pre220) {
					this.field2130 = ((buffer.get() & 0xFF) * 128);
				}
			} else if (opcode == 79) {
				anInt2112 = buffer.getShort() & 0xFFFF;
				anInt2113 = buffer.getShort() & 0xFFFF;
				anInt2083 = (buffer.get() & 0xFF) * 128;
				if(pre220) {
					this.field2130 = ((buffer.get() & 0xFF) * 128);
				}
				int length = buffer.get() & 0xFF;
				anIntArray2084 = new int[length];

				for (int index = 0; index < length; ++index) {
					anIntArray2084[index] = buffer.getShort() & 0xFFFF;
				}
			} else if (opcode == 81) {
				contouredGround = buffer.get() & 0xFF;
			} else if (opcode == 82) {
				mapIcon = buffer.getShort() & 0xFFFF;
			} else if(opcode == 89) {
				field3621 = false;
			} else if(opcode == 90){
				field2222 = true;
			} else if (opcode == 92) {
				varbit = buffer.getShort() & 0xFFFF;
				if (varbit == 0xFFFF) {
					varbit = -1;
				}

				varp = buffer.getShort() & 0xFFFF;

				if (varp == 0xFFFF) {
					varp = -1;
				}

				int var = buffer.getShort() & 0xFFFF;
				if (var == 0xFFFF) {
					var = -1;
				}

				int length = buffer.get() & 0xFF;
				morphisms = new int[length + 2];

				for (int index = 0; index <= length; ++index) {
					morphisms[index] = buffer.getShort() & 0xFFFF;
					if (0xFFFF == morphisms[index]) {
						morphisms[index] = -1;
					}
				}

				morphisms[length + 1] = var;
			} else if (opcode == 249) {
				int length = buffer.get() & 0xFF;

				params = new HashMap<>(BitUtils.nextPowerOfTwo(length));
				for (int i = 0; i < length; i++) {
					boolean isString = (buffer.get() & 0xFF) == 1;
					int key = ByteBufferUtils.get24Int(buffer);
					Object value;

					if (isString) {
						value = ByteBufferUtils.getString(buffer);
					}

					else {
						value = buffer.getInt();
					}

					params.put(key, value);
				}
			} else {
				//System.out.println("missing opcode:" + opcode);
			}
		}
	}

	@Override
	public void encode(DataOutputStream buffe) throws IOException {
		encode(buffe, true);
	}
	public void encode(DataOutputStream dos, boolean pre220) throws IOException {
		if (modelIds != null) {
			if (modelTypes == null) {
				dos.writeByte(5);
				dos.writeByte(modelIds.length);
				if (modelIds.length > 0) {
					for (int i = 0; i < modelIds.length; i++) {
						dos.writeShort(modelIds[i]);
					}
				}
			} else {
				dos.writeByte(1);
				dos.writeByte(modelIds.length);

				if (modelIds.length > 0) {
					for (int i = 0; i < modelIds.length; i++) {
						dos.writeShort(modelIds[i]);
						dos.writeByte(modelTypes[i]);
					}
				}
			}
		}

		// good
		if (name != null) {
			dos.writeByte(2); // good
			dos.write(ArrayUtils.toByteArray(name));
			dos.writeByte(10);
		}

		if (width != 1) { // good
			dos.writeByte(14);
			dos.writeByte(width);
		}

		if (length != 1) { // good
			dos.writeByte(15);
			dos.writeByte(length);
		}

		if (!clipType || !blocksProjectile) { // good
			dos.writeByte(17);
		}

		if (!blocksProjectile) { // good
			dos.writeByte(18);
		}

		if (interactive != -1) { // good
			dos.writeByte(19);
			dos.writeByte(interactive);
		}

		if (contouredGround == 0) { // good
			dos.writeByte(21);
		}

		if (nonFlatShading) { // good
			dos.writeByte(22);
		}

		if (modelClipped) { // good
			dos.writeByte(23);
		}

		if (animation != -1) { // good
			dos.writeByte(24);
			dos.writeShort(animation);
		}

		if (clipType) { // good
			dos.writeByte(27);
		}

		if (decorDisplacement != 16) { // good
			dos.writeByte(28);
			dos.writeByte(decorDisplacement);
		}

		if (ambientLighting != 0) { // good
			dos.writeByte(29);
			dos.writeByte(ambientLighting);
		}

		if (contrast != 0) { // good
			dos.writeByte(39);
			dos.writeByte(contrast / 25);
		}

		if (actions != null && !ArrayUtils.isEmpty(actions)) { // good
			for (int i = 0; i < actions.length; i++) {
				if (actions[i] == null) {
					continue;
				}
				dos.write(30 + i);
				dos.write(ArrayUtils.toByteArray(actions[i]));
				dos.writeByte(10);
			}
		}

		if (recolorToFind != null && recolorToReplace != null) { // good
			dos.writeByte(40);
			dos.writeByte(recolorToFind.length);

			for (int i = 0; i < recolorToFind.length; i++) {
				dos.writeShort(recolorToFind[i]);
				dos.writeShort(recolorToReplace[i]);
			}
		}

		if (textureToFind != null && textureToReplace != null) { // good
			dos.writeByte(41);
			dos.writeByte(textureToFind.length);

			for (int i = 0; i < textureToFind.length; i++) {
				dos.writeShort(textureToFind[i]);
				dos.writeShort(textureToReplace[i]);
			}
		}
		if (category != -1) { // good
			dos.writeByte(61);
			dos.writeShort(category);
		}
		if (inverted) { // good
			dos.writeByte(62);
		}

		if (!castsShadow) { // good
			dos.writeByte(64);
		}

		if (scaleX != 128) { // good
			dos.writeByte(65);
			dos.writeShort(scaleX);
		}

		if (scaleY != 128) { // good
			dos.writeByte(66);
			dos.writeShort(scaleY);
		}

		if (scaleZ != 128) { // good
			dos.writeByte(67);
			dos.writeShort(scaleZ);
		}

		if (mapscene != -1) { // good
			dos.writeByte(68);
			dos.writeShort(mapscene);
		}

		if (surroundings != 0) { // good
		    dos.writeByte(69);
		    dos.writeByte(surroundings);
        }

		if (offsetX != 0) { // good
			dos.writeByte(70);
			dos.writeShort(offsetX);
		}

		if (offsetZ != 0) { // good
			dos.writeByte(71);
			dos.writeShort(offsetZ);
		}

		if (offsetY != 0) { // good
			dos.writeByte(72);
			dos.writeShort(offsetY);
		}

		if (obstructsGround) { // good
			dos.writeByte(73);
		}

		if (isHollow) { // good
			dos.writeByte(74);
		}

		if (supportItems != -1) { // good
			dos.writeByte(75);
			dos.writeByte(supportItems);
		}

		if (ambientSoundId != -1 || anInt2083 != 0 || field2130 != 0) { // good
			dos.writeByte(78);
			dos.writeShort(ambientSoundId);
			dos.writeByte(anInt2083);
			//if(pre220) {
				dos.writeByte(field2130);
		//	}
		}

		if ((anInt2112 != 0 || anInt2113 != 0 || anInt2083 != 0  || field2130 != 0) && anIntArray2084 != null) { // good
			dos.writeByte(79);
			dos.writeShort(anInt2112);
			dos.writeShort(anInt2113);
			dos.writeByte(anInt2083);
			//if(pre220) {
				dos.writeByte(field2130);
			//}
			dos.writeByte(anIntArray2084.length);
			for (int i = 0; i < anIntArray2084.length; i++) {
				dos.writeShort(anIntArray2084[i]);
			}
		}

		if (contouredGround != -1) { // good
			dos.writeByte(81);
			dos.writeByte(contouredGround);
		}

		if (mapIcon != -1) { // good
			dos.writeByte(82);
			dos.writeShort(mapIcon);
		}
		if (!field3621) { // good
			dos.writeByte(89);
		}
		if (!field2222) { // good
			dos.writeByte(90);
		}
		if ((varbit != -1 || varp != -1)
				&& (morphisms != null && morphisms.length > 0)) {

		    int value = morphisms[morphisms.length - 1];

			dos.writeByte(value != -1 ? 92 : 77);
			dos.writeShort(varbit);
			dos.writeShort(varp);

            if (value != -1) {
                dos.writeShort(morphisms[morphisms.length - 1]);
            }

			dos.writeByte(morphisms.length - 2);
			for (int i = 0; i <= morphisms.length - 2; i++) {
				dos.writeShort(morphisms[i]);
			}
		}
	}

	@Override
	public int getID() {
		return id;
	}

	public int getMapAreaId() {
		return mapIcon;
	}
}
