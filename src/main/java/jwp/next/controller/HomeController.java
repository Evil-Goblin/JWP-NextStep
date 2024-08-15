package jwp.next.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jwp.core.annotation.Controller;
import jwp.core.annotation.Inject;
import jwp.core.annotation.RequestMapping;
import jwp.core.mvc.ModelAndView;
import jwp.core.mvc.controller.AbstractController;
import jwp.core.nmvc.AbstractNewController;
import jwp.next.dao.JdbcQuestionDao;
import jwp.next.dao.QuestionDao;

@Controller
public class HomeController extends AbstractNewController {

    private final QuestionDao questionDao;

    @Inject
    public HomeController(QuestionDao questionDao) {
        this.questionDao = questionDao;
    }

    @RequestMapping("/")
    public ModelAndView home(HttpServletRequest request, HttpServletResponse response) {
        return jspView("home.jsp").addObject("questions", questionDao.findAll());
    }
}
