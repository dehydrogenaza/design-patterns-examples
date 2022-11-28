package adapter.composition_example;

import adapter.incompatible_classes.IResponsive;
import adapter.incompatible_classes.NorskSvar;

//Wariant wzorca Adapter wykorzystujący KOMPOZYCJĘ.
//
//Adapter MA REFERENCJĘ DO OBIEKTU ADAPTOWANEJ (STAREJ) KLASY oraz IMPLEMENTUJE NOWY INTERFEJS.
//
//InheritanceAdapter nie ma bezpośredniego dostępu do pól i metod starej klasy, co może być ograniczeniem (jeśli np.
// stara klasa eksponuje część przydatnych metod jako protected, a nie public). Plusem tego rozwiązania jest to, że
// taki kod łatwiej jest rozszerzać — na ogół łatwiej jest "podmienić" elementy kompozycji niż zmienić strukturę
// dziedziczenia.
//
//Większość pól i metod tej klasy jest analogiczna co w przypadku InheritanceAdapter. Poniższe komentarze wskazują
// tylko na różnice.
public class CompositionAdapter implements IResponsive {

    //Kluczowy punkt tej wersji wzorca. Referencja do starej klasy.
    private final NorskSvar norwegian;
    private boolean wordy;

    private static final String HELLO_MSG = "hallo";
    private static final String THANKS_MSG = "takk";
    private static final String THERE_YOU_GO_MSG = "værsågod";

    //Konstruktor wymaga przekazania instancji starej klasy.
    public CompositionAdapter(NorskSvar norwegian, boolean wordy) {
        this.norwegian = norwegian;
        this.wordy = wordy;
    }

    @Override
    public void respondHello() {
        respondToMessage(HELLO_MSG);
    }

    @Override
    public void respondNoProblem() {
        respondToMessage(THANKS_MSG);
    }

    @Override
    public void respondThanks() {
        respondToMessage(THERE_YOU_GO_MSG);
    }

    //Adaptacja polega na wywołaniu "starej" metody przez metodę z nowego interfejsu.
    @Override
    public void respondToMessage(String originalMsg) {
        String translatedMsg = switch(originalMsg) {
            case "cześć" -> HELLO_MSG;
            case "dzięki" -> THANKS_MSG;
            case "proszę" -> THERE_YOU_GO_MSG;
            default -> originalMsg;
        };

        if (wordy) System.out.println(norwegian.svarLangt(translatedMsg));
        else System.out.println(norwegian.svarKort(translatedMsg));
    }

    public void setWordiness(boolean wordy) {
        this.wordy = wordy;
    }
}
