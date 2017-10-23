package at.paukl.springplay;

import at.paukl.springplay.beans.DbTest;
import at.paukl.springplay.domain.TestEntity;
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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.slf4j.LoggerFactory.getLogger;

@Configuration
@EnableJpaRepositories
@SpringBootApplication
@ComponentScan(basePackageClasses = {DbTest.class, TestEntity.class})
@EnableAutoConfiguration
@EnableTransactionManagement(proxyTargetClass = true, mode = AdviceMode.PROXY)
public class Main {
    private static final Logger LOG = getLogger(Main.class);

    public static void main(String[] args) throws Exception {
        final ConfigurableApplicationContext ctx = SpringApplication.run(Main.class, args);
        final DbTest dbTest = ctx.getBeansOfType(DbTest.class).values().iterator().next();

        LOG.info("saving...");
        final ArrayList<Long> ids = new ArrayList<>();
        for (String name : Arrays.asList("foo", "bar", "baz", "bazing", "bazinga", "blub", "blubbi", "blubbedi")) {
            ids.add(dbTest.saveEntityWithChild(name));
        }
        LOG.info("Testing lazy loading with Hibernate version {} ...", org.hibernate.Version.getVersionString());
        final List<String> names = dbTest.loadAllByIds(ids);
        LOG.info("found names: {}", names.stream().collect(Collectors.joining(", ")));
        LOG.info("done");

    }
}