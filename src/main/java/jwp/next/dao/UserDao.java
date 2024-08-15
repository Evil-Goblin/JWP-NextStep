package jwp.next.dao;

import jwp.next.model.User;

public interface UserDao extends Dao<User, Long> {
    User findByUserId(String userId);
}
