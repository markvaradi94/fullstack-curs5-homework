package ro.fasttrackit.curs5homework.reader;

import ro.fasttrackit.curs5homework.domain.Country;

import java.io.IOException;
import java.util.List;

public interface CountryReader {
    List<Country> readCountries() throws IOException;
}
