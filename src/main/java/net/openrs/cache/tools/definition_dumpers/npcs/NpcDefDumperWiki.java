package net.openrs.cache.tools.definition_dumpers.npcs;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import org.jsoup.HttpStatusException;
import org.jsoup.Jsoup;
import org.jsoup.select.Elements;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.google.gson.stream.JsonWriter;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;


public class NpcDefDumperWiki {
	
	static class Npc {  
	    int id = -1;  
	    String name = "Null";
	    String examine = "";
	    int combat = 0;
	    int size = 1;
	    boolean attackable = false;
	    boolean aggressive = false;
	    boolean retreats = true;
	    boolean poisonous = false;
	    int respawn = 20;
	    int maxHit = 0;
	    int hitpoints = 0;
	    int attackSpeed = 0;
	    int attackBonus = 0;
	    int defenceMelee = 0;
	    int defenceRange = 0;
	    int defenceMage = 0;
	} 
	
	public static final String EXPORT = "E:/dump/export/NpcDefinitionsTomiik.json";
	
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
	
	public static double round(double value, int places) {
	    if (places < 0) throw new IllegalArgumentException();

	    long factor = (long) Math.pow(10, places);
	    value = value * factor;
	    long tmp = Math.round(value);
	    return (double) tmp / factor;
	}

