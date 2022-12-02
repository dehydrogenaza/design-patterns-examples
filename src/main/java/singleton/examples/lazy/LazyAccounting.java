package singleton.examples.lazy;

import singleton.examples.AccountingBase;

//Prosta implementacja LazyAccounting, nadaje się TYLKO do zastosowań jednowątkowych.
public class LazyAccounting extends AccountingBase {

    //Początkowo, INSTANCE jest 'null'.
    private static LazyAccounting INSTANCE;

    public static LazyAccounting getInstance() {
        //Jeżeli INSTANCE jest 'nullem', tworzymy instancję. W przeciwnym wypadku omijamy ten blok.
        if (INSTANCE == null) {
            INSTANCE = new LazyAccounting();
        }
        //Zwracamy jedyną instancję.
        return INSTANCE;
        //Problem z tym kodem pojawia się, gdy dwa wątki wywołają .getInstance() jednocześnie. Może się zdarzyć, że
        // pierwszy wątek wejdzie do IFa, ale nie zdąży jeszcze utworzyć obiektu (bardzo możliwe, jeśli Singleton
        // jest "ciężki"). W tym momencie drugi wątek nadal widzi INSTANCE jako 'null' i zacznie tworzyć swoją własną
        // — DRUGĄ — instancję, co jest oczywiście niepożądane.
    }

    //KLUCZOWE jest ustawienie konstruktora jako prywatnego, co uniemożliwia bezpośrednie tworzenie dodatkowych
    // instancji. Prywatny konstruktor wciąż może być wykorzystywany (jak każda prywatna metoda) z wnętrza tej klasy,
    // co umożliwia nam inicjalizację z jego użyciem.
    private LazyAccounting() {
        super();
    }
}
