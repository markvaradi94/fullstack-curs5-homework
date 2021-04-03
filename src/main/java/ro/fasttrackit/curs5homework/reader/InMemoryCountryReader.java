package ro.fasttrackit.curs5homework.reader;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import ro.fasttrackit.curs5homework.domain.Country;

import java.util.List;

@Component
@Profile("memory")
public class InMemoryCountryReader implements CountryReader {
    @Override
    public List<Country> readCountries() {
        return List.of(
                new Country(
                        "Brazil",
                        "Brasília",
                        206135893L,
                        8515767L,
                        "Americas",
                        "ARG~BOL~COL~GUF~GUY~PRY~PER~SUR~URY~VEN"

                ),
                new Country(
                        "Burkina Faso",
                        "Ouagadougou",
                        19034397L,
                        272967L,
                        "Africa",
                        "BEN~CIV~GHA~MLI~NER~TGO"

                ),
                new Country(
                        "Mongolia",
                        "Ulan Bator",
                        3093100L,
                        1564110L,
                        "Asia",
                        "CHN~RUS"

                ),
                new Country(
                        "Nicaragua",
                        "Managua",
                        6262703L,
                        130373L,
                        "Americas",
                        "CRI~HND"

                ),
                new Country(
                        "Romania",
                        "Bucharest",
                        19861408L,
                        238391L,
                        "Europe",
                        "BGR~HUN~MDA~SRB~UKR"

                )
        );
    }
}
