package br.com.rsdvf.estoque.controller;

import br.com.rsdvf.estoque.model.Movimento; // Import do Movimento adicionado
import br.com.rsdvf.estoque.model.Produto;
import br.com.rsdvf.estoque.service.EstoqueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/produtos")
public class EstoqueController {

    @Autowired
    private EstoqueService estoqueService;

    // Endpoint POST: Cadastrar produto
    @PostMapping
    public ResponseEntity<?> cadastrarProduto(@RequestBody Produto produto) {
        try {
            Produto novoProduto = estoqueService.cadastrarProduto(produto);
            return ResponseEntity.ok(novoProduto);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // Endpoint GET: Listar todos os produtos
    @GetMapping
    public ResponseEntity<List<Produto>> listarProdutos() {
        List<Produto> produtos = estoqueService.listarTodosProdutos();
        return ResponseEntity.ok(produtos);
    }

    // Endpoint GET: Listar valor total por categoria
    @GetMapping("/total-por-categoria")
    public ResponseEntity<List<Object[]>> listarValorTotalPorCategoria() {
        List<Object[]> totais = estoqueService.listarValorTotalPorCategoria();
        return ResponseEntity.ok(totais);
    }

    // Endpoint GET: Listar todas as saídas de produtos (Item 5 da Prova)
    @GetMapping("/saidas")
    public ResponseEntity<List<Movimento>> listarSaidas() {
        List<Movimento> saidas = estoqueService.listarTodasSaidas();
        return ResponseEntity.ok(saidas);
    }
    
    // Endpoint POST: Registrar entrada de produto
    @PostMapping("/entradas")
    public ResponseEntity<?> registrarEntrada(
            @org.springframework.web.bind.annotation.RequestParam Long produtoId,
            @org.springframework.web.bind.annotation.RequestParam java.math.BigDecimal quantidade) {
        try {
            Movimento movimento = estoqueService.registrarEntrada(produtoId, quantidade);
            return ResponseEntity.ok(movimento);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    
    @GetMapping("/movimentacoes-periodo")
    public ResponseEntity<List<Object[]>> listarMovimentacoesPorPeriodo(
            @org.springframework.web.bind.annotation.RequestParam @org.springframework.format.annotation.DateTimeFormat(iso = org.springframework.format.annotation.DateTimeFormat.ISO.DATE_TIME) java.time.LocalDateTime inicio,
            @org.springframework.web.bind.annotation.RequestParam @org.springframework.format.annotation.DateTimeFormat(iso = org.springframework.format.annotation.DateTimeFormat.ISO.DATE_TIME) java.time.LocalDateTime fim) {
        List<Object[]> relatorio = estoqueService.listarMovimentacoesPorPeriodo(inicio, fim);
        return ResponseEntity.ok(relatorio);
    }

    @GetMapping("/top-saidas")
    public ResponseEntity<List<Object[]>> listarTopSaidas(
            @org.springframework.web.bind.annotation.RequestParam @org.springframework.format.annotation.DateTimeFormat(iso = org.springframework.format.annotation.DateTimeFormat.ISO.DATE_TIME) java.time.LocalDateTime inicio,
            @org.springframework.web.bind.annotation.RequestParam @org.springframework.format.annotation.DateTimeFormat(iso = org.springframework.format.annotation.DateTimeFormat.ISO.DATE_TIME) java.time.LocalDateTime fim) {
        List<Object[]> topSaidas = estoqueService.listarTopSaidasPorPeriodo(inicio, fim);
        return ResponseEntity.ok(topSaidas);
    }
}