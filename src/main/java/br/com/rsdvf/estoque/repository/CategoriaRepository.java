package br.com.rsdvf.estoque.repository;

import br.com.rsdvf.estoque.model.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

// Interface responsável por operações de CRUD na tabela 'categoria'
@Repository
public interface CategoriaRepository extends JpaRepository<Categoria, Long> {
}
