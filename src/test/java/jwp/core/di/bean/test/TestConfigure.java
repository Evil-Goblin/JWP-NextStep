package jwp.core.di.bean.test;

import jwp.core.annotation.Bean;
import jwp.core.annotation.Configuration;
import org.springframework.boot.jdbc.DataSourceBuilder;

import javax.sql.DataSource;

@Configuration
public class TestConfigure {

    @Bean
    public DataSource dataSource() {
        return DataSourceBuilder.create()
                .driverClassName("org.h2.Driver")
                .url("jdbc:h2:~/jwp-basic;AUTO_SERVER=TRUE")
                .username("sa")
                .build();
    }
}
