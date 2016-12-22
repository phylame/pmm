package pmm.pbm.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Data;

@Data
@ConfigurationProperties(prefix = "pmm", ignoreUnknownFields = true)
public class PmmProperties {
    private Paging paging;

    @Data
    public static class Paging {
        private Integer shownPages = 8;
        private Integer defaultLimit = 10;
    }
}
