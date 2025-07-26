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
package net.openrs.cache.type.texture;

import net.openrs.cache.Archive;
import net.openrs.cache.Cache;
import net.openrs.cache.Constants;
import net.openrs.cache.ReferenceTable;
import net.openrs.cache.ReferenceTable.ChildEntry;
import net.openrs.cache.ReferenceTable.Entry;
import net.openrs.cache.type.CacheIndex;
import net.openrs.cache.type.ConfigArchive;
import net.openrs.cache.type.TypeList;
import net.openrs.cache.type.TypePrinter;
import net.openrs.cache.type.sequences.SequenceType;
import net.openrs.util.Preconditions;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.Arrays;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Kyle Friz
 * 
 * @since May 26, 2015
 */
public class TextureTypeList implements TypeList<TextureType> {

	private Logger logger = Logger.getLogger(TextureTypeList.class.getName());

	private TextureType[] textures;

	@Override
	public void initialize(Cache cache) {
		int count = 0;
		try {
			ReferenceTable table = cache.getReferenceTable(CacheIndex.TEXTURES);
			Entry entry = table.getEntry(ConfigArchive.TEXTURES);
			Archive archive = Archive.decode(cache.read(CacheIndex.TEXTURES, ConfigArchive.TEXTURES).getData(),
					entry.size());

			textures = new TextureType[entry.capacity()];
			for (int id = 0; id < entry.capacity(); id++) {
				ChildEntry child = entry.getEntry(id);
				if (child == null)
					continue;

				ByteBuffer buffer = archive.getEntry(child.index());
				TextureType type = new TextureType(id);
				type.decode(buffer);
				switch(type.getID()){
					case 0:
						type.setFileIds(new int[]{0});
						type.setField2293(true);
						type.setField2295(new int[]{0});
						type.setAverageRGB(5654);
						break;

					case 1:
						type.setFileIds(new int[]{1});
						type.setField2293(true);
						type.setField2295(new int[]{ 0});
						type.setAverageRGB(39374);
						break;

					case 2:
						type.setFileIds(new int[]{ 2});
						type.setField2293(true);
						type.setField2295(new int[]{ 0});
						type.setAverageRGB(6183);
						break;

					case 3:
						type.setFileIds(new int[]{ 3});
						type.setField2293(true);
						type.setField2295(new int[]{ 0});
						type.setAverageRGB(4636);
						break;

					case 4:
						type.setFileIds(new int[]{ 4});
						type.setField2293(true);
						type.setField2295(new int[]{ 0});
						type.setAverageRGB(6678);
						break;

					case 5:
						type.setFileIds(new int[]{ 5});
						type.setField2293(true);
						type.setField2295(new int[]{ 0});
						type.setAverageRGB(6024);
						break;

					case 6:
						type.setFileIds(new int[]{ 6});
						type.setField2293(true);
						type.setField2295(new int[]{ 0});
						type.setAverageRGB(2473);
						break;

					case 7:
						type.setFileIds(new int[]{ 7});
						type.setField2295(new int[]{ 0});
						type.setAverageRGB(7057);
						break;

					case 8:
						type.setFileIds(new int[]{ 8});
						type.setField2295(new int[]{ 0});
						type.setAverageRGB(12818);
						break;

					case 9:
						type.setFileIds(new int[]{ 9});
						type.setField2295(new int[]{ 0});
						type.setAverageRGB(5927);
						break;

					case 10:
						type.setFileIds(new int[]{ 10});
						type.setField2293(true);
						type.setField2295(new int[]{ 0});
						type.setAverageRGB(8233);
						break;

					case 11:
						type.setFileIds(new int[]{ 11});
						type.setField2293(true);
						type.setField2295(new int[]{ 0});
						type.setAverageRGB(5155);
						break;

					case 12:
						type.setFileIds(new int[]{ 12});
						type.setField2295(new int[]{ 0});
						type.setAverageRGB(18);
						break;

					case 13:
						type.setFileIds(new int[]{ 13});
						type.setField2293(true);
						type.setField2295(new int[]{ 0});
						type.setAverageRGB(32847);
						break;

					case 14:
						type.setFileIds(new int[]{ 14});
						type.setField2293(true);
						type.setField2295(new int[]{ 0});
						type.setAverageRGB(5295);
						break;

					case 15:
						type.setFileIds(new int[]{ 15});
						type.setField2293(true);
						type.setField2295(new int[]{ 0});
						type.setAverageRGB(47161);
						break;

					case 16:
						type.setFileIds(new int[]{ 16});
						type.setField2293(true);
						type.setField2295(new int[]{ 0});
						type.setAverageRGB(6934);
						break;

					case 17:
						type.setFileIds(new int[]{ 17});
						type.setField2295(new int[]{ 0});
						type.setAnimationSpeed(2);
						type.setAnimationDirection(1);
						type.setAverageRGB(39640);
						break;

					case 18:
						type.setFileIds(new int[]{ 18});
						type.setField2293(true);
						type.setField2295(new int[]{ 0});
						type.setAverageRGB(9534);
						break;

					case 19:
						type.setFileIds(new int[]{ 19});
						type.setField2295(new int[]{ 0});
						type.setAverageRGB(5405);
						break;

					case 20:
						type.setFileIds(new int[]{ 20});
						type.setField2293(true);
						type.setField2295(new int[]{ 0});
						type.setAverageRGB(5662);
						break;

					case 21:
						type.setFileIds(new int[]{ 21});
						type.setField2295(new int[]{ 0});
						type.setAverageRGB(6673);
						break;

					case 22:
						type.setFileIds(new int[]{ 22});
						type.setField2293(true);
						type.setField2295(new int[]{ 0});
						type.setAverageRGB(4512);
						break;

					case 23:
						type.setFileIds(new int[]{ 23});
						type.setField2293(true);
						type.setField2295(new int[]{ 0});
						type.setAverageRGB(7446);
						break;

					case 24:
						type.setFileIds(new int[]{ 24});
						type.setField2293(true);
						type.setField2295(new int[]{ 0});
						type.setAnimationSpeed(2);
						type.setAnimationDirection(1);
						type.setAverageRGB(40269);
						break;

					case 25:
						type.setFileIds(new int[]{ 25});
						type.setField2293(true);
						type.setField2295(new int[]{ 0});
						type.setAverageRGB(29101);
						break;

					case 26:
						type.setFileIds(new int[]{ 26});
						type.setField2295(new int[]{ 0});
						type.setAverageRGB(81);
						break;

					case 27:
						type.setFileIds(new int[]{ 27});
						type.setField2293(true);
						type.setField2295(new int[]{ 0});
						type.setAverageRGB(5648);
						break;

					case 28:
						type.setFileIds(new int[]{ 28});
						type.setField2295(new int[]{ 0});
						type.setAverageRGB(21534);
						break;

					case 29:
						type.setFileIds(new int[]{ 29});
						type.setField2295(new int[]{ 0});
						type.setAverageRGB(13862);
						break;

					case 30:
						type.setFileIds(new int[]{ 30});
						type.setField2295(new int[]{ 0});
						type.setAverageRGB(12698);
						break;

					case 31:
						type.setFileIds(new int[]{ 31});
						type.setField2293(true);
						type.setField2295(new int[]{ 0});
						type.setAverageRGB(6089);
						break;

					case 32:
						type.setFileIds(new int[]{ 32});
						type.setField2293(true);
						type.setField2295(new int[]{ 0});
						type.setAverageRGB(5541);
						break;

					case 33:
						type.setFileIds(new int[]{ 33});
						type.setField2295(new int[]{ 0});
						type.setAverageRGB(5009);
						break;

					case 34:
						type.setFileIds(new int[]{ 34});
						type.setField2295(new int[]{ 0});
						type.setAnimationSpeed(2);
						type.setAnimationDirection(1);
						type.setAverageRGB(9443);
						break;

					case 35:
						type.setFileIds(new int[]{ 35});
						type.setField2293(true);
						type.setField2295(new int[]{ 0});
						type.setAverageRGB(7484);
						break;

					case 36:
						type.setFileIds(new int[]{ 36});
						type.setField2293(true);
						type.setField2295(new int[]{ 0});
						type.setAverageRGB(3733);
						break;

					case 37:
						type.setFileIds(new int[]{ 37});
						type.setField2293(true);
						type.setField2295(new int[]{ 0});
						type.setAverageRGB(32);
						break;

					case 38:
						type.setFileIds(new int[]{ 38});
						type.setField2293(true);
						type.setField2295(new int[]{ 0});
						type.setAverageRGB(6740);
						break;

					case 39:
						type.setFileIds(new int[]{ 38});
						type.setField2293(true);
						type.setField2295(new int[]{ 0});
						type.setAverageRGB(13482);
						break;

					case 40:
						type.setFileIds(new int[]{ 40});
						type.setField2293(true);
						type.setField2295(new int[]{ 0});
						type.setAnimationSpeed(2);
						type.setAnimationDirection(1);
						type.setAverageRGB(6089);
						break;

					case 41:
						type.setFileIds(new int[]{ 41});
						type.setField2295(new int[]{ 0});
						type.setAverageRGB(10128);
						break;

					case 42:
						type.setFileIds(new int[]{ 42});
						type.setField2293(true);
						type.setField2295(new int[]{ 0});
						type.setAverageRGB(8295);
						break;

					case 43:
						type.setFileIds(new int[]{ 43});
						type.setField2293(true);
						type.setField2295(new int[]{ 0});
						type.setAverageRGB(10290);
						break;

					case 44:
						type.setFileIds(new int[]{ 44});
						type.setField2293(true);
						type.setField2295(new int[]{ 0});
						type.setAverageRGB(41);
						break;

					case 45:
						type.setFileIds(new int[]{ 45});
						type.setField2293(true);
						type.setField2295(new int[]{ 0});
						type.setAverageRGB(9256);
						break;

					case 46:
						type.setFileIds(new int[]{ 46});
						type.setField2293(true);
						type.setField2295(new int[]{ 0});
						type.setAverageRGB(3119);
						break;

					case 47:
						type.setFileIds(new int[]{ 47});
						type.setField2293(true);
						type.setField2295(new int[]{ 0});
						type.setAverageRGB(8403);
						break;

					case 48:
						type.setFileIds(new int[]{ 48});
						type.setField2293(true);
						type.setField2295(new int[]{ 0});
						type.setAverageRGB(6434);
						break;

					case 49:
						type.setFileIds(new int[]{ 49});
						type.setField2293(true);
						type.setField2295(new int[]{ 0});
						type.setAverageRGB(5592);
						break;

					case 50:
						type.setFileIds(new int[]{ 50});
						type.setField2293(true);
						type.setField2295(new int[]{ 0});
						type.setAverageRGB(9380);
						break;

					case 51:
						type.setFileIds(new int[]{ 51});
						type.setField2293(true);
						type.setField2295(new int[]{ 0});
						type.setAverageRGB(5679);
						break;

					case 52:
						type.setFileIds(new int[]{ 52});
						type.setField2295(new int[]{ 0});
						type.setAverageRGB(41072);
						break;

					case 53:
						type.setFileIds(new int[]{ 53});
						type.setField2293(true);
						type.setField2295(new int[]{ 0});
						type.setAverageRGB(35049);
						break;


					case 56:
						type.setFileIds(new int[]{ 55});
						type.setField2293(true);
						type.setField2295(new int[]{ 0});
						type.setAnimationSpeed(2);
						type.setAnimationDirection(1);
						type.setAverageRGB(709);
						break;

					case 57:
						type.setFileIds(new int[]{ 56});
						type.setField2293(true);
						type.setField2295(new int[]{ 0});
						type.setAnimationSpeed(2);
						type.setAnimationDirection(1);
						type.setAverageRGB(108);
						break;

					case 58:
						type.setFileIds(new int[]{ 57});
						type.setField2295(new int[]{ 0});
						type.setAverageRGB(3747);
						break;

					case 59:
						type.setFileIds(new int[]{ 58});
						type.setField2293(true);
						type.setField2295(new int[]{ 0});
						type.setAnimationSpeed(1);
						type.setAnimationDirection(1);
						type.setAverageRGB(3875);
						break;

					case 60:
						type.setFileIds(new int[]{ 59});
						type.setField2295(new int[]{ 0});
						type.setAverageRGB(12818);
						break;

					case 61:
						type.setFileIds(new int[]{ 60});
						type.setField2295(new int[]{ 0});
						type.setAverageRGB(43086);
						break;

					case 62:
						type.setFileIds(new int[]{ 61});
						type.setField2295(new int[]{ 0});
						type.setAverageRGB(73);
						break;

					case 63:
						type.setFileIds(new int[]{ 62});
						type.setField2295(new int[]{ 0});
						type.setAverageRGB(58);
						break;

					case 64:
						type.setFileIds(new int[]{ 63});
						type.setField2295(new int[]{ 0});
						type.setAverageRGB(61);
						break;

					case 65:
						type.setFileIds(new int[]{ 64});
						type.setField2295(new int[]{ 0});
						type.setAverageRGB(55339);
						break;

					case 66:
						type.setFileIds(new int[]{ 65});
						type.setField2295(new int[]{ 0});
						type.setAverageRGB(55350);
						break;

					case 67:
						type.setFileIds(new int[]{ 66});
						type.setField2295(new int[]{ 0});
						type.setAverageRGB(61);
						break;

					case 68:
						type.setFileIds(new int[]{ 67});
						type.setField2295(new int[]{ 0});
						type.setAverageRGB(45);
						break;

					case 69:
						type.setFileIds(new int[]{ 68});
						type.setField2295(new int[]{ 0});
						type.setAverageRGB(43048);
						break;

					case 70:
						type.setFileIds(new int[]{ 69});
						type.setField2295(new int[]{ 0});
						type.setAverageRGB(43038);
						break;

					case 71:
						type.setFileIds(new int[]{ 70});
						type.setField2295(new int[]{ 0});
						type.setAverageRGB(66);
						break;

					case 72:
						type.setFileIds(new int[]{ 71});
						type.setField2295(new int[]{ 0});
						type.setAverageRGB(43017);
						break;

					case 73:
						type.setFileIds(new int[]{ 72});
						type.setField2295(new int[]{ 0});
						type.setAverageRGB(63);
						break;

					case 74:
						type.setFileIds(new int[]{ 73});
						type.setField2295(new int[]{ 0});
						type.setAverageRGB(36);
						break;

					case 75:
						type.setFileIds(new int[]{ 74});
						type.setField2295(new int[]{ 0});
						type.setAverageRGB(41);
						break;

					case 76:
						type.setFileIds(new int[]{ 75});
						type.setField2295(new int[]{ 0});
						type.setAverageRGB(55337);
						break;

					case 77:
						type.setFileIds(new int[]{ 76});
						type.setField2295(new int[]{ 0});
						type.setAverageRGB(54);
						break;

					case 78:
						type.setFileIds(new int[]{ 77});
						type.setField2295(new int[]{ 0});
						type.setAverageRGB(61);
						break;

					case 79:
						type.setFileIds(new int[]{ 78});
						type.setField2295(new int[]{ 0});
						type.setAverageRGB(55355);
						break;

					case 80:
						type.setFileIds(new int[]{ 79});
						type.setField2295(new int[]{ 0});
						type.setAverageRGB(32781);
						break;

					case 81:
						type.setFileIds(new int[]{ 80});
						type.setField2295(new int[]{ 0});
						type.setAverageRGB(50);
						break;

					case 82:
						type.setFileIds(new int[]{ 81});
						type.setField2295(new int[]{ 0});
						type.setAverageRGB(57);
						break;

					case 83:
						type.setFileIds(new int[]{ 82});
						type.setField2295(new int[]{ 0});
						type.setAverageRGB(70);
						break;

					case 84:
						type.setFileIds(new int[]{ 83});
						type.setField2295(new int[]{ 0});
						type.setAverageRGB(43066);
						break;

					case 85:
						type.setFileIds(new int[]{ 84});
						type.setField2295(new int[]{ 0});
						type.setAverageRGB(55346);
						break;

					case 86:
						type.setFileIds(new int[]{ 85});
						type.setField2295(new int[]{ 0});
						type.setAverageRGB(55342);
						break;

					case 87:
						type.setFileIds(new int[]{ 86});
						type.setField2295(new int[]{ 0});
						type.setAverageRGB(51);
						break;

					case 88:
						type.setFileIds(new int[]{ 87});
						type.setField2295(new int[]{ 0});
						type.setAverageRGB(42);
						break;

					case 89:
						type.setFileIds(new int[]{ 88});
						type.setField2295(new int[]{ 0});
						type.setAverageRGB(14482);
						break;

					case 90:
						type.setFileIds(new int[]{ 89});
						type.setField2295(new int[]{ 0});
						type.setAverageRGB(14482);
						break;

					case 91:
						type.setFileIds(new int[]{ 90});
						type.setField2293(true);
						type.setField2295(new int[]{ 0});
						type.setAverageRGB(38002);
						break;

					case 92:
						type.setFileIds(new int[]{ 91});
						type.setField2293(true);
						type.setField2295(new int[]{ 0});
						type.setAverageRGB(49262);
						break;

					case 93:
						type.setFileIds(new int[]{ 92});
						type.setField2293(true);
						type.setField2295(new int[]{ 0});
						type.setAverageRGB(120);
						break;

					case 94:
						type.setFileIds(new int[]{ 93});
						type.setField2293(true);
						type.setField2295(new int[]{ 0});
						type.setAverageRGB(4133);
						break;

					case 95:
						type.setFileIds(new int[]{ 94});
						type.setField2293(true);
						type.setField2295(new int[]{ 0});
						type.setAverageRGB(32);
						break;

					case 96:
						type.setFileIds(new int[]{ 95});
						type.setField2295(new int[]{ 0});
						type.setAverageRGB(8384);
						break;

					case 97:
						type.setFileIds(new int[]{ 96});
						type.setField2295(new int[]{ 0});
						type.setAverageRGB(8384);
						break;

					case 98:
						type.setFileIds(new int[]{ 97});
						type.setField2295(new int[]{ 0});
						type.setAverageRGB(55738);
						break;

					case 99:
						type.setFileIds(new int[]{ 97});
						type.setField2295(new int[]{ 0});
						type.setAnimationSpeed(1);
						type.setAnimationDirection(4);
						type.setAverageRGB(55738);
						break;

					case 100:
						type.setFileIds(new int[]{ 97});
						type.setField2295(new int[]{ 0});
						type.setAnimationSpeed(2);
						type.setAnimationDirection(4);
						type.setAverageRGB(55738);
						break;

					case 101:
						type.setFileIds(new int[]{ 97});
						type.setField2295(new int[]{ 0});
						type.setAnimationSpeed(3);
						type.setAnimationDirection(4);
						type.setAverageRGB(55738);
						break;

					case 102:
						type.setFileIds(new int[]{ 97});
						type.setField2295(new int[]{ 0});
						type.setAnimationSpeed(4);
						type.setAnimationDirection(4);
						type.setAverageRGB(55738);
						break;

					case 103:
						type.setFileIds(new int[]{ 97});
						type.setField2295(new int[]{ 0});
						type.setAnimationSpeed(5);
						type.setAnimationDirection(4);
						type.setAverageRGB(55738);
						break;

					case 104:
						type.setFileIds(new int[]{ 98});
						type.setField2295(new int[]{ 0});
						type.setAverageRGB(55606);
						break;

					case 105:
						type.setFileIds(new int[]{ 98});
						type.setField2295(new int[]{ 0});
						type.setAnimationSpeed(1);
						type.setAnimationDirection(4);
						type.setAverageRGB(55606);
						break;

					case 106:
						type.setFileIds(new int[]{ 98});
						type.setField2295(new int[]{ 0});
						type.setAnimationSpeed(2);
						type.setAnimationDirection(4);
						type.setAverageRGB(55606);
						break;

					case 107:
						type.setFileIds(new int[]{ 98});
						type.setField2295(new int[]{ 0});
						type.setAnimationSpeed(3);
						type.setAnimationDirection(4);
						type.setAverageRGB(55606);
						break;

					case 108:
						type.setFileIds(new int[]{ 98});
						type.setField2295(new int[]{ 0});
						type.setAnimationSpeed(4);
						type.setAnimationDirection(4);
						type.setAverageRGB(55606);
						break;

					case 109:
						type.setFileIds(new int[]{ 98});
						type.setField2295(new int[]{ 0});
						type.setAnimationSpeed(5);
						type.setAnimationDirection(4);
						type.setAverageRGB(55606);
						break;

					case 110:
						type.setFileIds(new int[]{ 99});
						type.setField2295(new int[]{ 0});
						type.setAverageRGB(55606);
						break;

					case 111:
						type.setFileIds(new int[]{ 99});
						type.setField2295(new int[]{ 0});
						type.setAnimationSpeed(1);
						type.setAnimationDirection(4);
						type.setAverageRGB(55606);
						break;

					case 112:
						type.setFileIds(new int[]{ 99});
						type.setField2295(new int[]{ 0});
						type.setAnimationSpeed(2);
						type.setAnimationDirection(4);
						type.setAverageRGB(55606);
						break;

					case 113:
						type.setFileIds(new int[]{ 99});
						type.setField2295(new int[]{ 0});
						type.setAnimationSpeed(3);
						type.setAnimationDirection(4);
						type.setAverageRGB(55606);
						break;

					case 114:
						type.setFileIds(new int[]{ 99});
						type.setField2295(new int[]{ 0});
						type.setAnimationSpeed(4);
						type.setAnimationDirection(4);
						type.setAverageRGB(55606);
						break;

					case 115:
						type.setFileIds(new int[]{ 99});
						type.setField2295(new int[]{ 0});
						type.setAnimationSpeed(5);
						type.setAnimationDirection(4);
						type.setAverageRGB(55606);
						break;

					case 116:
						type.setFileIds(new int[]{ 100 });
						type.setField2293(true);
						type.setField2295(new int[]{ 0 });
						type.setAverageRGB(5405);
						break;

					case 117:
						type.setFileIds(new int[]{ 101 });
						type.setField2293(true);
						type.setField2295(new int[]{ 0});
						type.setAverageRGB(6307);
						break;

					case 118:
						type.setFileIds(new int[]{ 102 });
						type.setField2293(true);
						type.setField2295(new int[]{ 0});
						type.setAverageRGB(6322);
						break;

					case 119:
						type.setFileIds(new int[]{ 103 });
						type.setField2293(true);
						type.setField2295(new int[]{ 0});
						type.setAverageRGB(5147);
						break;

					case 120:
						type.setFileIds(new int[]{ 109 });
						type.setField2293(true);
						type.setField2295(new int[]{ 0});
						type.setAverageRGB(7446);
						break;

					case 121:
						type.setFileIds(new int[]{ 104 });
						type.setField2293(true);
						type.setField2295(new int[]{ 0 });
						type.setAverageRGB(3272);
						break;

					case 122:
						type.setFileIds(new int[]{ 105});
						type.setField2293(true);
						type.setField2295(new int[]{ 0});
						type.setAverageRGB(3272);
						break;

					case 123:
						type.setFileIds(new int[]{ 106});
						type.setField2295(new int[]{ 0});
						type.setAverageRGB(11666);
						break;

					case 124:
						type.setFileIds(new int[]{ 107});
						type.setField2295(new int[]{ 0});
						type.setAverageRGB(16914);
						break;

					case 125:
						type.setFileIds(new int[]{ 110});
						type.setField2293(true);
						type.setField2295(new int[]{ 0});
						type.setAverageRGB(6303);
						break;

					case 126:
						type.setFileIds(new int[]{ 111});
						type.setField2293(true);
						type.setField2295(new int[]{ 0});
						type.setAverageRGB(6303);
						break;

					case 127:
						type.setFileIds(new int[]{ 112 });
						type.setField2295(new int[]{ 0});
						type.setAverageRGB(26921);
						break;
					case 128:
						type.setFileIds(new int[]{ 113 });
						type.setField2295(new int[]{ 0 });
						type.setAverageRGB(26921);
						break;

				}
				textures[id] = type;
				count++;
			}
		} catch (IOException e) {
			logger.log(Level.SEVERE, "Error Loading TextureType(s)!", e);
		}
		logger.info("Loaded " + count + " TextureType(s)!");
	}

	@Override
	public TextureType list(int id) {
		Preconditions.checkArgument(id >= 0, "ID can't be negative!");
		Preconditions.checkArgument(id < textures.length, "ID can't be greater than the max texture id!");
		return textures[id];
	}

	@Override
	public void print() {
	      File dir = new File(Constants.TYPE_PATH);

	      if (!dir.exists()) {
	            dir.mkdir();
	      }
	      
		File file = new File(Constants.TYPE_PATH, "textures.txt");
		try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
			Arrays.stream(textures).filter(Objects::nonNull).forEach((TextureType t) -> {
				TypePrinter.print(t, writer);
			});
			writer.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public int size() {
		return textures.length;
	}

}
