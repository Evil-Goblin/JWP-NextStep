package jwp.next.dao;

import jwp.next.model.User;
import jwp.next.support.context.ContextLoaderListener;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;

import static org.assertj.core.api.Assertions.assertThat;

class UserDaoTest {

    static ContextLoaderListener contextLoaderListener;

    UserDao userDao = UserDao.getInstance();

    @BeforeAll
    static void setUp() {
        UserDaoTest.contextLoaderListener = new ContextLoaderListener();

        UserDaoTest.contextLoaderListener.contextInitialized(null);
    }

    @AfterEach
    void tearDown() throws SQLException {
        userDao.deleteAll();
    }

    @Test
    void save() throws SQLException {
        // given
        User user = User.builder()
                .userId("userId")
                .name("name")
                .password("password")
                .email("email@email.com")
                .build();
        userDao.insert(user);

        // when
        User findUser = userDao.findByUserId("userId");

        // then
        assertThat(findUser).isNotNull();
        assertThat(findUser.getUserId()).isEqualTo("userId");
        assertThat(findUser.getName()).isEqualTo("name");
        assertThat(findUser.getPassword()).isEqualTo("password");
        assertThat(findUser.getEmail()).isEqualTo("email@email.com");
    }

    @Test
    void update() throws SQLException {
        // given
        User user = User.builder()
                .userId("userId")
                .name("name")
                .password("password")
                .email("email@email.com")
                .build();

        userDao.insert(user);

        User forUpdate = User.builder()
                .userId("userId")
                .name("name2")
                .password("password2")
                .email("email2@email.com")
                .build();

        // when
        userDao.update(forUpdate);
        User findUser = userDao.findByUserId("userId");

        // then
        assertThat(findUser).isNotNull();
        assertThat(findUser.getUserId()).isEqualTo("userId");
        assertThat(findUser.getName()).isEqualTo("name2");
        assertThat(findUser.getPassword()).isEqualTo("password2");
        assertThat(findUser.getEmail()).isEqualTo("email2@email.com");
    }
}
