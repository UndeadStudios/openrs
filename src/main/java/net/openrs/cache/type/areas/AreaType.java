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
package net.openrs.cache.type.areas;

import java.io.DataOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;

import net.openrs.cache.type.Type;
import net.openrs.cache.util.ArrayUtils;
import net.openrs.util.ByteBufferUtils;

/**
 * 
 * Created by Kyle Fricilone on Jun 1, 2017.
 */
public class AreaType implements Type {

	private final int id;

	public int spriteId = -1;
	public int anInt1967 = -1;
	public String name;
	public int anInt1959;
	public int anInt1968 = 0;
	public int[] anIntArray1982;
	public String aString1970;
	public int[] anIntArray1981;
	protected int category;
	public byte[] aByteArray1979;
	public String[] aStringArray1969 = new String[5];
	public int field3297;
	private boolean field1944;
	private boolean field1945;
	private int flags;
	private byte opcode8;
	private int int21;
	private int opcode18;
	private int int22;
	private byte int23_1;
	private byte int23_2;
	private byte int23_3;
	private short int24_1;
	private short int24_2;
	private int int25;
	private byte int28;
	private byte int29;
	private byte int30;

	public AreaType(int id) {

		this.id = id;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see net.openrs.cache.type.Type#decode(java.nio.ByteBuffer)
	 */
	@Override
	public void decode(ByteBuffer buffer) {
		while (true) {
			int opcode = buffer.get() & 0xFF;
			if (opcode == 0)
				break;

			if (opcode == 1) {
				spriteId = ByteBufferUtils.getSmartInt(buffer);
			} else if (opcode == 2) {
				anInt1967 = ByteBufferUtils.getSmartInt(buffer);
			} else if (opcode == 3) {
				name = ByteBufferUtils.getString(buffer);
			} else if (opcode == 4) {
				anInt1959 = ByteBufferUtils.get24Int(buffer);
			} else if (opcode == 5) {
				field3297 = ByteBufferUtils.get24Int(buffer);
			} else if (opcode == 6) {
				anInt1968 = buffer.get() & 0xFF;
			} else if (opcode == 7) {
				flags = buffer.get() & 0xFF;
				if ((flags & 0x1) == 0) {
					this.field1944 = false;
				}
				if ((flags & 0x2) == 2) {
					this.field1945 = true;
				}
			} else if (opcode == 8) {
				opcode8 = buffer.get();
			} else if (opcode >= 10 && opcode <= 14) {
				aStringArray1969[opcode - 10] = ByteBufferUtils.getString(buffer);
			} else if (opcode == 15) {
				int size = buffer.get() & 0xFF;
				anIntArray1982 = new int[size * 2];

				for (int i = 0; i < size * 2; ++i) {
					anIntArray1982[i] = buffer.getShort();
				}

				buffer.getInt();
				int size2 = buffer.get() & 0xFF;
				anIntArray1981 = new int[size2];

				for (int i = 0; i < anIntArray1981.length; ++i) {
					anIntArray1981[i] = buffer.getInt();
				}

				aByteArray1979 = new byte[size];

				for (int i = 0; i < size; ++i) {
					aByteArray1979[i] = buffer.get();
				}
			} else if (opcode == 17) {
				aString1970 = ByteBufferUtils.getString(buffer);
			} else if (opcode == 18) {
				opcode18 = ByteBufferUtils.getSmartInt(buffer);
			} else if (opcode == 19) {
				category = buffer.getShort() & 0xFFFF;
			} else if (opcode == 21) {
				int21 = buffer.getInt();
			} else if (opcode == 22) {
				int22 = buffer.getInt();
			} else if (opcode == 23) {
				int23_1 = buffer.get();
				int23_2 = buffer.get();
				int23_3 = buffer.get();
			} else if (opcode == 24) {
				int24_1 = buffer.getShort();
				int24_2 = buffer.getShort();
			} else if (opcode == 25) {
				int25 = ByteBufferUtils.getSmartInt(buffer);
			} else if (opcode == 28) {
				int28 = buffer.get();
			} else if (opcode == 29) {
				int29 = buffer.get();
			} else if (opcode == 30) {
				int30 = buffer.get();
			}
		}
	}

	@Override
	public void encode(DataOutputStream dos) throws IOException {
		if (spriteId != -1) {
			dos.writeByte(1);
			dos.writeInt(spriteId);
		}
		if (anInt1967 != -1) {
			dos.writeByte(2);
			dos.writeInt(anInt1967);
		}
		if (name != null) {
			dos.writeByte(3);
			dos.write(ArrayUtils.toByteArray(name));
			dos.writeByte(10);
		}
		if (anInt1959 != -1) {
			dos.writeByte(4);
			dos.writeInt(anInt1959);
		}
		if (field3297 != -1) {
			dos.writeByte(5);
			dos.writeInt(field3297);
		}
		if (anInt1968 != -1) {
			dos.writeByte(6);
			dos.writeInt(anInt1968);
		}
		if (!field1944 || !field1945) {
			dos.writeByte(7);
			dos.writeByte(flags);
		}
		if(opcode8 != -1){
			dos.writeByte(8);
			dos.writeByte(opcode8);
		}
		if (aStringArray1969 != null && !ArrayUtils.isEmpty(aStringArray1969)) { // good
			for (int i = 0; i < aStringArray1969.length; i++) {
				if (aStringArray1969[i] == null) {
					continue;
				}
				dos.write(10 + i);
				dos.write(ArrayUtils.toByteArray(aStringArray1969[i]));
				dos.writeByte(10);
			}
		}
		if (anIntArray1982 != null ) { // good
				for (int i = 0; i < anIntArray1982.length; i++) {
					if (anIntArray1982[i] == -1) {
						continue;
					}
					dos.writeByte(15);
					dos.writeInt(anIntArray1982[i]);
				}
			}
		if (aString1970 != null) {
			dos.writeByte(17);
			dos.write(ArrayUtils.toByteArray(aString1970));
			dos.writeByte(10);
		}
		if (opcode18 != -1) {
			dos.writeByte(18);
			dos.writeInt(opcode18);
		}
		if (category != -1) {
			dos.writeByte(19);
			dos.writeShort(category);
		}
		if (int21 != 1) {
			dos.writeByte(21);
			dos.writeInt(int21);
		}
		if (int22 != 1) {
			dos.writeByte(22);
			dos.writeInt(int22);
		}
		if(int23_1 != -1 || int23_2 != -1 || int23_3 != -1){
			dos.writeByte(23);
			dos.writeByte(int23_1);
			dos.writeByte(int23_2);
			dos.writeByte(int23_3);
		}
		if (int24_1 != -1 || int24_2 != -1) {
			dos.writeByte(24);
			dos.writeShort(int24_1);
			dos.writeShort(int24_2);
		}
		if (int25 != -1) {
			dos.writeByte(25);
			dos.writeInt(int25);
		}
		if(int28 != -1){
			dos.writeByte(28);
			dos.writeByte(int28);
		}
		if(int29 != -1){
			dos.writeByte(29);
			dos.writeByte(int29);
		}
		if(int30 != -1){
			dos.writeByte(30);
			dos.writeByte(int30);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see net.openrs.cache.type.Type#getID()
	 */
	@Override
	public int getID() {
		return id;
	}

	/**
	 * @return the spriteId
	 */
	public int getSpriteId() {
		return spriteId;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

}
