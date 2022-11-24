package observer.dispatch;

import observer.developer.Developer;
import observer.developer.Level;

import java.util.ArrayList;
import java.util.List;

//We wzorcu Obserwatora mamy "nadawcę", który przechowuje listę wszystkich subskrybentów oraz samych "subskrybentów".
//"Nadawca" tylko rozsyła wiadomości — nie interesuje go ich obsługa przez odbiorców.
//
//Tutaj, nasz "nadawca" implementuje IJobsBoard, zamiast stanowić jednostkową klasę — dzięki temu mielibyśmy
// możliwość stworzenia różnych implementacji i podmieniania ich w razie potrzeby.
public class ProgrammingJobsBoard implements IJobsBoard {
    private final List<Developer> subscribers = new ArrayList<>();

    //Rejestrujemy obiekt, który odtąd będzie odbierał wiadomości od IJobsBoard.
    //Dla wygody (chaining metod) użyłem IJobsBoard jako typu zwracanego.
    @Override
    public IJobsBoard subscribe(Developer developer) {
        subscribers.add(developer);
        return this;
    }

    //Wyrejestrowujemy obiekt — przestanie odbierać wiadomości.
    @Override
    public boolean unsubscribe(Developer developer) {
        return subscribers.remove(developer);
    }

    //Wywołujemy, by wysłać wiadomość do subskrybentów, zwykle przekazując im pewien kontekst — u nas są to parametry
    // 'level' oraz dowolna liczba 'requirements' (vararg).
    @Override
    public void notifySubscribers(Level level, String... requirements) {
        //Metoda .forEach wywoła lambdę na każdym elemencie listy 'subscribers'.
            //Tę lambdę należy czytać tak:
            //Weź 'dev' (pojedynczy element listy).
            //Dla 'dev' wywołaj .considerJob(level, requirements)
            //(.forEach zadba, by w następnym przebiegu 'dev' było kolejnym elementem)
        //To samo można zrobić dowolnym innym sposobem iteracji, nie ma wymogu używania lambd, ale tak jest IMO
        // szybciej.
        //Metoda .considerJob jest wspólnym "językiem" nadawcy i odbiorcy. U nas jest ona zdefiniowana jako
        // abstrakcyjna w klasie bazowej Developer, ale można by też wykorzystać w tym celu interfejs.
        subscribers.forEach(dev -> dev.considerJob(level, requirements));
    }

    //Dodatkowa metoda do sprawdzenia, czy ten "nadawca" ma jakichś subskrybentów.
    @Override
    public boolean hasSubscribers() {
        return !subscribers.isEmpty();
    }
}
