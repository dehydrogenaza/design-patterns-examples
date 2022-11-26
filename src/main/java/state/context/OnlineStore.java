package state.context;

import state.states.IStoreBehavior;
import state.states.NotAuthenticatedState;

import java.util.HashMap;
import java.util.Map;

public class OnlineStore {
    private IStoreBehavior state;
    private final Map<String, Integer> cart = new HashMap<>();
    private String shippingAddress = "";

    public Map<String, Integer> getCart() {
        return cart;
    }

    public OnlineStore() {
        changeState(new NotAuthenticatedState(this));
    }

    public void changeState(IStoreBehavior newState) {
        this.state = newState;
    }

    public int addToCart(String productID) {
        return state.addToCart(productID);
    }

    public boolean checkout() {
        return state.checkout();
    }

    public boolean signIn(String username, String password) {
        return state.signIn(username, password);
    }

    public void signOut() {
        state.signOut();
    }

    public boolean attemptSignIn(String username, String password) {
        System.out.println("Jakiś ekran logowania...");
        System.out.println("\tsignIn: " + username);

        String hiddenPassword = "*".repeat(password.length());
        System.out.println("\thasło: " + hiddenPassword);

        if (Math.random() > 0.5) {
            System.out.println("Udane logowanie!");
            return true;
        } else {
            System.out.println("Zła nazwa użytkownika lub hasło :/");
            return false;
        }
    }

    public void attemptSignOut() {
        System.out.println("Wylogowano.");
        state = new NotAuthenticatedState(this);
    }

    public void setShippingAddress(String address) {
        System.out.println("Ustawiam adres: " + address);
        this.shippingAddress = address;
    }

    public String getShippingAddress() {
        return shippingAddress;
    }

    public IStoreBehavior getState() {
        return state;
    }
}
