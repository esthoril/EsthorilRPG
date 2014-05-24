package be.esthoril.readLevel;

import be.esthoril.entities.NPC;
import be.esthoril.entities.ShopKeeper;
import be.esthoril.gfx.SpeechBubble;
import be.esthoril.item.Shop;

public enum MapType {

	// 0 overworld, 1 house, 2 shop, 3 dungeon
	
	OVERWORLD("world", 0, new NPC[] {
		new NPC("BlueGuy", new int[] {6,10, 6,12, 14,12, 6,12},	0, SpeechBubble.BUBBLE2),
		new NPC("Random", new int[] {5,12,	5,14}, 1, SpeechBubble.BUBBLE2)}),
	
	HOUSE1("house1", 1, new NPC[] {
		new NPC("GreenGirl", new int[] {9, 14}, 6, SpeechBubble.BUBBLE1)}),

	SHOP1("shop1", 2, new NPC[] {
		new ShopKeeper("ShopKeeper", new int[] {10, 5}, 3, Shop.WEAPONSHOP)}),
	
	DUN1("dun1", 3, new NPC[] {});
	
	
	

	private String mapName;
	private NPC[] chars;
	private int type;

	private MapType(String mapName, int type, NPC[] chars){
		
		this.type = type;
		this.mapName = mapName;
		this.chars = chars;
		
	}
	
	public String getName() {
		return mapName;
	}
	
	public NPC[] getChars() {
		return chars;
	}
	
	public int getType() {
		return type;
	}
}