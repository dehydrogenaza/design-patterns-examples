package mediator.employees;


public class Programmer extends EmployeeBase {
    public Programmer(String name) {
        super(name);
    }

    //Indywidualna "odpowiedź" klasy na requesty przekazywane przez mediator.
    @Override
    protected String reactToRequest(String msg) {
        return Math.random() > 0.5 ? "(Gra teraz w: Factorio)" : "(Gra teraz w: DOTA 2)";
    }
}
