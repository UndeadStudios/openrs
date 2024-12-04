package net.openrs.cache.tools.definition_dumpers.items;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.jsoup.HttpStatusException;
import org.jsoup.Jsoup;
import org.jsoup.select.Elements;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.google.gson.stream.JsonWriter;

public class ItemDefDumperExt {
	
	public static final String EXPORT = "E:/dump/export/ItemDefinitionsJon.json";
	
	public static final String LISTFILE = "./repository/types/items.txt";
	
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
	
	static class Item {
		String name = "Null";
		int id = -1;
		boolean members = false;
		boolean forceUntradable = false;
		boolean tradable = false;
		boolean stackable = false;
		boolean equipable = false;
		boolean maleModel = false;
		int highAlch = 0;
		int lowAlch = 0;
		double weight = 0;
		String examine = "";
		int slot = -1;
		boolean twoHanded = false;
		boolean helm = false;
		boolean mask = false;
		boolean body = false;
		int speed = 0;
		int[] bonusses = new int[14];
		boolean note = false;
		boolean noteable = false;
		int unnotedId = -1;
		int notedId = -1;
	}

	public static void main(String[] args) throws Exception {
		
		JsonWriter json = new JsonWriter(new FileWriter(EXPORT));
		json.setIndent("  ");
		json.beginArray();
		
		Item item = new Item();
		
		boolean finished = false;
		
		int maxId = 21051;
	    
	    List<Item> items = new ArrayList<>();
		
		try (BufferedReader br = new BufferedReader(new FileReader(LISTFILE))) {
		    String line;
		    while (!finished) {
		    	line = br.readLine();
			    if(line.startsWith("case ")) {
			    	item = new Item();
			    	line = line.replace("case ", "");
			    	line = line.replace(":", "");
			    	item.id = Integer.parseInt(line);
			    } else if(line.contains("type.name")) {
			    	line = line.replace("type.name = \"", "");
			    	line = line.replace("\";", "");
			    	line = line.replace("&", "and");
			    	item.name = line.trim();			    			    	
			    		
			    	String url = "http://2007.runescape.wikia.com/wiki/" + item.name.replaceAll(" ", "_");
			    	org.jsoup.nodes.Document document = null;
			    	try {
				        document = Jsoup.connect(url).timeout(60000).get();		
			        } catch (HttpStatusException e) {
			        	if(item.name.contains("Burnt ")) {
			        		item.examine = "Oh dear, it's totally burnt!";
			        		item.tradable = true;
			        	} else if(item.name.contains("(kp)")) {
			        		url = "http://2007.runescape.wikia.com/wiki/" + item.name.replaceAll(" ", "_").substring(0, item.name.length() - 4);
			        		document = Jsoup.connect(url).get();
			        	} else if(item.name.contains("Ahrim") || item.name.contains("Dharok") || item.name.contains("Guthan") || item.name.contains("Karil") || item.name.contains("Torag") || item.name.contains("Verac")) {
			        		if(item.name.contains("100")) {
				        		url = "http://2007.runescape.wikia.com/wiki/" + item.name.replaceAll(" ", "_").substring(0, item.name.length() - 4);
			        		} else {
				        		url = "http://2007.runescape.wikia.com/wiki/" + item.name.replaceAll(" ", "_").substring(0, item.name.length() - 3);
				        		item.forceUntradable = true;
			        		}
			        		document = Jsoup.connect(url).get();
			        	} else if(item.name.contains("- pt")) {
			        		url = "http://2007.runescape.wikia.com/wiki/" + item.name.substring(0, item.name.length() - 6).replaceAll(" ", "_");
			        		document = Jsoup.connect(url).get();
			        	} else if(item.name.contains("(m1)") || item.name.contains("(m2)") || item.name.contains("(m3)") || item.name.contains("(m4)") || item.name.contains("(10)")) {
			        		url = "http://2007.runescape.wikia.com/wiki/" + item.name.substring(0, item.name.length() - 4).trim().replaceAll(" ", "_");
			        		document = Jsoup.connect(url).get();
			        	} else if((item.name.contains("(0)") || item.name.contains("(1)") || item.name.contains("(2)") || item.name.contains("(3)") || item.name.contains("(4)") || item.name.contains("(5)") || item.name.contains("(6)") || item.name.contains("(7)") || item.name.contains("(8)") || item.name.contains("(9)") || item.name.contains("(o)")) && !item.name.contains("Mixture")) {
			        		url = "http://2007.runescape.wikia.com/wiki/" + item.name.substring(0, item.name.length() - 3).trim().replaceAll(" ", "_");
			        		document = Jsoup.connect(url).get();
			        	} else if((item.name.endsWith("0") || item.name.endsWith("1") || item.name.endsWith("2") || item.name.endsWith("3") || item.name.endsWith("4") || item.name.endsWith("5") || item.name.endsWith("6") || item.name.endsWith("7") || item.name.endsWith("8") || item.name.endsWith("9") || item.name.endsWith("10")) && !item.name.contains("Magical balance") && !item.name.contains("Rune case") && !item.name.contains("Godsword shards")) {
			        		url = "http://2007.runescape.wikia.com/wiki/" + item.name.substring(0, item.name.length() - 2).trim().replaceAll(" ", "_");
			        		document = Jsoup.connect(url).get();
			        	} else if(item.name.endsWith("cape(t)")) {
			        		url = "http://2007.runescape.wikia.com/wiki/" + item.name.substring(0, item.name.length() - 3).trim().replaceAll(" ", "_").replaceAll("Woodcut.", "Woodcutting");
			        		document = Jsoup.connect(url).get();
			        	} else if(item.name.contains("hat and")) {
			        		url = "http://2007.runescape.wikia.com/wiki/" + item.name.replaceAll("hat and", "hat %26").replaceAll(" ", "_");
			        		document = Jsoup.connect(url).get();
			        	} else if(item.name.contains(" (uncharged)")) {
			        		url = "http://2007.runescape.wikia.com/wiki/" + item.name.substring(0, item.name.length() - 12).replaceAll(" ", "_");
			        		document = Jsoup.connect(url).get();
			        	} else if(item.name.contains("Dragon javelin(p")) {
			        		url = "http://2007.runescape.wikia.com/wiki/Dragon_javelin";
			        		document = Jsoup.connect(url).get();
			        	}
			        	
			        }
			    	
			    	if(document != null) {
//			    		Elements sprite = document.select(".wikitable.infobox tr > td > a > img");
//	        			if(sprite.first() != null) {
//	        				try(InputStream in = new URL(sprite.first().attr("src")).openStream()){
//				    		    Files.copy(in, Paths.get(".-master/RS/Item sprites/" + item.id + ".png"));
//				    		} catch(IOException e) {
//				    			System.out.println(e.getMessage());
//				    		}
//	        			}
			    		boolean examineNext = false;
				    	Elements list = document.select(".wikitable.infobox tr");
				        for (org.jsoup.nodes.Element infoBox : list) {
				        	String text = infoBox.text();
				        	if(examineNext) {
				        		item.examine = text;
				        		examineNext = false;
				        	} else if(text.contains("Yes")) {
				        		if(text.contains("Members only")) {
				        			item.members = true;
				        		} else if(text.contains("Tradeable")) {
				        			item.tradable = true;
				        		} else if(text.contains("Equipable") && !text.contains("if the player does not unwield it ") && !item.name.contains("Fishing bait")) {
				        			item.equipable = true;
				        			Elements bonusTable = document.select(".wikitable.smallpadding tr > td");
				        			int bonusIndex = 0;
				        			for(org.jsoup.nodes.Element bonusNumber : bonusTable) {
				        				if(bonusIndex < 14) {
					        				try {
				        						if(bonusNumber.text().contains("to")) {
				        							item.bonusses[bonusIndex] = Integer.parseInt(bonusNumber.text().replaceAll("%", "").replaceAll("\\(+4 trimmed\\)", "").split("to")[1].trim().replaceAll("\u00A0", ""));
				        						} else {
				        							item.bonusses[bonusIndex] = Integer.parseInt(bonusNumber.text().replaceAll("%", "").replaceAll("\\(+4 trimmed\\)", ""));
				        						}
				        						bonusIndex++;		
					        				} catch (NumberFormatException e) {}
				        				}
				        			}
				        			Elements slotElement = document.select(".wikitable.smallpadding tr th p a");
				        			if(slotElement.last() != null) {
			        					switch(slotElement.last().attr("title").replaceAll(" slot table", "")) {
			        						case "Head":
			        							item.slot = 0;
			        							if(item.name.toLowerCase().contains("helm") || item.name.toLowerCase().contains("hat") || item.name.toLowerCase().contains("mask")) {
			        								item.helm = true;
			        							} else if(item.name.toLowerCase().contains("mask")) {
			        								item.mask = true;
			        							}
			        							break;
			        						case "Cape":
			        							item.slot = 1;
			        							break;
			        						case "Neck":
			        							item.slot = 2;
			        							break;
			        						case "Ammunition":
			        						case "Ammo":
			        							item.slot = 13;
			        							break;
			        						case "Weapon":
			        							item.slot = 3;
			        							if(document.select(".wikitable.smallpadding tr th span img").last().attr("alt").contains("Monster")) {
			        								item.speed = Integer.parseInt(document.select(".wikitable.smallpadding tr th span img").last().attr("alt").replace("Monster attack speed", "").trim());
			        							}
			        							break;
			        						case "Two-handed":
			        						case "2h":
			        							item.twoHanded = true;
			        							item.slot = 3;
			        							if(document.select(".wikitable.smallpadding tr th span img").last().attr("alt").contains("Monster")) {
			        								item.speed = Integer.parseInt(document.select(".wikitable.smallpadding tr th span img").last().attr("alt").replace("Monster attack speed", "").trim());
			        							}
			        							break;
			        						case "Body":
			        							item.slot = 4;
			        							if(item.name.toLowerCase().contains("body")) {
			        								item.body = true;
			        							}
			        							break;
			        						case "Shield":
			        							item.slot = 5;
			        							break;
			        						case "Legwear":
			        						case "Legs":
			        							item.slot = 7;
			        							break;
			        						case "Hands":
			        							item.slot = 9;
			        							break;
			        						case "Feet":
			        							item.slot = 10;
			        							break;
			        						case "Ring":
			        							item.slot = 12;
			        							break;
		        							default:
		        								System.out.println("SLOT NIET BEKEND - " + slotElement.last().attr("title").replaceAll(" slot table", ""));
		        								break;
			        					}
				        			}
				        			if(item.name.contains("cape(t)")) {
				        				item.bonusses[13] = 4;
				        			}
				        		} else if(text.contains("Stackable")) {
				        			item.stackable = true;
				        		}
				        	} else if(text.contains(" Alch") && !text.contains("Varies") && !text.contains("unknown") && !text.contains("Various") && !text.contains("N/A") && !text.contains("herbs")) {
				        		text = infoBox.children().last().text();
				        		if(text.contains("doses")) {
				        			text = text.split("doses: ")[1];
				        			if(text.split(",").length > 1) {
				        				text = text.split(",")[Integer.parseInt(item.name.substring(item.name.length() - 2, item.name.length() - 1)) - 1];
				        			}
				        		} else if(text.contains("Whole")) {
				        			text = text.split("Half")[0].split("Whole:")[1];
				        		} else if(text.contains("dose =")) {
				        			text = infoBox.children().last().text().replaceAll("dose", "").split("=")[Integer.parseInt(item.name.substring(item.name.length() - 2, item.name.length() - 1)) - 1].replaceAll("=", "");
				        		} else if(text.contains("Rune:")) {
				        			if(item.name.contains("Steel")) {
				        				text = text.split("Rune:")[0];
				        			} else {
				        				text = text.split("Rune:")[1];
				        			}
				        			text = text.replaceAll("Steel:", "");
				        		} else if(text.contains("-")) {
				        			if(text.split("-").length > 1) {
				        				text = text.split("-")[Integer.parseInt(item.name.substring(item.name.length() - 2, item.name.length() - 1)) - 1];
				        			}
				        		}
				        		if(infoBox.text().contains("High Alch") && !text.contains("Unknown")) {
				        			item.highAlch = Integer.parseInt(text.split("coins")[0].split("coin")[0].split("\\(")[0].replaceAll("coins", "").replaceAll("coin", "").trim().replace(",", "").replaceAll("\u00A0", ""));
				        		} else if(infoBox.text().contains("Low Alch") && !text.contains("Unknown")) {
				        			item.lowAlch = Integer.parseInt(text.split("coins")[0].split("coin")[0].split("\\(")[0].replaceAll("coins", "").replaceAll("coin", "").trim().replace(",", "").replaceAll("\u00A0", ""));
				        		}
				        	} else if(text.contains("Weight") && !text.contains("Unknown") && !text.contains("No")) {
			        			if(text.contains("Equipped")) {
			        				item.weight = Double.parseDouble(infoBox.children().last().text().split("Equipped:")[1].split("kg")[0].replaceAll("\u00A0", ""));
			        			} else if(text.contains("(full)")) {
			        				item.weight = Double.parseDouble(infoBox.children().last().text().split("\\(full\\)")[1].split("kg")[0].replaceAll("\u00A0", ""));
			        			} else if(text.contains("Whole:")) {
			        				item.weight = Double.parseDouble(infoBox.children().last().text().split("Half")[0].split("Whole:")[1].split("kg")[0].replaceAll("\u00A0", ""));
			        			} else if(text.contains("Raw:")) {
			        				item.weight = Double.parseDouble(infoBox.children().last().text().split("Cooked")[0].split("Raw:")[1].split("kg")[0].replaceAll("\u00A0", ""));
			        			} else {
			        				item.weight = Double.parseDouble(infoBox.children().last().text().split(",")[0].split("to")[0].split("kg")[0].replaceAll("\u00A0", "").replaceAll(">", "").replaceAll("<", "").replaceAll("~", ""));
			        			}
			        		} else if(text.contains("Examine")) {
			        			examineNext = true;
			        		}
				        }
				        
				        if(item.name.contains("Clue scroll")) {
				        	item.examine = "A set of instructions to be followed.";
				        }
				        
				        if(item.forceUntradable) {
				        	item.tradable = false;
				        }
			    	}
			    	
			    } else if(line.contains("type.notedID")) {
			    	line = line.replace("type.notedID = ", "");
			    	line = line.replace(";", "");
			    	item.notedId = Integer.parseInt(line.trim());
			    } else if(line.contains("type.notedTemplate")) {
			    	item.note = true;
			    } else if(line.contains("type.maleModel0")) {
			    	item.maleModel = true;
			    } else if(line.contains("break;")) {
			    	if(item.note) {
			    		int id = item.id;
			    		int notedId = item.notedId;
			    		item = items.get(notedId);
			    		item.id = id;
			    		item.notedId = notedId;
			    		item.unnotedId = notedId;
			    		item.note = true;
			    		item.stackable = true;
			    		item.equipable = false;
			    		item.stackable = true;
			    		item.helm = false;
			    		item.body = false;
			    		item.mask = false;
						Arrays.fill(item.bonusses, 0);
						item.slot = -1;
						item.weight = 0;
						item.speed = 0;
			    		item.examine = "Swap this note at any bank for the equivalent item.";
			    	}
			    	if(item.id != -1) {
//			    		if(item.id % 1000 == 0) {
			    			System.out.println(item.id);
//			    		}
			    		if(item.id == 995) {
			    			item.stackable = true;
			    		}
			    		json.beginObject();
			    		json.name("id");
			    		json.value(item.id);
			    		json.name("name");
			    		json.value(item.name);
			    		json.name("description");
			    		json.value(item.examine.replace("�", "").replace("�", "..."));
//			    		json.name("members");
//			    		json.value(item.members);
//			    		json.name("attackSpeed");
//			    		json.value(item.speed);
			    		
//			    		json.name("extraDefinitions");
//			    		json.value(false);
			    		
			    		json.name("equipmentSlot");
			    		json.value(item.slot);
			    		json.name("noteable");
			    		if(!item.note && item.notedId >= 0) {
			    			json.value(true);
			    		} else {
			    			json.value(false);
			    		}
			    		json.name("noted");
			    		json.value(item.note);
			    		json.name("stackable");
			    		json.value(item.stackable);
			    		json.name("specialPrice");
			    		json.value(Math.round(item.highAlch * 1.6));
			    		json.name("generalPrice");
			    		json.value(Math.round(item.highAlch * 1.6));
			    		json.name("lowAlchValue");
			    		json.value(item.lowAlch);
			    		json.name("highAlchValue");
			    		json.value(item.highAlch);
			    		json.name("weight");
			    		json.value(Math.round(item.weight));
			    		json.name("bonus");
			    		json.beginArray();
			    		int i = 0;
			    		for(int bonus : item.bonusses) {
			    			if(i != 11 && i != 12) {
			    				json.value(bonus);
			    			}
			    			i++;
			    		}
//			    		if(item.slot == -1 && item.maleModel && !item.note) {
//			    			System.out.println(item.id + ": " + item.name);
//			    		}
			    		json.endArray();
			    		json.name("twoHanded");
			    		json.value(item.twoHanded);
			    		json.name("fullHelm");
			    		json.value(item.helm);
//			    		json.name("fullMask");
//			    		json.value(item.mask);
			    		json.name("platebody");
			    		json.value(item.body);
			    		json.name("tradable");
			    		json.value(item.tradable);
			    		json.name("unnotedId");
			    		json.value(item.unnotedId);
			    		json.endObject();
			    		items.add(item);
			    	}
			    	if(item.id == maxId) {
			    		finished = true;
			    	}
			    }
			}
		}
		json.endArray();
		json.flush();
		json.close();
	}
}
