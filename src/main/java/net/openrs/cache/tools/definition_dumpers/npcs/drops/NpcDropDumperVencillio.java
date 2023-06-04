package net.openrs.cache.tools.definition_dumpers.npcs.drops;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
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

import net.openrs.cache.tools.ItemDefinition;
import net.openrs.cache.tools.definition_dumpers.Npc;

public class NpcDropDumperVencillio {
	
	public static final String EXPORT = "./repository/types/NpcDropsVencillio.xml";
	
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
		System.out.println("Reading ItemDefinitions XML file...");
		XStream xStream = new XStream(new DomDriver());
		xStream.alias("ItemDefinition", net.openrs.cache.tools.ItemDefinition.class);
		List<ItemDefinition> list = (List<ItemDefinition>) xStream.fromXML(new FileInputStream("./repository/types/ItemDefinitions.xml"));
		Map<String, ItemDefinition> itemDefinitions = new HashMap<String, ItemDefinition>();
		Map<Integer, ItemDefinition> itemDefinitionsId = new HashMap<Integer, ItemDefinition>();
		for (ItemDefinition definition : list) {
			if(itemDefinitions.get(definition.getName()) == null) {
				itemDefinitions.put(definition.getName(), definition);
			}
			itemDefinitionsId.put(definition.getId(), definition);
		}
		System.out.println("ItemDefinitions imported.");
		
//		ArrayList<DropDefinition> dropDefinitions = new ArrayList<DropDefinition>();
		
		Npc npc = new Npc();
	    
	    Npc[] npcs = new Npc[9999];
	    
