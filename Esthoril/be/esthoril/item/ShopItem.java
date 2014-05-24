package be.esthoril.item;

public enum ShopItem {
	BASICSWORD		("Wooden Sword", 10),
	BASICSHIELD		("Basic Shield", 15),
	KEYJ			("Key of Jack",25),
	KEYQ			("Key of Queen",100);

	private String name;
	private int price;

	private ShopItem(String name, int price) {
		
		this.name = name;
		this.price = price;
		
	}

	public String getName() {
		return name;
	}

	public int getPrice() {
		return price;
	}
}
