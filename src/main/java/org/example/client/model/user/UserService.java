package org.example.client.model.user;

import lombok.RequiredArgsConstructor;
import org.example.client.model.user.exception.InvalidUserIdException;

import java.util.List;

@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public User findByUserId(String userId) {
        return userRepository.findByUserId(userId).orElseThrow(() -> new InvalidUserIdException("Invalid user id: " + userId));
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }
}
