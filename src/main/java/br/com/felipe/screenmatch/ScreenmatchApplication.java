package br.com.felipe.screenmatch;

import br.com.felipe.screenmatch.model.DadosSerie;
import br.com.felipe.screenmatch.service.ConsumeAPI;
import br.com.felipe.screenmatch.service.ConverteDados;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ScreenmatchApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(ScreenmatchApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		ConsumeAPI consumeAPI = new ConsumeAPI();
		var json = consumeAPI.obterDados("http://www.omdbapi.com/?t=gilmore+girls&apikey=8b1809b5");
		System.out.println(json);
		ConverteDados conversor = new ConverteDados();
		DadosSerie dados = conversor.obterDados(json, DadosSerie.class);
		System.out.println(dados);
	}

}
