package pmm.pbm.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {
    public static int shownPages;

    public static int defaultLimit;

    @Value("${my.paging.shown-pages}")
    private void setShownPages(int pages) {
        shownPages = pages;
    }

    @Value("${my.paging.default-limit}")
    private void setPageLimit(int limit) {
        defaultLimit = limit;
    }
}
