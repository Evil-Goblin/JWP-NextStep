package jwp.next.dao;

import jwp.core.annotation.Inject;
import jwp.core.annotation.Repository;
import jwp.core.jdbc.JdbcTemplate;
import jwp.core.jdbc.KeyHolder;
import jwp.core.jdbc.PreparedStatementCreator;
import jwp.core.jdbc.RowMapper;
import jwp.next.model.Answer;

import java.sql.*;
import java.util.List;

@Repository
public class JdbcAnswerDao implements AnswerDao {

    private final JdbcTemplate jdbcTemplate;

    @Inject
    public JdbcAnswerDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Answer insert(Answer answer) {
        String sql = "INSERT INTO ANSWERS (writer, contents, createdDate, questionId) VALUES (?, ?, ?, ?)";
        PreparedStatementCreator psc = new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                PreparedStatement pstmt = con.prepareStatement(sql);
                pstmt.setString(1, answer.getWriter());
                pstmt.setString(2, answer.getContents());
                pstmt.setTimestamp(3, new Timestamp(answer.getTimeFromCreateDate()));
                pstmt.setLong(4, answer.getQuestionId());
                return pstmt;
            }
        };

        KeyHolder keyHolder = new KeyHolder();
        jdbcTemplate.update(psc, keyHolder);
        return findById(keyHolder.getId());
    }

    public Answer findById(Long answerId) {
        String sql = "SELECT answerId, writer, contents, createdDate, questionId FROM ANSWERS WHERE answerId = ?";

        RowMapper<Answer> rm = new RowMapper<Answer>() {
            @Override
            public Answer mapRow(ResultSet rs) throws SQLException {
                return new Answer(rs.getLong("answerId"), rs.getString("writer"), rs.getString("contents"),
                        rs.getTimestamp("createdDate"), rs.getLong("questionId"));
            }
        };

        return jdbcTemplate.queryForObject(sql, rm, answerId);
    }

    public List<Answer> findAllByQuestionId(Long questionId) {
        String sql = "SELECT answerId, writer, contents, createdDate FROM ANSWERS WHERE questionId = ? "
                + "order by answerId desc";

        RowMapper<Answer> rm = new RowMapper<Answer>() {
            @Override
            public Answer mapRow(ResultSet rs) throws SQLException {
                return new Answer(rs.getLong("answerId"), rs.getString("writer"), rs.getString("contents"),
                        rs.getTimestamp("createdDate"), questionId);
            }
        };

        return jdbcTemplate.query(sql, rm, questionId);
    }

    public void delete(Long answerId) {
        String sql = "DELETE FROM ANSWERS WHERE answerId = ?";
        jdbcTemplate.update(sql, answerId);
    }

    @Override
    public List<Answer> findAll() {
        return List.of();
    }
}
