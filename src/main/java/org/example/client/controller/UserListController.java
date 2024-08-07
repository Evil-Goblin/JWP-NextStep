package org.example.client.controller;

import lombok.RequiredArgsConstructor;
import org.example.client.model.user.User;
import org.example.client.model.user.UserService;
import org.example.webserver.annotations.RequestMapping;
import org.example.webserver.service.request.Request;
import org.example.webserver.service.servlet.adapter.queryparamrequest.QueryParamRequestHandler;
import org.example.webserver.service.servlet.modelandview.ManualModelAndView;
import org.example.webserver.service.servlet.modelandview.ModelAndView;
import org.example.webserver.service.variable.ContentType;
import org.example.webserver.service.variable.HTTPMethod;
import org.example.webserver.service.variable.StatusCode;

import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@RequestMapping(method = HTTPMethod.GET)
public class UserListController implements QueryParamRequestHandler {

    private final UserService userService;

    @Override
    public ModelAndView process(Map<String, String> queryParam, Request request) {
        String logined = request.getCookies().getOrDefault("logined", "false");
        if (logined.equals("false")) {
            return ModelAndView.of("user/login.html");
        }

        List<User> allUsers = userService.findAll();

        StringBuilder stringBuilder = new StringBuilder();
        writeNav(stringBuilder);

        stringBuilder.append("<div class=\"container\" id=\"main\">\n" +
                "    <div class=\"col-md-6 col-md-offset-3\">\n" +
                "        <div class=\"panel panel-default content-main\">" +
                "       <table>" +
                "           <tr>" +
                "               <th>No</th>" +
                "               <th>ID</th>" +
                "               <th>Name</th>" +
                "           </tr>");

        allUsers.forEach(user -> {
            stringBuilder.append("           <tr>")
                    .append("               <th>").append(user.getId()).append("</th>")
                    .append("               <th>").append(user.getUserId()).append("</th>")
                    .append("               <th>").append(user.getName()).append("</th>")
                    .append("           </tr>");
        });

        stringBuilder.append("</table>");

        stringBuilder.append("</div>\n" +
                "    </div>\n" +
                "</div>");

        writeFooter(stringBuilder);

        return new ManualModelAndView(ContentType.TEXT_HTML_VALUE, StatusCode.OK, stringBuilder.toString());
    }

    private void writeFooter(StringBuilder stringBuilder) {
        stringBuilder.append("<script src=\"/js/jquery-3.7.1.min.js\"></script>\n" +
                "<script src=\"/js/bootstrap.min.js\"></script>\n" +
                "<script src=\"/js/scripts.js\"></script>\n" +
                "</body>\n" +
                "</html>");
    }

