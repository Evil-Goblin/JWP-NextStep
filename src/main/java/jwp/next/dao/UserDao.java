package jwp.next.dao;

import jwp.core.jdbc.ConnectionManager;
import jwp.core.jdbc.InsertJdbcTemplate;
import jwp.core.jdbc.JdbcTemplate;
import jwp.core.jdbc.UpdateJdbcTemplate;
import jwp.next.model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserDao {

    private final JdbcTemplate insertJdbcTemplate = new JdbcTemplate() {
        @Override
        protected String createQuery() {
            return "INSERT INTO USERS VALUES (?, ?, ?, ?)";
        }

        @Override
        protected void setValues(User user, PreparedStatement pstmt) throws SQLException {
            pstmt.setString(1, user.getUserId());
            pstmt.setString(2, user.getPassword());
            pstmt.setString(3, user.getName());
            pstmt.setString(4, user.getEmail());
        }
    };
    private final JdbcTemplate updateJdbcTemplate = new JdbcTemplate() {
        @Override
        protected String createQuery() {
            return "UPDATE USERS SET password = ?, name = ?, email = ? WHERE userId = ?";
        }

        @Override
        protected void setValues(User user, PreparedStatement pstmt) throws SQLException {
            pstmt.setString(1, user.getPassword());
            pstmt.setString(2, user.getName());
            pstmt.setString(3, user.getEmail());
            pstmt.setString(4, user.getUserId());
        }
    };

    public void insert(User user) throws SQLException {
        insertJdbcTemplate.update(user);
    }

    public void update(User user) throws SQLException {
        updateJdbcTemplate.update(user);
    }

    public List<User> findAll() throws SQLException {
        // TODO 구현 필요함.
        return new ArrayList<User>();
    }

    public User findByUserId(String userId) throws SQLException {
        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            con = ConnectionManager.getConnection();
            String sql = "SELECT userId, password, name, email FROM USERS WHERE userid=?";
            pstmt = con.prepareStatement(sql);
            pstmt.setString(1, userId);

            rs = pstmt.executeQuery();

            User user = null;
            if (rs.next()) {
                user = new User(rs.getString("userId"), rs.getString("password"), rs.getString("name"),
                        rs.getString("email"));
            }

            return user;
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (pstmt != null) {
                pstmt.close();
            }
            if (con != null) {
                con.close();
            }
        }
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
