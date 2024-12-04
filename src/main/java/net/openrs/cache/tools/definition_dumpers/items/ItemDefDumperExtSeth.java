package net.openrs.cache.tools.definition_dumpers.items;

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
import java.util.Arrays;
import java.util.List;

import org.jsoup.HttpStatusException;
import org.jsoup.Jsoup;
import org.jsoup.select.Elements;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.google.gson.stream.JsonWriter;

public class ItemDefDumperExtSeth {
	
	public static final String EXPORT = "E:/dump/export/ItemDefinitionsSeth.json";
	
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
		int geprice = 0;
		double weight = 0;
		String examine = "Unknown";
		String slot = "NONE";
		boolean twoHanded = false;
		boolean helm = false;
		boolean mask = false;
		boolean body = false;
		int speed = 0;
		int[] bonusses = new int[14];
		int[] requirements = new int[23];
		boolean note = false;
		boolean noteable = false;
		int unnotedId = -1;
		int notedId = -1;
		public int cost;
		boolean headslot = false;
		boolean bodyslot = false;
		boolean weaponslot = false;
	}
	
	public static boolean stringContainsItemFromList(String inputStr, List<String> strings) {
	    return Arrays.stream((String[]) strings.toArray()).parallel().anyMatch(inputStr::contains);
	}

	public static void main(String[] args) throws Exception {
		
		JsonWriter json = new JsonWriter(new FileWriter(EXPORT));
		json.setIndent("  ");
		json.beginArray();
		
		Item item = new Item();
		
		boolean finished = false;
		
		int maxId = 21393;
	    
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
			    	
			    	if (item.name.contains(" seed")) {
			    		item.stackable = true;
			    	}
			    	
			    	if(item.name.contains(" arrow") || item.name.contains(" knife") || item.name.contains(" dart") || item.name.contains(" bolts")) {
			    		item.stackable = true;
			    	}
			    		
			    	String itemname = item.name.toLowerCase();
		    		if (itemname.contains("steel")) {
		    			if ((itemname.contains("sword")) || (itemname.contains("scimitar")) || (itemname.contains("battleaxe"))
		    					|| (itemname.contains("warhammer")) || (itemname.contains("spear")) || (itemname.contains("dagger"))) {
		    				item.requirements[0] = 5;
		    			} else if ((itemname.contains("platebody")) || (itemname.contains("chainbody")) || (itemname.contains("boots"))
		    					|| (itemname.contains("legs")) || (itemname.contains("skirt")) || (itemname.contains("gloves"))
		    					|| (itemname.contains("shield")) || (itemname.contains("defender"))) {
		    				item.requirements[1] = 5;
		    			} else if ((itemname.contains("knife")) || (itemname.contains("thrownaxe")) || (itemname.contains("javelin"))
		    					|| (itemname.contains("dart"))) {
		    				item.requirements[4] = 5;
		    			}
		    		} else if (itemname.contains("black")) {
		    			if ((itemname.contains("sword")) || (itemname.contains("scimitar")) || (itemname.contains("battleaxe"))
		    					|| (itemname.contains("warhammer")) || (itemname.contains("spear")) || (itemname.contains("dagger"))) {
		    				item.requirements[0] = 10;
		    			} else if ((itemname.contains("platebody")) || (itemname.contains("chainbody")) || (itemname.contains("boots"))
		    					|| (itemname.contains("legs")) || (itemname.contains("skirt")) || (itemname.contains("gloves"))
		    					|| (itemname.contains("shield")) || (itemname.contains("defender"))) {
		    				item.requirements[1] = 10;
		    			} else if ((itemname.contains("knife")) || (itemname.contains("thrownaxe")) || (itemname.contains("javelin"))
		    					|| (itemname.contains("dart"))) {
		    				item.requirements[4] = 10;
		    			}
		    			if (itemname.contains("d'hide")) {
		    				if (itemname.contains("body")) {
		    					item.requirements[1] = 40;
		    					item.requirements[4] = 70;
		    				} else if ((itemname.contains("chaps")) || (itemname.contains("vamb"))) {
		    					item.requirements[4] = 70;
		    				}
		    			}
		    		} else if (itemname.contains("mithril")) {
		    			if ((itemname.contains("sword")) || (itemname.contains("scimitar")) || (itemname.contains("battleaxe"))
		    					|| (itemname.contains("warhammer")) || (itemname.contains("spear")) || (itemname.contains("dagger"))) {
		    				item.requirements[0] = 20;
		    			} else if ((itemname.contains("platebody")) || (itemname.contains("chainbody")) || (itemname.contains("boots"))
		    					|| (itemname.contains("legs")) || (itemname.contains("skirt")) || (itemname.contains("gloves"))
		    					|| (itemname.contains("shield")) || (itemname.contains("defender"))) {
		    				item.requirements[1] = 20;
		    			} else if ((itemname.contains("knife")) || (itemname.contains("thrownaxe")) || (itemname.contains("javelin"))
		    					|| (itemname.contains("dart"))) {
		    				item.requirements[4] = 20;
		    			}
		    		} else if (itemname.contains("adamant")) {
		    			if ((itemname.contains("sword")) || (itemname.contains("scimitar")) || (itemname.contains("battleaxe"))
		    					|| (itemname.contains("warhammer")) || (itemname.contains("spear")) || (itemname.contains("dagger"))) {
		    				item.requirements[0] = 30;
		    			} else if ((itemname.contains("platebody")) || (itemname.contains("chainbody")) || (itemname.contains("boots"))
		    					|| (itemname.contains("legs")) || (itemname.contains("skirt")) || (itemname.contains("gloves"))
		    					|| (itemname.contains("shield")) || (itemname.contains("defender"))) {
		    				item.requirements[1] = 30;
		    			} else if ((itemname.contains("knife")) || (itemname.contains("thrownaxe")) || (itemname.contains("javelin"))
		    					|| (itemname.contains("dart"))) {
		    				item.requirements[4] = 30;
		    			}
		    		} else if (itemname.contains("rune")) {
		    			if ((itemname.contains("sword")) || (itemname.contains("scimitar")) || (itemname.contains("battleaxe"))
		    					|| (itemname.contains("warhammer")) || (itemname.contains("spear")) || (itemname.contains("dagger"))) {
		    				item.requirements[0] = 40;
		    			} else if ((itemname.contains("platebody")) || (itemname.contains("chainbody")) || (itemname.contains("boots"))
		    					|| (itemname.contains("legs")) || (itemname.contains("skirt")) || (itemname.contains("gloves"))
		    					|| (itemname.contains("shield")) || (itemname.contains("defender"))) {
		    				item.requirements[1] = 40;
		    			} else if ((itemname.contains("knife")) || (itemname.contains("thrownaxe")) || (itemname.contains("javelin"))
		    					|| (itemname.contains("dart"))) {
		    				item.requirements[4] = 40;
		    			}
		    		} else if (itemname.contains("dragon")) {
		    			if ((itemname.contains("sword")) || (itemname.contains("scimitar")) || (itemname.contains("battleaxe"))
		    					|| (itemname.contains("warhammer")) || (itemname.contains("spear")) || (itemname.contains("dagger"))) {
		    				item.requirements[0] = 60;
		    			} else if ((itemname.contains("platebody")) || (itemname.contains("chainbody")) || (itemname.contains("boots"))
		    					|| (itemname.contains("legs")) || (itemname.contains("skirt")) || (itemname.contains("gloves"))
		    					|| (itemname.contains("shield")) || (itemname.contains("defender"))) {
		    				item.requirements[1] = 60;
		    			} else if ((itemname.contains("knife")) || (itemname.contains("thrownaxe")) || (itemname.contains("javelin"))
		    					|| (itemname.contains("dart"))) {
		    				item.requirements[4] = 60;
		    			}
		    		}
		    		if (itemname.contains("godsword")) {
		    			item.requirements[0] = 75;
		    		}
		    		if (itemname.contains("neitiznot")) {
		    			item.requirements[1] = 55;
		    		}
		    		if ((itemname.contains("berserker helm")) || (itemname.contains("farseer helm")) || (itemname.contains("archer helm"))
		    				|| (itemname.contains("warrior helm"))) {
		    			item.requirements[1] = 45;
		    		}
		    		if (itemname.contains("abyssal")) {
		    			item.requirements[0] = 70;
		    		}
		    		if (itemname.contains("dharok")) {
		    			if (itemname.contains("greataxe")) {
		    				item.requirements[0] = 70;
		    				item.requirements[2] = 70;
		    			} else {
		    				item.requirements[1] = 70;
		    			}
		    		}
		    		if (itemname.contains("torag")) {
		    			if (itemname.contains("hammer")) {
		    				item.requirements[0] = 70;
		    				item.requirements[2] = 70;
		    			} else {
		    				item.requirements[1] = 70;
		    			}
		    		}
		    		if (itemname.contains("verac")) {
		    			if (itemname.contains("flail")) {
		    				item.requirements[0] = 70;
		    				item.requirements[2] = 70;
		    			} else {
		    				item.requirements[1] = 70;
		    			}
		    		}
		    		if (itemname.contains("guthan")) {
		    			if (itemname.contains("spear")) {
		    				item.requirements[0] = 70;
		    				item.requirements[2] = 70;
		    			} else {
		    				item.requirements[1] = 70;
		    			}
		    		}
		    		if (itemname.contains("karil")) {
		    			if (itemname.contains("bow")) {
		    				item.requirements[4] = 70;
		    			} else {
		    				item.requirements[4] = 70;
		    				item.requirements[1] = 70;
		    			}
		    		}
		    		if (itemname.contains("ahrim")) {
		    			if (itemname.contains("bow")) {
		    				item.requirements[6] = 70;
		    			} else {
		    				item.requirements[6] = 70;
		    				item.requirements[1] = 70;
		    			}
		    		}
		    		if (itemname.contains("d'hide")) {
		    			if (itemname.contains("blue")) {
		    				if (itemname.contains("body")) {
		    					item.requirements[1] = 40;
		    					item.requirements[4] = 50;
		    				} else if ((itemname.contains("chaps")) || (itemname.contains("vamb"))) {
		    					item.requirements[4] = 50;
		    				}
		    			}
		    			if (itemname.contains("green")) {
		    				if (itemname.contains("body")) {
		    					item.requirements[1] = 40;
		    					item.requirements[4] = 40;
		    				} else if ((itemname.contains("chaps")) || (itemname.contains("vamb"))) {
		    					item.requirements[4] = 40;
		    				}
		    			}
		    			if (itemname.contains("red")) {
		    				if (itemname.contains("body")) {
		    					item.requirements[1] = 40;
		    					item.requirements[4] = 60;
		    				} else if ((itemname.contains("chaps")) || (itemname.contains("vamb"))) {
		    					item.requirements[4] = 60;
		    				}
		    			}
		    		}
		    		if (itemname.contains("staff of the dead")) {
		    			item.requirements[0] = 75;
		    			item.requirements[6] = 75;
		    		}
		    		if (itemname.contains("max cape") || itemname.contains("max hood")) {
		    			for (int i = 0; i < 23; i++)
		    				item.requirements[i] = 99;
		    		}
		    		if (itemname.contains("void knight")) {
		    			item.requirements[0] = 42;
		    			item.requirements[1] = 42;
		    			item.requirements[2] = 42;
		    			item.requirements[3] = 42;
		    			item.requirements[4] = 42;
		    			item.requirements[6] = 42;
		    		}
		    		if (itemname.contains("spirit shield")) {
		    			item.requirements[1] = 45;
		    			item.requirements[5] = 55;
		    			if (itemname.contains("blessed")) {
		    				item.requirements[1] = 70;
		    				item.requirements[5] = 85;
		    			}
		    			if (itemname.contains("arcane")) {
		    				item.requirements[1] = 75;
		    				item.requirements[5] = 70;
		    				item.requirements[6] = 65;
		    			}
		    			if (itemname.contains("spectral")) {
		    				item.requirements[1] = 75;
		    				item.requirements[5] = 70;
		    				item.requirements[6] = 65;
		    			}
		    			if (itemname.contains("elysian")) {
		    				item.requirements[1] = 75;
		    				item.requirements[5] = 75;
		    			}
		    		}
		    		if (itemname.contains(" cape") || itemname.contains(" hood")) {
		    			if (itemname.contains("attack")) {
		    				item.requirements[0] = 99;
		    			} else if (itemname.contains("defence")) {
		    				item.requirements[1] = 99;
		    			} else if (itemname.contains("strength")) {
		    				item.requirements[2] = 99;
		    			} else if (itemname.contains("hitpoints")) {
		    				item.requirements[3] = 99;
		    			} else if (itemname.contains("ranging")) {
		    				item.requirements[4] = 99;
		    			} else if (itemname.contains("prayer")) {
		    				item.requirements[5] = 99;
		    			} else if (itemname.contains("magic")) {
		    				item.requirements[6] = 99;
		    			} else if (itemname.contains("cooking")) {
		    				item.requirements[7] = 99;
		    			} else if (itemname.contains("woodcutting")) {
		    				item.requirements[8] = 99;
		    			} else if (itemname.contains("fletching")) {
		    				item.requirements[9] = 99;
		    			} else if (itemname.contains("fishing")) {
		    				item.requirements[10] = 99;
		    			} else if (itemname.contains("firemaking")) {
		    				item.requirements[11] = 99;
		    			} else if (itemname.contains("crafting")) {
		    				item.requirements[12] = 99;
		    			} else if (itemname.contains("smithing")) {
		    				item.requirements[13] = 99;
		    			} else if (itemname.contains("mining")) {
		    				item.requirements[14] = 99;
		    			} else if (itemname.contains("herblore")) {
		    				item.requirements[15] = 99;
		    			} else if (itemname.contains("agility")) {
		    				item.requirements[16] = 99;
		    			} else if (itemname.contains("thieving")) {
		    				item.requirements[17] = 99;
		    			} else if (itemname.contains("slayer")) {
		    				item.requirements[18] = 99;
		    			} else if (itemname.contains("farming")) {
		    				item.requirements[19] = 99;
		    			} else if (itemname.contains("runecrafting")) {
		    				item.requirements[20] = 99;
		    			} else if (itemname.contains("hunter")) {
		    				item.requirements[21] = 99;
		    			} else if (itemname.contains("constr")) {
		    				item.requirements[22] = 99;
		    			}
		    		}
		    		
			    	String url = "http://2007.runescape.wikia.com/wiki/" + item.name.replaceAll(" ", "_");
			    	org.jsoup.nodes.Document document = null;
			    	try {
				        document = openDocument(url);		
			        } catch (HttpStatusException e) {
			        	if(item.name.contains("Burnt ")) {
			        		item.examine = "Oh dear, it's totally burnt!";
			        		item.tradable = true;
			        	} else if(item.name.contains("(kp)")) {
			        		url = "http://2007.runescape.wikia.com/wiki/" + item.name.replaceAll(" ", "_").substring(0, item.name.length() - 4);
			        		document = openDocument(url);
			        	} else if(item.name.contains("Ahrim") || item.name.contains("Dharok") || item.name.contains("Guthan") || item.name.contains("Karil") || item.name.contains("Torag") || item.name.contains("Verac")) {
			        		if(item.name.contains("100")) {
				        		url = "http://2007.runescape.wikia.com/wiki/" + item.name.replaceAll(" ", "_").substring(0, item.name.length() - 4);
			        		} else {
				        		url = "http://2007.runescape.wikia.com/wiki/" + item.name.replaceAll(" ", "_").substring(0, item.name.length() - 3);
				        		item.forceUntradable = true;
			        		}
			        		document = openDocument(url);
			        	} else if(item.name.contains("- pt")) {
			        		url = "http://2007.runescape.wikia.com/wiki/" + item.name.substring(0, item.name.length() - 6).replaceAll(" ", "_");
			        		document = openDocument(url);
			        	} else if(item.name.contains("(m1)") || item.name.contains("(m2)") || item.name.contains("(m3)") || item.name.contains("(m4)") || item.name.contains("(10)")) {
			        		url = "http://2007.runescape.wikia.com/wiki/" + item.name.substring(0, item.name.length() - 4).trim().replaceAll(" ", "_");
			        		document = openDocument(url);
			        	} else if((item.name.contains("(0)") || item.name.contains("(1)") || item.name.contains("(2)") || item.name.contains("(3)") || item.name.contains("(4)") || item.name.contains("(5)") || item.name.contains("(6)") || item.name.contains("(7)") || item.name.contains("(8)") || item.name.contains("(9)") || item.name.contains("(o)")) && !item.name.contains("Mixture")) {
			        		url = "http://2007.runescape.wikia.com/wiki/" + item.name.substring(0, item.name.length() - 3).trim().replaceAll(" ", "_");
			        		try {
						        document = openDocument(url);		
					        } catch (HttpStatusException ex) {
					        	
					        }
			        	} else if((item.name.endsWith("0") || item.name.endsWith("1") || item.name.endsWith("2") || item.name.endsWith("3") || item.name.endsWith("4") || item.name.endsWith("5") || item.name.endsWith("6") || item.name.endsWith("7") || item.name.endsWith("8") || item.name.endsWith("9") || item.name.endsWith("10")) && !item.name.contains("Magical balance") && !item.name.contains("Rune case") && !item.name.contains("Godsword shards")) {
			        		url = "http://2007.runescape.wikia.com/wiki/" + item.name.substring(0, item.name.length() - 2).trim().replaceAll(" ", "_");
			        		document = openDocument(url);
			        	} else if(item.name.endsWith("cape(t)")) {
			        		url = "http://2007.runescape.wikia.com/wiki/" + item.name.substring(0, item.name.length() - 3).trim().replaceAll(" ", "_").replaceAll("Woodcut.", "Woodcutting");
			        		document = openDocument(url);
			        	} else if(item.name.contains("hat and")) {
			        		url = "http://2007.runescape.wikia.com/wiki/" + item.name.replaceAll("hat and", "hat %26").replaceAll(" ", "_");
			        		document = openDocument(url);
			        	} else if(item.name.contains(" (uncharged)")) {
			        		url = "http://2007.runescape.wikia.com/wiki/" + item.name.substring(0, item.name.length() - 12).replaceAll(" ", "_");
			        		document = openDocument(url);
			        	} else if(item.name.contains("Dragon javelin(p")) {
			        		url = "http://2007.runescape.wikia.com/wiki/Dragon_javelin";
			        		document = openDocument(url);
			        	}
			        	
			        }
			    	
			    	if(document != null) {
			            BufferedWriter htmlWriter = 
			            	     new BufferedWriter(new OutputStreamWriter(
			            	    		 new FileOutputStream("C:/Users/Stan/Desktop/RS/wiki/" + 
			            	     url.replace("http://2007.runescape.wikia.com/wiki/", "").replace("/", "").replace("???", "") + ".html"), "UTF-8"));
			            htmlWriter.write(document.toString());
			            htmlWriter.flush();
			            htmlWriter.close();
//			    		Elements sprite = document.select(".wikitable.infobox tr > td > a > img");
//	        			if(sprite.first() != null) {
//	        				try(InputStream in = new URL(sprite.first().attr("src")).openStream()){
//				    		    Files.copy(in, Paths.get("C:/Users/Stan/Desktop/Item sprites/" + item.id + ".png"));
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
				        			Elements gemain = document.select("#GEPrice");
				        			Elements ge = document.select("#GEPrice .GEItem > span");
				        			if(gemain.text().contains("dose")) {
				        				if(item.name.contains("4")) {
				        					item.geprice = Integer.parseInt(ge.get(ge.size() - 4).text().replace(",", ""));
				        				} else if(item.name.contains("3")) {
				        					item.geprice = Integer.parseInt(ge.get(ge.size() - 3).text().replace(",", ""));
				        				} else if(item.name.contains("2")) {
				        					item.geprice = Integer.parseInt(ge.get(ge.size() - 2).text().replace(",", ""));
				        				} else if(item.name.contains("1")) {
				        					item.geprice = Integer.parseInt(ge.get(ge.size() - 1).text().replace(",", ""));
				        				}
				        			} else if(ge.first() != null && !ge.first().text().contains("error")) {
				        				item.geprice = Integer.parseInt(ge.first().text().replace(",", "").replaceAll("Clean:", ""));
				        			}
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
		        							if (item.name.toLowerCase().contains("med") && item.name.toLowerCase().contains("helm")) {
		        								item.slot = "MED_HELMET";
		        							} else if (item.name.toLowerCase().contains("helm") && item.name.toLowerCase().contains("full")) {
		        								item.slot = "FULL_HELMET";
		        							} else if (item.name.toLowerCase().contains("mask")) {
		        								item.slot = "MASK";
		        							} else if (item.name.toLowerCase().contains("coif")) {
		        								item.slot = "COIF";
		        							} else {
		        								item.slot = "HAT";
		        							}
		        							item.headslot = true;
		        							break;
		        						case "Cape":
		        							if (item.name.toLowerCase().contains("hooded")) {
		        								item.slot = "HOODED_CAPE";
		        							} else {
		        								item.slot = "CAPE";
		        							}
		        							break;
		        						case "Neck":
		        							item.slot = "AMULET";
		        							break;
		        						case "Ammunition":
		        						case "Ammo":
		        							item.slot = "ARROWS";
		        							break;
		        						case "Weapon":
		        							item.slot = "WEAPON";
		        							if(document.select(".wikitable.smallpadding tr th span img").last().attr("alt").contains("Monster")) {
		        								item.speed = Integer.parseInt(document.select(".wikitable.smallpadding tr th span img").last().attr("alt").replace("Monster attack speed", "").trim());
		        							}
		        							item.weaponslot = true;
		        							break;
		        						case "Two-handed":
		        						case "2h":
		        							item.twoHanded = true;
		        							item.slot = "WEAPON";
		        							if(document.select(".wikitable.smallpadding tr th span img").last().attr("alt").contains("Monster")) {
		        								item.speed = Integer.parseInt(document.select(".wikitable.smallpadding tr th span img").last().attr("alt").replace("Monster attack speed", "").trim());
		        							}
		        							item.weaponslot = true;
		        							break;
		        						case "Body":
		        							if(item.name.toLowerCase().contains("body") || item.name.toLowerCase().contains("shirt") || item.name.toLowerCase().contains("robe")  || item.name.toLowerCase().contains("robetop") || item.name.toLowerCase().contains("chestplate") || item.name.toLowerCase().contains("garb")) {
		        								item.slot = "BODY";
		        							} else {
		        								item.slot = "PLATEBODY";
		        							}
		        							item.bodyslot = true;
		        							break;
		        						case "Shield":
		        							item.slot = "SHIELD";
		        							break;
		        						case "Legwear":
		        						case "Legs":
		        							item.slot = "LEGS";
		        							break;
		        						case "Hands":
		        							item.slot = "GLOVES";
		        							break;
		        						case "Feet":
		        							item.slot = "BOOTS";
		        							break;
		        						case "Ring":
		        							item.slot = "RING";
		        							break;
		        							default:
		        								System.out.println("SLOT NIET BEKEND - " + slotElement.last().attr("title").replaceAll(" slot table", ""));
		        								break;
			        					}
				        			}
				        			
				        			Elements totalDescr = document.select("#mw-content-text > p");
				        			String descr = "";
				        			for (org.jsoup.nodes.Element el : totalDescr) {
				        				descr += el.text().toLowerCase();
				        			}
				        			if (descr != "" && !(descr.toLowerCase().contains("enchant"))) {
				        				descr = "to prevent errors boiiis" + descr + "to prevent errors boiiis";
				        				String reqText = "";
				        				String extraText = "";
				        				if (descr.contains("attack")) {
				        					extraText = descr.substring(descr.indexOf("attack") - 22, descr.indexOf("attack") + 26);
				        					if (extraText.matches(".*\\d+.*") && !extraText.contains("jinxed")) {
				        						reqText += extraText;
				        					}
				        					descr = descr.replaceFirst("attack", "jinxed");
				        					if (descr.contains("attack")) {
					        					extraText = descr.substring(descr.indexOf("attack") - 22, descr.indexOf("attack") + 26);
					        					if (extraText.matches(".*\\d+.*") && !extraText.contains("jinxed")) {
					        						reqText += extraText;
					        					}
					        					descr = descr.replaceFirst("attack", "jinxed");
					        					if (descr.contains("attack")) {
						        					extraText = descr.substring(descr.indexOf("attack") - 22, descr.indexOf("attack") + 26);
						        					if (extraText.matches(".*\\d+.*") && !extraText.contains("jinxed")) {
						        						reqText += extraText;
						        					}
						        					descr = descr.replaceFirst("attack", "jinxed");
						        				}
					        				}
				        				}
				        				if (descr.contains("defence")) {
				        					extraText = descr.substring(descr.indexOf("defence") - 22, descr.indexOf("defence") + 26);
				        					if (extraText.matches(".*\\d+.*") && !extraText.contains("jinxed")) {
				        						reqText += extraText;
				        					}
				        					descr = descr.replaceFirst("defence", "jinxed");
				        					if (descr.contains("defence")) {
					        					extraText = descr.substring(descr.indexOf("defence") - 22, descr.indexOf("defence") + 26);
					        					if (extraText.matches(".*\\d+.*") && !extraText.contains("jinxed")) {
					        						reqText += extraText;
					        					}
					        					descr = descr.replaceFirst("defence", "jinxed");
					        					if (descr.contains("defence")) {
						        					extraText = descr.substring(descr.indexOf("defence") - 22, descr.indexOf("defence") + 26);
						        					if (extraText.matches(".*\\d+.*") && !extraText.contains("jinxed")) {
						        						reqText += extraText;
						        					}
						        					descr = descr.replaceFirst("defence", "jinxed");
						        				}
					        				}
				        				}
				        				if (descr.contains("strength")) {
				        					extraText = descr.substring(descr.indexOf("strength") - 22, descr.indexOf("strength") + 26);
				        					if (extraText.matches(".*\\d+.*") && !extraText.contains("jinxed")) {
				        						reqText += extraText;
				        					}
				        					descr = descr.replaceFirst("strength", "jinxed");
				        					if (descr.contains("strength")) {
					        					extraText = descr.substring(descr.indexOf("strength") - 22, descr.indexOf("strength") + 26);
					        					if (extraText.matches(".*\\d+.*") && !extraText.contains("jinxed")) {
					        						reqText += extraText;
					        					}
					        					descr = descr.replaceFirst("strength", "jinxed");
					        					if (descr.contains("strength")) {
						        					extraText = descr.substring(descr.indexOf("strength") - 22, descr.indexOf("strength") + 26);
						        					if (extraText.matches(".*\\d+.*") && !extraText.contains("jinxed")) {
						        						reqText += extraText;
						        					}
						        					descr = descr.replaceFirst("strength", "jinxed");
						        				}
					        				}
				        				}
				        				if (descr.contains("hitpoints")) {
				        					extraText = descr.substring(descr.indexOf("hitpoints") - 22, descr.indexOf("hitpoints") + 26);
				        					if (extraText.matches(".*\\d+.*") && !extraText.contains("jinxed")) {
				        						reqText += extraText;
				        					}
				        					descr = descr.replaceFirst("hitpoints", "jinxed");
				        					if (descr.contains("hitpoints")) {
					        					extraText = descr.substring(descr.indexOf("hitpoints") - 22, descr.indexOf("hitpoints") + 26);
					        					if (extraText.matches(".*\\d+.*") && !extraText.contains("jinxed")) {
					        						reqText += extraText;
					        					}
					        					descr = descr.replaceFirst("hitpoints", "jinxed");
					        					if (descr.contains("hitpoints")) {
						        					extraText = descr.substring(descr.indexOf("hitpoints") - 22, descr.indexOf("hitpoints") + 26);
						        					if (extraText.matches(".*\\d+.*") && !extraText.contains("jinxed")) {
						        						reqText += extraText;
						        					}
						        					descr = descr.replaceFirst("hitpoints", "jinxed");
						        				}
					        				}
				        				}
				        				if (descr.contains("magic")) {
				        					extraText = descr.substring(descr.indexOf("magic") - 22, descr.indexOf("magic") + 26);
				        					if (extraText.matches(".*\\d+.*") && !extraText.contains("jinxed")) {
				        						reqText += extraText;
				        					}
				        					descr = descr.replaceFirst("magic", "jinxed");
				        					if (descr.contains("magic")) {
					        					extraText = descr.substring(descr.indexOf("magic") - 22, descr.indexOf("magic") + 26);
					        					if (extraText.matches(".*\\d+.*") && !extraText.contains("jinxed")) {
					        						reqText += extraText;
					        					}
					        					descr = descr.replaceFirst("magic", "jinxed");
					        					if (descr.contains("magic")) {
						        					extraText = descr.substring(descr.indexOf("magic") - 22, descr.indexOf("magic") + 26);
						        					if (extraText.matches(".*\\d+.*") && !extraText.contains("jinxed")) {
						        						reqText += extraText;
						        					}
						        					descr = descr.replaceFirst("magic", "jinxed");
						        				}
					        				}
				        				}
				        				if (descr.contains("ranged")) {
				        					extraText = descr.substring(descr.indexOf("ranged") - 22, descr.indexOf("ranged") + 26);
				        					if (extraText.matches(".*\\d+.*") && !extraText.contains("jinxed")) {
				        						reqText += extraText;
				        					}
				        					descr = descr.replaceFirst("ranged", "jinxed");
				        					if (descr.contains("ranged")) {
					        					extraText = descr.substring(descr.indexOf("ranged") - 22, descr.indexOf("ranged") + 26);
					        					if (extraText.matches(".*\\d+.*") && !extraText.contains("jinxed")) {
					        						reqText += extraText;
					        					}
					        					descr = descr.replaceFirst("ranged", "jinxed");
					        					if (descr.contains("ranged")) {
						        					extraText = descr.substring(descr.indexOf("ranged") - 22, descr.indexOf("ranged") + 26);
						        					if (extraText.matches(".*\\d+.*") && !extraText.contains("jinxed")) {
						        						reqText += extraText;
						        					}
						        					descr = descr.replaceFirst("ranged", "jinxed");
						        				}
					        				}
				        				}
				        				if (descr.contains("prayer")) {
				        					extraText = descr.substring(descr.indexOf("prayer") - 22, descr.indexOf("prayer") + 26);
				        					if (extraText.matches(".*\\d+.*") && !extraText.contains("jinxed")) {
				        						reqText += extraText;
				        					}
				        					descr = descr.replaceFirst("prayer", "jinxed");
				        					if (descr.contains("prayer")) {
					        					extraText = descr.substring(descr.indexOf("prayer") - 22, descr.indexOf("prayer") + 26);
					        					if (extraText.matches(".*\\d+.*") && !extraText.contains("jinxed")) {
					        						reqText += extraText;
					        					}
					        					descr = descr.replaceFirst("prayer", "jinxed");
					        					if (descr.contains("prayer")) {
						        					extraText = descr.substring(descr.indexOf("prayer") - 22, descr.indexOf("prayer") + 26);
						        					if (extraText.matches(".*\\d+.*") && !extraText.contains("jinxed")) {
						        						reqText += extraText;
						        					}
						        					descr = descr.replaceFirst("prayer", "jinxed");
						        				}
					        				}
				        				}
				        				if (descr.contains("agility")) {
				        					extraText = descr.substring(descr.indexOf("agility") - 22, descr.indexOf("agility") + 26);
				        					if (extraText.matches(".*\\d+.*") && !extraText.contains("jinxed")) {
				        						reqText += extraText;
				        					}
				        					descr = descr.replaceFirst("agility", "jinxed");
				        					if (descr.contains("agility")) {
					        					extraText = descr.substring(descr.indexOf("agility") - 22, descr.indexOf("agility") + 26);
					        					if (extraText.matches(".*\\d+.*") && !extraText.contains("jinxed")) {
					        						reqText += extraText;
					        					}
					        					descr = descr.replaceFirst("agility", "jinxed");
					        					if (descr.contains("agility")) {
						        					extraText = descr.substring(descr.indexOf("agility") - 22, descr.indexOf("agility") + 26);
						        					if (extraText.matches(".*\\d+.*") && !extraText.contains("jinxed")) {
						        						reqText += extraText;
						        					}
						        					descr = descr.replaceFirst("agility", "jinxed");
						        				}
					        				}
				        				}
				        				if (reqText != "") {
//				        					System.out.println(item.id + " - " + item.name + ":");
//				        					System.out.println(reqText);
//				        					System.out.println("----");
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
				        		if(infoBox.text().contains("High Alch") && !text.contains("Unknown") && !text.contains("?")) {
				        			String splitText = text.split("coins")[0].split("coin")[0].split("\\(")[0].replaceAll("Clean:", "").replaceAll("coins", "").replaceAll("coin", "").trim().replace(",", "").replaceAll("\u00A0", "");
				        			if (!splitText.isEmpty()) {
				        				item.highAlch = Integer.parseInt(splitText);
				        			}
				        		} else if(infoBox.text().contains("Low Alch") && !text.contains("Unknown") && !text.contains("?")) {
				        			String splitText = text.split("coins")[0].split("coin")[0].split("\\(")[0].replaceAll("Clean:", "").replaceAll("coins", "").replaceAll("coin", "").trim().replace(",", "").replaceAll("\u00A0", "");
				        			if (!splitText.isEmpty()) {
				        				item.lowAlch = Integer.parseInt(splitText);
				        			}
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
			    } else if(line.contains("type.cost")) {
			    	line = line.replace("type.cost = ", "");
			    	line = line.replace(";", "");
			    	item.cost = Integer.parseInt(line.trim());
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
						item.slot = "NONE";
						item.weight = 0;
						item.speed = 0;
			    		item.examine = "Swap this note at any bank for the equivalent item.";
			    	}
			    	if(item.id != -1) {
			    		if(item.id % 1000 == 0) {
			    			System.out.println(item.id);
			    		}
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
			    		json.name("stackable");
			    		json.value(item.stackable);
			    		json.name("tradeable");
			    		json.value(item.tradable);
			    		json.name("noted");
			    		json.value(item.note);
			    		json.name("notedId");
			    		json.value(item.notedId);
			    		json.name("shopValue");
			    		json.value(Math.round(((double) item.highAlch / 6) * 10));
			    		json.name("grandExchangePrice");
			    		json.value(item.geprice);
			    		json.name("dropValue");
			    		json.value(item.cost);
			    		json.name("lowAlchValue");
			    		json.value(item.lowAlch);
			    		json.name("highAlchValue");
			    		json.value(item.highAlch);
			    		json.name("weight");
			    		json.value(item.weight);
			    		json.name("equipmentType");
			    		json.value(item.slot);
			    		if(item.slot != "NONE") {
			    			if(item.headslot) {
			    				json.name("fullHelm");
					    		json.value(item.helm);
					    		json.name("mask");
					    		json.value(item.mask);
			    			} else if(item.weaponslot) {
			    				json.name("twoHanded");
					    		json.value(item.twoHanded);
					    		json.name("weaponSpeed");
					    		json.value(item.speed);
			    			} else if(item.bodyslot) {
			    				json.name("platebody");
					    		json.value(item.body);
			    			}				    				
				    		json.name("bonus");
				    		json.beginArray();
				    		for(int bonus : item.bonusses) {
			    				json.value(bonus);
				    		}
				    		json.endArray();
				    		json.name("requirements");
				    		json.beginArray();
				    		for(int requirement : item.requirements) {
				    			json.value(requirement);
				    		}
				    		json.endArray();
			    		}
			    		
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
	
	private static org.jsoup.nodes.Document openDocument(String url) throws IOException {
		File input = new File("C:/Users/Stan/Desktop/RS/wiki/" + 
       	     url.replace("http://2007.runescape.wikia.com/wiki/", "").replace("/", "").replace("???", "") + ".html");
		if (input.exists()) {
			return Jsoup.parse(input, "UTF-8", url);
		}
		return Jsoup.connect(url).timeout(60000).get();
	}
}
