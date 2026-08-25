package br.com.rsdvf.estoque.service;

import br.com.rsdvf.estoque.dto.ProdutoRequestDTO;
import br.com.rsdvf.estoque.dto.ProdutoResponseDTO;
import br.com.rsdvf.estoque.model.Categoria;
import br.com.rsdvf.estoque.model.Produto;
import br.com.rsdvf.estoque.model.VwEstoque;
import br.com.rsdvf.estoque.repository.CategoriaRepository;
import br.com.rsdvf.estoque.repository.ProdutoRepository;
import br.com.rsdvf.estoque.repository.VwEstoqueRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ProdutoService {

    @Autowired
    private ProdutoRepository produtoRepository;

    @Autowired
    private CategoriaRepository categoriaRepository;

    @Autowired
    private VwEstoqueRepository vwEstoqueRepository;

    // DTO: Validações para cadastro de produto
    public ProdutoResponseDTO cadastrarProduto(ProdutoRequestDTO dto) {
        if (dto.getValorUnitario() == null || dto.getValorUnitario().compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("O valor unitário deve ser maior que zero.");
        }
        if (dto.getSaldo() == null || dto.getSaldo().compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("A quantidade inicial não pode ser negativa.");
        }
        if (dto.getCategoriaId() == null) {
            throw new IllegalArgumentException("É obrigatório informar o ID de uma categoria válida.");
        }

        Categoria categoria = categoriaRepository.findById(dto.getCategoriaId())
                .orElseThrow(() -> new IllegalArgumentException("Categoria não encontrada no banco de dados."));

        // Converte DTO para Entidade
        Produto produto = new Produto();
        produto.setNome(dto.getNome());
        produto.setSaldo(dto.getSaldo());
        produto.setValorUnitario(dto.getValorUnitario());
        produto.setCategoria(categoria);

        Produto produtoSalvo = produtoRepository.save(produto);

        return converterParaResponseDTO(produtoSalvo);
    }

    // DTO: Listar todos os produtos
    public List<ProdutoResponseDTO> listarTodosProdutos() {
        return produtoRepository.findAll().stream()
                .map(this::converterParaResponseDTO)
                .collect(Collectors.toList());
    }
    
    // Método auxiliar para converter Entidade em DTO
    private ProdutoResponseDTO converterParaResponseDTO(Produto produto) {
        ProdutoResponseDTO dto = new ProdutoResponseDTO();
        dto.setId(produto.getId());
        dto.setNome(produto.getNome());
        dto.setSaldo(produto.getSaldo());
        dto.setValorUnitario(produto.getValorUnitario());
        if (produto.getCategoria() != null) {
            dto.setNomeCategoria(produto.getCategoria().getCategoria()); // Pega o nome ao invés do objeto inteiro
        }
        return dto;
    }

    // Listar valor total por categoria (MANTÉM IGUAL)
    public List<Object[]> listarValorTotalPorCategoria() {
        return produtoRepository.findValorTotalPorCategoria();
    }

    // Item 8: Identificar limites mínimos e máximos (MANTÉM IGUAL)
    public List<Map<String, Object>> listarLimitesEstoque() {
        List<VwEstoque> listaView = vwEstoqueRepository.findAll();
        List<Map<String, Object>> resultado = new ArrayList<>();

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
        } else {
            List<Produto> listaProdutos = produtoRepository.findAll();
            for (Produto item : listaProdutos) {
                BigDecimal saldo = item.getSaldo() != null ? item.getSaldo() : BigDecimal.ZERO;
                if (saldo.compareTo(BigDecimal.ZERO) <= 0 || saldo.compareTo(new BigDecimal("100")) >= 0) {
                    Map<String, Object> map = new HashMap<>();
                    map.put("produto", item.getNome());
                    map.put("saldo", saldo);
                    map.put("valorUnitario", item.getValorUnitario());
                    
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