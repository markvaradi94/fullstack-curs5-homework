package ro.fasttrackit.curs5homework.controller;

import org.springframework.web.bind.annotation.*;
import ro.fasttrackit.curs5homework.domain.Country;
import ro.fasttrackit.curs5homework.service.CountryService;

import java.net.http.HttpHeaders;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("countries")
public class CountryController {
    private final CountryService countryService;

    public CountryController(CountryService countryService) {
        this.countryService = countryService;
    }

    @GetMapping
    public List<Country> getAllCountries(@RequestParam(required = false) String includeNeighbour,
                                         @RequestParam(required = false) String excludeNeighbour) {
        return countryService.getCountries(includeNeighbour, excludeNeighbour);
    }

    @GetMapping("names")
    public List<String> getAllCountryNames() {
        return countryService.getAllCountryNames();
    }

    @GetMapping("{countryId}/capital")
    public String getCapitalOfCountry(@PathVariable Integer countryId) {
        return countryService.getCapitalOfCountry(countryId);
    }

    @GetMapping("{countryId}/population")
    public Long getPopulationOfCountry(@PathVariable Integer countryId) {
        return countryService.getPopulationOfCountry(countryId);
    }

    @GetMapping("{countryId}/neighbours")
    public List<String> getCountryNeighbours(@PathVariable Integer countryId) {
        return countryService.getNeighboursOfCountry(countryId);
    }

    @GetMapping("population")
    public Map<String, Long> getCountryNamesMappedToPopulation() {
        return countryService.mapCountryNamesToPopulations();
    }

    @GetMapping("mine")
    public Country getMyCountry(@RequestHeader(value = "X-Country", defaultValue = "Romania") String countryName) {
        return countryService.getMyCountry(countryName);
    }
}
