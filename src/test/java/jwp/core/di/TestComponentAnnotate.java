package jwp.core.di;

import jwp.core.annotation.Component;
import jwp.core.annotation.Inject;

@Component
public class TestComponentAnnotate {
    private final TestControllerAnnotate testControllerAnnotate;

    @Inject
    public TestComponentAnnotate(TestControllerAnnotate testControllerAnnotate) {
        this.testControllerAnnotate = testControllerAnnotate;
    }
}
