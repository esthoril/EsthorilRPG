package be.esthoril.entities;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.io.File;

import be.esthoril.game.Screen;
import be.esthoril.gfx.Img;
import be.esthoril.gfx.Room;
import be.esthoril.item.Item;

@SuppressWarnings("unused")
public class Player extends Entity{

	public int					x, y;
	public						String name;
	
	public boolean				isMoving = false;
	public boolean				isFighting = false;
	public boolean				inShop = false;
	public boolean				checkItems = false;
	public boolean				isDead = false;

	public int					currentHealth;
	public int					currentMana;
	public int					currentXP;
	public int[]				xpToLevel = {0,1000,2500,5000,10000};
	public int					nextLevel = 1;
	public final int			maxHealth = 12;
	public final int			maxMana = 10;
	public int					money = 100;
	public int					keys;
	
	// speeds for animation
	private int					motionSpeed = 70, motionFrame = 0;
	private int					fightSpeed = 40, fightFrame = 0;
	private int					motion;
	
	public Player(String name, int x, int y) {

		this.x = x*32;
		this.y = y*32 + Room.topBorder + Room.itemBar;
		this.name = name;
		currentHealth = 12;
		currentMana = 5;
	}

	public Rectangle getCollisionBox(int xD, int yD, int wD, int hD) {
		Rectangle playerBox = new Rectangle(x + 5 + xD, y + 22 + yD, 24 + wD, 6 + hD);
		return playerBox;
	}
	
	public Rectangle getWeaponBox() {
		int width = 24, height = 8; // width and height for left/right. switch it when moving up/down
		Rectangle weaponBox = new Rectangle(x,y,width, height);
		
		if(movingDir == UP || movingDir == DOWN) {
			weaponBox.width = 8;
			weaponBox.height = 24; 
		}
		if(movingDir == UP ) {
			weaponBox.x += 8;
			weaponBox.y -= 22;
		}
		if(movingDir == LEFT) {
			weaponBox.x -= 22;
			weaponBox.y += 14;
		}
		if(movingDir == DOWN) {
			weaponBox.x += 12; 
			weaponBox.y += 30;
		}
		if(movingDir == RIGHT) {
			weaponBox.x += 30;
			weaponBox.y += 12;
		}
		return weaponBox;
	}

	public boolean isFighting() {
		return isFighting;
	}
	
	
	
	// player movement
	
	public boolean horScroll = false, vertScroll = false;
	public void physic() {
		
		if(!isFighting) {
			if(Screen.left || Screen.up || Screen.right || Screen.down) {
				isMoving = true;
			}
	
			Rectangle box = getCollisionBox(0,0,0,0);
			Point playerPosition = new Point(box.x + box.width/2, box.y + box.height/2); // more or less middle of the box
			
			// check room scrolling			
			if(Room.vertBox.contains(playerPosition)) {
				horScroll = true;
			} else {
				horScroll = false;
			}
			if(Room.horBox.contains(playerPosition)) {
				vertScroll = true;
			} else {
				vertScroll = false;
			}			
				// check actual movement
			if(Screen.up){
				movingDir = UP;
				box.y -= 1;
				if(Room.PF.contains(box) && !tileCollide(UP)) {
					if(!vertScroll || Room.yOffset == 0) {
						y -= 1;
					} else {
						Room.yOffset += 1;
					}
				}
			}
			if(Screen.down){
				movingDir = DOWN;
				box.y += 1;
				if(Room.PF.contains(box) && !tileCollide(DOWN)) {
					if(!vertScroll || Room.yOffset == Room.yOffsetMax){
						y += 1;
					} else {
						Room.yOffset -= 1;
					}
				}
			}
			if(Screen.right){
				movingDir = RIGHT;
				box.x += 1;
				if(Room.PF.contains(box) && !tileCollide(RIGHT)) {
					if(!horScroll || Room.xOffset == Room.xOffsetMax) {
						x += 1;
					} else {
						Room.xOffset -= 1;
					}
				}
			}
			if(Screen.left){
				movingDir = LEFT;
				box.x -= 1;
				if(Room.PF.contains(box) && !tileCollide(LEFT)) {
					if(!horScroll || Room.xOffset == 0){
						x -= 1;
					} else {
						Room.xOffset += 1;
					}
				}
			}
	
			Room.currentDoor = doorCollide(-1,-1,+2,+2);
			if(Room.currentDoor > -1){
				System.out.println(Room.door[Room.currentDoor].toMap.getName());
				Screen.loadMap(Room.door[Room.currentDoor].toMap);
			}
			
			int thisNPC = NPCCollide(-1,-1,+2,+2);
			if(thisNPC > -1) {
				Screen.chars.get(thisNPC).setColliding(true);
			} else {
				Screen.textBubble = false;
			}
			
			int thisItem = itemCollide();
			if(thisItem > -1) {
				if(Screen.item.get(thisItem).getType() == Item.GOLD){
					money += 1;
				} else if(Screen.item.get(thisItem).getType() == Item.HEART) {
					if(currentHealth < maxHealth) {
						currentHealth += 1;
					}
				}
				Screen.item.remove(thisItem);
					
			}
			
		// fighting
		} else {

			int hit = weaponCollide();
			if(hit > -1 && !Screen.mob.get(hit).isHit) {
				Screen.mob.get(hit).currentHealth -= 1;
				Screen.mob.get(hit).isHit = true;
			}
		}

		if(mobCollide(-1,-18,+2,+20)) {
			currentHealth -= 1;
			if(movingDir == UP) { y += 10;}
			if(movingDir == DOWN) { y -= 10;}
			if(movingDir == LEFT) { x += 10;}
			if(movingDir == RIGHT) { x -= 10;}
			
			if(currentHealth < 1) {
				isDead = true;
				currentHealth = 0;
			}

		}

	}

