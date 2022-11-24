package observer.dispatch;

import observer.developer.Developer;
import observer.developer.Level;

//Interfejs "nadawcy". Koniecznym elementem wzorca są metody .subscribe i .notifySubscribers (może też być nazwana
// przykładowo .sendMessages), zwykle jest też .unsubscribe. Metoda .hasSubscribers nie jest konieczna.
public interface IJobsBoard {
    IJobsBoard subscribe(Developer developer);

    boolean unsubscribe(Developer developer);

    void notifySubscribers(Level level, String... requirements);

    boolean hasSubscribers();
}
