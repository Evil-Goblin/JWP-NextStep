package jwp.next.dao;

import jwp.core.jdbc.ConnectionManager;
import jwp.core.jdbc.JdbcTemplate;
import jwp.core.jdbc.SelectJdbcTemplate;
import jwp.next.model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class UserDao {

    public void insert(User user) throws SQLException {
        JdbcTemplate<User> jdbcTemplate = new JdbcTemplate<>() {
            @Override
            protected void setValues(PreparedStatement pstmt) throws SQLException {
                pstmt.setString(1, user.getUserId());
                pstmt.setString(2, user.getPassword());
                pstmt.setString(3, user.getName());
                pstmt.setString(4, user.getEmail());
            }

            @Override
            public User mapRow(ResultSet resultSet) throws SQLException {
                return null;
            }
        };
        jdbcTemplate.update(createQueryForInsert());
    }

    private String createQueryForInsert() {
        return "INSERT INTO USERS VALUES (?, ?, ?, ?)";
    }

    public void update(User user) throws SQLException {
        JdbcTemplate<User> jdbcTemplate = new JdbcTemplate<>() {
            @Override
            protected void setValues(PreparedStatement pstmt) throws SQLException {
                pstmt.setString(1, user.getPassword());
                pstmt.setString(2, user.getName());
                pstmt.setString(3, user.getEmail());
                pstmt.setString(4, user.getUserId());
            }

            @Override
            public User mapRow(ResultSet resultSet) throws SQLException {
                return null;
            }
        };
        jdbcTemplate.update(createQueryForUpdate());
    }

    private String createQueryForUpdate() {
        return "UPDATE USERS SET password = ?, name = ?, email = ? WHERE userId = ?";
    }

    public List<User> findAll() throws SQLException {
        SelectJdbcTemplate<User> selectJdbcTemplate = new SelectJdbcTemplate<>() {
            @Override
            public void setValues(PreparedStatement preparedStatement) throws SQLException {

            }

            @Override
            public User mapRow(ResultSet resultSet) throws SQLException {
                return new User(resultSet.getString("userId"), resultSet.getString("password"), resultSet.getString("name"),
                        resultSet.getString("email"));
            }
        };

        return selectJdbcTemplate.query(createQueryForSelectAll());
    }

    private String createQueryForSelectAll() {
        return "SELECT userId, password, name, email FROM USERS";
    }

    public User findByUserId(String userId) throws SQLException {
        SelectJdbcTemplate<User> selectJdbcTemplate = new SelectJdbcTemplate<>() {
            @Override
            public void setValues(PreparedStatement preparedStatement) throws SQLException {
                preparedStatement.setString(1, userId);
            }

            @Override
            public User mapRow(ResultSet resultSet) throws SQLException {
                return new User(resultSet.getString("userId"), resultSet.getString("password"), resultSet.getString("name"),
                        resultSet.getString("email"));
            }
        };

        return selectJdbcTemplate.queryForObject(createQueryForObject());
    }

    private String createQueryForObject() {
        return "SELECT userId, password, name, email FROM USERS WHERE userid=?";
    }

    public void deleteAll() throws SQLException {
        Connection con = null;
        PreparedStatement pstmt = null;

        try {
            con = ConnectionManager.getConnection();
            String sql = "DELETE FROM USERS";
            pstmt = con.prepareStatement(sql);
            pstmt.executeUpdate();
        } finally {
            if (pstmt != null) {
                pstmt.close();
            }
            if (con != null) {
                con.close();
            }
        }
    }
}
