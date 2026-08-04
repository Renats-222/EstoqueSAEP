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

/**
 *
 * @author digma
 */
@Entity
@Table(name = "produto")
public class Produto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;

    // RELACIONAMENTO CHAVE ESTRANGEIRA (FK):
    // @ManyToOne: Indica um relacionamento "Muitos para Um" (Muitos produtos pertencem a Uma categoria)
    // @JoinColumn: Mapeia o nome exato da coluna da Chave Estrangeira na tabela produto ('id_categoria')
    @ManyToOne
    @JoinColumn(name = "id_categoria")
    private Categoria categoria;

    private BigDecimal saldo;

    // @Column: Usado quando o nome do atributo Java é diferente do nome da coluna na tabela MySQL
    @Column(name = "valor_unitario")
    private BigDecimal valorUnitario;

    // CONSTRUTOR PADRÃO:
    // Exigência da especificação JPA/Hibernate
    public Produto() {
    }

    // CONSTRUTOR COM PARÂMETROS:
    // Útil para inicialização rápida do objeto Produto
    public Produto(Long id, String nome, Categoria categoria, BigDecimal saldo, BigDecimal valorUnitario) {
        this.id = id;
        this.nome = nome;
        this.categoria = categoria;
        this.saldo = saldo;
        this.valorUnitario = valorUnitario;
    }

    // GETTERS E SETTERS (Encapsulamento)

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    public BigDecimal getSaldo() {
        return saldo;
    }

    public void setSaldo(BigDecimal saldo) {
        this.saldo = saldo;
    }

    public BigDecimal getValorUnitario() {
        return valorUnitario;
    }

    public void setValorUnitario(BigDecimal valorUnitario) {
        this.valorUnitario = valorUnitario;
    }
}
