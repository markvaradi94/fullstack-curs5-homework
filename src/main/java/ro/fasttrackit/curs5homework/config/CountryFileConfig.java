package ro.fasttrackit.curs5homework.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotBlank;

@EnableConfigurationProperties(CountryFileConfig.class)
@ConfigurationProperties(prefix = "country")
@ConstructorBinding
@Validated
public class CountryFileConfig {
    @NotBlank
    private final String sourceFile;

    public CountryFileConfig(@NotBlank String sourceFile) {
        this.sourceFile = sourceFile;
    }

    public String getSourceFile() {
        return sourceFile;
    }
}
