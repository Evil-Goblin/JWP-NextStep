package org.example.webserver;

import org.example.webserver.service.request.Request;
import org.example.webserver.service.request.RequestResolver;
import org.example.webserver.service.variable.HTTPMethod;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.util.Enumeration;
import java.util.Map;
import java.util.Objects;

import static org.assertj.core.api.Assertions.assertThat;

class WebServerTest {

    private final TestUtils testUtils = new TestUtils();
    private final String POST_CREATE_USER_TEST = "POST_CREATE_USER";

    @Test
    void POST_CREATE_USER_TEST_파일을_불러온다() {
        File resourceByFileName = testUtils.getResourceByFileName(POST_CREATE_USER_TEST);

        assertThat(resourceByFileName).isNotNull();
    }

    @Test
    void POST_CREATE_USER_TEST_파일로부터_Request_객체를_생성한다() throws IOException {
        File resourceByFileName = testUtils.getResourceByFileName(POST_CREATE_USER_TEST);
        RequestResolver requestResolver = new RequestResolver();

        InputStream in = new FileInputStream(resourceByFileName);

        Request request = requestResolver.parse(in);

        assertThat(request.getMethod()).isEqualTo(HTTPMethod.POST);
        assertThat(request.getRequestURI()).isEqualTo("/user/create");

        Map<String, String> body = request.getBody();

        assertThat(body).isNotNull();
        assertThat(body.get("userId")).isEqualTo("test");
    }

    @Test
    void IndexPage_메소드를_리턴한다() throws NoSuchMethodException {
//        WebServer.initializeController();
//
//        // 뭔가 더 좋은 테스트 방법이 있을 것 같으나 현재로서는 컨트롤러의 갯수가 적어 추후 컨트롤러를 관리하는 친구를 추가해야할 것 같다.
//        assertEquals(WebServer.CONTROLLER_MAP.get("/index.html"), IndexController.class.getMethod("IndexPage", Request.class, Response.class));
    }

    @Test
    void filePath() {
        File file = new File("resource/webapp/index.html");
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

    @Test
    @DisplayName("resources 디렉토리의 경로 찾기")
    void resourcesFilePath() throws URISyntaxException {
        URL resource = getClass().getClassLoader().getResource("webapp/index.html");
        System.out.println("resource = " + resource);

        File file = new File(resource.toURI());
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

    @Test
    @DisplayName("basePackage 찾고 패키지 순회")
    void findBasePackage() throws IOException {
        Package aPackage = getClass().getPackage();
        String name = aPackage.getName();
        System.out.println("name = " + name);
        String searchPattern = "**/*.class";

        Enumeration<URL> resources = getClass().getClassLoader().getResources(name.replace('.', '/'));
        while (resources.hasMoreElements()) {
            URL url = resources.nextElement();
            System.out.println("url = " + url);
            File dir = new File(url.getFile());
            iteratePackage(dir);
        }
    }

    private void iteratePackage(File dir) {
        for (File file : Objects.requireNonNull(dir.listFiles())) {
            System.out.println("file = " + file);
            if (file.isDirectory()) {
                iteratePackage(file);
            }
        }
    }

    @Test
    @DisplayName("Hint3 의 ./path 로 파일을 읽는 테스트")
    void readFileBasedHint3() throws IOException {
        byte[] body = Files.readAllBytes(new File(getClass().getClassLoader().getResource("webapp/index.html").getFile()).toPath());
        System.out.println("body = " + body.toString());
    }
}
