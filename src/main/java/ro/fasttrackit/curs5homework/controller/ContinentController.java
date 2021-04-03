package ro.fasttrackit.curs5homework.controller;

import org.springframework.web.bind.annotation.*;
import ro.fasttrackit.curs5homework.domain.Country;
import ro.fasttrackit.curs5homework.service.CountryService;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("continents")
public class ContinentController {
    private final CountryService countryService;

    public ContinentController(CountryService countryService) {
        this.countryService = countryService;
    }

    @GetMapping("{continentName}/countries")
    public List<Country> getCountriesOnContinent(@PathVariable String continentName,
                                                 @RequestParam(required = false) Long minPopulation) {
        return countryService.getContinentCountries(continentName, minPopulation);
    }

    @GetMapping("countries")
    public Map<String, List<Country>> getMapFromContinentsToCountries() {
        return countryService.mapCountriesByContinent();
    }
}
