package net.openrs.cache.tools.definition_dumpers.items;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
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

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class ItemDefDumper {
	
	public static final String EXPORT = "E:/dump/export/ItemDefinitions.xml";
	
	public static final String LISTFILE = "E:/dump/types/items.txt";
	public static final String JSONFILE = "E:/dump/types/itemjson.txt";

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
	
	public static class Item {
		String name = "";
		int id = -1;
		boolean members = false;
		boolean tradable = false;
		boolean stackable = false;
		int highAlch = 0;
		int lowAlch = 0;
		int generalPrice = 0;
		int notedId = -1;
		boolean note = false;
	}

	public static void main(String[] args) throws Exception {
		
		DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder docBuilder = docFactory.newDocumentBuilder();

		Document doc = docBuilder.newDocument();
		Element defList = doc.createElement("list");
		doc.appendChild(defList);
		
	    String json = readFile(JSONFILE, StandardCharsets.UTF_8);

	    JsonElement jelement = new JsonParser().parse(json);
	    JsonObject prices = jelement.getAsJsonObject();
	    
	    Item item = new Item();
	    
	    List<Item> items = new ArrayList<>();
		
		try (BufferedReader br = new BufferedReader(new FileReader(LISTFILE))) {
		    String line;
		    while ((line = br.readLine()) != null) {
			    if(line.startsWith("case ")) {
			    	if(item.note) {
			    		int id = item.id;
			    		int notedId = item.notedId;
			    		item = items.get(notedId);
			    		item.id = id;
			    		item.notedId = notedId;
			    		item.note = true;
			    		item.stackable = true;
//			    		if(notedId != id - 1) {
//			    			System.out.println(id + " - " + item.name);
//			    		}
			    	}
			    	if(item.id != -1) {
			    		Element el = doc.createElement("ItemDefinition");
			    		defList.appendChild(el);			    		
			    		addAttr(doc, el, "name", item.name);
			    		addAttr(doc, el, "id", Integer.toString(item.id));
			    		addAttr(doc, el, "members", Boolean.toString(item.members));
			    		addAttr(doc, el, "tradable", Boolean.toString(item.tradable));
			    		addAttr(doc, el, "stackable", Boolean.toString(item.stackable));
			    		addAttr(doc, el, "note", Boolean.toString(item.note));
			    		addAttr(doc, el, "noteId", Integer.toString(item.notedId));
			    		addAttr(doc, el, "highAlch", Integer.toString(item.highAlch));
			    		addAttr(doc, el, "lowAlch", Integer.toString(item.lowAlch));
			    		addAttr(doc, el, "generalPrice", Integer.toString(item.generalPrice));
			    		items.add(item);
			    	}
			    	item = new Item();
			    	line = line.replace("case ", "");
			    	line = line.replace(":", "");
			    	item.id = Integer.parseInt(line);
			    	if(prices.get(Integer.toString(item.id)) == null) {
			    		item.tradable = false;
			    		item.highAlch = 0;
			    		item.lowAlch = 0;
			    		item.generalPrice = 0;
			    	} else {
			    		item.tradable = true;
			    		item.highAlch = prices.get(Integer.toString(item.id)).getAsJsonObject().get("store").getAsInt();
			    		item.lowAlch = (int) Math.floor(item.highAlch / 1.5);
			    		item.generalPrice = (int) (((double) item.highAlch / 6) * 10);			   
			    	}
			    } else if(line.contains("type.name")) {
			    	line = line.replace("type.name = \"", "");
			    	line = line.replace("\";", "");
			    	line = line.replace("&", "and");
			    	item.name = line.trim();	
			    } else if(line.contains("type.stackable")) {
			    	item.stackable = true;
			    } else if(line.contains("type.members")) {
			    	item.members = true;
			    } else if(line.contains("type.notedID")) {
			    	line = line.replace("type.notedID = ", "");
			    	line = line.replace(";", "");
			    	item.notedId = Integer.parseInt(line.trim());
			    } else if(line.contains("type.notedTemplate")) {
			    	item.note = true;
			    }
			}
		}
		Element el = doc.createElement("ItemDefinition");
		defList.appendChild(el);			    		
		addAttr(doc, el, "name", item.name);
		addAttr(doc, el, "id", Integer.toString(item.id));
		addAttr(doc, el, "members", Boolean.toString(item.members));
		addAttr(doc, el, "tradable", Boolean.toString(item.tradable));
		addAttr(doc, el, "stackable", Boolean.toString(item.stackable));
		addAttr(doc, el, "note", Boolean.toString(item.note));
		addAttr(doc, el, "noteId", Integer.toString(item.notedId));
		addAttr(doc, el, "highAlch", Integer.toString(item.highAlch));
		addAttr(doc, el, "lowAlch", Integer.toString(item.lowAlch));
		addAttr(doc, el, "generalPrice", Integer.toString(item.generalPrice));
		items.add(item);
		TransformerFactory transformerFactory = TransformerFactory.newInstance();
		Transformer transformer = transformerFactory.newTransformer();
		DOMSource source = new DOMSource(doc);
		StreamResult result = new StreamResult(new File(EXPORT));

		transformer.setOutputProperty(OutputKeys.INDENT, "yes");
		transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
		transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");
		transformer.transform(source, result);		
	}
}