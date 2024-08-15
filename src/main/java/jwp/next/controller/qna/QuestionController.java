package jwp.next.controller.qna;

import jwp.core.annotation.Controller;
import jwp.core.annotation.Inject;
import jwp.core.annotation.RequestMapping;
import jwp.core.annotation.RequestMethod;
import jwp.core.nmvc.AbstractNewController;
import jwp.core.mvc.ModelAndView;
import jwp.next.CannotDeleteException;
import jwp.next.controller.UserSessionUtils;
import jwp.next.dao.AnswerDao;
import jwp.next.dao.QuestionDao;
import jwp.next.model.Answer;
import jwp.next.model.Question;
import jwp.next.model.User;
import jwp.next.service.QnaService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.List;

@Controller
public class QuestionController extends AbstractNewController {
    private QnaService qnaService;
    private QuestionDao questionDao;
    private AnswerDao answerDao;

    @Inject
    public QuestionController(QnaService qnaService, QuestionDao questionDao, AnswerDao answerDao) {
        this.qnaService = qnaService;
        this.questionDao = questionDao;
        this.answerDao = answerDao;
    }

    @RequestMapping("/qna/show")
    public ModelAndView show(HttpServletRequest req, HttpServletResponse response) throws Exception {
        long questionId = Long.parseLong(req.getParameter("questionId"));

        Question question = questionDao.findById(questionId);
        List<Answer> answers = answerDao.findAllByQuestionId(questionId);

        ModelAndView mav = jspView("/qna/show.jsp");
        mav.addObject("question", question);
        mav.addObject("answers", answers);
        return mav;
    }

    @RequestMapping("/qna/form")
    public ModelAndView createForm(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        if (!UserSessionUtils.isLogined(req.getSession())) {
            return jspView("redirect:/users/loginForm");
        }
        return jspView("/qna/form.jsp");
    }

    @RequestMapping(value = "/qna/create", method = RequestMethod.POST)
    public ModelAndView create(HttpServletRequest request, HttpServletResponse response) throws Exception {
        if (!UserSessionUtils.isLogined(request.getSession())) {
            return jspView("redirect:/users/loginForm");
        }
        User user = UserSessionUtils.getUserFromSession(request.getSession());
        Question question = new Question(user.getUserId(), request.getParameter("title"),
                request.getParameter("contents"));
        questionDao.insert(question);
        return jspView("redirect:/");
    }

    @RequestMapping("/qna/updateForm")
    public ModelAndView updateForm(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        if (!UserSessionUtils.isLogined(req.getSession())) {
            return jspView("redirect:/users/loginForm");
        }

        long questionId = Long.parseLong(req.getParameter("questionId"));
        Question question = questionDao.findById(questionId);
        if (!question.isSameWriter(UserSessionUtils.getUserFromSession(req.getSession()))) {
            throw new IllegalStateException("다른 사용자가 쓴 글을 수정할 수 없습니다.");
        }
        return jspView("/qna/update.jsp").addObject("question", question);
    }

    @RequestMapping(value = "/qna/update", method = RequestMethod.POST)
    public ModelAndView update(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        if (!UserSessionUtils.isLogined(req.getSession())) {
            return jspView("redirect:/users/loginForm");
        }

        long questionId = Long.parseLong(req.getParameter("questionId"));
        Question question = questionDao.findById(questionId);
        if (!question.isSameWriter(UserSessionUtils.getUserFromSession(req.getSession()))) {
            throw new IllegalStateException("다른 사용자가 쓴 글을 수정할 수 없습니다.");
        }

        Question newQuestion = new Question(question.getWriter(), req.getParameter("title"),
                req.getParameter("contents"));
        question.update(newQuestion);
        questionDao.update(question);
        return jspView("redirect:/");
    }

    @RequestMapping(value = "/qna/delete", method = RequestMethod.POST)
    public ModelAndView delete(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        if (!UserSessionUtils.isLogined(req.getSession())) {
            return jspView("redirect:/users/loginForm");
        }

        long questionId = Long.parseLong(req.getParameter("questionId"));
        try {
            qnaService.deleteQuestion(questionId, UserSessionUtils.getUserFromSession(req.getSession()));
            return jspView("redirect:/");
        } catch (CannotDeleteException e) {
            return jspView("show.jsp").addObject("question", qnaService.findById(questionId))
                    .addObject("answers", qnaService.findAllByQuestionId(questionId))
                    .addObject("errorMessage", e.getMessage());
        }
    }
}
