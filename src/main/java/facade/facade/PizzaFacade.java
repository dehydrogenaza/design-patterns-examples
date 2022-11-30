package facade.facade;

import java.util.List;
import facade.kitchen_library.*;


//Fasada dla 'kitchen_library', ukrywająca detale tej skomplikowanej biblioteki. Spełnia 2 funkcje:
//1. Jeżeli zależy nam na tylko jednej (kilku) konkretnej funkcjonalności, wystarczy zaimplementować ją raz, w
//      fasadzie. W reszcie programu w ogóle nie musimy odnosić się do zewnętrznej biblioteki, co znacznie upraszcza kod
//      (zasada DRY). Dzięki temu, gdybyśmy chcieli np. zmienić przepis, wystarczy, że zrobimy to w tym miejscu (nie
//      musimy skakać po całym programie).
//2. Gdybyśmy chcieli zmienić całą bibliotekę 'kitchen_library', musimy tylko przebudować Fasadę. Reszty programu nie
//      interesuje to, co się za nią kryje.
public class PizzaFacade {

    //Fasada może ustalać niektóre elementy biblioteki "na sztywno" lub zostawiać je konfigurowalne. Tutaj lista
    // dostępnych składników jest konfigurowalna (przekazywana do Fasady).
    private final List<Ingredient> supplies;

    public PizzaFacade(List<Ingredient> supplies) {
        this.supplies = supplies;
    }

    //"Interfejs" fasady. W naszym przypadku to tylko jedna prosta funkcja (z całej biblioteki zależy nam tylko na
    // pieczeniu pizzy margherita), ale oczywiście zwykle będzie to albo
    // 1) funkcja konfigurowana za pomocą jakichś parametrów,
    // 2) albo kilka funkcji.
    //Przy czym, jeśli funkcji robi się zbyt wiele, warto rozważyć zastosowanie kilku fasad, zgodnie z zasadą Single
    // Responsibility.
    public void makeMargherita() {
        List<Ingredient> unbakedPizza = prepBase();

        bake(unbakedPizza);
    }

    //Fasada używa biblioteki 'kitchen_library', którą celowo trochę skomplikowałem. Można sobie wyobrazić, że
    // gdybyśmy chcieli "robić margheritę" wielokrotnie w kodzie, to bezpośrednie wpisywanie poniższych komend byłoby
    // bardzo uciążliwe. Chyba każdy "instynktownie" stworzyłby do tego metodę pomocniczą — będącą de facto
    // najprostszą formą Fasady.
    private List<Ingredient> prepBase() {
        //Najpierw mamy "menedżer przepisów", zarządzający całym procesem.
        RecipeManager kitchen = new RecipeManager(supplies, List.of("ciasto", "składniki"));

        //Musimy ręcznie wskazać potrzebny poziom doświadczenia kucharza. Być może biblioteka 'kitchen_library' była
        // stworzona np. dla restauracji, w których pomagała rozdzielać zadania między pracowników.
        kitchen.assignChef(2);

        //Musimy przygotować naczynia (tutaj akurat naczyniem jest 'blat') i dodać składniki. Jeśli mamy stały i
        // sprawdzony przepis na pizzę, te detale już nas nie interesują, ale 'kitchen_library' najwyraźniej pozwala
        // na wiele więcej, niż tylko robienie pizzy.
        kitchen.prepDish("blat");
        kitchen.addToDish(new Ingredient("mąka", 250, MeasurementUnit.G), "blat");
        kitchen.addToDish(new Ingredient("drożdże", 7, MeasurementUnit.G), "blat");
        kitchen.addToDish(new Ingredient("woda", 150, MeasurementUnit.ML), "blat");
        kitchen.addToDish(new Ingredient("oliwa", 2, MeasurementUnit.LYZECZKA), "blat");
        kitchen.addToDish(new Ingredient("sól", 1, MeasurementUnit.LYZECZKA), "blat");

        //Jw., tym razem dodajemy składniki do misek. Musimy ręcznie zaznaczyć, że część z nich chcemy umyć i posiekać.
        Ingredient tomatoes = kitchen.prepVegetable(new Ingredient("pomidory", 200, MeasurementUnit.G));
        Ingredient basil = kitchen.prepVegetable(new Ingredient("bazylia", 200, MeasurementUnit.G));
        kitchen.prepDish("miseczki");
        kitchen.addToDish(new Ingredient("ser", 200, MeasurementUnit.G), "miseczki");
        kitchen.addToDish(tomatoes, "miseczki");
        kitchen.addToDish(basil, "miseczki");

        //Musimy połączyć składniki i wskazać z przepisu metodę, którą się posługujemy.
        kitchen.mix("blat", 0);
        kitchen.mix("miseczki", 1);

        return kitchen.combineDishes("blat", "miseczki");
    }

    //'kitchen_library' pozwala też na precyzyjną kontrolę nad piekarnikiem, która w przypadku naszego zastosowania
    // nie jest tak istotna. Ją również ukrywamy za fasadą.
    private void bake(List<Ingredient> unbakedPizza) {
        Oven.setOn(true);
        Oven.preheat(250);

        if (Oven.bake(unbakedPizza, 250)) {
            System.out.println("*** PIZZA UPIECZONA! ***");
        } else {
            System.out.println("Pizza niedopieczona :(");
        }

        Oven.setOn(false);
    }
}
