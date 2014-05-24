package be.esthoril.handlers;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import be.esthoril.game.Screen;

public class KeyHandel implements KeyListener{
	
	public KeyHandel() {
	}

	public class Key {
		
		private boolean pressed = false;

		public boolean isPressed() {
			return pressed;
		}
		
	}

	public void keyPressed(KeyEvent e) {
		if(!Screen.player.inShop) {
			if(e.getKeyCode() == 37) { Screen.left	= true;}
			if(e.getKeyCode() == 38) { Screen.up	= true;}
			if(e.getKeyCode() == 39) { Screen.right	= true;}
			if(e.getKeyCode() == 40) { Screen.down	= true;}
			if(e.getKeyCode() == 65) { Screen.player.isFighting = true;} // 'a'
		} else {
			Screen.thisShop.clicked(e);
		}
	}

	public void keyReleased(KeyEvent e) {
		if(e.getKeyCode() == 37) { Screen.left	= false;}
		if(e.getKeyCode() == 38) { Screen.up	= false;}
		if(e.getKeyCode() == 39) { Screen.right	= false;}
		if(e.getKeyCode() == 40) { Screen.down	= false;}
		Screen.player.isMoving = false;
	}

	public void keyTyped(KeyEvent e) {
		
	}
}