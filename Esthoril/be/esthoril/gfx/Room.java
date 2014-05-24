package be.esthoril.gfx;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;

import be.esthoril.game.Door;
import be.esthoril.game.Screen;
import be.esthoril.readLevel.MapType;

public class Room {
	
	public static Rectangle PF;
	
	public static Rectangle horBox;
	public static Rectangle vertBox;
	public int boxHeight = 10; //22;
	public int boxWidth = 10; //30;
	
	public static final int itemBar = 100;
	public static final int topBorder = 25;
	
	public static boolean inWorld = true;
	
	public static int[][][] tile = new int[2][100][100]; // 12x7 tile grid
	
	@SuppressWarnings("unused")
	private static final int OVERWORLD = 0,OBJECT = 1, ITEM = 2;
	@SuppressWarnings("unused")
	private static final int LEFT = 0, DOWN = 1, RIGHT = 2, UP = 3;
	
	/* map on which door is, x,y, ID of door
	 * load this map, connect to this door, spawn on side of THIS door
	 */
	public static final Door[] door = {
		new Door(MapType.OVERWORLD,		9, 7, 0,	MapType.HOUSE1, 2, DOWN),
		new Door(MapType.OVERWORLD,		1, 18, 1,	MapType.DUN1, 3, RIGHT),
		new Door(MapType.HOUSE1,		3, 21, 2, 	MapType.OVERWORLD, 0, UP),
		new Door(MapType.DUN1,			3, 9, 3, 	MapType.OVERWORLD, 1, RIGHT),
		new Door(MapType.OVERWORLD,		32, 10, 4, 	MapType.SHOP1, 5, DOWN),
		new Door(MapType.SHOP1,			12, 12, 5, 	MapType.OVERWORLD, 4, UP)};
	
	public static int currentDoor = -1;
		
	// offSets for room scrolling
	public static int yOffset=0, xOffset=0;
	public static int yOffsetMax, xOffsetMax;
	
	public static final Dimension playingfield = new Dimension(768, 512);
	public static final Dimension totalScreen = new Dimension(792,670);
		
	public Point mseStart = new Point(0,0);
	
	public int tileX, tileY;

	public Room(){
		
		// put inside walls
		PF = new Rectangle(1,topBorder+itemBar,playingfield.width,playingfield.height);
		vertBox = new Rectangle(1+(playingfield.width-boxWidth)/2, topBorder+itemBar, boxWidth, playingfield.height);		
		horBox = new Rectangle(1, topBorder+itemBar+(playingfield.height-boxHeight)/2, playingfield.width, boxHeight);

	}
	
	public void define() {

		yOffsetMax = - Math.max(0,Screen.mapHeight-16)*32 ;
		xOffsetMax = - Math.max(0,Screen.mapWidth-24)*32;
		System.out.println("max offsets: "+xOffsetMax+" , "+yOffsetMax);
		//defineDoorBoxes();
	}
	
	public void tick() {

	}

	public void draw(Graphics g) {
		if(Screen.debug) {drawVisualAidBoxes(g);}
		if(Screen.showGrid) {drawGrid(g);}
		
	}
	
	private void drawVisualAidBoxes(Graphics g) {
		
		// playing field
		g.setColor(Color.PINK);
		g.drawRect(PF.x-1, PF.y-1, PF.width+1, PF.height+1);
		
		//scrollBoxes
		g.setColor(new Color(255,0,0,100));
		g.fillRect(horBox.x,horBox.y,horBox.width,horBox.height); //RED
		g.setColor(new Color(0,0,255,100));
		g.fillRect(vertBox.x,vertBox.y,vertBox.width,vertBox.height); //BLUE
		
		// doorboxes
		for(int i=0;i<door.length;i++) {
			if(door[i].myMap.getName() == Screen.thisMap.getName()) {
				g.fillRect(Room.xOffset + door[i].x * Img.tileSize + 12,
						Room.yOffset + door[i].y * Img.tileSize + Room.itemBar + Room.topBorder - 2,
						Img.tileSize - 24,
						Img.tileSize + 2);
				g.fillRect(Room.xOffset + door[i].x * Img.tileSize - 2,
						Room.yOffset + door[i].y * Img.tileSize + Room.itemBar + Room.topBorder + 10,
						Img.tileSize + 2,
						Img.tileSize - 14);
			}
		}
	}
	private void drawGrid(Graphics g) {

		g.setFont(new Font("Courier", Font.PLAIN, 10));
		g.setColor(Color.WHITE);
		int xO = -Room.xOffset/32; // amount of tiles that are offset
		int yO = -Room.yOffset/32;		
		for(int y=0+yO;y<Screen.mapHeight+yO;y++) {
			for(int x=0+xO;x<Screen.mapWidth+xO;x++) {
				g.drawRect(x*32+Room.xOffset,y*32+Room.yOffset+Room.itemBar + Room.topBorder-2,32,32);
				g.drawString(""+x,
						Room.xOffset + x * Img.tileSize + 4,
						Room.yOffset + y * Img.tileSize + Room.itemBar + Room.topBorder + 12);
				g.drawString(""+y,
						Room.xOffset + x * Img.tileSize + 4,
						Room.yOffset + y * Img.tileSize + Room.itemBar + Room.topBorder + 20);
			}
		}
	}
}
