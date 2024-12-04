package net.openrs.cache.tools.definition_dumpers.npcs;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
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


public class NpcDefDumperKen {
	
	static class Npc {  
	    int id = -1;  
	    String name = "Null";
	    String description = "";
	    int combatLevel = 0;
	    int size = 1;
	    boolean attackable = false;
	    boolean aggressive = false;
	    boolean poisonous = false;
	    boolean undead = false;
	    int hitpoints = 0;
	    int attackSpeed = 0;
	    boolean poisonImmunity = false;
	    boolean venomImmunity = false;
	    int[] skills = new int[5];
	    int[] bonusses = new int[13];
		int slayerLevel;
		int maxHit = 0;
	} 
	
	public static final String EXPORT = "E:/dump/export/NpcDefinitionsKen.json";
	
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
			    	npc.combatLevel = Integer.parseInt(line.trim());	
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
				        document = openDocument(url);	
				        if(document != null) {
				        	BufferedWriter htmlWriter = 
				            	     new BufferedWriter(new OutputStreamWriter(
				            	    		 new FileOutputStream("C:/Users/Stan/Desktop/RS/wiki/" + 
				            	     url.replace("http://2007.runescape.wikia.com/wiki/", "").replace("/", "").replace("?", "") + ".html"), "UTF-8"));
				            htmlWriter.write(document.toString());
				            htmlWriter.flush();
				            htmlWriter.close();
//				    		Elements sprite = document.select(".wikitable.infobox tr > td > a > img");
//		        			if(sprite.first() != null) {
//		        				try(InputStream in = new URL(sprite.first().attr("src")).openStream()){
//					    		    Files.copy(in, Paths.get("C:/Users/Stan/Desktop/NPC sprites/" + npc.id + ".png"));
//					    		} catch(IOException e) {
//					    			System.out.println(e.getMessage());
//					    		}
//		        			}
				        	Elements list = document.select(".wikitable.infobox > tbody > tr");
				        	boolean examineNext = false;
				        	for (org.jsoup.nodes.Element infoBox : list) {
				        		if(infoBox.text().contains("Examine")) {
				        			examineNext = true;
				        		} else if(examineNext) {
				        			examineNext = false;
				        			npc.description = infoBox.text().replace("�", "").replace("�", "...");
				        		}
				        	}
				        	Elements combatList = document.select(".wikitable.infobox > tbody > tr table tr");
				        	int combatStats = 0;
				        	int aggrStats = 0;
				        	int defStats = 0;
				        	int other = 0;
				        	for (org.jsoup.nodes.Element infoBox : combatList) {
				        		if(infoBox.text().contains("Hitpoints") && !infoBox.text().contains("Unknown") && !infoBox.text().contains("?") && !infoBox.text().contains("Varies") && !infoBox.text().contains("See") && !infoBox.text().contains("None")) {
				        			npc.hitpoints = Integer.parseInt(infoBox.text().replace("Hitpoints", "").replace("~", "").replace("Dragonfire:", "").split("\\(")[0].split(",")[0].split("/")[0].split("-")[0].trim().split(" ")[0].replaceAll("[^\\d.]", ""));
				        		} else if(infoBox.text().contains("Aggressive") && infoBox.text().contains("Yes")) {
				        			npc.aggressive = true;
				        		} else if(infoBox.text().contains("Weakness") && infoBox.text().toLowerCase().contains("undead")) {
				        			npc.undead = true;
				        		} else if(infoBox.text().contains("Poisonous") && infoBox.text().contains("Yes")) {
				        			npc.poisonous = true;
				        		} else if(infoBox.text().contains("Max hit") && !infoBox.text().contains("Unknown") && !infoBox.text().contains("Does not attack") && !infoBox.text().contains("?") && !infoBox.text().contains("Varies") && !infoBox.text().contains("See") && !infoBox.text().contains("None")
				        				&& infoBox.text().replace("Max hit", "").replace("~", "").replace("Dragonfire:", "").split("\\(")[0].split(",")[0].split("/")[0].split("-")[0].trim().split(" ")[0].replaceAll("[^\\d.]", "").matches(".*\\d+.*")) {
				        			npc.maxHit = Integer.parseInt(infoBox.text().replace("Max hit", "").replace("~", "").replace("Dragonfire:", "").split("\\(")[0].split(",")[0].split("/")[0].split("-")[0].trim().split(" ")[0].replaceAll("[^\\d.]", ""));
				        		} else if(infoBox.text().contains("Slayer level") && !infoBox.text().toLowerCase().contains("none")
				        				&& !infoBox.text().toLowerCase().contains("assigned")
				        				&& !infoBox.text().toLowerCase().contains("unknown")
				        				&& !infoBox.text().toLowerCase().contains("n/a")) {
				        			npc.slayerLevel = Integer.parseInt(infoBox.text().replace("Slayer level", "").trim());
				        		} else if(infoBox.text().contains("Combat stats")) {
				        			combatStats = 1;
				        		} else if(combatStats == 1) {
				        			combatStats = 2;
				        		}else if(infoBox.text().contains("Aggressive stats")) {
				        			aggrStats = 1;
				        		} else if(aggrStats == 1) {
				        			aggrStats = 2;
				        		} else if(infoBox.text().contains("Defensive stats")) {
				        			defStats = 1;
				        		} else if(defStats == 1) {
				        			defStats = 2;
				        		} else if(infoBox.text().contains("Other bonuses")) {
				        			other = 1;
				        		} else if(other == 1) {
				        			other = 2;
				        		} else if(combatStats == 2) {
				        			combatStats = 0;
				        			if(!infoBox.text().contains("?") && !infoBox.text().contains("N/A")) {
				        				for(int i = 0;i<5;i++) {
				        					npc.skills[i] = Integer.parseInt(infoBox.child(i).text().replace("+", "").split("\\(")[0].trim());
				        				}
				        			}
				        		} else if(aggrStats == 2) {
				        			aggrStats = 0;
				        			if(!infoBox.text().contains("?") && !infoBox.text().contains("N/A")) {
				        				for(int i = 0;i<5;i++) {
				        					npc.bonusses[i] = Integer.parseInt(infoBox.child(i).text().replace("+", "").split("\\(")[0].trim());
				        				}
				        			}
				        		} else if(defStats == 2) {
				        			defStats = 0;
				        			if(!infoBox.text().contains("?") && !infoBox.text().contains("N/A")) {
				        				for(int i = 0;i<5;i++) {
				        					npc.bonusses[i + 5] = Integer.parseInt(infoBox.child(i).text().replace("+", "").split("\\(")[0].trim());
				        				}
				        			}
				        		} else if(other == 2) {
				        			other = 0;
				        			for(int i = 0;i<5;i++) {
			        					if(!infoBox.child(i).text().contains("?") && !infoBox.child(i).text().contains("N/A")) {
				        					if(i<3) {
				        						npc.bonusses[i + 10] = Integer.parseInt(infoBox.child(i).text().replace("+", "").split("\\(")[0].trim());
				        					} else if(i==3 && infoBox.child(i).text().toLowerCase().contains("immune") && !infoBox.child(i).text().toLowerCase().contains("not")) {
				        						npc.poisonImmunity = true;
				        					} else if (infoBox.child(i).text().toLowerCase().contains("immune") && !infoBox.child(i).text().toLowerCase().contains("not")) {
				        						npc.venomImmunity = true;				        						
				        					}
				        				}
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
			    	json.name("npcId");
			    	json.value(npc.id);
			    	json.name("name");
			    	json.value(npc.name);
			    	if(npc.attackable) {
			    		json.name("attackable");
				    	json.value(npc.attackable);
				    	json.name("retreats");
				    	json.value(false);
				    	json.name("aggressive");
				    	json.value(npc.aggressive);
				    	json.name("poisonous");
				    	json.value(npc.poisonous);
				    	json.name("combat-follow");
				    	json.value(15);
				    	json.name("respawn");
				    	json.value(130);
				    	json.name("hitpoints");
				    	json.value(npc.hitpoints);
				    	json.name("combat-level");
				    	json.value(npc.combatLevel);
				    	json.name("attack-level");
				    	json.value(npc.skills[0]);
				    	json.name("strength-level");
				    	json.value(npc.skills[1]);
				    	json.name("ranged-level");
				    	json.value(npc.skills[3]);
				    	json.name("magic-level");
				    	json.value(npc.skills[4]);
				    	json.name("defence-melee");
				    	json.value(npc.bonusses[5] + npc.bonusses[6] + npc.bonusses[7]);
				    	json.name("defence-ranged");
				    	json.value(npc.bonusses[9]);
				    	json.name("defence-magic");
				    	json.value(npc.bonusses[8]);
				    	json.name("max-hit");
				    	json.value(npc.maxHit);
				    	json.name("attack-speed");
				    	json.value(npc.attackSpeed);
				    	json.name("defence-anim");
				    	json.value(-1);
				    	json.name("death-anim");
				    	json.value(-1);
				    	json.name("attack-anim");
				    	json.value(-1);
			    	}
			    	json.name("examine");
			    	json.value(npc.description);
			    	json.name("walk-radius");
			    	json.value(npc.attackable ? 20 : 2);
			    	json.endObject();
			    }
			}
		}
		json.endArray();	
		json.flush();
		json.close();
	}
	
	private static org.jsoup.nodes.Document openDocument(String url) throws IOException {
		File input = new File("C:/Users/Stan/Desktop/RS/wiki/" + 
       	     url.replace("http://2007.runescape.wikia.com/wiki/", "").replace("/", "").replace("???", "") + ".html");
		if (input.exists()) {
			return Jsoup.parse(input, "UTF-8", url);
		}
		return Jsoup.connect(url).timeout(60000).get();
	}
}
