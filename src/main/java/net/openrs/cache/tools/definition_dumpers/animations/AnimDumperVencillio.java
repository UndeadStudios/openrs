package net.openrs.cache.tools.definition_dumpers.animations;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
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

public class AnimDumperVencillio {
	
	public static final String EXPORT = "E:/dump/export/CombatDefinitionsVencillio.xml";
	
	public static final String LISTFILE = "./repository/types/sequences.txt";
	public static final String NPCFILE = "./repository/types/npcs.txt";
	
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
		
		DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder docBuilder = docFactory.newDocumentBuilder();

		Document doc = docBuilder.newDocument();
		Element defList = doc.createElement("list");
		doc.appendChild(defList);
	    
	    Animation anim = new Animation();
	    
	    List<Animation> anims = new ArrayList<>();
	    
	    System.out.println("Reading sequences txt file...");		
		try (BufferedReader br = new BufferedReader(new FileReader(LISTFILE))) {
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
		
		Npc npc = new Npc();
	    
	    List<Npc> npcs = new ArrayList<>();
	    
	    System.out.println("Reading npcs txt file...");		
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
			    } else if(line.contains("type.options") && line.contains("Attack")) {
			    	npc.attackable = true;	
			    } else if(line.contains("break") && npc.attackable && 
			    		npc.stanceAnimation != 808 && npc.walkAnimation != 819 &&
			    		npc.stanceAnimation != 842 && npc.walkAnimation != 841) {
				    	String url = "http://2007.runescape.wikia.com/wiki/" + npc.name.replaceAll(" ", "_");
				    	org.jsoup.nodes.Document document = null;
				    	try {
					        document = Jsoup.connect(url).timeout(60000).get();		
					        if(document != null) {
					        	Elements combatList = document.select(".wikitable.infobox > tbody > tr table tr");
					        	int attackStyle = 0;
					        	String maxHit = "0";
					        	int combatStats = 0;
					        	int aggrStats = 0;
					        	int defStats = 0;
					        	int other = 0;
					        	for (org.jsoup.nodes.Element infoBox : combatList) {
					        		if(infoBox.text().contains("Combat stats")) {
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
					        				for(int i = 0;i<3;i++) {
					        					npc.skills[i] = Integer.parseInt(infoBox.child(i).text().replace("+", "").split("\\(")[0].trim());
					        				}
					        			}
					        		} else if(aggrStats == 2) {
					        			aggrStats = 0;
					        			for(int i = 0;i<5;i++)
					        				if(!infoBox.child(i).text().contains("?") && !infoBox.text().contains("N/A"))
					        					npc.bonusses[i] = Integer.parseInt(infoBox.child(i).text().replace("+", "").split("\\(")[0].trim());
					        		} else if(defStats == 2) {
					        			defStats = 0;
				        				for(int i = 0;i<5;i++)
				        					if (!infoBox.child(i).text().contains("?") && !infoBox.text().contains("N/A"))
				        						npc.bonusses[i + 5] = Integer.parseInt(infoBox.child(i).text().replace("+", "").split("\\(")[0].trim());
					        		} else if(other == 2) {
					        			other = 0;
					        			for(int i = 0;i<5;i++) {
				        					if(!infoBox.child(i).text().contains("?") && !infoBox.child(i).text().contains("N/A")) {
					        					if(i<3) {
					        						npc.bonusses[i + 10] = Integer.parseInt(infoBox.child(i).text().replace("+", "").split("\\(")[0].trim());
					        					}
				        					}
					        			}
					        		} else if(infoBox.text().contains("Max hit") && !infoBox.text().contains("Unknown") && !infoBox.text().contains("?") && !infoBox.text().contains("Varies") && !infoBox.text().contains("See") && !infoBox.text().contains("None")) {
					        			maxHit = infoBox.text();
					        		} else if(infoBox.text().contains("Attack Styles")) {
					        			attackStyle = 1;
					        		} else if(attackStyle == 1) {
					        			if(!infoBox.text().toLowerCase().contains("magic") && infoBox.text().toLowerCase().contains("ranged") && !infoBox.text().toLowerCase().contains("melee")) {
					        				System.out.println(npc.id + " - " + npc.name + " is RANGED only");
					        			}
					        			if(infoBox.text().toLowerCase().contains("magic") && !infoBox.text().toLowerCase().contains("ranged") && !infoBox.text().toLowerCase().contains("melee")) {
					        				System.out.println(npc.id + " - " + npc.name + " is MAGIC only");
					        			}
					        			if(!infoBox.text().toLowerCase().contains("magic") && !infoBox.text().toLowerCase().contains("ranged") && infoBox.text().toLowerCase().contains("melee")) {
					        				if(infoBox.text().toLowerCase().contains("crush")) {
					        					npc.attackStyle = "CRUSH";
					        				} else if(infoBox.text().toLowerCase().contains("stab")) {
					        					npc.attackStyle = "STAB";
					        				} else {
					        					npc.attackStyle = "SLASH";
					        				}
					        				npc.maxHit = Integer.parseInt(maxHit.replace("Max hit", "").replace("~", "").replace("Dragonfire:", "").split("\\(")[0].split(",")[0].split("/")[0].split("-")[0].trim().split(" ")[0].replaceAll("[^\\d.]", ""));
					        				Elements speeds = document.select(".wikitable.infobox > tbody > tr table tr img");
								        	for(org.jsoup.nodes.Element speed : speeds) {
								        		if(speed.attr("alt").contains("Monster attack speed")) {
								        			npc.attackSpeed = Integer.parseInt(speed.attr("alt").replace("Monster attack speed", "").trim());
								        		}
								        	}
					        			}
					        		} else if(infoBox.text().contains("Hitpoints") && !infoBox.text().contains("Unknown") && !infoBox.text().contains("?") && !infoBox.text().contains("Varies") && !infoBox.text().contains("See") && !infoBox.text().contains("None")) {
					        			npc.hitpoints = Integer.parseInt(infoBox.text().replace("Hitpoints", "").replace("~", "").replace("Dragonfire:", "").split("\\(")[0].split(",")[0].split("/")[0].split("-")[0].trim().split(" ")[0].replaceAll("[^\\d.]", ""));
					        		}
					        	Elements speeds = document.select(".wikitable.infobox > tbody > tr table tr img");
					        	for(org.jsoup.nodes.Element speed : speeds) {
					        		if(speed.attr("alt").contains("Monster attack speed")) {
					        			npc.attackSpeed = 10 - Integer.parseInt(speed.attr("alt").replace("Monster attack speed", "").trim());
					        		}
					        	}
					        }
				        }
			        } catch (HttpStatusException e) {
//			        	System.out.println("Couldn't fetch page for: " + name);
			        }
			    	if(npc.attackStyle != "") {
			    		npcs.add(npc);
			    	}
			    }
			}
		}
		System.out.println("Npcs imported.");
		
		for(Npc NPC : npcs) {
			int[] animations = new int[20];
			int currentAnim = 2;
			int startAnim = NPC.stanceAnimation > NPC.walkAnimation ? NPC.walkAnimation : NPC.stanceAnimation;
			animations[0] = NPC.stanceAnimation;
			animations[1] = NPC.walkAnimation;
			int attackAnim = 0;
			int deathAnim = 0;
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
						if(deathAnim == 0) deathAnim = anim.id;
					} else if((anim.forcedPriority == 6 || anim.forcedPriority == 8) && attackAnim == 0) {
						animations[currentAnim] = anim.id;
						currentAnim++;
						attackAnim = anim.id;
					}
				}
			}
			int blockAnim = 0;
			if(attackAnim != 0 && deathAnim != 0) {
				for(int i = 1; i < 11; i++) {
					anim = anims.get(startAnim + i);
					boolean found = false;
					for(int j = 0; j < 10; j++) {
						if(anim.id == animations[j]) {
							found = true;
						}
					}
					if(!found) {
						if(blockAnim == 0 || (anims.get(deathAnim).forcedPriority == -1 && (anim.forcedPriority == 6 || anim.forcedPriority == 8))) {
							blockAnim = anim.id;
							if(anim.forcedPriority == 8 && anims.get(attackAnim).forcedPriority == 6) {
								blockAnim = attackAnim;
								attackAnim = anim.id;
							}
						}
					}
				}
				if(blockAnim == 0) {
					blockAnim = NPC.stanceAnimation;
				}
				defList.appendChild(doc.createComment(NPC.name));
				Element el = doc.createElement("NpcCombatDefinition");
				defList.appendChild(el);			    		
				addAttr(doc, el, "id", Integer.toString(NPC.id));
				addAttr(doc, el, "combatType", "MELEE");
				addAttr(doc, el, "respawnTime", "30");
				Element block = doc.createElement("block");
				el.appendChild(block);
				addAttr(doc, block, "id", Integer.toString(blockAnim));
				addAttr(doc, block, "delay", "0");
				Element death = doc.createElement("death");
				el.appendChild(death);
				addAttr(doc, death, "id", Integer.toString(deathAnim));
				addAttr(doc, death, "delay", "0");
				Element skills = doc.createElement("skills");
				el.appendChild(skills);
				Element skill0 = doc.createElement("skill");
				skills.appendChild(skill0);
				addAttr(doc, skill0, "id", "0");
				addAttr(doc, skill0, "level", Integer.toString(NPC.skills[0]));
				Element skill1 = doc.createElement("skill");
				skills.appendChild(skill1);
				addAttr(doc, skill1, "id", "1");
				addAttr(doc, skill1, "level", Integer.toString(NPC.skills[2]));
				Element skill2 = doc.createElement("skill");
				skills.appendChild(skill2);
				addAttr(doc, skill2, "id", "2");
				addAttr(doc, skill2, "level", Integer.toString(NPC.skills[1]));
				Element skill3 = doc.createElement("skill");
				skills.appendChild(skill3);
				addAttr(doc, skill3, "id", "3");
				addAttr(doc, skill3, "level", Integer.toString(NPC.hitpoints));
				Element bonuses = doc.createElement("bonuses");
				el.appendChild(bonuses);
				for(int i = 0;i<11;i++) {
					addAttr(doc, bonuses, "int", Integer.toString(NPC.bonusses[i]));
				}
				addAttr(doc, bonuses, "int", Integer.toString(0));
				Element meleeMain = doc.createElement("melee");
				el.appendChild(meleeMain);
				Element melee = doc.createElement("melee");
				meleeMain.appendChild(melee);
				Element attack = doc.createElement("attack");
				melee.appendChild(attack);
				addAttr(doc, attack, "hitDelay", Integer.toString(0));
				addAttr(doc, attack, "attackDelay", Integer.toString(NPC.attackSpeed));
				Element attAnim = doc.createElement("animation");
				melee.appendChild(attAnim);
				addAttr(doc, attAnim, "id", Integer.toString(attackAnim));
				addAttr(doc, attAnim, "delay", "0");
				addAttr(doc, melee, "max", Integer.toString(NPC.maxHit));
			}
		}
		TransformerFactory transformerFactory = TransformerFactory.newInstance();
		Transformer transformer = transformerFactory.newTransformer();
		DOMSource source = new DOMSource(doc);
		StreamResult result = new StreamResult(new File(EXPORT));

		transformer.setOutputProperty(OutputKeys.INDENT, "yes");
		transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");
		transformer.transform(source, result);	
	}
}