package be.esthoril.game;

import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;


public class MouseHandel implements MouseMotionListener, MouseListener {

	public void mouseClicked(MouseEvent e) {

	}

	public void mouseEntered(MouseEvent e) {

	}

	public void mouseExited(MouseEvent e) {

	}

	public void mousePressed(MouseEvent e) {
		Screen.mse.x = e.getX() - 4;
		Screen.mse.y = e.getY() - 30;
	}

	public void mouseReleased(MouseEvent e) {
		Screen.room.mseStart.x = e.getX() - 4;
		Screen.room.mseStart.y = e.getY() - 30;
	}

	public void mouseDragged(MouseEvent e) {

	}

	public void mouseMoved(MouseEvent e) {
		Screen.mse = new Point(e.getX(), e.getY());
	}
	
	

}