package jwp.next.controller.qna;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jwp.core.jdbc.DataAccessException;
import jwp.core.mvc.controller.AbstractController;
import jwp.next.dao.AnswerDao;
import jwp.next.model.Result;
import jwp.core.mvc.ModelAndView;

public class DeleteAnswerController extends AbstractController {
    @Override
    public ModelAndView execute(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        Long answerId = Long.parseLong(req.getParameter("answerId"));
        AnswerDao answerDao = new AnswerDao();

        try {
            answerDao.delete(answerId);
            return jsonView().addObject("result", Result.ok());
        } catch (DataAccessException e) {
            return jsonView().addObject("result", Result.fail(e.getMessage()));
        }
    }
}
