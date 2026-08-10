package br.com.rsdvf.estoque.controller;

import br.com.rsdvf.estoque.model.Produto;
import br.com.rsdvf.estoque.service.EstoqueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/produtos")
public class EstoqueController {

    @Autowired
    private EstoqueService estoqueService;

    // URI: http://localhost:8080/api/produtos
    @PostMapping
    public ResponseEntity<?> cadastrarProduto(@RequestBody Produto produto) {
        try {
            Produto novoProduto = estoqueService.cadastrarProduto(produto);
            return ResponseEntity.ok(novoProduto);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}