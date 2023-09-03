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

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.Arrays;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;

import net.openrs.cache.Archive;
import net.openrs.cache.Cache;
import net.openrs.cache.Constants;
import net.openrs.cache.Container;
import net.openrs.cache.ReferenceTable;
import net.openrs.cache.ReferenceTable.ChildEntry;
import net.openrs.cache.ReferenceTable.Entry;
import net.openrs.cache.type.CacheIndex;
import net.openrs.cache.type.ConfigArchive;
import net.openrs.cache.type.TypeList;
import net.openrs.cache.type.TypePrinter;
import net.openrs.util.Preconditions;

/**
 * @author Kyle Friz
 *
 * @since May 26, 2015
 */
public class AreaTypeList implements TypeList<AreaType> {

	private Logger logger = Logger.getLogger(AreaTypeList.class.getName());

	private AreaType[] areas;
	
	public boolean data317 = false;

	@Override
	public void initialize(Cache cache) {
		int count = 0;
		try {
			ReferenceTable table = cache.getReferenceTable(CacheIndex.CONFIGS);
			Entry entry = table.getEntry(ConfigArchive.AREA);
			Archive archive = Archive.decode(cache.read(CacheIndex.CONFIGS, ConfigArchive.AREA).getData(),
					entry.size());

			areas = new AreaType[entry.capacity()];
			for (int id = 0; id < entry.capacity(); id++) {
				ChildEntry child = entry.getEntry(id);
				if (child == null)
					continue;

				ByteBuffer buffer = archive.getEntry(child.index());
				AreaType type = new AreaType(id);
				type.decode(buffer);
				areas[id] = type;
				if(data317) {
					switch (id) {
						case 0:
							type.spriteId = 0;
							type.anInt1980 = -1;// -1;// -1;
							break;

						case 1:
							type.spriteId = 1;
							type.anInt1980 = -1;// -1;// -1;
							break;

						case 2:
							type.spriteId = 2;
							type.anInt1980 = -1;// -1;// -1;
							break;

						case 3:
							type.spriteId = 3;
							type.anInt1980 = -1;// -1;// 1053;
							break;

						case 4:
							type.spriteId = 4;
							type.anInt1980 = -1;// -1;// 1054;
							break;

						case 5:
							type.spriteId = 5;
							type.anInt1980 = -1;// -1;// 1055;
							break;

						case 6:
							type.spriteId = 6;
							type.anInt1980 = -1;// -1;// 1056;
							break;

						case 7:
							type.spriteId = 7;
							type.anInt1980 = -1;// -1;// 1057;
							break;

						case 8:
							type.spriteId = 8;
							type.anInt1980 = -1;// -1;// 1058;
							break;

						case 9:
							type.spriteId = 9;
							type.anInt1980 = -1;// -1;// 1059;
							break;

						case 10:
							type.spriteId = 10;
							type.anInt1980 = -1;// -1;// 1060;
							break;

						case 11:
							type.spriteId = 11;
							type.anInt1980 = -1;// -1;// 1061;
							break;

						case 12:
							type.spriteId = 12;
							type.anInt1980 = -1;// -1;// 1062;
							break;

						case 13:
							type.spriteId = 13;
							type.aString1970 = "Map";
							type.anInt1980 = -1;// -1;// 1062;
							type.aStringArray1969 = new String[]{"Open", null, null, null, null};
							break;

						case 14:
							type.spriteId = 14;
							type.aString1970 = "Map";
							type.anInt1980 = -1;// -1;// 1063;
							type.aStringArray1969 = new String[]{"Open", null, null, null, null};
							break;

						case 15:
							type.spriteId = 15;
							type.anInt1980 = -1;// -1;// 1064;
							break;

						case 16:
							type.spriteId = 16;
							type.anInt1980 = -1;// -1;// 1065;
							break;

						case 17:
							type.spriteId = 17;
							type.anInt1980 = -1;// -1;// 1066;
							break;

						case 18:
							type.spriteId = 18;
							type.anInt1980 = -1;// -1;// 1067;
							break;

						case 19:
							type.spriteId = 19;
							type.anInt1980 = -1;// -1;// 1068;
							break;

						case 20:
							type.spriteId = 20;
							type.anInt1980 = -1;// -1;// 1069;
							break;

						case 21:
							type.spriteId = 21;
							type.anInt1980 = -1;// -1;// 1070;
							break;

						case 22:
							type.spriteId = 22;
							type.anInt1980 = -1;// -1;// 1071;
							break;

						case 23:
							type.spriteId = 23;
							type.anInt1980 = -1;// -1;// 1072;
							break;

						case 24:
							type.spriteId = 24;
							type.anInt1980 = -1;// -1;// 1073;
							break;

						case 25:
							type.spriteId = 25;
							type.anInt1980 = -1;// -1;// 1074;
							break;

						case 26:
							type.spriteId = 26;
							type.anInt1980 = -1;// -1;// 1075;
							break;

						case 27:
							type.spriteId = 27;
							type.anInt1980 = -1;// -1;// 1076;
							break;

						case 28:
							type.spriteId = 28;
							type.anInt1980 = -1;// -1;// 1077;
							break;

						case 29:
							type.spriteId = 29;
							type.anInt1980 = -1;// -1;// 1078;
							break;

						case 30:
							type.spriteId = 30;
							type.anInt1980 = -1;// -1;// 1079;
							break;

						case 31:
							type.spriteId = 31;
							type.anInt1980 = -1;// -1;// 1080;
							break;

						case 32:
							type.spriteId = 32;
							type.anInt1980 = -1;// -1;// 1081;
							break;

						case 33:
							type.spriteId = 33;
							type.anInt1980 = -1;// -1;// 1082;
							break;

						case 34:
							type.spriteId = 34;
							type.anInt1980 = -1;// -1;// 1083;
							break;

						case 35:
							type.spriteId = 35;
							type.anInt1980 = -1;// -1;// 1084;
							break;

						case 36:
							type.spriteId = 36;
							type.anInt1980 = -1;// -1;// 1085;
							break;

						case 37:
							type.spriteId = 37;
							type.anInt1980 = -1;// -1;// 1086;
							break;

						case 38:
							type.spriteId = 38;
							type.anInt1980 = -1;// -1;// 1081;
							break;

						case 39:
							type.spriteId = 39;
							type.anInt1980 = -1;// -1;// 1081;
							break;

						case 40:
							type.spriteId = 40;
							type.anInt1980 = -1;// -1;// 1087;
							break;

						case 41:
							type.spriteId = 41;
							type.anInt1980 = -1;// -1;// 1088;
							break;

						case 42:
							type.spriteId = 42;
							type.anInt1980 = -1;// -1;// 1089;
							break;

						case 43:
							type.spriteId = 43;
							type.anInt1980 = -1;// -1;// 1090;
							break;

						case 44:
							type.spriteId = 44;
							type.anInt1980 = -1;// -1;// 1091;
							break;

						case 45:
							type.spriteId = 45;
							type.anInt1980 = -1;// -1;// 1092;
							break;

						case 46:
							type.spriteId = 46;
							type.anInt1980 = -1;// -1;// 1093;
							break;

						case 47:
							type.spriteId = 47;
							type.anInt1980 = -1;// -1;// 1094;
							break;

						case 48:
							type.spriteId = 48;
							type.anInt1980 = -1;// -1;// 1095;
							break;

						case 49:
							type.spriteId = 49;
							type.anInt1980 = -1;// -1;// 1096;
							break;

						case 50:
							type.spriteId = 50;
							type.anInt1980 = -1;// -1;// 1097;
							break;

						case 51:
							type.spriteId = 51;
							type.anInt1980 = -1;// -1;// 1098;
							break;

						case 52:
							type.spriteId = 52;
							type.anInt1980 = -1;// -1;// 1081;
							break;

						case 53:
							type.spriteId = 53;
							type.anInt1980 = -1;// -1;// 1099;
							break;

						case 54:
							type.spriteId = 54;
							type.anInt1980 = -1;// -1;// 1100;
							break;

						case 55:
							type.spriteId = 55;
							type.anInt1980 = -1;// -1;// 1101;
							break;

						case 56:
							type.spriteId = 56;
							type.anInt1980 = -1;// -1;// 1102;
							break;

						case 57:
							type.name = "Weiss";
							type.anInt1959 = 16777215;
							type.anInt1980 = -1;// -1;// 1129;
							break;

						case 58:
							type.spriteId = 57;
							type.anInt1980 = -1;// -1;// 1104;
							break;

						case 59:
							type.spriteId = 58;
							type.anInt1980 = -1;// -1;// 1105;
							break;

						case 60:
							type.spriteId = 59;
							type.anInt1980 = -1;// -1;// 1106;
							break;

						case 61:
							type.spriteId = 60;
							type.anInt1980 = -1;// -1;// 1107;
							break;

						case 62:
							type.spriteId = 67;
							type.anInt1980 = -1;// -1;// 1108;
							break;

						case 63:
							type.spriteId = 62;
							type.anInt1980 = -1;// -1;// 1109;
							break;

						case 64:
							type.spriteId = 63;
							type.anInt1980 = -1;// -1;// 1110;
							break;

						case 65:
							type.spriteId = 64;
							type.anInt1980 = -1;// -1;// 1111;
							break;

						case 66:
							type.spriteId = 65;
							type.anInt1980 = -1;// -1;// 1112;
							break;

						case 67:
							type.spriteId = 66;
							type.anInt1980 = -1;// -1;// 1113;
							break;

						case 68:
							type.spriteId = 67;
							type.anInt1980 = -1;// -1;// 1114;
							break;

						case 69:
							type.spriteId = 68;
							type.anInt1980 = -1;// -1;// 1115;
							break;

						case 70:
							type.spriteId = 69;
							type.anInt1980 = -1;// -1;// 1116;
							break;

						case 71:
							type.spriteId = 70;
							type.anInt1980 = -1;// -1;// 1117;
							break;

						case 72:
							type.spriteId = 71;
							type.anInt1980 = -1;// -1;// 1118;
							break;

						case 73:
							type.spriteId = 72;
							type.anInt1980 = -1;// -1;// 1119;
							break;

						case 74:
							type.spriteId = 73;
							type.anInt1980 = -1;// -1;// 1120;
							break;

						case 75:
							type.spriteId = 74;
							type.anInt1980 = -1;// -1;// 1121;
							break;

						case 76:
							type.spriteId = 75;
							type.anInt1980 = -1;// -1;// 1122;
							break;

						case 77:
							type.spriteId = 76;
							type.anInt1980 = -1;// -1;// 1123;
							break;

						case 78:
							type.name = "Salt Mine";
							type.anInt1959 = 16777215;
							type.anInt1980 = -1;// -1;// 1129;
							break;

						case 79:
							type.spriteId = 77;
							type.anInt1980 = -1;// -1;// 1125;
							break;

						case 80:
							type.spriteId = 78;
							type.anInt1980 = -1;// -1;// 1125;
							break;

						case 81:
							type.spriteId = 79;
							type.anInt1980 = -1;// -1;// 1125;
							break;

						case 82:
							type.spriteId = 80;
							type.anInt1980 = -1;// -1;// 1125;
							break;

						case 83:
							type.spriteId = 81;
							type.anInt1980 = -1;// -1;// 1125;
							break;

						case 84:
							type.spriteId = 82;
							type.anInt1980 = -1;// -1;// 1126;
							break;

						case 85:
							type.spriteId = 83;
							type.anInt1980 = -1;// -1;// 1127;
							break;

						case 86:
							type.spriteId = 84;
							type.anInt1980 = -1;// -1;// 1128;
							break;

						case 87:
							type.name = "Lumbridge";
							type.anInt1959 = 16777215;
							type.anInt1968 = 1;
							type.anInt1980 = -1;// -1;// 1129;
							break;

						case 88:
							type.name = "Varrock";
							type.anInt1959 = 16777215;
							type.anInt1968 = 1;
							type.anInt1980 = -1;// -1;// 1129;
							break;

						case 89:
							type.name = "Kingdom of<br>Misthalin";
							type.anInt1959 = 16750623;
							type.anInt1968 = 2;
							type.anInt1980 = -1;// -1;// 1129;
							break;

						case 90:
							type.name = "Al Kharid";
							type.anInt1959 = 16777215;
							type.anInt1968 = 1;
							type.anInt1980 = -1;// -1;// 1129;
							break;

						case 91:
							type.spriteId = 84;
							type.anInt1980 = -1;
							break;

						case 92:
							type.name = "Shantay<br>Pass";
							type.anInt1959 = 16777215;
							type.anInt1980 = -1;// -1;// 1129;
							break;

						case 93:
							type.name = "Toll<br>Gate";
							type.anInt1959 = 16777215;
							type.anInt1980 = -1;// -1;// 1129;
							break;

						case 94:
							type.name = "Tutorial Island";
							type.anInt1959 = 16777215;
							type.anInt1968 = 1;
							type.anInt1980 = -1;// -1;// 1129;
							break;

						case 95:
							type.name = "Port<br>Sarim";
							type.anInt1959 = 16777215;
							type.anInt1968 = 1;
							type.anInt1980 = -1;// -1;// 1129;
							break;

						case 96:
							type.name = "Falador";
							type.anInt1959 = 16777215;
							type.anInt1968 = 1;
							type.anInt1980 = -1;// -1;// 1129;
							break;

						case 97:
							type.name = "Edgeville";
							type.anInt1959 = 16777215;
							type.anInt1968 = 1;
							type.anInt1980 = -1;// -1;// 1129;
							break;

						case 98:
							type.name = "Brimhaven";
							type.anInt1959 = 16777215;
							type.anInt1968 = 1;
							type.anInt1980 = -1;// -1;// 1129;
							break;

						case 99:
							type.name = "Karamja";
							type.anInt1959 = 16750623;
							type.anInt1968 = 2;
							type.anInt1980 = -1;// -1;// 1129;
							break;

						case 100:
							type.name = "Crandor";
							type.anInt1959 = 16750623;
							type.anInt1968 = 2;
							type.anInt1980 = -1;// -1;// 1129;
							break;

						case 101:
							type.name = "Entrana";
							type.anInt1959 = 16750623;
							type.anInt1968 = 2;
							type.anInt1980 = -1;// -1;// 1129;
							break;

						case 102:
							type.name = "Draynor<br>Village";
							type.anInt1959 = 16777215;
							type.anInt1968 = 1;
							type.anInt1980 = -1;// -1;// 1129;
							break;

						case 103:
							type.name = "Jail";
							type.anInt1959 = 16777215;
							type.anInt1980 = -1;// -1;// 1129;
							break;

						case 104:
							type.name = "Market";
							type.anInt1959 = 16777215;
							type.anInt1980 = -1;// -1;// 1129;
							break;

						case 105:
							type.name = "Draynor Manor";
							type.anInt1959 = 16777215;
							type.anInt1968 = 1;
							type.anInt1980 = -1;// -1;// 1129;
							break;

						case 106:
							type.name = "Barbarian<br>Village";
							type.anInt1959 = 16777215;
							type.anInt1968 = 1;
							type.anInt1980 = -1;// -1;// 1129;
							break;

						case 107:
							type.name = "Desert Mining<br>Camp";
							type.anInt1959 = 16777215;
							type.anInt1980 = -1;// -1;// 1129;
							break;

						case 108:
							type.name = "Bedabin<br>Camp";
							type.anInt1959 = 16777215;
							type.anInt1980 = -1;// -1;// 1129;
							break;

						case 109:
							type.name = "Exam Centre";
							type.anInt1959 = 16777215;
							type.anInt1980 = -1;// -1;// 1129;
							break;

						case 110:
							type.name = "Digsite";
							type.anInt1959 = 16777215;
							type.anInt1980 = -1;// -1;// 1129;
							break;

						case 111:
							type.name = "Lumber Yard";
							type.anInt1959 = 16777215;
							type.anInt1980 = -1;// -1;// 1129;
							break;

						case 112:
							type.name = "Palace";
							type.anInt1959 = 16777215;
							type.anInt1980 = -1;// -1;// 1129;
							break;

						case 113:
							type.name = "Grand Exchange";
							type.anInt1959 = 16777215;
							type.anInt1980 = -1;// -1;// 1129;
							break;

						case 114:
							type.name = "Cooks'<br>Guild";
							type.anInt1959 = 16777215;
							type.anInt1980 = -1;// -1;// 1129;
							break;

						case 115:
							type.name = "Monastery";
							type.anInt1959 = 16777215;
							type.anInt1980 = -1;// -1;// 1129;
							break;

						case 116:
							type.name = "Dwarven<br>Mine";
							type.anInt1959 = 16777215;
							type.anInt1980 = -1;// -1;// 1129;
							break;

						case 117:
							type.name = "Ice<br>Mountain";
							type.anInt1959 = 16777215;
							type.anInt1968 = 1;
							type.anInt1980 = -1;// -1;// 1129;
							break;

						case 118:
							type.name = "Black Knights'<br>Fortress";
							type.anInt1959 = 16777215;
							type.anInt1980 = -1;// -1;// 1129;
							break;

						case 119:
							type.name = "Goblin<br>Village";
							type.anInt1959 = 16777215;
							type.anInt1980 = -1;// -1;// 1129;
							break;

						case 120:
							type.name = "White Knights'<br>Castle";
							type.anInt1959 = 16777215;
							type.anInt1980 = -1;// -1;// 1129;
							break;

						case 121:
							type.name = "Park";
							type.anInt1959 = 16777215;
							type.anInt1980 = -1;// -1;// 1129;
							break;

						case 122:
							type.name = "Kingdom of<br>Asgarnia";
							type.anInt1959 = 16750623;
							type.anInt1968 = 2;
							type.anInt1980 = -1;// -1;// 1129;
							break;

						case 123:
							type.name = "Dark Wizards'<br>Tower";
							type.anInt1959 = 16777215;
							type.anInt1980 = -1;// -1;// 1129;
							break;

						case 124:
							type.name = "Slayer<br>Tower";
							type.anInt1959 = 16777215;
							type.anInt1980 = -1;// -1;// 1129;
							break;

						case 125:
							type.name = "Melzar's<br>Maze";
							type.anInt1959 = 16777215;
							type.anInt1980 = -1;// -1;// 1129;
							break;

						case 126:
							type.name = "Rimmington";
							type.anInt1959 = 16777215;
							type.anInt1980 = -1;// -1;// 1129;
							break;

						case 127:
							type.name = "Wizards' Tower";
							type.anInt1959 = 16777215;
							type.anInt1980 = -1;// -1;// 1129;
							break;

						case 128:
							type.name = "Lumbridge<br>Swamp";
							type.anInt1959 = 16777215;
							type.anInt1980 = -1;// -1;// 1129;
							break;

						case 129:
							type.name = "Fishing<br>Platform";
							type.anInt1959 = 16777215;
							type.anInt1980 = -1;// -1;// 1129;
							break;

						case 130:
							type.name = "Tai Bwo Wannai";
							type.anInt1959 = 16777215;
							type.anInt1980 = -1;// -1;// 1129;
							break;

						case 131:
							type.name = "Shilo Village";
							type.anInt1959 = 16777215;
							type.anInt1968 = 1;
							type.anInt1980 = -1;// -1;// 1129;
							break;

						case 132:
							type.name = "Ship Yard";
							type.anInt1959 = 16777215;
							type.anInt1980 = -1;// -1;// 1129;
							break;

						case 133:
							type.name = "Cairn Isle";
							type.anInt1959 = 16777215;
							type.anInt1980 = -1;// -1;// 1129;
							break;

						case 134:
							type.name = "Kharazi Jungle";
							type.anInt1959 = 16777215;
							type.anInt1980 = -1;// -1;// 1129;
							break;

						case 135:
							type.name = "Chaos<br>Temple";
							type.anInt1959 = 16777215;
							type.anInt1980 = -1;// -1;// 1129;
							break;

						case 136:
							type.name = "Graveyard of<br>Shadows";
							type.anInt1959 = 16777215;
							type.anInt1980 = -1;// -1;// 1129;
							break;

						case 137:
							type.name = "Bandit<br>Camp";
							type.anInt1959 = 16777215;
							type.anInt1980 = -1;// -1;// 1129;
							break;

						case 138:
							type.name = "Dark Warriors'<br>Fortress";
							type.anInt1959 = 16777215;
							type.anInt1980 = -1;// -1;// 1129;
							break;

						case 139:
							type.name = "Ruins";
							type.anInt1959 = 16777215;
							type.anInt1980 = -1;// -1;// 1129;
							break;

						case 140:
							type.name = "The Forgotten<br>Cemetery";
							type.anInt1959 = 16777215;
							type.anInt1980 = -1;// -1;// 1129;
							break;

						case 141:
							type.name = "Bone Yard";
							type.anInt1959 = 16777215;
							type.anInt1980 = -1;// -1;// 1129;
							break;

						case 142:
							type.name = "Lava Dragon<br>Isle";
							type.anInt1959 = 16777215;
							type.anInt1980 = -1;// -1;// 1129;
							break;

						case 143:
							type.name = "Wilderness";
							type.anInt1959 = 16750623;
							type.anInt1968 = 2;
							type.anInt1980 = -1;// -1;// 1129;
							break;

						case 144:
							type.name = "Lava Maze";
							type.anInt1959 = 16777215;
							type.anInt1980 = -1;// -1;// 1129;
							break;

						case 145:
							type.name = "Frozen Waste<br>Plateau";
							type.anInt1959 = 16777215;
							type.anInt1980 = -1;// -1;// 1129;
							break;

						case 146:
							type.name = "Agility Training<br>Area";
							type.anInt1959 = 16777215;
							type.anInt1980 = -1;// -1;// 1129;
							break;

						case 147:
							type.name = "Pirates'<br>Hideout";
							type.anInt1959 = 16777215;
							type.anInt1980 = -1;// -1;// 1129;
							break;

						case 148:
							type.name = "Mage Arena";
							type.anInt1959 = 16777215;
							type.anInt1980 = -1;// -1;// 1129;
							break;

						case 149:
							type.name = "Deserted<br>Keep";
							type.anInt1959 = 16777215;
							type.anInt1980 = -1;// -1;// 1129;
							break;

						case 150:
							type.name = "Resource Area";
							type.anInt1959 = 16777215;
							type.anInt1980 = -1;// -1;// 1129;
							break;

						case 151:
							type.name = "Scorpion<br>Pit";
							type.anInt1959 = 16777215;
							type.anInt1980 = -1;// -1;// 1129;
							break;

						case 152:
							type.name = "Rogues' Castle";
							type.anInt1959 = 16777215;
							type.anInt1980 = -1;// -1;// 1129;
							break;

						case 153:
							type.name = "Demonic<br>Ruins";
							type.anInt1959 = 16777215;
							type.anInt1980 = -1;// -1;// 1129;
							break;

						case 154:
							type.name = "Fountain<br>of Rune";
							type.anInt1959 = 16777215;
							type.anInt1980 = -1;// -1;// 1129;
							break;

						case 155:
							type.name = "Bear";
							type.anInt1959 = 16777215;
							type.anInt1980 = -1;// -1;// 1129;
							break;

						case 156:
							type.name = "Spider";
							type.anInt1959 = 16777215;
							type.anInt1980 = -1;// -1;// 1129;
							break;

						case 157:
							type.name = "Heroes'<br>Guild";
							type.anInt1959 = 16777215;
							type.anInt1980 = -1;// -1;// 1129;
							break;

						case 158:
							type.name = "Druids'<br>Circle";
							type.anInt1959 = 16777215;
							type.anInt1980 = -1;// -1;// 1129;
							break;

						case 159:
							type.name = "Taverley";
							type.anInt1959 = 16777215;
							type.anInt1980 = -1;// -1;// 1129;
							break;

						case 160:
							type.name = "White Wolf<br>Mountain";
							type.anInt1959 = 16777215;
							type.anInt1968 = 1;
							type.anInt1980 = -1;// -1;// 1129;
							break;

						case 161:
							type.name = "Catherby";
							type.anInt1959 = 16777215;
							type.anInt1980 = -1;// -1;// 1129;
							break;

						case 162:
							type.name = "Camelot<br>Castle";
							type.anInt1959 = 16777215;
							type.anInt1980 = -1;// -1;// 1129;
							break;

						case 163:
							type.name = "Seers'<br>Village";
							type.anInt1959 = 16777215;
							type.anInt1968 = 1;
							type.anInt1980 = -1;// -1;// 1129;
							break;

						case 164:
							type.name = "Sinclair Mansion";
							type.anInt1959 = 16777215;
							type.anInt1980 = -1;// -1;// 1129;
							break;

						case 165:
							type.name = "Flax";
							type.anInt1959 = 16777215;
							type.anInt1980 = -1;// -1;// 1129;
							break;

						case 166:
							type.name = "Beehives";
							type.anInt1959 = 16777215;
							type.anInt1980 = -1;// -1;// 1129;
							break;

						case 167:
							type.name = "Sorcerer's Tower";
							type.anInt1959 = 16777215;
							type.anInt1980 = -1;// -1;// 1129;
							break;

						case 168:
							type.name = "Keep<br>Le Faye";
							type.anInt1959 = 16777215;
							type.anInt1980 = -1;// -1;// 1129;
							break;

						case 169:
							type.name = "Legends'<br>Guild";
							type.anInt1959 = 16777215;
							type.anInt1980 = -1;// -1;// 1129;
							break;

						case 170:
							type.name = "Hemenster";
							type.anInt1959 = 16777215;
							type.anInt1980 = -1;// -1;// 1129;
							break;

						case 171:
							type.name = "McGrubor's<br>Wood";
							type.anInt1959 = 16777215;
							type.anInt1980 = -1;// -1;// 1129;
							break;

						case 172:
							type.name = "Coal<br>Trucks";
							type.anInt1959 = 16777215;
							type.anInt1980 = -1;// -1;// 1129;
							break;

						case 173:
							type.name = "Fishing<br>Guild";
							type.anInt1959 = 16777215;
							type.anInt1980 = -1;// -1;// 1129;
							break;

						case 174:
							type.name = "Kingdom of<br>Kandarin";
							type.anInt1959 = 16750623;
							type.anInt1968 = 2;
							type.anInt1980 = -1;// -1;// 1129;
							break;

						case 175:
							type.name = "East<br>Ardougne";
							type.anInt1959 = 16777215;
							type.anInt1968 = 1;
							type.anInt1980 = -1;// -1;// 1129;
							break;

						case 176:
							type.name = "West<br>Ardougne";
							type.anInt1959 = 16777215;
							type.anInt1968 = 1;
							type.anInt1980 = -1;// -1;// 1129;
							break;

						case 177:
							type.name = "Combat<br>Training<br>Camp";
							type.anInt1959 = 16777215;
							type.anInt1980 = -1;// -1;// 1129;
							break;

						case 178:
							type.name = "Agility<br>Training<br>Area";
							type.anInt1959 = 16777215;
							type.anInt1980 = -1;// -1;// 1129;
							break;

						case 179:
							type.name = "Tree Gnome<br>Stronghold";
							type.anInt1959 = 16777215;
							type.anInt1968 = 1;
							type.anInt1980 = -1;// -1;// 1129;
							break;

						case 180:
							type.name = "Grand Tree";
							type.anInt1959 = 16777215;
							type.anInt1980 = -1;// -1;// 1129;
							break;

						case 181:
							type.name = "Swamp";
							type.anInt1959 = 16777215;
							type.anInt1980 = -1;// -1;// 1129;
							break;

						case 182:
							type.name = "Gnome Ball<br>Field";
							type.anInt1959 = 16777215;
							type.anInt1980 = -1;// -1;// 1129;
							break;

						case 183:
							type.name = "Barbarian<br>Outpost";
							type.anInt1959 = 16777215;
							type.anInt1980 = -1;// -1;// 1129;
							break;

						case 184:
							type.name = "Underground<br>Pass";
							type.anInt1959 = 16777215;
							type.anInt1980 = -1;// -1;// 1129;
							break;

						case 185:
							type.name = "Battlefield";
							type.anInt1959 = 16777215;
							type.anInt1980 = -1;// -1;// 1129;
							break;

						case 186:
							type.name = "Baxtorian<br>Falls";
							type.anInt1959 = 16777215;
							type.anInt1980 = -1;// -1;// 1129;
							break;

						case 187:
							type.name = "Tree Gnome<br>Village";
							type.anInt1959 = 16777215;
							type.anInt1980 = -1;// -1;// 1129;
							break;

						case 188:
							type.name = "Fight<br>Arena";
							type.anInt1959 = 16777215;
							type.anInt1980 = -1;// -1;// 1129;
							break;

						case 189:
							type.name = "Port<br>Khazard";
							type.anInt1959 = 16777215;
							type.anInt1968 = 1;
							type.anInt1980 = -1;// -1;// 1129;
							break;

						case 190:
							type.name = "Yanille";
							type.anInt1959 = 16777215;
							type.anInt1968 = 1;
							type.anInt1980 = -1;// -1;// 1129;
							break;

						case 191:
							type.name = "Wizards'<br>Guild";
							type.anInt1959 = 16777215;
							type.anInt1980 = -1;// -1;// 1129;
							break;

						case 192:
							type.name = "Nightmare<br>Zone";
							type.anInt1959 = 16777215;
							type.anInt1980 = -1;// -1;// 1129;
							break;

						case 193:
							type.name = "Observatory";
							type.anInt1959 = 16777215;
							type.anInt1980 = -1;// -1;// 1129;
							break;

						case 194:
							type.name = "Necromancer";
							type.anInt1959 = 16777215;
							type.anInt1980 = -1;// -1;// 1129;
							break;

						case 195:
							type.name = "Gu'Tanoth";
							type.anInt1959 = 16777215;
							type.anInt1980 = -1;// -1;// 1129;
							break;

						case 196:
							type.name = "Feldip<br>Hills";
							type.anInt1959 = 16750623;
							type.anInt1968 = 2;
							type.anInt1980 = -1;// -1;// 1129;
							break;

						case 197:
							type.name = "Ardougne<br>Zoo";
							type.anInt1959 = 16777215;
							type.anInt1980 = -1;// -1;// 1129;
							break;

						case 198:
							type.name = "Morytania";
							type.anInt1959 = 16750623;
							type.anInt1968 = 2;
							type.anInt1980 = -1;// -1;// 1129;
							break;

						case 199:
							type.name = "Mort Myre<br>Swamp";
							type.anInt1959 = 16777215;
							type.anInt1968 = 1;
							type.anInt1980 = -1;// -1;// 1129;
							break;

						case 200:
							type.name = "Canifis";
							type.anInt1959 = 16777215;
							type.anInt1968 = 1;
							type.anInt1980 = -1;// -1;// 1129;
							break;

						case 201:
							type.name = "River<br>Salve";
							type.anInt1959 = 16777215;
							type.anInt1980 = -1;// -1;// 1129;
							break;

						case 202:
							type.name = "River Lum";
							type.anInt1959 = 16777215;
							type.anInt1980 = -1;// -1;// 1129;
							break;

						case 203:
							type.name = "Crombwick<br>Manor";
							type.anInt1959 = 16777215;
							type.anInt1980 = -1;// -1;// 1129;
							break;

						case 204:
							type.name = "Agility<br>Arena";
							type.anInt1959 = 16777215;
							type.anInt1980 = -1;// -1;// 1129;
							break;

						case 205:
							type.name = "Burthorpe";
							type.anInt1959 = 16777215;
							type.anInt1968 = 1;
							type.anInt1980 = -1;// -1;// 1129;
							break;

						case 206:
							type.name = "Death<br>Plateau";
							type.anInt1959 = 16777215;
							type.anInt1980 = -1;// -1;// 1129;
							break;

						case 207:
							type.name = "Tirannwn";
							type.anInt1959 = 16750623;
							type.anInt1968 = 2;
							type.anInt1980 = -1;// -1;// 1129;
							break;

						case 208:
							type.name = "Isafdar";
							type.anInt1959 = 16777215;
							type.anInt1968 = 1;
							type.anInt1980 = -1;// -1;// 1129;
							break;

						case 209:
							type.name = "Arandar";
							type.anInt1959 = 16777215;
							type.anInt1968 = 1;
							type.anInt1980 = -1;// -1;// 1129;
							break;

						case 210:
							type.name = "Prifddinas";
							type.anInt1959 = 16777215;
							type.anInt1968 = 1;
							type.anInt1980 = -1;// -1;// 1129;
							break;

						case 211:
							type.name = "Tyras<br>Camp";
							type.anInt1959 = 16777215;
							type.anInt1980 = -1;// -1;// 1129;
							break;

						case 212:
							type.name = "Elf<br>Camp";
							type.anInt1959 = 16777215;
							type.anInt1980 = -1;// -1;// 1129;
							break;

						case 213:
							type.name = "Trollheim";
							type.anInt1959 = 16777215;
							type.anInt1980 = -1;// -1;// 1129;
							break;

						case 214:
							type.name = "Troll<br>Stronghold";
							type.anInt1959 = 16777215;
							type.anInt1980 = -1;// -1;// 1129;
							break;

						case 215:
							type.name = "Mort'ton";
							type.anInt1959 = 16777215;
							type.anInt1968 = 1;
							type.anInt1980 = -1;// -1;// 1129;
							break;

						case 216:
							type.name = "The<br>Hollows";
							type.anInt1959 = 16777215;
							type.anInt1980 = -1;// -1;// 1129;
							break;

						case 217:
							type.name = "Poison Waste";
							type.anInt1959 = 16777215;
							type.anInt1980 = -1;// -1;// 1129;
							break;

						case 218:
							type.name = "Castle Wars";
							type.anInt1959 = 16777215;
							type.anInt1980 = -1;// -1;// 1129;
							break;

						case 219:
							type.name = "Champions'<br>Guild";
							type.anInt1959 = 16777215;
							type.anInt1980 = -1;// -1;// 1129;
							break;

						case 220:
							type.name = "Rellekka";
							type.anInt1959 = 16777215;
							type.anInt1968 = 1;
							type.anInt1980 = -1;// -1;// 1129;
							break;

						case 221:
							type.name = "Golden<br>Apple Tree";
							type.anInt1959 = 16777215;
							type.anInt1980 = -1;// -1;// 1129;
							break;

						case 222:
							type.name = "Lighthouse";
							type.anInt1959 = 16777215;
							type.anInt1980 = -1;// -1;// 1129;
							break;

						case 223:
							type.name = "Fremennik<br>Province";
							type.anInt1959 = 16777215;
							type.anInt1968 = 1;
							type.anInt1980 = -1;// -1;// 1129;
							break;

						case 224:
							type.name = "Crafting<br>Guild";
							type.anInt1959 = 16777215;
							type.anInt1980 = -1;// -1;// 1129;
							break;

						case 225:
							type.name = "Haunted<br>Woods";
							type.anInt1959 = 16777215;
							type.anInt1980 = -1;// -1;// 1129;
							break;

						case 226:
							type.name = "Miscellania";
							type.anInt1959 = 16750623;
							type.anInt1968 = 2;
							type.anInt1980 = -1;// -1;// 1129;
							break;

						case 227:
							type.name = "Etceteria";
							type.anInt1959 = 16777215;
							type.anInt1968 = 1;
							type.anInt1980 = -1;// -1;// 1129;
							break;

						case 228:
							type.name = "Ape Atoll";
							type.anInt1959 = 16750623;
							type.anInt1968 = 2;
							type.anInt1980 = -1;// -1;// 1129;
							break;

						case 229:
							type.name = "Crash<br>Island";
							type.anInt1959 = 16777215;
							type.anInt1980 = -1;// -1;// 1129;
							break;

						case 230:
							type.name = "Marim";
							type.anInt1959 = 16777215;
							type.anInt1980 = -1;// -1;// 1129;
							break;

						case 231:
							type.name = "Fenkenstrain's<br>Castle";
							type.anInt1959 = 16777215;
							type.anInt1980 = -1;// -1;// 1129;
							break;

						case 232:
							type.name = "Mausoleum";
							type.anInt1959 = 16777215;
							type.anInt1980 = -1;// -1;// 1129;
							break;

						case 233:
							type.name = "Trollweiss<br>Mountain";
							type.anInt1959 = 16777215;
							type.anInt1980 = -1;// -1;// 1129;
							break;

						case 234:
							type.name = "Abandoned<br>Mine";
							type.anInt1959 = 16777215;
							type.anInt1980 = -1;// -1;// 1129;
							break;

						case 235:
							type.name = "Ruins of<br>Uzer";
							type.anInt1959 = 16777215;
							type.anInt1980 = -1;// -1;// 1129;
							break;

						case 236:
							type.name = "River<br>Elid";
							type.anInt1959 = 16777215;
							type.anInt1980 = -1;// -1;// 1129;
							break;

						case 237:
							type.name = "Kalphite<br>Lair";
							type.anInt1959 = 16777215;
							type.anInt1980 = -1;// -1;// 1129;
							break;

						case 238:
							type.name = "Pollnivneach";
							type.anInt1959 = 16777215;
							type.anInt1968 = 1;
							type.anInt1980 = -1;// -1;// 1129;
							break;

						case 239:
							type.spriteId = 6;
							type.name = "Pyramid";
							type.anInt1959 = 16777215;
							type.anInt1980 = -1;// -1;// 1129;
							break;

						case 240:
							type.name = "Menaphos";
							type.anInt1959 = 16777215;
							type.anInt1968 = 1;
							type.anInt1980 = -1;// -1;// 1129;
							break;

						case 241:
							type.name = "Sophanem";
							type.anInt1959 = 16777215;
							type.anInt1968 = 1;
							type.anInt1980 = -1;// -1;// 1129;
							break;

						case 242:
							type.name = "Agility<br>Pyramid";
							type.anInt1959 = 16777215;
							type.anInt1980 = -1;// -1;// 1129;
							break;

						case 243:
							type.name = "Graveyard";
							type.anInt1959 = 16777215;
							type.anInt1980 = -1;// -1;// 1129;
							break;

						case 244:
							type.name = "Port<br>Phasmatys";
							type.anInt1959 = 16777215;
							type.anInt1968 = 1;
							type.anInt1980 = -1;// -1;// 1129;
							break;

						case 245:
							type.name = "Ectofuntus";
							type.anInt1959 = 16777215;
							type.anInt1980 = -1;// -1;// 1129;
							break;

						case 246:
							type.name = "Mountain<br>Camp";
							type.anInt1959 = 16777215;
							type.anInt1980 = -1;// -1;// 1129;
							break;

						case 247:
							type.name = "Kharidian<br>Desert";
							type.anInt1959 = 16750623;
							type.anInt1968 = 2;
							type.anInt1980 = -1;// -1;// 1129;
							break;

						case 248:
							type.name = "Ice path";
							type.anInt1959 = 16777215;
							type.anInt1980 = -1;// -1;// 1129;
							break;

						case 249:
							type.name = "God Wars<br>Dungeon";
							type.anInt1959 = 16777215;
							type.anInt1980 = -1;// -1;// 1129;
							break;

						case 250:
							type.name = "Barrows";
							type.anInt1959 = 16777215;
							type.anInt1980 = -1;// -1;// 1129;
							break;

						case 251:
							type.name = "Jiggig";
							type.anInt1959 = 16777215;
							type.anInt1980 = -1;// -1;// 1129;
							break;

						case 252:
							type.name = "Mudskipper<br>Point";
							type.anInt1959 = 16777215;
							type.anInt1980 = -1;// -1;// 1129;
							break;

						case 253:
							type.name = "Nardah";
							type.anInt1959 = 16777215;
							type.anInt1980 = -1;// -1;// 1129;
							break;

						case 254:
							type.name = "Mage<br>Training<br>Arena";
							type.anInt1959 = 16777215;
							type.anInt1968 = 1;
							type.anInt1980 = -1;// -1;// 1129;
							break;

						case 255:
							type.name = "Keldagrim<br>Entrance";
							type.anInt1959 = 16777215;
							type.anInt1980 = -1;// -1;// 1129;
							break;

						case 256:
							type.name = "Waterbirth<br>Island";
							type.anInt1959 = 16777215;
							type.anInt1980 = -1;// -1;// 1129;
							break;

						case 257:
							type.name = "Lletya";
							type.anInt1959 = 16777215;
							type.anInt1980 = -1;// -1;// 1129;
							break;

						case 258:
							type.name = "Burgh de Rott";
							type.anInt1959 = 16777215;
							type.anInt1968 = 1;
							type.anInt1980 = -1;// -1;// 1129;
							break;

						case 259:
							type.name = "Lizards";
							type.anInt1959 = 16777215;
							type.anInt1980 = -1;// -1;// 1129;
							break;

						case 260:
							type.name = "Quarry";
							type.anInt1959 = 16777215;
							type.anInt1980 = -1;// -1;// 1129;
							break;

						case 261:
							type.name = "Outpost";
							type.anInt1959 = 16777215;
							type.anInt1980 = -1;// -1;// 1129;
							break;

						case 262:
							type.name = "Mos Le'Harmless";
							type.anInt1959 = 16777215;
							type.anInt1968 = 1;
							type.anInt1980 = -1;// -1;// 1129;
							break;

						case 263:
							type.spriteId = 6;
							type.name = "Distilleries";
							type.anInt1959 = 16777215;
							type.anInt1980 = -1;// -1;// 1129;
							break;

						case 264:
							type.name = "Ranging<br>Guild";
							type.anInt1959 = 16777215;
							type.anInt1980 = -1;// -1;// 1129;
							break;

						case 265:
							type.name = "Dragontooth<br>Island";
							type.anInt1959 = 16777215;
							type.anInt1980 = -1;// -1;// 1129;
							break;

						case 266:
							type.name = "Piscatoris<br>Fishing<br>Colony";
							type.anInt1959 = 16777215;
							type.anInt1980 = -1;// -1;// 1129;
							break;

						case 267:
							type.name = "Here be<br>penguins";
							type.anInt1959 = 16777215;
							type.anInt1980 = -1;// -1;// 1129;
							break;

						case 268:
							type.name = "Void Knights'<br>Outpost";
							type.anInt1959 = 16777215;
							type.anInt1980 = -1;// -1;// 1129;
							break;

						case 269:
							type.name = "Lunar Isle";
							type.anInt1959 = 16750623;
							type.anInt1968 = 2;
							type.anInt1980 = -1;// -1;// 1129;
							break;

						case 270:
							type.name = "Pirates' Cove";
							type.anInt1959 = 16777215;
							type.anInt1980 = -1;// -1;// 1129;
							break;

						case 271:
							type.name = "Vultures";
							type.anInt1959 = 16777215;
							type.anInt1980 = -1;// -1;// 1129;
							break;

						case 272:
							type.name = "Port<br>Tyras";
							type.anInt1959 = 16777215;
							type.anInt1980 = -1;// -1;// 1129;
							break;

						case 273:
							type.name = "Castle<br>Drakan";
							type.anInt1959 = 16777215;
							type.anInt1980 = -1;// -1;// 1129;
							break;

						case 274:
							type.spriteId = 85;
							type.anInt1980 = -1;// -1;// 1250;
							break;

						case 275:
							type.name = "Meiyerditch";
							type.anInt1959 = 16777215;
							type.anInt1968 = 1;
							type.anInt1980 = -1;// -1;// 1129;
							break;

						case 276:
							type.name = "Witchaven";
							type.anInt1959 = 16777215;
							type.anInt1980 = -1;// -1;// 1129;
							break;

						case 277:
							type.name = "Warriors'<br>Guild";
							type.anInt1959 = 16777215;
							type.anInt1980 = -1;// -1;// 1129;
							break;

						case 278:
							type.name = "Musa Point";
							type.anInt1959 = 16777215;
							type.anInt1980 = -1;// -1;// 1129;
							break;

						case 279:
							type.name = "Eagles'<br>Peak";
							type.anInt1959 = 16777215;
							type.anInt1968 = 1;
							type.anInt1980 = -1;// -1;// 1129;
							break;

						case 280:
							type.name = "Falconer";
							type.anInt1959 = 16777215;
							type.anInt1980 = -1;// -1;// 1129;
							break;

						case 281:
							type.name = "Barbarian<br>Assault";
							type.anInt1959 = 16777215;
							type.anInt1980 = -1;// -1;// 1129;
							break;

						case 282:
							type.name = "Iceberg";
							type.anInt1959 = 16777215;
							type.anInt1968 = 1;
							type.anInt1980 = -1;// -1;// 1129;
							break;

						case 283:
							type.name = "Pest<br>Control";
							type.anInt1959 = 16777215;
							type.anInt1980 = -1;// -1;// 1129;
							break;

						case 284:
							type.name = "Neitiznot";
							type.anInt1959 = 16777215;
							type.anInt1968 = 1;
							type.anInt1980 = -1;// -1;// 1129;
							break;

						case 285:
							type.name = "Jatizso";
							type.anInt1959 = 16777215;
							type.anInt1968 = 1;
							type.anInt1980 = -1;// -1;// 1129;
							break;

						case 286:
							type.name = "Fremennik Isles";
							type.anInt1959 = 16750623;
							type.anInt1968 = 2;
							type.anInt1980 = -1;// -1;// 1129;
							break;

						case 287:
							type.name = "Tower of<br>Life";
							type.anInt1959 = 16777215;
							type.anInt1980 = -1;// -1;// 1129;
							break;

						case 288:
							type.name = "Clocktower";
							type.anInt1959 = 16777215;
							type.anInt1980 = -1;// -1;// 1129;
							break;

						case 289:
							type.name = "Harmony";
							type.anInt1959 = 16777215;
							type.anInt1968 = 1;
							type.anInt1980 = -1;// -1;// 1129;
							break;

						case 290:
							type.name = "Trawler";
							type.anInt1959 = 16777215;
							type.anInt1980 = -1;// -1;// 1129;
							break;

						case 291:
							type.name = "Otto's<br>Grotto";
							type.anInt1959 = 16777215;
							type.anInt1980 = -1;// -1;// 1129;
							break;

						case 292:
							type.name = "Clan Wars";
							type.anInt1959 = 16777215;
							type.anInt1980 = -1;// -1;// 1129;
							break;

						case 293:
							type.name = "Zul-Andra";
							type.anInt1959 = 16777215;
							type.anInt1980 = -1;// -1;// 1129;
							break;

						case 294:
							type.name = "Zulrah's<br>shrine";
							type.anInt1959 = 16777215;
							type.anInt1980 = -1;// -1;// 1129;
							break;

						case 295:
							type.name = "Silvarea";
							type.anInt1959 = 16777215;
							type.anInt1980 = -1;// -1;// 1129;
							break;

						case 296:
							type.name = "Kingdom of<br>Great Kourend";
							type.anInt1959 = 16750623;
							type.anInt1968 = 2;
							type.anInt1980 = -1;// -1;// 1129;
							break;

						case 297:
							type.name = "Arceuus<br>House";
							type.anInt1959 = 16777215;
							type.anInt1968 = 1;
							type.anInt1980 = -1;// -1;// 1129;
							break;

						case 298:
							type.name = "Hosidius House";
							type.anInt1959 = 16777215;
							type.anInt1968 = 1;
							type.anInt1980 = -1;// -1;// 1129;
							break;

						case 299:
							type.name = "Lovakengj<br>House";
							type.anInt1959 = 16777215;
							type.anInt1968 = 1;
							type.anInt1980 = -1;// -1;// 1129;
							break;

						case 300:
							type.name = "Piscarilius<br>House";
							type.anInt1959 = 16777215;
							type.anInt1968 = 1;
							type.anInt1980 = -1;// -1;// 1129;
							break;

						case 301:
							type.name = "Shayzien<br>House";
							type.anInt1959 = 16777215;
							type.anInt1968 = 1;
							type.anInt1980 = -1;// -1;// 1129;
							break;

						case 302:
							type.name = "Library";
							type.anInt1959 = 16777215;
							type.anInt1980 = -1;// -1;// 1129;
							break;

						case 303:
							type.name = "Dark<br>Altar";
							type.anInt1959 = 16777215;
							type.anInt1980 = -1;// -1;// 1129;
							break;

						case 304:
							type.name = "Sulphur<br>mine";
							type.anInt1959 = 16777215;
							type.anInt1980 = -1;// -1;// 1129;
							break;

						case 305:
							type.name = "Blast mine";
							type.anInt1959 = 16777215;
							type.anInt1980 = -1;// -1;// 1129;
							break;

						case 306:
							type.name = "Lovakite<br>mine";
							type.anInt1959 = 16777215;
							type.anInt1980 = -1;// -1;// 1129;
							break;

						case 307:
							type.name = "Tower of<br>Magic";
							type.anInt1959 = 16777215;
							type.anInt1980 = -1;// -1;// 1129;
							break;

						case 308:
							type.name = "Mess";
							type.anInt1959 = 16777215;
							type.anInt1980 = -1;// -1;// 1129;
							break;

						case 309:
							type.name = "Saltpetre";
							type.anInt1959 = 16777215;
							type.anInt1980 = -1;// -1;// 1129;
							break;

						case 310:
							type.name = "Tithe farm";
							type.anInt1959 = 16777215;
							type.anInt1980 = -1;// -1;// 1129;
							break;

						case 311:
							type.name = "Charcoal<br>burners";
							type.anInt1959 = 16777215;
							type.anInt1980 = -1;// -1;// 1129;
							break;

						case 312:
							type.name = "Infirmary";
							type.anInt1959 = 16777215;
							type.anInt1980 = -1;// -1;// 1129;
							break;

						case 313:
							type.name = "Lizardman<br>Canyon";
							type.anInt1959 = 16777215;
							type.anInt1980 = -1;// -1;// 1129;
							break;

						case 314:
							type.name = "Combat<br>ring";
							type.anInt1959 = 16777215;
							type.anInt1980 = -1;// -1;// 1129;
							break;

						case 315:
							type.name = "Graveyard of<br>Heroes";
							type.anInt1959 = 16777215;
							type.anInt1980 = -1;// -1;// 1129;
							break;

						case 316:
							type.name = "Shamans";
							type.anInt1959 = 16777215;
							type.anInt1980 = -1;// -1;// 1129;
							break;

						case 317:
							type.name = "Vinery";
							type.anInt1959 = 16777215;
							type.anInt1980 = -1;// -1;// 1129;
							break;

						case 318:
							type.name = "Woodcutting<br>Guild";
							type.anInt1959 = 16777215;
							type.anInt1980 = -1;// -1;// 1129;
							break;

						case 319:
							type.name = "Doors of<br>Dinh";
							type.anInt1959 = 16777215;
							type.anInt1980 = -1;// -1;// 1129;
							break;

						case 320:
							type.name = "Wintertodt";
							type.anInt1959 = 16777215;
							type.anInt1968 = 1;
							type.anInt1980 = -1;// -1;// 1129;
							break;

						case 321:
							type.name = "Settlement<br>Ruins";
							type.anInt1959 = 16777215;
							type.anInt1980 = -1;// -1;// 1129;
							break;

						case 322:
							type.name = "Fishing Hamlet";
							type.anInt1959 = 16777215;
							type.anInt1980 = -1;// -1;// 1129;
							break;

						case 323:
							type.name = "Land's<br>End";
							type.anInt1959 = 16777215;
							type.anInt1980 = -1;// -1;// 1129;
							break;

						case 324:
							type.name = "Xeric's<br>Look out";
							type.anInt1959 = 16777215;
							type.anInt1980 = -1;// -1;// 1129;
							break;

						case 325:
							type.name = "Kourend<br>Woodland";
							type.anInt1959 = 16777215;
							type.anInt1968 = 1;
							type.anInt1980 = -1;// -1;// 1129;
							break;

						case 326:
							type.name = "Unmarked<br>Grave";
							type.anInt1959 = 16777215;
							type.anInt1980 = -1;// -1;// 1129;
							break;

						case 327:
							type.name = "Crabclaw<br>Isle";
							type.anInt1959 = 16777215;
							type.anInt1980 = -1;// -1;// 1129;
							break;

						case 328:
							type.name = "Shayziens'<br>Wall";
							type.anInt1959 = 16777215;
							type.anInt1980 = -1;// -1;// 1129;
							break;

						case 329:
							type.name = "Lacerta<br>Falls";
							type.anInt1959 = 16777215;
							type.anInt1980 = -1;// -1;// 1129;
							break;

						case 330:
							type.name = "Xeric's<br>Shrine";
							type.anInt1959 = 16777215;
							type.anInt1980 = -1;// -1;// 1129;
							break;

						case 331:
							type.name = "Lizardman<br>Settlement";
							type.anInt1959 = 16777215;
							type.anInt1980 = -1;// -1;// 1129;
							break;

						case 332:
							type.name = "Mount<br>Quidamortem";
							type.anInt1959 = 16777215;
							type.anInt1968 = 1;
							type.anInt1980 = -1;// -1;// 1129;
							break;

						case 333:
							type.name = "Chasm of Fire";
							type.anInt1959 = 16777215;
							type.anInt1980 = -1;// -1;// 1129;
							break;

						case 334:
							type.name = "Lower level";
							type.anInt1959 = 16776960;
							type.anInt1968 = 1;
							type.anInt1980 = -1;// -1;// 1129;
							break;

						case 335:
							type.name = "Middle level";
							type.anInt1959 = 16776960;
							type.anInt1968 = 1;
							type.anInt1980 = -1;// -1;// 1129;
							break;

						case 336:
							type.name = "Upper level";
							type.anInt1959 = 16776960;
							type.anInt1968 = 1;
							type.anInt1980 = -1;// -1;// 1129;
							break;

						case 337:
							type.name = "Waterfiends";
							type.anInt1959 = 16777215;
							type.anInt1980 = -1;// -1;// 1129;
							break;

						case 338:
							type.name = "Mithril<br>dragons";
							type.anInt1959 = 16777215;
							type.anInt1980 = -1;// -1;// 1129;
							break;

						case 339:
							type.name = "Brutal green<br>dragons";
							type.anInt1959 = 16777215;
							type.anInt1980 = -1;// -1;// 1129;
							break;

						case 340:
							type.name = "Shadow<br>warriors";
							type.anInt1959 = 16777215;
							type.anInt1980 = -1;// -1;// 1129;
							break;

						case 341:
							type.name = "Pit scorpions";
							type.anInt1959 = 16777215;
							type.anInt1980 = -1;// -1;// 1129;
							break;

						case 342:
							type.name = "Hobgoblins";
							type.anInt1959 = 16777215;
							type.anInt1980 = -1;// -1;// 1129;
							break;

						case 343:
							type.name = "Ardougne<br>Sewers";
							type.anInt1959 = 16777215;
							type.anInt1980 = -1;// -1;// 1129;
							break;

						case 344:
							type.name = "Zombies & rocks";
							type.anInt1959 = 16777215;
							type.anInt1980 = -1;// -1;// 1129;
							break;

						case 345:
							type.name = "Rat Pits";
							type.anInt1959 = 16777215;
							type.anInt1980 = -1;// -1;// 1129;
							break;

						case 346:
							type.name = "Bats";
							type.anInt1959 = 16777215;
							type.anInt1980 = -1;// -1;// 1129;
							break;

						case 347:
							type.name = "Ogres";
							type.anInt1959 = 16777215;
							type.anInt1980 = -1;// -1;// 1129;
							break;

						case 348:
							type.name = "Dungeon<br>rats";
							type.anInt1959 = 16777215;
							type.anInt1980 = -1;// -1;// 1129;
							break;

						case 349:
							type.name = "Spiders";
							type.anInt1959 = 16777215;
							type.anInt1980 = -1;// -1;// 1129;
							break;

						case 350:
							type.name = "Here be<br>goblins";
							type.anInt1959 = 16777215;
							type.anInt1980 = -1;// -1;// 1129;
							break;

						case 351:
							type.name = "Skeletal wyverns";
							type.anInt1959 = 16777215;
							type.anInt1980 = -1;// -1;// 1129;
							break;

						case 352:
							type.name = "Pirates";
							type.anInt1959 = 16777215;
							type.anInt1980 = -1;// -1;// 1129;
							break;

						case 353:
							type.name = "Muggers";
							type.anInt1959 = 16777215;
							type.anInt1980 = -1;// -1;// 1129;
							break;

						case 354:
							type.name = "Ice warriors<br>& giants";
							type.anInt1959 = 16777215;
							type.anInt1980 = -1;// -1;// 1129;
							break;

						case 355:
							type.name = "Distillery";
							type.anInt1959 = 16777215;
							type.anInt1980 = -1;// -1;// 1129;
							break;

						case 356:
							type.name = "Dead people";
							type.anInt1959 = 16777215;
							type.anInt1980 = -1;// -1;// 1129;
							break;

						case 357:
							type.name = "Nothing<br>interesting<br>happens<br>here";
							type.anInt1959 = 16777215;
							type.anInt1968 = 1;
							type.anInt1980 = -1;// -1;// 1129;
							break;

						case 358:
							type.name = "Agility course";
							type.anInt1959 = 16777215;
							type.anInt1980 = -1;// -1;// 1129;
							break;

						case 359:
							type.name = "Wire<br>machine";
							type.anInt1959 = 16777215;
							type.anInt1980 = -1;// -1;// 1129;
							break;

						case 360:
							type.name = "Bone<br>collector";
							type.anInt1959 = 16777215;
							type.anInt1980 = -1;// -1;// 1129;
							break;

						case 361:
							type.name = "Oldak's<br>teleportation<br>laboratory";
							type.anInt1959 = 16777215;
							type.anInt1980 = -1;// -1;// 1129;
							break;

						case 362:
							type.name = "Station";
							type.anInt1959 = 16777215;
							type.anInt1980 = -1;// -1;// 1129;
							break;

						case 363:
							type.name = "Council<br>chamber";
							type.anInt1959 = 16777215;
							type.anInt1980 = -1;// -1;// 1129;
							break;

						case 364:
							type.name = "Ur-tag";
							type.anInt1959 = 16777215;
							type.anInt1980 = -1;// -1;// 1129;
							break;

						case 365:
							type.name = "Ur-meg";
							type.anInt1959 = 16777215;
							type.anInt1980 = -1;// -1;// 1129;
							break;

						case 366:
							type.name = "Ur-zek & Ur-taal";
							type.anInt1959 = 16777215;
							type.anInt1980 = -1;// -1;// 1129;
							break;

						case 367:
							type.name = "Tegdak";
							type.anInt1959 = 16777215;
							type.anInt1980 = -1;// -1;// 1129;
							break;

						case 368:
							type.name = "Ur-vass";
							type.anInt1959 = 16777215;
							type.anInt1980 = -1;// -1;// 1129;
							break;

						case 369:
							type.name = "Nursery";
							type.anInt1959 = 16777215;
							type.anInt1980 = -1;// -1;// 1129;
							break;

						case 370:
							type.name = "Zanik's<br>bedroom";
							type.anInt1959 = 16777215;
							type.anInt1980 = -1;// -1;// 1129;
							break;

						case 371:
							type.name = "Beware of<br>the scorpions";
							type.anInt1959 = 16777215;
							type.anInt1980 = -1;// -1;// 1129;
							break;

						case 372:
							type.name = "Mining Guild";
							type.anInt1959 = 16777215;
							type.anInt1980 = -1;// -1;// 1129;
							break;

						case 373:
							type.name = "Access to<br>Falador";
							type.anInt1959 = 16777215;
							type.anInt1980 = -1;// -1;// 1129;
							break;

						case 374:
							type.name = "Hammerspike's<br>hangout";
							type.anInt1959 = 16777215;
							type.anInt1980 = -1;// -1;// 1129;
							break;

						case 375:
							type.name = "Boot";
							type.anInt1959 = 16777215;
							type.anInt1980 = -1;// -1;// 1129;
							break;

						case 376:
							type.name = "Kurask";
							type.anInt1959 = 16777215;
							type.anInt1980 = -1;// -1;// 1129;
							break;

						case 377:
							type.name = "Cave crawlers";
							type.anInt1959 = 16777215;
							type.anInt1980 = -1;// -1;// 1129;
							break;

						case 378:
							type.name = "Turoth";
							type.anInt1959 = 16777215;
							type.anInt1980 = -1;// -1;// 1129;
							break;

						case 379:
							type.name = "Basilisks";
							type.anInt1959 = 16777215;
							type.anInt1980 = -1;// -1;// 1129;
							break;

						case 380:
							type.name = "Rock<br>slugs";
							type.anInt1959 = 16777215;
							type.anInt1980 = -1;// -1;// 1129;
							break;

						case 381:
							type.name = "Cockatrice";
							type.anInt1959 = 16777215;
							type.anInt1980 = -1;// -1;// 1129;
							break;

						case 382:
							type.name = "Pyrefiends";
							type.anInt1959 = 16777215;
							type.anInt1980 = -1;// -1;// 1129;
							break;

						case 383:
							type.name = "Jellies";
							type.anInt1959 = 16777215;
							type.anInt1980 = -1;// -1;// 1129;
							break;

						case 384:
							type.name = "Saradomin's<br>Encampment";
							type.anInt1959 = 16777215;
							type.anInt1968 = 1;
							type.anInt1980 = -1;// -1;// 1129;
							break;

						case 385:
							type.name = "Zamorak's<br>Fortress";
							type.anInt1959 = 16777215;
							type.anInt1968 = 1;
							type.anInt1980 = -1;// -1;// 1129;
							break;

						case 386:
							type.name = "Armadyl's<br>Eyrie";
							type.anInt1959 = 16777215;
							type.anInt1968 = 1;
							type.anInt1980 = -1;// -1;// 1129;
							break;

						case 387:
							type.name = "Bandos's<br>Stronghold";
							type.anInt1959 = 16777215;
							type.anInt1968 = 1;
							type.anInt1980 = -1;// -1;// 1129;
							break;

						case 388:
							type.name = "Queen's<br>Chamber";
							type.anInt1959 = 16777215;
							type.anInt1980 = -1;// -1;// 1129;
							break;

						case 389:
							type.name = "Soldiers";
							type.anInt1959 = 16777215;
							type.anInt1980 = -1;// -1;// 1129;
							break;

						case 390:
							type.name = "Guardians";
							type.anInt1959 = 16777215;
							type.anInt1980 = -1;// -1;// 1129;
							break;

						case 391:
							type.name = "Moss giants";
							type.anInt1959 = 16777215;
							type.anInt1980 = -1;// -1;// 1129;
							break;

						case 392:
							type.name = "Dogs";
							type.anInt1959 = 16777215;
							type.anInt1980 = -1;// -1;// 1129;
							break;

						case 393:
							type.name = "Fire<br>giants";
							type.anInt1959 = 16777215;
							type.anInt1980 = -1;// -1;// 1129;
							break;

						case 394:
							type.name = "Red dragons";
							type.anInt1959 = 16777215;
							type.anInt1980 = -1;// -1;// 1129;
							break;

						case 395:
							type.name = "Bronze<br>dragons";
							type.anInt1959 = 16777215;
							type.anInt1980 = -1;// -1;// 1129;
							break;

						case 396:
							type.name = "Iron & steel<br>dragons";
							type.anInt1959 = 16777215;
							type.anInt1980 = -1;// -1;// 1129;
							break;

						case 397:
							type.name = "Bronze, iron &<br>steel dragons";
							type.anInt1959 = 16777215;
							type.anInt1980 = -1;// -1;// 1129;
							break;

						case 398:
							type.name = "Greater demons";
							type.anInt1959 = 16777215;
							type.anInt1980 = -1;// -1;// 1129;
							break;

						case 399:
							type.name = "Jogres";
							type.anInt1959 = 16777215;
							type.anInt1980 = -1;// -1;// 1129;
							break;

						case 400:
							type.name = "Elvarg";
							type.anInt1959 = 16777215;
							type.anInt1980 = -1;// -1;// 1129;
							break;

						case 401:
							type.name = "Lesser demons";
							type.anInt1959 = 16777215;
							type.anInt1980 = -1;// -1;// 1129;
							break;

						case 402:
							type.name = "Baby green<br>dragons";
							type.anInt1959 = 16777215;
							type.anInt1980 = -1;// -1;// 1129;
							break;

						case 403:
							type.name = "Black demons";
							type.anInt1959 = 16777215;
							type.anInt1980 = -1;// -1;// 1129;
							break;

						case 404:
							type.name = "Black<br>demons";
							type.anInt1959 = 16777215;
							type.anInt1980 = -1;// -1;// 1129;
							break;

						case 405:
							type.name = "Red Spiders";
							type.anInt1959 = 16777215;
							type.anInt1980 = -1;// -1;// 1129;
							break;

						case 406:
							type.name = "Skeletons";
							type.anInt1959 = 16777215;
							type.anInt1980 = -1;// -1;// 1129;
							break;

						case 407:
							type.name = "River Kelda";
							type.anInt1959 = 16777215;
							type.anInt1980 = -1;// -1;// 1129;
							break;

						case 408:
							type.name = "Dondakan's<br>rock";
							type.anInt1959 = 16777215;
							type.anInt1980 = -1;// -1;// 1129;
							break;

						case 409:
							type.name = "Minecarts";
							type.anInt1959 = 16777215;
							type.anInt1980 = -1;// -1;// 1129;
							break;

						case 410:
							type.name = "Blast<br>Furnace";
							type.anInt1959 = 16777215;
							type.anInt1980 = -1;// -1;// 1129;
							break;

						case 411:
							type.name = "Brewery";
							type.anInt1959 = 16777215;
							type.anInt1980 = -1;// -1;// 1129;
							break;

						case 412:
							type.name = "Black<br>Guard<br>H.Q.";
							type.anInt1959 = 16777215;
							type.anInt1980 = -1;// -1;// 1129;
							break;

						case 413:
							type.name = "Trolls";
							type.anInt1959 = 16777215;
							type.anInt1980 = -1;// -1;// 1129;
							break;

						case 414:
							type.name = "Exit";
							type.anInt1959 = 16777215;
							type.anInt1980 = -1;// -1;// 1129;
							break;

						case 415:
							type.name = "Terror dogs";
							type.anInt1959 = 16777215;
							type.anInt1980 = -1;// -1;// 1129;
							break;

						case 416:
							type.name = "Essence<br>Mine";
							type.anInt1959 = 16777215;
							type.anInt1980 = -1;// -1;// 1129;
							break;

						case 417:
							type.name = "Miscellania<br>Expansion";
							type.anInt1959 = 16777215;
							type.anInt1980 = -1;// -1;// 1129;
							break;

						case 418:
							type.name = "Sea snakes";
							type.anInt1959 = 16777215;
							type.anInt1980 = -1;// -1;// 1129;
							break;

						case 419:
							type.name = "H.A.M. Cult";
							type.anInt1959 = 16777215;
							type.anInt1980 = -1;// -1;// 1129;
							break;

						case 420:
							type.name = "Draynor Sewers";
							type.anInt1959 = 16777215;
							type.anInt1980 = -1;// -1;// 1129;
							break;

						case 421:
							type.name = "Mine";
							type.anInt1959 = 16777215;
							type.anInt1980 = -1;// -1;// 1129;
							break;

						case 422:
							type.name = "Watermill<br>cellar";
							type.anInt1959 = 16777215;
							type.anInt1980 = -1;// -1;// 1129;
							break;

						case 423:
							type.name = "Champions'<br>Challenge";
							type.anInt1959 = 16777215;
							type.anInt1980 = -1;// -1;// 1129;
							break;

						case 424:
							type.name = "Draynor Manor<br>basement";
							type.anInt1959 = 16777215;
							type.anInt1980 = -1;// -1;// 1129;
							break;

						case 425:
							type.name = "VTAM Corporation";
							type.anInt1959 = 16777215;
							type.anInt1980 = -1;// -1;// 1129;
							break;

						case 426:
							type.name = "Bank vault";
							type.anInt1959 = 16777215;
							type.anInt1980 = -1;// -1;// 1129;
							break;

						case 427:
							type.name = "Goblin Maze";
							type.anInt1959 = 16777215;
							type.anInt1980 = -1;// -1;// 1129;
							break;

						case 428:
							type.name = "Chasm of<br>Tears";
							type.anInt1959 = 16777215;
							type.anInt1980 = -1;// -1;// 1129;
							break;

						case 429:
							type.name = "Tears of<br>Guthix";
							type.anInt1959 = 16777215;
							type.anInt1980 = -1;// -1;// 1129;
							break;

						case 430:
							type.name = "Lumbridge<br>Castle<br>cellar";
							type.anInt1959 = 16777215;
							type.anInt1980 = -1;// -1;// 1129;
							break;

						case 431:
							type.name = "Giant frogs";
							type.anInt1959 = 16777215;
							type.anInt1980 = -1;// -1;// 1129;
							break;

						case 432:
							type.name = "Hill giants";
							type.anInt1959 = 16777215;
							type.anInt1980 = -1;// -1;// 1129;
							break;

						case 433:
							type.name = "Chronozon";
							type.anInt1959 = 16777215;
							type.anInt1980 = -1;// -1;// 1129;
							break;

						case 434:
							type.name = "Here be<br>dead stuff";
							type.anInt1959 = 16777215;
							type.anInt1980 = -1;// -1;// 1129;
							break;

						case 435:
							type.name = "Thugs";
							type.anInt1959 = 16777215;
							type.anInt1980 = -1;// -1;// 1129;
							break;

						case 436:
							type.name = "Dave's<br>Basement<br>of Doom";
							type.anInt1959 = 16777215;
							type.anInt1980 = -1;// -1;// 1129;
							break;

						case 437:
							type.name = "Poison spiders";
							type.anInt1959 = 16777215;
							type.anInt1980 = -1;// -1;// 1129;
							break;

						case 438:
							type.name = "Poison<br>spiders";
							type.anInt1959 = 16777215;
							type.anInt1980 = -1;// -1;// 1129;
							break;

						case 439:
							type.name = "Zombies";
							type.anInt1959 = 16777215;
							type.anInt1980 = -1;// -1;// 1129;
							break;

						case 440:
							type.name = "Skeletons<br>& zombies";
							type.anInt1959 = 16777215;
							type.anInt1980 = -1;// -1;// 1129;
							break;

						case 441:
							type.name = "Earth warriors";
							type.anInt1959 = 16777215;
							type.anInt1980 = -1;// -1;// 1129;
							break;

						case 442:
							type.name = "Chaos<br>druids";
							type.anInt1959 = 16777215;
							type.anInt1980 = -1;// -1;// 1129;
							break;

						case 443:
							type.name = "Rats";
							type.anInt1959 = 16777215;
							type.anInt1980 = -1;// -1;// 1129;
							break;

						case 444:
							type.name = "Here be a<br>big mole";
							type.anInt1959 = 16777215;
							type.anInt1968 = 1;
							type.anInt1980 = -1;// -1;// 1129;
							break;

						case 445:
							type.name = "Motherlode Mine";
							type.anInt1959 = 16777215;
							type.anInt1968 = 1;
							type.anInt1980 = -1;// -1;// 1129;
							break;

						case 446:
							type.name = "Obstacle<br>course";
							type.anInt1959 = 16777215;
							type.anInt1980 = -1;// -1;// 1129;
							break;

						case 447:
							type.name = "Skullball";
							type.anInt1959 = 16777215;
							type.anInt1980 = -1;// -1;// 1129;
							break;

						case 448:
							type.name = "Shades";
							type.anInt1959 = 16777215;
							type.anInt1980 = -1;// -1;// 1129;
							break;

						case 449:
							type.name = "Here be odd stuff";
							type.anInt1959 = 16777215;
							type.anInt1980 = -1;// -1;// 1129;
							break;

						case 450:
							type.name = "Nechryael";
							type.anInt1959 = 16777215;
							type.anInt1980 = -1;// -1;// 1129;
							break;

						case 451:
							type.name = "Abyssal<br>demons";
							type.anInt1959 = 16777215;
							type.anInt1980 = -1;// -1;// 1129;
							break;

						case 452:
							type.name = "Bloodvelds";
							type.anInt1959 = 16777215;
							type.anInt1980 = -1;// -1;// 1129;
							break;

						case 453:
							type.name = "Gargoyles";
							type.anInt1959 = 16777215;
							type.anInt1980 = -1;// -1;// 1129;
							break;

						case 454:
							type.name = "Aberrant<br>Spectres";
							type.anInt1959 = 16777215;
							type.anInt1980 = -1;// -1;// 1129;
							break;

						case 455:
							type.name = "Ankou";
							type.anInt1959 = 16777215;
							type.anInt1980 = -1;// -1;// 1129;
							break;

						case 456:
							type.name = "Cave horrors<br>&<br>albino bats";
							type.anInt1959 = 16777215;
							type.anInt1980 = -1;// -1;// 1129;
							break;

						case 457:
							type.name = "Altar";
							type.anInt1959 = 16777215;
							type.anInt1980 = -1;// -1;// 1129;
							break;

						case 458:
							type.name = "Lizards &<br>Zamorak fighters";
							type.anInt1959 = 16777215;
							type.anInt1980 = -1;// -1;// 1129;
							break;

						case 459:
							type.name = "War";
							type.anInt1959 = 16777215;
							type.anInt1968 = 1;
							type.anInt1980 = -1;// -1;// 1129;
							break;

						case 460:
							type.name = "Gift of<br>Peace";
							type.anInt1959 = 16777215;
							type.anInt1980 = -1;// -1;// 1129;
							break;

						case 461:
							type.name = "Famine";
							type.anInt1959 = 16777215;
							type.anInt1968 = 1;
							type.anInt1980 = -1;// -1;// 1129;
							break;

						case 462:
							type.name = "Grain of<br>Plenty";
							type.anInt1959 = 16777215;
							type.anInt1980 = -1;// -1;// 1129;
							break;

						case 463:
							type.name = "Pestilence";
							type.anInt1959 = 16777215;
							type.anInt1968 = 1;
							type.anInt1980 = -1;// -1;// 1129;
							break;

						case 464:
							type.name = "Box of<br>Health";
							type.anInt1959 = 16777215;
							type.anInt1980 = -1;// -1;// 1129;
							break;

						case 465:
							type.name = "Death";
							type.anInt1959 = 16777215;
							type.anInt1968 = 1;
							type.anInt1980 = -1;// -1;// 1129;
							break;

						case 466:
							type.name = "Cradle of<br>Life";
							type.anInt1959 = 16777215;
							type.anInt1980 = -1;// -1;// 1129;
							break;

						case 467:
							type.name = "Cauldron of<br>Thunder";
							type.anInt1959 = 16777215;
							type.anInt1980 = -1;// -1;// 1129;
							break;

						case 468:
							type.name = "Black Knights'<br>Base";
							type.anInt1959 = 16777215;
							type.anInt1968 = 1;
							type.anInt1980 = -1;// -1;// 1129;
							break;

						case 469:
							type.name = "Hellhounds";
							type.anInt1959 = 16777215;
							type.anInt1980 = -1;// -1;// 1129;
							break;

						case 470:
							type.name = "Fire Obelisk";
							type.anInt1959 = 16777215;
							type.anInt1980 = -1;// -1;// 1129;
							break;

						case 471:
							type.name = "Black dragons";
							type.anInt1959 = 16777215;
							type.anInt1980 = -1;// -1;// 1129;
							break;

						case 472:
							type.name = "Black<br>dragons";
							type.anInt1959 = 16777215;
							type.anInt1980 = -1;// -1;// 1129;
							break;

						case 473:
							type.name = "Dramen Tree";
							type.anInt1959 = 16777215;
							type.anInt1980 = -1;// -1;// 1129;
							break;

						case 474:
							type.name = "Animated<br>axes";
							type.anInt1959 = 16777215;
							type.anInt1980 = -1;// -1;// 1129;
							break;

						case 475:
							type.name = "Chaos<br>dwarves";
							type.anInt1959 = 16777215;
							type.anInt1980 = -1;// -1;// 1129;
							break;

						case 476:
							type.name = "Blue<br>dragons";
							type.anInt1959 = 16777215;
							type.anInt1980 = -1;// -1;// 1129;
							break;

						case 477:
							type.name = "Poisonous<br>scorpions";
							type.anInt1959 = 16777215;
							type.anInt1980 = -1;// -1;// 1129;
							break;

						case 478:
							type.name = "Cyclopes";
							type.anInt1959 = 16777215;
							type.anInt1980 = -1;// -1;// 1129;
							break;

						case 479:
							type.name = "Cyclopes<br>& Axes";
							type.anInt1959 = 16777215;
							type.anInt1980 = -1;// -1;// 1129;
							break;

						case 480:
							type.name = "Ice Queen's<br>Lair";
							type.anInt1959 = 16777215;
							type.anInt1980 = -1;// -1;// 1129;
							break;

						case 481:
							type.name = "Fountain<br>of Heroes";
							type.anInt1959 = 16777215;
							type.anInt1980 = -1;// -1;// 1129;
							break;

						case 482:
							type.name = "Mountain<br>dwarves";
							type.anInt1959 = 16777215;
							type.anInt1980 = -1;// -1;// 1129;
							break;

						case 483:
							type.name = "Nora T. Hagg's<br>cellar";
							type.anInt1959 = 16777215;
							type.anInt1980 = -1;// -1;// 1129;
							break;

						case 484:
							type.name = "Key Master";
							type.anInt1959 = 16777215;
							type.anInt1980 = -1;// -1;// 1129;
							break;

						case 485:
							type.name = "Beware of<br>the dog";
							type.anInt1959 = 16777215;
							type.anInt1968 = 1;
							type.anInt1980 = -1;// -1;// 1129;
							break;

						case 486:
							type.name = "Anger";
							type.anInt1959 = 16777215;
							type.anInt1980 = -1;// -1;// 1129;
							break;

						case 487:
							type.name = "Hopelessness";
							type.anInt1959 = 16777215;
							type.anInt1980 = -1;// -1;// 1129;
							break;

						case 488:
							type.name = "Confusion";
							type.anInt1959 = 16777215;
							type.anInt1980 = -1;// -1;// 1129;
							break;

						case 489:
							type.name = "Fear";
							type.anInt1959 = 16777215;
							type.anInt1980 = -1;// -1;// 1129;
							break;

						case 490:
							type.name = "Goutweed<br>store";
							type.anInt1959 = 16777215;
							type.anInt1980 = -1;// -1;// 1129;
							break;

						case 491:
							type.name = "Troll<br>generals";
							type.anInt1959 = 16777215;
							type.anInt1980 = -1;// -1;// 1129;
							break;

						case 492:
							type.name = "Burntmeat's<br>Kitchen";
							type.anInt1959 = 16777215;
							type.anInt1980 = -1;// -1;// 1129;
							break;

						case 493:
							type.name = "Fight Pit";
							type.anInt1959 = 16777215;
							type.anInt1968 = 1;
							type.anInt1980 = -1;// -1;// 1129;
							break;

						case 494:
							type.name = "Fight Cave";
							type.anInt1959 = 16777215;
							type.anInt1968 = 1;
							type.anInt1980 = -1;// -1;// 1129;
							break;

						case 495:
							type.name = "Sub-level 1";
							type.anInt1959 = 16776960;
							type.anInt1968 = 1;
							type.anInt1980 = -1;// -1;// 1129;
							break;

						case 496:
							type.name = "Sub-level 2";
							type.anInt1959 = 16776960;
							type.anInt1968 = 1;
							type.anInt1980 = -1;// -1;// 1129;
							break;

						case 497:
							type.name = "Sub-level 3";
							type.anInt1959 = 16776960;
							type.anInt1968 = 1;
							type.anInt1980 = -1;// -1;// 1129;
							break;

						case 498:
							type.name = "Sub-level 4";
							type.anInt1959 = 16776960;
							type.anInt1968 = 1;
							type.anInt1980 = -1;// -1;// 1129;
							break;

						case 499:
							type.name = "Sub-level 5";
							type.anInt1959 = 16776960;
							type.anInt1968 = 1;
							type.anInt1980 = -1;// -1;// 1129;
							break;

						case 500:
							type.name = "Sub-level 6";
							type.anInt1959 = 16776960;
							type.anInt1968 = 1;
							type.anInt1980 = -1;// -1;// 1129;
							break;

						case 501:
							type.name = "Dagannoth";
							type.anInt1959 = 16777215;
							type.anInt1980 = -1;// -1;// 1129;
							break;

						case 502:
							type.name = "Giant rock crabs";
							type.anInt1959 = 16777215;
							type.anInt1980 = -1;// -1;// 1129;
							break;

						case 503:
							type.name = "Giant<br>rock crabs";
							type.anInt1959 = 16777215;
							type.anInt1980 = -1;// -1;// 1129;
							break;

						case 504:
							type.name = "Rock lobsters";
							type.anInt1959 = 16777215;
							type.anInt1980 = -1;// -1;// 1129;
							break;

						case 505:
							type.name = "Rock<br>lobsters";
							type.anInt1959 = 16777215;
							type.anInt1980 = -1;// -1;// 1129;
							break;

						case 506:
							type.name = "Wallasalki";
							type.anInt1959 = 16777215;
							type.anInt1980 = -1;// -1;// 1129;
							break;

						case 507:
							type.name = "Dagannoth Kings";
							type.anInt1959 = 16777215;
							type.anInt1980 = -1;// -1;// 1129;
							break;

						case 508:
							type.name = "Shadow<br>spiders";
							type.anInt1959 = 16777215;
							type.anInt1980 = -1;// -1;// 1129;
							break;

						case 509:
							type.name = "Agility<br>course<br>spikes";
							type.anInt1959 = 16777215;
							type.anInt1980 = -1;// -1;// 1129;
							break;

						case 510:
							type.name = "Mage Arena<br>bank";
							type.anInt1959 = 16777215;
							type.anInt1980 = -1;// -1;// 1129;
							break;

						case 511:
							type.name = "King<br>Black<br>Dragon";
							type.anInt1959 = 16777215;
							type.anInt1980 = -1;// -1;// 1129;
							break;

						case 512:
							type.name = "Corporeal<br>Beast";
							type.anInt1959 = 16777215;
							type.anInt1980 = -1;// -1;// 1129;
							break;

						case 513:
							type.name = "Salarin the<br>Twisted";
							type.anInt1959 = 16777215;
							type.anInt1980 = -1;// -1;// 1129;
							break;

						case 514:
							type.name = "Smoke devils";
							type.anInt1959 = 16777215;
							type.anInt1980 = -1;// -1;// 1129;
							break;

						case 515:
							type.name = "Thermonuclear<br>Smoke Devil";
							type.anInt1959 = 16777215;
							type.anInt1980 = -1;// -1;// 1129;
							break;

						case 516:
							type.name = "Throne room";
							type.anInt1959 = 16777215;
							type.anInt1980 = -1;// -1;// 1129;
							break;

						case 517:
							type.name = "Beware of the<br>mushrooms";
							type.anInt1959 = 16777215;
							type.anInt1980 = -1;// -1;// 1129;
							break;

						case 518:
							type.name = "Shrine";
							type.anInt1959 = 16777215;
							type.anInt1980 = -1;// -1;// 1129;
							break;

						case 519:
							type.name = "Otherworldly<br>beings";
							type.anInt1959 = 16777215;
							type.anInt1980 = -1;// -1;// 1129;
							break;

						case 520:
							type.name = "Puro-puro";
							type.anInt1959 = 16777215;
							type.anInt1980 = -1;// -1;// 1129;
							break;

						case 521:
							type.name = "Tanglefeet";
							type.anInt1959 = 16777215;
							type.anInt1980 = -1;// -1;// 1129;
							break;

						case 522:
							type.name = "Brimstail";
							type.anInt1959 = 16777215;
							type.anInt1980 = -1;// -1;// 1129;
							break;

						case 523:
							type.name = "Voice of Yama";
							type.anInt1959 = 16777215;
							type.anInt1980 = -1;// -1;// 1129;
							break;

						case 524:
							type.name = "Ents";
							type.anInt1959 = 16777215;
							type.anInt1980 = -1;// -1;// 1129;
							break;

						case 525:
							type.name = "Black, blue &<br>red dragons";
							type.anInt1959 = 16777215;
							type.anInt1980 = -1;// -1;// 1129;
							break;

						case 526:
							type.name = "Bronze & iron<br>dragons";
							type.anInt1959 = 16777215;
							type.anInt1980 = -1;// -1;// 1129;
							break;

						case 527:
							type.name = "Steel<br>dragons";
							type.anInt1959 = 16777215;
							type.anInt1980 = -1;// -1;// 1129;
							break;

						case 528:
							type.name = "Dark<br>beasts";
							type.anInt1959 = 16777215;
							type.anInt1980 = -1;// -1;// 1129;
							break;

						case 529:
							type.name = "Ghosts";
							type.anInt1959 = 16777215;
							type.anInt1980 = -1;// -1;// 1129;
							break;

						case 530:
							type.name = "Dust devils";
							type.anInt1959 = 16777215;
							type.anInt1980 = -1;// -1;// 1129;
							break;

						case 531:
							type.name = "Sand<br>crabs";
							type.anInt1959 = 16777215;
							type.anInt1980 = -1;// -1;// 1129;
							break;

						case 532:
							type.name = "Spectres";
							type.anInt1959 = 16777215;
							type.anInt1980 = -1;// -1;// 1129;
							break;

						case 533:
							type.name = "Banshees";
							type.anInt1959 = 16777215;
							type.anInt1980 = -1;// -1;// 1129;
							break;

						case 534:
							type.name = "Mor Ul Rek";
							type.anInt1959 = 16750623;
							type.anInt1968 = 2;
							type.anInt1980 = -1;// -1;// 1129;
							break;

						case 535:
							type.name = "The Inferno";
							type.anInt1959 = 16777215;
							type.anInt1968 = 1;
							type.anInt1980 = -1;// -1;// 1129;
							break;

						case 536:
							type.name = "Lizardman<br>Caves";
							type.anInt1959 = 16777215;
							type.anInt1980 = -1;// -1;// 1129;
							break;

						case 537:
							type.name = "Underwater";
							type.anInt1959 = 16777215;
							type.anInt1968 = 1;
							type.anInt1980 = -1;// -1;// 1129;
							break;

						case 538:
							type.name = "Wyverns";
							type.anInt1959 = 16777215;
							type.anInt1980 = -1;// -1;// 1129;
							break;

						case 539:
							type.name = "Wyverns<br>(Task Only)";
							type.anInt1959 = 16777215;
							type.anInt1980 = -1;// -1;// 1129;
							break;

						case 540:
							type.name = "Volcano Mine";
							type.anInt1959 = 16777215;
							type.anInt1968 = 1;
							type.anInt1980 = -1;// -1;// 1129;
							break;

						case 541:
							type.name = "A";
							type.anInt1959 = 16777215;
							type.anInt1980 = -1;// -1;// 1129;
							break;

						case 542:
							type.name = "B";
							type.anInt1959 = 16777215;
							type.anInt1980 = -1;// -1;// 1129;
							break;

						case 543:
							type.name = "C";
							type.anInt1959 = 16777215;
							type.anInt1980 = -1;// -1;// 1129;
							break;

						case 544:
							type.name = "Fossil<br>Island";
							type.anInt1959 = 16750623;
							type.anInt1968 = 2;
							type.anInt1980 = -1;// -1;// 1129;
							break;

						case 545:
							type.name = "Museum<br>Camp";
							type.anInt1959 = 16777215;
							type.anInt1980 = -1;// -1;// 1129;
							break;

						case 546:
							type.name = "The Warrens";
							type.anInt1959 = 16777215;
							type.anInt1980 = -1;// -1;// 1129;
							break;

						case 547:
							type.name = "Mushroom<br>Forest";
							type.anInt1959 = 16777215;
							type.anInt1980 = -1;// -1;// 1129;
							break;

						case 548:
							type.name = "House<br>on the<br>Hill";
							type.anInt1959 = 16777215;
							type.anInt1980 = -1;// -1;// 1129;
							break;

						case 549:
							type.name = "Volcano";
							type.anInt1959 = 16777215;
							type.anInt1980 = -1;// -1;// 1129;
							break;

						case 550:
							type.name = "Tar<br>Swamp";
							type.anInt1959 = 16777215;
							type.anInt1980 = -1;// -1;// 1129;
							break;

						case 551:
							type.name = "Crabclaw Caves";
							type.anInt1959 = 16777215;
							type.anInt1980 = -1;// -1;// 1129;
							break;

						case 552:
							type.name = "Wilderness<br>Emblem Trader";
							type.anInt1959 = 16777215;
							type.anInt1980 = -1;// -1;// 1129;
							break;

						case 553:
							type.name = "Ice giants";
							type.anInt1959 = 16777215;
							type.anInt1980 = -1;// -1;// 1129;
							break;

						case 554:
							type.name = "Greater<br>demons";
							type.anInt1959 = 16777215;
							type.anInt1980 = -1;// -1;// 1129;
							break;

						case 555:
							type.name = "Green dragons";
							type.anInt1959 = 16777215;
							type.anInt1980 = -1;// -1;// 1129;
							break;

						case 556:
							type.name = "Green<br>dragons";
							type.anInt1959 = 16777215;
							type.anInt1980 = -1;// -1;// 1129;
							break;

						case 557:
							type.name = "Revenants";
							type.anInt1959 = 16777215;
							type.anInt1980 = -1;// -1;// 1129;
							break;

						case 558:
							type.name = "Myths'<br>Guild";
							type.anInt1959 = 16777215;
							type.anInt1980 = -1;// -1;// 1129;
							break;

						case 559:
							type.name = "Corsair<br>Cove";
							type.anInt1959 = 16777215;
							type.anInt1968 = 1;
							type.anInt1980 = -1;// -1;// 1129;
							break;

						case 560:
							type.name = "Ancient<br>Forge";
							type.anInt1959 = 16777215;
							type.anInt1980 = -1;// -1;// 1129;
							break;

						case 561:
							type.name = "Ogresses";
							type.anInt1959 = 16777215;
							type.anInt1980 = -1;// -1;// 1129;
							break;

						case 562:
							type.name = "Chinchompas";
							type.anInt1959 = 16777215;
							type.anInt1980 = -1;// -1;// 1129;
							break;

						case 563:
							type.name = "Dragons";
							type.anInt1959 = 16777215;
							type.anInt1980 = -1;// -1;// 1129;
							break;

						case 564:
							type.name = "Baby dragons";
							type.anInt1959 = 16777215;
							type.anInt1980 = -1;// -1;// 1129;
							break;

						case 565:
							type.name = "Chapel";
							type.anInt1959 = 16777215;
							type.anInt1980 = -1;// -1;// 1129;
							break;

						case 566:
							type.name = "Ungael";
							type.anInt1959 = 16777215;
							type.anInt1980 = -1;// -1;// 1129;
							break;

						case 567:
							type.name = "Lithkren";
							type.anInt1959 = 16777215;
							type.anInt1980 = -1;// -1;// 1129;
							break;

						case 568:
							type.name = "War Tent";
							type.anInt1959 = 16777215;
							type.anInt1980 = -1;// -1;// 1129;
							break;

						case 569:
							type.name = "Foodhall";
							type.anInt1959 = 16777215;
							type.anInt1980 = -1;// -1;// 1129;
							break;

						case 570:
							type.name = "Kourend<br>Castle";
							type.anInt1959 = 16777215;
							type.anInt1980 = -1;// -1;// 1129;
							break;

						case 571:
							type.name = "Kebos<br>Lowlands";
							type.anInt1959 = 16750623;
							type.anInt1968 = 2;
							type.anInt1980 = -1;// -1;// 1129;
							break;

						case 572:
							type.name = "Paterdomus<br>Temple";
							type.anInt1959 = 16777215;
							type.anInt1980 = -1;// -1;// 1129;
							break;

						case 573:
							type.name = "Darkmeyer";
							type.anInt1959 = 16777215;
							type.anInt1968 = 1;
							type.anInt1980 = -1;// -1;// 1129;
							break;

						case 574:
							type.name = "Slepe";
							type.anInt1959 = 16777215;
							type.anInt1968 = 1;
							type.anInt1980 = -1;// -1;// 1129;
							break;

						case 575:
							type.name = "Ver Sinhaza";
							type.anInt1959 = 16777215;
							type.anInt1980 = -1;// -1;// 1129;
							break;

						case 576:
							type.spriteId = 86;
							type.anInt1980 = -1;// -1;// 1251;
							break;

						case 577:
							type.spriteId = 87;
							type.anInt1980 = -1;// -1;// 1252;
							break;

						case 578:
							type.spriteId = 88;
							type.anInt1980 = -1;// -1;// 1253;
							break;

						case 579:
							type.spriteId = 89;
							type.anInt1980 = -1;// -1;// 1254;
							break;

						case 580:
							type.spriteId = 90;
							type.anInt1980 = -1;// -1;// 1255;
							break;

						case 581:
							type.spriteId = 91;
							type.anInt1980 = -1;// -1;// 1256;
							break;

						case 582:
							type.spriteId = 92;
							type.anInt1980 = -1;// -1;// 1257;
							break;

						case 583:
							type.spriteId = 93;
							type.anInt1980 = -1;// -1;// 1258;
							break;

						case 584:
							type.spriteId = 94;
							type.anInt1980 = -1;// -1;// 1259;
							break;

						case 585:
							type.spriteId = 95;
							type.anInt1980 = -1;// -1;// 1260;
							break;

						case 586:
							type.spriteId = 96;
							type.anInt1980 = -1;// -1;// 1261;
							break;

						case 587:
							type.spriteId = 97;
							type.anInt1980 = -1;// -1;// 1262;
							break;

						case 588:
							type.spriteId = 98;
							type.anInt1980 = -1;// -1;// 1263;
							break;

						case 589:
							type.spriteId = 99;
							type.anInt1980 = -1;// -1;// 1264;
							break;

						case 590:
							type.spriteId = 100;
							type.anInt1980 = -1;// -1;// 1265;
							break;

						case 591:
							type.spriteId = 101;
							type.anInt1980 = -1;// -1;// 1266;
							break;

						case 592:
							type.spriteId = 102;
							type.anInt1980 = -1;// -1;// 1267;
							break;

						case 593:
							type.spriteId = 103;
							type.anInt1980 = -1;// -1;// 1268;
							break;

						case 594:
							type.spriteId = 104;
							type.anInt1980 = -1;// -1;// 1269;
							break;

						case 595:
							type.spriteId = 105;
							type.anInt1980 = -1;// -1;// 1270;
							break;

						case 596:
							type.spriteId = 106;
							type.anInt1980 = -1;// -1;// 1271;
							break;

						case 597:
							type.spriteId = 107;
							type.anInt1980 = -1;// -1;// 1272;
							break;

						case 598:
							type.spriteId = 108;
							type.anInt1980 = -1;// -1;// 1273;
							break;

						case 599:
							type.spriteId = 109;
							type.anInt1980 = -1;// -1;// 1274;
							break;

						case 600:
							type.spriteId = 110;
							type.anInt1980 = -1;// -1;// 1275;
							break;
						case 601:
							type.name = "Kalphite<br>Cave";
							type.anInt1959 = 16777215;
							type.anInt1980 = -1;// -1;//-1;// -1;// 1129;
							break;

						case 602:
							type.name = "Lovakengj<br>Assembly";
							type.anInt1959 = 16777215;
							type.anInt1980 = -1;// -1;//-1;// -1;// 1129;
							break;

						case 603:
							type.name = "The Forsaken<br>Tower";
							type.anInt1959 = 16777215;
							type.anInt1980 = -1;// -1;//-1;// -1;// 1129;
							break;

						case 604:
							type.name = "Mount<br>Karuulm";
							type.anInt1959 = 16777215;
							type.anInt1968 = 1;
							type.anInt1980 = -1;// -1;//-1;// -1;// 1129;
							break;

						case 605:
							type.name = "Farming Guild";
							type.anInt1959 = 16777215;
							type.anInt1980 = -1;// -1;//-1;// -1;// 1129;
							break;

						case 606:
							type.name = "Battlefront";
							type.anInt1959 = 16777215;
							type.anInt1980 = -1;// -1;//-1;// -1;// 1129;
							break;

						case 607:
							type.name = "Molch";
							type.anInt1959 = 16777215;
							type.anInt1968 = 1;
							type.anInt1980 = -1;// -1;//-1;// -1;// 1129;
							break;

						case 608:
							type.name = "Kebos Swamp";
							type.anInt1959 = 16777215;
							type.anInt1968 = 1;
							type.anInt1980 = -1;// -1;//-1;// -1;// 1129;
							break;

						case 609:
							type.name = "Lake Molch";
							type.anInt1959 = 16777215;
							type.anInt1980 = -1;// -1;//-1;// -1;// 1129;
							break;

						case 610:
							type.name = "River Molch";
							type.anInt1959 = 16777215;
							type.anInt1980 = -1;// -1;//-1;// -1;// 1129;
							break;

						case 611:
							type.name = "Wyrms";
							type.anInt1959 = 16777215;
							type.anInt1980 = -1;// -1;//-1;// -1;// 1129;
							break;

						case 612:
							type.name = "Drakes";
							type.anInt1959 = 16777215;
							type.anInt1980 = -1;// -1;//-1;// -1;// 1129;
							break;

						case 613:
							type.name = "Hydras";
							type.anInt1959 = 16777215;
							type.anInt1980 = -1;// -1;//-1;// -1;// 1129;
							break;

						case 614:
							type.name = "Alchemical<br>Hydra";
							type.anInt1959 = 16777215;
							type.anInt1980 = -1;// -1;//-1;// -1;// 1129;
							break;

						case 615:
							type.name = "Sulphur<br>lizards";
							type.anInt1959 = 16777215;
							type.anInt1980 = -1;// -1;//-1;// -1;// 1129;
							break;

						case 616:
							type.name = "Tasakaal";
							type.anInt1959 = 16777215;
							type.anInt1980 = -1;// -1;//-1;// -1;// 1129;
							break;

						case 617:
							type.name = "Hespori";
							type.anInt1959 = 16777215;
							type.anInt1980 = -1;// -1;//-1;// -1;// 1129;
							break;

						case 618:
							type.name = "Lizardman<br>Temple";
							type.anInt1959 = 16777215;
							type.anInt1980 = -1;// -1;//-1;// -1;// 1129;
							break;

						case 619:
							type.spriteId = 111;
							type.anInt1980 = -1;// -1;//-1;// -1;// 1317;
							break;

						case 620:
							type.name = "Forthos Ruin";
							type.anInt1959 = 16777215;
							type.anInt1980 = -1;// -1;//-1;// -1;// 1129;
							break;

						case 621:
							type.name = "Kingstown";
							type.anInt1959 = 16777215;
							type.anInt1980 = -1;// -1;//-1;// -1;// 1129;
							break;

						case 622:
							type.name = "Wine of<br>Zamorak";
							type.anInt1959 = 16777215;
							type.anInt1980 = -1;// -1;//-1;// -1;// 1129;
							break;

						case 623:
							type.name = "Larran's<br>Chest";
							type.anInt1959 = 16777215;
							type.anInt1980 = -1;// -1;//-1;// -1;// 1129;
							break;

						case 624:
							type.name = "River<br>Dougne";
							type.anInt1959 = 16777215;
							type.anInt1980 = -1;// -1;//-1;// -1;// 1129;
							break;

						case 625:
							type.name = "River<br>Hos";
							type.anInt1959 = 16777215;
							type.anInt1980 = -1;// -1;//-1;// -1;// 1129;
							break;

						case 626:
							type.name = "Undead<br>druids";
							type.anInt1959 = 16777215;
							type.anInt1980 = -1;// -1;//-1;// -1;// 1129;
							break;

						case 627:
							type.name = "Bone<br>Burner";
							type.anInt1959 = 16777215;
							type.anInt1980 = -1;// -1;//-1;// -1;// 1129;
							break;

						case 628:
							type.name = "Temple Spiders";
							type.anInt1959 = 16777215;
							type.anInt1980 = -1;// -1;//-1;// -1;// 1129;
							break;

						case 629:
							type.name = "Sarachnis";
							type.anInt1959 = 16777215;
							type.anInt1980 = -1;// -1;//-1;// -1;// 1129;
							break;

						case 630:
							type.spriteId = 112;
							type.anInt1980 = -1;// -1;//-1;// -1;// 1070;
							break;

						case 631:
							type.spriteId = 113;
							type.anInt1980 = -1;// -1;//-1;// -1;// 1396;
							break;

						case 632:
							type.name = "Moss<br>giants";
							type.anInt1959 = 16777215;
							type.anInt1980 = -1;// -1;//-1;// -1;// 1129;
							break;

						case 633:
							type.name = "Trahaearn<br>Mine";
							type.anInt1959 = 16777215;
							type.anInt1980 = -1;// -1;//-1;// -1;// 1129;
							break;

						case 634:
							type.name = "Zalcano";
							type.anInt1959 = 16777215;
							type.anInt1980 = -1;// -1;//-1;// -1;// 1129;
							break;

						case 635:
							type.name = "The Gauntlet";
							type.anInt1959 = 16777215;
							type.anInt1980 = -1;// -1;//-1;// -1;// 1129;
							break;

						case 636:
							type.name = "Gwenith";
							type.anInt1959 = 16777215;
							type.anInt1980 = -1;// -1;//-1;// -1;// 1129;
							break;

						case 637:
							type.name = "Last Man<br>Standing";
							type.anInt1959 = 16777215;
							type.anInt1980 = -1;// -1;//-1;// -1;// 1129;
							break;

						case 638:
							type.name = "The Mountain";
							type.anInt1959 = 16777215;
							type.anInt1980 = -1;// -1;//-1;// -1;// 1129;
							break;

						case 639:
							type.name = "Trinity<br>Outpost";
							type.anInt1959 = 16777215;
							type.anInt1980 = -1;// -1;//-1;// -1;// 1129;
							break;

						case 640:
							type.name = "Debtor<br>Hideout";
							type.anInt1959 = 16777215;
							type.anInt1980 = -1;// -1;//-1;// -1;// 1129;
							break;

						case 641:
							type.name = "Moser<br>Settlement";
							type.anInt1959 = 16777215;
							type.anInt1980 = -1;// -1;//-1;// -1;// 1129;
							break;

						case 642:
							type.name = "Rocky<br>Outcrop";
							type.anInt1959 = 16777215;
							type.anInt1980 = -1;// -1;//-1;// -1;// 1129;
							break;

						case 643:
							type.name = "Island<br>of Stone";
							type.anInt1959 = 16777215;
							type.anInt1980 = -1;// -1;//-1;// -1;// 1129;
							break;

						case 644:
							type.name = "Basilisk<br>Knights";
							type.anInt1959 = 16777215;
							type.anInt1980 = -1;// -1;//-1;// -1;// 1129;
							break;

						case 645:
							type.spriteId = 114;
							type.anInt1980 = -1;// -1;//-1;// -1;// 176;
							break;

						case 646:
							type.name = "Lesser<br>demons";
							type.anInt1959 = 16777215;
							type.anInt1980 = -1;// -1;//-1;// -1;// 1129;
							break;

						case 647:
							type.name = "The<br>Nightmare";
							type.anInt1959 = 16777215;
							type.anInt1980 = -1;// -1;//-1;// -1;// 1129;
							break;

						case 648:
							type.name = "Shura";
							type.anInt1959 = 16777215;
							type.anInt1980 = -1;// -1;//-1;// -1;// 1129;
							break;

						case 649:
							type.name = "Elves";
							type.anInt1959 = 16777215;
							type.anInt1980 = -1;// -1;//-1;// -1;// 1129;
							break;

						case 650:
							type.spriteId = 115;
							type.anInt1980 = -1;// -1;//-1;// -1;// 1425;
							break;

						case 651:
							type.name = "Icyene<br>Graveyard";
							type.anInt1959 = 16777215;
							type.anInt1980 = -1;// -1;//-1;// -1;// 1129;
							break;

						case 652:
							type.spriteId = 116;
							type.anInt1980 = -1;// -1;//-1;// -1;// 1459;
							break;

						case 653:
							type.name = "Crypt";
							type.anInt1959 = 16777215;
							type.anInt1980 = -1;// -1;//-1;// -1;// 1129;
							break;

						case 654:
							type.name = "Ferox<br>Enclave";
							type.anInt1959 = 16777215;
							type.anInt1968 = 1;
							type.anInt1980 = -1;// -1;//-1;// -1;// 1129;
							break;

						case 655:
							type.name = "Citharede<br>Abbey";
							type.anInt1959 = 16777215;
							type.anInt1980 = -1;// -1;//-1;// -1;// 1129;
							break;

						case 656:
							type.name = "South Falador<br>Farm";
							type.anInt1959 = 16777215;
							type.anInt1980 = -1;// -1;//-1;// -1;// 1129;
							break;

						case 657:
							type.name = "Sourhog Cave";
							type.anInt1959 = 16777215;
							type.anInt1980 = -1;// -1;//-1;// -1;// 1129;
							break;

						case 658:
							type.name = "Isle of Souls";
							type.anInt1959 = 16750623;
							type.anInt1968 = 2;
							type.anInt1980 = -1;// -1;//-1;// -1;// 1129;
							break;

						case 659:
							type.name = "Soul Wars<br>Arena";
							type.anInt1959 = 16777215;
							type.anInt1968 = 1;
							type.anInt1980 = -1;// -1;//-1;// -1;// 1129;
							break;

						case 660:
							type.name = "Soul Wars<br>Lobby";
							type.anInt1959 = 16777215;
							type.anInt1968 = 1;
							type.anInt1980 = -1;// -1;//-1;// -1;// 1129;
							break;

						case 661:
							type.name = "Soul<br>Obelisk";
							type.anInt1959 = 16777215;
							type.anInt1980 = -1;// -1;//-1;// -1;// 1129;
							break;

						case 662:
							type.name = "Western<br>Graveyard";
							type.anInt1959 = 16777215;
							type.anInt1980 = -1;// -1;//-1;// -1;// 1129;
							break;

						case 663:
							type.name = "Eastern<br>Graveyard";
							type.anInt1959 = 16777215;
							type.anInt1980 = -1;// -1;//-1;// -1;// 1129;
							break;

						case 664:
							type.name = "Blue<br>Base";
							type.anInt1959 = 16777215;
							type.anInt1980 = -1;// -1;//-1;// -1;// 1129;
							break;

						case 665:
							type.name = "Red<br>Base";
							type.anInt1959 = 16777215;
							type.anInt1980 = -1;// -1;//-1;// -1;// 1129;
							break;

						case 666:
							type.name = "Avatar of<br>Creation";
							type.anInt1959 = 16777215;
							type.anInt1980 = -1;// -1;//-1;// -1;// 1129;
							break;

						case 667:
							type.name = "Avatar of<br>Destruction";
							type.anInt1959 = 16777215;
							type.anInt1980 = -1;// -1;//-1;// -1;// 1129;
							break;

						case 668:
							type.name = "Iron<br>dragons";
							type.anInt1959 = 16777215;
							type.anInt1980 = -1;// -1;//-1;// -1;// 1129;
							break;

						case 669:
							type.name = "Desert Island";
							type.anInt1959 = 16750623;
							type.anInt1968 = 2;
							type.anInt1980 = -1;// -1;//-1;// -1;// 1129;
							break;

						case 670:
							type.name = "Wild Varrock";
							type.anInt1959 = 16750623;
							type.anInt1968 = 2;
							type.anInt1980 = -1;// -1;//-1;// -1;// 1129;
							break;

						case 671:
							type.name = "Hut";
							type.anInt1959 = 16777215;
							type.anInt1980 = -1;// -1;//-1;// -1;// 1129;
							break;

						case 672:
							type.name = "Stone Circle";
							type.anInt1959 = 16777215;
							type.anInt1980 = -1;// -1;//-1;// -1;// 1129;
							break;

						case 673:
							type.name = "Court of Chaos";
							type.anInt1959 = 16777215;
							type.anInt1980 = -1;// -1;//-1;// -1;// 1129;
							break;

						case 674:
							type.name = "Old Manor";
							type.anInt1959 = 16777215;
							type.anInt1980 = -1;// -1;//-1;// -1;// 1129;
							break;

						case 675:
							type.name = "Town Center";
							type.anInt1959 = 16777215;
							type.anInt1980 = -1;// -1;//-1;// -1;// 1129;
							break;

						case 676:
							type.name = "Blank Tower";
							type.anInt1959 = 16777215;
							type.anInt1980 = -1;// -1;//-1;// -1;// 1129;
							break;

						case 677:
							type.name = "Trading Post";
							type.anInt1959 = 16777215;
							type.anInt1980 = -1;// -1;//-1;// -1;// 1129;
							break;

						case 678:
							type.name = "Dark Warrior's<br>Palace";
							type.anInt1959 = 16777215;
							type.anInt1968 = 1;
							type.anInt1980 = -1;// -1;//-1;// -1;// 1129;
							break;

						case 679:
							type.name = "Pillars of Sacrifice";
							type.anInt1959 = 16777215;
							type.anInt1980 = -1;// -1;//-1;// -1;// 1129;
							break;

						case 680:
							type.spriteId = 117;
							type.anInt1980 = -1;// -1;//-1;// -1;// 1499;
							break;

						case 681:
							type.name = "Ruins of<br>Unkah";
							type.anInt1959 = 16777215;
							type.anInt1968 = 1;
							type.anInt1980 = -1;// -1;//-1;// -1;// 1129;
							break;

						case 682:
							type.name = "Sea Spirit<br>Dock";
							type.anInt1959 = 16777215;
							type.anInt1980 = -1;// -1;//-1;// -1;// 1129;
							break;

						case 683:
							type.name = "Tempoross<br>Cove";
							type.anInt1959 = 16777215;
							type.anInt1968 = 1;
							type.anInt1980 = -1;// -1;//-1;// -1;// 1129;
							break;

						case 684:
							type.name = "Camdozaal<br>Vault";
							type.anInt1959 = 16777215;
							type.anInt1968 = 1;
							type.anInt1980 = -1;// -1;//-1;// -1;// 1129;
							break;

						case 685:
							type.name = "Ramarno";
							type.anInt1959 = 16777215;
							type.anInt1980 = -1;// -1;//-1;// -1;// 1129;
							break;

						case 686:
							type.name = "Offering<br>Altar";
							type.anInt1959 = 16777215;
							type.anInt1968 = 1;
							type.anInt1980 = -1;// -1;//-1;// -1;// 1129;
							break;

						case 687:
							type.name = "Golem<br>Workshop";
							type.anInt1959 = 16777215;
							type.anInt1968 = 1;
							type.anInt1980 = -1;// -1;//-1;// -1;// 1129;
							break;

						case 688:
							type.name = "Camdozaal<br>Mines";
							type.anInt1959 = 16777215;
							type.anInt1968 = 1;
							type.anInt1980 = -1;// -1;//-1;// -1;// 1129;
							break;

						case 689:
							type.name = "Clan Hub";
							type.anInt1959 = 16777215;
							type.anInt1980 = -1;// -1;//-1;// -1;// 1129;
							break;

						case 690:
							type.spriteId = 118;
							type.anInt1980 = -1;// -1;//-1;// -1;// 1551;
							break;
						case 691:
							type.name = "Shayzien<br>Encampment";
							type.anInt1959 = 16777215;
							type.anInt1968 = 1;
							type.anInt1980 = -1;// -1;//-1;// -1;
							break;

						case 692:
							type.name = "Giant<br>Pit";
							type.anInt1959 = 16777215;
							type.anInt1980 = -1;// -1;//-1;// -1;
							break;

						case 693:
							type.name = "Ruins<br>of Morra";
							type.anInt1959 = 16777215;
							type.anInt1980 = -1;// -1;//-1;// -1;
							break;

						case 694:
							type.name = "Shayzia<br>Ruin";
							type.anInt1959 = 16777215;
							type.anInt1980 = -1;// -1;//-1;// -1;
							break;

						case 695:
							type.name = "Northern<br>Tundras";
							type.anInt1959 = 16777215;
							type.anInt1968 = 1;
							type.anInt1980 = -1;// -1;//-1;// -1;
							break;

						case 696:
							type.name = "River<br>of Souls";
							type.anInt1959 = 16777215;
							type.anInt1980 = -1;// -1;//-1;// -1;
							break;
						case 697:
							type.spriteId = 119;
							type.anInt1980 = -1;// -1;//-1;// 1579;
							break;

						case 698:
							type.name = "Sire";
							type.anInt1959 = 16777215;
							type.anInt1980 = -1;// -1;//-1;// 1129;
							break;

						case 699:
							type.name = "Here be<br>spiders";
							type.anInt1959 = 16777215;
							type.anInt1980 = -1;// -1;//-1;// 1129;
							break;

						case 700:
							type.spriteId = 6;
							type.anInt1980 = -1;// -1;//-1;// 1056;
							break;

						case 701:
							type.spriteId = 6;
							type.anInt1980 = -1;// -1;//-1;// 1056;
							break;

						case 702:
							type.spriteId = 6;
							type.anInt1980 = -1;// -1;//-1;// 1056;
							break;

						case 703:
							type.spriteId = 6;
							type.anInt1980 = -1;// -1;//-1;// 1056;
							break;

						case 704:
							type.spriteId = 6;
							type.anInt1980 = -1;// -1;//-1;// 1056;
							break;

						case 705:
							type.spriteId = 6;
							type.anInt1980 = -1;// -1;//-1;// 1056;
							break;

						case 706:
							type.spriteId = 6;
							type.anInt1980 = -1;// -1;//-1;// 1056;
							break;

						case 707:
							type.spriteId = 6;
							type.anInt1980 = -1;// -1;//-1;// 1056;
							break;

						case 708:
							type.spriteId = 6;
							type.anInt1980 = -1;// -1;//-1;// 1056;
							break;

						case 709:
							type.spriteId = 6;
							type.anInt1980 = -1;// -1;//-1;// 1056;
							break;

						case 710:
							type.spriteId = 6;
							type.anInt1980 = -1;// -1;//-1;// 1056;
							break;

						case 711:
							type.spriteId = 6;
							type.anInt1980 = -1;// -1;//-1;// 1056;
							break;

						case 712:
							type.spriteId = 6;
							type.anInt1980 = -1;// -1;//-1;// 1056;
							break;

						case 713:
							type.spriteId = 6;
							type.anInt1980 = -1;// -1;//-1;// 1056;
							break;

						case 714:
							type.spriteId = 6;
							type.anInt1980 = -1;// -1;//-1;// 1056;
							break;

						case 715:
							type.spriteId = 6;
							type.anInt1980 = -1;// -1;//-1;// 1056;
							break;

						case 716:
							type.spriteId = 6;
							type.anInt1980 = -1;// -1;//-1;// 1056;
							break;

						case 717:
							type.spriteId = 6;
							type.anInt1980 = -1;// -1;//-1;// 1056;
							break;

						case 718:
							type.spriteId = 6;
							type.anInt1980 = -1;// -1;//-1;// 1056;
							break;

						case 719:
							type.spriteId = 6;
							type.anInt1980 = -1;// -1;//-1;// 1056;
							break;

						case 720:
							type.spriteId = 6;
							type.anInt1980 = -1;// -1;//-1;// 1056;
							break;

						case 721:
							type.spriteId = 6;
							type.anInt1980 = -1;// -1;//-1;// 1056;
							break;

						case 722:
							type.spriteId = 6;
							type.anInt1980 = -1;// -1;//-1;// 1056;
							break;

						case 723:
							type.spriteId = 6;
							type.anInt1980 = -1;// -1;//-1;// 1056;
							break;

						case 724:
							type.spriteId = 6;
							type.anInt1980 = -1;// -1;//-1;// 1056;
							break;

						case 725:
							type.spriteId = 6;
							type.anInt1980 = -1;// -1;//-1;// 1056;
							break;

						case 726:
							type.spriteId = 6;
							type.anInt1980 = -1;// -1;//-1;// 1056;
							break;

						case 727:
							type.spriteId = 6;
							type.anInt1980 = -1;// -1;//-1;// 1056;
							break;

						case 728:
							type.spriteId = 6;
							type.anInt1980 = -1;// -1;//-1;// 1056;
							break;

						case 729:
							type.spriteId = 6;
							type.anInt1980 = -1;// -1;//-1;// 1056;
							break;

						case 730:
							type.spriteId = 6;
							type.anInt1980 = -1;// -1;//-1;// 1056;
							break;

						case 731:
							type.spriteId = 6;
							type.anInt1980 = -1;// -1;//-1;// 1056;
							break;

						case 732:
							type.spriteId = 6;
							type.anInt1980 = -1;// -1;//-1;// 1056;
							break;

						case 733:
							type.spriteId = 6;
							type.anInt1980 = -1;// -1;//-1;// 1056;
							break;

						case 734:
							type.spriteId = 6;
							type.anInt1980 = -1;// -1;//-1;// 1056;
							break;

						case 735:
							type.spriteId = 6;
							type.anInt1980 = -1;// -1;//-1;// 1056;
							break;

						case 736:
							type.spriteId = 6;
							type.anInt1980 = -1;// -1;//-1;// 1056;
							break;

						case 737:
							type.spriteId = 6;
							type.anInt1980 = -1;// -1;//-1;// 1056;
							break;

						case 738:
							type.spriteId = 6;
							type.anInt1980 = -1;// -1;//-1;// 1056;
							break;

						case 739:
							type.spriteId = 6;
							type.anInt1980 = -1;// -1;//-1;// 1056;
							break;

						case 740:
							type.spriteId = 6;
							type.anInt1980 = -1;// -1;//-1;// 1056;
							break;

						case 741:
							type.spriteId = 6;
							type.anInt1980 = -1;// -1;//-1;// 1056;
							break;

						case 742:
							type.spriteId = 6;
							type.anInt1980 = -1;// -1;//-1;// 1056;
							break;

						case 743:
							type.spriteId = 6;
							type.anInt1980 = -1;// -1;//-1;// 1056;
							break;

						case 744:
							type.spriteId = 6;
							type.anInt1980 = -1;// -1;//-1;// 1056;
							break;

						case 745:
							type.spriteId = 6;
							type.anInt1980 = -1;// -1;//-1;// 1056;
							break;

						case 746:
							type.spriteId = 6;
							type.anInt1980 = -1;// -1;//-1;// 1056;
							break;

						case 747:
							type.spriteId = 6;
							type.anInt1980 = -1;// -1;//-1;// 1056;
							break;

						case 748:
							type.spriteId = 6;
							type.anInt1980 = -1;// -1;//-1;// 1056;
							break;

						case 749:
							type.spriteId = 6;
							type.anInt1980 = -1;// -1;//-1;// 1056;
							break;

						case 750:
							type.spriteId = 6;
							type.anInt1980 = -1;// -1;//-1;// 1056;
							break;

						case 751:
							type.spriteId = 6;
							type.anInt1980 = -1;// -1;//-1;// 1056;
							break;

						case 752:
							type.spriteId = 6;
							type.anInt1980 = -1;// -1;//-1;// 1056;
							break;

						case 753:
							type.spriteId = 6;
							type.anInt1980 = -1;// -1;//-1;// 1056;
							break;

						case 754:
							type.spriteId = 6;
							type.anInt1980 = -1;// -1;//-1;// 1056;
							break;

						case 755:
							type.spriteId = 6;
							type.anInt1980 = -1;// -1;//-1;// 1056;
							break;

						case 756:
							type.spriteId = 6;
							type.anInt1980 = -1;// -1;//-1;// 1056;
							break;

						case 757:
							type.spriteId = 6;
							type.anInt1980 = -1;// -1;//-1;// 1056;
							break;

						case 758:
							type.spriteId = 6;
							type.anInt1980 = -1;// -1;//-1;// 1056;
							break;

						case 759:
							type.spriteId = 6;
							type.anInt1980 = -1;// -1;//-1;// 1056;
							break;

						case 760:
							type.spriteId = 6;
							type.anInt1980 = -1;// -1;//-1;// 1056;
							break;

						case 761:
							type.spriteId = 6;
							type.anInt1980 = -1;// -1;//-1;// 1056;
							break;

						case 762:
							type.spriteId = 6;
							type.anInt1980 = -1;// -1;//-1;// 1056;
							break;

						case 763:
							type.spriteId = 6;
							type.anInt1980 = -1;// -1;//-1;// 1056;
							break;

						case 764:
							type.spriteId = 6;
							type.anInt1980 = -1;// -1;//-1;// 1056;
							break;

						case 765:
							type.spriteId = 6;
							type.anInt1980 = -1;// -1;//-1;// 1056;
							break;

						case 766:
							type.spriteId = 6;
							type.anInt1980 = -1;// -1;//-1;// 1056;
							break;

						case 767:
							type.spriteId = 6;
							type.anInt1980 = -1;// -1;//-1;// 1056;
							break;

						case 768:
							type.spriteId = 6;
							type.anInt1980 = -1;// -1;//-1;// 1056;
							break;

						case 769:
							type.spriteId = 6;
							type.anInt1980 = -1;// -1;//-1;// 1056;
							break;

						case 770:
							type.spriteId = 6;
							type.anInt1980 = -1;// -1;//-1;// 1056;
							break;

						case 771:
							type.spriteId = 6;
							type.anInt1980 = -1;// -1;//-1;// 1056;
							break;

						case 772:
							type.spriteId = 6;
							type.anInt1980 = -1;// -1;//-1;// 1056;
							break;

						case 773:
							type.spriteId = 6;
							type.anInt1980 = -1;// -1;//-1;// 1056;
							break;

						case 774:
							type.spriteId = 6;
							type.anInt1980 = -1;// -1;//-1;// 1056;
							break;

						case 775:
							type.spriteId = 6;
							type.anInt1980 = -1;// -1;//-1;// 1056;
							break;

						case 776:
							type.spriteId = 6;
							type.anInt1980 = -1;// -1;//-1;// 1056;
							break;

						case 777:
							type.spriteId = 6;
							type.anInt1980 = -1;// -1;//-1;// 1056;
							break;

						case 778:
							type.spriteId = 6;
							type.anInt1980 = -1;// -1;//-1;// 1056;
							break;

						case 779:
							type.spriteId = 6;
							type.anInt1980 = -1;// -1;//-1;// 1056;
							break;

						case 780:
							type.spriteId = 6;
							type.anInt1980 = -1;// -1;//-1;// 1056;
							break;

						case 781:
							type.spriteId = 6;
							type.anInt1980 = -1;// -1;//-1;// 1056;
							break;

						case 782:
							type.spriteId = 6;
							type.anInt1980 = -1;// -1;//-1;// 1056;
							break;

						case 783:
							type.spriteId = 6;
							type.anInt1980 = -1;// -1;//-1;// 1056;
							break;

						case 784:
							type.spriteId = 6;
							type.anInt1980 = -1;// -1;//-1;// 1056;
							break;

						case 785:
							type.spriteId = 6;
							type.anInt1980 = -1;// -1;//-1;// 1056;
							break;

						case 786:
							type.spriteId = 6;
							type.anInt1980 = -1;// -1;//-1;// 1056;
							break;

						case 787:
							type.spriteId = 6;
							type.anInt1980 = -1;// -1;//-1;// 1056;
							break;

						case 788:
							type.spriteId = 6;
							type.anInt1980 = -1;// -1;//-1;// 1056;
							break;

						case 789:
							type.spriteId = 6;
							type.anInt1980 = -1;// -1;//-1;// 1056;
							break;

						case 790:
							type.spriteId = 6;
							type.anInt1980 = -1;// -1;//-1;// 1056;
							break;

						case 791:
							type.spriteId = 6;
							type.anInt1980 = -1;// -1;//-1;// 1056;
							break;

						case 792:
							type.spriteId = 6;
							type.anInt1980 = -1;// -1;//-1;// 1056;
							break;

						case 793:
							type.spriteId = 6;
							type.anInt1980 = -1;// -1;//-1;// 1056;
							break;

						case 794:
							type.spriteId = 6;
							type.anInt1980 = -1;// -1;//-1;// 1056;
							break;

						case 795:
							type.spriteId = 6;
							type.anInt1980 = -1;// -1;//-1;// 1056;
							break;

						case 796:
							type.spriteId = 6;
							type.anInt1980 = -1;// -1;//-1;// 1056;
							break;

						case 797:
							type.spriteId = 6;
							type.anInt1980 = -1;// -1;//-1;// 1056;
							break;

						case 798:
							type.spriteId = 6;
							type.anInt1980 = -1;// -1;//-1;// 1056;
							break;

						case 799:
							type.spriteId = 6;
							type.anInt1980 = -1;// -1;//-1;// 1056;
							break;

						case 800:
							type.spriteId = 6;
							type.anInt1980 = -1;// -1;//-1;// 1056;
							break;

						case 801:
							type.spriteId = 6;
							type.anInt1980 = -1;// -1;//-1;// 1056;
							break;

						case 802:
							type.spriteId = 6;
							type.anInt1980 = -1;// -1;//-1;// 1056;
							break;

						case 803:
							type.spriteId = 6;
							type.anInt1980 = -1;// -1;//-1;// 1056;
							break;

						case 804:
							type.spriteId = 6;
							type.anInt1980 = -1;// -1;//-1;// 1056;
							break;

						case 805:
							type.spriteId = 6;
							type.anInt1980 = -1;// -1;//-1;// 1056;
							break;

						case 806:
							type.spriteId = 6;
							type.anInt1980 = -1;// -1;//-1;// 1056;
							break;

						case 807:
							type.spriteId = 6;
							type.anInt1980 = -1;// -1;//-1;// 1056;
							break;

						case 808:
							type.spriteId = 6;
							type.anInt1980 = -1;// -1;//-1;// 1056;
							break;

						case 809:
							type.spriteId = 6;
							type.anInt1980 = -1;// -1;//-1;// 1056;
							break;

						case 810:
							type.spriteId = 6;
							type.anInt1980 = -1;// -1;//-1;// 1056;
							break;

						case 811:
							type.spriteId = 6;
							type.anInt1980 = -1;// -1;//-1;// 1056;
							break;

						case 812:
							type.spriteId = 6;
							type.anInt1980 = -1;// -1;//-1;// 1056;
							break;

						case 813:
							type.spriteId = 6;
							type.anInt1980 = -1;// -1;//-1;// 1056;
							break;

						case 814:
							type.spriteId = 6;
							type.anInt1980 = -1;// -1;//-1;// 1056;
							break;

						case 815:
							type.spriteId = 6;
							type.anInt1980 = -1;// -1;//-1;// 1056;
							break;

						case 816:
							type.spriteId = 6;
							type.anInt1980 = -1;// -1;//-1;// 1056;
							break;

						case 817:
							type.spriteId = 6;
							type.anInt1980 = -1;// -1;//-1;// 1056;
							break;

						case 818:
							type.spriteId = 6;
							type.anInt1980 = -1;// -1;//-1;// 1056;
							break;

						case 819:
							type.spriteId = 6;
							type.anInt1980 = -1;// -1;//-1;// 1056;
							break;

						case 820:
							type.spriteId = 6;
							type.anInt1980 = -1;// -1;//-1;// 1056;
							break;

						case 821:
							type.spriteId = 6;
							type.anInt1980 = -1;// -1;//-1;// 1056;
							break;

						case 822:
							type.spriteId = 6;
							type.anInt1980 = -1;// -1;//-1;// 1056;
							break;

						case 823:
							type.spriteId = 6;
							type.anInt1980 = -1;// -1;//-1;// 1056;
							break;

						case 824:
							type.spriteId = 6;
							type.anInt1980 = -1;// -1;//-1;// 1056;
							break;

						case 825:
							type.spriteId = 6;
							type.anInt1980 = -1;// -1;//-1;// 1056;
							break;

						case 826:
							type.spriteId = 6;
							type.anInt1980 = -1;// -1;//-1;// 1056;
							break;

						case 827:
							type.spriteId = 6;
							type.anInt1980 = -1;// -1;//-1;// 1056;
							break;

						case 828:
							type.spriteId = 6;
							type.anInt1980 = -1;// -1;//-1;// 1056;
							break;

						case 829:
							type.spriteId = 6;
							type.anInt1980 = -1;// -1;//-1;// 1056;
							break;

						case 830:
							type.spriteId = 6;
							type.anInt1980 = -1;// -1;//-1;// 1056;
							break;

						case 831:
							type.spriteId = 6;
							type.anInt1980 = -1;// -1;//-1;// 1056;
							break;

						case 832:
							type.spriteId = 6;
							type.anInt1980 = -1;// -1;//-1;// 1056;
							break;

						case 833:
							type.spriteId = 6;
							type.anInt1980 = -1;// -1;//-1;// 1056;
							break;

						case 834:
							type.spriteId = 6;
							type.anInt1980 = -1;// -1;//-1;// 1056;
							break;

						case 835:
							type.spriteId = 6;
							type.anInt1980 = -1;// -1;//-1;// 1056;
							break;

						case 836:
							type.spriteId = 6;
							type.anInt1980 = -1;// -1;//-1;// 1056;
							break;

						case 837:
							type.spriteId = 6;
							type.anInt1980 = -1;// -1;//-1;// 1056;
							break;

						case 838:
							type.spriteId = 6;
							type.anInt1980 = -1;// -1;//-1;// 1056;
							break;

						case 839:
							type.spriteId = 6;
							type.anInt1980 = -1;// -1;//-1;// 1056;
							break;

						case 840:
							type.spriteId = 6;
							type.anInt1980 = -1;// -1;//-1;// 1056;
							break;

						case 841:
							type.spriteId = 6;
							type.anInt1980 = -1;// -1;//-1;// 1056;
							break;

						case 842:
							type.spriteId = 6;
							type.anInt1980 = -1;// -1;//-1;// 1056;
							break;

						case 843:
							type.spriteId = 6;
							type.anInt1980 = -1;// -1;//-1;// 1056;
							break;

						case 844:
							type.spriteId = 6;
							type.anInt1980 = -1;// -1;//-1;// 1056;
							break;

						case 845:
							type.spriteId = 6;
							type.anInt1980 = -1;// -1;//-1;// 1056;
							break;

						case 846:
							type.spriteId = 6;
							type.anInt1980 = -1;// -1;//-1;// 1056;
							break;

						case 847:
							type.spriteId = 6;
							type.anInt1980 = -1;// -1;//-1;// 1056;
							break;

						case 848:
							type.spriteId = 6;
							type.anInt1980 = -1;// -1;//-1;// 1056;
							break;

						case 849:
							type.spriteId = 6;
							type.anInt1980 = -1;// -1;//-1;// 1056;
							break;

						case 850:
							type.spriteId = 6;
							type.anInt1980 = -1;// -1;//-1;// 1056;
							break;

						case 851:
							type.name = "Ancient<br>Prison";
							type.anInt1959 = 16777215;
							type.anInt1968 = 1;
							type.anInt1980 = -1;// 1129;
							break;

						case 852:
							type.name = "Greater<br>nechryael";
							type.anInt1959 = 16777215;
							type.anInt1980 = -1;// 1129;
							break;

						case 853:
							type.spriteId = 6;
							type.anInt1980 = -1;// -1;
							break;

						case 854:
							type.spriteId = 6;
							type.anInt1980 = -1;// -1;
							break;

						case 855:
							type.name = "Ruins of<br>Ullek";
							type.anInt1959 = 16777215;
							type.anInt1968 = 1;
							type.anInt1980 = -1;
							break;

						case 856:
							type.name = "Necropolis";
							type.anInt1959 = 16777215;
							type.anInt1980 = -1;
							break;

						case 857:
							type.spriteId = 6;
							type.anInt1980 = -1;
							break;

						case 858:
							type.spriteId = 120;
							type.anInt1980 = -1;
							break;

						case 859:
							type.spriteId = 121;
							type.anInt1980 = -1;
							break;

						case 860:
							type.spriteId = 122;
							type.anInt1980 = -1;
							break;

						case 861:
							type.name = "Kovac";
							type.anInt1959 = 16777215;
							type.anInt1980 = -1;
							break;

						case 862:
							type.name = "Giants' Plateau";
							type.anInt1959 = 16777215;
							type.anInt1968 = 1;
							type.anInt1980 = -1;
							break;

						case 863:
							type.spriteId = 6;
							type.anInt1980 = -1;
							break;
						case 864:
							type.name = "PvP<br>Arena";
							type.anInt1959 = 16777215;
							type.anInt1968 = 1;
							type.anInt1980 = -1;
							break;
						case 865:
							type.name = "Kalphite<br>Lair";
							type.anInt1959 = 16777215;
							type.anInt1968 = 1;
							type.anInt1980 = -1;
							break;

						case 866:
							type.name = "Kalphite<br>Cave";
							type.anInt1959 = 16777215;
							type.anInt1968 = 1;
							type.anInt1980 = -1;
							break;

						case 867:
							type.name = "Trouble<br>Brewing";
							type.anInt1959 = 16777215;
							type.anInt1980 = -1;
							break;

						case 868:
							type.name = "Piscatoris";
							type.anInt1959 = 16777215;
							type.anInt1968 = 1;
							type.anInt1980 = -1;
							break;

						case 869:
							type.name = "Troll<br>Country";
							type.anInt1959 = 16750623;
							type.anInt1968 = 2;
							type.anInt1980 = -1;//
							break;

						case 870:
							type.name = "Uzer<br>Oasis";
							type.anInt1959 = 16777215;
							type.anInt1980 = -1;
							break;

						case 871:
							type.spriteId = 6;
							type.anInt1980 = -1;
							break;
						case 872:
							type.name = "Skeleton";
							type.anInt1959 = 16777215;
							type.anInt1980 = -1;//= 1129;
							break;

						case 873:
							type.name = "Callisto's Den";
							type.anInt1959 = 16777215;
							type.anInt1980 = -1;//= 1129;
							break;

						case 874:
							type.name = "Silk Chasm";
							type.anInt1959 = 16777215;
							type.anInt1980 = -1;//= 1129;
							break;

						case 875:
							type.name = "Vet'ion's Rest";
							type.anInt1959 = 16777215;
							type.anInt1980 = -1;//= 1129;
							break;

						case 876:
							type.name = "Hunter's End";
							type.anInt1959 = 16777215;
							type.anInt1980 = -1;//= 1129;
							break;

						case 877:
							type.name = "Web Chasm";
							type.anInt1959 = 16777215;
							type.anInt1980 = -1;//= 1129;
							break;

						case 878:
							type.name = "Skeletal Tomb";
							type.anInt1959 = 16777215;
							type.anInt1980 = -1;//= 1129;
							break;

						case 879:
							type.name = "Ancient<br>Pyramid<br>(Jaldraocht)";
							type.anInt1959 = 16777215;
							type.anInt1980 = -1;//= 1129;
							break;

						case 880:
							type.name = "Pyramid Plunder<br>(Jalsavrah)";
							type.anInt1959 = 16777215;
							type.anInt1980 = -1;//= 1129;
							break;

						case 881:
							type.name = "Jaltevas";
							type.anInt1959 = 16777215;
							type.anInt1980 = -1;//= 1129;
							break;

						case 882:
							type.name = "Daimon's<br>Crater";
							type.anInt1959 = 16777215;
							type.anInt1968 = 1;
							type.anInt1980 = -1;//= 1129;
							break;

						case 883:
							type.name = "Frozen Temple";
							type.anInt1959 = 16777215;
							type.anInt1980 = -1;//= 1129;
							break;

						case 884:
							type.name = "Huntsman's<br>Wood";
							type.anInt1959 = 16777215;
							type.anInt1980 = -1;//= 1129;
							break;

						case 885:
							type.name = "Tower of<br>Bounty";
							type.anInt1959 = 16777215;
							type.anInt1980 = -1;//= 1129;
							break;

						case 886:
							type.name = "Bounty Hunter";
							type.anInt1959 = 16777215;
							type.anInt1980 = -1;//= 1129;
							break;

						case 887:
							type.spriteId = 125;
							type.anInt1980 = -1;//= 1786;
							break;

						case 888:
							type.name = "The<br>Stranglewood";
							type.anInt1959 = 16777215;
							type.anInt1968 = 1;
							type.anInt1980 = -1;
							break;

						case 889:
							type.name = "Temple";
							type.anInt1959 = 16777215;
							type.anInt1980 = -1;
							break;

						case 890:
							type.name = "Ritual<br>Site";
							type.anInt1959 = 16777215;
							type.anInt1980 = -1;
							break;

						case 891:
							type.name = "Main<br>Block";
							type.anInt1959 = 16777215;
							type.anInt1980 = -1;
							break;

						case 892:
							type.name = "Administration";
							type.anInt1959 = 16777215;
							type.anInt1980 = -1;
							break;

						case 893:
							type.name = "Refugee<br>Camp";
							type.anInt1959 = 16777215;
							type.anInt1980 = -1;
							break;

						case 894:
							type.name = "Science<br>District";
							type.anInt1959 = 16777215;
							type.anInt1980 = -1;
							break;

						case 895:
							type.name = "Residential<br>District";
							type.anInt1959 = 16777215;
							type.anInt1980 = -1;
							break;

						case 896:
							type.name = "Sunken<br>Cathedral";
							type.anInt1959 = 16777215;
							type.anInt1980 = -1;
							break;
					}
				}
				count++;
			}
		} catch (IOException e) {
			logger.log(Level.SEVERE, "Error Loading AreaType(s)!", e);
		}
		logger.info("Loaded " + count + " AreaType(s)!");
	}

	@Override
	public AreaType list(int id) {
		Preconditions.checkArgument(id >= 0, "ID can't be negative!");
		Preconditions.checkArgument(id < areas.length, "ID can't be greater than the max area id!");
		return areas[id];
	}

	@Override
	public void print() {
	      
	  File dir = new File(Constants.TYPE_PATH);

	  if (!dir.exists()) {
	        dir.mkdir();
	  }
	      
		File file = new File(Constants.TYPE_PATH, "areas.txt");
		try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
			Arrays.stream(areas).filter(Objects::nonNull).forEach((AreaType t) -> {
				TypePrinter.print(t, writer);
			});
			writer.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public int size() {
		return areas.length;
	}

}
