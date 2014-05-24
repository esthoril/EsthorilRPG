package be.esthoril.game;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.io.File;
import java.util.ArrayList;

import javax.swing.JPanel;

import be.esthoril.entities.Mob;
import be.esthoril.entities.MobType;
import be.esthoril.entities.NPC;
import be.esthoril.entities.Player;
import be.esthoril.entities.ShopKeeper;
import be.esthoril.gfx.Img;
import be.esthoril.gfx.Room;
import be.esthoril.gfx.WorldMap;
import be.esthoril.item.Item;
import be.esthoril.item.ItemBar;
import be.esthoril.item.Shop;
import be.esthoril.readLevel.Load;
import be.esthoril.readLevel.MapType;


public class Screen extends JPanel implements Runnable {
	
	
	// draw aid boxes 
	// ***************************************************
	
	public static boolean debug = false;
	public static boolean showGrid = false;
	
	// ***************************************************
	
	private static final long serialVersionUID = 1L;
	
	private static final int OVERWORLD = 0, OBJECT = 1, ITEM = 2;
	private static final int LEFT = 0, DOWN = 1, RIGHT = 2, UP = 3;
	
	public static int mapWidth, mapHeight;
	
	public static int xBorder = 10;
	public static int yBorder = 124;

	public static Point			mse = new Point(0, 0);
	public static Point			mseRel = new Point(0,0);
	
	public static boolean up, down, left, right;
	public static boolean textBubble = true;
	
	public static ArrayList<Mob>	mob = new ArrayList<Mob>();
	public static ArrayList<NPC>	chars = new ArrayList<NPC>();
	public static ArrayList<Item>	item = new ArrayList<Item>();
	
	private boolean					running = false;
	private Thread					gameLoop;
	private int totalTicks;
	
	public static WorldMap[]		world = new WorldMap[3];
	public static MapType			thisMap;
	public static Shop				thisShop;
	
	public static Load				load;
	public static Room				room;
	public static Player			player;
	public static ItemBar			itembar;
	private static Img				img;
		
	public Screen(Frame frame){

		frame.addMouseListener(new MouseHandel());
		frame.addMouseMotionListener(new MouseHandel());
		frame.addKeyListener(new KeyHandel());
		
		// initializing everythihg, then start the gameloop
		init();
		start();
	
	}
	
	private void init() {
		
		img = new Img();
		img.init();
		load = new Load();
		room = new Room();
		itembar = new ItemBar();
		
		player = new Player("Zelda", 3, 7);

		mob.add(new Mob(MobType.DARKREDBAT, new int[] {7,7, 11,7}, "dun1"));
		mob.add(new Mob(MobType.DARKREDBAT, new int[] {7,8, 11,8}, "dun1"));
		mob.add(new Mob(MobType.DARKREDBAT, new int[] {6,6, 6,9}, "dun1"));
		mob.add(new Mob(MobType.DARKREDBAT, new int[] {12,8, 12,10}, "dun1"));
		mob.add(new Mob(MobType.BLUEKNIGHT, new int[] {17,13, 20,13}, "world"));

//		thisMap = MapType.SHOP1;
		thisMap = MapType.OVERWORLD;
		
		initWorld(thisMap.getName());

		Screen.room.define();

	}
	
	private void initWorld(String filename) {
			
		load.loadDimension(new File("worlds/"+filename+"world.txt"));
		
		for(int i=0;i<3;i++) {
//			world[i] = new WorldMap(mapWidth, mapHeight);
			world[i] = new WorldMap(50, 50);
		}

		load.loadGridTxt(world[OVERWORLD], new File("worlds/"+filename+"world.txt"));
		load.loadGridTxt(world[OBJECT], new File("worlds/"+filename+"object.txt"));
		load.loadGridTxt(world[ITEM], new File("worlds/"+filename+"item.txt"));
		
		for(int i=0;i<thisMap.getChars().length;i++) {
			chars.add(thisMap.getChars()[i]);
		}
	}
	
