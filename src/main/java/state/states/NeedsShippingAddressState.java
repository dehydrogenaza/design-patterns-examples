package state.states;

import state.context.OnlineStore;

//Stan "użytkownika zalogowanego, bez podanego adresu wysyłki".
public class NeedsShippingAddressState extends BaseState implements IStoreBehavior {

    public NeedsShippingAddressState(OnlineStore store) {
        super(store);
    }

    //Zachowanie .checkout() charakterystyczne tylko dla tego Stanu.
    @Override
    public boolean checkout() {
        System.out.println("Najpierw podaj adres dostawy.\n");
        return false;
    }

    //Metoda .signIn() jest już dostępna w klasie bazowej, ale tutaj wykorzystujemy ją w specjalny sposób: najpierw
    // wywołujemy wersję "bazową", a potem dodajemy jeszcze dodatkowe zachowanie.
    @Override
    public boolean signIn(String username, String password) {
        super.signIn(username, password); //Taka notacja pozwala wywołać "bazową" metodę z metody przesłoniętej.
        System.out.println("\t-> teraz podaj adres dostawy.\n");
        return true;
    }

    //Metody .addToCart() oraz .signOut() z interfejsu IStoreBehavior są odziedziczone po klasie bazowej.
    //Z kolei metoda .getStateName() z tego interfejsu ma implementację domyślną.
}
