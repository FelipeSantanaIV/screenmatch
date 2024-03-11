package br.com.felipe.screenmatch.service;

import br.com.felipe.screenmatch.dto.EpisodioDTO;
import br.com.felipe.screenmatch.dto.SerieDTO;
import br.com.felipe.screenmatch.model.Categoria;
import br.com.felipe.screenmatch.model.Episodio;
import br.com.felipe.screenmatch.model.Serie;
import br.com.felipe.screenmatch.repository.SeriesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class SerieService {

    @Autowired
    private SeriesRepository repositorio;

    public List<SerieDTO> obterTodasAsSeries(){
        return converteDados(repositorio.findAll());
    }

    public List<SerieDTO> obterTop5Series() {
        return converteDados(repositorio.findTop5ByOrderByAvaliacaoDesc());
    }

    public List<SerieDTO> obterLancamentos() {
        return converteDados(repositorio.lancamentosMaisRecentes());
    }

    public SerieDTO obterPorId(Long id) {
        Optional<Serie> serie = repositorio.findById(id);
        if (serie.isPresent()){
            Serie s = serie.get();
            return new SerieDTO(s.getId(), s.getTitulo(), s.getTotalTemporadas(),
                    s.getAvaliacao(), s.getGenero(), s.getAtores(),
                    s.getPoster(), s.getSinopse());
        }
        return null;
    }
    public List<EpisodioDTO> obterTodasAsTemporadas(Long id) {
        Optional<Serie> serie = repositorio.findById(id);
        if (serie.isPresent()){
            Serie s = serie.get();
            return  s.getEpisodios().stream()
                    .map(e -> new EpisodioDTO(e.getTemporada(), e.getNumeroEpisodio(), e.getTitulo()))
                    .collect(Collectors.toList());
        }
        return null;
    }
    public List<EpisodioDTO> obterTemporadasPorNumero(Long id, Long numeroTemporada) {

        return repositorio.obterEpisodiosPorTemporada(id, numeroTemporada)
                .stream()
                .map(e -> new EpisodioDTO(e.getTemporada(), e.getNumeroEpisodio(), e.getTitulo()))
                        .collect(Collectors.toList());
    }
    public List<SerieDTO> obterSeriesPorCategoria(String genero) {
        Categoria categoria = Categoria.fromPortugues(genero);
        return converteDados(repositorio.findByGenero(categoria));
    }
    public List<EpisodioDTO> obterTop5Episodios(Long id) {
        var serie = repositorio.findById(id).get();
        return repositorio.obterTop5Episodios(serie)
                .stream()
                .map(e -> new EpisodioDTO(e.getNumeroEpisodio(), e.getTemporada(),e.getTitulo()))
                .collect(Collectors.toList());
    }
    private List<SerieDTO> converteDados(List<Serie> series){
        return  series.stream()
                .map(s -> new SerieDTO(s.getId(), s.getTitulo(), s.getTotalTemporadas(),
                        s.getAvaliacao(), s.getGenero(), s.getAtores(),
                        s.getPoster(), s.getSinopse()))
                .collect(Collectors.toList());
    }

}
