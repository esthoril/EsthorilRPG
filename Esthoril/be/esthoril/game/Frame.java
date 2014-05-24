package be.esthoril.game;

import java.awt.Dimension;

import javax.swing.JFrame;

/* CHANGELOG
 * 
 * 7jan2014		added NPC class
 * 				speech class
 * 8jan2014		reviewed code and did some optimalizing
 * 				img class
 * 				door class
 * 				mob class - player fall back + lose life when hit
 * 9jan2014		mobType enum, cleaned up entity-npc-mob hierarchy a bit
 * 				mobs now lose health and die (get removed)
 * 10jan2014	mob movement turn method and movement changed to following points of polygon
 * 				item class
 * 				mobs random dropping item and player picking up item, mobs give exp
 * 11jan2014	shop added
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 */

public class Frame extends JFrame {

	private static final long serialVersionUID = 1L;
	
	private Dimension totalSize = new Dimension(774,662);
	public static Screen screen;
	public Menu menu;

	public Frame() {

	}

	public void init() {
		setSize(totalSize);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle("Esthoril RPG");
		setLocationRelativeTo(null);
		setResizable(false);
		
		menu = new Menu();
		screen = new Screen(this);

		add(menu);
		add(screen);
		
		setVisible(true);
	}
	
	public static void main(String args[]) {
		Frame mapmaker = new Frame();
		mapmaker.init();
	}

}
