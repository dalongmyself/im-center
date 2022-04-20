package com.im;

import com.im.configuration.properties.ImProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

/**
 * @author dalong
 * @date 2022/4/1 18:33
 */
@EnableConfigurationProperties({
        ImProperties.class
})
@SpringBootApplication
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
