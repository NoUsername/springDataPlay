package at.paukl.springplay;

import at.paukl.springplay.beans.DbTest;
import at.paukl.springplay.domain.EntityWithConverter;
import org.slf4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AdviceMode;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import static org.slf4j.LoggerFactory.getLogger;

@Configuration
@EnableJpaRepositories
@SpringBootApplication
@ComponentScan(basePackageClasses = {DbTest.class, EntityWithConverter.class})
@EnableAutoConfiguration
@EnableTransactionManagement(proxyTargetClass = true, mode = AdviceMode.PROXY)
public class Main {
    private static final Logger LOG = getLogger(Main.class);

    public static void main(String[] args) throws Exception {
        final ConfigurableApplicationContext ctx = SpringApplication.run(Main.class, args);
        final DbTest dbTest = ctx.getBeansOfType(DbTest.class).values().iterator().next();

        LOG.info("saving...");
        dbTest.saveEntity("hello");
        LOG.info("loading...");
        dbTest.loadAndRecreateDto();
        LOG.info("done");
    }
}