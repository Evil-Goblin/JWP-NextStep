package jwp.core.nmvc;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Map;

public class ControllerScannerTest {

    private ControllerScanner controllerScanner;

    @BeforeEach
    void setUp() {
        controllerScanner = new ControllerScanner("jwp.core.nmvc");
    }

    @Test
    void getControllerTest() {
        Map<Class<?>, Object> controllers = controllerScanner.getControllers();
        controllers.forEach((k, v) -> System.out.printf("Class: %s || Instance: %s\n", k, v));
    }
}
