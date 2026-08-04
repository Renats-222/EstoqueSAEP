package br.com.rsdvf.estoque.repository;

import br.com.rsdvf.estoque.model.Produto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

// Interface responsável pelas consultas da tabela 'produto'
@Repository
public interface ProdutoRepository extends JpaRepository<Produto, Long> {

    // Consulta customizada (JPQL) para calcular o valor total por categoria
    @Query("SELECT p.categoria.categoria, SUM(p.saldo * p.valorUnitario) " +
           "FROM Produto p GROUP BY p.categoria.categoria")
    List<Object[]> findValorTotalPorCategoria();
}