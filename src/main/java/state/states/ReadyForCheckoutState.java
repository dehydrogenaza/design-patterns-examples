package state.states;

import state.context.OnlineStore;

import java.util.Map;

public class ReadyForCheckoutState extends BaseState implements IStoreBehavior {

    public ReadyForCheckoutState(OnlineStore store) {
        super(store);
    }

    @Override
    public boolean checkout() {
        if (cart.isEmpty()) {
            System.out.println("Nie masz nic w koszyku!");
            return false;
        }

        System.out.println("TWOJE ZAMÓWIENIE:");
        for (Map.Entry<String, Integer> item : cart.entrySet()) {
            System.out.println("\t" + item.getValue() + "x " + item.getKey());
        }
        cart.clear();

        return true;
    }
}
