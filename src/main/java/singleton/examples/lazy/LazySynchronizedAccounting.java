package singleton.examples.lazy;

import singleton.examples.AccountingBase;

public class LazySynchronizedAccounting extends AccountingBase {

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
        if (INSTANCE == null) {
            synchronized (LazySynchronizedAccounting.class) {
                if (INSTANCE == null) {
                    INSTANCE = new LazySynchronizedAccounting();
                }
            }
        }
        return INSTANCE;
    }

    private LazySynchronizedAccounting() {
        super();
    }
}
