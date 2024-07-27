package org.example.client.model.user;

import org.example.webserver.model.exception.InvalidIdException;
import org.example.webserver.util.assertion.Assert;
import org.example.webserver.util.idgenerator.IdGenerator;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class MemoryUserRepository implements UserRepository {

    private final IdGenerator<Long> idGenerator;
    private final Map<Long, User> repository = new HashMap<>();

    public MemoryUserRepository(IdGenerator<Long> idGenerator) {
        this.idGenerator = idGenerator;
    }

    @Override
    public Optional<User> findById(Long id) {
        User user = repository.get(id);
        if (user == null) {
            return Optional.empty();
        }

        return Optional.of(user);
    }

    @Override
    public User save(User user) {
        if (user.getId() != null) {
            return merge(user);
        }
        return persist(user);
    }

    private User merge(User user) {
        User persistedUser = findPersistedUser(user.getId());

        if (user.getUserId() != null) persistedUser.setUserId(user.getUserId());
        if (user.getPassword() != null) persistedUser.setPassword(user.getPassword());
        if (user.getName() != null) persistedUser.setName(user.getName());

        return persistedUser;
    }

    private User persist(User user) {
        user.setId(idGenerator.getNext());
        repository.put(user.getId(), user);

        return user;
    }

    @Override
    public User update(User user) {
        User persistedUser = findPersistedUser(user.getId());

        persistedUser.setUserId(user.getUserId());
        persistedUser.setPassword(user.getPassword());
        persistedUser.setName(user.getName());

        return persistedUser;
    }

    @Override
    public User delete(User user) {
        User persistedUser = findPersistedUser(user.getId());

        return repository.remove(persistedUser.getId());
    }

    private User findPersistedUser(Long id) {
        Assert.notNull(id, "Id must not be null");

        return findById(id).orElseThrow(() -> new InvalidIdException("User Id is not persisted"));
    }
}
