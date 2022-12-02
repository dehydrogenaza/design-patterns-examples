package singleton.examples.lazy;

import singleton.examples.AccountingBase;

//Jedno z możliwych rozwiązań problemu LazyAccounting z wielowątkowością.
// NIEZALECANE: wersja z Holder jest lepsza (jak możecie zauważyć, nawet IntelliJ to podpowiada).
public class LazySynchronizedAccounting extends AccountingBase {

    //Początkowo, INSTANCE jest 'null', jak w typowej wersji LazyAccounting.
    private static LazySynchronizedAccounting INSTANCE;


    //Oczywiste rozwiązanie problemu (zakomentowane) jest w rzeczywistości bardzo kosztowne (jeśli pracujemy w
    // środowisku wielowątkowym, chcemy wchodzić do bloków 'synchronized' tak rzadko, jak to tylko możliwe). Blok
    // 'synchronized' jest używany za każdym razem, gdy chcemy skorzystać z instancji Singletona.

//    public static LazySynchronizedAccounting getInstance() {
//        synchronized (LazySynchronizedAccounting.class) {
//            if (instance == null) {
//                instance = new LazySynchronizedAccounting();
//            }
//        }
//        return instance;
//    }

    //Rozwiązaniem jest tzw. "double-check". Najpierw niesynchronicznie sprawdzamy, czy instancja istnieje, co
    // załatwia zdecydowaną większość przypadków. Blok 'synchronized' chroni nas tylko w "przypadku granicznym",
    // czyli podczas inicjalizacji Singletona.
    public static LazySynchronizedAccounting getInstance() {
        //Jeśli INSTANCE istnieje, omijamy IFa.
        if (INSTANCE == null) {
            //Kilka wątków mogło "dojść" do tego punktu jednocześnie, dlatego musimy je zsynchronizować.
            //Jako 'lock' używamy po prostu pola .class tej klasy.
            synchronized (LazySynchronizedAccounting.class) {
                //Jeszcze raz sprawdzamy, czy INSTANCE istnieje. Jeśli kilka wątków czeka na dostęp do bloku
                // 'synchronized', tylko pierwszy z nich wejdzie do IFa, stworzy instancję i dopiero potem zwolni
                // blok dla pozostałych wątków (które zobaczą, że INSTANCE != null i ominą IFa).
                if (INSTANCE == null) {
                    INSTANCE = new LazySynchronizedAccounting();
                }
            }
        }
        return INSTANCE;
    }


    //KLUCZOWE jest ustawienie konstruktora jako prywatnego, co uniemożliwia bezpośrednie tworzenie dodatkowych
    // instancji. Prywatny konstruktor wciąż może być wykorzystywany (jak każda prywatna metoda) z wnętrza tej klasy,
    // co umożliwia nam inicjalizację z jego użyciem.
    private LazySynchronizedAccounting() {
        super();
    }
}
