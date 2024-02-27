package br.com.felipe.screenmatch.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record DadosEpsodios(@JsonAlias("Title") String titulo,
                            @JsonAlias("Episode") Integer numeroEpsodio,
                            @JsonAlias("imdbRating") String avaliacao,
                            @JsonAlias("Released") String lancamento) {
}
