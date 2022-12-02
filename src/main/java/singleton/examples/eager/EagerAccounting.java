package singleton.examples.eager;

import singleton.examples.AccountingBase;

//Najbardziej podstawowa wersja Singletona. Wystarcza pewnie w 99% przypadków.
public class EagerAccounting extends AccountingBase {

    //Statycznie inicjalizujemy instancję. Sama specyfikacja Javy zapewnia, że instancja zostanie stworzona w sposób
    // "bezpieczny" wielowątkowo.
    private static final EagerAccounting INSTANCE = new EagerAccounting();

    public static EagerAccounting getInstance() {
        return INSTANCE;
    }

    //KLUCZOWE jest ustawienie konstruktora jako prywatnego, co uniemożliwia bezpośrednie tworzenie dodatkowych
    // instancji. Prywatny konstruktor wciąż może być wykorzystywany (jak każda prywatna metoda) z wnętrza tej klasy,
    // co umożliwia nam inicjalizację z jego użyciem.
    private EagerAccounting() {
        super();
    }
}
