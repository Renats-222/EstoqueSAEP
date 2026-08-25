package br.com.rsdvf.estoque.controller;

import br.com.rsdvf.estoque.dto.ProdutoRequestDTO;
import br.com.rsdvf.estoque.dto.ProdutoResponseDTO;
import br.com.rsdvf.estoque.service.ProdutoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/produtos")
public class ProdutoController {

    @Autowired
    private ProdutoService produtoService; 

    // POST: Cadastrar produto (Agora usando o DTO de Entrada)
    @PostMapping
    public ResponseEntity<?> cadastrarProduto(@RequestBody ProdutoRequestDTO dto) {
        try {
            ProdutoResponseDTO novoProduto = produtoService.cadastrarProduto(dto);
            return ResponseEntity.ok(novoProduto);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // GET: Listar todos os produtos (Já estava correto, usando o DTO de Saída)
    @GetMapping
    public ResponseEntity<List<ProdutoResponseDTO>> listarProdutos() {
        return ResponseEntity.ok(produtoService.listarTodosProdutos());
    }

    // GET: Listar valor total por categoria
    @GetMapping("/total-por-categoria")
    public ResponseEntity<List<Object[]>> listarValorTotalPorCategoria() {
        return ResponseEntity.ok(produtoService.listarValorTotalPorCategoria());
    }

    // GET: Limites de Estoque
    @GetMapping("/limites")
    public ResponseEntity<List<Map<String, Object>>> listarLimitesEstoque() {
        return ResponseEntity.ok(produtoService.listarLimitesEstoque());
    }
}