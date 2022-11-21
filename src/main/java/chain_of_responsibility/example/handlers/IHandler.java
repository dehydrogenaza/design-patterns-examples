package chain_of_responsibility.example.handlers;

import java.util.List;

//Interfejs dzielony przez każdą z METOD (tzw. HANDLERÓW), którymi będziemy kolejno próbowali obsłużyć zadanie.
public interface IHandler {

    //Próbujemy "zdobyć jedzenie" używając dostępnych 'supplies'.
    //Implementacje zwracają 'true', jeśli danemu handlerowi się udało.
    boolean findFood(List<String> supplies);
}
