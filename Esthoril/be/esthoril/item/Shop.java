package be.esthoril.item;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;

import be.esthoril.game.Screen;
import be.esthoril.gfx.Img;
import be.esthoril.gfx.Room;
import be.esthoril.gfx.Text;

public enum Shop {

	WEAPONSHOP ("WEAPONS R US", new ShopItem[] {ShopItem.BASICSWORD,
												ShopItem.BASICSHIELD,
												ShopItem.KEYJ});

	private String name;
	private ShopItem[] items;
	
	private final int shopX = 150, shopY = Room.itemBar + Room.topBorder + 40;
	private final int shopWidth = 388;
	private final int textX = 50, textY = 30, priceX = 270;
	private final int separatorX = shopWidth - 98, separatorY = 98;
	
	private int shopHeight;
	
	public static int selectedItem = 0;
	public static boolean exitShop = false;

	private Shop(String name, ShopItem[] items) {

		this.name = name;
		this.items = items;
	}
	
	public String getName() {
		return name;
	}
	
	public ShopItem[] getItems() {
		return items;
	}
	
	public void clicked(KeyEvent e) {

		if(e.getKeyCode() == 38) {selectedItem -= 1;}
		if(e.getKeyCode() == 40) {selectedItem += 1;}

		if(selectedItem < 0) {selectedItem = 0;}
		if(selectedItem > items.length-1) {selectedItem = items.length;}

		if(e.getKeyCode() == 68) {
			if(selectedItem == getItems().length) {
				exitShop = true;
			} else if(Screen.player.money >= items[selectedItem].getPrice()) {
				Screen.player.money -= items[selectedItem].getPrice();
			}
		}		
	}
	
	public void draw(Graphics g) {
	
		shopHeight = getItems().length*30 + separatorY + 44;
		


		Text.drawBox(g, shopX, shopY, separatorX, separatorY);
		Text.drawBox(g, shopX, shopY + separatorY, shopWidth, shopHeight - separatorY);
		g.setColor(Color.BLACK);
		g.fillRect(shopX + separatorX - 1, shopY, 98, 97);
		g.drawImage(Img.actor[0], shopX + separatorX, shopY, 96, 96, null);

		g.drawImage(Img.pointer, shopX + 5, shopY + separatorY + 10 + selectedItem * 30, 32, 24, null);
		
		g.setColor(Text.basic);
		g.setFont(Text.font);
		for(int i=0;i<items.length;i++) {
			g.drawString(items[i].getName(), shopX + textX, shopY + separatorY + textY + 30 * i);
			g.drawString(""+items[i].getPrice(), shopX + priceX, shopY + separatorY + textY + 30 * i);
		}
		g.drawString("Done", shopX + textX, shopY + separatorY + textY + 30 * items.length);

		
		
	}
}
