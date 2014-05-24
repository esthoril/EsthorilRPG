package be.esthoril.gfx;

import java.awt.Graphics;
import java.awt.Point;
import java.io.Serializable;

import be.esthoril.game.Screen;
import be.esthoril.readLevel.GridTile;

public class WorldMap implements Serializable{
	
	private static final long serialVersionUID = 1L;

	private boolean isVisible;
	private boolean isActive;
	
	public int width, height;

	public GridTile grid[][];
	public boolean isFilled[][];

	
	public WorldMap(int height, int width) {
		this.width = width;
		this.height = height;
		grid = new GridTile[this.height][this.width];
		init();
	}
	
	public void init() {
		isVisible = true;
		isActive = false;
		for(int y=0;y<this.height;y++) {
			for(int x=0;x<this.width;x++) {
				grid[y][x] = new GridTile(0, 0, 0, false);
			}
		}
	}

	public void toggleVisible() {
		if(isVisible) {
			isVisible = false;
		} else {
			isVisible = true;
		}
	}
	
	public void setVisible(boolean status) {
		isVisible = status;
	}
	
	public void setActive(boolean status) {
		isActive = status;
	}
	
	public boolean isVisible() {
		return isVisible;
	}
	
	public boolean isActive() {
		return isActive;
	}
	
	public Point getMin() {
		Point min = new Point(200,200);
		for(int y=0;y<this.height;y++) {
			for(int x=0;x<this.width;x++) {
				if(grid[y][x].isFilled) {
					if(y < min.y) {min.y = y;}
					if(x < min.x) {min.x = x;}
				}
			}
		}
		return min;
	}
	
	public Point getMax() {
		Point max = new Point(0,0);
		for(int y=0;y<this.height;y++) {
			for(int x=0;x<this.width;x++) {
				if(grid[y][x].isFilled) {
					if(y > max.y) {max.y = y;}
					if(x > max.x) {max.x = x;}
				}
			}
		}
		return max;
	}
	
	public void draw(Graphics g) {
		
		int xO = -Room.xOffset/32; // amount of tiles that are offset
		int yO = -Room.yOffset/32;
		
		for(int y=0+yO;y<height+yO-1;y++) { //no idea why i need the -5 for now... but it works
			for(int x=0+xO;x<width+xO;x++) {
				/*
				 * instead of grid[y][x].isfilled. else objecgrid overrides
				 * worldgrid with generic tile on empty spots
				 */
				if(grid[y][x].x > 0 || grid[y][x].y > 0 || grid[y][x].getSet() > 0) { 
					g.drawImage(Img.tiles[grid[y][x].getSet()][grid[y][x].y][grid[y][x].x],
							Screen.xBorder - 10 + Img.tileSize * x + Room.xOffset,
							Screen.yBorder + Img.tileSize * y + Room.yOffset,
							null);
				}
			}
		}
	}
}