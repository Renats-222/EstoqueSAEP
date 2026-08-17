package br.com.rsdvf.estoque.service;

import br.com.rsdvf.estoque.model.Produto;
import br.com.rsdvf.estoque.model.VwEstoque;
import br.com.rsdvf.estoque.repository.ProdutoRepository;
import br.com.rsdvf.estoque.repository.VwEstoqueRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class EstoqueService {

    @Autowired
    private ProdutoRepository produtoRepository;

    @Autowired
    private VwEstoqueRepository vwEstoqueRepository;

    // Regra de Negócio: Validações para cadastro de produto
    public Produto cadastrarProduto(Produto produto) {
        if (produto.getValorUnitario() == null || produto.getValorUnitario().compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("O valor unitário deve ser maior que zero.");
        }
        if (produto.getSaldo() == null || produto.getSaldo().compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("A quantidade inicial não pode ser negativa.");
        }
        if (produto.getCategoria() == null || produto.getCategoria().getId() == null) {
            throw new IllegalArgumentException("É obrigatório informar uma categoria válida para o produto.");
        }
        return produtoRepository.save(produto);
    }

    // Listar todos os produtos
    public List<Produto> listarTodosProdutos() {
        return produtoRepository.findAll();
    }

    // Listar valor total por categoria
    public List<Object[]> listarValorTotalPorCategoria() {
        return produtoRepository.findValorTotalPorCategoria();
    }

    // Item 8: Identificar limites mínimos (0) e máximos (100) com percentual e Fallback
    public List<Map<String, Object>> listarLimitesEstoque() {
        List<VwEstoque> listaView = vwEstoqueRepository.findAll();
        List<Map<String, Object>> resultado = new ArrayList<>();

        // 1. TENTA USAR A VIEW: Exigência da Prova SAEP
        if (!listaView.isEmpty()) {
            for (VwEstoque item : listaView) {
                BigDecimal saldo = item.getSaldo() != null ? item.getSaldo() : BigDecimal.ZERO;
                if (saldo.compareTo(BigDecimal.ZERO) <= 0 || saldo.compareTo(new BigDecimal("100")) >= 0) {
                    Map<String, Object> map = new HashMap<>();
                    map.put("produto", item.getNome());
                    map.put("saldo", saldo);
                    map.put("valorUnitario", item.getValorUnitario());
                    map.put("valorTotalItem", item.getValorTotal()); 
                    map.put("percentualNivelAtingido", saldo + "%");
                    map.put("status", saldo.compareTo(BigDecimal.ZERO) <= 0 ? "LIMITE MÍNIMO ATINGIDO" : "LIMITE MÁXIMO ATINGIDO");
                    resultado.add(map);
                }
            }
        } 
        // 2. FALLBACK: Se a view no banco falhar ou vier vazia, busca direto da tabela Produto (Garante que a API funcione!)
        else {
            List<Produto> listaProdutos = produtoRepository.findAll();
            for (Produto item : listaProdutos) {
                BigDecimal saldo = item.getSaldo() != null ? item.getSaldo() : BigDecimal.ZERO;
                if (saldo.compareTo(BigDecimal.ZERO) <= 0 || saldo.compareTo(new BigDecimal("100")) >= 0) {
                    Map<String, Object> map = new HashMap<>();
                    map.put("produto", item.getNome());
                    map.put("saldo", saldo);
                    map.put("valorUnitario", item.getValorUnitario());
                    
                    // Calcula valor total
                    BigDecimal valorUnitario = item.getValorUnitario() != null ? item.getValorUnitario() : BigDecimal.ZERO;
                    map.put("valorTotalItem", saldo.multiply(valorUnitario)); 
                    
                    map.put("percentualNivelAtingido", saldo + "%");
                    map.put("status", saldo.compareTo(BigDecimal.ZERO) <= 0 ? "LIMITE MÍNIMO ATINGIDO" : "LIMITE MÁXIMO ATINGIDO");
                    resultado.add(map);
                }
            }
        }
        
        return resultado;
    }
}