	public static void loadMap(MapType map) {
		
		load.loadDimension(new File("worlds/" + map.getName() + "world.txt"));
		thisMap = map;
		item.clear();
		chars.clear();
		
		if(map.getType() == 2) {
			Shop.exitShop = false;
			Shop.selectedItem = 0;
		}

		System.out.println(mapWidth+" "+mapHeight);

		load.loadGridTxt(world[OVERWORLD], new File("worlds/" + map.getName() + "world.txt"));
		load.loadGridTxt(world[OBJECT], new File("worlds/" + map.getName() + "object.txt"));
		load.loadGridTxt(world[ITEM], new File("worlds/" + map.getName() + "item.txt"));


		for(int i=0;i<map.getChars().length;i++) {
			chars.add(map.getChars()[i]);
		}

		int connect = Room.door[Room.currentDoor].connect; // connect to this door
		Door door = Room.door[connect];

		// rest offsets
		Room.xOffset = 0;
		Room.yOffset = 0;

		room.define(); // set xoffset and yoffset correct for this mapsize
		int l = mapHeight - 8; // no idea why it needs to be 8 but it works..
		if(door.y > 16) {Room.yOffset = -(door.y-l)*32;}
		if(door.x > 24) {Room.xOffset = -(door.x+3-24)*32;}

		player.x = door.x * 32 + Room.xOffset;
		player.y = door.y * 32 + Room.topBorder + Room.itemBar + Room.yOffset;

		// position player next to the door
		if(door.getDir() == UP) {player.y -= 32;}
		if(door.getDir() == DOWN) {player.y += 10;}
		if(door.getDir() == LEFT) {player.x -= 32;}
		if(door.getDir() == RIGHT) {player.x += 36;}

	}

	/*
	 * betere methode voor verzinnen, die verschillende snelheden
	 * nanosecs liever niet /120 zoals nu, maar gewoon /60 
	 */
	private void tick() {
		
		if((totalTicks % 3 == 0) && !player.isDead &&
									!player.inShop &&
									!player.checkItems) { player.physic();}
		
		for(int i=0;i<chars.size();i++) {
			if(totalTicks % 4 == 0) {
				chars.get(i).physic();
			}
		}
		
		for(int i=0;i<mob.size();i++) {
			if(totalTicks % 4 == 0) { mob.get(i).physic();}
		}
		
		for(int i=0;i<mob.size();i++) {
			if(mob.get(i).died){
				mob.get(i).getReward();
				mob.remove(i);}
		}
	}
	
	
	
	public void paintComponent(Graphics g) {

		g.setColor(new Color(5,5,5));
		g.fillRect(0,24,800,620);

		for(int i=0;i<3;i++) {
			world[i].draw(g);
		}
		
		room.draw(g);
		itembar.draw(g);

		for(int i=0;i<mob.size();i++) {
			if(thisMap.getName() == mob.get(i).getMyMap()) {
				mob.get(i).draw(g);
			}
		}

		player.draw(g);

		// if normal NPC draw speechbubble
		// else if shopkeeper and not done yet, draw shop
		// else don't draw shop again (until it gets reset by reentering the shop)
		
		for(int i=0;i<chars.size();i++) {
			
			chars.get(i).draw(g);
			if((chars.get(i)).getColliding()) {
				if(!chars.get(i).getClass().toString().equals("class entities.ShopKeeper")) {
					chars.get(i).getSpeech().draw(g, chars.get(i).x, chars.get(i).y);
				} else if(!Shop.exitShop){
					player.inShop = true;
					thisShop = ((ShopKeeper) chars.get(i)).getShop();
				} else {
					player.inShop = false;
				}
			}
		}
		
		if(player.inShop) { thisShop.draw(g);}
		
		
		for(int i=0;i<item.size();i++) {
			item.get(i).draw(g);
		}
	}





	private synchronized void start() {
		running = true;
		gameLoop = new Thread(this);
		gameLoop.start();
	}

	@SuppressWarnings("unused")
	private synchronized void stop() {
		running = false;
		if(gameLoop != null) {
			try {
				gameLoop.join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	public void run() {
		long lastTime = System.nanoTime();
		double nsPerTick = 1000000000D/240D;
		
		long lastTimer = System.currentTimeMillis();
		double delta = 0;

		while(running) {
			long now = System.nanoTime();
			delta += (now - lastTime) / nsPerTick;
			lastTime = now;
			boolean shouldRender = true;
			
			while(delta >= 1) {
				delta -= 1;
				shouldRender = true;
				totalTicks++;
				tick();
			}
			try {
				Thread.sleep(2);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			if(shouldRender) {
//				System.out.println(mse);
				repaint();
			}
			
			if(System.currentTimeMillis() - lastTimer >= 1000) {
				lastTimer += 1000;
			}
		}
	}
	
	
}
