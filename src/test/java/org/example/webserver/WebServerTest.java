package org.example.webserver;

import org.example.handler.controller.IndexController;
import org.example.handler.request.Request;
import org.example.handler.response.Response;
import org.junit.jupiter.api.Test;

import java.io.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

class WebServerTest {

    @Test
    void IndexPage_메소드를_리턴한다() throws NoSuchMethodException {
        WebServer.initializeController();

        // 뭔가 더 좋은 테스트 방법이 있을 것 같으나 현재로서는 컨트롤러의 갯수가 적어 추후 컨트롤러를 관리하는 친구를 추가해야할 것 같다.
        assertEquals(WebServer.CONTROLLER_MAP.get("/index.html"), IndexController.class.getMethod("IndexPage", Request.class, Response.class));
    }

    @Test
    void filePath() {
        File file = new File("./src/main/webapp/index.html");
        System.out.println("file.getPath() = " + file.getAbsolutePath());
        if (file.exists()) {
            try {
                BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
                String line;
                while ((line = bufferedReader.readLine()) != null) {
                    System.out.println("line = " + line);
                }
            } catch (IOException exception) {
                System.out.println("exception = " + exception);
            }

        }
    }
}