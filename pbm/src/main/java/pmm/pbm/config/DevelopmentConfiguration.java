package pmm.pbm.config;

import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

@Configuration("development")
public class DevelopmentConfiguration extends BaseConfiguration implements EnvironmentAware {

    @Override
    public void setEnvironment(Environment environment) {
        // TODO Auto-generated method stub

    }

}
