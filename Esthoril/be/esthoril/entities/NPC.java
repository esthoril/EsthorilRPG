package be.esthoril.entities;

import java.awt.Graphics;
import java.awt.Point;
import java.awt.Polygon;
import java.awt.Rectangle;

import be.esthoril.game.Screen;
import be.esthoril.gfx.Img;
import be.esthoril.gfx.Room;
import be.esthoril.gfx.SpeechBubble;

public class NPC extends Entity {

	public int			x, y;
	public String		name;

	private Point			start = new Point();
	private Point			nextPoint = new Point();
	private int				atPoint, toPoint;
	private SpeechBubble	bubble;
	
	protected boolean		isColliding = false;
	private int walkSpeed = 16, walkFrame = 0;
	private int talkSpeed = 40, talkFrame = 0;		// wait some loops before he can talk again
													// after not colliding anymore

	protected int[]		walkOrder = {0,1,2,1};	// to make move animation from 3 pics
	protected int		pic;
	protected String	myMap;
	protected Polygon	poly;
	
	// constructor used by class Mob
	public NPC(int[] p,	String myMap) {

		this.myMap = myMap;
		init(p);

	}
	
	// constructor used by shopkeeper
	public NPC(String name, int[] p, int pic) {

		this.name = name;
		this.pic = pic;
		init(p);

	}
	
	public NPC(String name, int[] p, int pic, SpeechBubble bubble) {

		this.name = name;
		this.pic = pic;
		this.bubble = bubble;
		init(p);
	}
	
	public SpeechBubble getSpeech() {
		return bubble;
	}
	
	// initializing starting position in pixels from given gridpoints
	private void init(int[] p) {
		
		start.x = p[0] * 32;
		start.y = p[1] * 32 + Room.itemBar + Room.topBorder;
		
		x = start.x ;
		y = start.y ;
		
		// even amount of ints means amount/2 points to move
		// odd amount (should be 3) means starting point + special moveAI
		if(p.length % 2 == 0) {
			poly = new Polygon();
			for(int i=0;i<p.length/2;i++) {
				poly.addPoint(p[i*2], p[i*2+1]);
			}
		}
		if(poly.npoints>1) {
			nextPoint.x = poly.xpoints[1] * 32;
			nextPoint.y = poly.ypoints[1] * 32 + Room.itemBar + Room.topBorder;
			toPoint = 1;
		}
	}

	public Rectangle getCollisionBox(int xD, int yD, int wD, int hD) {
		Rectangle NPCBox = new Rectangle(x + 1 + Room.xOffset + xD,
				y + 17 + Room.yOffset + yD,
				30 + wD,
				30 + hD);
		return NPCBox;
	}
	
	public void setColliding(boolean col) {
		isColliding = col;
	}
	
	public boolean getColliding() {
		return isColliding;
	}

	public void physic() {
		
		if(!isColliding) {
			if(poly.npoints>1) {
				checkMotion();
				move();}
			else {
				turn();
			}
		} else if(Screen.player.NPCCollide(-1,-1,+2,+2) < 0) {
			if(talkFrame > talkSpeed) {
				Screen.textBubble = true;
				isColliding = false;
				talkFrame = 0;
			} else {
				talkFrame += 1;
			}
		}
	}
	
	protected void move() {

		if(nextPoint.x > start.x) { x += 1; movingDir = RIGHT;}
		if(nextPoint.x < start.x) { x -= 1; movingDir = LEFT;}
		if(nextPoint.y > start.y) { y += 1; movingDir = DOWN;}
		if(nextPoint.y < start.y) { y -= 1; movingDir = UP;}
		
		if(x == nextPoint.x && y == nextPoint.y) {
			atPoint = toPoint;
			toPoint += 1;
			if(atPoint > poly.npoints-1) {atPoint = 0;}
			if(toPoint > poly.npoints-1) {toPoint = 0;}
			start.x = poly.xpoints[atPoint] * 32;
			start.y = poly.ypoints[atPoint] * 32 + Room.itemBar + Room.topBorder;;
			nextPoint.x = poly.xpoints[toPoint] * 32;
			nextPoint.y = poly.ypoints[toPoint] * 32 + Room.itemBar + Room.topBorder;
		}
	}

	protected void turn() {

		int xT = - x + Screen.player.x + Room.xOffset;
		int yT = y - Screen.player.y + Room.yOffset + 14;
		
		if(Math.abs(xT) > Math.abs(yT)) {
			if(xT > 0) {movingDir = RIGHT;}
			if(xT < 0) {movingDir = LEFT;}
		} else if(Math.abs(xT) < Math.abs(yT)) {
			if(yT > 0) {movingDir = UP;}
			if(yT < 0) {movingDir = DOWN;}			
		}	
	}
		
	protected int motion;	
	protected void checkMotion() {
		if(walkFrame > walkSpeed) {
			if(motion < walkOrder.length-1){ 
				motion++;
			} else {
				motion=0;
			}
			walkFrame = 0;
		} else  {
			walkFrame += 1;
		}
	}
	
	public void draw(Graphics g) {
		if(isColliding || poly.npoints == 1) {
			g.drawImage(Img.NPCimg[movingDir + pic * 4][1],
					Room.xOffset+x,
					Room.yOffset+y,
					32,
					48,
					null);
		} else {
			g.drawImage(Img.NPCimg[movingDir + pic * 4][walkOrder[motion]],
					Room.xOffset+x,
					Room.yOffset+y,
					32,
					48,
					null);
		}
		
		if(Screen.debug) {
			g.drawRect(getCollisionBox(0,0,0,0).x,
					getCollisionBox(0,0,0,0).y,
					getCollisionBox(0,0,0,0).width,
					getCollisionBox(0,0,0,0).height);
		}
		
	}

}
