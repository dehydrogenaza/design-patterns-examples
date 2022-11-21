package chain_of_responsibility.example.handlers;

import java.util.List;

//To będzie druga próba: sprawdzenie, czy w 'supplies' są składniki na jajka na bekonie.
public class CookingHandler implements IHandler {
    @Override
    public boolean findFood(List<String> supplies) {
        System.out.println("2. Eggs & bacon?");
        if (supplies.contains("eggs") && supplies.contains("bacon")) {
//            cookEggsAndBacon(); //przykładowa obsługa sytuacji, tutaj niezaimplementowana
            System.out.println("\tMaking eggs & bacon. Good stuff!");
            return true;
        }
        System.out.println("\tOut of ingredients...");
        return false;
    }
}
