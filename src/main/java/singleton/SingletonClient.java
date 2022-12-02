package singleton;

import singleton.examples.eager.*;
import singleton.examples.lazy.*;

public class SingletonClient {

    public static void main(String[] args) {
        EagerAccounting.getInstance().addFunds(200_000);
        EagerAccounting.getInstance().pay();
        EagerAccounting.getInstance().displayFunds();

        EagerEnumAccounting.INSTANCE.addFunds(210_000);
        EagerEnumAccounting.INSTANCE.pay();
        EagerEnumAccounting.INSTANCE.displayFunds();

        LazyAccounting.getInstance().addFunds(85_000);
        LazyAccounting.getInstance().pay();
        LazyAccounting.getInstance().displayFunds();

        LazySynchronizedAccounting.getInstance().addFunds(150_000);
        LazySynchronizedAccounting.getInstance().pay();
        LazySynchronizedAccounting.getInstance().displayFunds();

        LazyHolderAccounting.getInstance().addFunds(170_000);
        LazyHolderAccounting.getInstance().pay();
        LazyHolderAccounting.getInstance().displayFunds();
    }
}
