package singleton.examples.lazy;

import singleton.examples.AccountingBase;

public class LazyHolderAccounting extends AccountingBase {

    private static final class Holder {
        private static final LazyHolderAccounting INSTANCE = new LazyHolderAccounting();
    }

    public static LazyHolderAccounting getInstance() {
        return Holder.INSTANCE;
    }

    private LazyHolderAccounting() {
        super();
    }
}
