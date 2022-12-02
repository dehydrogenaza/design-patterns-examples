package singleton.examples.lazy;

import singleton.examples.AccountingBase;

public class LazyAccounting extends AccountingBase {
    private static LazyAccounting INSTANCE;

    public static LazyAccounting getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new LazyAccounting();
        }
        return INSTANCE;
    }

    private LazyAccounting() {
        super();
    }
}
