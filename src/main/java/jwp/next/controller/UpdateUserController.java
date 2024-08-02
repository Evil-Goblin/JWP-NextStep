package jwp.next.controller;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jwp.core.db.DataBase;
import jwp.next.model.User;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

@Slf4j
public class UpdateUserController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String userId = req.getParameter("userId");
        User user = findLoginedUserByUserId(req, userId);

        req.setAttribute("user", user);
        RequestDispatcher requestDispatcher = req.getRequestDispatcher("/user/updateForm.jsp");
        requestDispatcher.forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String userId = req.getParameter("userId");
        User user = findLoginedUserByUserId(req, userId);

        User updateUser = User.builder()
                .userId(userId)
                .password(req.getParameter("password"))
                .name(req.getParameter("name"))
                .email(req.getParameter("email"))
                .build();

        log.debug("Update User : {}", updateUser);
        user.update(updateUser);
        resp.sendRedirect("/");
    }

    private User findLoginedUserByUserId(HttpServletRequest req, String userId) {
        User user = DataBase.findUserById(userId);
        if (user == null) {
            throw new IllegalArgumentException("존재하지 않는 계정입니다.");
        } else if (!UserSessionUtils.isSameUser(req.getSession(), user)) {
            throw new IllegalStateException("다른 사용자의 정보를 수정할 수 없습니다.");
        }
        return user;
    }
}
