package adapter.incompatible_classes;


//Nowy interfejs, z którym ma pracować nasza aplikacja. Klasa NorskSvar, z której chcielibyśmy skorzystać, nie jest
// zgodna z tym interfejsem. Nasze opcje to:
//1. zmodyfikować klasę NorskSvar (może popsuć program albo być wręcz niemożliwe, jeśli pracujemy z zewnętrznym kodem),
//2. napisać funkcjonalność NorskSvar od nowa w zgodzie z nowym interfejsem (dużo powtórzonej pracy),
//3. zastosować Adapter, w wersji InheritanceAdapter albo CompositionAdapter.
public interface IResponsive {
    void respondHello();
    void respondNoProblem();
    void respondThanks();
    void respondToMessage(String msg);

    void setWordiness(boolean wordy);
}
