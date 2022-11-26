package state.states;

import state.context.OnlineStore;

import java.util.Map;

//Poszczególne stany nie tylko implementują IStoreBehavior, ale też dziedziczą po poniższej klasie, która zapewnia im
// wspólne implementacje części metod.
abstract public class BaseState {

    //Referencja do Kontekstu.
    protected final OnlineStore store;

    //"Skrót" do zawartej w Kontekście zmiennej `cart`, ustawiony w konstruktorze BaseState dla wygody użytkownika.
    // Równie dobrze moglibyśmy nie mieć tego pola i wszędzie zastąpić je wywołaniem `store.getCart()`.
    protected final Map<String, Integer> cart;

    protected BaseState(OnlineStore store) {
        this.store = store;
        this.cart = store.getCart();
    }

    //Metoda .addToCart() jest identyczna w każdym ze stanów (niezalogowani użytkownicy też mogą wybierać produkty).
    // Mogłaby być częścią samego OnlineStore, jednak wydzielenie jej do Stanu ułatwia późniejszą modyfikację
    // zachowania (gdybyśmy np. chcieli później zaimplementować stan ServiceNotAvailable który przesłania tę metodę i
    // wyświetla jakiś błąd).
    public int addToCart(String productID) {
        if (cart.containsKey(productID)) {
            //Jeśli produkt o danej nazwie jest w "koszyku", zwiększamy liczbę sztuk o 1.
            cart.put(productID, cart.get(productID) + 1);
        } else {
            //W przeciwnym wypadku dodajemy produkt i ustawiamy liczbę sztuk na 1.
            cart.put(productID, 1);
        }
        return cart.size();
    }

    //Domyślna implementacja `signIn`, będzie przesłonięta w NotAuthenticatedState (w pozostałych Stanach zadziała
    // implementacja odziedziczona z tej klasy).
    //Dzięki wprowadzaniu implementacji domyślnych/bazowych unikamy duplikacji kodu. Gdybyśmy tego nie zrobili,
    // NeedsShippingAddressState oraz ReadyForCheckoutState musiałyby powtórzyć identyczny kod (bo i ich spodziewane
    // zachowanie jest identyczne).
    public boolean signIn(String username, String password) {
        System.out.println("Jesteś już zalogowany!\n");
        return true;
    }

    //Jak wyżej. Tutaj wywołujemy pomocniczą metodę z Kontekstu.
    public void signOut() {
        store.attemptSignOut();
    }

    //Klasa bazowa nie proponuje żadnego wariantu metody .checkout(), niezbędnej do uzyskania zgodności z interfejsem
    // IStoreBehavior — każdy Stan dziedziczący będzie musiał zaimplementować tę metodę od nowa.
}
