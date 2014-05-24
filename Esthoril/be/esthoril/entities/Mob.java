package be.esthoril.entities;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.Random;

import be.esthoril.game.Screen;
import be.esthoril.gfx.Img;
import be.esthoril.gfx.Room;
import be.esthoril.item.Item;

public class Mob extends NPC {

	private int totalHealth;
	private MobType mob;
	private boolean isDead;
	
	public int currentHealth;
	public boolean isHit;
	public boolean died;
	
	private int deathSpeed = 10, deathFrame = 0, i=0; // speed of mobdeath animation
	
	public Mob(MobType mob,	int[] p, String myMap) {

		super(p, myMap);
		
		this.mob = mob;
		totalHealth = mob.getHealth();
		currentHealth = 3;
		isHit = false;
		isDead = false;
		died = false;
	}

	public void physic() {
		
		if(currentHealth == 0) {
			isDead = true;
		}
		
		if(!isDead) {
			checkMotion();
			move();
		}
	}

	public String getMyMap() {
		return myMap;
	}

	public Rectangle getCollisionBox() {
		Rectangle NPCBox = new Rectangle(x + 1 + Room.xOffset, y + Room.yOffset, 30, 32);
		return NPCBox;
	}

	public void getReward() {
		
		Screen.player.currentXP += mob.getXP();
		
		Random rg = new Random();
		int drop = rg.nextInt(100);
		if(drop < 40) {
			Screen.item.add(new Item(Item.GOLD, x, y));
		} else if(drop < 80) {
			Screen.item.add(new Item(Item.HEART, x, y));
		}
	}

	// little animation when a mob dies
	public void drawDeath(Graphics g) {
		if(deathFrame > deathSpeed) {
			deathFrame = 0;
			i++;
		} else if(i<3) {
			g.drawImage(Img.mobDeath[i], Room.xOffset+x-2, Room.yOffset+y-2, 34, 34, null);
			deathFrame++;
		} else {
			died = true;
		}
	}

	public void draw(Graphics g) {
		if(!isDead) {
			
			g.drawImage(Img.mobimg[movingDir + mob.getType() * 4][walkOrder[motion]],
					Room.xOffset+x,
					Room.yOffset+y,
					32,
					32,
					null);
	
			// health rectangles
			g.setColor(new Color(200,200,200));
			for(int i=0;i<totalHealth;i++) {
				g.fillRect(Room.xOffset + x + 6 * i, Room.yOffset + y - 12, 4, 10);
			}
			g.setColor(new Color(50,100,50));
			for(int i=0;i<currentHealth;i++) {
				g.fillRect(Room.xOffset + x + 6 * i, Room.yOffset + y - 12, 4, 10);
			}
			
		} else {
			drawDeath(g);
		}
		
		if(Screen.debug) {
			g.drawRect(getCollisionBox().x,
					getCollisionBox().y,
					getCollisionBox().width,
					getCollisionBox().height);
		}
	}
}
