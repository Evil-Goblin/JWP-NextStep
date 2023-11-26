package org.example.webserver.service.request;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class RequestResolverTest {

    @Test
    @DisplayName("URL Parse 테스트")
    void urlParseTest() {
        String indexUrl = "/index.html";
        RequestUrl noParam = RequestUrl.from(indexUrl);
        Assertions.assertEquals(noParam.getUri(), indexUrl);

        String indexUrlLastToken = "/index.html?";
        RequestUrl lastToken = RequestUrl.from(indexUrlLastToken);
        Assertions.assertEquals(lastToken.getUri(), "/index.html");

        String indexUrlTokenize = "/index.html?asdf=fe";
        RequestUrl withParam = RequestUrl.from(indexUrlTokenize);
        Assertions.assertEquals(withParam.getUri(), "/index.html");
    }
}
