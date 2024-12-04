package net.openrs.cache.tools.definition_dumpers.npcs.drops;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
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

class Drop {  
    int id;  
    int minAmount;
    int maxAmount;
    String dropType;
    double chance;
    Drop(int id,int minAmount,int maxAmount,String dropType,double chance){  
        this.id = id;
        this.minAmount = minAmount;
        this.maxAmount = maxAmount;
        this.dropType = dropType;
        this.chance = chance;
    }  
} 

public class NpcDropDumper {
	
	public static final String EXPORT = "E:/dump/export/NpcDropsEXILE.json";
	
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
		for (ItemDefinition definition : list) {
			if(itemDefinitions.get(definition.getName()) == null) {
				itemDefinitions.put(definition.getName(), definition);
			}
		}
		System.out.println("ItemDefinitions imported.");
		
		JsonWriter json = new JsonWriter(new FileWriter(EXPORT));
//		json.setIndent("  ");
		json.beginArray();
		
		try (BufferedReader br = new BufferedReader(new FileReader(LISTFILE))) {
		    String line;
		    String name = "";
		    int level = 0;
		    int npcId = 0;
		    ArrayList<Drop> npcDrops =new ArrayList<Drop>();
		    while ((line = br.readLine()) != null) {
			    if(line.startsWith("case ")) {
			    	if(name != "" && level > 0) {
			    		String url = "http://2007.runescape.wikia.com/wiki/" + name.replaceAll(" ", "_");
				    	org.jsoup.nodes.Document document = null;
				    	try {
					        document = Jsoup.connect(url).timeout(60000).get();		
					        if(document != null) {
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
					        					rarityLevel = "SPECIAL";
					        					rarity.child(0).children().remove();
					        					String chance = rarity.child(0).text();
					        					rarityChance = round((Double.parseDouble(chance.substring(1).split("/")[0]) * 100) / Double.parseDouble(chance.substring(0, chance.length() - 1).split("/")[1].replace(",", "")), 5);
					        				}
					        				
					        				System.out.println(id + " - " + dropName + ": " + minAmount + " to " + maxAmount + " | Chance (percentage): " + rarityChance);
					        				npcDrops.add(new Drop(id, minAmount, maxAmount, rarityLevel, rarityChance));
					        			}
						        	}
						        }
						        if(!npcDrops.isEmpty()) {
							        json.beginObject();
							    		json.name("npcList");
							    		json.beginArray();
							    			json.value(npcId);
							    		json.endArray();
							    		json.name("rareDropTableAccess");
							    		json.value(true);
							    		json.name("rareDropTableChance");
							    		json.value(0.0);
							    		json.name("dropChances");
							    		json.beginObject();
								    		json.name("COMMON");
								    		json.value(100.0);
								    		json.name("UNCOMMON");
								    		json.value(20.0);
								    		json.name("RARE");
								    		json.value(5.0);
								    		json.name("VERY_RARE");
								    		json.value(1.0);
							    		json.endObject();
							    		json.name("drops");
							    		json.beginArray();
								    		for(Drop drop : npcDrops) {
								    			json.beginObject();
								    			json.name("item");
									    		json.value(drop.id);
									    		json.name("minimumAmount");
									    		json.value(drop.minAmount);
									    		json.name("maximumAmount");
									    		json.value(drop.maxAmount);
									    		json.name("dropType");
									    		json.value(drop.dropType);
									    		json.name("chance");
									    		json.value(drop.chance);
								    			json.endObject();
								    		}
						    			json.endArray();
						    		json.endObject();
						        }
					        }
				        } catch (HttpStatusException e) {
//				        	System.out.println("Couldn't fetch page for: " + name);
				        }
			    	}
			    	name = "";
			    	level = 0;
			    	npcDrops.clear();
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
		json.endArray();	
		json.flush();
		json.close();
	}
}
