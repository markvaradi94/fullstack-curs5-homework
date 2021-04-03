package ro.fasttrackit.curs5homework.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ro.fasttrackit.curs5homework.domain.Country;

public interface CountryRepository extends JpaRepository<Country, Integer> {
}
