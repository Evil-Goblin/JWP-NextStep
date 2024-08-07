package jwp.next.controller.qna;

import jwp.core.mvc.controller.AbstractController;
import jwp.next.dao.JdbcAnswerDao;
import jwp.next.dao.QuestionDao;
import jwp.core.mvc.ModelAndView;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class ShowController extends AbstractController {
    private final QuestionDao questionDao = QuestionDao.getInstance();
    private final JdbcAnswerDao answerDao = JdbcAnswerDao.getInstance();

    @Override
    public ModelAndView execute(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        Long questionId = Long.parseLong(req.getParameter("questionId"));

        return jspView("/qna/show.jsp")
                .addObject("question", questionDao.findById(questionId))
                .addObject("answers", answerDao.findAllByQuestionId(questionId));
    }
}
