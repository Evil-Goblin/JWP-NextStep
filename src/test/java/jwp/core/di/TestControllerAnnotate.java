package jwp.core.di;

import jwp.core.annotation.Controller;
import jwp.core.annotation.Inject;

@Controller
public class TestControllerAnnotate {
    private final IA a;
    private final B b;

    @Inject
    public TestControllerAnnotate(IA a, B b) {
        this.a = a;
        this.b = b;
    }
}
