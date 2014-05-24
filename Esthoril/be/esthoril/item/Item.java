package be.esthoril.item;

import java.awt.Graphics;
import java.awt.Rectangle;

import be.esthoril.game.Screen;
import be.esthoril.gfx.Img;
import be.esthoril.gfx.Room;

public class Item {

	public static int HEART = 0, GOLD = 1, MANA = 2;
	private int type;
	private int x, y;
	
	public Item(int type, int x, int y) {
		this.type = type;
		this.x = x;
		this.y = y;
	}
	
	public void draw(Graphics g) {
		g.drawImage(Img.item[type],	x + Room.xOffset, y + Room.yOffset, 32, 32, null);
		
		if(Screen.debug) {
			g.drawRect(getCollisionBox().x,
					getCollisionBox().y,
					getCollisionBox().width,
					getCollisionBox().height);
		}
	}
	
	public Rectangle getCollisionBox() {
		Rectangle itemBox = new Rectangle(x + Room.xOffset + 3, y + Room.yOffset + 3, 26, 26);
		return itemBox;
	}

	public int getType() {
		return type;
	}

}
