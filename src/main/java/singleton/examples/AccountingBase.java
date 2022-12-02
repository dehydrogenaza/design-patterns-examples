package singleton.examples;

import java.util.Map;
import java.util.TreeMap;

//Na potrzeby przykładu, poszczególne Singletony dziedziczą ze wspólnej bazy, dzięki czemu nie muszę duplikować ich
// zawartości. W praktyce jest to raczej rzadko spotykane.
public abstract class AccountingBase {

    //Mapuje "osobę" do "pensji".
    protected final Map<String, Integer> payroll;

    //Dostępne fundusze na wypłaty.
    private long funds;

    protected AccountingBase() {
        this.payroll = makeSomePayroll();
        this.funds = 0;
    }

    //Metoda do testów.
    public void pay() {
        //Nazwa obecnej implementacji Singletona.
        System.out.println("\nWYPŁACAM Z: " + getClass().getSimpleName());
        displayFunds();

        for (Map.Entry<String, Integer> entry : payroll.entrySet()) {
            if (funds >= entry.getValue()) {
                System.out.println(" - " + entry.getKey() + ": otrzymuje " + entry.getValue() + " PLN");
                funds -= entry.getValue();
            } else {
                System.out.println(" - " + entry.getKey() + ": brak funduszy na wypłatę, trzeba zwolnić.");
                break;
            }
        }
    }

    public void addFunds(long funds) {
        this.funds += funds;
    }

    public void displayFunds() {
        System.out.println("Fundusze: " + funds + " PLN");
    }

    //Pomocnicza metoda do stworzenia przykładowych danych testowych.
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
