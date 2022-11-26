package state.states;

import state.context.OnlineStore;

public class NeedsShippingAddressState extends BaseState implements IStoreBehavior {

    public NeedsShippingAddressState(OnlineStore store) {
        super(store);
    }

    @Override
    public boolean checkout() {
        if (store.getShippingAddress().isBlank()) {
            System.out.println("Najpierw podaj adres dostawy.");
            return false;
        } else {
            store.changeState(new ReadyForCheckoutState(store));
            return store.getState().checkout();
        }
    }

    @Override
    public boolean signIn(String username, String password) {
        super.signIn(username, password);
        System.out.println("Teraz podaj adres dostawy.");
        return true;
    }
}
