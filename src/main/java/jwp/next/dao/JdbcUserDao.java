package jwp.next.dao;

import jwp.core.annotation.Inject;
import jwp.core.annotation.Repository;
import jwp.core.jdbc.JdbcTemplate;
import jwp.next.model.User;

import java.util.List;

@Repository
public class JdbcUserDao implements UserDao {

    private final JdbcTemplate jdbcTemplate;

    @Inject
    public JdbcUserDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public User insert(User user) {
        jdbcTemplate.update(createQueryForInsert(), user.getUserId(), user.getPassword(), user.getName(), user.getEmail());
        return user;
    }

    @Override
    public User findById(Long id) {
        return null;
    }

    @Override
    public void delete(Long id) {

    }

    private String createQueryForInsert() {
        return "INSERT INTO USERS VALUES (?, ?, ?, ?)";
    }

    public void update(User user) {
        jdbcTemplate.update(createQueryForUpdate(), user.getPassword(), user.getName(), user.getEmail(), user.getUserId());
    }

    private String createQueryForUpdate() {
        return "UPDATE USERS SET password = ?, name = ?, email = ? WHERE userId = ?";
    }

    public List<User> findAll() {
        return jdbcTemplate.query(createQueryForSelectAll(), resultSet -> new User(resultSet.getString("userId"), resultSet.getString("password"), resultSet.getString("name"),
                resultSet.getString("email")));
    }

    private String createQueryForSelectAll() {
        return "SELECT userId, password, name, email FROM USERS";
    }

    @Override
    public User findByUserId(String userId) {
        return jdbcTemplate.queryForObject(createQueryForObject(), resultSet -> new User(resultSet.getString("userId"), resultSet.getString("password"), resultSet.getString("name"),
                resultSet.getString("email")), userId);
    }

    private String createQueryForObject() {
        return "SELECT userId, password, name, email FROM USERS WHERE userid=?";
    }

    public void deleteAll() {
        jdbcTemplate.update(createQueryForDeleteAll());
    }

    private String createQueryForDeleteAll() {
        return "DELETE FROM USERS";
    }
}
