package chain_of_responsibility.example.handlers;

import java.util.List;

//Ostatnia próba: jeśli wszystko inne zawiedzie, próbujemy zamówić pizzę. Uda się, jeśli w 'supplies' mamy hajs oraz
//  "pizzeria" powiązana z tym handlerem jest otwarta.
//Do parametru moglibyśmy oczywiście podawać coś bardziej przydatnego, np. nazwę restauracji, z której chcemy zamówić.
public class TakeoutHandler implements IHandler {
    boolean pizzaPlaceOpen;

    public TakeoutHandler(boolean pizzaPlaceOpen) {
        this.pizzaPlaceOpen = pizzaPlaceOpen;
    }

    @Override
    public boolean findFood(List<String> supplies) {
        System.out.println("3. Takeout?");
        if (supplies.contains("money")) {
            System.out.println("\tCalling the pizza delivery...");
            return orderPizza();
        }
        System.out.println("\tDamn, I'm broke...");
        return false;
    }

    private boolean orderPizza() {
        if (pizzaPlaceOpen) {
            System.out.println("\tPizza for dinner!");
            return true;
        }
        System.out.println("\tOh no, it's closed :/");
        return false;
    }
}
