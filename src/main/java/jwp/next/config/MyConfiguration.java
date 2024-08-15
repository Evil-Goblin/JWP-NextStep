package jwp.next.config;

import jwp.core.annotation.Bean;
import jwp.core.annotation.ComponentScan;
import jwp.core.annotation.Configuration;
import jwp.core.jdbc.JdbcTemplate;
import org.springframework.boot.jdbc.DataSourceBuilder;

import javax.sql.DataSource;

@Configuration
@ComponentScan({"jwp.next", "jwp.core"})
public class MyConfiguration {

    private static final String DB_DRIVER = "org.h2.Driver";
    private static final String DB_URL = "jdbc:h2:~/jwp-basic;AUTO_SERVER=TRUE";
    private static final String DB_USERNAME = "sa";
    private static final String DB_PW = "";

    @Bean
    public DataSource dataSource() {
        return DataSourceBuilder.create()
                .driverClassName(DB_DRIVER)
                .url(DB_URL)
                .username(DB_USERNAME)
                .build();
    }

    @Bean
    public JdbcTemplate jdbcTemplate(DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }
}
