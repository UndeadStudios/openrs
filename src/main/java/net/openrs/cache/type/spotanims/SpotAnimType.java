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
package net.openrs.cache.type.spotanims;

import java.io.DataOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;

import net.openrs.cache.type.Type;

/**
 * @author Kyle Friz
 * @since Oct 18, 2015
 */
public class SpotAnimType implements Type {

	private final int id;
	private int resizeX = 128;
	private short[] retextureToFind;
	private short[] recolorToReplace;
	private int animationID = -1;
	private short[] recolorToFind;
	private int ambient = 0;
	private short[] retextureToReplace;
	private int gfxID;
	private int resizeY = 128;
	private int rotation = 0;
	private int modelID;
	private int contrast = 0;

	public SpotAnimType(int id) {
		this.id = id;
	}

	@Override
	public void decode(ByteBuffer buffer) {
		while (true) {
			int opcode = buffer.get() & 0xFF;

			if (opcode == 0) {
				break;
			}

			if (opcode == 1) {
				this.modelID = buffer.getShort() & 0xFFFF;
			} else if (opcode == 2) {
				this.animationID = buffer.getShort() & 0xFFFF;
			} else if (opcode == 4) {
				this.resizeX = buffer.getShort() & 0xFFFF;
			} else if (opcode == 5) {
				this.resizeY = buffer.getShort() & 0xFFFF;
			} else if (opcode == 6) {
				this.rotation = buffer.getShort() & 0xFFFF;
			} else if (opcode == 7) {
				this.ambient = buffer.get() & 0xFF;
			} else if (opcode == 8) {
				this.contrast = buffer.get() & 0xFF;
			} else if (opcode == 40) {
				int length = buffer.get() & 0xFF;
				this.recolorToFind = new short[length];
				this.recolorToReplace = new short[length];

				for (int index = 0; index < length; ++index) {
					this.recolorToFind[index] = (short) (buffer.getShort() & 0xFFFF);
					this.recolorToReplace[index] = (short) (buffer.getShort() & 0xFFFF);
				}

			} else if (opcode == 41) {
				int length = buffer.get() & 0xFF;
				this.retextureToFind = new short[length];
				this.retextureToReplace = new short[length];

				for (int index = 0; index < length; ++index) {
					this.retextureToFind[index] = (short) (buffer.getShort() & 0xFFFF);
					this.retextureToReplace[index] = (short) (buffer.getShort() & 0xFFFF);
				}

			}
		}
	}

	@Override
	public void encode(DataOutputStream dos) throws IOException {

		if (getModelID() != 0) {
			dos.writeByte(1);
			dos.writeShort(getModelID());
		}

		if (getAnimationID() != -1) {
			dos.writeByte(2);
			dos.writeShort(getAnimationID());
		}

		if (getResizeX() != 128) {
			dos.writeByte(4);
			dos.writeShort(getResizeX());
		}

		if (getResizeY() != 128) {
			dos.writeByte(5);
			dos.writeShort(getResizeY());
		}

		if (getRotation() != 0) {
			dos.writeByte(6);
			dos.writeShort(getRotation());
		}

		if (getAmbient() != 0) {
			dos.writeByte(7);
			dos.writeByte(getAmbient());
		}

		if (getContrast() != 0) {
			dos.writeByte(8);
			dos.writeByte(getContrast());
		}

		if (getRecolorToFind() != null && getRecolorToReplace() != null) {
			dos.writeByte(40);

			final int len = getRecolorToFind().length;
			dos.writeByte(len);

			for (int i = 0; i < len; i++) {
				dos.writeShort(getRecolorToFind()[i]);
				dos.writeShort(getRecolorToReplace()[i]);
			}

		}

		if (getRetextureToFind() != null && getRetextureToReplace() != null) {
			dos.writeByte(41);

			final int len = getRetextureToFind().length;
			dos.writeByte(len);

			for (int i = 0; i < len; i++) {
				dos.writeShort(getRetextureToFind()[i]);
				dos.writeShort(getRetextureToReplace()[i]);
			}
		}

		dos.writeByte(0);
	}

	@Override
	public int getID() {
		return id;
	}

	public int getId() {
		return id;
	}

	public int getResizeX() {
		return resizeX;
	}

	public void setResizeX(int resizeX) {
		this.resizeX = resizeX;
	}

	public short[] getRetextureToFind() {
		return retextureToFind;
	}

	public void setRetextureToFind(short[] retextureToFind) {
		this.retextureToFind = retextureToFind;
	}

	public short[] getRecolorToReplace() {
		return recolorToReplace;
	}

	public void setRecolorToReplace(short[] recolorToReplace) {
		this.recolorToReplace = recolorToReplace;
	}

	public int getAnimationID() {
		return animationID;
	}

	public void setAnimationID(int animationID) {
		this.animationID = animationID;
	}

	public short[] getRecolorToFind() {
		return recolorToFind;
	}

	public void setRecolorToFind(short[] recolorToFind) {
		this.recolorToFind = recolorToFind;
	}

	public int getAmbient() {
		return ambient;
	}

	public void setAmbient(int ambient) {
		this.ambient = ambient;
	}

	public short[] getRetextureToReplace() {
		return retextureToReplace;
	}

	public void setRetextureToReplace(short[] retextureToReplace) {
		this.retextureToReplace = retextureToReplace;
	}

	public int getGfxID() {
		return gfxID;
	}

	public void setGfxID(int gfxID) {
		this.gfxID = gfxID;
	}

	public int getResizeY() {
		return resizeY;
	}

	public void setResizeY(int resizeY) {
		this.resizeY = resizeY;
	}

	public int getRotation() {
		return rotation;
	}

	public void setRotation(int rotation) {
		this.rotation = rotation;
	}

	public int getModelID() {
		return modelID;
	}

	public void setModelID(int modelID) {
		this.modelID = modelID;
	}

	public int getContrast() {
		return contrast;
	}

	public void setContrast(int contrast) {
		this.contrast = contrast;
	}
}
