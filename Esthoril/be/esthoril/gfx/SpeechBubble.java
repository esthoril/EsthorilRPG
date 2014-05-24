package be.esthoril.gfx;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Rectangle;

public enum SpeechBubble {

	BUBBLE1("My hair is Green.."),
	BUBBLE2("Hello world!"),
	BUBBLE3("Welcome to my shop");
	
	private String text;
	



	private Font font = new Font("Courier", Font.BOLD, 16);
	private Color textdark = new Color(25,21,18);
	private Color textlight = new Color(223,206,180);
	
	private Rectangle b; // short for bubble :)
	
	
	
	private SpeechBubble(String text) {
		this.text = text;
	}
	
	public String getText() {
		return text;
	}
	
	public void draw(Graphics g, int x, int y) {
		
		int l = text.length();

		b = new Rectangle(x - 80, y - 70, l * 11 + 12, 50); // width en height moet afhankelijk van tekstlengte worden
		int bX = b.x + Room.xOffset;
		int bY = b.y + Room.yOffset;
		
		g.drawImage(Img.talk[0],bX + 80, bY - 12 + b.height, 32,32,null);
		
		g.setColor(Text.dark);
		g.drawLine(bX - 2, bY - 3, bX + 1 + b.width, bY - 3);
		g.drawLine(bX - 2, bY + 2 + b.height, bX + 80, bY + 2 + b.height);
		g.drawLine(bX + 110, bY + 2 + b.height, bX + 1 + b.width, bY + 2 + b.height);
		g.drawLine(bX - 3, bY - 2, bX - 3, bY + 1 + b.height);
		g.drawLine(bX + 2 + b.width, bY - 2, bX + 2 + b.width, bY + 1 + b.height);

		g.setColor(Text.light);
		g.drawRect(bX - 2, bY - 2, b.width + 3, b.height + 3);

		g.setColor(Text.mid);
		g.drawRect(bX - 1, bY - 1, b.width + 1, b.height + 1);

		g.setColor(Text.backgr);
		g.fillRect(bX, bY, b.width, b.height);

		drawText(g, text, b.x, b.y);
		
	}
	
	private void drawText(Graphics g, String text, int x, int y) {
		
		g.setFont(font);
		g.setColor(textdark);
		g.drawString(text, x + 11 + Room.xOffset, y + 21 + Room.yOffset);
		g.setColor(textlight);
		g.drawString(text, x + 10 + Room.xOffset, y + 20 + Room.yOffset);
	}
}
