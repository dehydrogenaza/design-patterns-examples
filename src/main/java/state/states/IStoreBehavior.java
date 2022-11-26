package state.states;

public interface IStoreBehavior {

    int addToCart(String productID);
    boolean checkout();
    boolean signIn(String username, String password);
    void signOut();
}
