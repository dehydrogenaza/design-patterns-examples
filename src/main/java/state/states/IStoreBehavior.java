package state.states;

//Interfejs współdzielony przez wszystkie Stany. Dzięki niemu klasa Kontekstu może korzystać ze Stanów zamiennie.
public interface IStoreBehavior {

    int addToCart(String productID);
    boolean checkout();
    boolean signIn(String username, String password);
    void signOut();

    //Domyślna implementacja; zwraca nazwę obecnego stanu. Bardziej profesjonalne byłoby wykorzystanie enuma, ale
    // chciałem uprościć przykład.
    default String getStateName() {
        return "\t---\tstan: " + this.getClass().getSimpleName() + "\t---";
    }
}
