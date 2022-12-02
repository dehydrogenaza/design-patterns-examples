package singleton.examples.eager;


import java.util.Map;
import java.util.TreeMap;

//Wersja nie różni się funkcjonalnie od EagerAccounting, z wyjątkiem tego, że wykorzystuje enuma.
//
//Taki enum powinien mieć tylko jedną wartość (zwykle zwaną INSTANCE). Jest ona domyślnie inicjalizowana statycznie
// przez Javę (i zawsze ma tylko jedną kopię). Nie musimy tworzyć gettera — do instancji odnosimy się po prostu przez
// INSTANCE.
//
//Poniższy przykład może wydawać się długi, ponieważ DUPLIKUJE zawartość AccountingBase. Zwróćcie uwagę, że
// EagerEnumAccounting NIE DZIEDZICZY (ponieważ enumy nie mogą dziedziczyć). Akurat tutaj prowadzi to do duplikacji
// kodu, ale zwykle nie jest problemem, ponieważ Singletony rzadko kiedy dziedziczą po innych klasach.
public enum EagerEnumAccounting {
    INSTANCE; //Wartość, przez którą odnosimy się do instancji.

    //Konstruktor ENUMa jest domyślnie prywatny.
    EagerEnumAccounting() {
        this.payroll = makeSomePayroll();
        this.funds = 0;
    }



    //* * *
    //Reszta klasy jest identyczna co AccountingBase
    //* * *


    private final Map<String, Integer> payroll;
    private long funds;


    public void pay() {
        System.out.println("\nWYPŁACAM Z: " + getClass().getSimpleName());
        displayFunds();

        for (Map.Entry<String, Integer> entry : payroll.entrySet()) {
            if (funds > entry.getValue()) {
                System.out.println(" - " + entry.getKey() + ": otrzymuje " + entry.getValue() + " PLN");
                funds -= entry.getValue();
            } else {
                System.out.println(" - " + entry.getKey() + ": brak funduszy na wypłatę, trzeba zwolnić.");
            }
        }
    }

    public void addFunds(long funds) {
        this.funds += funds;
    }

    public void displayFunds() {
        System.out.println("Fundusze: " + funds + " PLN");
    }

    private Map<String, Integer> makeSomePayroll() {
        TreeMap<String, Integer> payroll = new TreeMap<>();
        payroll.put("CEO Kowalski", 85000);
        payroll.put("CTO (szwagier CEO)", 55000);
        payroll.put("Manager Nowak", 20000);
        payroll.put("Manager Wiśniewski", 19000);
        payroll.put("Manager Dąbrowska", 18000);
        payroll.put("Pracownik", 4200);

        return payroll;
    }
}
