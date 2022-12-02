package singleton.examples.eager;


import java.util.Map;
import java.util.TreeMap;

public enum EagerEnumAccounting {
    INSTANCE;

    private final Map<String, Integer> payroll;
    private long funds;

    EagerEnumAccounting() {
        this.payroll = makeSomePayroll();
        this.funds = 0;
    }

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
