package mediator.employees;

import mediator.mediator.IMediator;

import java.util.function.BiConsumer;

//Wspólna baza wszystkich typów "pracownika". Mógłby też być wspólny interfejs, ale uznałem, że jest na tyle dużo
// podobieństw w implementacjach poszczególnych metod, że lepiej zrobić to przez dziedziczenie po klasie abstrakcyjnej.
public abstract class EmployeeBase {
    protected final String name;

    //Kluczowe w tym wzorcu jest to, by każdy komponent miał referencję do mediatora (zamiast do innych
    // indywidualnych obiektów, z którymi musi się komunikować).
    protected IMediator mediator;

    //Nie ustawiamy mediatora w konstruktorze, by uniknąć problemu "zapętlonej referencji" (czyli: do stworzenia
    // mediatora potrzebujemy listy pracowników, ale do stworzenia pracowników potrzebowalibyśmy mediatora).
    //Zamiast tego, mediatora musimy zarejestrować metodą .registerMediator(), gdy ten będziemy gotowy - przy czym
    // zwykle dba o to sam mediator w swoim konstruktorze.
    protected EmployeeBase(String name) {
        this.name = name;
    }

    //Każdy obiekt połączony z mediatorem może "wysłać wiadomość" (czyt. dowolne żądanie) do każdego innego obiektu w
    // "sieci".
    public void sendToAll(String msg) {
        System.out.printf(getDisplayTitleFormat(msg), "Rozsyłam wszystkim");
        //mediator.sendToAll(this, msg);
        tryMediator(mediator::sendToAll, msg); //Stworzyłem sobie tak trochę "dla picu" pomocniczą metodę
        // sprawdzającą, czy mediator nie jest nullem, i wywołuję ją w ten oto sposób, ale to w tym przykładzie "sztuka
        // dla sztuki" która absolutnie nie jest częścią wzorca. Jeśli Wam przeszkadza, zakomentujcie ją i
        // odkomentujcie linijkę wyżej, zadziała tak samo. To samo dotyczy pozostałych metod .send.
    }

    //Możemy też ograniczyć liczbę adresatów. Obsługa tego żądania spada na mediatora, który roześle wiadomość dalej.
    //"Sender" (czyli obecna klasa) nie powinien się przejmować, w jaki sposób wiadomość dotrze do adresatów, to jest
    // zadanie mediatora.
    public void sendToOthers(String msg) {
        System.out.printf(getDisplayTitleFormat(msg), "Rozsyłam pozostałym");
        //mediator.sendToOthers(this, msg);
        tryMediator(mediator::sendToOthers, msg);
    }

    //Jak wyżej.
    public void sendToManagers(String msg) {
        System.out.printf(getDisplayTitleFormat(msg), "Wysyłam do szefa");
        //mediator.sendToCEO(this, msg);
        tryMediator(mediator::sendToCEO, msg);
    }

    //Ta metoda wywoływana jest przez sam mediator i pozwala na *odbieranie* komunikatów. To, co dalej się stanie z
    // tym requestem, zależy już od konkretnej implementacji odbiorcy, ale NIE POWINNO ZALEŻEĆ w żaden sposób od klasy
    // nadawcy — to WorkplaceMediator powinien zdecydować, jaka metoda będzie wykonana, a odbiorca ma tylko ją wykonać.
    //
    //W tym przykładzie kluczowa jest metoda abstrakcyjna '.reactToRequest', którą implementuje każdy z wariantów.
    public void receiveRequest(String msg) {
        System.out.printf(getDisplayTextFormat(), reactToRequest(msg));
    }

    //Inna metoda "odbiorcza" wywoływana przez sam mediator. Wywoływana jest wtedy, gdy obiekt otrzyma "wiadomość" od
    // "CEO" - ale to nie sam obiekt ma o tym wiedzieć, sprawdza to mediator.
    public void receiveCriticalRequest(String msg) {
        System.out.printf(getDisplayTextFormat(), "Oh shit muszę zrobić \"" + msg + "\"");
    }

    //Jest to de facto setter dla mediatora. Można tu także dodać sprawdzenie, czy nie nadpisujemy innego mediatora,
    // robiąc z tego "setter jednorazowy".
    public void registerMediator(IMediator mediator) {
        this.mediator = mediator;
    }

    //Pomocnicza metoda, która zabezpiecza nas przed wysyłaniem żądania PRZED zarejestrowaniem mediatora.
    //
    // Ten przykład nie ma zbytniego sensu, bo zamieniamy po prostu NullPointerException (który otrzymamy, jeśli
    // spróbujemy wywołać null-owy mediator) na jakiś inny Exception, ale chodzi tylko o pokazanie, że można
    // spróbować jakoś obsłużyć tę sytuację.
    private void tryMediator(BiConsumer<EmployeeBase, String> method, String msg) {
        if (mediator == null) {
            throw new IllegalStateException("Mediator dla tego obiektu nie został zarejestrowany!");
        }
        method.accept(this, msg);
    }

    //Pomocnicze metody do formatowania wyświetlanych tekstów.
    //Jeśli zastanawiają Was te dziwne randomowe znaczki, to poczytajcie o ANSI Escape Codes ;-)
    protected String getDisplayTextFormat() {
        return " - " + name + " ("
                + getClass().getSimpleName()
                + "): \u001B[30;47m"
                + "%s\u001B[0m\n";
    }

    protected String getDisplayTitleFormat(String msg) {
        return "%s \""
                + msg + "\" od \""
                + name.toUpperCase()
                + "\" (" + getClass().getSimpleName() + "):\n";
    }

    //Deklaracja metody będąca faktyczną "odpowiedzią" danej klasy na request przekazany przez mediator.
    abstract protected String reactToRequest(String msg);
}
