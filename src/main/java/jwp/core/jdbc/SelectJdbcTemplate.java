package jwp.core.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public abstract class SelectJdbcTemplate<T> {

    public List<T> query(String query) throws SQLException {
        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            con = ConnectionManager.getConnection();
            pstmt = con.prepareStatement(query);
            setValues(pstmt);

            rs = pstmt.executeQuery();

            ArrayList<T> result = new ArrayList<>();
            if (rs.next()) {
                result.add(mapRow(rs));
            }

            return result;
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

    public T queryForObject(String query) throws SQLException {
        List<T> result = query(query);
        if (result.isEmpty()) {
            return null;
        }

        return result.get(0);
    }

    public abstract void setValues(PreparedStatement preparedStatement) throws SQLException;
    public abstract T mapRow(ResultSet resultSet) throws SQLException;
}
