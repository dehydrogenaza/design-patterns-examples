package adapter;

import adapter.composition_example.CompositionAdapter;
import adapter.inheritance_example.InheritanceAdapter;
import adapter.incompatible_classes.*;

//Klasa do testów.
public class AdapterClient {

    public static void main(String[] args) {
        String username = "Bob";

        //Z adaptera możemy korzystać dzięki interfejsowi. Kod kliencki nie musi nawet "wiedzieć", że korzysta z
        // adaptera.
        IResponsive norwegianResponder = new CompositionAdapter(new NorskSvar(username), true);

        System.out.println("ADAPTER PRZEZ KOMPOZYCJĘ, OPCJA GADATLIWA:");
        haveConversation(norwegianResponder, username);

        System.out.println("\nADAPTER PRZEZ KOMPOZYCJĘ, OPCJA MNIEJ GADATLIWA:");
        norwegianResponder.setWordiness(false);
        haveConversation(norwegianResponder, username);

        System.out.println("\nADAPTER PRZEZ DZIEDZICZENIE:");
        norwegianResponder = new InheritanceAdapter(username, true);
        haveConversation(norwegianResponder, username);
    }

    //Test metod z interfejsu IResponsive. Klasa NorskSvar wymagała od nas, byśmy wywoływali jedną z metod .svarKort()
    // lub .svarLangt() (odpowiedzialnych odpowiednio za krótką lub długą odpowiedź) podając jako parametr wiadomość,
    // na którą obiekt ma odpowiedzieć. Następnie musieliśmy sami wyświetlić zwrócony String.
    //W naszej obecnej implementacji chcemy, by implementacja IResponsive:
    //    1) była odpowiedzialna za wyświetlanie, a nie zwracała nam Stringa,
    //    2) miała oddzielne metody do najczęściej używanych wiadomości,
    //    3) pozwalała jednorazowo ustawić opcję krótką/długą na wiele wiadomości.
    //Adaptera używamy po to, by pogodzić nowe wymagania z "ograniczeniami" klasy NorskSvar, zamiast np. tworzyć
    // zupełnie nową klasę lub drastycznie modyfikować starą.
    private static void haveConversation(IResponsive norwegianResponder, String username) {
        System.out.println("cześć, jestem " + username);
        norwegianResponder.respondHello();

        System.out.println("to dla Ciebie!");
        norwegianResponder.respondThanks();

        System.out.println("dzięki za pomoc");
        norwegianResponder.respondNoProblem();

        String customMsg = "proszę";
        System.out.println(customMsg);
        norwegianResponder.respondToMessage(customMsg);

        customMsg = "do widzenia";
        System.out.println(customMsg);
        norwegianResponder.respondToMessage(customMsg);
    }
}
