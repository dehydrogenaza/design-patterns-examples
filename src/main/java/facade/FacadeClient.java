package facade;

import facade.kitchen_library.*;
import facade.facade.PizzaFacade;

import java.util.LinkedList;
import java.util.List;

//Klasa do testów. Nie jest częścią wzorca.
//
//Zamiast bezpośrednio korzystać ze skomplikowanego "zewnętrznego" narzędzia `kitchen_library`, używamy fasady
// PizzaFacade, która zawiera tylko te metody, na których nam zależy (w naszym przypadku, będziemy żywić się
// wyłącznie pizzą).
// Fasada ułatwiłaby również ew. podmianę wykorzystywanego narzędzia w przyszłości.
public class FacadeClient {

    public static void main(String[] args) {
        //Ustaliliśmy sobie, że jedyna część 'kitchen_library', którą chcemy konfigurować w naszym programie, to
        // lista dostępnych składników. Podajemy ją do naszej Fasady jako parametr.
        List<Ingredient> supplies = new LinkedList<>(List.of(new Ingredient("mąka", 10, MeasurementUnit.KG),
                new Ingredient("drożdże", 200, MeasurementUnit.G),
                new Ingredient("oliwa", 2, MeasurementUnit.L),
                new Ingredient("woda", 5, MeasurementUnit.L),
                new Ingredient("sól", 2, MeasurementUnit.KG),
                new Ingredient("ser", 2, MeasurementUnit.KG),
                new Ingredient("pomidory", 3, MeasurementUnit.KG),
                new Ingredient("bazylia", 50, MeasurementUnit.G)));

        PizzaFacade pizzaFacade = new PizzaFacade(supplies);
        //Metoda 'makeMargerita()' ukrywa bardzo skomplikowany proces, który nas jednak nie interesuje — zależy nam
        // tylko na wyniku.
        pizzaFacade.makeMargherita();
    }
}
