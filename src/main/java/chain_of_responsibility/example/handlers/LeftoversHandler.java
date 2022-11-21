package chain_of_responsibility.example.handlers;

import java.util.List;

//To będzie nasza pierwsza próba: sprawdzenie, czy w 'supplies' są resztki z wczoraj.
public class LeftoversHandler implements IHandler {

    @Override
    public boolean findFood(List<String> supplies) {
        System.out.println("1. Leftovers?");
        if (supplies.contains("pizza") || supplies.contains("momsDinner")) {
//            reheatLeftovers(); //przykładowa obsługa sytuacji, tutaj niezaimplementowana
            System.out.println("\tLeftovers for dinner!");
            return true;
        }
        System.out.println("\tNothing left from yesterday...");
        return false;
    }
}
