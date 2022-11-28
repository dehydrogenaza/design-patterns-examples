package adapter.inheritance_example;

import adapter.incompatible_classes.*;

//Wariant wzorca Adapter wykorzystujący DZIEDZICZENIE.
//
//Adapter DZIEDZICZY PO ADAPTOWANEJ (STAREJ) KLASIE oraz IMPLEMENTUJE NOWY INTERFEJS.
//
//Dzięki temu InheritanceAdapter ma bezpośredni dostęp do pól/metod protected starej klasy, co może ułatwiać
// adaptację, o ile stara klasa była otwarta na dziedziczenie, a np. nie eksponowała publicznie pewnych przydatnych
// metod pomocniczych.
//Minusy tego rozwiązania to:
//    1) taki Adapter jest potencjalnie mniej elastyczny (na ogół łatwiej jest rozszerzać funkcjonalność klas
//    opartych o kompozycję), co może nie byc problemem, jeśli Adapter jest mocno wyspecjalizowany i niemal na pewno
//    nie będziemy w nim musieli "podmieniać" klasy bazowej,
//    2) dziedziczenie jest często nadużywane, prowadzi do nawarstwiania niepotrzebnych zależności między
//    komponentami, komplikuje strukturę kodu, i zachęca do łamania enkapsulacji.
public class InheritanceAdapter extends NorskSvar implements IResponsive {

    //Pozwala ustawić, czy program będzie odpowiadał krótkimi/długimi zdaniami.
    private boolean wordy;

    //Kilka stałych definiujących wiadomości, na jakie może odpowiadać nasza klasa. Mógłby to być enum!
    private static final String HELLO_MSG = "hallo";
    private static final String THANKS_MSG = "takk";
    private static final String THERE_YOU_GO_MSG = "værsågod";

    //W tej wersji dziedziczymy po NorskSvar, którego również musimy inicjalizować w konstruktorze (wywołaniem "super").
    public InheritanceAdapter(String name, boolean wordy) {
        super(name);
        this.wordy = wordy;
    }

    //Implementacje metod z nowego interfejsu, u nas sprowadzone do pomocniczych "skrótów" wywołujących "główną"
    // metodę .respondToMessage().
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

    //Istota wzorca Adapter. Ta metoda "tłumaczy" nowy interfejs IResponsive na wersję zrozumiałą dla adaptowanej
    // klasy NorskSvar (z której InheritanceAdapter dziedziczy — ma zatem jej metody/pola protected).
    @Override
    public void respondToMessage(String originalMsg) {
        String translatedMsg = switch(originalMsg) {
            case "cześć" -> HELLO_MSG;
            case "dzięki" -> THANKS_MSG;
            case "proszę" -> THERE_YOU_GO_MSG;
            default -> originalMsg;
        };
        //Metody .svarLangt() oraz .svarKort() odziedziczone są z klasy NorskSvar.
        if (wordy) System.out.println(svarLangt(translatedMsg));
        else System.out.println(svarKort(translatedMsg));
    }

    //Kolejna metoda z IResponsive.
    public void setWordiness(boolean wordy) {
        this.wordy = wordy;
    }
}
