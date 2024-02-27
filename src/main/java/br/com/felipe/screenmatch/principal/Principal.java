package br.com.felipe.screenmatch.principal;

import br.com.felipe.screenmatch.model.DadosEpsodios;
import br.com.felipe.screenmatch.model.DadosSerie;
import br.com.felipe.screenmatch.model.DadosTemporadas;
import br.com.felipe.screenmatch.service.ConsumeAPI;
import br.com.felipe.screenmatch.service.ConverteDados;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Principal {
    private Scanner leitura = new Scanner(System.in);
    private ConsumeAPI consumeAPI = new ConsumeAPI();

    private ConverteDados conversor = new ConverteDados();

    private final String ENDERECO = "https://omdbapi.com/?t=";
    private final String API_KEY = "&apikey=8b1809b5";

    public void exibeMenu(){
        System.out.println("Digite o nome da série para busca: ");
        var nomesSerie = leitura.nextLine();
        var json = consumeAPI.obterDados(ENDERECO + nomesSerie.replace(" ", "+") + API_KEY);
        DadosSerie dados = conversor.obterDados(json, DadosSerie.class);
        System.out.println(dados);


        List<DadosTemporadas> temporadas = new ArrayList<>();

		for (int i  = 1; i<= dados.totalTemporadas(); i++){
			json = consumeAPI.obterDados(ENDERECO + nomesSerie.replace(" ", "+") + "&season=" + i + API_KEY);
			DadosTemporadas dadosTemporadas = conversor.obterDados(json, DadosTemporadas.class);
			temporadas.add(dadosTemporadas);
		}
		temporadas.forEach(System.out::println);

//        for(int i = 0; i < dados.totalTemporadas(); i++){
//            List<DadosEpsodios> epsodiosTemporadas = temporadas.get(i).episodios();
//            for(int j = 0; j < epsodiosTemporadas.size(); j++){
//                System.out.println(epsodiosTemporadas.get(j).titulo());
//            }
//        }

        temporadas.forEach(t -> t.episodios().forEach(e -> System.out.println(e.titulo())));
    }
}
