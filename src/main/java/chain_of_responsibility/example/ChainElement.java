package chain_of_responsibility.example;

import chain_of_responsibility.example.handlers.IHandler;

import java.util.List;

//Reprezentuje kolejne ogniwa łańcucha. Każde ogniwo to 1 alternatywne podejście do obsługi danej sytuacji.
//Ogniwo ma 2 główne elementy:
//  - METODA: handler, którego wywołujemy w tym podejściu.
//  - NEXT: referencja do kolejnego elementu łańcuszka. Jeśli jest 'null', łańcuszek się kończy.
//Uwaga: w przykładzie z zajęć zamiast sprawdzania nulla użyto Optionala. To BŁĘDNE PODEJŚCIE. Optionale
// (zgodnie z założeniami twórców) powinny być używane WYŁĄCZNIE jako "return value" — wartość zwracana z funkcji —
// a nie jako typy pól czy parametry.
//Dlaczego? Pamiętajcie, że SAM OPTIONAL MOŻE BYĆ NULLEM. Innymi słowy, takie coś może istnieć:
//Optional<SomeClass> = null;
//Używając Optionala jako pole, z 2 możliwych sytuacji (non-null albo null) robimy trzy: Optional zawierający non-null,
// Optional zawierający null (pusty), albo null. Jest to zupełny bezsens, który tylko dorabia nam pracy, i jasno daje
// do zrozumienia, że ktoś tu nie do końca zrozumiał Optionala ;)
public class ChainElement {

    //Obecny handler.
    private final IHandler method;

    //Następny handler, jeśli istnieje.
    private final ChainElement next;

    //Konstruktor handlera będącego na początku lub w środku łańcucha.
    public ChainElement(IHandler method, ChainElement next) {
        this.method = method;
        this.next = next;
    }

    //Konstruktor dla ostatniego ogniwa łańcucha, domyślnie ustawia 'next' na null.
    public ChainElement(IHandler handler) {
        this.method = handler;
        this.next = null;
    }

    //Najwyraźniej przyjęło się, że ta metoda ma tę samą nazwę, co w interfejsie handlera, ale nie jest to konieczne
    // (a IMO nawet mylące, ale co ja tam wiem).
    // Zauważcie, że sam ChainElement nie implementuje IHandler — robią to jedynie jego pola 'method'.
    public void findFood(List<String> supplies) {
        //Każda z implementacji handlera zwraca 'true' z metody findFood() jeśli proces się udał.
        boolean successful = method.findFood(supplies);

        //Obecnej implementacji (zamieszczonej w tym ogniwie łańcucha) się powiodło, więc wychodzimy z łańcucha - nie
        // ma potrzeby wywoływania kolejnych implementacji.
        if (successful) {
            System.out.println("(!) Managed to get food with " + method.getClass().getSimpleName() + "\n");
            return;
        }

        //Obecnej implementacji się nie powiodło. Jeśli istnieje kolejne ogniwo, wywołujemy jego metodę findFood().
        if (next != null) {
            next.findFood(supplies);
        } else {
            //'next' jest nullem: wyczerpaliśmy wszystkie opcje łańcucha, kończymy pracę.
            System.out.println("(!) Exhausted all options - no dinner today ;(((\n");
        }
    }
}
