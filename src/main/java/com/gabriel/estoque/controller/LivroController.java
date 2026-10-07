package com.gabriel.estoque.controller;

import com.gabriel.estoque.model.Livro;
import com.gabriel.estoque.repository.LivroRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/livros")
public class LivroController {

    // Antes: List<Livro> em memória. Agora: repository que acessa o PostgreSQL.
    private final LivroRepository repository;

    public LivroController(LivroRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public ResponseEntity<List<Livro>> listar(
            @RequestParam(required = false) String autor,
            @RequestParam(required = false) String categoria,
            @RequestParam(required = false) Double precoMaximo) {

        List<Livro> resultado;
        if (autor == null && categoria == null && precoMaximo == null) {
            resultado = repository.findAll();
        } else {
            resultado = repository.filtrar(autor, categoria, precoMaximo);
        }
        return ResponseEntity.ok(resultado);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Livro> buscarPorId(@PathVariable Long id) {
        return repository.findById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Livro> cadastrar(@RequestBody Livro novoLivro) {
        novoLivro.setId(null); // o ID é sempre gerado pelo banco
        Livro salvo = repository.save(novoLivro);
        return ResponseEntity.status(HttpStatus.CREATED).body(salvo);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Livro> atualizar(@PathVariable Long id, @RequestBody Livro livroAtualizado) {
        return repository.findById(id)
                .map(livroExistente -> {
                    livroExistente.setTitulo(livroAtualizado.getTitulo());
                    livroExistente.setAutor(livroAtualizado.getAutor());
                    livroExistente.setCategoria(livroAtualizado.getCategoria());
                    livroExistente.setPreco(livroAtualizado.getPreco());
                    return ResponseEntity.ok(repository.save(livroExistente));
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable Long id) {
        return repository.findById(id)
                .map(livro -> {
                    repository.delete(livro);
                    return ResponseEntity.noContent().<Void>build();
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}