	    System.out.println("Reading npcs txt file...");		
		try (BufferedReader br = new BufferedReader(new FileReader(LISTFILE))) {
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
			    } else if(line.contains("break")) {
		    		npcs[npc.id] = npc;
			    }
			}
		}
		System.out.println("Npcs imported.");
		
		DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder docBuilder = docFactory.newDocumentBuilder();

		Document doc = docBuilder.newDocument();
		Element defList = doc.createElement("list");
		doc.appendChild(defList);
		
		File file = new File("./repository/types/dropChancesVencillio.txt");
		BufferedWriter writer = new BufferedWriter(new FileWriter(file));
		
		ArrayList<Integer> ids = new ArrayList<Integer>();
		
		ArrayList<Drop> npcDropsAlways =new ArrayList<Drop>();
		ArrayList<Drop> npcDropsCommon =new ArrayList<Drop>();
		ArrayList<Drop> npcDropsUncommon =new ArrayList<Drop>();
		ArrayList<Drop> npcDropsRare =new ArrayList<Drop>();
		
		try (BufferedReader br = new BufferedReader(new FileReader(LISTFILE))) {
		    String line;
		    String name = "";
		    int level = 0;
		    int npcId = 0;
		    while ((line = br.readLine()) != null) {
			    if(line.startsWith("case ")) {
			    	if(name != "" && level > 0) {
//			    		if(npcId > 100) break;
			    		String url = "http://2007.runescape.wikia.com/wiki/" + name.replaceAll(" ", "_");
				    	org.jsoup.nodes.Document document = null;
				    	try {
					        document = Jsoup.connect(url).timeout(60000).get();		
					        if(document != null) {
					        	npcDropsAlways.clear();
					        	npcDropsCommon.clear();
					        	npcDropsUncommon.clear();
					        	npcDropsRare.clear();
					        	Elements drops = document.select(".dropstable:not(.rdtable) > tbody > tr");
					        	System.out.println(name);
					        	boolean rdt = false;
					        	if(document.toString().contains("Show/hide rare drop table")) {
					        		rdt = true;
					        	}
						        for (org.jsoup.nodes.Element drop : drops) {
						        	if(!drop.text().contains("Show/hide") && !drop.text().contains("GE market price") && !drop.text().contains("Rare drop table") && !drop.text().contains("Champion scroll") && !drop.text().contains("Champion's scroll")) {
						        		String dropName =  Jsoup.parse(drop.child(1).child(0).text()).text();
						        		dropName = dropName.substring(0, 1).toUpperCase() + dropName.substring(1);
						        		ItemDefinition item = itemDefinitions.get(dropName);
						        		boolean noted = false;
						        		if(item == null) {						        			
						        			if(dropName.contains("(")) {
						        				if(dropName.toLowerCase().contains("noted")) {
							        				noted = true;
							        				dropName = Jsoup.parse(dropName.replaceAll("\\(Noted\\)", "")).text();
							        				dropName = Jsoup.parse(dropName.replaceAll("\\(noted\\)", "")).text();
						        				}
						        				dropName = Jsoup.parse(dropName.replaceAll("\\(blue\\)", "")).text();
						        				dropName = Jsoup.parse(dropName.replaceAll("\\(red\\)", "")).text();
						        				dropName = Jsoup.parse(dropName.replaceAll("\\(elemental\\)", "")).text();
						        				dropName = Jsoup.parse(dropName.replaceAll("\\(bearded gorilla\\)", "")).text();
						        				dropName = Jsoup.parse(dropName.replaceAll("\\(gorilla\\)", "")).text();
						        				dropName = Jsoup.parse(dropName.replaceAll("\\(top\\)", "")).text();
						        				dropName = Jsoup.parse(dropName.replaceAll("\\(small zombie\\)", "")).text();
						        				dropName = Jsoup.parse(dropName.replaceAll("\\(big zombie\\)", "")).text();
						        				dropName = Jsoup.parse(dropName.replaceAll("\\(small ninja\\)", "")).text();
						        				dropName = Jsoup.parse(dropName.replaceAll("\\(bottom\\)", "")).text();
						        				dropName = Jsoup.parse(dropName.replaceAll("\\(half\\)", "")).text();
						        				dropName = Jsoup.parse(dropName.replaceAll("\\(Half\\)", "")).text();
						        				dropName = Jsoup.parse(dropName.replaceAll("\\(black\\)", "")).text();
						        				dropName = Jsoup.parse(dropName.replaceAll("\\(H.A.M.\\)", "")).text();
						        				dropName = Jsoup.parse(dropName.replaceAll("\\(item\\)", "")).text();
						        				dropName = Jsoup.parse(dropName.replaceAll("\\(unlit\\)", "")).text();
						        				dropName = Jsoup.parse(dropName.replaceAll(" \\(", "\\(")).text();
					        					dropName.trim();
					        					if(dropName.contains("Oil lantern(empty)")) {
					        						dropName = "Empty oil lantern";
					        					}
						        				item = itemDefinitions.get(dropName);
						        			} else if(dropName.contains("Bandana and eyepatch")) {
						        				dropName = "Bandana eyepatch";
						        			} else if(dropName.contains("Abyssal demon head")) {
						        				dropName = "Abyssal head";
						        			} else if(dropName.contains("Herb")) {
						        				dropName = "Grimy guam leaf";
						        			} else if(dropName.contains("Ring of dueling")) {
						        				dropName = "Ring of dueling(8)";
						        			} else if(dropName.contains("antipoison")) {
							        				dropName = "Superantipoison(4)";	
						        			} else if(dropName.contains("arrow")) {
						        				dropName = dropName.replaceAll("arrow", "arrows");
						        			} else if(dropName.contains("[")) {
						        				dropName = dropName.substring(0, dropName.length() - 3);
						        			}
						        			
						        			item = itemDefinitions.get(dropName);
						        		}
						        		if(item != null) {
					        				String quantity = Jsoup.parse(drop.child(2).text()).text().replaceAll(",", "").toLowerCase();
					        				if(quantity.contains("noted")) {
					        					noted = true;
					        					quantity = Jsoup.parse(quantity.replaceAll("\\(noted\\)", "")).text();
					        				} else if(quantity.contains("unknown")) {
					        					quantity = "1";
					        				}
					        				quantity = quantity.trim();
					        				int minAmount;
					        				int maxAmount;
					        				
					        				if(quantity.contains(";")) {
					        					if(quantity.contains("\u2013")) {
					        						minAmount = Integer.parseInt(quantity.split(";")[0].split("\u2013")[0].trim());
						        					maxAmount = Integer.parseInt(quantity.split(";")[quantity.split(";").length - 1].split("\u2013")[0].trim());
					        					} else {
						        					minAmount = Integer.parseInt(quantity.split(";")[0].trim());
						        					maxAmount = Integer.parseInt(quantity.split(";")[quantity.split(";").length - 1].trim());
					        					}
					        				} else if(quantity.contains("\u2013")) {
					        					minAmount = Integer.parseInt(quantity.split("\u2013")[0].trim());
					        					maxAmount = Integer.parseInt(quantity.split("\u2013")[1].trim());
					        				} else {
					        					minAmount = Integer.parseInt(quantity);
					        					maxAmount = Integer.parseInt(quantity);
					        				}
					        				
					        				int id = item.getId();
					        				if(noted) {
					        					id = item.getNoteId();
					        				}
					        				if(id == 617) { //Coins
					        					id = 995;
					        				}
					        				
					        				org.jsoup.nodes.Element rarity = drop.child(3);
					        				String rarityLevel = rarity.text().toLowerCase();
					        				
					        				if(rarityLevel.contains("very rare")) {
					        					rarityLevel = "VERY_RARE";
					        				} else if(rarityLevel.contains("rare")) {
					        					rarityLevel = "RARE";
					        				} else if(rarityLevel.contains("uncommon")) {
					        					rarityLevel = "UNCOMMON";
					        				} else if(rarityLevel.contains("common")) {
					        					rarityLevel = "COMMON";
					        				} else if(rarityLevel.contains("always")) {
					        					rarityLevel = "ALWAYS";
					        				} else if(rarityLevel.contains("unknown")) {
					        					rarityLevel = "UNCOMMON";
					        				} else if(rarityLevel.contains("random")) {
					        					rarityLevel = "UNCOMMON";
					        				} else if(rarityLevel.contains("varies")) {
					        					rarityLevel = "COMMON";
					        				}
					        				
					        				double rarityChance = 0.0;
					        				
					        				if(rarity.children().first() != null && rarity.children().first().text().contains("/")) {
//					        					rarityLevel = "SPECIAL";
					        					rarity.child(0).children().remove();
					        					String chance = rarity.child(0).text();
					        					rarityChance = round((Double.parseDouble(chance.substring(1).split("/")[0]) * 100) / Double.parseDouble(chance.substring(0, chance.length() - 1).split("/")[1].replace(",", "")), 5);
					        				}
					        				
//					        				System.out.println(id + " - " + dropName + ": " + minAmount + " to " + maxAmount + " | Chance (percentage): " + rarityChance);
					        				if(rarityLevel == "ALWAYS")
					        					npcDropsAlways.add(new Drop(id, minAmount, maxAmount, rarityLevel, rarityChance));
					        				else if(rarityLevel == "COMMON")
					        					npcDropsCommon.add(new Drop(id, minAmount, maxAmount, rarityLevel, rarityChance));
					        				else if(rarityLevel == "UNCOMMON")
					        					npcDropsUncommon.add(new Drop(id, minAmount, maxAmount, rarityLevel, rarityChance));
					        				else if(rarityLevel == "RARE" || rarityLevel == "VERY_RARE")
					        					npcDropsRare.add(new Drop(id, minAmount, maxAmount, rarityLevel, rarityChance));
					        			}
						        	}
						        }
						        if(!npcDropsAlways.isEmpty() || !npcDropsCommon.isEmpty() || !npcDropsUncommon.isEmpty() || !npcDropsRare.isEmpty()) {
									Element el = doc.createElement("ItemDropDefinition");
						    		defList.appendChild(el);			    		
						    		addAttr(doc, el, "id", Integer.toString(npcId));
						    		Element el2 = doc.createElement("constant");
						    		el.appendChild(el2);
						    		addAttr(doc, el2, "scrolls", "null");
						    		addAttr(doc, el2, "charms", "null");
						    		Element el3 = doc.createElement("drops");
						    		el2.appendChild(el3);
						    		for(Drop drop : npcDropsAlways) {
						    			Element elDrop = doc.createElement("itemDrop");
							    		el3.appendChild(elDrop);
							    		addAttr(doc, elDrop, "id", Integer.toString(drop.id));
							    		addAttr(doc, elDrop, "min", Integer.toString(drop.minAmount));
							    		addAttr(doc, elDrop, "max", Integer.toString(drop.maxAmount));
						    		}
						    		Element el4 = doc.createElement("common");
						    		el.appendChild(el4);
						    		addAttr(doc, el4, "scrolls", "null");
						    		addAttr(doc, el4, "charms", "null");
						    		Element el5 = doc.createElement("drops");
						    		el4.appendChild(el5);
						    		for(Drop drop : npcDropsCommon) {
						    			Element elDrop = doc.createElement("itemDrop");
							    		el5.appendChild(elDrop);
							    		addAttr(doc, elDrop, "id", Integer.toString(drop.id));
							    		addAttr(doc, elDrop, "min", Integer.toString(drop.minAmount));
							    		addAttr(doc, elDrop, "max", Integer.toString(drop.maxAmount));
						    		}
						    		Element el6 = doc.createElement("uncommon");
						    		el.appendChild(el6);
						    		addAttr(doc, el6, "scrolls", "null");
						    		addAttr(doc, el6, "charms", "null");
						    		Element el7 = doc.createElement("drops");
						    		el6.appendChild(el7);
						    		for(Drop drop : npcDropsUncommon) {
						    			Element elDrop = doc.createElement("itemDrop");
							    		el7.appendChild(elDrop);
							    		addAttr(doc, elDrop, "id", Integer.toString(drop.id));
							    		addAttr(doc, elDrop, "min", Integer.toString(drop.minAmount));
							    		addAttr(doc, elDrop, "max", Integer.toString(drop.maxAmount));
						    		}
						    		Element el8 = doc.createElement("rare");
						    		el.appendChild(el8);
						    		addAttr(doc, el8, "scrolls", "null");
						    		addAttr(doc, el8, "charms", "null");
						    		Element el9 = doc.createElement("drops");
						    		el8.appendChild(el9);
						    		for(Drop drop : npcDropsRare) {
						    			Element elDrop = doc.createElement("itemDrop");
							    		el9.appendChild(elDrop);
							    		addAttr(doc, elDrop, "id", Integer.toString(drop.id));
							    		addAttr(doc, elDrop, "min", Integer.toString(drop.minAmount));
							    		addAttr(doc, elDrop, "max", Integer.toString(drop.maxAmount));
							    		if(drop.chance != 0.0) {
							    			writer.write(drop.id + ":" + ((int) Math.round(drop.chance * 10)) + "//" + itemDefinitionsId.get(drop.id).getName());
							    			writer.newLine();
							    		}
						    		}
						    		addAttr(doc, el, "useRareTable", Boolean.toString(rdt));
						        }
					        }
				        } catch (HttpStatusException e) {
//				        	System.out.println("Couldn't fetch page for: " + name);
				        }
			    	}
			    	name = "";
			    	level = 0;
			    	line = line.replace("case ", "");
			    	line = line.replace(":", "");
			    	npcId = Integer.parseInt(line);
			    } else if(line.contains("type.name")) {
			    	line = line.replace("type.name = \"", "");
			    	line = line.replace("\";", "");
			    	if(line.contains("<col"))
			    		line = line.substring(13, line.length() - 6);
			    	name = line.trim();	
			    } else if(line.contains("type.combatLevel")) {
			    	line = line.replace("type.combatLevel = ", "");
			    	line = line.replace(";", "");
			    	level = Integer.parseInt(line.trim());	
			    }
			}
		}
		
		writer.flush();
        writer.close();
		
		TransformerFactory transformerFactory = TransformerFactory.newInstance();
		Transformer transformer = transformerFactory.newTransformer();
		DOMSource source = new DOMSource(doc);
		StreamResult result = new StreamResult(new File(EXPORT));

		transformer.setOutputProperty(OutputKeys.INDENT, "yes");
		transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");
		transformer.transform(source, result);	
	}
}
