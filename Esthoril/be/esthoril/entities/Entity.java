package be.esthoril.entities;

import java.awt.Graphics;
import java.awt.Rectangle;

import be.esthoril.game.Screen;
import be.esthoril.gfx.Img;
import be.esthoril.gfx.Room;

public abstract class Entity {

	public int x, y;
	public int movingDir = 1;
	protected final int DOWN = 0, LEFT = 1, RIGHT = 2, UP = 3;
	private Rectangle tileBox;
	
	public Entity() {

	}
	
	// parameters so i can set different size for player on mob and mob on mob colliding
	public boolean mobCollide(int x, int y, int w, int h) {

		boolean collide = false;
		Rectangle box = new Rectangle(getCollisionBox(x, y, w, h));
		
		for(int i=0;i<Screen.mob.size();i++) {
			if(Screen.mob.get(i).myMap == Screen.thisMap.getName()) {
				if(Screen.mob.get(i).getCollisionBox().intersects(box)) {
					collide = true;
					break;
				}
			}
		}
		return collide;
	}	

	public boolean tileCollide(int dir) {
		
		boolean collide = false;
		Rectangle box = getCollisionBox(0,0,0,0);
		
		if(dir == LEFT) { box.x -= 1;}
		if(dir == UP) { box.y -= 1;}
		if(dir == RIGHT) { box.x += 1;}
		if(dir == DOWN) { box.y += 1;}

		outer:
		for(int y = 0 ; y < Screen.mapHeight ; y++){
			for(int x = 0 ; x < Screen.mapWidth ; x++){
				if(Screen.world[1].grid[y][x].x > 0 ||
						Screen.world[1].grid[y][x].y > 0 ||
						Screen.world[1].grid[y][x].getSet() > 0) {
					tileBox = new Rectangle(Room.xOffset + x * Img.tileSize,
											Room.yOffset + y * Img.tileSize + Room.itemBar + Room.topBorder,
											Img.tileSize,
											Img.tileSize);
					if(box.intersects(tileBox)) {
						collide = true;
						break outer;
					}
				}
			}
		}
		return collide;
	}

	public abstract Rectangle getCollisionBox(int xD, int yD, int wD, int hD);
	public abstract void physic();
	public abstract void draw(Graphics g);

}
