
public class Beer {
	private String name;
	private String style;
	private String subStyle; //maybe implement this into the constructor and Beer in general later
	private String origin; // maybe implement this to be a sorting option later
	private int color; // uses SRM color indicator. This ranges from 0 - 40
	private int ibu; // International Bitterness Units indicates bitterness, and ranges from 0 - 100
	private int abv; // Alcohol By Volume will have a range of 30 - 150 this simply moves the decimal point right once place 
	private double price;
	private int score; //identifier key for a beer given it's color, abv, and ibu
	
	// Arg Constructor
	public Beer (String name, String style, int color, int ibu, int abv, double price) {
		this.name = name;
		this.style = style;
		this.color = color;
		this.ibu = ibu;
		this.abv = abv;
		this.price = price;
		this.score = getScore(color, ibu, abv);
	}
	
	//gets a given beers score, may need to change algorithm later
	public static int getScore(int color, int ibu, int abv) {
		int score = color + ibu + abv;	
		return score;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getStyle() {
		return style;
	}

	public void setStyle(String style) {
		this.style = style;
	}

	public String getSubStyle() {
		return subStyle;
	}

	public void setSubStyle(String subStyle) {
		this.subStyle = subStyle;
	}

	public String getOrigin() {
		return origin;
	}

	public void setOrigin(String origin) {
		this.origin = origin;
	}

	public int getColor() {
		return color;
	}

	public void setColor(int color) {
		this.color = color;
	}

	public int getIbu() {
		return ibu;
	}

	public void setIbu(int ibu) {
		this.ibu = ibu;
	}

	public double getAbv() {
		return abv;
	}

	public void setAbv(int abv) {
		this.abv = abv;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	@Override
	public String toString() {
		return "Beer [name=" + name + ", style=" + style + ", subStyle=" + subStyle + ", origin=" + origin + ", color="
				+ color + ", ibu=" + ibu + ", abv=" + abv + ", price=" + price + ", score=" + score + "]";
	}
	
	
	
	
}
