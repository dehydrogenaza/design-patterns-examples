package mediator.employees;


public class CEO extends EmployeeBase {
    public CEO(String name) {
        super(name);
    }

    //Indywidualna "odpowiedź" klasy na requesty przekazywane przez mediator.
    @Override
    protected String reactToRequest(String msg) {
        return "Idc :/";
    }

    //CEO inaczej odpowiada na "critical request" - pozostałe klasy dziedziczą metodę po klasie bazowej.
    @Override
    public void receiveCriticalRequest(String msg) {
        System.out.printf(getDisplayTextFormat(), "Hej, to *mój* request!");
    }

    //Metoda charakterystyczna tylko dla klasy CEO. Warto pamiętać, że nie możemy z niej korzystać posługując się typem
    // EmployeeBase (trzeba rzutować na CEO).
    public void boast() {
        System.out.println("Zapracowałem na swój status ciężką pracą i milionem bezzwrotnej pożyczki od Taty.");
    }
}
