package com.gabriel.estoque.repository;

import com.gabriel.estoque.model.Livro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface LivroRepository extends JpaRepository<Livro, Long> {

    // save, findAll, findById e delete já vêm prontos do JpaRepository.

    /*
     * Filtros combináveis: cada filtro só é aplicado quando for informado.
     * Se o parâmetro vier null, a condição "(:param IS NULL OR ...)" vira verdadeira
     * e o filtro é ignorado. Assim funciona com 0, 1, 2 ou os 3 filtros juntos.
     */
    @Query("""
            SELECT l FROM Livro l
            WHERE (:autor IS NULL OR LOWER(l.autor) = LOWER(:autor))
              AND (:categoria IS NULL OR LOWER(l.categoria) = LOWER(:categoria))
              AND (:precoMaximo IS NULL OR l.preco <= :precoMaximo)
            ORDER BY l.id
            """)
    List<Livro> filtrar(@Param("autor") String autor,
                        @Param("categoria") String categoria,
                        @Param("precoMaximo") Double precoMaximo);
}
