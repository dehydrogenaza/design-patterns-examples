package mediator.mediator;

import mediator.employees.EmployeeBase;
import mediator.employees.CEO;

import java.util.List;

//Mediator to wzorzec, który upraszcza *dwustronną komunikację* między siecią obiektów. Przez "komunikację" można
// rozumieć dowolne przesyłanie danych czy wywoływanie metod. Poszczególne klasy, zamiast bezpośrednio "rozmawiać" ze
// sobą, komunikują się z mediatorem, który przekazuje ich żądania dalej. Znacznie ułatwia to późniejsze modyfikacje
// takiej sieci.
//
//W poniższym przykładzie tworzymy mediator "firmy", w której mogą być CEO, Accountant, HR i Programmer. Każdy z nich
// może wysyłać "wiadomości" (mogłyby to być w rzeczywistości dowolne wywołania metod) do reszty zespołu albo tylko
// do CEO. To mediator zadecyduje, komu i w jaki sposób przekazać te wiadomości.
public class WorkplaceMediator implements IMediator {
    //Lista wszystkich pracowników. Wszystkie grupy dziedziczą po EmployeeBase, więc mogę je zmieścić do jednej
    // kolekcji, ale wcale nie jest to takie częste w tym wzorcu. Często mediatora wykorzystuje się do skomunikowania
    // różnych podsystemów programu (bardzo często są to np. różne elementy interfejsu graficznego), niektóre mogą
    // nawet być Singletonami.
    private final List<EmployeeBase> employees;

    //Kluczem w tym wzorcu jest to, by mediator miał odniesienie do wszystkich "celów" (tutaj: listy pracowników), a
    // poszczególne "cele" (pracownicy) mieli odniesienie do mediatora.
    //W konstruktorze rejestruję mediatora ('this') u każdego pracownika. Jest to po prostu setter, ale mógłby np.
    // być "setter jednorazowy" ze sprawdzeniem, czy inny mediator nie został już zarejestrowany.
    public WorkplaceMediator(List<EmployeeBase> employees) {
        this.employees = employees;
        for (EmployeeBase employee : employees) {
            employee.registerMediator(this);
        }
    }

    //Każdy pracownik może wywołać tę metodę dzięki swojemu odniesieniu do mediatora.
    //W odpowiedzi mediator wywoła pewną metodę na wszystkich pracownikach — tu w przykładzie wybierze inną metodę,
    // jeśli nadawcą był CEO, a inną w pozostałych przypadkach. Typowo na tym etapie parametr "nadawca" (sender)
    // decyduje o tym, jakie metody i na czym wywołać.
    @Override
    public void sendToAll(EmployeeBase sender, String request) {
        boolean fromBoss = sender.getClass() == CEO.class;
        for (EmployeeBase employee : employees) {
            if (fromBoss) {
                employee.receiveCriticalRequest(request);
            } else {
                employee.receiveRequest(request);
            }
        }
    }

    //Mediator rzadko przesyła wiadomości do WSZYSTKICH odbiorców — jednym z celów tego wzorca jest właśnie
    // uporządkowanie komunikacji, tak by klasy nie musiały obsługiwać "nieistotnych" dla nich żądań.
    // W tej metodzie wywoływani są tylko pracownicy INNI niż nadawca.
    @Override
    public void sendToOthers(EmployeeBase sender, String request) {
        for (EmployeeBase employee : employees) {
            if (employee == sender) continue;

            employee.receiveRequest(request);
        }
    }

    //Z kolei w tej metodzie wywoływani są tylko CEO.
    @Override
    public void sendToCEO(EmployeeBase sender, String request) {
        for (EmployeeBase employee : employees) {
            if (employee.getClass() == CEO.class) {
                employee.receiveRequest(request);
            }
        }
    }

    //Nie ma przeszkód, by mediator zawierał też metody przeznaczone do wykorzystania "z zewnątrz", spoza
    // zarejestrowanych "użytkowników" mediatora — choć nie jest to stały element samego wzorca.
    //Tutaj każda klasa może wywołać .CEOBoast(), a mediator wyszuka CEO i wywoła jego unikalną metodę .boast().
    @Override
    public void CEOBoast() {
        for (EmployeeBase employee : employees) {
            if (employee.getClass() == CEO.class) {
                //Hard-cast do CEO, ponieważ 'employee' jest typu 'EmployeeBase',
                // a 'EmployeeBase' nie ma potrzebnej metody .boast().
                //Oczywiście to nie jest konieczna część wzorca — jak pisałem na początku, bardzo często mediator
                // będzie trzymał referencje do konkretnych, określonych obiektów.
                ((CEO) employee).boast();
            }
        }
        System.out.println("Wszyscy: xD");
    }
}
