package org.example.webserver;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.Objects;

class WebServerTest {

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
