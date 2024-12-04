package net.openrs.cache.tools.definition_dumpers.npcs;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.google.gson.stream.JsonWriter;

public class NpcDefDumperJSON {
	
	public static final String EXPORT = "E:/dump/export/NpcDefinitions.json";
	
	public static final String LISTFILE = "./repository/types/npcs.txt";
	
	static String readFile(String path, Charset encoding) 
	  throws IOException 
	{
	  byte[] encoded = Files.readAllBytes(Paths.get(path));
	  return new String(encoded, encoding);
	}
	
	static void addAttr(Document doc, Element el, String key, String value) {
		Element attr = doc.createElement(key);
		attr.appendChild(doc.createTextNode(value));
		el.appendChild(attr);
	}

	public static void main(String[] args) throws Exception {
		
		JsonWriter json = new JsonWriter(new FileWriter(EXPORT));
		json.setIndent("  ");
		json.beginArray();

		int id = -1;
		String name = "null";
		int walkAnimation = 65535;
		int standAnimation = 65535;
		int turn180Animation = 65535;
		int turn90CWAnimation = 65535;
		int turn90CCWAnimation = 65535;
		int level = -1;
		int size = 1;
		boolean attackable = false;
		
		try (BufferedReader br = new BufferedReader(new FileReader(LISTFILE))) {
		    String line;
		    while ((line = br.readLine()) != null) {
			    if(line.startsWith("case ")) {
			    	if(id != -1) {
			    		json.beginObject();
				    	json.name("id");   
				    	json.value(id);
				    	json.name("name");
				    	json.value(name);
				    	json.name("combatLevel");
				    	json.value(level);
				    	json.name("size");
				    	json.value(size);
				    	json.name("standAnimation");
				    	json.value(standAnimation);
				    	json.name("walkAimation");
				    	json.value(walkAnimation);
				    	json.name("turn180Animation");
				    	json.value(turn180Animation);
				    	json.name("turn90CWAnimation");
				    	json.value(turn90CWAnimation);
				    	json.name("turn90CCWAnimation");
				    	json.value(turn90CCWAnimation);
				    	json.name("attackAnimation");
				    	json.value(-1);
				    	json.name("blockAimation");
				    	json.value(-1);
				    	json.name("deathAimation");
				    	json.value(-1);
				    	json.endObject();
			    	}
			    	name = "null";
					walkAnimation = 65535;
					standAnimation = 65535;
					turn180Animation = 65535;
					turn90CWAnimation = 65535;
					turn90CCWAnimation = 65535;
					level = -1;
					size = 1;
					attackable = false;
					line = line.replace("case ", "");
			    	line = line.replace(":", "");
			    	id = Integer.parseInt(line);
			    } else if(line.contains("type.name")) {
			    	line = line.replace("type.name = \"", "");
			    	line = line.replace("\";", "");
			    	if(line.contains("<col"))
			    		line = line.substring(13, line.length() - 6);
			    	name = line.trim();	
			    } else if(line.contains("type.stanceAnimation")) {
			    	line = line.replace("type.stanceAnimation = ", "");
			    	line = line.replace(";", "");
			    	standAnimation = Integer.parseInt(line.trim());	
			    } else if(line.contains("type.walkAnimation")) {
			    	line = line.replace("type.walkAnimation = ", "");
			    	line = line.replace(";", "");
			    	walkAnimation = Integer.parseInt(line.trim());	
			    } else if(line.contains("type.rotate180Animation")) {
			    	line = line.replace("type.rotate180Animation = ", "");
			    	line = line.replace(";", "");
			    	turn180Animation = Integer.parseInt(line.trim());	
			    } else if(line.contains("type.rotate90LeftAnimation")) {
			    	line = line.replace("type.rotate90LeftAnimation = ", "");
			    	line = line.replace(";", "");
			    	turn90CWAnimation = Integer.parseInt(line.trim());	
			    } else if(line.contains("type.rotate90RightAnimation")) {
			    	line = line.replace("type.rotate90RightAnimation = ", "");
			    	line = line.replace(";", "");
			    	turn90CCWAnimation = Integer.parseInt(line.trim());	
			    } else if(line.contains("type.combatLevel")) {
			    	line = line.replace("type.combatLevel = ", "");
			    	line = line.replace(";", "");
			    	level = Integer.parseInt(line.trim());	
			    } else if(line.contains("type.tileSpacesOccupied")) {
			    	line = line.replace("type.tileSpacesOccupied = ", "");
			    	line = line.replace(";", "");
			    	size = Integer.parseInt(line.trim());	
			    } else if(line.contains("type.options") && line.contains("Attack")) {
			    	attackable = true;	
			    }
			}
		}
		
		json.beginObject();
    	json.name("id");   
    	json.value(id);
    	json.name("name");
    	json.value(name);
    	json.name("combatLevel");
    	json.value(level);
    	json.name("size");
    	json.value(size);
    	json.name("standAnimation");
    	json.value(standAnimation);
    	json.name("walkAimation");
    	json.value(walkAnimation);
    	json.name("turn180Animation");
    	json.value(turn180Animation);
    	json.name("turn90CWAnimation");
    	json.value(turn90CWAnimation);
    	json.name("turn90CCWAnimation");
    	json.value(turn90CCWAnimation);
    	json.name("attackAnimation");
    	json.value(-1);
    	json.name("blockAimation");
    	json.value(-1);
    	json.name("deathAimation");
    	json.value(-1);
    	json.endObject();
		
    	json.endArray();	
		json.flush();
		json.close();
	}
}
