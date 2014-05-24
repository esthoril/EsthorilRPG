package be.esthoril.gfx;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

public class Text {

	public static final Color light = new Color(223,206,180);
	public static final Color mid = new Color(154,137,108);
	public static final Color dark = new Color(26,21,18);
	public static final Color thisRed = new Color(220, 0, 0);
	public static final Color thisBlue = new Color(140, 40, 220);
	public static final Color backgr = new Color(50,50,50,220);
	public static final Color basic = new Color(200,200,200);

	public static final Font font = new Font("", Font.BOLD, 24);
	
	public static void drawText(Graphics g, String text, Color c, int x, int y) {
		
		g.setFont(font);
		g.setColor(c.darker());
		g.drawString(text, x+1, y+1);
		g.setColor(c);
		g.drawString(text, x, y);
		
	}

	public static void drawBox(Graphics g, int x, int y, int width, int height) {
		
		g.setColor(Text.dark);
		g.drawRect(x - 1,y - 1,width - 1,height - 1);
		g.setColor(Text.light);
		g.drawRect(x, y, width - 3, height - 3);
		g.drawRect(x + 1, y + 1, width - 5, height - 5);
		g.setColor(Text.mid);
		g.drawRect(x + 2, y + 2, width - 7, height - 7);
		g.setColor(Text.backgr);
		g.fillRect(x + 3, y + 3, width - 8, height - 8);
		
	}
}
