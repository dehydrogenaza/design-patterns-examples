package singleton.examples.eager;

import singleton.examples.AccountingBase;


public class EagerAccounting extends AccountingBase {
    private static final EagerAccounting INSTANCE = new EagerAccounting();

    public static EagerAccounting getInstance() {
        return INSTANCE;
    }

    private EagerAccounting() {
        super();
    }
}
