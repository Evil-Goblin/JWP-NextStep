package jwp.core.di.bean.test;

import jwp.core.annotation.Component;
import jwp.core.annotation.Inject;

@Component
public class TestFieldInject {

    @Inject
    private C c;

    public C getC() {
        return c;
    }
}
