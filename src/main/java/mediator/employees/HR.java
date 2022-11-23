package mediator.employees;


public class HR extends EmployeeBase {
    public HR(String name) {
        super(name);
    }

    //Indywidualna "odpowiedź" klasy na requesty przekazywane przez mediator.
    @Override
    protected String reactToRequest(String msg) {
        return "Powtórzę \"" + msg + "\" programistom.";
    }
}
