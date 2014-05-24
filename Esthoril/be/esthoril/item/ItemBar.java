package be.esthoril.item;

import java.awt.Color;
import java.awt.Graphics;

import be.esthoril.game.Screen;
import be.esthoril.gfx.Img;
import be.esthoril.gfx.Room;
import be.esthoril.gfx.Text;

public class ItemBar {

	private int separator = 500;
	private int barX = 590, barY = 66;

	public ItemBar() {
		
	}
	
	public void draw(Graphics g) {

		g.setColor(Color.BLACK);
		g.fillRect(0,Room.topBorder,Room.playingfield.width, Room.itemBar-2);
		
		Text.drawBox(g, 1, Room.topBorder, separator, Room.itemBar);
		Text.drawBox(g, 1 + separator, Room.topBorder, Room.playingfield.width - separator, Room.itemBar);
		
		drawXP(g);
		
		drawBar(g, "Health", Text.thisRed, barY, Screen.player.currentHealth, Screen.player.maxHealth);
		drawBar(g, "Mana", Text.thisBlue, barY + 26, Screen.player.currentMana, Screen.player.maxMana);
		
	}


	
	public void drawXP(Graphics g) {
		
		int level = Screen.player.nextLevel;
		int xp = Screen.player.currentXP, toLevel = Screen.player.xpToLevel[level];
		
		Text.drawText(g, "Exp:  ", Text.basic, barX - 80, barY - 7);
		Text.drawText(g, xp + " / " + toLevel, Text.basic, barX - 1, barY - 7);
	}
	
	public void drawBar(Graphics g, String type, Color color, int y, int amount, int max) {
		
		Text.drawText(g, type, color, barX - 80, y + 20);

		int height = 20, width = 12, SHADE_WIDTH = 2;

		for(int i=0;i<amount;i++) {
			g.setColor(color);
			g.fillRect(barX+14*i, y , width, height);
			
	        g.setColor(color.darker());
	        g.fillRect(barX+14*i, y + height - SHADE_WIDTH, width, SHADE_WIDTH);
	        g.fillRect(barX+14*i + width - SHADE_WIDTH, y, SHADE_WIDTH, height);
	        
	        g.setColor(color.brighter());
	        for(int j = 0; j < SHADE_WIDTH; j++) {
	        	g.drawLine(barX+14*i, y + j, barX + width - j - 1+14*i, y + j);
	        	g.drawLine(barX+14*i + j, y, barX + j+14*i, y + height - j - 1);
	        }
		}
		
		g.setColor(Text.basic);
		for(int i=amount;i<max;i++) {
			g.fillRect(barX+14*i , y, width, height);
			
		g.drawImage(Img.item[Item.GOLD],barX - 190, barY - 8 + Room.topBorder, 32 ,32, null);
		g.drawString(": " + Screen.player.money, barX - 158, barY + 21 + Room.topBorder);
			
		}

	}	
}
