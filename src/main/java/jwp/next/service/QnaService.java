package jwp.next.service;

import jwp.core.annotation.Inject;
import jwp.core.annotation.Service;
import jwp.next.CannotDeleteException;
import jwp.next.dao.AnswerDao;
import jwp.next.dao.QuestionDao;
import jwp.next.model.Answer;
import jwp.next.model.Question;
import jwp.next.model.User;

import java.util.List;

@Service
public class QnaService {
    private final QuestionDao questionDao;
    private final AnswerDao answerDao;

    @Inject
    public QnaService(QuestionDao questionDao, AnswerDao answerDao) {
        this.questionDao = questionDao;
        this.answerDao = answerDao;
    }

    public Question findById(long questionId) {
        return questionDao.findById(questionId);
    }

    public List<Answer> findAllByQuestionId(long questionId) {
        return answerDao.findAllByQuestionId(questionId);
    }

    public void deleteQuestion(long questionId, User user) throws CannotDeleteException {
        Question question = questionDao.findById(questionId);
        if (question == null) {
            throw new CannotDeleteException("존재하지 않는 질문입니다.");
        }

        List<Answer> answers = answerDao.findAllByQuestionId(questionId);
        if (question.canDelete(user, answers)) {
            questionDao.delete(questionId);
        }
    }
}
