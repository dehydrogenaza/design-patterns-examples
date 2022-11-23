package mediator.employees;


public class Accountant extends EmployeeBase {
    public Accountant(String name) {
        super(name);
    }

    //Indywidualna "odpowiedź" klasy na requesty przekazywane przez mediator.
    @Override
    protected String reactToRequest(String msg) {
        return "Nie mam czasu, hajs się musi zgadzać.";
    }
}
