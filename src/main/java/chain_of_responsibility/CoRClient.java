package chain_of_responsibility;

import chain_of_responsibility.example.ChainElement;
import chain_of_responsibility.example.handlers.*;

import java.util.ArrayList;
import java.util.List;

//Klasa do testowania, nie jest częścią wzorca.
//W tym przykładzie próbujemy znaleźć jedzenie według następującego schematu:
//  1. Sprawdzamy, czy są resztki z wczoraj; jeśli tak, odgrzewamy i nie przechodzimy dalej.
//  2. Sprawdzamy, czy mamy składniki na jajka na bekonie; jeśli tak, przygotowujemy je i nie przechodzimy dalej.
//  3. Próbujemy zamówić pizzę. Jeśli mamy pieniądze, jemy pizzę, jeśli nie, będziemy dzisiaj głodni. Niezależnie od
//      wyniku, na tym kończymy próby.
public class CoRClient {

    public static void main(String[] args) {
        //Tworzymy instancje metod (zwanych często Handlerami), które mogą obsłużyć nasze zadanie.
        //Ponieważ wszystkie implementują IHandler, możemy je obsługiwać metodą findFood() z tego interfejsu.
        IHandler leftoversHandler = new LeftoversHandler();
        IHandler cookingHandler = new CookingHandler();
        IHandler takeoutHandler = new TakeoutHandler(true);

        //Jeden ze sposobów inicjacji łańcucha
        //1. argument konstruktora to HANDLER (metoda),
        //2. argument konstruktora to KOLEJNY ELEMENT ŁAŃCUCHA.
        //Ostatni element łańcucha nie ma kolejnego elementu — tylko metodę.
        ChainElement firstElement = new ChainElement(leftoversHandler,
                new ChainElement(cookingHandler,
                new ChainElement(takeoutHandler)));

        //Można też inicjować kolejno ogniwa łańcucha, nie trzeba tego robić w jednym konstruktorze.
//        ChainElement alternative3 = new ChainElement(takeoutHandler);
//        ChainElement alternative2 = new ChainElement(cookingHandler, alternative3);
//        ChainElement alternative1 = new ChainElement(leftoversHandler, alternative2);

        //Kilka testów. Metoda findFood() przyjmuje listę Stringów zawierających dostępne "środki", np. kasę na pizzę.
        firstElement.findFood(new ArrayList<>());
        firstElement.findFood(List.of("money"));
        firstElement.findFood(List.of("eggs"));
        firstElement.findFood(List.of("eggs", "bacon"));
        firstElement.findFood(List.of("pizza"));
        firstElement.findFood(List.of("momsDinner"));
        //Poniższe dwie metody pokazują, że łańcuch faktycznie działa sekwencyjnie. Jeśli łańcuch "znajdzie"
        // odgrzewki, wykorzysta je i nie przejdzie do wykorzystania kolejnych składników, podobnie w przypadku eggs
        // & bacon.
        firstElement.findFood(List.of("eggs", "bacon", "money"));
        firstElement.findFood(List.of("momsDinner", "pizza", "eggs", "bacon", "money"));

        //Tutaj test z "zamkniętą pizzerią". To wszystko są tylko elementy testowe i nie mają nic wspólnego z samym
        //  Patternem, a jedynie ilustrują jak z niego korzystać.
        IHandler takeoutHandlerClosed = new TakeoutHandler(false);
        ChainElement otherFirstElement = new ChainElement(leftoversHandler,
                new ChainElement(cookingHandler,
                new ChainElement(takeoutHandlerClosed)));
        otherFirstElement.findFood(List.of("money"));

        //Możemy łatwo zmieniać kolejność wykonania. Tutaj, preferujemy świeżą pizzę (ale pizzeria jest zamknięta),
        // potem gotowanie, a dopiero w ostateczności odgrzewki.
        ChainElement changedOrder = new ChainElement(takeoutHandlerClosed,
                new ChainElement(cookingHandler,
                new ChainElement(leftoversHandler)));
        changedOrder.findFood(List.of("money", "momsDinner"));
    }
}