    private void writeNav(StringBuilder stringBuilder) {
        stringBuilder.append("<!DOCTYPE html>\n" +
                "<html lang=\"kr\">\n" +
                "<head>\n" +
                "    <meta http-equiv=\"content-type\" content=\"text/html; charset=UTF-8\">\n" +
                "    <meta charset=\"utf-8\">\n" +
                "    <title>SLiPP Java Web Programming</title>\n" +
                "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1, maximum-scale=1\">\n" +
                "    <link href=\"/css/bootstrap.min.css\" rel=\"stylesheet\">\n" +
                "    <script src=\"//html5shim.googlecode.com/svn/trunk/html5.js\"></script>\n" +
                "    <link href=\"/css/styles.css\" rel=\"stylesheet\">\n" +
                "</head>\n" +
                "<body>\n" +
                "<nav class=\"navbar navbar-fixed-top header\">\n" +
                "    <div class=\"col-md-12\">\n" +
                "        <div class=\"navbar-header\">\n" +
                "\n" +
                "            <a href=\"/index.html\" class=\"navbar-brand\">SLiPP</a>\n" +
                "            <button type=\"button\" class=\"navbar-toggle\" data-toggle=\"collapse\" data-target=\"#navbar-collapse1\">\n" +
                "                <i class=\"glyphicon glyphicon-search\"></i>\n" +
                "            </button>\n" +
                "\n" +
                "        </div>\n" +
                "        <div class=\"collapse navbar-collapse\" id=\"navbar-collapse1\">\n" +
                "            <form class=\"navbar-form pull-left\">\n" +
                "                <div class=\"input-group\" style=\"max-width:470px;\">\n" +
                "                    <input type=\"text\" class=\"form-control\" placeholder=\"Search\" name=\"srch-term\" id=\"srch-term\">\n" +
                "                    <div class=\"input-group-btn\">\n" +
                "                        <button class=\"btn btn-default btn-primary\" type=\"submit\"><i class=\"glyphicon glyphicon-search\"></i></button>\n" +
                "                    </div>\n" +
                "                </div>\n" +
                "            </form>\n" +
                "            <ul class=\"nav navbar-nav navbar-right\">\n" +
                "                <li>\n" +
                "                    <a href=\"#\" class=\"dropdown-toggle\" data-toggle=\"dropdown\"><i class=\"glyphicon glyphicon-bell\"></i></a>\n" +
                "                    <ul class=\"dropdown-menu\">\n" +
                "                        <li><a href=\"https://slipp.net\" target=\"_blank\">SLiPP</a></li>\n" +
                "                        <li><a href=\"https://facebook.com\" target=\"_blank\">Facebook</a></li>\n" +
                "                    </ul>\n" +
                "                </li>\n" +
                "                <li><a href=\"/user/list\">목록<i class=\"glyphicon glyphicon-user\"></i></a></li>\n" +
                "            </ul>\n" +
                "        </div>\n" +
                "    </div>\n" +
                "</nav>\n" +
                "<div class=\"navbar navbar-default\" id=\"subnav\">\n" +
                "    <div class=\"col-md-12\">\n" +
                "        <div class=\"navbar-header\">\n" +
                "            <a href=\"#\" style=\"margin-left:15px;\" class=\"navbar-btn btn btn-default btn-plus dropdown-toggle\" data-toggle=\"dropdown\"><i class=\"glyphicon glyphicon-home\" style=\"color:#dd1111;\"></i> Home <small><i class=\"glyphicon glyphicon-chevron-down\"></i></small></a>\n" +
                "            <ul class=\"nav dropdown-menu\">\n" +
                "                <li><a href=\"/user/profile.html\"><i class=\"glyphicon glyphicon-user\" style=\"color:#1111dd;\"></i> Profile</a></li>\n" +
                "                <li class=\"nav-divider\"></li>\n" +
                "                <li><a href=\"#\"><i class=\"glyphicon glyphicon-cog\" style=\"color:#dd1111;\"></i> Settings</a></li>\n" +
                "            </ul>\n" +
                "\n" +
                "            <button type=\"button\" class=\"navbar-toggle\" data-toggle=\"collapse\" data-target=\"#navbar-collapse2\">\n" +
                "                <span class=\"sr-only\">Toggle navigation</span>\n" +
                "                <span class=\"icon-bar\"></span>\n" +
                "                <span class=\"icon-bar\"></span>\n" +
                "                <span class=\"icon-bar\"></span>\n" +
                "            </button>\n" +
                "        </div>\n" +
                "        <div class=\"collapse navbar-collapse\" id=\"navbar-collapse2\">\n" +
                "            <ul class=\"nav navbar-nav navbar-right\">\n" +
                "                <li class=\"active\"><a href=\"/index.html\">Posts</a></li>\n" +
                "                <li><a href=\"/user/login.html\" role=\"button\">로그인</a></li>\n" +
                "                <li><a href=\"/user/form.html\" role=\"button\">회원가입</a></li>\n" +
                "                <li><a href=\"#\" role=\"button\">로그아웃</a></li>\n" +
                "                <li><a href=\"#\" role=\"button\">개인정보수정</a></li>\n" +
                "            </ul>\n" +
                "        </div>\n" +
                "    </div>\n" +
                "</div>");
    }
}
