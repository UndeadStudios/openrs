package net.openrs.cache.tools.definition_dumpers.npcs;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.jsoup.HttpStatusException;
import org.jsoup.Jsoup;
import org.jsoup.select.Elements;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.google.gson.stream.JsonWriter;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.converters.reflection.Sun14ReflectionProvider;
import com.thoughtworks.xstream.io.xml.DomDriver;


public class NpcDefDumperJaamakutt {
	
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
	} 
	
	public static final String EXPORT = "E:/dump/export/NpcBonussesJaamakutt.json";
	
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
				        document = Jsoup.connect(url).timeout(60000).get();		
				        if(document != null) {
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
			    	json.name("id");
			    	json.value(npc.id);
			    	String[] skills = new String[] {"attack", "strength", "defence", "ranged", "mage" };
			    	String[] bonusses = new String[] { "stabAttack", "slashAttack", "crushAttack", "mageAttack", "rangeAttack", "stabDefence", "slashDefence", "crushDefence", "mageDefence", "rangeDefence" };
			    	for(int i = 0; i < npc.skills.length; i++) {
			    		json.name(skills[i]);
				    	json.value(npc.skills[i]);
			    	}
			    	for(int i = 0; i < 10; i++) {
			    		json.name(bonusses[i]);
				    	json.value(npc.bonusses[i]);
			    	}
			    	json.endObject();
			    }
			}
		}
		json.endArray();	
		json.flush();
		json.close();
	}
}
