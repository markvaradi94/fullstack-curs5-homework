package ro.fasttrackit.curs5homework.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.RequestScope;
import ro.fasttrackit.curs5homework.domain.Country;
import ro.fasttrackit.curs5homework.reader.CountryReader;
import ro.fasttrackit.curs5homework.repository.CountryRepository;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import static java.util.Arrays.asList;
import static java.util.Collections.unmodifiableList;
import static java.util.stream.Collectors.*;

@Service
public class CountryService {
    private final CountryReader countryReader;
    private final CountryRepository countryRepository;

    public CountryService(CountryReader countryReader, CountryRepository countryRepository) {
        this.countryReader = countryReader;
        this.countryRepository = countryRepository;
        persistCountriesInDb();
    }

    public List<String> getAllCountryNames() {
        return countryRepository.findAll().stream()
                .map(Country::getName)
                .collect(toUnmodifiableList());
    }

    public String getCapitalOfCountry(Integer countryId) {
        Country countryToFind = getOrThrow(countryId);
        return countryToFind.getCapital();
    }

    public Long getPopulationOfCountry(Integer countryId) {
        Country countryToFind = getOrThrow(countryId);
        return countryToFind.getPopulation();
    }

    public List<String> getNeighboursOfCountry(Integer countryId) {
        Country countryToFind = getOrThrow(countryId);
        return parseCountryNeighbours(countryToFind);
    }

    public List<Country> getContinentCountries(String continentName, Long minPopulation) {
        return minPopulation == null ?
                getCountriesOnContinent(continentName) :
                getCountriesOnContinentWithMinPopulationOf(continentName, minPopulation);
    }

    private List<Country> getCountriesOnContinent(String continentName) {
        return countryRepository.findAll().stream()
                .filter(country -> country.getContinent().equalsIgnoreCase(continentName))
                .collect(toUnmodifiableList());
    }

    private List<Country> getCountriesOnContinentWithMinPopulationOf(String continentName, Long minPopulation) {
        return getCountriesOnContinent(continentName).stream()
                .filter(country -> country.getPopulation() >= minPopulation)
                .collect(toUnmodifiableList());
    }

    public List<Country> getCountries(String includeNeighbour, String excludeNeighbour) {
        return includeNeighbour == null && excludeNeighbour == null ?
                getAllCountries() : getCountriesByNeighbours(includeNeighbour, excludeNeighbour);
    }

    private List<Country> getAllCountries() {
        return unmodifiableList(countryRepository.findAll());
    }

    private List<Country> getCountriesByNeighbours(String includeNeighbour, String excludeNeighbour) {
        return countryRepository.findAll().stream()
                .filter(country -> isNeighbouredByIncludedButNotByExcluded(includeNeighbour, excludeNeighbour, country))
                .collect(toUnmodifiableList());
    }

    public Map<String, Long> mapCountryNamesToPopulations() {
        return countryRepository.findAll().stream()
                .collect(toMap(
                        Country::getName,
                        Country::getPopulation,
                        Math::addExact,
                        TreeMap::new
                ));
    }

    public Map<String, List<Country>> mapCountriesByContinent() {
        return countryRepository.findAll().stream()
                .collect(groupingBy(
                        Country::getContinent,
                        TreeMap::new,
                        toUnmodifiableList()
                ));
    }

    public Country getMyCountry(String countryName) {
        return getAllCountries().stream()
                .filter(country -> country.getName().equalsIgnoreCase(countryName))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Couldn't find country with name:  " + countryName));
    }

    private boolean isNeighbouredByIncludedButNotByExcluded(String includeNeighbour, String excludeNeighbour, Country country) {
        return country.getNeighbours().contains(includeNeighbour.toUpperCase()) &&
                !country.getNeighbours().contains(excludeNeighbour.toUpperCase());
    }

    private List<String> parseCountryNeighbours(Country country) {
        return asList(country.getNeighbours().split("~"));
    }

    private Country getOrThrow(Integer countryId) {
        return countryRepository.findAll().stream()
                .filter(country -> country.getId().equals(countryId))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Could not find country with ID: " + countryId));
    }

    private void persistCountriesInDb() {
        try {
            countryReader.readCountries().forEach(this::saveCountry);
        } catch (IOException exception) {
            System.err.println("Could not load countries from file.\n" + exception.getMessage());
        }
    }

    private Country saveCountry(Country country) {
        return countryRepository.save(country);
    }
}
