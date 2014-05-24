package be.esthoril.entities;

import java.awt.Rectangle;

import be.esthoril.gfx.Room;
import be.esthoril.item.Shop;

public class ShopKeeper extends NPC {

	private Shop shop;
	
	public ShopKeeper(String name, int[] p, int pic, Shop shop) {
		
		super(name, p, pic);
		this.shop = shop;
	}

	// from +1,+17,30,30 to -26,-4,80,80
	public Rectangle getCollisionBox(int xD, int yD, int wD, int hD) {
		Rectangle box = new Rectangle(x - 26 + Room.xOffset + xD,
				y - 4 + Room.yOffset + yD,
				80 + wD,
				80 + hD);
		return box;
	}

	public Shop getShop() {
		return shop;
	}
}