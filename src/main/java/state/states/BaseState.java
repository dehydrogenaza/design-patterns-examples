package state.states;

import state.context.OnlineStore;

import java.util.Map;

abstract public class BaseState {

    protected final OnlineStore store;
    protected final Map<String, Integer> cart;

    public BaseState(OnlineStore store) {
        this.store = store;
        this.cart = store.getCart();
    }

    public int addToCart(String productID) {
        if (cart.containsKey(productID)) {
            cart.put(productID, cart.get(productID) + 1);
        } else {
            cart.put(productID, 1);
        }
        return cart.size();
    }

    public boolean signIn(String username, String password) {
        System.out.println("Jesteś już zalogowany!");
        return true;
    }

    public void signOut() {
        store.attemptSignOut();
    }
}
