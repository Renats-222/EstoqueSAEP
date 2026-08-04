package br.com.rsdvf.estoque.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.math.BigDecimal;

@Entity
@Table(name = "vw_estoque")
public class VwEstoque {

    @Id
    private Long id;

    private String nome;

    private BigDecimal saldo;

    @Column(name = "valor_unitario")
    private BigDecimal valorUnitario;

    @Column(name = "valor_total")
    private BigDecimal valorTotal;

    // CONSTRUTOR PADRÃO 
    public VwEstoque() {
    }

    // CONSTRUTOR 
    public VwEstoque(Long id, String nome, BigDecimal saldo, BigDecimal valorUnitario, BigDecimal valorTotal) {
        this.id = id;
        this.nome = nome;
        this.saldo = saldo;
        this.valorUnitario = valorUnitario;
        this.valorTotal = valorTotal;
    }

    // GETTERS 

    public Long getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public BigDecimal getSaldo() {
        return saldo;
    }

    public BigDecimal getValorUnitario() {
        return valorUnitario;
    }

    public BigDecimal getValorTotal() {
        return valorTotal;
    }
}