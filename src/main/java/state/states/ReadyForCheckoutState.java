package state.states;

import state.context.OnlineStore;

import java.util.Map;

//Stan "użytkownika zalogowanego, z wypełnionymi niezbędnymi danymi".
public class ReadyForCheckoutState extends BaseState implements IStoreBehavior {

    public ReadyForCheckoutState(OnlineStore store) {
        super(store);
    }

    //Zachowanie .checkout() charakterystyczne tylko dla tego Stanu.
    @Override
    public boolean checkout() {
        if (cart.isEmpty()) {
            System.out.println("Nie masz nic w koszyku!\n");
            return false;
        }

        System.out.println("TWOJE ZAMÓWIENIE:");
        for (Map.Entry<String, Integer> item : cart.entrySet()) {
            System.out.println("\t" + item.getValue() + "x " + item.getKey());
        }
        cart.clear();

        return true;
    }

    //Metody .addToCart(), .signIn() oraz .signOut() z interfejsu IStoreBehavior są odziedziczone po klasie bazowej.
    //Z kolei metoda .getStateName() z tego interfejsu ma implementację domyślną.
}
