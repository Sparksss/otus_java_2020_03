package springboot.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * Created by ilya on Sep, 2020
 */
@Configuration
@ConfigurationProperties(prefix = "alphavantage")
public class AlphavantageConf {
    @Getter
    @Setter
    private String apikey;

    @Getter
    @Setter
    private String url;

}
