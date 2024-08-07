package jwp.core.mvc;

import jwp.core.mvc.view.View;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
public class ModelAndView {
    @Getter
    private final View view;
    private final Map<String, Object> model = new HashMap<>();

    public ModelAndView addObject(String attributeName, Object attributeValue) {
        model.put(attributeName, attributeValue);
        return this;
    }

    public Map<String, Object> getModel() {
        return Collections.unmodifiableMap(model);
    }
}
