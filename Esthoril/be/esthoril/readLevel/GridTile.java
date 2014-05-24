package be.esthoril.readLevel;

import java.io.Serializable;

public class GridTile implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private int set;
	public boolean isFilled;
	public boolean isDoor;
	public int x;
	public int y;

	
	public GridTile(int set, int y, int x, boolean isFilled) {
		this.isFilled = isFilled;
		this.set = set;
		this.y = y;
		this.x = x;		
	}
	
	public int getSet() {
		return set;
	}
	
	public void setSet(int s) {
		this.set = s;
	}
	

}
