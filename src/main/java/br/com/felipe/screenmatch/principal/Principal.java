package br.com.felipe.screenmatch.principal;

import br.com.felipe.screenmatch.model.DadosEpsodios;
import br.com.felipe.screenmatch.model.DadosSerie;
import br.com.felipe.screenmatch.model.DadosTemporadas;
import br.com.felipe.screenmatch.model.Episodio;
import br.com.felipe.screenmatch.service.ConsumeAPI;
import br.com.felipe.screenmatch.service.ConverteDados;

import java.sql.SQLOutput;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

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

        List<DadosEpsodios> dadosEpsodios = temporadas.stream()
                .flatMap(t -> t.episodios().stream())
                .toList();

//        System.out.println("\nTop 10 Episodios: ");
//        dadosEpsodios.stream()
//                .filter(e -> !e.avaliacao().equalsIgnoreCase("N/A"))
//                .sorted(Comparator.comparing(DadosEpsodios::avaliacao).reversed())
//                .limit(10)
//                .forEach(System.out::println);

//        System.out.println("\nTop 10 Episodios: ");
//        dadosEpsodios.stream()
//                .filter(e -> !e.avaliacao().equalsIgnoreCase("N/A"))
//                .peek(e -> System.out.println("Primeiro filtro(N/A)" + e))
//                .sorted(Comparator.comparing(DadosEpsodios::avaliacao).reversed())
//                .peek(e -> System.out.println(" Ordenação" + e))
//                .limit(10)
//                .peek(e -> System.out.println(" limite" + e))
//                .map(e -> e.titulo().toUpperCase())
//                .peek(e -> System.out.println(" Mapeamento" + e))
//                .forEach(System.out::println);

        // Lista para filtrar temporada por ano
        List<Episodio> episodios = temporadas.stream()
                .flatMap(t -> t.episodios().stream()
                        .map(d -> new Episodio(t.numero(), d))
                ).collect(Collectors.toList());

        episodios.forEach(System.out::println);

        System.out.println("\nDigite um trecho do titulo do episódio: ");
        var trechoTitulo = leitura.nextLine();

        Optional<Episodio> episodioBuscado = episodios.stream()
                .filter(e -> e.getTitulo().toUpperCase().contains(trechoTitulo.toUpperCase()))
                .findFirst();
        if (episodioBuscado.isPresent()){
            System.out.println("Episódio encontrado! " );
            System.out.println("Nome do episódio: " + episodioBuscado.get().getTitulo() + " " +
                    "| Número episódio: " + episodioBuscado.get().getNumeroEpisodio());
            System.out.println("Temporada: " + episodioBuscado.get().getTemporada());
        } else {
            System.out.println("Episódio não encontrado!");
        }
//
//        System.out.println("A partir de que ano você deseja ver os episódios ");
//        var ano = leitura.nextInt();
//        leitura.nextLine();
//
//        DateTimeFormatter formatador = DateTimeFormatter.ofPattern("dd/MM/yyyy");
//
//        LocalDate dataBusca = LocalDate.of(ano, 1, 1);
//        episodios.stream()
//                .filter(e -> e.getDataLancamento() != null && e.getDataLancamento().isAfter(dataBusca))
//                .forEach(e -> System.out.println(
//                        "Temporada: " + e.getTemporada() +
//                        " Episódio: " + e.getTitulo() +
//                        " Data lançamento: " + e.getDataLancamento().format(formatador)
//                ));

        // Agrupamento de dados com map
        Map<Integer, Double> avaliacaoPorTemporada = episodios
                .stream()
                .filter(e -> e.getAvaliacao() > 0.0)
                .collect(Collectors.groupingBy(Episodio::getTemporada,
                        Collectors.averagingDouble(Episodio::getAvaliacao)));
        System.out.println(avaliacaoPorTemporada);

        // Coleção de estatisticas, min, max, media, cont(quantidade de notas) e soma das notas.
        DoubleSummaryStatistics est = episodios.stream()
                .filter(e -> e.getAvaliacao() > 0.0)
                .collect(Collectors.summarizingDouble(Episodio::getAvaliacao));
        System.out.println("Média: " + est.getAverage());
        System.out.println("Melhor episódio: " + est.getMax());
        System.out.println("Pior Episódio: " + est.getMin());
        System.out.println("Total de episódios avaliados: " + est.getCount());
    }
}
