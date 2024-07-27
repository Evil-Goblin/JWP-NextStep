package org.example.client;

import org.assertj.core.api.Assertions;
import org.example.client.controller.UserCreateController;
import org.example.client.model.user.User;
import org.example.client.model.user.UserRepository;
import org.example.webserver.service.servlet.modelandview.ModelAndView;
import org.example.webserver.service.servlet.modelandview.RedirectModelAndView;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class UserCreateControllerTest {

    static class EmptyUserRepository implements UserRepository {
        @Override
        public Optional<User> findById(Long id) {
            return Optional.empty();
        }

        @Override
        public User save(User user) {
            return null;
        }

        @Override
        public User update(User user) {
            return null;
        }

        @Override
        public User delete(User user) {
            return null;
        }

        @Override
        public List<User> findAll() {
            return List.of();
        }

        @Override
        public Optional<User> findByUserId(String userId) {
            return Optional.empty();
        }
    }

    @Test
    void redirectTest() {
        UserCreateController userCreateController = new UserCreateController(new EmptyUserRepository());
        Map<String, String> queryParam = new HashMap<>();
        queryParam.put("userId", "test");
        queryParam.put("password", "test");
        queryParam.put("name", "test");

        ModelAndView view = userCreateController.process(queryParam);

        Assertions.assertThat(view).isNotNull();
        Assertions.assertThat(view).isInstanceOf(RedirectModelAndView.class);
    }

}
