package state;

import state.context.OnlineStore;

public class StateClient {

    public static void main(String[] args) {

        OnlineStore store = new OnlineStore();

        store.checkout();
        store.signIn("xXx_DotaSlayer420_xXx", "1234");
        store.checkout();
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
