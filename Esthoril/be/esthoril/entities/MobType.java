package be.esthoril.entities;

// nog toevoegen hoeveel damage ze doen en ev special attacks oid
public enum MobType {

	DARKREDBAT		("Evil Bat", 3, 1, 10, 0),
	REDBAT			("", 5, 1, 15, 2),
	BROWNKNIGHT		("", 5, 1, 25, 1),
	BLUEKNIGHT		("Blue Knight", 5, 1, 50, 3),
	REDGHOST		("", 5, 1, 25, 4),
	REDARAB			("", 2, 1, 10, 6),
	YELLOWPUNK		("", 5, 1, 5, 5),
	REDPUNK			("", 2, 1, 8, 7);

	private final int health;
	private final int speed;
	private final int xp;
	private final int type;			// which picture from the tileset
	private final String name;
	
	private MobType(String name, int health, int speed, int xp, int type) {
		this.health = health;
		this.speed = speed;
		this.type = type;
		this.name = name;
		this.xp = xp;
	}

	public int getHealth() {
		return health;
	}
	
	public int getSpeed() {
		return speed;
	}
	
	public int getType() {
		return type;
	}
	
	public String getName() {
		return name;
	}
	
	public int getXP() {
		return xp;
	}
}
