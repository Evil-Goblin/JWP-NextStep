package jwp.core.mvc;

import jakarta.servlet.http.HttpServletRequest;
import jwp.core.mvc.controller.Controller;
import jwp.core.nmvc.HandlerMapping;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;

@Slf4j
public class LegacyHandlerMapping implements HandlerMapping {
    private Map<String, Controller> mapping = new HashMap<>();

    public void initMapping() {
        log.info("Initialized Request Mapping");
    }

    public Controller findController(String url) {
        return mapping.get(url);
    }

    void put(String url, Controller controller) {
        mapping.put(url, controller);
    }

    @Override
    public Object getHandler(HttpServletRequest request) {
        return mapping.get(request.getRequestURI());
    }
}