	public static void main(String[] args) throws Exception {
		XStream xStream = new XStream(new DomDriver());
		
		JsonWriter json = new JsonWriter(new FileWriter(EXPORT));
		json.setIndent("  ");
		json.beginArray();
		
		try (BufferedReader br = new BufferedReader(new FileReader(LISTFILE))) {
		    String line;
		    Npc npc = new Npc();
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
			    } else if(line.contains("type.combatLevel")) {
			    	line = line.replace("type.combatLevel = ", "");
			    	line = line.replace(";", "");
			    	npc.combat = Integer.parseInt(line.trim());	
			    } else if(line.contains("type.options") && line.contains("Attack")) {
			    	npc.attackable = true;
			    } else if(line.contains("type.tileSpacesOccupied")) {
			    	line = line.replace("type.tileSpacesOccupied = ", "");
			    	line = line.replace(";", "");
			    	npc.size = Integer.parseInt(line.trim());	
			    } else if(line.contains("break;")) {
			    	String url = "http://2007.runescape.wikia.com/wiki/" + npc.name.replaceAll(" ", "_");
			    	org.jsoup.nodes.Document document = null;
			    	try {
				        document = Jsoup.connect(url).timeout(60000).get();		
				        if(document != null) {
				        	Elements list = document.select(".wikitable.infobox > tbody > tr");
				        	boolean examineNext = false;
				        	for (org.jsoup.nodes.Element infoBox : list) {
				        		if(infoBox.text().contains("Examine")) {
				        			examineNext = true;
				        		} else if(examineNext) {
				        			examineNext = false;
				        			npc.examine = infoBox.text().replace("�", "").replace("�", "...");
				        		}
				        	}
				        	Elements combatList = document.select(".wikitable.infobox > tbody > tr table tr");
				        	int defStats = 0;
				        	int other = 0;
				        	for (org.jsoup.nodes.Element infoBox : combatList) {
				        		if(infoBox.text().contains("Hitpoints") && !infoBox.text().contains("Unknown") && !infoBox.text().contains("?") && !infoBox.text().contains("Varies") && !infoBox.text().contains("See") && !infoBox.text().contains("None")) {
				        			npc.hitpoints = Integer.parseInt(infoBox.text().replace("Hitpoints", "").replace("~", "").replace("Dragonfire:", "").split("\\(")[0].split(",")[0].split("/")[0].split("-")[0].trim().split(" ")[0]);
				        		} else if(infoBox.text().contains("Aggressive") && infoBox.text().contains("Yes")) {
				        			npc.aggressive = true;
				        		} else if(infoBox.text().contains("Poisonous") && infoBox.text().contains("Yes")) {
				        			npc.poisonous = true;
				        		} else if(infoBox.text().contains("Max hit") && !infoBox.text().contains("Unknown") && !infoBox.text().contains("Does not attack") && !infoBox.text().contains("?") && !infoBox.text().contains("Varies") && !infoBox.text().contains("See") && !infoBox.text().contains("N/A") && !infoBox.text().contains("Cannot")) {
				        			npc.maxHit = Integer.parseInt(infoBox.text().replace("Max hit", "").replace("Melee (slash):", "").replace("Melee -", "").replace("~", "").replace("Melee:", "").replace("Melee", "").replace("Dragonfire:", "").replace("Without earmuffs:", "").replace("+", "").split("\\(")[0].split(",")[0].split("/")[0].split("-")[0].trim().split(" ")[0]);
				        		} else if(infoBox.text().contains("Defensive stats")) {
				        			defStats = 1;
				        		} else if(defStats == 1) {
				        			defStats = 2;
				        		} else if(infoBox.text().contains("Other bonuses")) {
				        			other = 1;
				        		} else if(other == 1) {
				        			other = 2;
				        		} else if(defStats == 2) {
				        			defStats = 0;
				        			if(!infoBox.text().contains("?") && !infoBox.text().contains("N/A")) {
					        			npc.defenceMelee = Integer.parseInt(infoBox.child(0).text().replace("+", "").split("\\(")[0].trim()) + Integer.parseInt(infoBox.child(1).text().replace("+", "").split("\\(")[0].trim()) + Integer.parseInt(infoBox.child(2).text().replace("+", "").split("\\(")[0].trim());
					        			npc.defenceMage = Integer.parseInt(infoBox.child(3).text().replace("+", "").split("\\(")[0].trim());
					        			npc.defenceRange = Integer.parseInt(infoBox.child(4).text().replace("+", "").split("\\(")[0].trim());
				        			}
				        		} else if(other == 2) {
				        			other = 0;
				        			if(!infoBox.text().contains("?") && !infoBox.text().contains("N/A")) {
				        				npc.attackBonus = Integer.parseInt(infoBox.child(2).text().replace("+", "").split("\\(")[0].trim());
				        			}
				        		}
				        	}
				        	Elements speeds = document.select(".wikitable.infobox > tbody > tr table tr img");
				        	for(org.jsoup.nodes.Element speed : speeds) {
				        		if(speed.attr("alt").contains("Monster attack speed")) {
				        			npc.attackSpeed = Integer.parseInt(speed.attr("alt").replace("Monster attack speed", "").trim());
				        		}
				        	}
				        }
			        } catch (HttpStatusException e) {
//			        	System.out.println("Couldn't fetch page for: " + name);
			        }
			    	System.out.println(npc.id);
			    	json.beginObject();
			    	json.name("id");
			    	json.value(npc.id);
			    	json.name("name");
			    	json.value(npc.name);
			    	json.name("examine");
			    	json.value(npc.examine);
			    	json.name("combat");
			    	json.value(npc.combat);
			    	json.name("size");
			    	json.value(npc.size);
			    	json.name("attackable");
			    	json.value(npc.attackable);
			    	json.name("aggressive");
			    	json.value(npc.aggressive);
			    	json.name("retreats");
			    	json.value(npc.retreats);
			    	json.name("poisonous");
			    	json.value(npc.poisonous);
			    	json.name("respawn");
			    	json.value(npc.respawn);
			    	json.name("maxHit");
			    	json.value(npc.maxHit);
			    	json.name("hitpoints");
			    	json.value(npc.hitpoints);
			    	json.name("attackSpeed");
			    	json.value(npc.attackSpeed);
			    	json.name("attackAnim");
			    	json.value(0);
			    	json.name("defenceAnim");
			    	json.value(0);
			    	json.name("deathAnim");
			    	json.value(0);
			    	json.name("attackBonus");
			    	json.value(npc.attackBonus);
			    	json.name("defenceMelee");
			    	json.value(npc.defenceMelee);
			    	json.name("defenceRange");
			    	json.value(npc.defenceRange);
			    	json.name("defenceMage");
			    	json.value(npc.defenceMage);
			    	json.endObject();
			    }
			}
		}
		json.endArray();	
		json.flush();
		json.close();
	}
}
