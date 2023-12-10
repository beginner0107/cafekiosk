package sample.cafekiosk;

import org.junit.jupiter.api.Test;
import sample.cafekiosk.unit.CafeKiosk;
import sample.cafekiosk.unit.beverage.Americano;

class CafekioskApplicationTests {

	@Test
	void add() {
		CafeKiosk cafeKiosk = new CafeKiosk();
		cafeKiosk.add(new Americano());

		System.out.println(">>> 담긴 음료 수 : " + cafeKiosk.getBeverages().size());
		System.out.println(">>> 담긴 음료 : " + cafeKiosk.getBeverages().get(0).getName());
	}

}
