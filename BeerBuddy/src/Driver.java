import java.util.HashMap;
import java.util.Scanner;

public class Driver {

	public static void main(String[] args) {
		HashMap<String, Beer> beerMap = new HashMap<>();
		Scanner input = new Scanner(System.in);
		
		beerMap.put("Coors Light", new Beer("Coors Light", "Lager", 2, 10, 42, 1.42));
		beerMap.put("Modelo Especial", new Beer("Modelo Especial", "Lager", 5, 18, 45, 1.58));
		
		
		System.out.println("Welcome to Beer Buddy!\nEnter the name of a beer you enjoy:");
		String theChosenBeer = input.nextLine();
		System.out.print(theChosenBeer + ", excellent choice!");
		
		System.out.println("Here are your beers stats: \n" + beerMap.get(theChosenBeer));
	}

}