	public int doorCollide(int x, int y, int w, int h) {
		int collide = -1;
		Rectangle box = new Rectangle(getCollisionBox(x,y,w,h));

		for(int i=0;i<Room.door.length;i++) {
			if(Room.door[i].myMap.getName() == Screen.thisMap.getName()) {
				if(box.intersects(Room.door[i].getDoorBoxV()) ||
						box.intersects(Room.door[i].getDoorBoxH())) {
					collide = i;
					break;
				}
			}
		}
		return collide;
	}
	
	// returns number of mob that got hit
	public int weaponCollide() {

		int collide = -1;
		Rectangle box = new Rectangle(getWeaponBox().x,
				getWeaponBox().y,
				getWeaponBox().width,
				getWeaponBox().height);
		for(int i=0;i<Screen.mob.size();i++) {
			if(Screen.mob.get(i).myMap == Screen.thisMap.getName()) {
				if(Screen.mob.get(i).getCollisionBox().intersects(box)) {
					collide = i;
					break;
				}
			}
		}
		return collide;
	}
	
	public int NPCCollide(int x, int y, int w, int h) {

		int thisNPC = -1;
		Rectangle box = new Rectangle(getCollisionBox(x,y,w,h));
		for(int i=0;i<Screen.chars.size();i++) {
			if(Screen.chars.get(i).getCollisionBox(0,0,0,0).intersects(box)) {
				thisNPC = i;
				break;
			}
		}
		return thisNPC;
	}

	public int itemCollide() {

		int item = -1;
		Rectangle box = new Rectangle(getCollisionBox(-1,+4,+2,+24));
		for(int i=0;i<Screen.item.size();i++) {
			if(Screen.item.get(i).getCollisionBox().intersects(box)) {
				item = i;
				break;
			}
		}
		return item;
	}
	
	
	public void drawDeath(Graphics g) {
		
	}

	public void draw(Graphics g) {
		
		// animation when moving every 70 loops
		if(motionFrame > motionSpeed && isMoving) {
			motion += 4;
			if(motion > 7) {
				motion -= 8;
			}
			motionFrame = 0;
		} else  {
			motionFrame += 1;
		}

		if(!isFighting()) {
			
			g.drawImage(Img.linkNormal[movingDir+motion], x, y, 36, 32, null);
			
		} else {
			int xF = x, yF = y;
			// set link in the upperleft corner of the pic when movingDir is left or up
			// he's in the bottomright corner in those pics
			if(movingDir == 1 || movingDir == 3){ 
				xF -= 22;						
				yF -= 22;
			}

			// weaponBox
			if(Screen.debug) {
				g.setColor(Color.PINK);
				g.drawRect(getWeaponBox().x, getWeaponBox().y, getWeaponBox().width, getWeaponBox().height);
			}

			g.drawImage(Img.linkFighting[0][movingDir], xF, yF, 54, 54, null);
			if(fightFrame > fightSpeed) {	// how long fighting pic needs to be shown
				
				fightFrame = 0;
				isFighting = false;
				
				for(int a=0;a<Screen.mob.size();a++) {
					Screen.mob.get(a).isHit = false;
				}
				
			} else {
				fightFrame += 1;
			}
		}

		// collisionBox
		if(Screen.debug) {
			g.setColor(Color.BLUE);
			g.fillRect(getCollisionBox(0,0,0,0).x,
					getCollisionBox(0,0,0,0).y,
					getCollisionBox(0,0,0,0).width,
					getCollisionBox(0,0,0,0).height);	// bit smaller than actual player to make it bit smoother
		}

		isMoving = false;
		
	}
}