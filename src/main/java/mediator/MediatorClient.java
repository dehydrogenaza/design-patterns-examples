package mediator;

import mediator.employees.*;
import mediator.mediator.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

//Klasa do testów, nie ma nic wspólnego ze wzorcem.
public class MediatorClient {

    public static void main(String[] args) {

        //Na potrzeby testu, wszystkich pracowników będziemy trzymać w jednej kolekcji.
        List<EmployeeBase> employees = buildEmployeeList();
        //Mediator dostaje jako parametr referencję do wszystkich "celów", a w swoim konstruktorze "rejestruje się" u
        // każdego z nich.
        IMediator mediator = new WorkplaceMediator(employees);

        System.out.println("\n* DO WSZYSTKICH OD KAŻDEGO KOLEJNO *\n");
        for (int i = 0; i < employees.size(); i++) {
            //employees.get(i) - odwołujemy się do obiektu pracownika
            //          .sendToAll - pracownik tą metodą może wysłać "request" do wszystkich, poprzez mediator
            employees.get(i).sendToAll("REQUEST NUMBER " + i);
            System.out.println("-----------------\n");
        }

        //Użyte do losowania, który pracownik wyśle "request"
        Random rng = new Random();

        int senderIndex = rng.nextInt(employees.size());
        System.out.println("\n* ROZSYŁANIE DO RESZTY (POZA NADAWCĄ) *\n");
        employees.get(senderIndex).sendToOthers("MY REQUEST");
        System.out.println("-----------------\n");

        senderIndex = rng.nextInt(employees.size());
        System.out.println("\n* ROZSYŁANIE DO CEO *\n");
        employees.get(senderIndex).sendToManagers("KOCHANY SZEFIE UwU");
        System.out.println("-----------------\n");

        //Nie tylko obiekty będące częścią "sieci" mediatora mogą z niego korzystać.
        //Nie jest to część wzorca, ale nie ma żadnego problemu, by mediatora używać też "z zewnątrz". Tutaj z maina
        // wywołujemy '.CEOBoast()', które nie pochodzi od żadnego konkretnego adresata z "wnętrza" sieci.
        System.out.println("\n* SPECJALNA WIADOMOŚĆ OD CEO *\n");
        mediator.CEOBoast();
        System.out.println("-----------------\n");
    }

    //Pomocnicza metoda dodająca paru pracowników do listy, na potrzeby testów.
    private static List<EmployeeBase> buildEmployeeList() {
        List<EmployeeBase> employees = new ArrayList<>();
        employees.add(new Accountant("Smutna pani w okularach"));
        employees.add(new HR("Pumpkin Spice Latte")); //btw nic złego w Pumpkin Spice...
        employees.add(new Programmer("Człowiek we flanelowej koszuli")); //ani we flanelowych koszulach
        employees.add(new Programmer("Długowłosy w glanach"));
        employees.add(new Programmer("Jegomość z Indii"));
        employees.add(new Programmer("Azjata mądrzejszy niż cała reszta zespołu razem wzięta"));
        employees.add(new CEO("Krzysztof Jarzyna ze Szczecina"));

        return employees;
    }
}
