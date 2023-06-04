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
package net.openrs.cache.type.varbits;

import java.io.DataOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;

import net.openrs.cache.type.Type;
import net.openrs.util.BitUtils;

/**
 * @author Kyle Friz
 * 
 * @since May 26, 2015
 */
public class VarBitType implements Type {

	private final int id;
	private int configId = -1;
	private int lsb = -1;
	private int msb = -1;

	public VarBitType(int id) {
		this.id = id;
	}

	@Override
	public void decode(ByteBuffer buffer) {
		while (true) {
			int opcode = buffer.get() & 0xFF;

			if (opcode == 0) {
				break;
			} else if (opcode == 1) {
				configId = buffer.getShort() & 0xFFFF;
				lsb = buffer.get() & 0xFF;
				msb = buffer.get() & 0xFF;
			}
		}
	}

	@Override
	public void encode(DataOutputStream dos) throws IOException {
		if (configId != -1 || lsb != -1 || msb != -1) {
			dos.writeByte(1);
			dos.writeShort(configId);
			dos.writeByte(lsb);
			dos.writeByte(msb);
		}
	}

	public int getID() {
		return id;
	}

	public int getConfigId() {
		return configId;
	}

	public int getLsb() {
		return lsb;
	}

	public int getMsb() {
		return msb;
	}

	public int getBitCount() {
		return (getMsb() - getLsb()) + 1;
	}

	public boolean isBooleanType() {
		return getBitCount() == 1;
	}

	public int setBoolean(int oVal, boolean nVal) {
		if (!isBooleanType())
			throw new Error();
		return set(oVal, nVal ? 1 : 0);
	}

	public int set(int oVal, int nVal) {
		int least = getLsb();
		int most = getMsb();
		int mask = BitUtils.getMask(most - least);
		if (nVal < 0 || nVal > mask) {
			throw new Error("Value out of bit range:" + nVal + ", MAX:" + mask);
		}
		mask <<= least;
		return oVal & ~mask | nVal << least & mask;
	}

	public int get(int val) {
		int least = getLsb();
		int most = getMsb();
		int mask = BitUtils.getMask(most - least);
		return val >> least & mask;
	}

	public boolean getBoolean(int val) {
		if (!isBooleanType())
			throw new Error();
		return get(val) == 1;
	}

}
