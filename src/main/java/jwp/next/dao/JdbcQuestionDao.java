package jwp.next.dao;

import jwp.core.annotation.Inject;
import jwp.core.annotation.Repository;
import jwp.core.jdbc.JdbcTemplate;
import jwp.core.jdbc.RowMapper;
import jwp.next.model.Question;

import java.util.List;

@Repository
public class JdbcQuestionDao implements QuestionDao {

    private final JdbcTemplate jdbcTemplate;

    @Inject
    public JdbcQuestionDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Question insert(Question entity) {
        return null;
    }

    @Override
    public void delete(Long id) {

    }

    @Override
    public List<Question> findAll() {
        String sql = "SELECT questionId, writer, title, createdDate, countOfAnswer FROM QUESTIONS "
                + "order by questionId desc";

        RowMapper<Question> rm = rs -> new Question(rs.getLong("questionId"), rs.getString("writer"), rs.getString("title"), null,
                rs.getTimestamp("createdDate"), rs.getInt("countOfAnswer"));

        return jdbcTemplate.query(sql, rm);
    }

    @Override
    public Question findById(Long questionId) {
        String sql = "SELECT questionId, writer, title, contents, createdDate, countOfAnswer FROM QUESTIONS "
                + "WHERE questionId = ?";

        RowMapper<Question> rm = rs -> new Question(rs.getLong("questionId"), rs.getString("writer"), rs.getString("title"),
                rs.getString("contents"), rs.getTimestamp("createdDate"), rs.getInt("countOfAnswer"));

        return jdbcTemplate.queryForObject(sql, rm, questionId);
    }

    @Override
    public void update(Question question) {
        String sql = "UPDATE QUESTIONS set title = ?, contents = ? WHERE questionId = ?";
        jdbcTemplate.update(sql, question.getTitle(), question.getContents(), question.getQuestionId());
    }
}
