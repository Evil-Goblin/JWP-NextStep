package jwp.next.dao;

import jwp.next.model.Answer;

import java.util.List;

public interface AnswerDao extends Dao<Answer, Long> {
    List<Answer> findAllByQuestionId(Long questionId);
}
