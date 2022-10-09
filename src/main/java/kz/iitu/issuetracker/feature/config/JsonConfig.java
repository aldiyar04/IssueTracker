package kz.iitu.issuetracker.feature.config;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

@Configuration
@AllArgsConstructor
public class JsonConfig {
    private final ObjectMapper objectMapper;

    @PostConstruct
    public void excludeNullFieldsFromJson() {
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
    }
}
