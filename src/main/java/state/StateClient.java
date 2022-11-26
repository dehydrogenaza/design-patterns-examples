package state;

import state.context.OnlineStore;


//Klasa do testów, nie jest częścią wzorca.
public class StateClient {

    public static void main(String[] args) {

        //OnlineStore wykorzystuje MASZYNĘ STANU do kontrolowania, czy użytkownik jest a) niezalogowany, b)
        // zalogowany, ale nie podał adresu wysyłki, c) zalogowany i podał potrzebne dane.
        OnlineStore store = new OnlineStore();

        //Poniżej znajduje się kilka testowych metod. Kluczowe jest to, że metody .checkout(), .signIn() oraz
        // .signOut() robią różne rzeczy w zależności od tego, w jakim stanie znajduje się program, tzn. na wynik
        // wywołania tych metod wpływają metody wywołane uprzednio.
        //
        //Zwróćcie uwagę, że obecna implementacja .signIn() zwraca losowe wyniki, więc wyniki całego programu też
        // będą się różnić z uruchomienia na uruchomienie (nie jest to związane ze wzorcem, tak sobie po prostu
        // zrobiłem). Jeśli chcecie mieć deterministyczne wyniki, zmieńcie metodę .attemptSignIn() w OnlineStore.
        store.checkout();
        store.signIn("xXx_DotaSlayer420_xXx", "1234");
        store.checkout();
        //Poniższa metoda nie jest częścią maszyny stanu: jest zwyczajnie zaimplementowana w OnlineStore.
        store.setShippingAddress("Kaczki Średnie 15, Polska");
        store.checkout();
        store.signIn("bob", "#58bf*@b81bGJh18fhw8kK21$$+[");
        store.addToCart("Komputer gejmingowy");
        store.addToCart("Świecąca klawiatura");
        store.addToCart("Monitor");
        store.addToCart("Monitor");
        store.checkout();
        store.signOut();
        store.signOut();
        store.addToCart("Jabłko");
        store.checkout();
        store.signIn("someone", "password");
        store.checkout();
    }
}
