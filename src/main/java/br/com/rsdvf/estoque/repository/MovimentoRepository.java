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

    List<Movimento> findByTipoOrderByDataMovtoDesc(String tipo);

    @Query("SELECT m FROM Movimento m WHERE m.dataMovto BETWEEN :inicio AND :fim ORDER BY m.dataMovto ASC")
    List<Movimento> findMovimentacoesPorPeriodo(@Param("inicio") LocalDateTime inicio, @Param("fim") LocalDateTime fim);

    @Query("SELECT m.produto.nome, SUM(m.qtd), SUM(m.qtd * m.produto.valorUnitario) " +
           "FROM Movimento m WHERE m.tipo = 'SAIDA' AND m.dataMovto BETWEEN :inicio AND :fim " +
           "GROUP BY m.produto.nome ORDER BY SUM(m.qtd) DESC")
    List<Object[]> findTopSaidasPorPeriodo(@Param("inicio") LocalDateTime inicio, @Param("fim") LocalDateTime fim);
}