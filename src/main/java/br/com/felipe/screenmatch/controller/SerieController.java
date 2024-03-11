package br.com.felipe.screenmatch.controller;

import br.com.felipe.screenmatch.dto.EpisodioDTO;
import br.com.felipe.screenmatch.dto.SerieDTO;
import br.com.felipe.screenmatch.model.Categoria;
import br.com.felipe.screenmatch.model.Episodio;
import br.com.felipe.screenmatch.service.SerieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/series")
public class SerieController {

    @Autowired
    private SerieService service;

    @GetMapping("")
    public List<SerieDTO> obterSeries(){
        return  service.obterTodasAsSeries();
    }

    @GetMapping("/top5")
    public List<SerieDTO> obterTop5Series(){
        return service.obterTop5Series();
    }

    @GetMapping("/lancamentos")
    public List<SerieDTO> obterLancamentos(){
        return service.obterLancamentos();
    }

    @GetMapping("/{id}")
    public SerieDTO obterPorID(@PathVariable Long id){
        return service.obterPorId(id);
    }

    @GetMapping("/{id}/temporadas/{numeroTemporada}")
    public List<EpisodioDTO> obterTemporadasPorNumero(@PathVariable Long id, @PathVariable Long numeroTemporada){
        return service.obterTemporadasPorNumero(id, numeroTemporada);
    }

    @GetMapping("/{id}/temporadas/todas")
    public List<EpisodioDTO> obterTodasTemproadas(@PathVariable Long id){
        return service.obterTodasAsTemporadas(id);
    }

    @GetMapping("/categoria/{genero}")
    public List<SerieDTO> obterSeriesPorCategoria(@PathVariable String genero) {
        return service.obterSeriesPorCategoria(genero);
    }

    @GetMapping("/{id}/temporadas/top")
    public List<EpisodioDTO> obterTop5Episodios(@PathVariable Long id) {
        return service.obterTop5Episodios(id);
    }

}
