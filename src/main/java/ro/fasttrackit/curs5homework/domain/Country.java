package ro.fasttrackit.curs5homework.domain;

import lombok.*;
import org.springframework.validation.annotation.Validated;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "countries")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@ToString
@Validated
public class Country {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotBlank
    private String name;

    @NotBlank
    private String capital;

    @NotNull
    private Long population;

    @NotNull
    private Long area;

    @NotBlank
    private String continent;

    private String neighbours;

    public Country(
            @NotBlank String name,
            @NotBlank String capital,
            @NotNull Long population,
            @NotNull Long area,
            @NotBlank String continent,
            String neighbours
    ) {
        this.name = name;
        this.capital = capital;
        this.population = population;
        this.area = area;
        this.continent = continent;
        this.neighbours = neighbours;
    }
}
