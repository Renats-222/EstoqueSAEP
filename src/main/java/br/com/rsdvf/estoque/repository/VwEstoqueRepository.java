package br.com.rsdvf.estoque.repository;

import br.com.rsdvf.estoque.model.VwEstoque;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VwEstoqueRepository extends JpaRepository<VwEstoque, Long> {
}