package be.esthoril.readLevel;

import java.io.*;
import java.util.Scanner;

import be.esthoril.game.Screen;
import be.esthoril.gfx.WorldMap;

public class Load {
	
	public void loadDimension(File loadPath) {
		
		try {
			
			Scanner loadScanner = new Scanner(loadPath);
			Screen.mapHeight =	loadScanner.nextInt();
			Screen.mapWidth = loadScanner.nextInt();
			
			loadScanner.close();
			
		} catch(Exception e) {
			
		}
	}

	public void loadGridTxt(WorldMap map, File loadPath) {

		try {
			Scanner loadScanner = new Scanner(loadPath);
			
			while(loadScanner.hasNext()) {
			
				map.height = loadScanner.nextInt();
				map.width = loadScanner.nextInt();
				
				// loading set + setting spot to filled
				for(int y=0 ; y<map.height ; y++) {
					for(int x=0 ; x<map.width ; x++) {
						map.grid[y][x].setSet(loadScanner.nextInt());
						map.grid[y][x].isFilled = true;
					}
				}
				
				// loading x coord
				for(int y=0 ; y<map.height ; y++) {
					for(int x=0 ; x<map.width ; x++) {
						map.grid[y][x].x = loadScanner.nextInt();
					}
				}
				
				// loading y coord
				for(int y=0 ; y<map.height ; y++) {
					for(int x=0 ; x<map.width ; x++) {
						map.grid[y][x].y = loadScanner.nextInt();
					}
				}
			}
			loadScanner.close();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	
	public void loadGridSer(String filename) {
		//clearGrid();
		
		try {
			ObjectInputStream is = new ObjectInputStream(new FileInputStream("worlds/"+filename));
			WorldMap grid0 = (WorldMap) is.readObject();
			WorldMap grid1 = (WorldMap) is.readObject();
			WorldMap grid2 = (WorldMap) is.readObject();
			is.close();
						
			Screen.world[0] = grid0;
			Screen.world[1] = grid1;
			Screen.world[2] = grid2;
		} catch(Exception ex) {
			ex.printStackTrace();
		}
		
		// setting offsets for this map
		Screen.room.define();
	}
	
}
