package jwp.next.dao;

import jwp.next.model.Question;

public interface QuestionDao extends Dao<Question, Long> {
    void update(Question question);
}
