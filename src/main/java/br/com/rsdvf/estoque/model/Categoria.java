package br.com.rsdvf.estoque.model;

// Importações das anotações do JPA (Jakarta Persistence API) para mapeamento de banco de dados
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

// @Entity: Informa ao Spring JPA que esta classe é uma entidade mapeada para uma tabela do banco de dados
@Entity
// @Table: Define o nome exato da tabela correspondente no banco de dados MySQL
@Table(name = "categoria")
public class Categoria {

    // @Id: Especifica que este atributo representa a Chave Primária (PK) da tabela
    @Id
    // @GeneratedValue: Define que a chave primária é gerada automaticamente pelo banco (Auto Increment)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Atributo que mapeia a coluna 'categoria' da tabela
    private String categoria;

    // CONSTRUTOR PADRÃO (Sem parâmetros):
    // Obrigatório pelo JPA/Hibernate para instanciar a classe via reflexão ao buscar dados no banco.
    public Categoria() {
    }

    // CONSTRUTOR COM PARÂMETROS:
    // Facilita a criação manual de objetos Categoria no código sem precisar usar vários setters.
    public Categoria(Long id, String categoria) {
        this.id = id;
        this.categoria = categoria;
    }

    // GETTERS E SETTERS:
    // Métodos de acesso e alteração dos atributos privados (Encapsulamento do Java)

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }
}