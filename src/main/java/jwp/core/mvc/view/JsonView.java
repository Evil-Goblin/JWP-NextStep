package jwp.core.mvc.view;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.util.Map;

public class JsonView implements View {
    @Override
    public void render(Map<String, ?> model, HttpServletRequest req, HttpServletResponse resp) throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        resp.setContentType("application/json; charset=utf-8");
        resp.getWriter().write(objectMapper.writeValueAsString(model));
    }
}
