package br.com.rsdvf.estoque.service;

import br.com.rsdvf.estoque.model.Produto;
import br.com.rsdvf.estoque.repository.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

// @Service: Indica que esta classe gerencia a lógica e as regras de negócio
@Service
public class EstoqueService {

    @Autowired
    private ProdutoRepository produtoRepository;

    // Regra de Negócio: Validações para cadastro de produto
    public Produto cadastrarProduto(Produto produto) {
        // Validação do Valor Unitário
        if (produto.getValorUnitario() == null || produto.getValorUnitario().compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("O valor unitário deve ser maior que zero.");
        }

        // Validação da Quantidade (Saldo)
        if (produto.getSaldo() == null || produto.getSaldo().compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("A quantidade inicial não pode ser negativa.");
        }

        // Validação da Categoria
        if (produto.getCategoria() == null || produto.getCategoria().getId() == null) {
            throw new IllegalArgumentException("É obrigatório informar uma categoria válida para o produto.");
        }

        // Salva o produto validado no MySQL
        return produtoRepository.save(produto);
    }
}