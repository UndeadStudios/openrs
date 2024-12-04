package net.openrs.cache.tools;

public class ItemDefinition {

	private String name;
	private short id;
	private boolean members;
	private boolean tradable;
	private boolean stackable;
	private boolean note;
	private short noteId;
	private int highAlch;
	private int lowAlch;
	private int generalPrice;

	public boolean canNote() {
		return noteId != -1;
	}

	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public int getNoteId() {
		return noteId;
	}
	
	public int getHighAlch() {
		return highAlch;
	}
	
	public int getLowAlch() {
		return lowAlch;
	}
	
	public int getGeneralPrice() {
		return generalPrice;
	}

	public boolean isMembers() {
		return members;
	}

	public boolean isNote() {
		return note;
	}

	public boolean isStackable() {
		return stackable || note;
	}

	public boolean isTradable() {
		return tradable;
	}

	public void setUntradable() {
		tradable = false;
	}
	
	public void setLowAlch(int lowAlch) {
		this.lowAlch = lowAlch;
	}
	
	public void setHighAlch(int highAlch) {
		this.highAlch = highAlch;
	}
	
	
}