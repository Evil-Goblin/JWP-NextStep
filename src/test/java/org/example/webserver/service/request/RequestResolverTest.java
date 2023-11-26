package org.example.webserver.service.request;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class RequestResolverTest {

    @Test
    @DisplayName("URL Parse 테스트")
    void urlParseTest() {
        String indexUrl = "/index.html";
        RequestUrl noParam = RequestUrl.from(indexUrl);
        assertThat(noParam.getUri()).isEqualTo(indexUrl);

        String indexUrlLastToken = "/index.html?";
        RequestUrl lastToken = RequestUrl.from(indexUrlLastToken);
        assertThat(lastToken.getUri()).isEqualTo("/index.html");

        String indexUrlTokenize = "/index.html?asdf=fe";
        RequestUrl withParam = RequestUrl.from(indexUrlTokenize);
        assertThat(withParam.getUri()).isEqualTo("/index.html");
    }
}
