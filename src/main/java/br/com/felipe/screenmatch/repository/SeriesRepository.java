package br.com.felipe.screenmatch.repository;

import br.com.felipe.screenmatch.dto.EpisodioDTO;
import br.com.felipe.screenmatch.model.Categoria;
import br.com.felipe.screenmatch.model.Episodio;
import br.com.felipe.screenmatch.model.Serie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface SeriesRepository extends JpaRepository<Serie, Long> {
    Optional<Serie> findByTituloContainingIgnoreCase(String nomeSerie);

    List<Serie> findByAtoresContainingIgnoreCaseAndAvaliacaoGreaterThanEqual(String nomeAtor, Double avaliacao);

    List<Serie> findTop5ByOrderByAvaliacaoDesc();

    List<Serie> findByGenero(Categoria categoria);

    //List<Serie> findByTotalTemporadasLessThanEqualAndAvaliacaoGreaterThanEqual(int numeroTemporadas, double avaliacao);

    @Query("SELECT s FROM Serie s WHERE s.totalTemporadas <= :numeroTemporadas AND s.avaliacao >= :avaliacao")
    List<Serie> buscaSerieTemporadaEAvaliacao(int numeroTemporadas, double avaliacao);

    @Query("SELECT e FROM Serie s JOIN s.episodios e WHERE e.titulo ILIKE %:trechoEpisodio%")
    List<Episodio> episodiosPorTrecho(String trechoEpisodio);

    @Query("SELECT e FROM Serie s JOIN s.episodios e WHERE s =:serie ORDER BY e.avaliacao DESC LIMIT 5")
    List<Episodio> topEpisodioPorSerie(Serie serie);

    @Query("SELECT e FROM Serie s JOIN s.episodios e WHERE s =:serie AND YEAR(e.dataLancamento) >= :anoLancamento")
    List<Episodio> episodiosPorSerieEAno(Serie serie, int anoLancamento);

    @Query("SELECT s FROM Serie s JOIN s.episodios e GROUP BY s ORDER BY MAX(e.dataLancamento) DESC LIMIT 5")
    List<Serie> lancamentosMaisRecentes();

    @Query("SELECT e FROM Serie s JOIN s.episodios e WHERE s.id = :id AND e.temporada = :numeroTemporada")
    List<Episodio> obterEpisodiosPorTemporada(Long id, Long numeroTemporada);

    @Query("SELECT e FROM Serie s JOIN s.episodios e WHERE s = :serie ORDER BY e.avaliacao DESC LIMIT 5")
    List<Episodio> obterTop5Episodios(Serie serie);
}
