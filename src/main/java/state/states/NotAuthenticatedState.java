package state.states;

import state.context.OnlineStore;

//Stan "użytkownika niezalogowanego".
public class NotAuthenticatedState extends BaseState implements IStoreBehavior {

    public NotAuthenticatedState(OnlineStore store) {
        super(store);
    }

    //Zachowanie .checkout() charakterystyczne tylko dla tego Stanu.
    @Override
    public boolean checkout() {
        System.out.println("Najpierw się zaloguj.\n");
        return false; //Wartość zwracana może służyć np. do obsługi błędów.
    }

    //Metoda .signIn() jest już dostępna w klasie bazowej, ale w obecnym Stanie zależy nam na innym zachowaniu,
    // dlatego przesłaniamy metodę.
    //Ta metoda pokazuje, że w tym wzorcu same Stany mogą zarządzać przechodzeniem w inne Stany, nie jest to jednak
    // wymogiem (patrz opis metody .setShippingAddress() w klasie OnlineStore).
    @Override
    public boolean signIn(String username, String password) {
        //Wywołujemy pomocniczą metodę z Kontekstu (referencja odziedziczona jest z BaseState).
        boolean authenticated = store.attemptSignIn(username, password);

        //Nieudane logowanie, pozostajemy w stanie NotAuthenticated.
        if (!authenticated) return false;

        if (!store.getShippingAddress().isEmpty()) {
            //Kontekst ma adres: Maszyna Stanu przechodzi w ReadyForCheckoutState.
            store.changeState(new ReadyForCheckoutState(store));
        } else {
            //Brak adresu: przechodzimy w NeedsShippingAddressState.
            store.changeState(new NeedsShippingAddressState(store));
        }

        return true;
    }

    //Jak wyżej, przesłaniamy metodę z klasy bazowej.
    @Override
    public void signOut() {
        System.out.println("Nie jesteś teraz zalogowany!\n");
    }

    //Metoda .addToCart() z interfejsu IStoreBehavior jest odziedziczona z klasy bazowej.
    //Z kolei metoda .getStateName() z tego interfejsu ma implementację domyślną.
}
