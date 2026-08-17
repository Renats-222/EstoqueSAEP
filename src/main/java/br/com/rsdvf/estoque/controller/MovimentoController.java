package br.com.rsdvf.estoque.controller;

import br.com.rsdvf.estoque.model.Movimento;
import br.com.rsdvf.estoque.service.MovimentoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/movimentos")
public class MovimentoController {

    @Autowired
    private MovimentoService movimentoService;

    // GET: Listar todas as saídas
    @GetMapping("/saidas")
    public ResponseEntity<List<Movimento>> listarSaidas() {
        return ResponseEntity.ok(movimentoService.listarTodasSaidas());
    }

    // POST: Registrar entrada
    @PostMapping("/entradas")
    public ResponseEntity<?> registrarEntrada(
            @RequestParam Long produtoId,
            @RequestParam BigDecimal quantidade) {
        try {
            Movimento movimento = movimentoService.registrarEntrada(produtoId, quantidade);
            return ResponseEntity.ok(movimento);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // GET: Relatório por período
    @GetMapping("/periodo")
    public ResponseEntity<List<Object[]>> listarMovimentacoesPorPeriodo(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime inicio,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime fim) {
        return ResponseEntity.ok(movimentoService.listarMovimentacoesPorPeriodo(inicio, fim));
    }

    // GET: Top saídas por período
    @GetMapping("/top-saidas")
    public ResponseEntity<List<Object[]>> listarTopSaidas(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime inicio,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime fim) {
        return ResponseEntity.ok(movimentoService.listarTopSaidasPorPeriodo(inicio, fim));
    }
}