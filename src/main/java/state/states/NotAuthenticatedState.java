package state.states;

import state.context.OnlineStore;

public class NotAuthenticatedState extends BaseState implements IStoreBehavior {

    public NotAuthenticatedState(OnlineStore store) {
        super(store);
    }

    @Override
    public boolean checkout() {
        System.out.println("Najpierw się zaloguj.");
        return false;
    }

    @Override
    public boolean signIn(String username, String password) {
        boolean authenticated = store.attemptSignIn(username, password);

        if (!authenticated) return false;

        if (!store.getShippingAddress().isBlank()) {
            store.changeState(new ReadyForCheckoutState(store));
        } else {
            store.changeState(new NeedsShippingAddressState(store));
        }

        return true;
    }

    @Override
    public void signOut() {
        System.out.println("Nie jesteś teraz zalogowany!");
    }
}
