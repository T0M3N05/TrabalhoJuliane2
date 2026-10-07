package com.gabriel.estoque;

import com.gabriel.estoque.model.Livro;
import com.gabriel.estoque.repository.LivroRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class EstoqueApplication {

	public static void main(String[] args) {
		SpringApplication.run(EstoqueApplication.class, args);
	}

	// Cadastra os livros iniciais do Trabalho I somente se a tabela estiver vazia,
	// para não duplicar registros a cada reinício da aplicação.
	@Bean
	CommandLineRunner carregarDadosIniciais(LivroRepository repository) {
		return args -> {
			if (repository.count() == 0) {
				repository.save(new Livro("O Senhor dos Anéis", "J.R.R. Tolkien", "Fantasia", 89.90));
				repository.save(new Livro("O Hobbit", "J.R.R. Tolkien", "Fantasia", 59.90));
				repository.save(new Livro("1984", "George Orwell", "Ficção", 45.00));
			}
		};
	}

}
