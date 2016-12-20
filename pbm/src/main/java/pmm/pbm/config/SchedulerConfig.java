package pmm.pbm.config;

import java.io.IOException;
import java.util.Properties;

import org.quartz.spi.JobFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.PropertiesFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.UrlResource;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.scheduling.quartz.SpringBeanJobFactory;
import org.springframework.util.ResourceUtils;

import lombok.val;

@Configuration
@EnableScheduling
public class SchedulerConfig {

    @Bean
    public SpringBeanJobFactory jobFactory() {
        return new SpringBeanJobFactory();
    }

    @Bean
    public SchedulerFactoryBean schedulerFactoryBean(JobFactory jobFactory) throws IOException {
        SchedulerFactoryBean factory = new SchedulerFactoryBean();
        factory.setAutoStartup(false);
        factory.setQuartzProperties(quartzProperties());
        return factory;
    }

    @Value("${schedule.quartz.config-location}")
    private String configLocation;

    private Properties quartzProperties() throws IOException {
        val bean = new PropertiesFactoryBean();
        bean.setLocation(new UrlResource(ResourceUtils.getURL(configLocation)));
        bean.afterPropertiesSet();
        return bean.getObject();
    }
}
