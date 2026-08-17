package br.com.rsdvf.estoque.controller;

import br.com.rsdvf.estoque.model.Produto;
import br.com.rsdvf.estoque.service.EstoqueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/produtos")
public class EstoqueController {

    @Autowired
    private EstoqueService estoqueService;

    // POST: Cadastrar produto
    @PostMapping
    public ResponseEntity<?> cadastrarProduto(@RequestBody Produto produto) {
        try {
            Produto novoProduto = estoqueService.cadastrarProduto(produto);
            return ResponseEntity.ok(novoProduto);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // GET: Listar todos os produtos
    @GetMapping
    public ResponseEntity<List<Produto>> listarProdutos() {
        return ResponseEntity.ok(estoqueService.listarTodosProdutos());
    }

    // GET: Listar valor total por categoria
    @GetMapping("/total-por-categoria")
    public ResponseEntity<List<Object[]>> listarValorTotalPorCategoria() {
        return ResponseEntity.ok(estoqueService.listarValorTotalPorCategoria());
    }

    // GET: Limites de Estoque
    @GetMapping("/limites")
    public ResponseEntity<List<Map<String, Object>>> listarLimitesEstoque() {
        return ResponseEntity.ok(estoqueService.listarLimitesEstoque());
    }
}