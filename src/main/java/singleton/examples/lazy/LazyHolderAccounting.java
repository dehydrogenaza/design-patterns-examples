package singleton.examples.lazy;

import singleton.examples.AccountingBase;

//Ulepszona wersja Lazy Singletona z zabezpieczoną wielowątkowością. Wykorzystuje specyficzny sposób, w jaki Java
// inicjalizuje wewnętrzne klasy statyczne.
public class LazyHolderAccounting extends AccountingBase {

    //Przy uruchomieniu programu, gdy JVM "czyta" LazyHolderAccounting, nie "widzi" żadnych PÓL statycznych
    // (klasy wewnętrzne jej nie interesują).
    private static final class Holder {
        //Statyczne pole INSTANCE należy do wewnętrznej klasy Holder, a nie do samego Singletona, co ma znaczenie dla
        // JVM. Dzięki temu pole to nie jest inicjalizowane od razu przy starcie programu, a dopiero wtedy, gdy
        // wewnętrzna klasa zostanie pierwszy raz użyta.
        private static final LazyHolderAccounting INSTANCE = new LazyHolderAccounting();
    }

    //Dopiero przy pierwszym użyciu .getInstance() pola wewnętrznej klasy Holder zostają zainicjalizowane.
    //Ponieważ pole INSTANCE jest statyczne, zgodnie ze specyfikacją Javy mamy gwarancję, że zostanie
    // zainicjalizowane "bezpiecznie".
    public static LazyHolderAccounting getInstance() {
        return Holder.INSTANCE;
    }


    //KLUCZOWE jest ustawienie konstruktora jako prywatnego, co uniemożliwia bezpośrednie tworzenie dodatkowych
    // instancji. Prywatny konstruktor wciąż może być wykorzystywany (jak każda prywatna metoda) z wnętrza tej klasy,
    // co umożliwia nam inicjalizację z jego użyciem.
    private LazyHolderAccounting() {
        super();
    }
}
