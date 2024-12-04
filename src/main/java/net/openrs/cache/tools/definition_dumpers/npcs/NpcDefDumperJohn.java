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
import java.util.ArrayList;
import java.util.List;
import org.jsoup.HttpStatusException;
import org.jsoup.Jsoup;
import org.jsoup.select.Elements;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.google.gson.stream.JsonWriter;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;


public class NpcDefDumperJohn {
	
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
		int stanceAnimation = -1;
		int walkAnimation = -1;
		String attackStyle = "";
	} 
	
	static class Animation {
		int id = -1;
		int lastFrameLength = -1;
		int secondLastFrameLength = -1;
		int forcedPriority = -1;
	}
	
	public static final String EXPORT = "E:/dump/export/NpcDefinitionsJohn.json";
	
	public static final String LISTFILE = "./repository/types/npcs.txt";
	public static final String ANIMFILE = "./repository/types/sequences.txt";
	
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
	    
	    Animation anim = new Animation();
	    
	    List<Animation> anims = new ArrayList<>();
	    
	    System.out.println("Reading sequences txt file...");		
		try (BufferedReader br = new BufferedReader(new FileReader(ANIMFILE))) {
		    String line;
		    while ((line = br.readLine()) != null) {
			    if(line.startsWith("case ")) {
			    	anim = new Animation();
			    	line = line.replace("case ", "");
			    	line = line.replace(":", "");
			    	anim.id = Integer.parseInt(line);
			    } else if(line.contains("type.frameLengths")) {
			    	line = line.replace("type.frameLengths = new int[] {", "");
			    	line = line.replace("};", "");
			    	String[] frameLengths = line.split(",");
			    	if(frameLengths.length >= 2) {
				    	anim.lastFrameLength = Integer.parseInt(frameLengths[frameLengths.length - 1].trim());
				    	anim.secondLastFrameLength = Integer.parseInt(frameLengths[frameLengths.length - 2].trim());
			    	}
			    } else if(line.contains("type.forcedPriority")) {
			    	line = line.replace("type.forcedPriority = ", "");
			    	line = line.replace(";", "");
			    	anim.forcedPriority = Integer.parseInt(line.trim());
			    } else if(line.contains("break")) {
			    	anims.add(anim);
			    }
			}
		}
		System.out.println("Sequences imported.");
	    
	    List<Npc> npcs = new ArrayList<>();
		
		
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
			    } else if(line.contains("type.stanceAnimation")) {
			    	line = line.replace("type.stanceAnimation = ", "");
			    	line = line.replace(";", "");
			    	npc.stanceAnimation = Integer.parseInt(line.trim());	
			    } else if(line.contains("type.walkAnimation")) {
			    	line = line.replace("type.walkAnimation = ", "");
			    	line = line.replace(";", "");
			    	npc.walkAnimation = Integer.parseInt(line.trim());
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
				            Elements combatList = document.select(".wikitable.infobox > tbody > tr table tr");
				        	int attackStyle = 0;
				        	String maxHit = "0";
				        	for (org.jsoup.nodes.Element infoBox : combatList) {
				        		if(infoBox.text().contains("Max hit") && !infoBox.text().contains("Unknown") && !infoBox.text().contains("N/A") && !infoBox.text().contains("Does not attack") && !infoBox.text().contains("?") && !infoBox.text().contains("Varies") && !infoBox.text().contains("See") && !infoBox.text().contains("None")) {
				        			maxHit = infoBox.text();
				        		} else if(infoBox.text().contains("Attack Styles")) {
				        			attackStyle = 1;
				        		} else if(attackStyle == 1) {
			        				if(infoBox.text().toLowerCase().contains("crush")) {
			        					npc.attackStyle = "CRUSH";
			        				} else if(infoBox.text().toLowerCase().contains("stab")) {
			        					npc.attackStyle = "STAB";
			        				} else {
			        					npc.attackStyle = "SLASH";
			        				}
			        				npc.maxHit = Integer.parseInt(maxHit.replace("Max hit", "").replace("�", "").replace("Melee -", "").replace("Melee:", "").replace("Melee (slash):", "").replace("Melee", "").replace("~", "").replace("Without earmuffs:", "").replace("Dragonfire:", "").split("\\(")[0].split(",")[0].split("/")[0].split("-")[0].trim().split(" ")[0].replaceAll("[^\\d.]", ""));
			        				Elements speeds = document.select(".wikitable.infobox > tbody > tr table tr img");
						        	for(org.jsoup.nodes.Element speed : speeds) {
						        		if(speed.attr("alt").contains("Monster attack speed") && !speed.attr("alt").contains("random")) {
						        			npc.attackSpeed = Integer.parseInt(speed.attr("alt").replace("Monster attack speed", "").trim());
						        		}
						        	}
				        			break;
				        		}
				        	}
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
				        	Elements combatList2 = document.select(".wikitable.infobox > tbody > tr table tr");
				        	int combatStats = 0;
				        	int aggrStats = 0;
				        	int defStats = 0;
				        	int other = 0;
				        	for (org.jsoup.nodes.Element infoBox : combatList2) {
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
				        		if(speed.attr("alt").contains("Monster attack speed") && !speed.attr("alt").contains("random")) {
				        			npc.attackSpeed = Integer.parseInt(speed.attr("alt").replace("Monster attack speed", "").trim());
				        		}
				        	}
				        }
			        } catch (HttpStatusException e) {
//			        	System.out.println("Couldn't fetch page for: " + name);
			        }
			    	
			    	int[] animations = new int[20];
					int currentAnim = 2;
					int startAnim = npc.stanceAnimation > npc.walkAnimation ? npc.walkAnimation : npc.stanceAnimation;
					animations[0] = npc.stanceAnimation;
					animations[1] = npc.walkAnimation;
					int attackAnim = -1;
					int deathAnim = -1;
					int blockAnim = -1;
			    	
			    	if (npc.attackable) {			    	
				    	
						for(int i = 1; i < 11; i++) {
							anim = anims.get(startAnim + i);
							boolean found = false;
							for(int j = 0; j < 10; j++) {
								if(anim.id == animations[j]) {
									found = true;
								}
							}
							if(!found && anim.lastFrameLength != -1 && anim.secondLastFrameLength != -1) {
								if(anim.forcedPriority == 10 || anim.lastFrameLength > anim.secondLastFrameLength * 5) {
									animations[currentAnim] = anim.id;
									currentAnim++;
									if(deathAnim == -1) deathAnim = anim.id;
								} else if((anim.forcedPriority == 6 || anim.forcedPriority == 8) && attackAnim == -1) {
									animations[currentAnim] = anim.id;
									currentAnim++;
									attackAnim = anim.id;
								}
							}
						}
						
						if(attackAnim != -1 && deathAnim != -1) {
							for(int i = 1; i < 11; i++) {
								anim = anims.get(startAnim + i);
								boolean found = false;
								for(int j = 0; j < 10; j++) {
									if(anim.id == animations[j]) {
										found = true;
									}
								}
								if(!found) {
									if(blockAnim == -1 || (anims.get(deathAnim).forcedPriority == -1 && (anim.forcedPriority == 6 || anim.forcedPriority == 8))) {
										blockAnim = anim.id;
										if(anim.forcedPriority == 8 && anims.get(attackAnim).forcedPriority == 6) {
											blockAnim = attackAnim;
											attackAnim = anim.id;
										}
									}
								}
							}
							if(blockAnim == 0) {
								blockAnim = npc.stanceAnimation;
							}
						}
			    	}
			    	
			    	System.out.println(npc.id);
			    	json.beginObject();
			    	json.name("id");
			    	json.value(npc.id);
			    	json.name("name");
			    	json.value(npc.name);
			    	json.name("examine");
			    	json.value(npc.description);
			    	json.name("size");
			    	json.value(npc.size);
			    	json.name("walkRadius");
			    	json.value(0);
			    	json.name("attackable");
			    	json.value(npc.attackable);
			    	json.name("retreats");
			    	json.value(true);
			    	json.name("aggressive");
			    	json.value(npc.aggressive);
			    	json.name("poisonous");
			    	json.value(npc.poisonous);
			    	json.name("respawn");
			    	json.value(25);
			    	json.name("maxHit");
			    	json.value(npc.maxHit);
			    	json.name("hitpoints");
			    	json.value(npc.hitpoints);
			    	json.name("attackSpeed");
			    	json.value(npc.attackSpeed);
			    	json.name("attackAnim");
			    	json.value(attackAnim);
			    	json.name("defenceAnim");
			    	json.value(blockAnim);
			    	json.name("deathAnim");
			    	json.value(deathAnim);
			    	json.name("combatLevel");
			    	json.value(npc.combatLevel);
			    	json.name("stats");
			    	json.beginArray();
			    	for(int i = 0; i < npc.skills.length; i++) {
				    	json.value(npc.skills[i]);
			    	}
			    	for(int i = 0; i < npc.bonusses.length; i++) {
				    	json.value(npc.bonusses[i]);
			    	}
			    	json.endArray();
//			    	json.name("attackLevel");
//			    	json.value(npc.skills[0]);
//			    	json.name("strengthLevel");
//			    	json.value(npc.skills[1]);
//			    	json.name("rangedLevel");
//			    	json.value(npc.skills[4]);
//			    	json.name("magicLevel");
//			    	json.value(npc.skills[3]);
//			    	json.name("defenceMelee");
//			    	json.value((int) Math.round((npc.bonusses[0] + npc.bonusses[1] + npc.bonusses[2]) / 3));
//			    	json.name("defenceRange");
//			    	json.value(npc.bonusses[4]);
//			    	json.name("defenceMagic");
//			    	json.value(npc.bonusses[3]);
			    	json.name("slayerLevel");
			    	json.value(npc.slayerLevel);
			    	json.name("combatFollowDistance");
			    	json.value(7);
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
