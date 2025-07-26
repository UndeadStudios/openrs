package net.openrs.cache.tools.definition_dumpers.animations;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.google.gson.stream.JsonWriter;

public class PossibleAnimDumper {
	
	public static final String EXPORT = "E:/dump/PossibleanimsNoHumanoid227.json";
	
	public static final String LISTFILE = "E:/dump/types/sequences.txt";
	public static final String NPCFILE = "E:/dump/types/npcs.txt";

	private static boolean noHumanoidNpcs = true;
	
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
	
	static class Animation {
		int id = -1;
		int lastFrameLength = -1;
		int secondLastFrameLength = -1;
		int forcedPriority = -1;
		ArrayList<Integer> frameIds = new ArrayList<Integer>();
		int skeletalId = -1; // New field for skeletal ID
	}
	
	static class Npc {
		int id = -1;
		String name = "";
		int stanceAnimation = -1;
		int walkAnimation = -1;
		boolean attackable = false;
		int maxHit = 0;
		String attackStyle = "";
		int attackSpeed = 0;
		public int hitpoints;
		public int[] bonusses = new int[13];
		public int[] skills = new int[4];
	}

	public static void main(String[] args) throws Exception {
		
		JsonWriter json = new JsonWriter(new FileWriter(EXPORT));
		json.setIndent("  ");
		json.beginArray();
  
	    Animation anim = new Animation();
	    
	    HashMap<Integer, Animation> anims = new HashMap<Integer, Animation>();
	    	
		try (BufferedReader br = new BufferedReader(new FileReader(LISTFILE))) {
		    String line;
		    while ((line = br.readLine()) != null) {
			    if(line.startsWith("case ")) {
			    	anim = new Animation();
			    	line = line.replace("case ", "");
			    	line = line.replace(":", "");
			    	anim.id = Integer.parseInt(line);
				} else if (line.contains("type.frameLengths")) {
					line = line.replace("type.frameLengths = new int[] {", "").replace("};", "").trim();
					if (!line.isEmpty()) {
						String[] frameLengths = line.split(",");
						if (frameLengths.length >= 2) {
							anim.lastFrameLength = Integer.parseInt(frameLengths[frameLengths.length - 1].trim());
							anim.secondLastFrameLength = Integer.parseInt(frameLengths[frameLengths.length - 2].trim());
						}
					}
				} else if (line.contains("type.frameIDs")) {
					line = line.replace("type.frameIDs = new int[] {", "").replace("};", "").trim();
					if (!line.isEmpty()) {
						String[] frameIds = line.split(",");
						for (String frame : frameIds) {
							anim.frameIds.add(Integer.parseInt(frame.trim()));
						}
					}
				} else if(line.contains("type.forcedPriority") || line.contains("type.priority")) {
			    	line = line.replace("type.priority = ", "");
			    	line = line.replace("type.forcedPriority = ", "");
			    	line = line.replace(";", "");
			    	anim.forcedPriority = Integer.parseInt(line.trim());
			    } else if (line.contains("type.skeletalId")) {
				line = line.replace("type.skeletalId = ", "").replace(";", "").trim();
				anim.skeletalId = Integer.parseInt(line);
			} else if(line.contains("break")) {
			    	anims.put(anim.id, anim);
			    }
			}
		}
		
		Npc npc = new Npc();
	    
	    List<Npc> npcs = new ArrayList<>();
	    
		try (BufferedReader br = new BufferedReader(new FileReader(NPCFILE))) {
		    String line;
		    while ((line = br.readLine()) != null) {
			    if(line.startsWith("case ")) {
			    	npc = new Npc();
			    	line = line.replace("case ", "");
			    	line = line.replace(":", "");
			    	npc.id = Integer.parseInt(line);
			    } else if(line.contains("type.name")) {
			    	line = line.replace("type.name = \"", "");
			    	line = line.replace("\";", "");
			    	if(line.contains("<col"))
			    		line = line.substring(13, line.length() - 6);
			    	npc.name = line.trim();	
			    } else if(line.contains("type.stanceAnimation")) {
			    	line = line.replace("type.stanceAnimation = ", "");
			    	line = line.replace(";", "");
			    	npc.stanceAnimation = Integer.parseInt(line.trim());	
			    } else if(line.contains("type.walkAnimation")) {
			    	line = line.replace("type.walkAnimation = ", "");
			    	line = line.replace(";", "");
			    	npc.walkAnimation = Integer.parseInt(line.trim());
			    } else if(line.contains("break")) {
			    	npcs.add(npc);
			    }
			}
		}
		System.out.println("Npcs imported.");
		
		Iterator<Npc> npcIterator = npcs.iterator();
		while (npcIterator.hasNext()) {
			Npc NPC = npcIterator.next();
			ArrayList<Integer> possibleAnims = new ArrayList<Integer>();
			for (Animation an : anims.values()) {
				Iterator<Integer> iterator = an.frameIds.iterator();
				while (iterator.hasNext()) {
					int id = iterator.next();
					boolean found = false;
					if (NPC.stanceAnimation != -1)
						for (int i : anims.get(NPC.stanceAnimation).frameIds) {
							if (id >= i - 1000 && id <= i + 1000 && !possibleAnims.contains(an.id)) {
								possibleAnims.add(an.id);
								found = true;
								break;
							}
						}
					if (NPC.walkAnimation != -1)
						if (!found)
							for (int i : anims.get(NPC.walkAnimation).frameIds) {
								if (id >= i - 1000 && id <= i + 1000 && !possibleAnims.contains(an.id)) {
									possibleAnims.add(an.id);
									break;
								}
							}
				}
			}
	    	
	    	ArrayList<Integer> attackBlockAnims = new ArrayList<Integer>();
	    	ArrayList<Integer> deathAnims = new ArrayList<Integer>();
	    	ArrayList<Integer> walkAnims = new ArrayList<Integer>();
	    	ArrayList<Integer> stanceAnims = new ArrayList<Integer>();
	    	ArrayList<Integer> otherAnims = new ArrayList<Integer>();
	    	
	    	Iterator<Integer> iterator = possibleAnims.iterator();
			while (iterator.hasNext()) {
				int i = iterator.next();
				Animation a = anims.get(i);
				boolean found = false;
				for (Npc n : npcs) {
					if (n.walkAnimation == i) {
						walkAnims.add(i);
						found = true;
						break;
					} else if (n.stanceAnimation == i) {
						stanceAnims.add(i);
						found = true;
						break;
					}
				}
				if (!found) {
					if(a.forcedPriority == 10 || a.lastFrameLength > a.secondLastFrameLength * 5) {
						deathAnims.add(i);
					} else if((a.forcedPriority == 6 || a.forcedPriority == 8)) {
						attackBlockAnims.add(i);
					} else {
						otherAnims.add(i);
					}
				}
			}	
			
			json.beginObject();
			json.name("id");
	    	json.value(NPC.id);
	    	json.name("name");
	    	json.value(NPC.name);
			
			if (noHumanoidNpcs && walkAnims.contains(819)) {
		    	json.name("type");
		    	json.value("HUMANOID");
			} else {
				json.name("stanceAnims");
		    	json.beginArray();
				Iterator<Integer> stanceAnimsIterator = stanceAnims.iterator();
				while (stanceAnimsIterator.hasNext()) {
					json.value(stanceAnimsIterator.next());
				}
				json.endArray();
				json.name("walkAnims");
		    	json.beginArray();
				Iterator<Integer> walkAnimsIterator = walkAnims.iterator();
				while (walkAnimsIterator.hasNext()) {
					json.value(walkAnimsIterator.next());
				}
				json.endArray();
		    	json.name("attackBlockAnims");
		    	json.beginArray();
				Iterator<Integer> attackBlockAnimsIterator = attackBlockAnims.iterator();
				while (attackBlockAnimsIterator.hasNext()) {
					json.value(attackBlockAnimsIterator.next());
				}
				json.endArray();
				json.name("deathAnims");
		    	json.beginArray();
				Iterator<Integer> deathAnimsIterator = deathAnims.iterator();
				while (deathAnimsIterator.hasNext()) {
					json.value(deathAnimsIterator.next());
				}
				json.endArray();
				json.name("otherAnims");
		    	json.beginArray();
				Iterator<Integer> otherAnimsIterator = otherAnims.iterator();
				while (otherAnimsIterator.hasNext()) {
					json.value(otherAnimsIterator.next());
				}
				json.endArray();
			}
			json.endObject();
	    }
		json.endArray();
		json.flush();
		json.close();
	}
}