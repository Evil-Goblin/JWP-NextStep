package jwp.core.di;

import jwp.core.annotation.Component;
import jwp.core.annotation.Inject;

@Component
public class TestSetterInject {
    private D d;

    @Inject
    public void setD(D d) {
        this.d = d;
    }

    public D getD() {
        return d;
    }
}
