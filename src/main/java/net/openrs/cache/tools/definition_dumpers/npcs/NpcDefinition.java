package net.openrs.cache.tools.definition_dumpers.npcs;

public class NpcDefinition {

	private String name;
	private short id;
	private short walkAnimation;
	private short standAnimation;
	private short turn180Animation;
	private short turn90CWAnimation;
	private short turn90CCWAnimation;
	private short level;
	private byte size;
	private boolean attackable;

	public int getId() {
		return id;
	}
	
	public short getWalkAnimation() {
		return walkAnimation;
	}
	
	public short getStandAnimation() {
		return standAnimation;
	}
	
	public short getTurn180Animation() {
		return turn180Animation;
	}
	
	public short getTurn90CWAnimation() {
		return turn90CWAnimation;
	}
	
	public short getTurn90CCWAnimation() {
		return turn90CCWAnimation;
	}

	public int getLevel() {
		return level;
	}

	public String getName() {
		return name;
	}

	public int getSize() {
		return size;
	}

	public boolean isAttackable() {
		return attackable;
	}
}