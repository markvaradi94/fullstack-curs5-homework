package ro.fasttrackit.curs5homework.reader;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import ro.fasttrackit.curs5homework.config.CountryFileConfig;
import ro.fasttrackit.curs5homework.domain.Country;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Collectors;

@Component
@Profile("file")
public class FileCountryReader implements CountryReader {
    private final CountryFileConfig fileConfig;

    public FileCountryReader(CountryFileConfig fileConfig) {
        this.fileConfig = fileConfig;
    }

    @Override
    public List<Country> readCountries() throws IOException {
        return Files.lines(Path.of(fileConfig.getSourceFile()))
                .map(this::readCountry)
                .collect(Collectors.toList());
    }

    private Country readCountry(String line) {
        String[] tokens = line.split("[|]");
        return new Country(
                tokens[0],
                tokens[1],
                Long.parseLong(tokens[2]),
                Long.parseLong(tokens[3]),
                tokens[4],
                tokens.length > 5 ? tokens[5] : ""
        );
    }

}
