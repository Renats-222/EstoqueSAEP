package br.com.rsdvf.estoque.repository;

import br.com.rsdvf.estoque.model.Movimento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface MovimentoRepository extends JpaRepository<Movimento, Long> {

    @Query("SELECT m FROM Movimento m WHERE m.qtd < 0 ORDER BY m.dataMovto DESC")
    List<Movimento> findAllSaidasOrderByDataMovtoDesc();

    @Query("SELECT m.produto.nome, 'UN', " +
           "SUM(CASE WHEN m.qtd > 0 THEN m.qtd ELSE 0 END), " +
           "SUM(CASE WHEN m.qtd < 0 THEN ABS(m.qtd) ELSE 0 END), " +
           "SUM(m.qtd), " +
           "SUM(CASE WHEN m.qtd > 0 THEN (m.qtd * m.produto.valorUnitario) ELSE 0 END), " +
           "SUM(CASE WHEN m.qtd < 0 THEN (ABS(m.qtd) * m.produto.valorUnitario) ELSE 0 END) " +
           "FROM Movimento m " +
           "WHERE m.dataMovto BETWEEN :inicio AND :fim " +
           "GROUP BY m.produto.nome, m.produto.valorUnitario")
    List<Object[]> findMovimentacoesPorPeriodo(@Param("inicio") LocalDateTime inicio, @Param("fim") LocalDateTime fim);

    @Query("SELECT m.produto.nome, " +
           "SUM(ABS(m.qtd)), " +
           "SUM(ABS(m.qtd) * m.produto.valorUnitario) " +
           "FROM Movimento m " +
           "WHERE m.qtd < 0 AND m.dataMovto BETWEEN :inicio AND :fim " +
           "GROUP BY m.produto.nome " +
           "ORDER BY SUM(ABS(m.qtd)) DESC")
    List<Object[]> findTopSaidasPorPeriodo(@Param("inicio") LocalDateTime inicio, @Param("fim") LocalDateTime fim);
}