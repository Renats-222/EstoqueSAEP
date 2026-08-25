package br.com.rsdvf.estoque.service;

import br.com.rsdvf.estoque.model.Movimento;
import br.com.rsdvf.estoque.model.Produto;
import br.com.rsdvf.estoque.repository.MovimentoRepository;
import br.com.rsdvf.estoque.repository.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class MovimentoService {

    @Autowired
    private MovimentoRepository movimentoRepository;

    @Autowired
    private ProdutoRepository produtoRepository;

    //Listar saídas (qtd < 0 em ordem decrescente)
    public List<Movimento> listarTodasSaidas() {
        return movimentoRepository.findAllSaidasOrderByDataMovtoDesc();
    }

    //Registrar entrada de produtos e atualizar saldo
    public Movimento registrarEntrada(Long produtoId, BigDecimal quantidade) {
        if (quantidade == null || quantidade.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("A quantidade de entrada deve ser maior que zero.");
        }

        Produto produto = produtoRepository.findById(produtoId)
                .orElseThrow(() -> new IllegalArgumentException("Produto não encontrado."));

        produto.setSaldo(produto.getSaldo().add(quantidade));
        produtoRepository.save(produto);

        Movimento movimento = new Movimento();
        movimento.setProduto(produto);
        movimento.setQtd(quantidade);
        movimento.setDataMovto(LocalDateTime.now());

        return movimentoRepository.save(movimento);
    }

    public List<Object[]> listarMovimentacoesPorPeriodo(LocalDateTime inicio, LocalDateTime fim) {
        return movimentoRepository.findMovimentacoesPorPeriodo(inicio, fim);
    }

    public List<Object[]> listarTopSaidasPorPeriodo(LocalDateTime inicio, LocalDateTime fim) {
        return movimentoRepository.findTopSaidasPorPeriodo(inicio, fim);
    }
}