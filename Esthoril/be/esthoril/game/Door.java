package be.esthoril.game;

import java.awt.Rectangle;

import be.esthoril.gfx.Img;
import be.esthoril.gfx.Room;
import be.esthoril.readLevel.MapType;

public class Door {

	public int x;
	public int y;
	public int connect;
	public MapType toMap;
	public MapType myMap;
	public int ID;
	private int spawnDir;

	public Door(MapType myMap, int x, int y, int ID, MapType toMap, int connect, int spawnDir) {
		this.x = x;
		this.y = y;
		this.connect = connect;
		this.toMap = toMap;
		this.myMap = myMap;
		this.ID = ID;
		this.spawnDir = spawnDir;
	}
	
	public int getDir() {
		return spawnDir;
	}
	
	public Rectangle getDoorBoxV() {
		return new Rectangle(Room.xOffset + x * Img.tileSize + 12,
				Room.yOffset + y * Img.tileSize + Room.itemBar + Room.topBorder,
				Img.tileSize - 24,
				Img.tileSize);
	}
	
	public Rectangle getDoorBoxH() {
		return new Rectangle(Room.xOffset + x * Img.tileSize - 2,
				Room.yOffset + y * Img.tileSize + Room.itemBar + Room.topBorder + 10,
				Img.tileSize + 2,
				Img.tileSize - 14);
	}
}
