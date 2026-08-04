/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.rsdvf.estoque.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 *
 * @author digma
 */

@Entity
@Table(name = "movimento") 
public class Movimento {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne
    @JoinColumn(name = "id_produto")
    private Produto produto;
    private BigDecimal qtd;
    
    @Column(name = "data_movto")
    private LocalDateTime dataMovto;
    
    // Mapeia o tipo da movimentação: "ENTRADA" ou "SAIDA"
    @Column(name = "tipo")
    private String tipo;

    public Movimento() {
    }

    public Movimento(Long id, Produto produto, BigDecimal qtd, LocalDateTime dataMovto, String tipo) {
        this.id = id;
        this.produto = produto;
        this.qtd = qtd;
        this.dataMovto = dataMovto;
        this.tipo = tipo;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Produto getProduto() {
        return produto;
    }

    public void setProduto(Produto produto) {
        this.produto = produto;
    }

    public BigDecimal getQtd() {
        return qtd;
    }

    public void setQtd(BigDecimal qtd) {
        this.qtd = qtd;
    }

    public LocalDateTime getDataMovto() {
        return dataMovto;
    }

    public void setDataMovto(LocalDateTime dataMovto) {
        this.dataMovto = dataMovto;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
}
