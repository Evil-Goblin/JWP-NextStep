package jwp.next.controller.qna;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jwp.core.mvc.ModelAndView;
import jwp.core.mvc.controller.AbstractController;
import jwp.next.dao.AnswerDao;
import jwp.next.model.Answer;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class AddAnswerController extends AbstractController {

    private final AnswerDao answerDao = new AnswerDao();

    @Override
    public ModelAndView execute(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        Answer answer = new Answer(req.getParameter("writer"), req.getParameter("contents"), Long.parseLong(req.getParameter("questionId")));
        log.debug("answer: {}", answer);

        Answer savedAnswer = answerDao.insert(answer);

        return jsonView().addObject("answer", savedAnswer);
    }
}
