package state.context;

import state.states.*;

import java.util.HashMap;
import java.util.Map;

//Poniższa klasa będzie zachowywała się inaczej w zależności od statusu "konta" użytkownika, z rozróżnieniem na trzy
// możliwości:
//1. Użytkownik niezalogowany — NotAuthenticatedState
//2. Użytkownik zalogowany, ale nie podał adresu wysyłki — NeedsShippingAddressState
//3. Użytkownik zalogowany z podanym adresem — ReadyForCheckoutState
//Osiągamy to dzięki oddelegowaniu pewnych zachowań klasy do oddzielnych klas "stanów", które następnie są (przez
// kompozycję) podpinane do OnlineStore.
//
//Klasę tego typu nazywamy KONTEKSTEM Stanu.
public class OnlineStore {

    //To, co aktualnie kryje się pod zmienną `state` decyduje o zachowaniu OnlineStore.
    private IStoreBehavior state;

    //Koszyk, przechowujący nazwę produktu i liczbę sztuk zamawianego towaru.
    private final Map<String, Integer> cart = new HashMap<>();

    //Adres wysyłki; musi zostać podany przed finalizacją zakupów.
    private String shippingAddress = "";

    //W konstruktorze ustawiamy domyślny Stan. U nas jest on akurat ustawiony na sztywno, ale możliwe są też inne
    // rozwiązania (stała, parametr, oddzielna metoda do inicjalizacji).
    public OnlineStore() {
        changeState(new NotAuthenticatedState(this));
    }

    //Tą metodą zmieniamy zachowanie programu. Kolejne wywołania metod .addToCart(), .checkout(), .signIn() i
    // .signOut() będą zachowywały się już zgodnie z nowym stanem.
    public void changeState(IStoreBehavior newState) {
        this.state = newState;
    }

    //Warto zwrócić uwagę, że choć poniższe metody odpowiadają tym z interfejsu IStoreBehavior, to klasa OnlineStore
    // NIE IMPLEMENTUJE TEGO INTERFEJSU. OnlineStore jedynie deleguje obsługę pewnych żądań do faktycznych
    // implementacji przechowywanego w zmiennej `state` Stanu.
    //Metody zawarte w OnlineStore NIE MUSZĄ pokrywać się z tymi z samych stanów — OnlineStore mógłby np. eksponować
    // bogatszy interfejs (z różnymi wariantami tych metod) albo w ogóle operować na innym poziomie abstrakcji. W tym
    // przypadku jest tylko jedna subtelna różnica: metody z interfejsu IStoreBehavior zwracają pewne wartości (które
    // mogą pomóc np. w obsłudze błędów), a poniższe metody nic nie zwracają.
    public void addToCart(String productID) {
        state.addToCart(productID);
    }

    public void checkout() {
        System.out.println(state.getStateName());
        state.checkout();
    }

    public void signIn(String username, String password) {
        System.out.println(state.getStateName());
        state.signIn(username, password);
    }

    public void signOut() {
        System.out.println(state.getStateName());
        state.signOut();
    }

    //Stany mogą wchodzić w interakcję z Kontekstem, wywołując jego metody dzięki przechowywanej przez nie referencji
    // do kontekstu.
    public boolean attemptSignIn(String username, String password) {
        System.out.println("Jakiś ekran logowania...");
        System.out.println("\tsignIn: " + username);

        String hiddenPassword = "*".repeat(password.length());
        System.out.println("\thasło: " + hiddenPassword);

        //Ta implementacja daje 50% szansy na udane zalogowanie. Możecie sobie ustawić, by zawsze zwracała `true`
        // lub `false` jeśli wolicie testować w ten sposób.
        if (Math.random() > 0.5) {
            System.out.println("Udane logowanie!\n");
            return true;
        } else {
            System.out.println("Zła nazwa użytkownika lub hasło :/\n");
            return false;
        }
    }

    //Kolejna z metod interakcji Stanu z Kontekstem.
    public void attemptSignOut() {
        System.out.println("Wylogowano.\n");
        state = new NotAuthenticatedState(this);
    }

    //Po pierwsze, jest to przykład Settera, który wreszcie uzasadnia istnienie Setterów, czyli faktycznie robi coś
    // innego niż przypisanie wartości do pola.
    //Po drugie, ta metoda obrazuje zmianę Stanu przez sam Kontekst. W tym wzorcu nie jest ustalone, czy zmieniać
    // Stan mogą JEDYNIE INNE STANU, JEDYNIE KONTEKST, czy też JEDNO I DRUGIE. Gdybym miał faktycznie implementować
    // ten wzorzec, zależnie od wygody wybrałbym raczej opcję 1 lub 2 (by być konsekwentnym). Ponieważ tutaj chodziło
    // o przykład, zastosowany jest wariant 3.
    public void setShippingAddress(String address) {
        if (address == null || address.isEmpty())
            throw new IllegalArgumentException("Nie można podać pustego adresu!");

        System.out.println("Ustawiam adres: " + address + "\n");
        this.shippingAddress = address;

        //Bardziej profesjonalne byłoby utworzenie enuma, np. "StoreState", listującego wszystkie możliwe stany, i
        // wykorzystywanie jego do sprawdzania i porównywania Stanów. Tutaj dla uproszczenia po prostu sprawdzam samą
        // klasę Stanu.
        if (state.getClass() == NeedsShippingAddressState.class) {
            changeState(new ReadyForCheckoutState(this));
        }
    }

    public String getShippingAddress() {
        return shippingAddress;
    }

    public Map<String, Integer> getCart() {
        return cart;
    }
}
